package com.gt.manager.merchant.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
//import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.sys.alliance.OpenAllianceCommissionEdit;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.gtop.service.dubbo.grocery.IFingertipGroceryStoreDubboAPIService;
import com.gt.manager.account.service.WtBranchesAccountService;
import com.gt.manager.entity.wtOrderMes.WtOrderMes;
import com.gt.manager.entity.wtProfit.WtProfit;
import com.gt.manager.entity.wtProfitCity.WtProfitCity;
import com.gt.manager.entity.wtProfitPartner.WtProfitPartner;
import com.gt.manager.entity.wtProfitSystem.WtProfitSystem;
import com.gt.manager.entity.wtProfitWaterstore.WtProfitWaterstore;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.merchant.entity.ProfitMesEntity;
import com.gt.manager.merchant.entity.response.WtSendResponse;
import com.gt.manager.merchant.repository.*;
import com.gt.manager.merchant.service.WtSendService;
import com.gt.util.exception.GtopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 派单管理
 *
 * @author fengyueli
 * @date 2018/8/3 14:43
 */
@Service
public class WtSendServiceImpl implements WtSendService {

    @Autowired
    private WtSendDAO wtSendDAO;
    @Autowired
    private WtWaterstoreDAO wtWaterstoreDAO;

    @Autowired
    private WtSendMesDAO wtSendMesDAO;

    @Autowired
    private WtUserTicketDAO wtUserTicketDAO;

    @Autowired
    private WtTicketLogDAO wtTicketLogDAO;

    @Autowired
    private WtProfitDAO wtProfitDAO;

    @Autowired
    private WtProfitCityDAO wtProfitCityDAO;
    @Autowired
    private WtProfitPartnerDAO wtProfitPartnerDAO;
    @Autowired
    private WtProfitSystemDAO wtProfitSystemDAO;
    @Autowired
    private WtProfitWaterstoreDAO wtProfitWaterstoreDAO;

    @Autowired
    private WtOrderMesDAO wtOrderMesDAO;
    @Autowired
    private WtBranchesAccountService wtBranchesAccountService;
    @Reference
    private IFingertipGroceryStoreDubboAPIService iFingertipGroceryStoreDubboAPIService;
    @Reference
    private IBranchesDubboService iBranchesDubboService;

    private static final Logger log = LoggerFactory.getLogger(WtSendServiceImpl.class);

//    @Autowired
//    private IBranchesDubboService iBranchesDubboService;

    /**
     * 分页查询，商家所有派单（branchesId状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】）
     *
     * @param pageNo
     * @param pageSize
     * @param branchesId     商家branchesId,status
     * @return
     */
    @Override
    public PageInfo<WtSendResponse> getSendPage(Integer pageNo, Integer pageSize, Long branchesId, Integer status) {

        WtWaterstore wtWaterstore = wtWaterstoreDAO.selectByBranchesId(branchesId);
        if(wtWaterstore!=null){

            Long waterstoreId =wtWaterstore.getId();
            WtSend wtSend = new WtSend();
            if (status != null) {
                wtSend.setStatus(status);
            }
            wtSend.setWaterstoreId(waterstoreId);

            PageHelper.startPage(pageNo, pageSize);
            List<WtSendResponse> list = wtSendDAO.selectListAndUrgeTimes(wtSend);
            PageInfo<WtSendResponse> pageInfo=new PageInfo<WtSendResponse>(list);
            return pageInfo;
        }

        return null;
    }

    /**
     * 派送单详情
     *
     * @param sendCode 派单编号send_code
     * @return
     */
    @Override
    public WtSendResponse getSendDetailByBranchesId(String sendCode) {

        WtSendResponse wtSendResponse = wtSendDAO.selectBySendCode(sendCode);
        List<WtSendMes> wtSendMes = wtSendMesDAO.selectBySendCode(sendCode);
        wtSendResponse.setWtSendMesList(wtSendMes);
        return wtSendResponse;
    }

    /**
     * 接单
     *
     * @param sendCode 派单编号send_code
     * @return
     */
    @Override
    public void orderReceiving(String sendCode) {
        //设置状态为1，已确认派单（待收货）
        wtSendDAO.updateOrderReceivingStatus(sendCode);
    }

    /**
     * 确认收货
     *
     * @param sendCode 派单编号send_code派单编号send_code,各个详情的编号及对应的实际送达数量
     *                 {
     *                 "sendNo": 1,
     *                 "detail": {
     *                 "123": 2,
     *                 "345": 4
     *                 }
     *                 }
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void confirmDelivery(Long orgId, Long branchesId, String sendCode, Long loginId, Map<String, Object> realMes) throws Exception {
        //当前派件信息
        WtSendResponse wtSendResponse = wtSendDAO.selectBySendCode(sendCode);
        if(wtSendResponse.getStatus().intValue()==5){
            return;

        }
        //修改派单状态 5 已送达，实际派送时间，签收人
        Map<String, Object> map = new HashMap<>();
        map.put("status", 5);
        long currentTime = new Date().getTime();
        map.put("sendTime", currentTime);
        map.put("sendNo", sendCode);
        map.put("updateTime",currentTime);
        map.put("updateId",loginId);
        wtSendDAO.updateConfirmStatus(map);

        //当前水站信息
        WtWaterstore wtWaterstore = wtWaterstoreDAO.selectById(wtSendResponse.getWaterstoreId());
        //根据派送编号获取派单详情编号
        List<WtSendMes> wtSendMes = wtSendMesDAO.selectBySendCode(sendCode);




        //派单总量+=num
        //派单总价+=num*单价
        Integer totalCount = 0;
        Long totalAccount = 0l;
        //平台分佣记录
        List<WtProfitSystem> wtProfitSystemList = new ArrayList<>();
        //水站分佣
        List<WtProfitWaterstore> wtProfitWaterstoreArrayList = new ArrayList<>();
        //城市分佣
        List<WtProfitCity> wtProfitCityArrayList = new ArrayList<>();
        //合伙人分佣，查看是否有合伙人
        List<WtProfitPartner> wtProfitPartnerArrayList = new ArrayList<>();

        Long systemProfitmoneyTotal=0l;
        Long cityProfitmoneyTotal=0l;
        Long partnerProfitmoneyTotal=0l;
        Long waterstoreProfitmoneyTotal=0l;
        Long cityId = null;
        Long cityBindUserId = null;
        Integer systemsScale = 0;//调平台接口
        Integer cityScale = 0;//调平台接口
        Integer waterstoreScale = 0;//调平台接口
        Integer partnerScale = 0;//调平台接口



            //根据组织D，水站 平台机构编码调接口得到城市机构id
            OpenBranches filialeByBranches = iBranchesDubboService.getFilialeByBranches(orgId,branchesId);
            cityId = filialeByBranches.getId();
            cityBindUserId = null;
            if(filialeByBranches.getBindUser()!=null){

                cityBindUserId=filialeByBranches.getBindUser().getId();
            }else{
                log.error("城市绑定人不能为空,城市编码是："+cityId);
            }

            //佣金接口比例获取

            List<OpenAllianceCommissionEdit> fingertipGroceryStoreComissionEditInfo = iFingertipGroceryStoreDubboAPIService.getFingertipGroceryStoreComissionEditInfo(3, branchesId);
            if(fingertipGroceryStoreComissionEditInfo!=null){
                for (OpenAllianceCommissionEdit openAllianceCommissionEdit : fingertipGroceryStoreComissionEditInfo) {
                    if(openAllianceCommissionEdit.getType()==1){//平台
                        systemsScale=openAllianceCommissionEdit.getPushMoney().intValue();
                    }else if(openAllianceCommissionEdit.getType()==2){//城市
                        cityScale= openAllianceCommissionEdit.getPushMoney().intValue();
                    }else if(openAllianceCommissionEdit.getType()==4){//销售
                        partnerScale= openAllianceCommissionEdit.getPushMoney().intValue();
                    }else if(openAllianceCommissionEdit.getType()==6){//水站
                        waterstoreScale= openAllianceCommissionEdit.getPushMoney().intValue();
                    }
                }

            }



//        Long cityId = 1l;//调接口
//        Long cityBindUserId =1288989l;
//        //佣金接口比例获取
//        Integer systemsScale = 500;//调平台接口
//        Integer cityScale = 300;//调平台接口
//        Integer waterstoreScale = 9000;//调平台接口
//        Integer partnerScale = 200;//调平台接口



        //分佣总表
        WtProfit wtProfit = new WtProfit();
        wtProfit.setSendNo(sendCode);
        wtProfit.setUserId(wtSendResponse.getUserId());
        wtProfit.setWaterstoreId(wtSendResponse.getWaterstoreId());

        //根据用户扫码记录表，扫码表得到合伙人id
        Long partnerId = wtProfitDAO.selectByUserId(wtSendResponse.getUserId());
        wtProfit.setPartnerId(partnerId);
        wtProfit.setSendNum(totalCount);
        wtProfit.setProfitMoney(totalAccount);
        wtProfit.setSendTime(currentTime);
        wtProfit.setCreateTime(currentTime);
        wtProfit.setPaymentTime(wtSendResponse.getCreateTime());
        wtProfitDAO.insert(wtProfit);
        Long profitId = wtProfit.getId();//获取分佣id

        for (WtSendMes wtSendMe : wtSendMes) {
            //记录每个派单明细的商品总价---------------后加
//            Long sendMesMoney=0l;
            Integer oldNum = wtSendMe.getNum();
            Integer realNum = oldNum;
            if (realMes != null) {
                Integer reciveNum = Integer.valueOf(realMes.get(wtSendMe.getSendMesCode()).toString());
                if(reciveNum<0){
                    reciveNum=0;

                }
                if(reciveNum>oldNum){
                    throw new GtopException("实际送达数量不能大于预定数量");
                }
                if (reciveNum != null) {
                    realNum = reciveNum;
                }
            }
            Integer backNum = 0;//需退票数
            if (oldNum != realNum) {
                backNum = oldNum - realNum;
                //不一致，循环判断更新派单详情的实际数量
                WtSendMes temp = new WtSendMes();
                temp.setId(wtSendMe.getId());
                temp.setDeliveryNum(realNum);
                wtSendMesDAO.update(temp);
            }
            //1水票|| 2水(现金) || 3混合支付//4赠品
            if (wtSendMe.getType() == 4){
                log.info("此为赠品不计佣金,不支持退货");

            }else if (wtSendMe.getType() == 2) {//纯水现金
                String orderMesCode = wtSendMe.getOrderMesCode();
                WtOrderMes wtOrderMes = wtOrderMesDAO.selectByOrderMesCode(orderMesCode);
                if(wtOrderMes.getpType()==2){//商品类型 是桶水,计算佣金，若是套餐赠品，不计算佣金，不计入派单总价
                    //得到分佣记录所需数所的实体类
                    ProfitMesEntity profitMesEntity = getProfitMesEntity(loginId,cityId, cityBindUserId,currentTime, wtSendResponse, wtWaterstore, systemsScale, cityScale, waterstoreScale, partnerScale, profitId, wtOrderMes, realNum);
                    //分佣记录数据集合封装
                    profitRecord(wtProfitSystemList, wtProfitCityArrayList, wtProfitPartnerArrayList, wtProfitWaterstoreArrayList, profitMesEntity);


                    //各平台佣金计算
                    systemProfitmoneyTotal+=profitMesEntity.getSystemProfitMoney();
                    cityProfitmoneyTotal+=profitMesEntity.getCityProfitMoney();
                    partnerProfitmoneyTotal+=profitMesEntity.getPartnerProfitMoney();
                    waterstoreProfitmoneyTotal+=profitMesEntity.getWaterstoreProfitMoney();
                    //派单总量+=num
                    totalCount += realNum;
                    //派单总价+=num*单价
                    totalAccount += realNum * wtOrderMes.getPrice();
//                sendMesMoney+=realNum* wtOrderMes.getPrice();

                    if (oldNum != realNum) {
                        log.error("余下现金退还请线下联系统管理员");
                    }

                }

            } else{
                //水票记录与退还
                //水票佣金计算

                //根据派件详情编号获取水票消费记录按订单日期倒序
                WtTicketLog wtTicketLog = new WtTicketLog();
                wtTicketLog.setSendMesCode(wtSendMe.getSendMesCode());
                List<WtTicketLog> wtTicketLogs = wtTicketLogDAO.selectListOrderByOrderTimeDesc(wtTicketLog);//倒序

                //水票消费的记录及佣金
                for (WtTicketLog ticketLog : wtTicketLogs) {
                    //

                    if (ticketLog.getNum() < backNum) {
                        //水票记录表加一条,用户水票余量+getNum
                        ticketLog.setOperation(1);//设置类型为增 【1增、-1减】
                        ticketLog.setType(2);//设置类型为 订单/派送单？？？1/2
                        ticketLog.setCreateTime(currentTime);
                        ticketLog.setLogTime(currentTime);
                        ticketLog.setId(null);
                        wtTicketLogDAO.insert(ticketLog);

                        Map<String, Object> condition = new HashMap<>();
                        condition.put("num", ticketLog.getNum());
//                        condition.put("orderMesCode", ticketLog.getOrderMesCode());
                        condition.put("ticketId", ticketLog.getTicketId());
                        condition.put("updateTime", currentTime);
                        wtUserTicketDAO.updateSurplusNum(condition);//在原有余量上加上num

                        backNum -= ticketLog.getNum();
                    } else {
                        //如果backNum不等于0，记录水票消费日志
                        //执行一次水票记录，消费记录+，操作数量backNum,用户水票表+backNum
                        Integer oldTicketLog=ticketLog.getNum();
                        if (backNum != 0) {
                            ticketLog.setOperation(1);//设置类型为增 【1增、-1减】
                            ticketLog.setType(2);//设置类型为 订单/派送单1/2
                            ticketLog.setCreateTime(currentTime);
                            ticketLog.setLogTime(currentTime);
                            ticketLog.setId(null);
                            ticketLog.setNum(backNum);
                            wtTicketLogDAO.insert(ticketLog);

                            Map<String, Object> condition = new HashMap<>();
                            condition.put("num", backNum);
                            condition.put("ticketId", ticketLog.getTicketId());
                            condition.put("updateTime", currentTime);
                            wtUserTicketDAO.updateSurplusNum(condition);//在原有余量上加上backnum

                        }
                        //一个订单明细的数量num=数量-backNum
                        Integer num = oldTicketLog - backNum;
                        //计算佣金，一个订单明细的是num*单价*比例( 不同角色，不同比例，从平台获取)

                        if (num != 0) {
                            Long unitPrice = ticketLog.getTicketPrice();


                            Long partnerId2 = wtProfitPartnerDAO.selectPartnerIdByOrderMesCode(ticketLog.getOrderMesCode());
                            Long cityProfitMoney = calculateCommission(num,unitPrice,cityScale);
                            Long waterstoreProfitMoney = calculateCommission(num,unitPrice,waterstoreScale);
                            Long partnerProfitMoney =0l;
                            if(partnerId2!=null){
                                partnerProfitMoney = calculateCommission(num,unitPrice,partnerScale);

                            }
                            Long systemsProfitMoney = num*unitPrice-cityProfitMoney-waterstoreProfitMoney-partnerProfitMoney;

                           //记录佣金总额
                            systemProfitmoneyTotal+=systemsProfitMoney;
                            cityProfitmoneyTotal+=cityProfitMoney;
                            partnerProfitmoneyTotal+=partnerProfitMoney;
                            waterstoreProfitmoneyTotal+=waterstoreProfitMoney;

                            //记录分佣明细
                            ProfitMesEntity profitMesEntity = new ProfitMesEntity();

                            //记录从tickLog中获取
                            //记录佣金

                            profitMesEntity.setProfitId(profitId);
                            profitMesEntity.setOrderCode(ticketLog.getOrderMesCode());
                            profitMesEntity.setOrderNum(num);//明细的实际数量
                            profitMesEntity.setUnitPrice(unitPrice);
                            profitMesEntity.setSkuCode(ticketLog.getSkuCode());
                            profitMesEntity.setSystemProfitMoney(systemsProfitMoney);
                            //如果没有合伙人，平台比列+=合伙人比例
                            if(partnerId2==null){
                                profitMesEntity.setSystemProportion(systemsScale+partnerScale);
                            }else{
                                profitMesEntity.setSystemProportion(systemsScale);
                            }

                            profitMesEntity.setOrderTime(wtSendResponse.getCreateTime());
                            profitMesEntity.setCreateTime(currentTime);
                            profitMesEntity.setCreateId(loginId);
                            profitMesEntity.setDelStatus(1);
                            profitMesEntity.setCityId(cityId);
                            profitMesEntity.setCityUserId(cityBindUserId);
                            profitMesEntity.setCityProfitMoney(cityProfitMoney);
                            profitMesEntity.setCityProportion(cityScale);
                            profitMesEntity.setPartnerProfitMoney(partnerProfitMoney);
                            profitMesEntity.setPartnerProportion(partnerScale);
                            profitMesEntity.setPartnerId(partnerId2);
                            profitMesEntity.setWaterstoreId(wtWaterstore.getId());
                            profitMesEntity.setBranchesId(wtWaterstore.getBranchesId());
                            profitMesEntity.setWaterName(wtWaterstore.getWaterName());
                            profitMesEntity.setWaterstoreNo(wtWaterstore.getWaterstoreNo());
                            profitMesEntity.setWaterstoreProfitMoney(waterstoreProfitMoney);
                            profitMesEntity.setWaterstoreroportion(waterstoreScale);

                            profitRecord(wtProfitSystemList, wtProfitCityArrayList, wtProfitPartnerArrayList, wtProfitWaterstoreArrayList, profitMesEntity);
                        }
                        //派单明细总价
//                        sendMesMoney+=num * ticketLog.getTicketPrice();
                        //派单总量+=num
                        totalCount += num;
                        //派单总价+=num*单价
                        totalAccount += num * ticketLog.getTicketPrice();
                        backNum = 0;//订单消耗数大于等于退还数时，操作完成水票消费记录与水票表后，置为0


                    }
                }
                if (wtSendMe.getType() == 3) {//混合购买

                    //区别于纯水购买，realNum,处理为减去退还数后的数量(如水票退完后仍然不够的情况)
                    String orderMesCode = wtSendMe.getOrderMesCode();
                    WtOrderMes wtOrderMes = wtOrderMesDAO.selectByOrderMesCode(orderMesCode);
                    realNum = wtOrderMes.getNum() - backNum;

                    if (realNum != 0) {
                        //得到分佣记录所需数所的实体类
                        ProfitMesEntity profitMesEntity = getProfitMesEntity(loginId,cityId, cityBindUserId,currentTime, wtSendResponse, wtWaterstore, systemsScale, cityScale, waterstoreScale, partnerScale, profitId, wtOrderMes, realNum);
                        //分佣记录数据集合封装
                        profitRecord(wtProfitSystemList, wtProfitCityArrayList, wtProfitPartnerArrayList, wtProfitWaterstoreArrayList, profitMesEntity);

                        systemProfitmoneyTotal+=profitMesEntity.getSystemProfitMoney();
                        cityProfitmoneyTotal+=profitMesEntity.getCityProfitMoney();
                        partnerProfitmoneyTotal+=profitMesEntity.getPartnerProfitMoney();
                        waterstoreProfitmoneyTotal+=profitMesEntity.getWaterstoreProfitMoney();

                    }


                    //派单总量+=num
                    totalCount += realNum;
                    //派单总价+=num*单价
                    totalAccount += realNum * wtOrderMes.getPrice();
                    if (wtOrderMes.getNum() != realNum) {
                        log.warn("余下现金退还请线下联系统管理员");
                        //异常不能抛，线下处理
                    }
                }
            }
        }

        //更新派单总金额
        if (wtSendResponse.getMoney() != totalAccount) {
            WtSend wtSend = new WtSend();
            wtSend.setMoney(totalAccount);
            wtSend.setUpdateTime(currentTime);
            wtSend.setUpdateId(loginId);
            wtSend.setId(wtSendResponse.getId());
            wtSendDAO.update(wtSend);
        }
        //更新总分佣的数量与金额

        WtProfit wtProfitTemp = new WtProfit();
        wtProfitTemp.setId(profitId);
        wtProfitTemp.setSendNum(totalCount);
        wtProfitTemp.setProfitMoney(totalAccount);//为派送水的总金额
        wtProfitDAO.update(wtProfitTemp);
        //更新各个分佣表的分佣明细
        if(wtProfitSystemList!=null && wtProfitSystemList.size()>0){
            wtProfitSystemDAO.batchInsert(wtProfitSystemList);
        }

        if(wtProfitCityArrayList!=null && wtProfitCityArrayList.size()>0){
            wtProfitCityDAO.batchInsert(wtProfitCityArrayList);
        }

        if (wtProfitPartnerArrayList != null && wtProfitPartnerArrayList.size() > 0) {
            wtProfitPartnerDAO.batchInsert(wtProfitPartnerArrayList);
        }
        if(wtProfitWaterstoreArrayList!=null && wtProfitWaterstoreArrayList.size()>0){
            wtProfitWaterstoreDAO.batchInsert(wtProfitWaterstoreArrayList);

        }


        //更新钱包与钱包记录
        List<Map<String,Object>> list=new ArrayList<>();
        if(systemProfitmoneyTotal!=0){
            Map<String,Object> system=new HashMap<>();
            system.put("onlyId",orgId);
            system.put("userType",1);
            system.put("money",systemProfitmoneyTotal);
            system.put("type",1);
            system.put("userId",loginId);
            list.add(system);

        }

        //城市记录
        if(cityProfitmoneyTotal!=0){
            Map<String,Object> city=new HashMap<>();
            city.put("onlyId",cityId);
            city.put("userType",2);
            city.put("money",cityProfitmoneyTotal);
            city.put("userId",loginId);
            city.put("type",1);
            list.add(city);

        }

        //水站
        if(waterstoreProfitmoneyTotal!=0){
            Map<String,Object> waterstore=new HashMap<>();
            waterstore.put("onlyId",branchesId);
            waterstore.put("userType",3);
            waterstore.put("money",waterstoreProfitmoneyTotal);
            waterstore.put("type",1);
            waterstore.put("userId",loginId);
            list.add(waterstore);

        }


        //合伙人
        if(partnerProfitmoneyTotal!=0){
            Map<String,Object> partner=new HashMap<>();
            partner.put("onlyId",partnerId);
            partner.put("userType",4);
            partner.put("money",partnerProfitmoneyTotal);
            partner.put("type",1);
            partner.put("userId",loginId);
            list.add(partner);
        }


        if(list.size()>0){
            String s = JSON.toJSONString(list);
            //分佣后更新钱包
            String s1 = wtBranchesAccountService.addWtBranchesAccountData(s);
            System.out.println(s1);

        }



    }

    /**
     * 返回派单列表的各状态的数量
     *
     * @return
     */
    @Override
    public Map<String, Integer> sendStatusNum(Long branchesId) {
        WtWaterstore wtWaterstore = wtWaterstoreDAO.selectByBranchesId(branchesId);
        Map<String,Integer> map=new HashMap<>();
        if(wtWaterstore!=null){
            Long wtWaterstoreId = wtWaterstore.getId();


            Integer newOrderCount=wtSendDAO.selectCountByStatus(0,wtWaterstoreId);
            map.put("newOrderCount",newOrderCount);
            Integer haveOrderCount=wtSendDAO.selectCountByStatus(1,wtWaterstoreId);
            map.put("haveOrderCount",haveOrderCount);
            Integer deliveryOrderCount=wtSendDAO.selectCountByStatus(5,wtWaterstoreId);
            map.put("deliveryOrderCount",deliveryOrderCount);
            Integer cancleOrderCount=wtSendDAO.selectCountByStatus(-1,wtWaterstoreId);
            map.put("cancleOrderCount",cancleOrderCount);
            return map;

        }else{
            map.put("newOrderCount",0);
            map.put("haveOrderCount",0);
            map.put("deliveryOrderCount",0);
            map.put("cancleOrderCount",0);
            return map;

        }

    }

    //纯水支付使用
    public ProfitMesEntity getProfitMesEntity(Long loginId,Long cityId,Long cityBindUserId, long currentTime, WtSendResponse wtSendResponse, WtWaterstore wtWaterstore, Integer systemsScale, Integer cityScale, Integer waterstoreScale, Integer partnerScale, Long profitId, WtOrderMes wtOrderMes, Integer realNum) {

        //记录佣金

        Long price = wtOrderMes.getPrice();
        //获取该订单的合伙人id
        Long partnerId2 = wtProfitPartnerDAO.selectPartnerIdByOrderMesCode(wtOrderMes.getOrderMesNo());

        Long cityProfitMoney =calculateCommission(realNum,price,cityScale);
        Long waterstoreProfitMoney = calculateCommission(realNum,price,waterstoreScale);
        Long partnerProfitMoney=0l;
        if(partnerId2!=null){
            partnerProfitMoney=calculateCommission(realNum,price,partnerScale);

        }else{
            systemsScale+=partnerScale;

        }
        Long systemsProfitMoney =realNum*price-cityProfitMoney-partnerProfitMoney-waterstoreProfitMoney;




        //计录各佣金

        ProfitMesEntity profitMesEntity = new ProfitMesEntity();
        profitMesEntity.setProfitId(profitId);
        profitMesEntity.setOrderCode(wtOrderMes.getOrderMesNo());
        profitMesEntity.setOrderNum(realNum);
        profitMesEntity.setUnitPrice(price);
        profitMesEntity.setSkuCode(wtOrderMes.getSkuCode());
        profitMesEntity.setSystemProfitMoney(systemsProfitMoney);
        profitMesEntity.setSystemProportion(systemsScale);
        profitMesEntity.setOrderTime(wtSendResponse.getCreateTime());
        profitMesEntity.setCreateTime(currentTime);
        profitMesEntity.setCreateId(loginId);
        profitMesEntity.setDelStatus(1);
        profitMesEntity.setCityId(cityId);
        profitMesEntity.setCityUserId(cityBindUserId);
        profitMesEntity.setCityProfitMoney(cityProfitMoney);
        profitMesEntity.setCityProportion(cityScale);
        profitMesEntity.setPartnerProfitMoney(partnerProfitMoney);
        profitMesEntity.setPartnerProportion(partnerScale);
        profitMesEntity.setPartnerId(partnerId2);
        profitMesEntity.setWaterstoreId(wtWaterstore.getId());
        profitMesEntity.setBranchesId(wtWaterstore.getBranchesId());
        profitMesEntity.setWaterName(wtWaterstore.getWaterName());
        profitMesEntity.setWaterstoreNo(wtWaterstore.getWaterstoreNo());
        profitMesEntity.setWaterstoreProfitMoney(waterstoreProfitMoney);
        profitMesEntity.setWaterstoreroportion(waterstoreScale);
        return profitMesEntity;
    }

    /**
     * 计算佣金
     * @param realNum
     * @param price
     * @param scale
     * @return
     */
    private Long calculateCommission(Integer realNum, Long price, Integer scale) {
        BigDecimal realNumB=new BigDecimal(realNum);
        BigDecimal priceB=new BigDecimal(price);
        BigDecimal scaleB=new BigDecimal(scale);
        BigDecimal divisor =new BigDecimal(10000);
        BigDecimal profitMoney=realNumB.multiply(priceB).multiply(scaleB).divide(divisor);
        return profitMoney.setScale(0,BigDecimal.ROUND_DOWN ).longValue();
    }

    private void profitRecord(List<WtProfitSystem> wtProfitSystemList, List<WtProfitCity> wtProfitCityArrayList, List<WtProfitPartner> wtProfitPartnerArrayList,
                              List<WtProfitWaterstore> wtProfitWaterstoreArrayList, ProfitMesEntity profitMesEntity) {


        //共用属性
        Long profitId = profitMesEntity.getProfitId();
        String orderCode = profitMesEntity.getOrderCode();
        Integer orderNum = profitMesEntity.getOrderNum();
        Long unitPrice = profitMesEntity.getUnitPrice();
        String skuCode = profitMesEntity.getSkuCode();
        Long orderTime = profitMesEntity.getOrderTime();
        Long createTime = profitMesEntity.getCreateTime();
        Integer delStatus = profitMesEntity.getDelStatus();


        //平台分佣记录
        if(profitMesEntity.getSystemProfitMoney()>0){

            WtProfitSystem wtProfitSystem = new WtProfitSystem();
            wtProfitSystem.setProfitId(profitId);
            wtProfitSystem.setOrderCode(orderCode);
            wtProfitSystem.setOrderNum(orderNum);
            wtProfitSystem.setUnitPrice(unitPrice);
            wtProfitSystem.setSkuCode(skuCode);
            wtProfitSystem.setProfitMoney(profitMesEntity.getSystemProfitMoney());
            wtProfitSystem.setOrderTime(orderTime);
            wtProfitSystem.setProportion(profitMesEntity.getSystemProportion());
            wtProfitSystem.setCreateTime(createTime);
            wtProfitSystem.setCreateId(profitMesEntity.getCreateId());
            wtProfitSystem.setDelStatus(delStatus);
            wtProfitSystemList.add(wtProfitSystem);
        }


        //水站分佣
        if(profitMesEntity.getWaterstoreProfitMoney()>0){
            WtProfitWaterstore wtProfitWaterstore = new WtProfitWaterstore();
            wtProfitWaterstore.setProfitId(profitId);
            wtProfitWaterstore.setOrderCode(orderCode);
            wtProfitWaterstore.setOrderNum(orderNum);
            wtProfitWaterstore.setUnitPrice(unitPrice);
            wtProfitWaterstore.setWaterstoreId(profitMesEntity.getWaterstoreId());
            wtProfitWaterstore.setBranchesId(profitMesEntity.getBranchesId());
            wtProfitWaterstore.setWaterName(profitMesEntity.getWaterName());
            wtProfitWaterstore.setWaterstoreNo(profitMesEntity.getWaterstoreNo());
            wtProfitWaterstore.setSkuCode(skuCode);
            wtProfitWaterstore.setProportion(profitMesEntity.getWaterstoreroportion());
            wtProfitWaterstore.setProfitMoney(profitMesEntity.getWaterstoreProfitMoney());
            wtProfitWaterstore.setCreateTime(createTime);
            wtProfitWaterstore.setCreateId(profitMesEntity.getCreateId());
            wtProfitWaterstore.setDelStatus(delStatus);
            wtProfitWaterstoreArrayList.add(wtProfitWaterstore);
        }



        //城市分佣
        if(profitMesEntity.getCityProfitMoney()>0){
            WtProfitCity wtProfitCity = new WtProfitCity();
            wtProfitCity.setBranchesId(profitMesEntity.getCityId());
            wtProfitCity.setUserId(profitMesEntity.getCityUserId());
            wtProfitCity.setProfitId(profitId);
            wtProfitCity.setOrderCode(orderCode);
            wtProfitCity.setOrderNum(orderNum);
            wtProfitCity.setUnitPrice(unitPrice);
            wtProfitCity.setSkuCode(skuCode);
            wtProfitCity.setProfitMoney(profitMesEntity.getCityProfitMoney());
            wtProfitCity.setOrderTime(orderTime);
            wtProfitCity.setProportion(profitMesEntity.getCityProportion());
            wtProfitCity.setCreateTime(createTime);
            wtProfitCity.setCreateId(profitMesEntity.getCreateId());
            wtProfitCity.setDelStatus(delStatus);
            wtProfitCityArrayList.add(wtProfitCity);

        }


        //合伙人分佣，查看是否有合伙人

        if (profitMesEntity.getPartnerId() != null) {
            WtProfitPartner wtProfitPartner = new WtProfitPartner();
            wtProfitPartner.setProfitId(profitId);
            wtProfitPartner.setOrderCode(orderCode);
            wtProfitPartner.setOrderNum(orderNum);
            wtProfitPartner.setUnitPrice(unitPrice);
            wtProfitPartner.setSkuCode(skuCode);
            wtProfitPartner.setProfitMoney(profitMesEntity.getPartnerProfitMoney());
            wtProfitPartner.setOrderTime(orderTime);
            wtProfitPartner.setProportion(profitMesEntity.getPartnerProportion());
           // wtProfitPartner.setBranchesId(profitMesEntity.getBranchesId());//水站
            wtProfitPartner.setBranchesId(profitMesEntity.getCityId());//城市
            wtProfitPartner.setPartnerId(profitMesEntity.getPartnerId());
            wtProfitPartner.setCreateTime(createTime);
            wtProfitPartner.setCreateId(profitMesEntity.getCreateId());
            wtProfitPartner.setDelStatus(delStatus);
            wtProfitPartnerArrayList.add(wtProfitPartner);

        }
    }


    public static void main(String[] args) {
        String json = "{\n" +
                "    \"sendNo\": 1,\n" +
                "    \"detail\": {\n" +
                "    \"123\": 2,\n" +
                "    \"345\": 4\n" +
                "    }\n" +
                "    }";

        Object parse = JSON.parse(json);
        System.out.println(parse.toString());

        WtSendServiceImpl wtSendService = new WtSendServiceImpl();
        Map<String, Integer> map = new HashMap<>();
        map.put("56", 2);
        //wtSendService.confirmDelivery(11l, 33l, "55", map);
        BigDecimal b = new BigDecimal(9999999);
        BigDecimal b2 = new BigDecimal(2370);
        BigDecimal b3=new BigDecimal(10000);
        BigDecimal divide = b.multiply(b2).divide(b3);

        System.out.println(divide);
        WtSendServiceImpl s=new WtSendServiceImpl();
        divide=divide.setScale(0,BigDecimal.ROUND_DOWN );
        System.out.println(s.calculateCommission(9999999,1l,2370));


        //更新钱包与钱包记录
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> system=new HashMap<>();
        system.put("onlyId",1);
        system.put("userType",1);
        system.put("money",1);
        system.put("type",1);

        //城市记录
        Map<String,Object> city=new HashMap<>();
        city.put("onlyId",1);
        city.put("userType",2);
        city.put("money",1);
        city.put("type",1);
        //水站
        Map<String,Object> waterstore=new HashMap<>();
        waterstore.put("onlyId",1);
        waterstore.put("userType",3);
        waterstore.put("money",1);
        waterstore.put("type",1);

        //合伙人
        if(0!=0){
            Map<String,Object> partner=new HashMap<>();
            partner.put("onlyId",1);
            partner.put("userType",4);
            partner.put("money",1);
            partner.put("type",1);
            list.add(partner);
        }

        list.add(system);
        list.add(city);
        list.add(waterstore);
        String s1 = JSON.toJSONString(list);
        System.out.println(s1);






    }
}
