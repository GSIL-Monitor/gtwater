package com.gt.manager.user.utils.wx;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 创建网页授权地址
 */
public class MakeOauthUrlUtil {
    /**
     * 创建网页授权地址
     * @param appid         公众号appid
     * @param redirect_uri  url(URLEncoder.encode转换)
     * @param scope         1：snsapi_base  2：snsapi_userinfo
     * @param state         自定义参数
     * @return
     */
    public static String makeUrl(String appid, String redirect_uri, int scope, String state){
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect"
                .replace("APPID", appid)
                .replace("REDIRECT_URI", redirect_uri)
                .replace("SCOPE", scope == 1?"snsapi_base":"snsapi_userinfo")
                .replace("STATE", StringUtils.isBlank(state)?"state":state);
        return url;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("http://www.baidocu.dmf","utf-8"));
    }
}
