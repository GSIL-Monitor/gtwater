package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.ticket.entity
 * @ClassName TicketRecordEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/12 10:29
 */
public class TicketRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;//用户ID
    private String orderCode;//订单编号
    private String cityName;//分支机构名称
    private Long cityId;//分支机构编号
    private String userPhone;//客户电话
    private String userName;//客户名称
    private String address;//用户地址
    private Long orderTime;//订单时间
    private String setmealName;//套餐名称
    private String productName;//商品名称
    private String productSpec;//商品规格
    private Integer productCount;//订单数量
    private Long money;//金额
    private String qrCode;//二维码编号
    private  String partnerPhone ;//合伙人电话
    private Long newIncomeTime;//最近充值时间
    private Long newSendTime;//最近派送时间
    private Integer purchaseTimes; //购买次数
    private Integer surplusNum;//剩余水票总数
    private Integer isSetmeal;//是否有套餐
    private String userIdString;//用户ID--String


    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public String getUserIdString() {
        return userIdString;
    }

    public void setUserIdString(String userIdString) {
        this.userIdString = userIdString;
    }

    public Long getNewSendTime() {
        return newSendTime;
    }

    public void setNewSendTime(Long newSendTime) {
        this.newSendTime = newSendTime;
    }

    public Integer getIsSetmeal() {
        return isSetmeal;
    }

    public void setIsSetmeal(Integer isSetmeal) {
        this.isSetmeal = isSetmeal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long branchesId) {
        this.cityId = cityId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer orderCount) {
        this.productCount = productCount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getPartnerPhone() {
        return partnerPhone;
    }

    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getNewIncomeTime() {
        return newIncomeTime;
    }

    public void setNewIncomeTime(Long newIncomeTime) {
        this.newIncomeTime = newIncomeTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getPurchaseTimes() {
        return purchaseTimes;
    }

    public void setPurchaseTimes(Integer purchaseTimes) {
        this.purchaseTimes = purchaseTimes;
    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }
}
