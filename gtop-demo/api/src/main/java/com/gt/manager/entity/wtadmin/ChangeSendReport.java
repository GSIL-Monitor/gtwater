package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.order.entity
 * @ClassName ChangeSendReport
 * @Description:
 * @Author towards
 * @Date 2018/8/13 13:17
 */
public class ChangeSendReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String oldSendCode;//原派送单编号
    private String oldWaterName;//原站点名称
    private String oldWaterPhone;//原站点电话
    private String newSendCode;//新派送单编号
    private String newWaterName;//现站点名称
    private String newWaterPhone;//现站点电话
    private Long creatTime;//下单时间
    private Long appointmentTime;//预约时间
    private Long changeTime;//改派时间
    private Integer status;//派送单状态
    private String updateUserName;//操作人
    private Long updateTime;//操作时间

    public String getOldSendCode() {
        return oldSendCode;
    }

    public void setOldSendCode(String oldSendCode) {
        this.oldSendCode = oldSendCode;
    }

    public String getNewSendCode() {
        return newSendCode;
    }

    public void setNewSendCode(String newSendCode) {
        this.newSendCode = newSendCode;
    }

    public String getOldWaterName() {
        return oldWaterName;
    }

    public void setOldWaterName(String oldWaterName) {
        this.oldWaterName = oldWaterName;
    }

    public String getOldWaterPhone() {
        return oldWaterPhone;
    }

    public void setOldWaterPhone(String oldWaterPhone) {
        this.oldWaterPhone = oldWaterPhone;
    }

    public String getNewWaterName() {
        return newWaterName;
    }

    public void setNewWaterName(String newWaterName) {
        this.newWaterName = newWaterName;
    }

    public String getNewWaterPhone() {
        return newWaterPhone;
    }

    public void setNewWaterPhone(String newWaterPhone) {
        this.newWaterPhone = newWaterPhone;
    }

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    public Long getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Long appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
