package com.gt.manager.order.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import com.gt.manager.entity.wtadmin.SendRecordEntity;
import com.gt.manager.order.service.ISendOrderService;
import com.gt.manager.rpc.order.IDeliveryOrderRpcService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Package com.gt.manager.order.rpc
 * @ClassName DeliveryOrderRpcServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/13 13:15
 */
@Service
public class DeliveryOrderRpcServiceImpl implements IDeliveryOrderRpcService {
    private static final Logger log = Logger.getLogger(DeliveryOrderRpcServiceImpl.class);

    @Autowired
    ISendOrderService sendOrderService;

    /**
     * 根据条件查询派送单信息（分页）
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception Integer pageNo, Integer pageSize,
     *                   String sendCode, String userPhone,
     *                   Long fromCreatTime, Long toCreatTime,
     *                   Long fromAppointmentTime, Long toAppointmentTime,
     *                   Integer sendOrderStatus
     */
    @Override
    public DataMessage getSendRecordList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer pageNo = jsonObject.getInteger("pageNo");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String sendCode = jsonObject.getString("sendCode");
        String userPhone = jsonObject.getString("userPhone");
        Long fromCreatTime = jsonObject.getLong("fromCreatTime");
        Long toCreatTime = jsonObject.getLong("toCreatTime");
        Long fromAppointmentTime = jsonObject.getLong("fromAppointmentTime");
        Long toAppointmentTime = jsonObject.getLong("toAppointmentTime");
        if (fromCreatTime != null && toCreatTime != null && fromCreatTime > toCreatTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        if (fromAppointmentTime != null && toAppointmentTime != null && fromAppointmentTime > toAppointmentTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        Integer sendOrderStatus = jsonObject.getInteger("sendOrderStatus");
        JSONObject json = sendOrderService.getSendRecordList(pageNo, pageSize, sendCode, userPhone, fromCreatTime, toCreatTime, fromAppointmentTime, toAppointmentTime, sendOrderStatus);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }

    /**
     * 根据条件导出派送单信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception String sendCode, String userPhone, Long fromCreatTime, Long toCreatTime, Long fromAppointmentTime, Long toAppointmentTime, Integer sendOrderStatus
     */
    @Override
    public DataMessage exportSendRecordList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String sendCode = jsonObject.getString("sendCode");
        String userPhone = jsonObject.getString("userPhone");
        Long fromCreatTime = jsonObject.getLong("fromCreatTime");
        Long toCreatTime = jsonObject.getLong("toCreatTime");
        Long fromAppointmentTime = jsonObject.getLong("fromAppointmentTime");
        Long toAppointmentTime = jsonObject.getLong("toAppointmentTime");
        if (fromCreatTime != null && toCreatTime != null && fromCreatTime > toCreatTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        if (fromAppointmentTime != null && toAppointmentTime != null && fromAppointmentTime > toAppointmentTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        Integer sendOrderStatus = jsonObject.getInteger("sendOrderStatus");
        List<SendRecordEntity> sendRecordEntities = sendOrderService.exportSendRecordList(sendCode, userPhone, fromCreatTime, toCreatTime, fromAppointmentTime, toAppointmentTime, sendOrderStatus);
        return new DataMessage(DataMessage.RESULT_SUCESS, sendRecordEntities, "请求成功");
    }

    /**
     * 查询超时未接单派送单
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage selectSendListWithTimeOut(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer duration = jsonObject.getInteger("duration");
        if (duration == null) {
            duration = 30;
        }
        log.info("duration:" + duration);
        List<WtSend> wtSends = sendOrderService.selectSendListWithTimeOut(duration);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtSends, "请求成功");
    }

    /**
     * 根据派送单号查询商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getProductListBySendCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String sendCode = jsonObject.getString("sendCode");
        if (StringUtils.isEmpty(sendCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("sendCode:" + sendCode);
        List<ProductMsgEntity> productMsgEntities = sendOrderService.getProductListBySendCode(sendCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, productMsgEntities, "请求成功");
    }

    /**
     * 根据地址和skuCode查询符合条件水站列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception Long orgId, Integer sceneId, String skuCode, String address
     */
    @Override
    public DataMessage getBranchesListBySkuCodeAddress(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //组织ID
        Long orgId = jsonObject.getLong("orgId");
        if (orgId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("orgId:" + orgId);

        //场景ID  3--水业务
        Integer sceneId = jsonObject.getInteger("sceneId");
        if (sceneId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("场景ID：" + sceneId);
        //水站机构ID
        Long branchesId= jsonObject.getLong("branchesId");
        if (branchesId==null){
            return  new DataMessage(DataMessage.RESULT_FAILED,null,"缺少机构参数");
        }

        //商品skuCode
        String skuCode = jsonObject.getString("skuCode");
       /* if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        skuCode = skuCode.trim();
        log.info("skuCode:" + skuCode);*/

        //用户收货地址
        String address = jsonObject.getString("address");
        if (StringUtils.isEmpty(address)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        address = address.trim();
        log.info("用户地址：" + address);
        List<WtWaterstore> wtWaterstores = sendOrderService.getBranchesListBySkuCodeAddress(orgId, sceneId, skuCode, address,branchesId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtWaterstores, "请求成功");
    }

    /**
     * 根据水站名称模糊查询水站列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getBranchesListByWaterName(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String waterName = jsonObject.getString("waterName");
        log.info("waterName:" + waterName);
        if (StringUtils.isEmpty(waterName)){
            log.info("waterName is null ");
            waterName=null;
        }else {
            waterName=waterName.trim();
        }

        //水站所属城市机构ID
        Long cityBranchesId= jsonObject.getLong("cityBranchesId");
        if (cityBranchesId==null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("所属城市ID:"+cityBranchesId);

        List<WtWaterstore> wtWaterstores = sendOrderService.getBranchesListByWaterName(waterName,cityBranchesId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtWaterstores, "请求成功");
    }

    /**
     * 改派
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception String sendCode, Long newWaterId, Long loginId
     */
    @Override
    @Transactional
    public DataMessage changeSendByNewBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //派送单ID
        Long sendId = jsonObject.getLong("sendId");
        if (sendId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //派送单编号
        String sendCode = jsonObject.getString("sendCode");
        if (StringUtils.isEmpty(sendCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //新水站ID
        Long newWaterId = jsonObject.getLong("newWaterId");
        if (newWaterId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Long branchesId = jsonObject.getLong("branchesId");
        if (branchesId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //操作者id
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //操作者名称
        String loginName= jsonObject.getString("loginName");
        if (StringUtils.isEmpty(loginName)){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        loginName=loginName.trim();
        log.info("用户名称");
        sendOrderService.changeSendByNewBranchesId(sendId, branchesId, sendCode, newWaterId, loginId,loginName);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 根据条件查询改派记录（分页）
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception Integer pageNo, Integer pageSize,
     *                   String sendCode, String waterName,
     *                   Long fromCreatTime, Long toCreatTime,
     *                   Long fromAppointmentTime, Long toAppointmentTime,
     *                   Integer sendOrderStatus
     */
    @Override
    public DataMessage getchangeSendReport(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer pageNo = jsonObject.getInteger("pageNo");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String sendCode = jsonObject.getString("sendCode");
        String waterName = jsonObject.getString("waterName");
        Long fromCreatTime = jsonObject.getLong("fromCreatTime");
        Long toCreatTime = jsonObject.getLong("toCreatTime");
        Long fromAppointmentTime = jsonObject.getLong("fromAppointmentTime");
        Long toAppointmentTime = jsonObject.getLong("toAppointmentTime");
        if (fromCreatTime != null && toCreatTime != null && fromCreatTime > toCreatTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        if (fromAppointmentTime != null && toAppointmentTime != null && fromAppointmentTime > toAppointmentTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        Integer sendOrderStatus = jsonObject.getInteger("sendOrderStatus");
        JSONObject json = sendOrderService.getchangeSendReport(pageNo, pageSize, sendCode, waterName, fromCreatTime, toCreatTime, fromAppointmentTime, toAppointmentTime, sendOrderStatus);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }
}
