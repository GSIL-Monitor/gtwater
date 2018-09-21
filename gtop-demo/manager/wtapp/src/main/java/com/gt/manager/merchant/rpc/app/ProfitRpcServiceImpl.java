package com.gt.manager.merchant.rpc.app;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.merchant.entity.response.WaterstoreSaleReport;
import com.gt.manager.merchant.service.ProfitService;
import com.gt.manager.rpc.app.IProfitRpcService;
import com.gt.util.exception.GtopException;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author fengyueli
 * @date 2018/8/7 20:08
 */
@Service
public class ProfitRpcServiceImpl implements IProfitRpcService {
    @Autowired
    private ProfitService profitService;
    /**
     * 根据水站机构平台编号获取，水站本月，本周，今日的派送量，总收益
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWaterstoreProfitByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long branchesId=jsonObject.getLong("branchesId");
            if(branchesId==null){
                throw new GtopException("水站机构编码id不能为空");
            }


            Map<String, Object> waterstoreProfitByBranchesId = profitService.getWaterstoreProfitByBranchesId(branchesId);


            return new DataMessage(DataMessage.RESULT_SUCESS, waterstoreProfitByBranchesId, "查询成功");


    }

    /**
     * 根据城市机构平台编号获取，城市总本月，本周，今日的派送量，总收益
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getCityProfitByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long branchesId=jsonObject.getLong("branchesId");
            if(branchesId==null){
                throw new GtopException("城市机构编码id不能为空");
            }


            Map<String, Object> cityProfitByBranchesId = profitService.getCityProfitByBranchesId(branchesId);


            return new DataMessage(DataMessage.RESULT_SUCESS, cityProfitByBranchesId, "查询成功");


    }

    /**
     * 根据城市机构平台编号获取平台下所有水站的默认当天的，可选择时间段询，每个店铺的流水与分佣总额集合
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getCityProfitDetailByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long branchesId=jsonObject.getLong("branchesId");
            Long orgId=jsonObject.getLong("orgId");
            Long startTime=jsonObject.getLong("startTime");
            Long endTime=jsonObject.getLong("endTime");
            Integer pageNo=jsonObject.getInteger("pageNo");
            Integer pageSize=jsonObject.getInteger("pageSize");
            if(branchesId==null){
                throw new GtopException("城市机构编码id不能为空");
            }
            if(orgId==null){
                throw new GtopException("组织id不能为空");
            }
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            if(startTime!=null && endTime!=null && endTime<startTime){
                throw new GtopException("开始时间不能大于结束时间");
            }


            PageInfo cityProfitDetailByBranchesId = profitService.getCityProfitDetailByBranchesId(pageNo, pageSize, orgId, branchesId, startTime, endTime);


            return new DataMessage(DataMessage.RESULT_SUCESS, cityProfitDetailByBranchesId, "查询成功");


    }

    /**
     * 根据水站branchesId下每个派单的销售信息,可按品牌查询、日期查询
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWaterstoreSaleReport(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long branchesId=jsonObject.getLong("branchesId");
            Long startTime=jsonObject.getLong("startTime");
            Long endTime=jsonObject.getLong("endTime");
            Integer pageNo=jsonObject.getInteger("pageNo");
            Integer pageSize=jsonObject.getInteger("pageSize");
            String brandName=jsonObject.getString("key");
            if(branchesId==null){
                throw new GtopException("城市机构编码id不能为空");
            }
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            if(endTime!=null && startTime!=null &&endTime<startTime){
                throw new GtopException("开始时间不能大于结束时间");
            }


            PageInfo<WaterstoreSaleReport> waterstoreSaleReport = profitService.getWaterstoreSaleReport(pageNo, pageSize, branchesId, brandName, startTime, endTime);

            return new DataMessage(DataMessage.RESULT_SUCESS, waterstoreSaleReport, "查询成功");

    }
}
