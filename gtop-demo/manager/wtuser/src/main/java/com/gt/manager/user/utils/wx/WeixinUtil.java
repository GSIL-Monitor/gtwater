package com.gt.manager.user.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.user.entity.wx.AccessToken;
import net.sf.json.JSONException;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

/**
 * 微信请求工具类
 */
public class WeixinUtil {
    private static Logger logger = Logger.getLogger(WeixinUtil.class);

    /**
     * 发送请求
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return jsonObject;
    }

    /**
     * 获取token
     * @param appid
     * @param appsecret
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"
                .replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                logger.error("获取token失败 errcode:{} errmsg:{}"
                        + Integer.valueOf(jsonObject.getInteger("errcode"))
                        + jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 获取js ticket
     * @param accessToken
     * @return
     */
    public static String getJsapiTicket(String accessToken){
        String result = null;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", null);
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

    public static void main(String[] args) {
        System.out.println(getJsapiTicket("13_h8ZUHLuHvI9dpeNq8p_ZJl4jpDy4WyCLuzyTdtoUKCdXq1K5qmcxE2Nr9zEfCCeJx1PFOM86dga14nYQD9nHbYjUV4oVTVHgRnl0zOZLiED3EGBTIXdwCOr-dfbs1FXMSQe3_eZcT_DtrS1pYMWdAAANBL"));
    }

}
