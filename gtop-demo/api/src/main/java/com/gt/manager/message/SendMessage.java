package com.gt.manager.message;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>ClassName: SendMessage.java</p>
 * <p>Description:短信发送接口实现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月28日 上午10:14:01</p>
 */
public class SendMessage {
    // 短信发送地址url
    private final static String INF_URL = "http://q.hl95.com:8061";

    // 用户名
    private final static String username = "wyct";

    // 密码
    private final static String password = "@Weiye123";

    // 企业id
    private final static String epid = "123067";

    // 短信内容
    // 短信内容
    public static final String content1 = "您的验证码是";

    public static final String content2 = "，有效期30分钟，请尽快使用！";

    /**
     * @Title:sendMessage  
     * @Description:发送短信方法
     * @param mobile 手机号码
     * @param message 短信内容
     * @throws UnsupportedEncodingException 
     */
    public static String sendMessage(String mobile, String code) throws UnsupportedEncodingException {
        Map<String, String> pmap = new HashMap<String, String>();
        pmap.put("username", username);
        pmap.put("password", password);
        pmap.put("phone", mobile);
        String message = content1 + code + content2;
        pmap.put("message", URLEncoder.encode(message, "gb2312"));
        pmap.put("epid", epid);
        pmap.put("linkid", "");
        pmap.put("subcode", "");
        String pstr = "";
        if (pmap != null && pmap.size() > 0) {
            for (Map.Entry<String, String> entry : pmap.entrySet()) {
                pstr += "&" + entry.getKey() + "=" + entry.getValue();
            }
            pstr = pstr.substring(1);
        }
        String result = sendGet(INF_URL + "?" + pstr);
        return result;
    }

    public static String sendGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        BufferedReader in = null;
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(get);
            try {
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    String tempStr = "";
                    in = new BufferedReader(new InputStreamReader(entity.getContent()));
                    StringBuffer content = new StringBuffer();
                    while ((tempStr = in.readLine()) != null) {
                        content.append(tempStr);
                    }
                    return content.toString();
                }
            }
            finally {
                httpResponse.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
