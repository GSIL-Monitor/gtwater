package com.gt.manager.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.manager.entity.wtOrderMes.WtOrderMes;
import com.gt.manager.entity.wtadmin.OrderRecordEntity;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import com.gt.manager.order.repository.WtOrderDAO;
import com.gt.manager.order.repository.WtOrderMesDAO;
import com.gt.manager.order.service.IOrderService;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.order.service.impl
 * @ClassName OrderServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/12 22:33
 */
@Service

public class OrderServiceImpl implements IOrderService {
    private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
    @Autowired
    WtOrderDAO wtOrderDAO;
    @Autowired
    WtOrderMesDAO wtOrderMesDAO;
    /**
     * 根据条件查询订单信息
     *
     * @param orderCode
     * @param userPhone
     * @param fromOrderTime
     * @param toOrderTime
     * @param orderStatus
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getOrderRecordList(Integer pageNo, Integer pageSize, String orderCode, String userPhone,
                                         Long fromOrderTime, Long toOrderTime,
                                         Integer orderStatus) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("orderCode",orderCode);
        map.put("userPhone",userPhone);
        map.put("fromOrderTime",fromOrderTime);
        map.put("toOrderTime",toOrderTime);
        map.put("orderStatus",orderStatus);
        boolean isPage = false;
        if (pageNo != null && pageSize != null) {
            isPage = true;
        }
        if (isPage) {
            PageHelper.startPage(pageNo, pageSize);
        }

        List<OrderRecordEntity> orderRecordList = wtOrderDAO.getOrderRecordList(map);
        JSONObject json = new JSONObject();
        json.put("orderRecordList", orderRecordList);
        if (isPage) {
            PageInfo<OrderRecordEntity> pageInfo = new PageInfo<OrderRecordEntity>(orderRecordList);
            JSONObject page = new JSONObject();
            page.put("pageNo", pageNo);
            page.put("total", pageInfo.getPages());
            page.put("count", pageInfo.getTotal());
            json.put("page", page);
        }

        return json;
    }

    /**
     * 根据订单编号获取商品信息
     *
     * @param orderCode
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductMsgEntity> getProductListByOrderCode(String orderCode) throws Exception {
        if (StringUtils.isEmpty(orderCode)){
            throw new GtopException("缺少订单信息");
        }

        //根据订单编号查询订单详情
        List<WtOrderMes> wtOrderMes=wtOrderMesDAO.selectByOrderCode(orderCode);
        if(CollectionUtils.isEmpty(wtOrderMes)){
            throw new GtopException("没有数据");
        }
        List<ProductMsgEntity> productMsgEntities = new ArrayList<>();
        for (int i = 0; i <wtOrderMes.size() ; i++) {
            ProductMsgEntity productMsgEntity= new ProductMsgEntity();
            Integer count= wtOrderMes.get(i).getNum();
            String sequence= wtOrderMes.get(i).getSequence();
            JSONObject json = JSONObject.parseObject(sequence);
            //String productName= json.getString("");
            Integer type= wtOrderMes.get(i).getpType();
            productMsgEntity.setCount(count);
            if (type==1){
                JSONObject setmealJson = json.getJSONObject("setmealJson");
                productMsgEntity.setProductName(setmealJson.getString("seriesName"));
                productMsgEntity.setPrice(setmealJson.getLong("price"));
                productMsgEntity.setProductPic(setmealJson.getString("shopcartImg"));

            }
            if (type==2){
                JSONObject productJson = json.getJSONObject("productJson");
                productMsgEntity.setProductPic(productJson.getString("goodsPic"));
                productMsgEntity.setPrice(productJson.getLong("sellPrice"));
                productMsgEntity.setProductName(productJson.getString("goodsName"));
                productMsgEntity.setProductSpec(productJson.getString("goodsSpec"));
                productMsgEntity.setBrandName(productJson.getString("brandName"));
            }
            productMsgEntities.add(productMsgEntity);

        }
        return productMsgEntities;

        //return wtOrderDAO.getProductListByOrderCode(orderCode);
    }
}
