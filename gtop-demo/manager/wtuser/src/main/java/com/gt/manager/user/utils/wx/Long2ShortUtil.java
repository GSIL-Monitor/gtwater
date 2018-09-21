package com.gt.manager.user.utils.wx;

import com.alibaba.fastjson.JSONObject;

public class Long2ShortUtil {
    public static JSONObject long2Short(String accessToken, String jsonMsg){
        boolean result = false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", jsonMsg);
        if(null != jsonObject){
            System.out.println(jsonObject.getString("short_url"));

        }
        return jsonObject;
    }
    public static void main(String[] args) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5fe899e6a4ffb49d&redirect_uri=http%3A%2F%2Fgeneralstore.gtexpress.cn%2Fwtuser%2Fapiwtuser%2Fpartner%2FaddPartner&response_type=code&scope=snsapi_base&state=2018091208#wechat_redirect";
        String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\""+url+"\"}";
        String token = "13_BoXwB0xw1y25zr-DxBnotPdtU2yJMPHHkJw9JljZ5_s4KEQDG5AxxTvEZdZEdqHUWi9akNBIehQFh8i1Cf3vUCm1I02rxj1owyw9L8akHG3_scrNMoYmBA1mn7DZrdm-d01bhp9ESy6ytN7ZYXSfAHATSN";
        System.out.println(long2Short(token,jsonMsg));
    }
}
