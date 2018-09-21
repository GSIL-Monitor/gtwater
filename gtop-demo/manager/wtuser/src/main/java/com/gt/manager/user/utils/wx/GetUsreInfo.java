package com.gt.manager.user.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.thirdUser.ThirdUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetUsreInfo {
    private static Logger logger = LoggerFactory.getLogger(GetUsreInfo.class);

    /**
     * 根据accessToken和openId获取用户信息
     * @param accessToken  通用token
     * @param openId       微信openId
     * @return
     * 未关注返回内容  {"subscribe":0,"openid":"o5wvZs7CkOtVh6_8nhUqr9xl2Dao","tagid_list":[]}
     * 关注返回内容    {"country":"中国","qr_scene":0,"subscribe":1,"city":"","openid":"o5wvZs7CkOtVh6_8nhUqr9xl2Dao","tagid_list":[],"sex":1,"groupid":0,"language":"zh_CN","remark":"","subscribe_time":1534822536,"province":"北京","subscribe_scene":"ADD_SCENE_QR_CODE","nickname":"王慧永","headimgurl":"http://thirdwx.qlogo.cn/mmopen/tlibmG9XzkWAmto4SupLVIHMBUVMbJHjhPnlvuMc9Tkw4raKoUVBvI9HIt2R3OXmfn4S08pWvtO9IHajSv1exBfaDId8N43Mia/132","qr_scene_str":""}
     */
    public static ThirdUser getWxUserInfo(String accessToken, String openId){
        ThirdUser t = null;
        try {
            String reqUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"
                    .replace("ACCESS_TOKEN", accessToken)
                    .replace("OPENID", openId);
            JSONObject jsonObject = WeixinUtil.httpRequest(reqUrl, "GET", null);

            logger.info("获取用户信息内容:{}", jsonObject);
            t = new ThirdUser();
            t.setSubscribe(jsonObject.getInteger("subscribe"));//1关注  0未关注
            t.setOpenId(jsonObject.getString("openid"));
            t.setNickname(jsonObject.getString("nickname"));
            t.setUnionId(jsonObject.getString("unionid"));
            t.setIcon(jsonObject.getString("headimgurl"));
            logger.info(t.getOpenId()+"该用户{}",t.getSubscribe() ==1?"已关注":"未关注");
        }catch (Exception ex){
            ex.printStackTrace();
            t = null;
            logger.error("getWxUserInfo异常=", ex);
        }
        return t;
    }

    public static void main(String[] args) {
        String token = "12_6Hwm0otfuaRtFKg1fNSWzqwGz4leOYsSs_XkaX8eh6dPkd3OxkUJDQtT2oHYQjnle5chv7bM5Qm6zwPuNqhrgqVH1sn099z7jTiCjj4v4iAAScxyh5UHBVfCGpKCYonQPnSHsOHCrHe6fl6BEGKfAJASUY";
        String openId = "o5wvZs7CkOtVh6_8nhUqr9xl2Dao";
        System.out.println(getWxUserInfo(token, openId).toString());
    }
}
