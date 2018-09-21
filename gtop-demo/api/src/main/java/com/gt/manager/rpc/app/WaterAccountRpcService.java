package com.gt.manager.rpc.app;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
//import com.gt.manager.DataMessage;

import javax.servlet.http.HttpServletRequest;

public interface WaterAccountRpcService {

    /**
     * 其他业务站点账户提现到个人账户
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage tixianAccountMoneyToBranches(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 合伙人提现到微信
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage tixianAccountMoneyToWeiXin(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 提现明细
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage tixianAccountDetailed(HttpServletRequest request, JSONObject jsonObject) throws Exception;

}
