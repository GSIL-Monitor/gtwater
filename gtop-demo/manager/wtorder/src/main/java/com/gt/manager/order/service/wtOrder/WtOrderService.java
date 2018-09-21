package com.gt.manager.order.service.wtOrder;


import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.wtOrder.WtOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * @author why
 */
public interface WtOrderService {



   /**
	* 查询所有订单
	*/
   List<Map<String,Object>> queryList(HashMap <String, Object> map);
    /**
     * 订单详情
     */
   List<Map<String,Object>>  queryOrderDetails(HashMap <String, Object> map);

    /**
     * 添加订单 与 订单详情
     */
    Map<String,Object> insert(JSONObject json) throws Exception;

    /**
     * 添加用户水票和 水票消费记录表
     */
    String insertUserTicket(JSONObject json) throws Exception;
    /**
     * 支付水票或钱款
     */
    Map<String,Object> getPayment(JSONObject json) throws Exception;
    /**
     * 取消订单 返还水票
     */
    Map<String,Object> cancellationOrder(String orderNo) throws Exception;
    /**
     * 水管家开关
     */
    Map<String,Object> queryFunction() throws Exception;

    /**
     * 查询订单和派送单
     */
    Map<String,Object> queryOrders(JSONObject json) throws Exception;

    /**
     * 查询状态条数
     */
    Map<String,Object> queryOrdersNums(JSONObject json) throws Exception;

    /**
     * 通过用户ID查询水票剩余量
     */
    List<Map> queryUserTickets(Long userId,String waterstoreId);
    /**
     * 查找店铺营业时间
     */
      Map<String,Object>  selsectOpenTimes(String waterstoreId);
      /**
       *   回调 添加水票 生成派送单
       */
      Map<String,Object>  querySend(String payCode) throws Exception;
        /**
         *   通过订单查找订单想抢
         */
        WtOrder  queryOrderNos(String orderNo);
}