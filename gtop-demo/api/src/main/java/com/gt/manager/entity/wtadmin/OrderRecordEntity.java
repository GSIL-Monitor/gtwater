package com.gt.manager.entity.wtadmin;



import java.io.Serializable;

/**
 * @Package com.gt.manager.order
 * @ClassName OrederRecordEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/12 21:07
 */
public class OrderRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderCode;//订单编号
    private String userName;//客户名称
    private String userPhone;//客户电话
    private String address;//送货地址
    private String branchesName;//所属区域
    private Long orderCreatTime;//订单创建时间
    private Integer num;//订购数量
    private Long orderTotal;//订单总价
    private Integer orderStatus;//订单状态

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranchesName() {
        return branchesName;
    }

    public void setBranchesName(String branchesName) {
        this.branchesName = branchesName;
    }

    public Long getOrderCreatTime() {
        return orderCreatTime;
    }

    public void setOrderCreatTime(Long orderCreatTime) {
        this.orderCreatTime = orderCreatTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
