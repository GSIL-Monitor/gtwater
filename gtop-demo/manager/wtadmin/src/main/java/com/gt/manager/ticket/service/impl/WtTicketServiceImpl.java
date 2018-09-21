package com.gt.manager.ticket.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.entity.user.User;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtOrderMes.WtOrderMes;
import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.entity.wtSku.WtSku;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtadmin.TicketRecordEntity;
import com.gt.manager.entity.wtadmin.UserSimpleEntity;
import com.gt.manager.entity.wtadmin.WtTicketSimple;
import com.gt.manager.order.repository.WtOrderDAO;
import com.gt.manager.order.repository.WtOrderMesDAO;
import com.gt.manager.product.repository.WtSkuDAO;
import com.gt.manager.setmeal.repository.WtOrgSetmealDAO;
import com.gt.manager.ticket.repository.*;
import com.gt.manager.ticket.service.IWtTicketService;
import com.gt.manager.util.numberCreate.NumberCreateUtil;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.ticket.service.impl
 * @ClassName WtTicketService
 * @Description:
 * @Author towards
 * @Date 2018/8/9 19:26
 */
@Service
public class WtTicketServiceImpl implements IWtTicketService {
    private static final Logger log = Logger.getLogger(WtTicketServiceImpl.class);

    @Autowired
    WtUserTicketDAO wtUserTicketDAO;
    @Autowired
    ReceiveAddressDAO receiveAddressDAO;
    @Autowired
    WtOrderDAO wtOrderDAO;
    @Autowired
    WtOrderMesDAO wtOrderMesDAO;
    @Autowired
    WtSkuDAO wtSkuDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    WtTicketLogDAO wtTicketLogDAO;

    @Autowired
    SysRegionDAO sysRegionDAO;
    @Autowired
    WtOrgSetmealDAO wtOrgSetmealDAO;
    @Reference
    IBranchesDubboService branchesDubboService;

    /**
     * 根据电话查询用户简单信息
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    public UserSimpleEntity getUserSimpleByPhone(String phone) throws Exception {
        if (StringUtils.isEmpty(phone)) {
            throw new GtopException("缺少电话信息");
        }
        log.info("电话：" + phone);
        phone = phone.trim();
        return wtUserTicketDAO.getUserSimpleByPhone(phone);

    }

    /**
     * 根据电话查询用户收货信息
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    public List<ReceiveAddress> getUserAddressByPhone(String phone) throws Exception {
        if (StringUtils.isEmpty(phone)) {
            throw new GtopException("缺少联系方式");
        }

        log.info("getUserAddressByPhone--电话：" + phone);
        phone = phone.trim();
        return receiveAddressDAO.selectListByPhone(phone);
    }

    /**
     * 根据上级ID查询直属下级分支机构信息
     *
     * @param orgId
     * @return
     * @throws Exception
     */
    @Override

    public List<OpenBranches> getAllBranchesList(Long orgId) throws Exception {
        if (orgId == null) {
            throw new GtopException("缺少上级信息");
        }
        log.info("组织ID：" + orgId);
        List<OpenBranches> branchesList = branchesDubboService.getFilialeByOrg(orgId);
        if (CollectionUtils.isEmpty(branchesList)) {
            log.info("此组织下没有机构");
            return null;
        }
        log.info("branches.size:" + branchesList.size());
        return branchesList;
    }

    /**
     * 根据用户ID查询所拥有水票信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtTicketSimple> getWtTicketSimpleByUserId(Long userId) throws Exception {
        if (userId == null) {
            throw new GtopException("缺少用户参数");
        }
        List<WtTicketSimple> wtTicketSimples = wtUserTicketDAO.getWtTicketSimpleByuserId(userId);
        if (CollectionUtils.isEmpty(wtTicketSimples)) {
            log.info("此用户没有水票信息");
            return null;
        }
        log.info("数据量：" + wtTicketSimples.size());
        return wtTicketSimples;
    }

    /**
     * 增加水票
     *
     * @param receiveAddressId
     * @param branchesId
     * @param skuCode
     * @param num
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTicket(Long receiveAddressId, Long branchesId, String skuCode, Integer num, Long loginId) throws Exception {

        //生成订单编号
        //根据用户ID获取联系方式
        //生成订单
        //------------------------------

        Long createTime = System.currentTimeMillis();
        //用户联系方式
        ReceiveAddress receiveAddress = receiveAddressDAO.selectById(receiveAddressId);
        if (receiveAddress == null) {
            throw new GtopException("没有这个地址");
        }

        //生成订单号
        String orderCode = NumberCreateUtil.makeOrderNum(1, null);
        WtOrder wtOrder = new WtOrder();
        wtOrder.setAddress(receiveAddress.getAddress());
        wtOrder.setOrderNo(orderCode);
        wtOrder.setUserId(receiveAddress.getUserid());
        wtOrder.setCreateId(loginId);
        wtOrder.setCreateTime(createTime);
        wtOrder.setContacts(receiveAddress.getName());
        wtOrder.setPhone(receiveAddress.getPhone());

        wtOrder.setOrderTime(createTime);
        wtOrder.setPaymentMoney(0L);
        wtOrder.setPaymentType(1);
        //'订单状态【1订单确认（代付款）、3完成、-1取消】
        wtOrder.setOrderState(3);
        wtOrder.setCity(receiveAddress.getCityName());
        wtOrder.setCityId(receiveAddress.getCityId().toString());
        wtOrderDAO.insert(wtOrder);
        Long wtOrderId = wtOrder.getId();
        log.info("orderId:" + wtOrderId);


        /**
         * 2,生成订单详情
         * 根据sku查询sku信息（sku_name）
         * 根据订单信息生成订单详情
         */
        //生成订单详情编号
        String orderMesCode = NumberCreateUtil.makeOrderNum(2, orderCode);
        WtSku wtSku = wtSkuDAO.getWtSkuBySkuCodeBranchesId(skuCode, branchesId);
        if (wtSku == null) {
            throw new GtopException("此区域没有这个商品");
        }
        WtOrderMes wtOrderMes = new WtOrderMes();
        wtOrderMes.setCreateId(loginId);
        wtOrderMes.setCreateTime(createTime);
        wtOrderMes.setNum(num);
        wtOrderMes.setOrderMesNo(orderMesCode);
        wtOrderMes.setOrderNo(orderCode);
        wtOrderMes.setPrice(wtSku.getSellPrice());
        wtOrderMes.setSkuName(wtSku.getSkuName());
        wtOrderMes.setSkuCode(skuCode);
        //pType商品类型1水票|| 2桶水
        wtOrderMes.setpType(1);
        wtOrderMes.setTotalPrice(0L);
        wtOrderMesDAO.insert(wtOrderMes);
        Long orderMesId = wtOrderMes.getId();
        log.info("orderMesId:" + orderMesId);


        //-----------------------------------
        /**
         * 3,生成水票单
         *
         */
        log.info("skuCode:" + skuCode);
        WtUserTicket wtUserTicket = new WtUserTicket();
        wtUserTicket.setUserId(receiveAddress.getUserid());
        wtUserTicket.setCreateId(loginId);
        wtUserTicket.setCreateTime(createTime);
        wtUserTicket.setNum(num);
        wtUserTicket.setSurplusNum(num);
        wtUserTicket.setOrderCode(orderCode);
        wtUserTicket.setOrderTime(createTime);
        wtUserTicket.setSkuCode(skuCode);
        wtUserTicket.setTicketPrice(wtSku.getSellPrice());
        log.info("branchesId:" + branchesId);
        wtUserTicket.setBranchesId(branchesId);
        wtUserTicketDAO.insert(wtUserTicket);
        Long ticketId = wtUserTicket.getId();
        log.info("ticketId:" + ticketId);
        //----------------
        /**
         * 4,生成水票水票log
         */
        User user = userDAO.selectById(receiveAddress.getUserid());
        if (user == null) {
            throw new GtopException("没有这个用户");
        }
        WtTicketLog wtTicketLog = new WtTicketLog();
        wtTicketLog.setTicketId(ticketId);
        wtTicketLog.setAddress(receiveAddress.getAddress());
        wtTicketLog.setCreateId(loginId);
        wtTicketLog.setCreateTime(createTime);
        wtTicketLog.setNum(num);
        wtTicketLog.setOrderMesCode(orderMesCode);
        wtTicketLog.setSkuCode(skuCode);
        wtTicketLog.setSkuName(wtSku.getSkuName());
        //增/减 【1增、-1减】
        wtTicketLog.setOperation(1);
        //订单1/派送单2
        wtTicketLog.setType(1);
        wtTicketLog.setUserId(receiveAddress.getUserid());
        wtTicketLog.setUserName(user.getUsername());
        wtTicketLogDAO.insert(wtTicketLog);
        log.info("管理员：" + loginId + "为用户名称：" + user.getUsername() + "，用户ID：" + user.getId()
                + "增加了“" + num + "”张水票");


    }

    /**
     * 减少水票
     *
     * @param userId
     * @param skuCode
     * @param num
     * @param loginId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTicket(Long userId, String skuCode, Integer num, Long loginId) throws Exception {
        //按照水票创建顺序从余额里扣减水票
        //添加水票日志记录

        //获取登录用户
        if (loginId == null) {
            log.info("没有登录");
            throw new GtopException("没有登录");
        }

        //获取变更数量
        if (num == null) {
            log.info("没有输入数量");
            throw new GtopException("没有输入数量");
        }

        //获取用户信息
        User user = userDAO.selectById(userId);
        if (user == null) {
            log.info("没有这个用户");
            throw new GtopException("没有这个用户");
        }

        //获取商品信息
        List<WtSku> wtSkus = wtSkuDAO.getWtSkuBySkuCode(skuCode);
        if (CollectionUtils.isEmpty(wtSkus)) {
            log.info("没有这个商品");
            throw new GtopException("没有这个商品");
        }
        WtSku wtSku = wtSkus.get(0);

        List<WtUserTicket> wtUserTickets = wtUserTicketDAO.getWtUserTicketListByUserIdSkuCode(userId, skuCode);
        if (CollectionUtils.isEmpty(wtUserTickets)) {
            throw new GtopException("没有可用水票");
        }
        int userTicketNum = 0;
        for (int i = 0; i < wtUserTickets.size(); i++) {
            userTicketNum += wtUserTickets.get(i).getSurplusNum();
        }
        log.info("用户" + user.getUsername() + "原有商品" + skuCode + "水票" + userTicketNum + "张");
        if (num > userTicketNum) {
            log.info("输入数量大于原有此商品水票数量");
            throw new GtopException("输入数量大于原有此商品水票数量");
        }

        //更新时间
        Long updateTime = System.currentTimeMillis();
        Integer temp;
        for (int i = 0; i < wtUserTickets.size(); i++) {
            temp = wtUserTickets.get(i).getSurplusNum() - num;
            if (temp >= 0) {
                //更新余量
                wtUserTicketDAO.updateTicketSurplusNum(temp, updateTime, wtUserTickets.get(i).getId());
                //增加水票日志
                WtTicketLog wtTicketLog = new WtTicketLog();
                wtTicketLog.setSkuName(wtSku.getSkuName());
                wtTicketLog.setUserName(user.getUsername());
                wtTicketLog.setUserId(userId);
                wtTicketLog.setSkuCode(skuCode);
                wtTicketLog.setTicketId(wtUserTickets.get(i).getId());
                wtTicketLog.setNum(num);
                wtTicketLog.setCreateTime(updateTime);
                wtTicketLog.setCreateId(loginId);
                //增/减 【1增、-1减】
                wtTicketLog.setOperation(-1);
                wtTicketLogDAO.insert(wtTicketLog);
                break;
            } else {
                wtUserTicketDAO.updateTicketSurplusNum(0, updateTime, wtUserTickets.get(i).getId());
                WtTicketLog wtTicketLog = new WtTicketLog();
                wtTicketLog.setSkuName(wtSku.getSkuName());
                wtTicketLog.setUserName(user.getUsername());
                wtTicketLog.setUserId(userId);
                wtTicketLog.setSkuCode(skuCode);
                wtTicketLog.setTicketId(wtUserTickets.get(i).getId());
                wtTicketLog.setNum(num);
                wtTicketLog.setCreateTime(updateTime);
                wtTicketLog.setCreateId(loginId);
                //增/减 【1增、-1减】
                wtTicketLog.setOperation(-1);
                wtTicketLogDAO.insert(wtTicketLog);
                num = -temp;
                //增加水票日志
            }
        }
        log.info("管理员：" + loginId + "为用户名称：" + user.getUsername() + "，用户ID：" + user.getId()
                + "减少了“" + num + "”张水票");

    }

    /**
     * 根据条件查询水票相关信息
     *
     * @param userName
     * @param userPhone
     * @param cityId
     * @param qrCode
     * @param minTicketNum
     * @param maxTicketNum
     * @param fromRechargeTime
     * @param toRechargeTime
     * @return
     */
    @Override
    public JSONObject getTicketRecordList(Integer pageNo, Integer pageSize, String userName, String userPhone,
                                          String cityId, String qrCode,
                                          Integer minTicketNum, Integer maxTicketNum,
                                          Long fromRechargeTime, Long toRechargeTime) throws Exception {
        //验证时间
        if (fromRechargeTime != null && toRechargeTime != null && fromRechargeTime > toRechargeTime) {
            log.info("开始时间大于结束时间");
            log.info("开始时间：" + fromRechargeTime);
            log.info("结束时间：" + toRechargeTime);
            throw new GtopException("开始时间大于结束时间");
        }

        log.info("最小水票数："+minTicketNum);
        log.info("最大水票数："+maxTicketNum);
        if(minTicketNum!=null){

            log.info("最小票数类型："+minTicketNum.getClass());
        }
        if (minTicketNum != null && maxTicketNum != null && minTicketNum > maxTicketNum) {
            log.info("最小水票数：" + minTicketNum);
            log.info("最大水票数：" + maxTicketNum);
            log.info("最小水票数大于最大水票数");
            throw new GtopException("剩余水票数输入有误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPhone", userPhone);
        map.put("cityId", cityId);
        map.put("qrCode", qrCode);
        map.put("minTicketNum", minTicketNum);
        map.put("maxTicketNum", maxTicketNum);
        map.put("fromRechargeTime", fromRechargeTime);
        map.put("toRechargeTime", toRechargeTime);
        log.info("参数列表：" + map.toString());

        boolean isPage = false;
        if (pageNo != null && pageSize != null) {
            isPage = true;
        }
        if (isPage) {
            PageHelper.startPage(pageNo, pageSize);
        }
        List<TicketRecordEntity> ticketRecordEntities = wtUserTicketDAO.getTicketRecordList(map);
        JSONObject json = new JSONObject();
        json.put("ticketRecordList", ticketRecordEntities);
        if (isPage) {
            PageInfo<TicketRecordEntity> pageInfo = new PageInfo<TicketRecordEntity>(ticketRecordEntities);
            JSONObject page = new JSONObject();
            page.put("pageNo", pageNo);
            page.put("total", pageInfo.getPages());
            page.put("count", pageInfo.getTotal());
            json.put("page", page);
        }

        return json;
    }

    /**
     * 根据条件导出水票信息
     *
     * @param userName
     * @param userPhone
     * @param cityId
     * @param qrCode
     * @param minTicketNum
     * @param maxTicketNum
     * @param fromRechargeTime
     * @param toRechargeTime
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject exportTicketRecordList(String userName, String userPhone, String cityId, String qrCode, Integer minTicketNum, Integer maxTicketNum, Long fromRechargeTime, Long toRechargeTime) throws Exception {
        //验证时间
        if (fromRechargeTime != null && toRechargeTime != null && fromRechargeTime > toRechargeTime) {
            log.info("开始时间大于结束时间");
            log.info("开始时间：" + fromRechargeTime);
            log.info("结束时间：" + toRechargeTime);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPhone", userPhone);
        map.put("cityId", cityId);
        map.put("qrCode", qrCode);
        map.put("minTicketNum", minTicketNum);
        map.put("maxTicketNum", maxTicketNum);
        map.put("fromRechargeTime", fromRechargeTime);
        map.put("toRechargeTime", toRechargeTime);
        List<TicketRecordEntity> ticketRecordEntities = wtUserTicketDAO.exportTicketRecordList(map);
        JSONObject json = new JSONObject();
        json.put("ticketRecordList", ticketRecordEntities);
        return json;
    }


    /**
     * 根据订单编号获取套系信息
     *
     * @param orderCode
     * @return
     * @throws Exception
     */
    @Override
    public List<WtOrgSetmeal> getSetmealListByOrderCode(String orderCode) throws Exception {
        if (StringUtils.isEmpty(orderCode)) {
            throw new GtopException("缺少订单参数");
        }
        return wtOrgSetmealDAO.getSetmealListByOrderCode(orderCode);
    }

    /**
     * 根据上级区域ID查询所属区域信息
     *
     * @param parentId
     * @return
     * @throws Exception
     */
    @Override
    public List<SysRegion> getSysRegionByParentId(String parentId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        return sysRegionDAO.selectListByParentId(map);
    }


}
