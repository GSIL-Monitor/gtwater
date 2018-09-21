package com.gt.manager.entity.wtCycle;

import java.io.Serializable;

/**
 * 周期订单
 * @author why
 */

public class WtCycle implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long cycleId;// 周期ID
	private String userName;// 用户名称
	private Long userId;// 用户ID
	private Long sendId;// 派送单ID
	private Long orderTime;// 下单时间
	private Long sendTimeStart;// 开始派送时间
	private Long sendTimeEnd;// 最后一次时间
	private String company;// 公司名称
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtCycle() {
		super();
	}
	
	// Full Constructor
	public WtCycle(Long cycleId, String userName, Long userId, Long sendId, Long orderTime, Long sendTimeStart, Long sendTimeEnd, String company, Long createTime, Long createId, Integer delState) {
		this.cycleId =  cycleId;
		this.userName =  userName;
		this.userId =  userId;
		this.sendId =  sendId;
		this.orderTime =  orderTime;
		this.sendTimeStart =  sendTimeStart;
		this.sendTimeEnd =  sendTimeEnd;
		this.company =  company;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delState =  delState;
	}

	// Property accessors

	public Long getCycleId () {
		return this.cycleId;
	}
	
	public void setCycleId (Long cycleId) {
		this.cycleId =  cycleId;
	}
	
	public String getUserName () {
		return this.userName;
	}
	
	public void setUserName (String userName) {
		this.userName =  userName;
	}
	
	public Long getUserId () {
		return this.userId;
	}
	
	public void setUserId (Long userId) {
		this.userId =  userId;
	}
	
	public Long getSendId () {
		return this.sendId;
	}
	
	public void setSendId (Long sendId) {
		this.sendId =  sendId;
	}
	
	public Long getOrderTime () {
		return this.orderTime;
	}
	
	public void setOrderTime (Long orderTime) {
		this.orderTime =  orderTime;
	}
	
	public Long getSendTimeStart () {
		return this.sendTimeStart;
	}
	
	public void setSendTimeStart (Long sendTimeStart) {
		this.sendTimeStart =  sendTimeStart;
	}
	
	public Long getSendTimeEnd () {
		return this.sendTimeEnd;
	}
	
	public void setSendTimeEnd (Long sendTimeEnd) {
		this.sendTimeEnd =  sendTimeEnd;
	}
	
	public String getCompany () {
		return this.company;
	}
	
	public void setCompany (String company) {
		this.company =  company;
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
		entityStirngBuffer.append("cycleId = " +  this.getCycleId() + ",");
		entityStirngBuffer.append("userName = " +  this.getUserName() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("sendId = " +  this.getSendId() + ",");
		entityStirngBuffer.append("orderTime = " +  this.getOrderTime() + ",");
		entityStirngBuffer.append("sendTimeStart = " +  this.getSendTimeStart() + ",");
		entityStirngBuffer.append("sendTimeEnd = " +  this.getSendTimeEnd() + ",");
		entityStirngBuffer.append("company = " +  this.getCompany() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}