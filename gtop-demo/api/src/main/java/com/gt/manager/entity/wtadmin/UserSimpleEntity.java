package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.ticket.entity
 * @ClassName UserSimpleEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/9 19:19
 */
public class UserSimpleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;//用户ID
    private String userName;//用户名称
    private String phone;//用户电话

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
