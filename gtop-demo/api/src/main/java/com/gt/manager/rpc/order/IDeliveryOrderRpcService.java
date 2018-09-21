package com.gt.manager.rpc.order;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.order
 * @ClassName IDeliveryOrderRpcService
 * @Description: 派送单dubbo接口
 * @Author towards
 * @Date 2018/8/1 11:07
 */
public interface IDeliveryOrderRpcService {

    /**
     * 根据条件查询派送单信息（分页）
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getSendRecordList(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据条件导出派送单信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage exportSendRecordList(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 查询超时未接单派送单
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage selectSendListWithTimeOut(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据派送单号查询商品信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getProductListBySendCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据地址和skuCode查询符合条件水站列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getBranchesListBySkuCodeAddress(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据水站名称模糊查询水站列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getBranchesListByWaterName(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 改派
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage changeSendByNewBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据条件查询改派记录（分页）
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getchangeSendReport(HttpServletRequest request, JSONObject jsonObject) throws Exception;



}
