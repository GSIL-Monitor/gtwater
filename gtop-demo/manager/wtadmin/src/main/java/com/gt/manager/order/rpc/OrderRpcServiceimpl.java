package com.gt.manager.order.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;

import com.gt.manager.order.service.IOrderService;
import com.gt.manager.rpc.order.IOrderRpcService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Package com.gt.manager.order.rpc
 * @ClassName OrderRpcServiceimpl
 * @Description:
 * @Author towards
 * @Date 2018/8/12 21:04
 */
@Service
public class OrderRpcServiceimpl implements IOrderRpcService {
    private static final Logger log = Logger.getLogger(OrderRpcServiceimpl.class);


    @Autowired
    IOrderService orderService;

    /**
     * 根据条件查询订单信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception String orderCode,String userPhone,
     *                   Long fromOrderTime,Long toOrderTime,
     *                   Integer orderStatus
     */
    @Override
    public DataMessage getOrderRecordList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer pageNo = jsonObject.getInteger("pageNo");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String orderCode = jsonObject.getString("orderCode");
        String userPhone = jsonObject.getString("userPhone");
        Long fromOrderTime = jsonObject.getLong("fromOrderTime");
        Long toOrderTime = jsonObject.getLong("toOrderTime");
        Integer orderStatus = jsonObject.getInteger("orderStatus");
        if (fromOrderTime!=null&&toOrderTime!=null&&fromOrderTime>toOrderTime){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "起始时间大于结束时间！");
        }
        JSONObject json = orderService.getOrderRecordList(pageNo,pageSize,orderCode, userPhone,
                fromOrderTime, toOrderTime, orderStatus);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }

    /**
     * 根据订单号查询商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getProductListByOrderCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String orderCode=jsonObject.getString("orderCode");
        if (StringUtils.isEmpty(orderCode)){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("orderCode:"+orderCode);
        orderCode=orderCode.trim();
        List<ProductMsgEntity> productMsgEntities= orderService.getProductListByOrderCode(orderCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, productMsgEntities, "请求成功");
    }

    /**
     * 根据条件导出订单信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage exportOrderList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        return null;
    }
}
