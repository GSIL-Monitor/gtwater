package com.gt.manager.entity.thirdUser;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方鉴权对照表
 * @author why
 */

public class ThirdUser implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 
	private String thirdName;// 第三方名称
	private Integer thirdType;// 第三方类型 1微信 2QQ
	private String openId;// 第三方唯一id
	private String unionId;// 系统唯一id
	private Long userId;// 
	private String icon;// 头像url
	private Date createTime;// 建立时间
	private Date updateTime;// 更新时间
	private String nickname;// 昵称
	private String appCode;// 接入的应用编号【1为杂货铺 2为水管家】
	private String appName;// 接入的app名称

	private int subscribe; // 1关注  0未关注
	
	// Empty Constructor
	public ThirdUser() {
		super();
	}
	
	// Full Constructor
	public ThirdUser(Long id, String thirdName, Integer thirdType, String openId, String unionId, Long userId, String icon, Date createTime, Date updateTime, String nickname, String appCode, String appName, int subscribe) {
		this.id =  id;
		this.thirdName =  thirdName;
		this.thirdType =  thirdType;
		this.openId =  openId;
		this.unionId =  unionId;
		this.userId =  userId;
		this.icon =  icon;
		this.createTime =  createTime;
		this.updateTime =  updateTime;
		this.nickname =  nickname;
		this.appCode =  appCode;
		this.appName =  appName;
		this.subscribe = subscribe;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public String getThirdName () {
		return this.thirdName;
	}
	
	public void setThirdName (String thirdName) {
		this.thirdName =  thirdName;
	}
	
	public Integer getThirdType () {
		return this.thirdType;
	}
	
	public void setThirdType (Integer thirdType) {
		this.thirdType =  thirdType;
	}
	
	public String getOpenId () {
		return this.openId;
	}
	
	public void setOpenId (String openId) {
		this.openId =  openId;
	}
	
	public String getUnionId () {
		return this.unionId;
	}
	
	public void setUnionId (String unionId) {
		this.unionId =  unionId;
	}
	
	public Long getUserId () {
		return this.userId;
	}
	
	public void setUserId (Long userId) {
		this.userId =  userId;
	}
	
	public String getIcon () {
		return this.icon;
	}
	
	public void setIcon (String icon) {
		this.icon =  icon;
	}
	
	public Date getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Date createTime) {
		this.createTime =  createTime;
	}
	
	public Date getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Date updateTime) {
		this.updateTime =  updateTime;
	}
	
	public String getNickname () {
		return this.nickname;
	}
	
	public void setNickname (String nickname) {
		this.nickname =  nickname;
	}
	
	public String getAppCode () {
		return this.appCode;
	}
	
	public void setAppCode (String appCode) {
		this.appCode =  appCode;
	}
	
	public String getAppName () {
		return this.appName;
	}
	
	public void setAppName (String appName) {
		this.appName =  appName;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("thirdName = " +  this.getThirdName() + ",");
		entityStirngBuffer.append("thirdType = " +  this.getThirdType() + ",");
		entityStirngBuffer.append("openId = " +  this.getOpenId() + ",");
		entityStirngBuffer.append("unionId = " +  this.getUnionId() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("icon = " +  this.getIcon() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("nickname = " +  this.getNickname() + ",");
		entityStirngBuffer.append("appCode = " +  this.getAppCode() + ",");
		entityStirngBuffer.append("appName = " +  this.getAppName() + ",");
		return entityStirngBuffer.toString();
	}
	
}