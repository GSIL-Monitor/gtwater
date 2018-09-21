package com.gt.manager.order.service;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;


import java.util.List;

/**
 * @Package com.gt.manager.order.service.impl
 * @ClassName IOrderService
 * @Description:
 * @Author towards
 * @Date 2018/8/12 21:06
 */
public interface IOrderService {

    /**
     * 根据条件查询订单信息
     * @param orderCode
     * @param userPhone
     * @param fromOrderTime
     * @param toOrderTime
     * @param orderStatus
     * @return
     * @throws Exception
     */
    public JSONObject getOrderRecordList(Integer pageNo, Integer pageSize, String orderCode, String userPhone,
                                         Long fromOrderTime, Long toOrderTime,
                                         Integer orderStatus)throws  Exception;


    /**
     * 根据订单编号获取商品信息
     * @param orderCode
     * @return
     * @throws Exception
     */
    public List<ProductMsgEntity> getProductListByOrderCode(String orderCode) throws  Exception;
}
