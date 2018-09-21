package com.gt.manager.user.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.thirdUser.ThirdUser;
import org.apache.log4j.Logger;

public class OauthUtil {
    private static Logger logger = Logger.getLogger(OauthUtil.class);

    /**
     * 网页授权
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    public static JSONObject getOauth2AccessToken(String appId, String appSecret, String code){
        //拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        //获取网页授权凭证
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        logger.info("getOauth2AccessToken返回信息="+jsonObject);
        return jsonObject;
    }

    public static ThirdUser getUserInfo(String accessToken, String openId){
        ThirdUser t = null;
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID"
                .replace("ACCESS_TOKEN",accessToken)
                .replace("OPENID", openId);
        try {
            JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
            t = new ThirdUser();
            t.setOpenId(jsonObject.getString("openid"));
            t.setNickname(jsonObject.getString("nickname"));
            t.setUnionId(jsonObject.getString("unionid"));
            t.setIcon(jsonObject.getString("headimgurl"));
        }catch (Exception ex){
            logger.error("getUserInfo异常="+ex);
            ex.printStackTrace();
            t = null;
            return t;
        }
        return t;
    }

    public static void main(String[] args) {
        String code = "081UAKNJ19gCi60RGwNJ1b2WNJ1UAKNo";
        System.out.println(getOauth2AccessToken("wx4a34a8924e582d02","2c0f8ddb03fc06ebf5522119182b4dc8", code));
    }

}
