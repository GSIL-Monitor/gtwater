package com.gt.manager.user.entity.user;

import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.user.User;

import java.util.Date;

public class UserAndAdd extends User {
    private ReceiveAddress receiveAddress;

    public UserAndAdd() {
    }

    public UserAndAdd(Long id, Date createTime, Date loginTime, Integer loginSource, String phone, String nickname, String username, String password, String icon, String voicePromptSwitch) {
        super(id, createTime, loginTime, loginSource, phone, nickname, username, password, icon, voicePromptSwitch);
    }

    public UserAndAdd(Long id, Date createTime, Date loginTime, Integer loginSource, String phone, String nickname, String username, String password, String icon, String voicePromptSwitch, ReceiveAddress receiveAddress) {
        super(id, createTime, loginTime, loginSource, phone, nickname, username, password, icon, voicePromptSwitch);
        this.receiveAddress = receiveAddress;
    }

    public ReceiveAddress getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(ReceiveAddress receiveAddress) {
        this.receiveAddress = receiveAddress;
    }
}
