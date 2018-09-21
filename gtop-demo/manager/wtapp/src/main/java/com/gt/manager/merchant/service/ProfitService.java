package com.gt.manager.merchant.service;


import com.github.pagehelper.PageInfo;
import com.gt.manager.merchant.entity.response.WaterstoreSaleReport;

import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.merchant.service
 * @ClassName WtSendService
 *  商家收益分佣管理服务
 * @author fengyueli
 * @date 2018/8/7 20:13
 */
public interface ProfitService {
    /**
     * 水站老板分佣收益报表，当天，本周，本月的收益与派送量
     * @param branchesId    商家水站branchesId
     * @return
     */
    Map<String,Object> getWaterstoreProfitByBranchesId(Long branchesId);

    /**
     * 城市总分佣收益报表，当天，本周，本月的收益与派送量
     * @param branchesId    城市 branchesId
     * @return
     */
    Map<String,Object> getCityProfitByBranchesId(Long branchesId);
    /**
     * 根据城市机构平台编号获取平台下所有水站的默认当天的，可选择时间段询，每个店铺的流水与分佣总额集合
     * @param branchesId    城市 branchesId,起始结束时间过滤
     * @return
     */
   PageInfo getCityProfitDetailByBranchesId(int pageNumber, int pageSize, Long orgId, Long branchesId,
                                            Long startTime, Long endTime) throws Exception;

    /**
     * 根据水站branchesId下每个派单的销售信息,可按品牌查询、日期查询
     * @param branchesId    水站 branchesId,起始结束时间过滤
     * @return
     */
    PageInfo<WaterstoreSaleReport> getWaterstoreSaleReport(int pageNumber, int pageSize, Long branchesId,
                                                           String brandName, Long startTime, Long endTime) throws Exception;
}
