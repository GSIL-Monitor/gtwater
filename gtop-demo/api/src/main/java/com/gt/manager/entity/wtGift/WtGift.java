package com.gt.manager.entity.wtGift;

import java.io.Serializable;

/**
 * 赠品记录
 *
 * @author why
 */

public class WtGift implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Fields

    private Long id;// 赠送ID
    private Integer num;//赠品数量
    private Long productId;// 赠品ID
    private String seriesSkuCode;// 套系编号（sku）
    private String skuCode;// 赠品SKU
    private Long pick;// 赠品当前价格
    private String mes;// 赠品描述
    private String orderCode;// 订单ID
    private Long userId;// 客户
    private String sigenName;// 签收人
    private Long createTime;// 创建时间
    private Long createId;// 创建人
    private Integer delState;// 删除状态 1正常、0删除
    private Integer giftType;//赠品类型【1水票、2水、3其它】

    // Empty Constructor
    public WtGift() {
        super();
    }

    // Full Constructor
    public WtGift(Long id, Integer num, Long productId, String seriesSkuCode, String skuCode, Long pick, String mes, String orderCode, Long userId, String sigenName, Long createTime, Long createId, Integer delState, Integer giftType) {
        this.id = id;
        this.num = num;
        this.productId = productId;
        this.seriesSkuCode = seriesSkuCode;
        this.skuCode = skuCode;
        this.pick = pick;
        this.mes = mes;
        this.orderCode = orderCode;
        this.userId = userId;
        this.sigenName = sigenName;
        this.createTime = createTime;
        this.createId = createId;
        this.delState = delState;
        this.giftType = giftType;
    }

    // Property accessors


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSeriesSkuCode() {
        return this.seriesSkuCode;
    }

    public void setSeriesSkuCode(String seriesSkuCode) {
        this.seriesSkuCode = seriesSkuCode;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getPick() {
        return this.pick;
    }

    public void setPick(Long pick) {
        this.pick = pick;
    }

    public String getMes() {
        return this.mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSigenName() {
        return this.sigenName;
    }

    public void setSigenName(String sigenName) {
        this.sigenName = sigenName;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return this.createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Integer getDelState() {
        return this.delState;
    }

    public void setDelState(Integer delState) {
        this.delState = delState;
    }

    @Override
    public String toString() {
        StringBuffer entityStirngBuffer = new StringBuffer();
        entityStirngBuffer.append("id = " + this.getId() + ",");
        entityStirngBuffer.append("num = " + this.getNum() + ",");
        entityStirngBuffer.append("productId = " + this.getProductId() + ",");
        entityStirngBuffer.append("seriesSkuCode = " + this.getSeriesSkuCode() + ",");
        entityStirngBuffer.append("skuCode = " + this.getSkuCode() + ",");
        entityStirngBuffer.append("pick = " + this.getPick() + ",");
        entityStirngBuffer.append("mes = " + this.getMes() + ",");
        entityStirngBuffer.append("orderCode = " + this.getOrderCode() + ",");
        entityStirngBuffer.append("userId = " + this.getUserId() + ",");
        entityStirngBuffer.append("sigenName = " + this.getSigenName() + ",");
        entityStirngBuffer.append("createTime = " + this.getCreateTime() + ",");
        entityStirngBuffer.append("createId = " + this.getCreateId() + ",");
        entityStirngBuffer.append("delState = " + this.getDelState() + ",");
        entityStirngBuffer.append("giftType = " + this.getGiftType() + ",");
        return entityStirngBuffer.toString();
    }

}