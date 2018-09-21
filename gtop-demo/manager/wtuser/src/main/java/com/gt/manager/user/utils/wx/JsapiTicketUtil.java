package com.gt.manager.user.utils.wx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;


/**
 * 获取JS-SDK使用权限签名算法
 * @author why
 *
 */
public class JsapiTicketUtil {
	private static Logger logger = Logger.getLogger(JsapiTicketUtil.class);// 日志
	/**
	 * 生成签名
	 * @param request
	 * @param jsapiTicket
	 * @return
	 */
	public static void createSign(HttpServletRequest request,String jsapiTicket, String appId, String jsApiList){
		String yuming = request.getServerName();
//		Integer duankou = request.getServerPort();
		String xiangmuming = request.getContextPath();
		String qitalujing = request.getServletPath();
		String canshu = request.getQueryString();
		String url = null;
		if(canshu != null && !"".equals(canshu)){
			url = "http://"+yuming+xiangmuming+qitalujing+"?"+canshu;
		}else{
			url = "http://"+yuming+xiangmuming+qitalujing;
		}
		String noncestr = "Wm3WZYTPz0wzccnW";
		String timestamp = System.currentTimeMillis()+"";
		timestamp = timestamp.substring(0, timestamp.length()-3);
		String signature = JsapiTicketUtil.getSign(noncestr,jsapiTicket,timestamp,url);
		request.setAttribute("appId", appId);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("nonce", noncestr);
		request.setAttribute("signature", signature);
		request.setAttribute("jsApiList", jsApiList);
	}
	
	public static String getJsapiTicket(String accessToken){
		String result = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		com.alibaba.fastjson.JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", null);
		if(null != jsonObject){
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(0 == errorCode){
				result = jsonObject.getString("ticket")+"";
			}else{
				System.out.println("失败errorCode:{"+errorCode+"},errmsg:{"+errorMsg+"}");
			}
		}
		return result;
	}
	
	public static String getSign(String noncestrs, String jsapi_tickets, String timestamps, String urls){
//		String jsapi_ticket = null;
//		String noncestr = null;
//		String timestamp = null;
//		String url = null;
		String[] arr = new String[] { "jsapi_ticket="+jsapi_tickets, "noncestr="+noncestrs, "timestamp="+timestamps, "url="+urls };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		//StringBuilder content = new StringBuilder();
		String string1 = null;
		for (int i = 0; i < arr.length; i++) {
			//content.append(arr[i]);
			if(string1 == null){
				string1 = arr[i]+"&";
			}else{
				string1 +=arr[i]+"&";
			}
		}
		string1 = string1.substring(0, string1.length()-1);
//		System.out.println(string1);
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(string1.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tmpStr;
	}
	/**
	 * 将字节数组转换为十六进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 *            *
	 * @return
	 **/
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
//		String accessToken = WeixinUtil.getAccessToken("wxc46cf2f17f1fa43b", "cba3b5220d05b851fdd0ea4a06336c09").getToken();
//		System.out.println(getJsapiTicket(accessToken));
//		noncestr=Wm3WZYTPz0wzccnW
//		jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
//		timestamp=1414587457
//		url=http://mp.weixin.qq.com
		//F4D90DAF4B3BCA3078AB155816175BA34C443A7B
		//371E882DF02E3E902FC67AE6536FA46FA946CD57
		String noncestr = "Wm3WZYTPz0wzccnW";
		String jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VETz4gt-SJ3rpSmmsr0vSsLj8lZCFvxw7i0wpXfOOXkRiXUt72GgyvysGzmE7WN23g";
		String timestamp = "1421118787";
		String url = "http://server.gtexpress.cn";
		System.out.println(getSign(noncestr,jsapi_ticket,timestamp,url));
		System.out.println(jsapi_ticket.length());
		Date today=new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(today));
		Date d = sdf.parse(sdf.format(today));
		System.out.println(d.getTime());
		System.out.println(System.currentTimeMillis());
		
		
//		
	}
}
