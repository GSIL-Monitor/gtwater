package com.gt.manager.entity.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * @author why
 */

public class User implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 系统唯一id
	private String userId2;//用户id
	private Date createTime;// 生成时间
	private Date loginTime;// 登录时间
	private Integer loginSource;// 登录来源 1微信 2QQ
	private String phone;// 电话号码
	private String nickname;// 昵称
	private String username;// 用户名
	private String password;// 密码
	private String icon;// 头像url
	private String voicePromptSwitch;// 语音提示开关 0:否  1:是
	
	// Empty Constructor
	public User() {
		super();
	}
	
	// Full Constructor
	public User(Long id, Date createTime, Date loginTime, Integer loginSource, String phone, String nickname, String username, String password, String icon, String voicePromptSwitch) {
		this.id =  id;
		this.createTime =  createTime;
		this.loginTime =  loginTime;
		this.loginSource =  loginSource;
		this.phone =  phone;
		this.nickname =  nickname;
		this.username =  username;
		this.password =  password;
		this.icon =  icon;
		this.voicePromptSwitch =  voicePromptSwitch;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Date getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Date createTime) {
		this.createTime =  createTime;
	}
	
	public Date getLoginTime () {
		return this.loginTime;
	}
	
	public void setLoginTime (Date loginTime) {
		this.loginTime =  loginTime;
	}
	
	public Integer getLoginSource () {
		return this.loginSource;
	}
	
	public void setLoginSource (Integer loginSource) {
		this.loginSource =  loginSource;
	}
	
	public String getPhone () {
		return this.phone;
	}
	
	public void setPhone (String phone) {
		this.phone =  phone;
	}
	
	public String getNickname () {
		return this.nickname;
	}
	
	public void setNickname (String nickname) {
		this.nickname =  nickname;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	public void setUsername (String username) {
		this.username =  username;
	}
	
	public String getPassword () {
		return this.password;
	}
	
	public void setPassword (String password) {
		this.password =  password;
	}
	
	public String getIcon () {
		return this.icon;
	}
	
	public void setIcon (String icon) {
		this.icon =  icon;
	}
	
	public String getVoicePromptSwitch () {
		return this.voicePromptSwitch;
	}
	
	public void setVoicePromptSwitch (String voicePromptSwitch) {
		this.voicePromptSwitch =  voicePromptSwitch;
	}

	public String getUserId2() {
		return userId2;
	}

	public void setUserId2(String userId2) {
		this.userId2 = userId2;
	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("loginTime = " +  this.getLoginTime() + ",");
		entityStirngBuffer.append("loginSource = " +  this.getLoginSource() + ",");
		entityStirngBuffer.append("phone = " +  this.getPhone() + ",");
		entityStirngBuffer.append("nickname = " +  this.getNickname() + ",");
		entityStirngBuffer.append("username = " +  this.getUsername() + ",");
		entityStirngBuffer.append("password = " +  this.getPassword() + ",");
		entityStirngBuffer.append("icon = " +  this.getIcon() + ",");
		entityStirngBuffer.append("voicePromptSwitch = " +  this.getVoicePromptSwitch() + ",");
		return entityStirngBuffer.toString();
	}
	
}