package com.gt.manager.entity.wtBranchesAccount;

import java.io.Serializable;

/**
 * 钱包表
 * @author why
 */

public class WtBranchesAccount implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 
	private Long userId;// 充值/提现合伙人id
	private Long branchesId;// 
	private Integer userType;// 用户类型[1平台、2城市机构、3水站、4合伙人]
	private Long account;// 
	private Integer status;// 账户状态1正常2清算3异常',
	private Long createId;// 
	private Long createTime;// 
	private Long updateId;// 
	private Long updateTime;// 
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtBranchesAccount() {
		super();
	}
	
	// Full Constructor
	public WtBranchesAccount(Long id, Long userId, Long branchesId, Integer userType, Long account, Integer status, Long createId, Long createTime, Long updateId, Long updateTime, Integer delState) {
		this.id =  id;
		this.userId =  userId;
		this.branchesId =  branchesId;
		this.userType =  userType;
		this.account =  account;
		this.status =  status;
		this.createId =  createId;
		this.createTime =  createTime;
		this.updateId =  updateId;
		this.updateTime =  updateTime;
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
	
	public Long getBranchesId () {
		return this.branchesId;
	}
	
	public void setBranchesId (Long branchesId) {
		this.branchesId =  branchesId;
	}
	
	public Integer getUserType () {
		return this.userType;
	}
	
	public void setUserType (Integer userType) {
		this.userType =  userType;
	}
	
	public Long getAccount () {
		return this.account;
	}
	
	public void setAccount (Long account) {
		this.account =  account;
	}
	
	public Integer getStatus () {
		return this.status;
	}
	
	public void setStatus (Integer status) {
		this.status =  status;
	}
	
	public Long getCreateId () {
		return this.createId;
	}
	
	public void setCreateId (Long createId) {
		this.createId =  createId;
	}
	
	public Long getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Long createTime) {
		this.createTime =  createTime;
	}
	
	public Long getUpdateId () {
		return this.updateId;
	}
	
	public void setUpdateId (Long updateId) {
		this.updateId =  updateId;
	}
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
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
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("userType = " +  this.getUserType() + ",");
		entityStirngBuffer.append("account = " +  this.getAccount() + ",");
		entityStirngBuffer.append("status = " +  this.getStatus() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateId = " +  this.getUpdateId() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}