package com.gt.manager.rpc.app;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
//import com.gt.manager.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.app
 * @ClassName IProfitRpcService
 * @Description:  商家app分佣服务
 * @Author fengyueli
 * @Date 2018/8/7 20:01
 */
public interface IProfitRpcService {
    /**
     * 根据水站机构平台编号获取，水站本月，本周，今日的派送量，总收益
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getWaterstoreProfitByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据城市机构平台编号获取，城市总本月，本周，今日的派送量，总收益
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getCityProfitByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;
    /**
     * 根据城市机构平台编号获取平台下所有水站的默认当天的，可选择时间段询，每个店铺的流水与分佣总额集合
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getCityProfitDetailByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据水站branchesId下每个派单的销售信息,可按品牌查询、日期查询
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getWaterstoreSaleReport(HttpServletRequest request, JSONObject jsonObject) throws Exception;
}
