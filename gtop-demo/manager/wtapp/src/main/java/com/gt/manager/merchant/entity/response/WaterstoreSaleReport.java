package com.gt.manager.merchant.entity.response;

import java.io.Serializable;

/**
 * @author fengyueli
 * @date 2018/8/9 10:46
 */
public class WaterstoreSaleReport implements Serializable{
    private static final long serialVersionUID = 1L;
    private String sendNo;
    private String contacts;
    private String address;
    private String orderMoney;//派送单的金额
    private String profitMoney;//分佣金额
    private Integer sendNum;
    private String brandName;
    private Integer proportion;
    private String detail;

    public String getDetail() {
        return detail;
    }



    public WaterstoreSaleReport() {

    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }


    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(String profitMoney) {
        this.profitMoney = profitMoney;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }
}
