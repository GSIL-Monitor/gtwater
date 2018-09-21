package com.gt.manager.user.resource.user;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.entity.user.User;
import com.gt.manager.entity.wtWxconfig.WtWxconfig;
import com.gt.manager.user.entity.user.UserAndAdd;
import com.gt.manager.user.service.thirdUser.ThirdUserService;
import com.gt.manager.user.service.user.UserService;
import com.gt.manager.user.service.wxconfig.WxconfigService;
import com.gt.manager.user.utils.wx.GetUsreInfo;
import com.gt.manager.user.utils.wx.JsonToObject;
import com.gt.manager.user.utils.wx.MakeOauthUrlUtil;
import com.gt.manager.user.utils.wx.OauthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.jvmstat.perfdata.monitor.PerfStringConstantMonitor;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@Path("/user")
@Api(value = "/UserController", description = "用户")
@Component
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    @Autowired
    private UserService userService;
    @Autowired
    private WxconfigService wxconfigService;

        //http://whyvip.viphk1.ngrok.org/wtuser/apiwtuser/user/getCode?url=http://whyvip.viphk1.ngrok.org?a=123
        //http://water.gyexpress.cn/wtuser/apiwtuser/user/getCode?url=http://water.gyexpress.cn?a=123
    @GET
    @Path("/getCode")
    @ApiOperation(value = "/getCode", notes = "获取code")
    public void getCode(@QueryParam("url") String url, @Context HttpServletResponse response){
        try {
            //url 为了防止url中包含&符号，被拆为多个参数，前端需要做encodeURI 操作
//            String oauthUrl = MakeOauthUrlUtil.makeUrl(appId, url, 1, "");
            String oauthUrl = MakeOauthUrlUtil.makeUrl(appId, URLEncoder.encode(url, "UTF-8"), 1, "");
            logger.info("重定向url={}",oauthUrl);
            response.sendRedirect(oauthUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @POST
    @Path("/getOpenId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getOpenId(ReqData r){
        String platform = r.getPlatform();
        String requestCode = r.getRequestCode();
        String params = r.getParams();
        DataMessage dm = new DataMessage();
        try {
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject json = JSONObject.parseObject(params);
                String code = json.getString("code");
                if(StringUtils.isBlank(code)){
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }else{
                    JSONObject jsonObject = OauthUtil.getOauth2AccessToken(appId, appSecret, code);
                    if(jsonObject.containsKey("errcode")){
                        dm.setResult(DataMessage.RESULT_FAILED);
                        dm.setData(null);
                        dm.setMessage(jsonObject.getString("errmsg"));
                    }else{
                        //需要snsapi_userinfo授权
//                ThirdUser t = OauthUtil.getUserInfo(jsonObject.getString("access_token"), jsonObject.getString("openid"));
                        //静默授权
                        WtWxconfig wx = this.wxconfigService.queryById(1L);
                        //注意 用户没有关注的情况
                        ThirdUser t = GetUsreInfo.getWxUserInfo(wx.getAccessToken(), jsonObject.getString("openid"));
                        if(null == t){
                            dm.setResult(DataMessage.RESULT_FAILED);
                            dm.setData(null);
                            dm.setMessage("获取失败");
                        }else{
                            Long userId = this.userService.insert(t);
                            dm.setResult(DataMessage.RESULT_SUCESS);
                            //默认注册成功，返回用户信息和默认地址信息
                            Map<String, Object> resMap = this.userService.getUserAndDefaultAdd(userId);
                            UserAndAdd ua = mapToUa(resMap);
                            dm.setData(ua);
                            dm.setMessage("获取成功");
                        }
                    }
                }
            }

        }catch (Exception ex){
            logger.error("getOpenId异常=", ex);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("获取失败");
        }
        logger.info("getOpenId=",JSONObject.toJSONString(dm));
        return dm;
    }

    /**
     * 测试方法
     * @param code
     * @return
     */
    //http://whyvip.viphk1.ngrok.org/wtuser/apiwtuser/user/getCode?url=http://whyvip.viphk1.ngrok.org?a=123
    @GET
    @Path("/getOpenIdTest")
    public String getOpenIdTest(@QueryParam("code") String code){
        DataMessage dm = new DataMessage();
        JSONObject jsonObject = OauthUtil.getOauth2AccessToken(appId, appSecret, code);
        if(jsonObject.containsKey("errcode")){
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage(jsonObject.getString("errmsg"));
        }else{
            //需要snsapi_userinfo授权
//                ThirdUser t = OauthUtil.getUserInfo(jsonObject.getString("access_token"), jsonObject.getString("openid"));
            //静默授权
            WtWxconfig wx = this.wxconfigService.queryById(1L);
            //注意 用户没有关注的情况
            ThirdUser t = GetUsreInfo.getWxUserInfo(wx.getAccessToken(), jsonObject.getString("openid"));
            if(null == t){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("获取失败");
            }else{
                Long userId = this.userService.insert(t);
                dm.setResult(DataMessage.RESULT_SUCESS);
                //默认注册成功，返回用户信息和默认地址信息
                Map<String, Object> resMap = this.userService.getUserAndDefaultAdd(userId);
                UserAndAdd ua = mapToUa(resMap);
                dm.setData(ua);
                dm.setMessage("获取成功");
            }
        }
        return JSONObject.toJSONString(dm);
    }

    public static UserAndAdd mapToUa(Map<String, Object> p){
        logger.info("UserAdnAdd=",JSONObject.toJSONString(p));
        UserAndAdd ua = new UserAndAdd();
        User u = (User) p.get("userInfo");
        ua.setUserId2(u.getId()+"");
        ua.setNickname(u.getNickname());
        ua.setIcon(u.getIcon());
        if(null != p.get("defAddress")){
            ReceiveAddress a = (ReceiveAddress) p.get("defAddress");
            a.setAddId(a.getId()+"");
            a.setUserId2(a.getUserid()+"");
            ua.setReceiveAddress(a);
        }
        return ua;
    }


}
