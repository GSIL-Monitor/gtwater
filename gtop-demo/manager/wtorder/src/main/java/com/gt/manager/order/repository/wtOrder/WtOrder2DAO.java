package com.gt.manager.order.repository.wtOrder;


import com.gt.manager.entity.wtInvoice.WtInvoice;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtOrderMes.WtOrderMes;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * @author why
 */
@Mapper
public interface WtOrder2DAO {

   /**
	* 查询出所有订单
	*/
    List<Map<String, Object>> queryAllOrder(HashMap <String, Object> map);

    /**
     * 订单详情
     */
    List<Map<String, Object>> queryOrderMes(HashMap<String, Object> map);

    /**
     * 添加订单
     */
     Long insert(WtOrder wt);
    /**
     * 添加订单详情
     */
     Long insertMes(WtOrderMes wtmes);
    /**
     * 插入发票
     */
     Long insertWtInvoice(WtInvoice wtInvoice);
     /**
     * 添加用户水票
     */
     Long insertUserTicket(HashMap <String, Object> map);
    /**
     * 添加水票消费记录表
     */
     Long insertTicketLog(HashMap <String, Object> map);
    /**
     * 根据水站ID和SKU 查询水站商品是否下架 1上架 2下架
     */
     Map queryProductStatus(HashMap <String, Object> map);
    /**
     * 根据水站ID和SKU 查询水站套餐是否下架 1上架 0下架
     */
    Map querySetmealStatus(HashMap <String, Object> map);
    /**
     * 根据用户ID和SKU 查询剩余水票
     */
     List<Map<String, Object>> queryWaterStatus(HashMap<String, Object> map);
   /**
    * 修改用户水票
    */
     Long updateUserTicket(HashMap <String, Object> map);
    /**
     * 查询条件订单列表
     */
      WtOrder selectOrderList(HashMap <String, Object> map);
    /**
     * 根据订单编号查询用户水票I
     */
      HashMap<String,Object> queryOneUserId(HashMap <String, Object> map);
    /**
     * 修改订单状态
     */
      Long updateOrder(Map <String, Object> map);
    /**
     * 根据用户ID查看用户发票
     */
      List<HashMap<String,Object>> queryUserDetailed(HashMap <String, Object> map);
    /**
     * 修改订单状态
     */
      Long updateticketLogMes(HashMap <String, Object> map);
    /**
     * 查询是否为混合支付 如果 sku_code =2 则为混和支付
     */
      Map<String,Object> querySkuCodeNum(HashMap <String, Object> map);
    /**
     * 查询商品规格和商品名称
     */
      Map<String,Object> queryNameSpece(HashMap <String, Object> map);
    /**
     * 通过订单状态为未付款和 付款方式为水票 查出订单编号，订单明细编号，商品编号，商品明细编号，下单时间
     */
      List<Map<String,Object>> queryTimeOut();
    /**
     * 通过订单明细查询 水票ID 和数量
     */
      List<Map<String,Object>> queryTicketId(HashMap <String, Object> map);
     /**
      * 根据水票ID修改 用户水票余量
      */
       Long updateUserSurplusNum(HashMap <String, Object> map);
     /**
      * 通过订单 查询订单明细
      */
       List  queryOrderMesNo(HashMap <String, Object> map);
     /**
      * 通过用户ID 查看二维码ID
      */
     Map<String,Object>  queryCodeId(HashMap <String, Object> map);
    /**
     * 水管家开关
     */
     Map<String,Object>  queryFunction();
    /**
     * 查询套系数量和赠品数量
     */
    Map<String,Object>  querySetmealNum(HashMap <String, Object> map);

    /**
     * 查询订单
     */
    List<Map<String,Object>>  queryOrders(HashMap <String, Object> map);
    /**
     * 查询派送单
     */
    List<Map<String,Object>>  queryDistributeLeaflets(HashMap <String, Object> map);

    /**
     * 订单根据用户ID查找状态条数
     */
    List<Map<String,Object>>  queryOrdersNums(Map <String, Object> map);
    /**
     * 派单根据用户ID查找状态条数
     */
    List<Map<String,Object>>  queryDistributeLeafletsNums(Map <String, Object> map);
    /**
     * 通过订单编号查找订单详情
     */
    List<WtOrderMes> selectByOrderId(String orderNo);
    /**
     * 通过用户ID查找订单
     */
    List<WtOrder> selectByOrderUserId(Map<String,Object> map);
    /**
     * 通过用户ID查询水票剩余量
     */
    List<Map> queryUserTickets(Map<String,Object> map);
    /**
     * 通过订单编号查询信息
     */
    WtOrder selectByOrders(Map<String,Object> map);
    /**
     * 修改gtpay生成的订单号
     */
    Long updateOrders(Map map);
    /**
     * 通过gtpay 查询订单 和 订单详情
     */
    WtOrder  weChatPayment(String payCode);
    /**
     * 查询商品信息生成商品序列
     */
    Map<String,Object>  selectProducts(Map <String, Object> map);
    /**
     * 通过订单编号查询付款金额
     */
    Map<String,Object>  queryPrices(String orderNo);
    /**
     * 通过订单编号查找 订单 订单详情  赠品  赠品商品图文
     */
     List<Map<String,Object>> selsectMesByNo(String orderNo);
    /**
     * 通过订单编号查找发票
     */
     Map<String,Object>  selsectInvoiceByNo(String orderNo);
    /**
     * 查找店铺营业时间
     */
    Map<String,Object>  selsectOpenTimes(String waterstoreId);
    /**
      * 通过USERID查找openid
     */
     Map<String,Object>  selsectOpenId(String userId);
    /**
      * 通过skucode 水站ID 查找套餐详情
     */
     Map<String,Object> querySetmealJson(Map <String, Object> map);
     /**
      * 通过skucode 机构id 查找套餐商品
      */
     Map<String,Object> querySetmealProJson(Map <String, Object> map);
     /**
      * 通过套系code 机构id 查找赠品和赠品商品信息
      */
     List<Map<String,Object>> queryGiftProJson(Map <String, Object> map);
    /**
     * 通过套系code 水站ID查找套餐与套餐商品
     */
    Map<String,Object> queryetmealsSMes(Map <String, Object> map);

     /**
      * 添加用户水票表
      */
     Long insertTickList(WtUserTicket tickList);

     /**
      * 批量添加水票日志表
      */
     Long insertTickLogLost(List<WtTicketLog> tickLogLost);

     /**
      * 批量添加派单表
      */
     Long insertSendList(List<WtSend> sendList);

     /**
      * 批量添加派单详情表
      */
     Long insertSendMesList(List<WtSendMes> sendMesList);

     /**
      * 批量添加派单详情表
      */
      WtUserTicket selsectTicketByCode(Map<String,Object> map);

      /**
       * 修改用户水票数量
       */
      Long updateUTickList(Map<String,Object> map);

      /**
       * 修改用户水票数量
       */
      WtOrderMes queryOrderMesNoTick(WtOrderMes ws);

}