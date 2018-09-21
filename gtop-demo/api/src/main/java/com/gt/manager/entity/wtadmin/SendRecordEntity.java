package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.order.entity
 * @ClassName SendRecordEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/13 9:43
 */
public class SendRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sendId;//配送单ID
    private String sendCode;//派送单编号
    private String userName;//客户名称
    private String userPhone;//客户电话
    private String branchesName;//所在城市
    private String address;//送货地址
    private Long orderCreatTime;//下单时间
    private Long appointmentTime;//预约时间
    private Integer orderQuantity;//订购数量
    private Long orderTotal;//订单总价
    private Integer sendStatus;//派送单状态
    private String waterName;//水站名称
    private Long branchesId;//水站机构ID
    private Long cityBranchesId;//水站所属城市机构ID
    private String waterPhone;//水站电话
    private  Integer payType;//支付方式
    private Long sendTime;//实际送达时间
    private String productName;//商品名称
    private String productSpec;//商品规格
    private  Long sendMesTotalPrice;//配送结算价格

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public Long getCityBranchesId() {
        return cityBranchesId;
    }

    public void setCityBranchesId(Long cityBranchesId) {
        this.cityBranchesId = cityBranchesId;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBranchesName() {
        return branchesName;
    }

    public void setBranchesName(String branchesName) {
        this.branchesName = branchesName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOrderCreatTime() {
        return orderCreatTime;
    }

    public void setOrderCreatTime(Long orderCreatTime) {
        this.orderCreatTime = orderCreatTime;
    }

    public Long getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Long appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getWaterName() {
        return waterName;
    }

    public void setWaterName(String waterName) {
        this.waterName = waterName;
    }

    public String getWaterPhone() {
        return waterPhone;
    }

    public void setWaterPhone(String waterPhone) {
        this.waterPhone = waterPhone;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
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

    public Long getSendMesTotalPrice() {
        return sendMesTotalPrice;
    }

    public void setSendMesTotalPrice(Long sendMesTotalPrice) {
        this.sendMesTotalPrice = sendMesTotalPrice;
    }
}
