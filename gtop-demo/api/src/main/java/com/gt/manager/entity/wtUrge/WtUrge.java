package com.gt.manager.entity.wtUrge;

import java.io.Serializable;

/**
 * 催单表
 * @author why
 */

public class WtUrge implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 催单ID
	private Long userId;// 用户（客户）ID
	private String userName;// 用户（客户）名称
	private Long sendId;// 派单ID
	private String remarks;// 备注
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态
	
	// Empty Constructor
	public WtUrge() {
		super();
	}
	
	// Full Constructor
	public WtUrge(Long id, Long userId, String userName, Long sendId, String remarks, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.userId =  userId;
		this.userName =  userName;
		this.sendId =  sendId;
		this.remarks =  remarks;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delState =  delState;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Long getUserId () {
		return this.userId;
	}
	
	public void setUserId (Long userId) {
		this.userId =  userId;
	}
	
	public String getUserName () {
		return this.userName;
	}
	
	public void setUserName (String userName) {
		this.userName =  userName;
	}
	
	public Long getSendId () {
		return this.sendId;
	}
	
	public void setSendId (Long sendId) {
		this.sendId =  sendId;
	}
	
	public String getRemarks () {
		return this.remarks;
	}
	
	public void setRemarks (String remarks) {
		this.remarks =  remarks;
	}
	
	public Long getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Long createTime) {
		this.createTime =  createTime;
	}
	
	public Long getCreateId () {
		return this.createId;
	}
	
	public void setCreateId (Long createId) {
		this.createId =  createId;
	}
	
	public Integer getDelState () {
		return this.delState;
	}
	
	public void setDelState (Integer delState) {
		this.delState =  delState;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("userName = " +  this.getUserName() + ",");
		entityStirngBuffer.append("sendId = " +  this.getSendId() + ",");
		entityStirngBuffer.append("remarks = " +  this.getRemarks() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}