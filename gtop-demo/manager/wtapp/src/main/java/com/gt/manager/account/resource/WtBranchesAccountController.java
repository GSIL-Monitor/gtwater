package com.gt.manager.account.resource;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.Constant;
import com.gt.manager.account.service.WtBranchesAccountService;
import com.gt.manager.entity.common.ReqData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;

/**
 * 账户钱包入口 controller
 */
@Path(value = "/tixian")
@Api(value = "/WtBranchesAccountController", description = "提现的接口")
@SuppressWarnings("all")
public class WtBranchesAccountController {

    @Autowired
    private WtBranchesAccountService wtBranchesAccountService;


    @POST
    @Path("/tixianAccountMoneyToWeiXin")
    @ApiOperation(value = "/tixianAccountMoneyToWeiXin", notes = "个人账户提现到微信")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public DataMessage tixianAccountMoneyToWeiXin(ReqData reqData){
        try{
            String platform = reqData.getPlatform();
            String requestCode = reqData.getRequestCode();
            String params = reqData.getParams();
            if(StringUtils.isBlank(params)){
                return new DataMessage(DataMessage.RESULT_FAILED, null,"初始参数为空");
            }
            synchronized(String.valueOf(JSONObject.parseObject(params).getLongValue("onlyId")).intern()) {
                DataMessage result = wtBranchesAccountService.tixianAccountMoneyToWeiXin(JSONObject.parseObject(params));
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, null,"系统异常");
        }
    }

    @POST
    @Path("/tixianAccountDetailed")
    @ApiOperation(value = "/tixianAccountDetailed", notes = "提现到微信的明细")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public DataMessage tixianAccountDetailed(ReqData reqData){
        try{
            String platform = reqData.getPlatform();
            String requestCode = reqData.getRequestCode();
            String params = reqData.getParams();
            if(StringUtils.isBlank(params)){
                return new DataMessage(DataMessage.RESULT_FAILED, null,"初始参数为空");
            }
            DataMessage result = wtBranchesAccountService.tixianAccountDetailed(JSONObject.parseObject(params));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getCause().getMessage(),"系统异常");
        }
    }

}
