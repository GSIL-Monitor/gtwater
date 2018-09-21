package com.gt.manager.order.service;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import com.gt.manager.entity.wtadmin.SendRecordEntity;

import java.util.List;

/**
 * @Package com.gt.manager.order.service
 * @ClassName ISendOrderService
 * @Description:
 * @Author towards
 * @Date 2018/8/13 13:31
 */
public interface ISendOrderService {
    /**
     * 根据 条件查询派送单
     *
     * @param sendCode
     * @param userPhone
     * @param fromCreatTime
     * @param toCreatTime
     * @param fromAppoinmentTime
     * @param toAppoinmentTime
     * @param sendOrderStatus
     * @return
     * @throws Exception
     */
    public JSONObject getSendRecordList(Integer pageNo, Integer pageSize, String sendCode, String userPhone,
                                        Long fromCreatTime, Long toCreatTime,
                                        Long fromAppoinmentTime, Long toAppoinmentTime,
                                        Integer sendOrderStatus) throws Exception;

    /**
     * 根据条件导出派送单信息
     *
     * @param sendCode
     * @param userPhone
     * @param formCreatTime
     * @param toCreatTime
     * @param fromAppoinmentTime
     * @param toAppoinmentTime
     * @param sendOrderStatus
     * @return
     * @throws Exception
     */
    public List<SendRecordEntity> exportSendRecordList(String sendCode, String userPhone,
                                                       Long formCreatTime, Long toCreatTime,
                                                       Long fromAppoinmentTime, Long toAppoinmentTime,
                                                       Integer sendOrderStatus) throws Exception;

    /**
     * 根据派送单编号查询商品信息
     *
     * @param sendCode
     * @return
     * @throws Exception
     */
    public List<ProductMsgEntity> getProductListBySendCode(String sendCode) throws Exception;

    /**
     * 根据skuCode和地址查询区域内水站
     *
     * @param skuCode
     * @param address
     * @return
     * @throws Exception
     */
    public List<WtWaterstore> getBranchesListBySkuCodeAddress(Long orgId,Integer sceneId,String skuCode, String address,Long branchesId) throws Exception;

    /**
     * 根据水站名称模糊查询水站列表
     *
     * @param WaterName
     * @return
     * @throws Exception
     */
    public List<WtWaterstore> getBranchesListByWaterName(String WaterName,Long cityBranchesId) throws Exception;

    /**
     * 改派
     *
     * @param sendCode
     * @param newWaterId
     * @throws Exception
     */
    public void changeSendByNewBranchesId(Long sendId,Long branchesId,String sendCode, Long newWaterId,Long loginId,String loginName) throws Exception;

    /**
     * 根据条件查询改派记录（分页）
     *
     * @param sendCode
     * @param waterName
     * @param fromCreatTime
     * @param toCreatTime
     * @param fromAppoinmentTime
     * @param toAppoinmentTime
     * @param sendOrderStatus
     * @return
     * @throws Exception
     */
    public JSONObject getchangeSendReport(Integer pageNo, Integer pageSize,
                                                      String sendCode, String waterName,
                                                      Long fromCreatTime, Long toCreatTime,
                                                      Long fromAppoinmentTime, Long toAppoinmentTime,
                                                      Integer sendOrderStatus) throws Exception;

    /**
     * 查询超时未接单派送单
     * @param duration 时长（分钟）
     * @return
     */
    public List<WtSend> selectSendListWithTimeOut(Integer duration);
}
