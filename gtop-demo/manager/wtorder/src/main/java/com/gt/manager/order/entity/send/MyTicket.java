package com.gt.manager.order.entity.send;

import com.gt.manager.entity.wtUserTicket.WtUserTicket;

public class MyTicket extends WtUserTicket {
    private String goodsName;//商品名称
    private String goodsSpec;//商品规格
    private Long skuId;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Long getSkuId(){return this.skuId;}

    public void setSkuId(Long skuId){this.skuId = skuId;}
}
