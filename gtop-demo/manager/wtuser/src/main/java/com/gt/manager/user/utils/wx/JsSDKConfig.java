package com.gt.manager.user.utils.wx;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取jsSDK配置
 */
public class JsSDKConfig {
    public static Map<String, Object> getJsSDKConfig(String appId, String jsTicket, String url){
        String noncestr = "Wm3WZYTPz0wzccnW";
        String timestamp = System.currentTimeMillis()+"";
        timestamp = timestamp.substring(0, timestamp.length()-3);
        String signature = JsapiTicketUtil.getSign(noncestr, jsTicket, timestamp, url);
        Map<String, Object> res = new HashMap<>();
        res.put("appId", appId);
        res.put("timestamp", timestamp);
        res.put("nonceStr", noncestr);
        res.put("signature", signature);
        return res;
    }
}
