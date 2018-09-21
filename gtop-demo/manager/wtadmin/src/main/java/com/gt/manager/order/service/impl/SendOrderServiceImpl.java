package com.gt.manager.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.gtop.service.dubbo.gtpush.IPushMessageDubboAPIService;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.entity.wtadmin.ChangeSendReport;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import com.gt.manager.entity.wtadmin.SendRecordEntity;
import com.gt.manager.order.repository.WtSendDAO;
import com.gt.manager.order.repository.WtSendMesDAO;
import com.gt.manager.order.repository.WtWaterstoreDAO;
import com.gt.manager.order.service.ISendOrderService;
import com.gt.manager.util.numberCreate.NumberCreateUtil;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Package com.gt.manager.order.service.impl
 * @ClassName SendOrderServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/13 13:53
 */

@com.alibaba.dubbo.config.annotation.Service

public class SendOrderServiceImpl implements ISendOrderService {
    private static final Logger log = Logger.getLogger(SendOrderServiceImpl.class);

    @Autowired
    WtSendDAO wtSendDAO;
    @Autowired
    WtSendMesDAO wtSendMesDAO;
    @Reference
    IBranchesDubboService branchesDubboService;
    @Autowired
    WtWaterstoreDAO wtWaterstoreDAO;
    @Reference
    IPushMessageDubboAPIService pushMessageDubboAPIService;

    /**
     * 根据 条件查询派送单
     *
     * @param pageNo
     * @param pageSize
     * @param sendCode
     * @param userPhone
     * @param fromCreatTime
     * @param toCreatTime
     * @param fromAppointmentTime
     * @param toAppointmentTime
     * @param sendOrderStatus
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getSendRecordList(Integer pageNo, Integer pageSize,
                                        String sendCode, String userPhone,
                                        Long fromCreatTime, Long toCreatTime,
                                        Long fromAppointmentTime, Long toAppointmentTime,
                                        Integer sendOrderStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("sendCode", sendCode);
        map.put("userPhone", userPhone);
        map.put("fromCreatTime", fromCreatTime);
        map.put("toCreatTime", toCreatTime);
        map.put("fromAppointmentTime", fromAppointmentTime);
        map.put("toAppointmentTime", toAppointmentTime);
        map.put("sendOrderStatus", sendOrderStatus);
        log.info("map:" + map.toString());

        boolean isPage = false;
        if (pageNo != null && pageSize != null) {
            isPage = true;
        }
        if (isPage) {
            PageHelper.startPage(pageNo, pageSize);
        }
        try {

            List<SendRecordEntity> sendRecordEntities = wtSendDAO.getSendRecordList(map);
            JSONObject json = new JSONObject();
            json.put("sendRecordList", sendRecordEntities);
            if (isPage) {
                PageInfo<SendRecordEntity> pageInfo = new PageInfo<SendRecordEntity>(sendRecordEntities);
                JSONObject page = new JSONObject();
                page.put("pageNo", pageNo);
                page.put("total", pageInfo.getPages());
                page.put("count", pageInfo.getTotal());
                json.put("page", page);
            }

            return json;
        } catch (Exception e) {
            log.error("查询失败", e);
            throw new GtopException("查询失败");
        }
    }

    /**
     * 根据条件导出派送单信息
     *
     * @param sendCode
     * @param userPhone
     * @param fromCreatTime
     * @param toCreatTime
     * @param fromAppointmentTime
     * @param toAppointmentTime
     * @param sendOrderStatus
     * @return
     * @throws Exception
     */
    @Override
    public List<SendRecordEntity> exportSendRecordList(String sendCode, String userPhone, Long fromCreatTime, Long toCreatTime, Long fromAppointmentTime, Long toAppointmentTime, Integer sendOrderStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("sendCode", sendCode);
        map.put("userPhone", userPhone);
        map.put("fromCreatTime", fromCreatTime);
        map.put("toCreatTime", toCreatTime);
        map.put("fromAppointmentTime", fromAppointmentTime);
        map.put("toAppointmentTime", toAppointmentTime);
        map.put("sendOrderStatus", sendOrderStatus);
        log.info("map:" + map.toString());

        return wtSendDAO.exportSendRecordList(map);

    }

    /**
     * 根据派送单编号查询商品信息
     *
     * @param sendCode
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductMsgEntity> getProductListBySendCode(String sendCode) throws Exception {
        if (StringUtils.isEmpty(sendCode)) {
            throw new GtopException("缺少派送参数");
        }
        sendCode=sendCode.trim();
        log.info("sendCode:" + sendCode);


        //从序列化数据获取商品信息
        List<WtSendMes> wtSendMes= wtSendMesDAO.selectBySendCode(sendCode);
        if (CollectionUtils.isEmpty(wtSendMes)){
            throw new GtopException("没有查询到此派送单商品");
        }
        List<ProductMsgEntity> productMsgEntities = new ArrayList<>();

        for (int i = 0; i <wtSendMes.size() ; i++) {
            ProductMsgEntity productMsgEntity= new ProductMsgEntity();
            //获取序列化数据
            JSONObject sequence = JSONObject.parseObject(wtSendMes.get(i).getSequence());
            if (sequence==null){
                productMsgEntity.setCount(wtSendMes.get(i).getNum());
                productMsgEntity.setBrandName(wtSendMes.get(i).getBrandName());
                productMsgEntity.setProductSpec(wtSendMes.get(i).getGoodsSpec());
                productMsgEntity.setProductName(wtSendMes.get(i).getSkuName());
            }else {

                productMsgEntity.setProductSpec(sequence.getString("goodsSpec"));
                productMsgEntity.setProductName(sequence.getString("goodsName"));
                productMsgEntity.setPrice(sequence.getLong("sellPrice"));
                productMsgEntity.setProductPic(sequence.getString("goodsPic"));
                productMsgEntity.setCount(wtSendMes.get(i).getNum());
                productMsgEntity.setBrandName(sequence.getString("brandName"));
            }
            productMsgEntities.add(productMsgEntity);

            
        }

        //List<ProductMsgEntity> productMsgEntities = wtSendMesDAO.getProductListBySendCode(sendCode);
       /* if (CollectionUtils.isEmpty(productMsgEntities)) {
            log.info("返回数据为空");
            throw new GtopException("没有数据");
        }*/
        log.info("msgList:" + productMsgEntities.size());
        return productMsgEntities;
    }

    /**
     * 根据skuCode和地址查询区域内水站
     *
     * @param skuCode
     * @param address
     * @return
     * @throws Exception
     */
    @Override
    public List<WtWaterstore> getBranchesListBySkuCodeAddress(Long orgId, Integer sceneId, String skuCode, String address, Long branchesId) throws Exception {
        //根据地址调用接口获取机构ID
        //根据机构ID和sku查询水站信息
        List<WtWaterstore> wtWaterstores = null;
        List<OpenBranches> branchesList = null;
        try {
            branchesList = branchesDubboService.getListBySceneIdInLocation(orgId, sceneId, address);
            if (CollectionUtils.isNotEmpty(branchesList)) {

                List<Long> longs = new ArrayList<>();
                for (OpenBranches o : branchesList) {
                    //不包括原水站
                    if (!o.getId().equals(branchesId)) {
                        longs.add(o.getId());
                        log.info("开通水站功能机构ID：" + o.getId());
                    }

                }
                wtWaterstores = wtWaterstoreDAO.getBranchesListBySkuCodeBranches(skuCode, longs);

            }
        } catch (Exception e) {
            log.error("没有查询到符合条件水站");

            return null;
        }
        return wtWaterstores;

    }

    /**
     * 根据水站名称模糊查询水站列表
     *
     * @param waterName
     * @return
     * @throws Exception
     */
    @Override
    public List<WtWaterstore> getBranchesListByWaterName(String waterName, Long cityBranchesId) throws Exception {

        if (StringUtils.isEmpty(waterName)) {
            waterName = null;
        } else {
            waterName = waterName.trim();
        }
        log.info("waterName:" + waterName);
        if (cityBranchesId == null) {
            throw new GtopException("城市参数缺失");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("waterName", waterName);
        map.put("cityBranchesId", cityBranchesId);
        return wtWaterstoreDAO.getWaterStoreListByName(map);
    }

    /**
     * 改派
     *
     * @param sendCode
     * @param newWaterId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeSendByNewBranchesId(Long sendId, Long branchesId, String sendCode, Long newWaterId, Long loginId, String loginName) throws Exception {
        if (StringUtils.isEmpty(sendCode.trim())) {
            throw new GtopException("缺少派送参数");
        }
        if (newWaterId == null) {
            throw new GtopException("缺少站点参数");
        }
        if (branchesId == null) {
            throw new GtopException("缺少机构参数");
        }
        if (sendId == null) {
            throw new GtopException("缺少派送单");
        }
        if (loginId == null) {
            throw new GtopException("缺少登录参数");
        }
        sendCode = sendCode.trim();

        //登录用户名
        if (StringUtils.isEmpty(loginName)) {
            throw new GtopException("缺少登录参数");
        }

        Long updateTime = System.currentTimeMillis();
        String newSendCode = NumberCreateUtil.makeOrderNum(3, null);
        WtSend wtSend = wtSendDAO.selectById(sendId);
        if (wtSend == null) {
            log.info("没有查询到此派送单");
            throw new GtopException("查询失败");
        }
        WtSend wtSend1 = new WtSend();
        wtSend1.setAddress(wtSend.getAddress());
        wtSend1.setAppointmentTime(wtSend.getAppointmentTime());
        wtSend1.setArea(wtSend.getArea());
        wtSend1.setAreaId(wtSend.getAreaId());
        wtSend1.setCity(wtSend.getCity());
        wtSend1.setCityId(wtSend.getCityId());
        wtSend1.setContacts(wtSend.getContacts());
        wtSend1.setCreateId(loginId);
        wtSend1.setCreateTime(wtSend.getCreateTime());
        wtSend1.setMoney(wtSend.getMoney());
        wtSend1.setPhone(wtSend.getPhone());
        wtSend1.setProvince(wtSend.getProvince());
        wtSend1.setProvinceId(wtSend.getProvinceId());
        wtSend1.setRemarks(wtSend.getRemarks());
        wtSend1.setSendNo(newSendCode);
        wtSend1.setSendTime(wtSend.getSendTime());
        wtSend1.setSendUser(wtSend1.getSendUser());
        wtSend1.setSigenUser(wtSend.getSigenUser());
        wtSend1.setStatus(wtSend.getStatus());
        wtSend1.setUserId(wtSend.getUserId());
        wtSend1.setWaterstoreId(newWaterId);
        wtSendDAO.insert(wtSend1);

        //更新原配送表
        int status = 6;
        Map<String, Object> map = new HashMap<>();
        map.put("sendCode", sendCode);
        map.put("updateName", loginName);
        map.put("updateTime", updateTime);
        map.put("newSendCode", newSendCode);
        map.put("updateId", loginId);
        map.put("status", status);
        wtSendDAO.changeSend(map);
        //更新配送详情
        Map<String, Object> map1 = new HashMap<>();
        map1.put("oldSendCode", sendCode);
        map1.put("newSendCode", newSendCode);
        int result = wtSendMesDAO.changeSend(map1);
        log.info("result:" + result);
        //发送改派信息
        if (result > 0) {
            JSONObject json = new JSONObject();
            json.put("receiveId", branchesId);//接受的分支机构id
            json.put("pushId", branchesId);//推送的分支机构ID
            json.put("title", "新订单");//消息题目
            json.put("content", "您有新的派送单，请注意查收！");//内容

            json.put("thenType", "1");//0.为定时发送 1.立即发送
            json.put("type", "1");//1:通知消息 2:系统消息
            json.put("sourceName", "开放平台");//   开放平台
            json.put("batchId", UUID.randomUUID().toString());//   批次号(batchId =  = UUID.randomUUID().D().toString())

            DataMessage re;
            try {
                re = pushMessageDubboAPIService.buildPushObjectWithAlias(json.toJSONString());
                log.info("abcd===============" + re.getData());
                log.info("abcd===============" + re.getMessage());
                log.info("abcd===============" + re.getResult());
            } catch (GtopException ge) {
                ge.printStackTrace();
                log.error("发送消息失败", ge);
            }

        }


    }

    /**
     * 根据条件查询改派记录（分页）
     *
     * @param pageNo
     * @param pageSize
     * @param sendCode
     * @param waterName
     * @param fromCreatTime
     * @param toCreatTime
     * @param fromAppointmentTime
     * @param toAppointmentTime
     * @param sendOrderStatus
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getchangeSendReport(Integer pageNo, Integer pageSize,
                                          String sendCode, String waterName,
                                          Long fromCreatTime, Long toCreatTime,
                                          Long fromAppointmentTime, Long toAppointmentTime,
                                          Integer sendOrderStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("sendCode", sendCode);
        map.put("waterName", waterName);
        map.put("fromCreatTime", fromCreatTime);
        map.put("toCreatTime", toCreatTime);
        map.put("fromAppointmentTime", fromAppointmentTime);
        map.put("toAppointmentTime", toAppointmentTime);
        map.put("sendOrderStatus", sendOrderStatus);


        boolean isPage = false;
        if (pageNo != null && pageSize != null) {
            isPage = true;
        }
        if (isPage) {
            PageHelper.startPage(pageNo, pageSize);
        }
        List<ChangeSendReport> changeSendReports = wtSendDAO.getchangeSendReport(map);
        JSONObject json = new JSONObject();
        json.put("changeSendRecordList", changeSendReports);
        if (isPage) {
            PageInfo<ChangeSendReport> pageInfo = new PageInfo<ChangeSendReport>(changeSendReports);
            JSONObject page = new JSONObject();
            page.put("pageNo", pageNo);
            page.put("total", pageInfo.getPages());
            page.put("count", pageInfo.getTotal());
            json.put("page", page);
        }

        return json;

    }

    /**
     * 查询超时未接单派送单
     *
     * @param duration 时长（分钟）
     * @return
     */
    @Override
    public List<WtSend> selectSendListWithTimeOut(Integer duration) {
        //如果输入超时时间为空，则设置为30分钟
        if (duration == null) {
            duration = 30;
        }
        //当前时间戳
        Long newTime = System.currentTimeMillis();
        log.info("newTime:" + newTime);
        Long durationTime = Integer.valueOf(duration * 60 * 1000).longValue();
        log.info("durationTime:" + durationTime);
        Long time = newTime - durationTime;
        log.info("time:" + time);

        return wtSendDAO.selectSendListWithTimeOut(time);
    }
}
