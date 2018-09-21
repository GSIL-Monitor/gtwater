package com.gt.manager.user.resource.wtPartner;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.entity.wtBranchesAccount.WtBranchesAccount;
import com.gt.manager.entity.wtBranchesAccountStatement.WtBranchesAccountStatement;
import com.gt.manager.entity.wtExtensioncode.WtExtensioncode;
import com.gt.manager.entity.wtPartner.WtPartner;
import com.gt.manager.entity.wtUserExtensioncode.WtUserExtensioncode;
import com.gt.manager.entity.wtWxconfig.WtWxconfig;
import com.gt.manager.user.entity.profit.ProfitDetail;
import com.gt.manager.user.service.partner.PartnerService;
import com.gt.manager.user.service.profit.ProfitService;
import com.gt.manager.user.service.thirdUser.ThirdUserService;
import com.gt.manager.user.service.user.UserService;
import com.gt.manager.user.service.wxconfig.WxconfigService;
import com.gt.manager.user.utils.date.DateUtil;
import com.gt.manager.user.utils.wx.GetUsreInfo;
import com.gt.manager.user.utils.wx.OauthUtil;
import com.gt.manager.util.fileUpload.OssImageUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/partner")
@Api(value = "/partner", description = "合伙人")
@Component
public class WtPartnerController {
    private static Logger logger = LoggerFactory.getLogger(WtPartnerController.class);
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    @Value("${wx.index_url}")
    private String indexUrl;
    @Value("${wx.partner_url}")
    private String partnerUrl;
    @Value("${wx.error_url}")
    private String errorUrl;
    @Autowired
    private ThirdUserService thirdUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ProfitService profitService;
    @Autowired
    private WxconfigService wxconfigService;

    /**
     * 合伙人二维码扫描
     * @param code      微信授权code
     * @param state     二维码内容唯一标示
     * @param response
     */
    //扫描url
    // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxca754849de3d4586&redirect_uri=http%3A%2F%2Fwhyvip.viphk1.ngrok.org%2Fwtuser%2Fapiwtuser%2Fpartner%2FaddPartner&response_type=code&scope=snsapi_base&state=456#wechat_redirect
    // http://whyvip.viphk1.ngrok.org/wtuser/apiwtuser/partner/addPartner
    //http%3A%2F%2Fwhyvip.viphk1.ngrok.org%2Fwtuser%2Fapiwtuser%2Fpartner%2FaddPartner
    @GET
    @Path("/addPartner")
    @ApiOperation(value = "/addPartner", notes = "二维码扫描")
    public void partnerScan(@QueryParam("code") String code, @QueryParam("state") String state, @Context HttpServletResponse response){
        /**
         * 0.判断二维码是否正确
         * 1.判断用户是否注册
         * 2.判断用户是否是合伙人或者被合伙
         * 3.判断二维码是否被绑定
         * 4.跳转合伙人页面  ||  添加扫描记录，跳转首页
         * 5.合伙人，或者被合伙人重复扫描，不做操作，跳转首页
         */
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;

        WtExtensioncode we = this.partnerService.queryQrByMessages(state);
        if(null == we){
            //二维码无效
//                response.sendRedirect(errorUrl+"?msg="+ URLEncoder.encode("二维码无效","UTF-8"));
            try {
                logger.info("二维码无效，跳转错误页面");
                out = response.getWriter();
                out.append("二维码无效，跳转错误页面");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (out != null) {
                    out.close();
                }
            }
        }else{
            //二维码有效
            JSONObject jsonObject = OauthUtil.getOauth2AccessToken(appId, appSecret, code);
            if(jsonObject.containsKey("errcode")){
                logger.error("code被使用");
            }
            String openId = jsonObject.getString("openid");
            ThirdUser t = this.thirdUserService.queryByOpenId(openId);
            Long userId = null;
            if(null == t){
                //用户没有注册
                //获取用户信息(需要用户授权)
//                ThirdUser register = OauthUtil.getUserInfo(jsonObject.getString("access_token"), jsonObject.getString("openid"));
                //静默获取
                WtWxconfig wx = this.wxconfigService.queryById(1L);
                ThirdUser register = GetUsreInfo.getWxUserInfo(wx.getAccessToken(), jsonObject.getString("openid"));
                //注册
                userId = this.userService.insert(register);
            }else{
                //用户已注册
                userId = t.getUserId();
            }
            WtPartner wp = this.partnerService.queryParentByUserId(userId);
            if(null == wp){
                //不是合伙人
                WtUserExtensioncode wue = this.partnerService.queryScanRecordByUserId(userId);
                if(null == wue){
                    //没有被合伙，判断二维码是否被使用
                    if(null == we.getPartnerId()){
                        try {
                            logger.info("跳转添加合伙人页面");
                            response.sendRedirect(partnerUrl+"?userId="+userId+"&messages="+we.getMessages());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        //添加扫码记录
                        WtUserExtensioncode add = new WtUserExtensioncode();
                        add.setCodeId(we.getId());
                        add.setUserId(userId);
                        if(t != null){
                            add.setUserName(t.getNickname());
                        }
                        this.partnerService.addWtUserExtensioncode(add);
                        //被合伙，添加记录，跳转首页
                        try {
                            logger.info("被合伙，跳转首页");
                            response.sendRedirect(indexUrl);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    //被合伙，跳转首页
                    try {
                        response.sendRedirect(indexUrl);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }else{
                //是合伙人，跳转首页
                try {
                    logger.info("是合伙人，跳转首页");
                    response.sendRedirect(indexUrl);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    @POST
    @Path("/partnerReg")
    @ApiOperation(value = "/partnerReg", notes = "合伙人注册")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage partnerReg(ReqData r) throws IOException {
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
                JSONObject jsonObject = JSONObject.parseObject(params);
                if(jsonObject.containsKey("userId") && jsonObject.containsKey("messages") && jsonObject.containsKey("userName") && jsonObject.containsKey("phone")
                        && jsonObject.containsKey("zmFile") && jsonObject.containsKey("fmFile")){
                    Long userId = Long.parseLong(jsonObject.getString("userId"));
                    String messages = jsonObject.getString("messages");//二维码信息
                    String userName = jsonObject.getString("userName");
                    String phone = jsonObject.getString("phone");
                    String zmFile = jsonObject.getString("zmFile");//正面图片
                    String fmFile = jsonObject.getString("fmFile");//反面图片

                    WtPartner add = new WtPartner();
                    add.setUserId(userId);//用户id
                    add.setUserName(userName);//用户名称
                    add.setPhone(phone);//联系方式
                    add.setQrCode(messages);
                    com.gt.gtop.entity.base.DataMessage dm1 = OssImageUploadUtil.uploadBase64Image(zmFile,"gtwater/");
                    if(dm1.getResult() != 0){
                        dm.setResult(DataMessage.RESULT_FAILED);
                        dm.setData(null);
                        dm.setMessage(dm1.getMessage());
                        return dm;
                    }
                    add.setCardFront(dm1.getData()+"");//正面照片
                    com.gt.gtop.entity.base.DataMessage dm2 = OssImageUploadUtil.uploadBase64Image(fmFile,"gtwater/");
                    if(dm2.getResult() != 0){
                        dm.setResult(DataMessage.RESULT_FAILED);
                        dm.setData(null);
                        dm.setMessage(dm2.getMessage());
                        return dm;
                    }
                    add.setCardBack(dm2.getData()+"");//反面照片

                    Long id = this.partnerService.addPartner(add);
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(id);
                    dm.setMessage("添加成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("添加合伙人异常",ex);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("系统异常");
        }
        logger.info("合伙人注册=",JSONObject.toJSONString(dm));
        return dm;
    }

    /**
     * 提成明细
     * @return
     */
    @POST
    @Path("/partnerProfitDetails")
    @ApiOperation(value = "/partnerProfitDetails", notes = "提成明细")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage partnerProfitDetails(ReqData r){
        String platform = r.getPlatform();
        String requestCode = r.getRequestCode();
        String params = r.getParams();
        DataMessage dm = new DataMessage();
        try {
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                String goodsName = null;
                Long startTime = null;
                Long endTime = null;
                Long partnerId = null;
                int pageNo = 0;
                int pageSize = 0;
                if( jsonObject.containsKey("partnerId") && jsonObject.containsKey("pageNo") && jsonObject.containsKey("pageSize")){
                    partnerId = jsonObject.getLong("partnerId");
                    pageNo = jsonObject.getInteger("pageNo");
                    pageSize = jsonObject.getInteger("pageSize");
                    if(jsonObject.containsKey("goodsName")){
                        goodsName = jsonObject.getString("goodsName");
                    }
                    if(jsonObject.containsKey("startTime")){
                        startTime = jsonObject.getLong("startTime");
                    }
                    if(jsonObject.containsKey("endTime")){
                        endTime = jsonObject.getLong("endTime");
                    }
                    List<ProfitDetail> profitDetailList = this.profitService.queryList(goodsName, startTime, endTime, pageNo, pageSize, partnerId);
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(profitDetailList);
                    dm.setMessage("查询成功");
                }else{
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("提成明细",ex);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("系统异常");
        }
        logger.info("提成明细=",JSONObject.toJSONString(dm));
        return dm;
    }

    /**
     * 根据合伙人id获取二维码内容
     * @return
     */
    @POST
    @Path("/getMyQr")
    @ApiOperation(value = "/getMyQr", notes = "根据合伙人id获取二维码内容")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getMyQr(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long partnerId = jsonObject.getLong("partnerId");
                WtExtensioncode query = new WtExtensioncode();
                query.setPartnerId(partnerId);
                query.setDelState(1);
                List<WtExtensioncode> wtExtensioncodes = this.partnerService.queryQrList(query);
                if(null != wtExtensioncodes && !wtExtensioncodes.isEmpty()){
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(wtExtensioncodes.get(0));
                    dm.setMessage("查询成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("该用户没有二维码");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("根据合伙人id获取二维码内容异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("根据合伙人id获取二维码内容异常");
        }
        logger.info("根据合伙人id查询二维码内容=",JSONObject.toJSONString(dm));
        return dm;
    }

    @POST
    @Path("/getMyPartner")
    @ApiOperation(value = "/getMyPartner", notes = "根据用户id获取合伙人信息")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getMyPartner(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(requestCode)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long userId = null;
                if(jsonObject.containsKey("userId")){
                    userId = Long.parseLong(jsonObject.getString("userId"));
                    WtPartner p = this.partnerService.queryParentByUserId(userId);
                    if(p == null){//不是合伙人
                        WtUserExtensioncode wtUserExtensioncode = this.partnerService.queryScanRecordByUserId(userId);
                        if(wtUserExtensioncode == null){//不是被合伙人
                            //点击我的推广打开扫一扫
                            dm.setResult(1);
                            dm.setData(null);
                            dm.setMessage("该用户不是合伙人");
                        }else{//是被合伙人
                            //影藏我的推广
                            dm.setResult(2);
                            dm.setData(null);
                            dm.setMessage("该用户是被合伙人");
                        }
                    }else{//是合伙人
                        //点击我的推广进入我的推广页面
                        dm.setResult(DataMessage.RESULT_SUCESS);
                        dm.setData(p);
                        dm.setMessage("改用户是合伙人");
                    }
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("根据用户id获取合伙人信息异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("根据用户id获取合伙人信息异常");
        }
        logger.info("根据用户id获取合伙人信息=",JSONObject.toJSONString(dm));
        return dm;
    }

    /**
     * 获取我的推广列表
     * @return
     */
    @POST
    @Path("/getMyList")
    @ApiOperation(value = "/getMyList", notes = "获取我的推广列表")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getMyList(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long codeId = jsonObject.getLong("codeId");
                WtUserExtensioncode query = new WtUserExtensioncode();
                query.setCodeId(codeId);
                List<WtUserExtensioncode> wtUserExtensioncodes = this.partnerService.queryScanRecordList(query);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(wtUserExtensioncodes);
                dm.setMessage("查询成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取我的推广列表异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("获取我的推广列表异常");
        }
        logger.info("获取我的推广列表=",JSONObject.toJSONString(dm));
        return dm;
    }

    @POST
    @Path("/getMyTotalIncome")
    @ApiOperation(value = "/getMyTotalIncome", notes = "获取累计收入")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getMyTotalIncome(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long userId = Long.parseLong(jsonObject.getString("userId"));
                WtBranchesAccount wtBranchesAccount = this.partnerService.queryAccountByUserId(userId);
                if(null == wtBranchesAccount){
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("没有记录");
                }else{
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(wtBranchesAccount);
                    dm.setMessage("获取成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取累计收入异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("获取累计收入异常");
        }
        logger.info("获取累计收入=",JSONObject.toJSONString(dm));
        return dm;
    }


    /**
     * 我的提前记录
     * @param r
     * @return
     */
    @POST
    @Path("/getMyAccountRecord")
    @ApiOperation(value = "/getMyAccountRecord", notes = "我的提现记录")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getMyAccountRecord(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long id = Long.parseLong(jsonObject.getString("partnerId"));//合伙人id
                Long startTime = null;
                Long endTime = null;
                if(jsonObject.containsKey("startTime")){
                    startTime = jsonObject.getLong("startTime");
                }
                if(jsonObject.containsKey("endTime")){
                    endTime = jsonObject.getLong("endTime");
                }
                List<WtBranchesAccountStatement> list = this.partnerService.queryAccountStatement(id, startTime, endTime);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(list);
                dm.setMessage("获取成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("我的提现记录异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("我的提现记录异常");
        }
        logger.info("我的提现记录=",JSONObject.toJSONString(dm));
        return dm;
    }

    /**
     * 获取我的推广首页数据
     * @param r
     * @return
     */
    @POST
    @Path("/getTotalIncomeAndCustomerNum")
    @ApiOperation(value = "/getTotalIncomeAndCustomerNum", notes = "获取我的推广首页数据")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getTotalIncomeAndCustomerNum(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(requestCode)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else{
                JSONObject jsonObject = JSONObject.parseObject(params);
                if(jsonObject.containsKey("id")){
                    Long id = jsonObject.getLong("id");
                    WtExtensioncode wtExtensioncode = this.partnerService.queryQrByPartnerId(id);
                    WtUserExtensioncode query = new WtUserExtensioncode();
                    query.setCodeId(wtExtensioncode.getId());
                    List<WtUserExtensioncode> wtUserExtensioncodes = this.partnerService.queryScanRecordList(query);
                    WtBranchesAccount wtBranchesAccount = this.partnerService.queryAccountByUserId(id);
                    List<WtBranchesAccountStatement> wtBranchesAccountStatements = this.partnerService.queryToday(id, DateUtil.dayBegin(new Date()).getTime(), DateUtil.dayEnd(new Date()).getTime());
                    Map<String, Object> resMap = new HashMap<>();
                    Long todayMoney = 0L;
                    if(null != wtBranchesAccountStatements && !wtBranchesAccountStatements.isEmpty()){
                        for (WtBranchesAccountStatement w : wtBranchesAccountStatements){
                            todayMoney += w.getMoney();
                        }
                    }
                    resMap.put("personNum",(wtUserExtensioncodes == null || wtUserExtensioncodes.isEmpty())?0:wtUserExtensioncodes.size());
                    resMap.put("money", wtBranchesAccount == null ? 0 : wtBranchesAccount.getAccount());
                    resMap.put("todayMoney", todayMoney);
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(resMap);
                    dm.setMessage("获取成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取我的推广首页数据异常", e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("获取我的推广首页数据异常");
        }
        logger.info("获取我的推广首页数据=",JSONObject.toJSONString(dm));
        return dm;
    }



    public static void main(String[] args) throws Exception {
        String res2 = URLEncoder.encode("http://whyvip.viphk1.ngrok.org/wtuser/apiwtuser/partner/addPartner","UTF-8");
        System.out.println(res2);

        System.out.println(URLDecoder.decode(res2, "UTF-8"));

        String res = "http%3A%2F%2Fwhyvip.viphk1.ngrok.org%2F%23%2Fstatic%2Fhome%2Findex";
        System.out.println(URLDecoder.decode(res, "UTF-8"));

        String res3 = "http://whyvip.viphk1.ngrok.org/#/static/home/index";
        System.out.println(URLEncoder.encode(res3, "UTF-8"));

        String res4 = "http://water.gyexpress.cn/wtuser/apiwtuser/partner/addPartner";
        System.out.println(URLEncoder.encode(res4,"UTF-8"));



    }



}
