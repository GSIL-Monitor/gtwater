package com.gt.manager.merchant.entity;

import java.io.Serializable;

public class ProfitMesEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long profitId;//主分佣id
    private String orderCode;//订单明细编号
    private Integer orderNum;//订单数量
    private Long unitPrice;//单价
    private String skuCode;//sku编号
    private Long systemProfitMoney;//系统分佣
    private Integer systemProportion;//系统比例
    private Long orderTime;//下单时间即派单生成时间

    private Long createTime;//分佣生成时间，即当前时间
    private Long createId;//创建人
    private Integer delStatus=1;

    private Long cityId;
    private Long cityUserId;
    private Long cityProfitMoney;
    private Integer cityProportion;

    private Long partnerProfitMoney;
    private Integer partnerProportion;
    private Long partnerId;

    private Long waterstoreId;
    private Long branchesId;//水站机构id
    private String waterName;
    private String waterstoreNo;
    private Long waterstoreProfitMoney;
    private Integer waterstoreroportion;

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getProfitId() {
        return profitId;
    }

    public void setProfitId(Long profitId) {
        this.profitId = profitId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getSystemProfitMoney() {
        return systemProfitMoney;
    }

    public void setSystemProfitMoney(Long systemProfitMoney) {
        this.systemProfitMoney = systemProfitMoney;
    }

    public Integer getSystemProportion() {
        return systemProportion;
    }

    public void setSystemProportion(Integer systemProportion) {
        this.systemProportion = systemProportion;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCityUserId() {
        return cityUserId;
    }

    public void setCityUserId(Long cityUserId) {
        this.cityUserId = cityUserId;
    }

    public Long getCityProfitMoney() {
        return cityProfitMoney;
    }

    public void setCityProfitMoney(Long cityProfitMoney) {
        this.cityProfitMoney = cityProfitMoney;
    }

    public Integer getCityProportion() {
        return cityProportion;
    }

    public void setCityProportion(Integer cityProportion) {
        this.cityProportion = cityProportion;
    }

    public Long getPartnerProfitMoney() {
        return partnerProfitMoney;
    }

    public void setPartnerProfitMoney(Long partnerProfitMoney) {
        this.partnerProfitMoney = partnerProfitMoney;
    }

    public Integer getPartnerProportion() {
        return partnerProportion;
    }

    public void setPartnerProportion(Integer partnerProportion) {
        this.partnerProportion = partnerProportion;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getWaterstoreId() {
        return waterstoreId;
    }

    public void setWaterstoreId(Long waterstoreId) {
        this.waterstoreId = waterstoreId;
    }

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public String getWaterName() {
        return waterName;
    }

    public void setWaterName(String waterName) {
        this.waterName = waterName;
    }

    public String getWaterstoreNo() {
        return waterstoreNo;
    }

    public void setWaterstoreNo(String waterstoreNo) {
        this.waterstoreNo = waterstoreNo;
    }

    public Long getWaterstoreProfitMoney() {
        return waterstoreProfitMoney;
    }

    public void setWaterstoreProfitMoney(Long waterstoreProfitMoney) {
        this.waterstoreProfitMoney = waterstoreProfitMoney;
    }

    public Integer getWaterstoreroportion() {
        return waterstoreroportion;
    }

    public void setWaterstoreroportion(Integer waterstoreroportion) {
        this.waterstoreroportion = waterstoreroportion;
    }
}
