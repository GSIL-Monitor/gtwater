package com.gt.manager.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.entity.wtadmin.UserSimpleEntity;
import com.gt.manager.entity.wtadmin.WtTicketSimple;


import java.util.List;

/**
 * @Package com.gt.manager.ticket.service
 * @ClassName IWtTicketService
 * @Description:
 * @Author towards
 * @Date 2018/8/9 19:26
 */
public interface IWtTicketService {

    /**
     * 根据电话查询用户简单信息
     * @param phone
     * @return
     * @throws Exception
     */
    public UserSimpleEntity getUserSimpleByPhone(String phone) throws Exception;
    /**
     * 根据电话查询用户收货信息
     * @param phone
     * @return
     * @throws Exception
     */
    public List<ReceiveAddress> getUserAddressByPhone(String phone) throws Exception;


    /**
     * 根据上级ID查询直属下级分支机构信息
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<OpenBranches> getAllBranchesList(Long parentId) throws Exception;




    /**
     * 根据用户ID查询水票简单信息
     * @param userId
     * @return
     * @throws Exception
     */
    public List<WtTicketSimple> getWtTicketSimpleByUserId(Long userId) throws Exception;


    /**
     *
     * @param receiveAddressId
     * @param branchesId
     * @param skuCode
     * @param num
     * @throws Exception
     */
    public void addTicket(Long receiveAddressId,Long branchesId,String skuCode,Integer num,Long loginId) throws Exception;

    /**
     * 减少水票
     * @param userId
     * @param skuCode
     * @param num
     * @param loginId
     * @throws Exception
     */
    public void deleteTicket(Long userId,String skuCode,Integer num,Long loginId) throws Exception;


    /**
     * 根据条件查询水票相关信息
     * @param userName
     * @param userPhone
     * @param
     * @param qrCode
     * @param minTichetNum
     * @param maxTicketNum
     * @param
     * @return
     */
    public JSONObject getTicketRecordList(Integer pageNo, Integer pageSize, String userName, String userPhone,
                                          String cityId, String qrCode,
                                          Integer minTichetNum, Integer maxTicketNum,
                                          Long fromRechargeTime,Long toRechargeTime) throws Exception;

    /**
     * 根据条件导出水票信息
     * @param userName
     * @param userPhone
     * @param cityId
     * @param qrCode
     * @param minTichetNum
     * @param maxTicketNum
     * @param fromRechargeTime
     * @param toRechargeTime
     * @return
     * @throws Exception
     */
    public JSONObject exportTicketRecordList(String userName, String userPhone,
                                          String cityId, String qrCode,
                                          Integer minTichetNum, Integer maxTicketNum,
                                          Long fromRechargeTime,Long toRechargeTime) throws Exception;


    /**
     * 根据订单编号获取套系信息
     * @param orderCode
     * @return
     * @throws Exception
     */
    public List<WtOrgSetmeal> getSetmealListByOrderCode(String orderCode)throws Exception;

    /**
     * 根据上级区域ID查询所属区域信息
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<SysRegion> getSysRegionByParentId(String parentId)throws Exception;
}
