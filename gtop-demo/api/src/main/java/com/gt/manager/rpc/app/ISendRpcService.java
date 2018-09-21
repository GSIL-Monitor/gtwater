package com.gt.manager.rpc.app;


import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
//import com.gt.manager.DataMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Package com.gt.manager.rpc.app
 * @ClassName ISendRpcService
 * @Description:  商家app派件管理dubbo服务
 * @Author fengyueli
 * @Date 2018/7/31 13:19
 */
public interface ISendRpcService {
    /**
     * 根据商家平台机构编号branchesId及状态（可空为全部），获取水管家对应个体商家的派送单（状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】）
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getSendByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据派送单编号获取派送单详情
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getSendDetailByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据派送单编号修改派单状态，接单
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage orderReceiving(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据派送单编号修改派单状态，确认送达
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage confirmDelivery(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 派单不同状态的总数量
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage sendStatusNum(HttpServletRequest request, JSONObject jsonObject) throws Exception;
}
