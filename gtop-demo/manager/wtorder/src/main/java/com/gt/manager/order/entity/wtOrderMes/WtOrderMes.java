package com.gt.manager.order.entity.wtOrderMes;

import com.gt.manager.entity.wtGift.WtGift;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情
 *
 * @author why
 */

public class WtOrderMes implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Fields

    private Long id;// 详情ID
    private String orderNo;// 订单编号
    private String orderMesNo;//
    private String skuCode;// SKU编号
    private String skuName;// SKU名称
    private Integer type;//支付类型（1水票|| 2现金 ）
    private Integer pType;//商品类型 1水票|| 2桶水
    private Long price;// 售价
    private Integer num;// 数量
    private Long totalPrice;//
    private String sequence;// 商品序列
    private Long createTime;// 创建时间
    private Long createId;// 创建人
    private Integer delState;// 删除状态 1正常、0删除
    private List<WtGift> wtGift;

    // Empty Constructor
    public WtOrderMes() {
        super();
    }

    // Full Constructor
    public WtOrderMes(Long id, String orderNo, String orderMesNo, String skuCode, String skuName, Integer type, Integer pType, Long price, Integer num, Long totalPrice, String sequence, Long createTime, Long createId, Integer delState,List<WtGift> wtGift) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderMesNo = orderMesNo;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.type = type;
        this.pType = pType;
        this.price = price;
        this.num = num;
        this.totalPrice = totalPrice;
        this.sequence = sequence;
        this.createTime = createTime;
        this.createId = createId;
        this.delState = delState;
        this.wtGift = wtGift;
    }

    // Property accessors


    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderMesNo() {
        return this.orderMesNo;
    }

    public void setOrderMesNo(String orderMesNo) {
        this.orderMesNo = orderMesNo;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    public List <WtGift> getWtGift() {
        return wtGift;
    }

    public void setWtGift(List <WtGift> wtGift) {
        this.wtGift = wtGift;
    }

    @Override
    public String toString() {
        StringBuffer entityStirngBuffer = new StringBuffer();
        entityStirngBuffer.append("id = " + this.getId() + ",");
        entityStirngBuffer.append("orderNo = " + this.getOrderNo() + ",");
        entityStirngBuffer.append("orderMesNo = " + this.getOrderMesNo() + ",");
        entityStirngBuffer.append("skuCode = " + this.getSkuCode() + ",");
        entityStirngBuffer.append("skuName = " + this.getSkuName() + ",");
        entityStirngBuffer.append("type = " + this.getType() + ",");
        entityStirngBuffer.append("pType = " + this.getpType() + ",");
        entityStirngBuffer.append("price = " + this.getPrice() + ",");
        entityStirngBuffer.append("num = " + this.getNum() + ",");
        entityStirngBuffer.append("totalPrice = " + this.getTotalPrice() + ",");
        entityStirngBuffer.append("sequence = " + this.getSequence() + ",");
        entityStirngBuffer.append("createTime = " + this.getCreateTime() + ",");
        entityStirngBuffer.append("createId = " + this.getCreateId() + ",");
        entityStirngBuffer.append("delState = " + this.getDelState() + ",");
        entityStirngBuffer.append("wtGift = " + this.getWtGift() + ",");
        return entityStirngBuffer.toString();
    }

}