package com.gt.manager.rpc.order;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.order
 * @ClassName IOrderRpcService
 * @Description: 订单dubbo 接口
 * @Author towards
 * @Date 2018/8/1 11:06
 */
public interface IOrderRpcService {
    /**
     * 根据条件查询订单信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getOrderRecordList(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据订单号查询商品信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getProductListByOrderCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据条件导出订单信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage exportOrderList(HttpServletRequest request, JSONObject jsonObject) throws Exception;




}
