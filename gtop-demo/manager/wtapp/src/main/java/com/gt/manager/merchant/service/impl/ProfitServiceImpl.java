package com.gt.manager.merchant.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.account.repository.WtBranchesAccountDAO;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.merchant.entity.response.WaterstoreSaleReport;
import com.gt.manager.merchant.repository.WtProfitDAO;
import com.gt.manager.merchant.repository.WtWaterstoreDAO;
import com.gt.manager.merchant.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分佣收益
 * @author fengyueli
 * @date 2018/8/8 9:56
 */
@Service
public class ProfitServiceImpl implements ProfitService {

    @Autowired
    private WtProfitDAO wtProfitDAO;
    @Reference
    private IBranchesDubboService iBranchesDubboService;
    @Autowired
    private WtWaterstoreDAO wtWaterstoreDAO;
    @Autowired
    private WtBranchesAccountDAO wtBranchesAccountDAO;
    /**
     * 水站老板分佣收益报表，当天，本周，本月的收益与派送量
     *
     * @param branchesId 商家水站branchesId
     * @return
     */
    @Override
    public Map<String, Object> getWaterstoreProfitByBranchesId(Long branchesId) {
        Map<String, Object> profit=new HashMap<>();
        //调接口，查看是否是水站-------------

            Map<String, Object> dayProfit= wtProfitDAO.getWaterstoreProfitByDay(branchesId);
            Map<String, Object> weekProfit= wtProfitDAO.getWaterstoreProfitByWeek(branchesId);
            Map<String, Object> monthProfit= wtProfitDAO.getWaterstoreProfitByMonth(branchesId);
            Long money=wtBranchesAccountDAO.getAccountByBranchesId(branchesId,3);//用户类型[1平台、2城市机构、3水站、4合伙人]
            profit.put("day",dayProfit);
            profit.put("week",weekProfit);
            profit.put("month",monthProfit);
            if(money==null){
                money=0l;

            }
            profit.put("money",money);



        return profit;
    }
//    private Map<String,Object> getProfitEmpty(){
//        Map<String, Object> profit=new HashMap<>();
//
//        Map<String, Object> dayProfit=getProfitOneEmpty();
//
//        Map<String, Object> weekProfit=getProfitOneEmpty();
//
//        Map<String, Object> monthProfit=getProfitOneEmpty();
//        Long money=0l;
//        profit.put("day",dayProfit);
//        profit.put("week",weekProfit);
//        profit.put("month",monthProfit);
//        profit.put("money",money);
//        return profit;
//    }

//    private Map<String,Object> getProfitOneEmpty(){
//        Map<String, Object> profit=new HashMap<>();
//        profit.put("num",0);
//        profit.put("profit",0);
//        return profit;
//    }

    /**
     * 城市总分佣收益报表，当天，本周，本月的收益与派送量
     *
     * @param branchesId 城市 branchesId
     * @return
     */
    @Override
    public Map<String, Object> getCityProfitByBranchesId(Long branchesId) {
        Map<String, Object> dayProfit= wtProfitDAO.getCityProfitByDay(branchesId);
        Map<String, Object> weekProfit= wtProfitDAO.getCityProfitByWeek(branchesId);
        Map<String, Object> monthProfit= wtProfitDAO.getCityProfitByMonth(branchesId);


        Long money=wtBranchesAccountDAO.getAccountByBranchesId(branchesId,2);//用户类型[1平台、2城市机构、3水站、4合伙人]
        Map<String, Object> profit=new HashMap<>();
        profit.put("day",dayProfit);
        profit.put("week",weekProfit);
        profit.put("month",monthProfit);
        if(money==null){
            money=0l;

        }
        profit.put("money",money);
        return profit;
    }

    /**
     * 根据城市机构平台编号获取平台下所有水站的默认当天的，可选择时间段询，每个店铺的流水与分佣总额集合
     *
     * @param branchesId 城市 branchesId,起始结束时间
     * @return
     */
    @Override
    public  PageInfo getCityProfitDetailByBranchesId(int pageNumber, int pageSize, Long orgId,Long branchesId,
                                                                     Long startTime,Long endTime) throws Exception {
        //调接口根据城市id得下其一面的子水站 的id
        List<Long> ids=new ArrayList<>();
        List<Long> waterstroeIds=new ArrayList<>();

        List<OpenBranches> childrenListByParentHaveScene = iBranchesDubboService.getChildrenListByParentHaveScene(orgId, branchesId, 3l);//水业务场景3
//        List<OpenBranches> childrenListByParentHaveScene =new ArrayList<>();


        if(childrenListByParentHaveScene!=null){
            for (OpenBranches openBranches : childrenListByParentHaveScene) {
                if(!branchesId.equals(openBranches.getId())){

                    ids.add(openBranches.getId());
                }

            }
        }

        if(ids.size()>0){
            waterstroeIds=wtWaterstoreDAO.selectIdsByBranchesIds(ids);
        }



        PageHelper.startPage(pageNumber, pageSize);
        List<Map<String,Object>> list=new ArrayList<>();
        //test----------------------
//        waterstroeIds.add(1l);
//        waterstroeIds.add(8l);
        if(waterstroeIds.size()>0){
           list = wtProfitDAO.getCityProfitDetailByBranchesId(waterstroeIds,startTime,endTime);

        }

        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据水站branchesId下每个派单的销售信息,可按品牌查询、日期查询
     *
     * @param pageNumber
     * @param pageSize
     * @param branchesId 水站 branchesId,起始结束时间过滤
     * @param brandName
     * @param startTime
     * @param endTime    @return
     */
    @Override
    public  PageInfo<WaterstoreSaleReport> getWaterstoreSaleReport(int pageNumber, int pageSize, Long branchesId, String brandName,
                                                              Long startTime, Long endTime) throws Exception {
        WtWaterstore wtWaterstore = wtWaterstoreDAO.selectByBranchesId(branchesId);
        if(wtWaterstore!=null){
            Long waterstoreId =wtWaterstore.getId();
            PageHelper.startPage(pageNumber, pageSize);
            List<WaterstoreSaleReport> list=wtProfitDAO.getWaterstoreSaleReport(waterstoreId,brandName,startTime,endTime );
            PageInfo<WaterstoreSaleReport> pageInfo=new PageInfo<>(list);
            return pageInfo;

        }
        return null;


    }


}
