package com.gt.manager.entity.wtUser;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * @author why
 */

public class WtUser implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 系统唯一id
	private Date createTime;// 生成时间
	private Date loginTime;// 登录时间
	private Integer loginSource;// 登录来源 1微信 2QQ
	private String openCode;// 
	private Long partnerId;// 
	private String phone;// 电话号码
	private String nickname;// 昵称
	private String username;// 用户名
	private String password;// 密码
	private String icon;// 头像url
	private String voicePromptSwitch;// 语音提示开关 0:否  1:是
	private Long createTime2;// 创建时间
	private Long createId;// 创建人
	private Integer delStatus;// 删除状态
	
	// Empty Constructor
	public WtUser() {
		super();
	}
	
	// Full Constructor
	public WtUser(Long id, Date createTime, Date loginTime, Integer loginSource, String openCode, Long partnerId, String phone, String nickname, String username, String password, String icon, String voicePromptSwitch, Long createTime2, Long createId, Integer delStatus) {
		this.id =  id;
		this.createTime =  createTime;
		this.loginTime =  loginTime;
		this.loginSource =  loginSource;
		this.openCode =  openCode;
		this.partnerId =  partnerId;
		this.phone =  phone;
		this.nickname =  nickname;
		this.username =  username;
		this.password =  password;
		this.icon =  icon;
		this.voicePromptSwitch =  voicePromptSwitch;
		this.createTime2 =  createTime2;
		this.createId =  createId;
		this.delStatus =  delStatus;
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
	
	public String getOpenCode () {
		return this.openCode;
	}
	
	public void setOpenCode (String openCode) {
		this.openCode =  openCode;
	}
	
	public Long getPartnerId () {
		return this.partnerId;
	}
	
	public void setPartnerId (Long partnerId) {
		this.partnerId =  partnerId;
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
	
	public Long getCreateTime2 () {
		return this.createTime2;
	}
	
	public void setCreateTime2 (Long createTime2) {
		this.createTime2 =  createTime2;
	}
	
	public Long getCreateId () {
		return this.createId;
	}
	
	public void setCreateId (Long createId) {
		this.createId =  createId;
	}
	
	public Integer getDelStatus () {
		return this.delStatus;
	}
	
	public void setDelStatus (Integer delStatus) {
		this.delStatus =  delStatus;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("loginTime = " +  this.getLoginTime() + ",");
		entityStirngBuffer.append("loginSource = " +  this.getLoginSource() + ",");
		entityStirngBuffer.append("openCode = " +  this.getOpenCode() + ",");
		entityStirngBuffer.append("partnerId = " +  this.getPartnerId() + ",");
		entityStirngBuffer.append("phone = " +  this.getPhone() + ",");
		entityStirngBuffer.append("nickname = " +  this.getNickname() + ",");
		entityStirngBuffer.append("username = " +  this.getUsername() + ",");
		entityStirngBuffer.append("password = " +  this.getPassword() + ",");
		entityStirngBuffer.append("icon = " +  this.getIcon() + ",");
		entityStirngBuffer.append("voicePromptSwitch = " +  this.getVoicePromptSwitch() + ",");
		entityStirngBuffer.append("createTime2 = " +  this.getCreateTime2() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delStatus = " +  this.getDelStatus() + ",");
		return entityStirngBuffer.toString();
	}
	
}