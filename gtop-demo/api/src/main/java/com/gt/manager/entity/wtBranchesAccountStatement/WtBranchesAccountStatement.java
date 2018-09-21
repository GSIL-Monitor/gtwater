package com.gt.manager.entity.wtBranchesAccountStatement;

import java.io.Serializable;

/**
 * 钱包操作记录表
 * @author why
 */

public class WtBranchesAccountStatement implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 
	private Long branchesAccountId;// 
	private Integer userType;// 用户类型[1平台、2城市机构、3水站、4合伙人]
	private Long branchesId;// 充值/提现机构id
	private Long userId;// 充值/提现合伙人id
	private Long money;// 金额
	private Integer type;// 操作类型1分佣，2提现
	private Long payTime;// 到账/打款时间
	private String transactionNumber;// 
	private Integer payStatus;// 交易状态[1成功，-1 失败]
	private String failMes;// 失败原因
	private Long createId;// 
	private Long createTime;// 
	private Long updateId;// 
	private Long updateTime;// 
	private Integer delState;// 删除状态 1正常、0删除

	//筛选条件
	private Long startTime;   //开始时间
	private Long endTime;     //结束时间
	
	// Empty Constructor
	public WtBranchesAccountStatement() {
		super();
	}
	
	// Full Constructor
	public WtBranchesAccountStatement(Long id, Long branchesAccountId, Integer userType, Long branchesId, Long userId, Long money, Integer type, Long payTime, String transactionNumber, Integer payStatus, String failMes, Long createId, Long createTime, Long updateId, Long updateTime, Integer delState) {
		this.id =  id;
		this.branchesAccountId =  branchesAccountId;
		this.userType =  userType;
		this.branchesId =  branchesId;
		this.userId =  userId;
		this.money =  money;
		this.type =  type;
		this.payTime =  payTime;
		this.transactionNumber =  transactionNumber;
		this.payStatus =  payStatus;
		this.failMes =  failMes;
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
	
	public Long getBranchesAccountId () {
		return this.branchesAccountId;
	}
	
	public void setBranchesAccountId (Long branchesAccountId) {
		this.branchesAccountId =  branchesAccountId;
	}
	
	public Integer getUserType () {
		return this.userType;
	}
	
	public void setUserType (Integer userType) {
		this.userType =  userType;
	}
	
	public Long getBranchesId () {
		return this.branchesId;
	}
	
	public void setBranchesId (Long branchesId) {
		this.branchesId =  branchesId;
	}
	
	public Long getUserId () {
		return this.userId;
	}
	
	public void setUserId (Long userId) {
		this.userId =  userId;
	}
	
	public Long getMoney () {
		return this.money;
	}
	
	public void setMoney (Long money) {
		this.money =  money;
	}
	
	public Integer getType () {
		return this.type;
	}
	
	public void setType (Integer type) {
		this.type =  type;
	}
	
	public Long getPayTime () {
		return this.payTime;
	}
	
	public void setPayTime (Long payTime) {
		this.payTime =  payTime;
	}
	
	public String getTransactionNumber () {
		return this.transactionNumber;
	}
	
	public void setTransactionNumber (String transactionNumber) {
		this.transactionNumber =  transactionNumber;
	}
	
	public Integer getPayStatus () {
		return this.payStatus;
	}
	
	public void setPayStatus (Integer payStatus) {
		this.payStatus =  payStatus;
	}
	
	public String getFailMes () {
		return this.failMes;
	}
	
	public void setFailMes (String failMes) {
		this.failMes =  failMes;
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

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("branchesAccountId = " +  this.getBranchesAccountId() + ",");
		entityStirngBuffer.append("userType = " +  this.getUserType() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("money = " +  this.getMoney() + ",");
		entityStirngBuffer.append("type = " +  this.getType() + ",");
		entityStirngBuffer.append("payTime = " +  this.getPayTime() + ",");
		entityStirngBuffer.append("transactionNumber = " +  this.getTransactionNumber() + ",");
		entityStirngBuffer.append("payStatus = " +  this.getPayStatus() + ",");
		entityStirngBuffer.append("failMes = " +  this.getFailMes() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateId = " +  this.getUpdateId() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}