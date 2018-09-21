package com.gt.manager.entity.wtSend;


import com.gt.manager.entity.wtSendMes.WtSendMes;

import java.io.Serializable;
import java.util.List;


/**
 * 派送订单
 *
 * @author why
 */

public class WtSend implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Fields

    private Long id;// 派送ID
    private String sendNo;// 派送单编号
    private Long waterstoreId;// 水站ID
    private String provinceId;// 省ID
    private String province;// 省
    private String cityId;// 市ID
    private String city;// 市
    private String areaId;// 区ID
    private String area;// 区
    private String address;// 详细地址
    private Long userId;// 用户（客户）ID
    private String contacts;// 联系人
    private String phone;// 联系电话
    private Long appointmentTime;// 预约时间
    private Long sendTime;// 实际派送时间
    private String sendUser;// 派送人
    private String sigenUser;// 签收人
    private Integer status;// 状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】
    private String changeSendNo;// 改派后单号
    private String remarks;// 备注
    private Long createTime;// 创建时间
    private Long createId;// 创建人
    private Integer delStatus;// 删除状态
    private Long money; // 派送单总金额
    private Long updateTime;
    private Long updateId;
    private List<WtSendMes> wtSendMes;//订单详情
    private String waterstoreTel;//水站电话
    private String updateName;//

    // Empty Constructor
    public WtSend() {
        super();
    }

    // Full Constructor
    public WtSend(Long id, String sendNo, Long waterstoreId, String provinceId, String province, String cityId, String city, String areaId, String area, String address, Long userId, String contacts, String phone, Long appointmentTime, Long sendTime, String sendUser, String sigenUser, Integer status, String changeSendNo, String remarks, Long createTime, Long createId, Integer delStatus, Long money, List<WtSendMes> wtSendMes, String waterstoreTel, Long updateId, Long updateTime, String updateName) {
        this.id = id;
        this.sendNo = sendNo;
        this.waterstoreId = waterstoreId;
        this.provinceId = provinceId;
        this.province = province;
        this.cityId = cityId;
        this.city = city;
        this.areaId = areaId;
        this.area = area;
        this.address = address;
        this.userId = userId;
        this.contacts = contacts;
        this.phone = phone;
        this.appointmentTime = appointmentTime;
        this.sendTime = sendTime;
        this.sendUser = sendUser;
        this.sigenUser = sigenUser;
        this.status = status;
        this.changeSendNo = changeSendNo;
        this.remarks = remarks;
        this.createTime = createTime;
        this.createId = createId;
        this.delStatus = delStatus;
        this.money = money;
        this.wtSendMes = wtSendMes;
        this.waterstoreTel = waterstoreTel;
        this.updateId = updateId;
        this.updateName = updateName;
        this.updateTime = updateTime;
    }

    // Property accessors


    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendNo() {
        return this.sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public Long getWaterstoreId() {
        return this.waterstoreId;
    }

    public void setWaterstoreId(Long waterstoreId) {
        this.waterstoreId = waterstoreId;
    }

    public String getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getAppointmentTime() {
        return this.appointmentTime;
    }

    public void setAppointmentTime(Long appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendUser() {
        return this.sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSigenUser() {
        return this.sigenUser;
    }

    public void setSigenUser(String sigenUser) {
        this.sigenUser = sigenUser;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChangeSendNo() {
        return this.changeSendNo;
    }

    public void setChangeSendNo(String changeSendNo) {
        this.changeSendNo = changeSendNo;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Integer getDelStatus() {
        return this.delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Long getMoney() {
        return this.money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public List<WtSendMes> getWtSendMes() {
        return wtSendMes;
    }

    public void setWtSendMes(List<WtSendMes> wtSendMes) {
        this.wtSendMes = wtSendMes;
    }

    public String getWaterstoreTel() {
        return waterstoreTel;
    }

    public void setWaterstoreTel(String waterstoreTel) {
        this.waterstoreTel = waterstoreTel;
    }

    @Override
    public String toString() {
        StringBuffer entityStirngBuffer = new StringBuffer();
        entityStirngBuffer.append("id = " + this.getId() + ",");
        entityStirngBuffer.append("sendNo = " + this.getSendNo() + ",");
        entityStirngBuffer.append("waterstoreId = " + this.getWaterstoreId() + ",");
        entityStirngBuffer.append("provinceId = " + this.getProvinceId() + ",");
        entityStirngBuffer.append("province = " + this.getProvince() + ",");
        entityStirngBuffer.append("cityId = " + this.getCityId() + ",");
        entityStirngBuffer.append("city = " + this.getCity() + ",");
        entityStirngBuffer.append("areaId = " + this.getAreaId() + ",");
        entityStirngBuffer.append("area = " + this.getArea() + ",");
        entityStirngBuffer.append("address = " + this.getAddress() + ",");
        entityStirngBuffer.append("userId = " + this.getUserId() + ",");
        entityStirngBuffer.append("contacts = " + this.getContacts() + ",");
        entityStirngBuffer.append("phone = " + this.getPhone() + ",");
        entityStirngBuffer.append("appointmentTime = " + this.getAppointmentTime() + ",");
        entityStirngBuffer.append("sendTime = " + this.getSendTime() + ",");
        entityStirngBuffer.append("sendUser = " + this.getSendUser() + ",");
        entityStirngBuffer.append("sigenUser = " + this.getSigenUser() + ",");
        entityStirngBuffer.append("status = " + this.getStatus() + ",");
        entityStirngBuffer.append("changeSendNo = " + this.getChangeSendNo() + ",");
        entityStirngBuffer.append("remarks = " + this.getRemarks() + ",");
        entityStirngBuffer.append("createTime = " + this.getCreateTime() + ",");
        entityStirngBuffer.append("createId = " + this.getCreateId() + ",");
        entityStirngBuffer.append("delStatus = " + this.getDelStatus() + ",");
        entityStirngBuffer.append("money = " + this.getMoney() + ",");
        entityStirngBuffer.append("updateTime = " + this.getUpdateTime() + ",");
        entityStirngBuffer.append("updateId = " + this.getUpdateId() + ",");
        entityStirngBuffer.append("wtSendMes = " + this.getWtSendMes() + ",");
        entityStirngBuffer.append("waterstoreTel = " + this.getWaterstoreTel() + ",");
        entityStirngBuffer.append("updateName = " + this.getUpdateName() + ",");
        return entityStirngBuffer.toString();
    }

}