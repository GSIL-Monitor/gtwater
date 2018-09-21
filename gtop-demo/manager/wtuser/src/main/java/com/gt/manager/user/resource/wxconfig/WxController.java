package com.gt.manager.user.resource.wxconfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.common.RequestCodeEnum;
import com.gt.manager.entity.wtWxconfig.WtWxconfig;
import com.gt.manager.user.service.wxconfig.WxconfigService;
import com.gt.manager.user.utils.wx.JsSDKConfig;
import com.gt.manager.user.utils.wx.Long2ShortUtil;
import com.gt.manager.util.selectAdress.util.common.GeoConstant;
import com.gt.manager.util.selectAdress.util.entity.GeoCode;
import com.gt.manager.util.selectAdress.util.geocode.GeoCoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin
@Path("/wx")
@Api(value = "/WxController", description = "微信配置")
@Component
public class WxController {
    private static Logger logger = LoggerFactory.getLogger(WxController.class);
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    @Autowired
    private WxconfigService wxconfigService;

    @POST
    @Path("/wxBusiness")
    @ApiOperation(value = "/wxBusiness", notes = "获取微信配置")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage wxBusiness(ReqData r) throws Exception{
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else if(RequestCodeEnum.获取微信配置.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                logger.info("获取微信配置参数={}",jsonObject);
                if(jsonObject.containsKey("url")){
                    String url = jsonObject.getString("url");
                    url = URLDecoder.decode(url, "UTF-8");//防止页面url中有参数有&符号,把前端转码的url解码
                    WtWxconfig wtWxconfig = this.wxconfigService.queryById(1L);
                    Map<String, Object> res = JsSDKConfig.getJsSDKConfig(appId, wtWxconfig.getApiTicket(), url);
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(res);
                    dm.setMessage("获取成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else if(RequestCodeEnum.根据微信定位获取地址.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                logger.info("根据微信定位获取地址={}",jsonObject);
                if(jsonObject.containsKey("latitude") && jsonObject.containsKey("longitude")){
                    try {
                        GeoCode gc = GeoCoder.geocode(GeoConstant.WGS84LL,jsonObject.getString("latitude"),jsonObject.getString("longitude"),0);
                        Map<String, Object> res = new HashMap<>();
                        res.put("provinceName", gc.getProvince());
                        res.put("cityName", gc.getCity());
                        res.put("districtName", gc.getDistrict());
                        res.put("address", gc.getStreet()+gc.getStreetNumber());
                        dm.setResult(DataMessage.RESULT_SUCESS);
                        dm.setData(res);
                        dm.setMessage("获取成功");
                    } catch (Exception e) {
                        logger.error("根据微信定位获取地址", e);
                        e.printStackTrace();
                    }
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else{
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("请求码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取微信配置异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("获取微信配置异常");
        }
        logger.info("微信相关=",JSONObject.toJSONString(dm));
        return dm;
    }

    @POST
    @Path("/long2Short")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ "application/x-www-form-urlencoded" })
    public DataMessage long2Short(@FormParam("url") String url){
        logger.info("long2Short的url是{}", url);
        DataMessage dm = new DataMessage();
        if(StringUtils.isBlank(url)){
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("参数错误");
        }else{
            WtWxconfig w = this.wxconfigService.queryById(1L);
            String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\""+url+"\"}";
            JSONObject jsonObject = Long2ShortUtil.long2Short(w.getAccessToken(),jsonMsg);
            if(null != jsonObject && jsonObject.getInteger("errcode") == 0){
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(jsonObject.getString("short_url"));
                dm.setMessage("获取成功");
            }else{
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage(jsonObject+"");
            }
        }
        return dm;
    }


    public static void main(String[] args) {
//        String str = "{\"url\":\"http://localhost/confirmPay/watershop.html?orderNo=D20180814203417633007\"}";
//        JSONObject jsonObject = JSONObject.parseObject(str);
//        String a = jsonObject.getString("url");
//        System.out.println(a);

//        String res = "http%3A%2F%2Fwater.gyexpress.cn%2F%23%2Fstatic%2Fhome%2Findex";
        String res = "http%3A%2F%2Fwater.gyexpress.cn%2Findex.html%3Fcode%3D011495qG1PSYv004FLoG1j6NpG1495qX%26state%3Dstate%23%2Fstatic%2Fhome%2Findex";

        try {
            System.out.println(URLDecoder.decode(res,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
