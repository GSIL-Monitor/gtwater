package com.gt.manager.order.resource.order;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.BaseResource;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.Result;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.order.service.wtOrder.impl.WtOrderServiceImpl;
import com.gt.manager.order.service.wtSend.impl.WtSendServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@Path("/order")
@Api(value = "/order", description = "订单" + "")
public class OrderResources extends BaseResource {

    private static Logger log = Logger.getLogger(OrderResources.class);

    @Autowired
    private WtOrderServiceImpl service;
    @Autowired
    private WtSendServiceImpl sendService;

    /**
     * @Description  所有订单展示订单
     * @author
     * @return
     */
    @POST
    @Path("/getOrderPage")
    @ApiOperation(value = "/getOrderPage", notes = "所有订单展示订单")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getOrderPage(ReqData re ) {
        JSONObject json = JSONObject.parseObject(re.getParams());
        HashMap<String,Object> has = new HashMap<String,Object>();
        has.put("userId",Long.parseLong(json.getString("userId")));
        DataMessage res = null;
        try {
            List<Map<String, Object>> response = service.queryList(has);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
         * @Description 订单详情
     * @author
     * @return
     */
    @POST
    @Path("/getOrderDetails")
    @ApiOperation(value = "/getOrderDetails", notes = "订单详情")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getDetails(ReqData re) {
        JSONObject json = JSONObject.parseObject(re.getParams());
        HashMap<String,Object> has = new HashMap<String,Object>();
        has.put("orderNo",json.getInteger("orderNo"));
        DataMessage res = null;
        try {
            List<Map<String, Object>> response = service.queryOrderDetails(has);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 添加订单 与 订单详情
     * @author
     * @return
     */
    @POST
    @Path("/getSaveOrder")
    @ApiOperation(value = "/getSaveOrder", notes = "添加订单 与 订单详情")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getSaveOrder(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            if(json != null && !json.isEmpty()){
                synchronized(json.getString("userId").intern()){
                    HashMap<String,Object> response = service.insert(json);
                    res = new DataMessage(Result.SUCCESS.getCode(),response,"添加成功");
                }
            }else{
                throw  new Exception("订单数据为空");
            }
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description
     * @author添加用户水票和 水票消费记录表
     * @return
     */
    @POST
    @Path("/getSaveUserTicket")
    @ApiOperation(value = "/getSaveUserTicket", notes = "添加用户水票和 水票消费记录表")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage insertUserTicket(ReqData re ){
        DataMessage res = null;

        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            String response = service.insertUserTicket(json);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"添加成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"添加失败");
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description
     * @author 水管家开关
     * @return
     */
    @POST
    @Path("/getFunction")
    @ApiOperation(value = "/getFunction", notes = "水管家开关 ")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getFunction(){
        DataMessage res = null;
        try {
            Map response = service.queryFunction();
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description 购物车 支付水票 或支付钱款
     * @author
     * @return
     */
    @POST
    @Path("/getPayment")
    @ApiOperation(value = "/getPayment", notes = " 购物车 支付水票 或支付钱款")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getPayment(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.getPayment(json);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"支付成功");
        } catch (Exception ex) {
            Map response = null;
            try {
                response = service.getPayment(json);
                res = new DataMessage(Result.SUCCESS.getCode(),response,"支付成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * @Description 取消订单 修改订单状态 归还水票
     * @author
     * @return
     */
    @POST
    @Path("/cancellationOrder")
    @ApiOperation(value = "/cancellationOrder", notes = " 取消订单 修改订单状态 归还水票")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage updateCancellationOrder(ReqData re){
        DataMessage res = null;
        System.out.println("ReqData============="+re);
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.cancellationOrder(json.getString("orderNo"));
            res = new DataMessage(Result.SUCCESS.getCode(),response,"修改成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"修改失败");
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 通过订单编号查询信息
     * @author
     * @return
     */
    @POST
    @Path("/queryOrderXinXi")
    @ApiOperation(value = "/queryOrderXinXi", notes = " 通过订单编号查询信息")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage queryOrderXinXi(ReqData re){
        log.info("通过订单编号查询信息="+JSONObject.toJSONString(re));
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            log.info("json 通过订单编号查询信息 = " + json);
            String orderNo = json.getString("orderNo");
            WtOrder response = service.queryOrderNos(orderNo);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 查询订单和派送单
     * @author
     * @return
     */
    @POST
    @Path("/queryOrders")
    @ApiOperation(value = "/queryOrders", notes = " 查询订单和派送单")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage queryOrders(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.queryOrders(json);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * 查询状态数量
     * @param
     * @return
     * @throws Exception
     */
    @POST
    @Path("/queryOrdersNums")
    @ApiOperation(value = "/queryOrdersNums", notes = " 查询订单和派送单")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage queryOrdersNums(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.queryOrdersNums(json);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
            log.info("查询数量打印  res"+JSONObject.toJSONString(res));
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex,"查询状态数量失败");
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * orderCode	微信支付订单号（需找到对应的orderCode）
     * @param
     * @Description	线上支付
     * @author 李喆
     */
    @POST
    @Path("/onLinePayCallBack")
    @ApiOperation(value = "/onLinePayCallBack", notes = "线上支付回调")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    //@Consumes({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes("application/x-www-form-urlencoded")

    public com.gt.gtpay.entity.base.DataMessage onLinePayCallBack(@FormParam("orderCode") String payCode)
    {
        com.gt.gtpay.entity.base.DataMessage res = null;
        try {
            System.out.println("payCode===="+payCode);
            log.info("payCode=========================="+payCode);
            Map orderNos =  service.querySend(payCode);
            res = new com.gt.gtpay.entity.base.DataMessage(Result.SUCCESS.getCode(),orderNos,(String) orderNos.get("message"));
        } catch (Exception ex) {
            res = new com.gt.gtpay.entity.base.DataMessage(Result.FAIL.getCode(), "失败",null);
            ex.printStackTrace();
        }
        return res;

    }
    /**
     * 通过用户ID查询水票剩余量
     * @param
     * @Description
     * @author 李喆
     */
    @GET
    @Path("/queryUserTickets")
    @ApiOperation(value = "/queryUserTickets", notes = "通过用户ID查询水票剩余量")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage queryUserTickets(@QueryParam("userId") Long userId,@QueryParam("waterstoreId") String waterstoreId)
    {
        DataMessage res = null;
        try {
            List<Map> response = service.queryUserTickets(userId,waterstoreId);
            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 通过水站ID查找营业时间
     * @author
     * @return
     */
    @POST
    @Path("/getOpenTimes")
    @ApiOperation(value = "/getOpenTimes", notes = " 通过水站ID查找营业时间")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getOpenTimes(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            String waterstoreId = json.getString("waterstoreId");
            Map response = service.selsectOpenTimes(waterstoreId);

            res = new DataMessage(Result.SUCCESS.getCode(),response,"查询成功");

        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }
}
