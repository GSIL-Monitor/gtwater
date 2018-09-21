package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.ticket.entity
 * @ClassName WtTicketSimple
 * @Description:
 * @Author towards
 * @Date 2018/8/9 19:29
 */
public class WtTicketSimple implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productName;//商品名称
    private String goodsSpec;//商品规格
    private String productSkuCode;//商品sku编号
    private Integer surplusNum;//剩余数量


    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }
}
