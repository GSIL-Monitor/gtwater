package com.gt.manager.merchant.entity.response;

import com.gt.manager.entity.wtSendMes.WtSendMes;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyueli
 * @date 2018/8/3 14:27
 */
public class WtSendResponse implements Serializable {
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
    private Long money;//派单总金额
    private Long updateTime;//最后操作时间
    private Long updateId;//最后操作人
    private Long urgeTimes=0l;//催单次数

    private List<WtSendMes> wtSendMesList;//派单详情

    // Empty Constructor


    public Long getUrgeTimes() {
        return urgeTimes;
    }

    public void setUrgeTimes(Long urgeTimes) {
        this.urgeTimes = urgeTimes;
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

    public WtSendResponse() {
        super();
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public Long getWaterstoreId() {
        return waterstoreId;
    }

    public void setWaterstoreId(Long waterstoreId) {
        this.waterstoreId = waterstoreId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Long appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSigenUser() {
        return sigenUser;
    }

    public void setSigenUser(String sigenUser) {
        this.sigenUser = sigenUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChangeSendNo() {
        return changeSendNo;
    }

    public void setChangeSendNo(String changeSendNo) {
        this.changeSendNo = changeSendNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public List<WtSendMes> getWtSendMesList() {
        return wtSendMesList;
    }

    public void setWtSendMesList(List<WtSendMes> wtSendMesList) {
        this.wtSendMesList = wtSendMesList;
    }

    @Override
    public String toString() {
        return "WtSendResponse{" +
                "id=" + id +
                ", sendNo='" + sendNo + '\'' +
                ", waterstoreId=" + waterstoreId +
                ", provinceId='" + provinceId + '\'' +
                ", province='" + province + '\'' +
                ", cityId='" + cityId + '\'' +
                ", city='" + city + '\'' +
                ", areaId='" + areaId + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                ", contacts='" + contacts + '\'' +
                ", phone='" + phone + '\'' +
                ", appointmentTime=" + appointmentTime +
                ", sendTime=" + sendTime +
                ", sendUser='" + sendUser + '\'' +
                ", sigenUser='" + sigenUser + '\'' +
                ", status=" + status +
                ", changeSendNo='" + changeSendNo + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", delStatus=" + delStatus +
                ", wtSendMesList=" + wtSendMesList +
                '}';
    }
}
