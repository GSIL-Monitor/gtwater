package com.gt.manager.entity.wtUserTicket;

import java.io.Serializable;

/**
 * 用户水票表
 * @author why
 */

public class WtUserTicket implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 用户水票ID
	private Long userId;// 用户ID
	private Integer num;// 水票数量
	private Integer surplusNum;// 水票余量
	private Long ticketPrice;// 水票单价
	private String skuCode;// skucode
	private String orderCode;// 
	private Long orderTime;// 
	private Long updateTime;// 
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	private Long branchesId;//
	
	// Empty Constructor
	public WtUserTicket() {
		super();
	}
	
	// Full Constructor
	public WtUserTicket(Long id, Long userId, Integer num, Integer surplusNum, Long ticketPrice, String skuCode, String orderCode, Long orderTime, Long updateTime, Long createTime, Long createId, Integer delState, Long branchesId) {
		this.id =  id;
		this.userId =  userId;
		this.num =  num;
		this.surplusNum =  surplusNum;
		this.ticketPrice =  ticketPrice;
		this.skuCode =  skuCode;
		this.orderCode =  orderCode;
		this.orderTime =  orderTime;
		this.updateTime =  updateTime;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delState =  delState;
		this.branchesId = branchesId;
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
	
	public Integer getNum () {
		return this.num;
	}
	
	public void setNum (Integer num) {
		this.num =  num;
	}
	
	public Integer getSurplusNum () {
		return this.surplusNum;
	}
	
	public void setSurplusNum (Integer surplusNum) {
		this.surplusNum =  surplusNum;
	}
	
	public Long getTicketPrice () {
		return this.ticketPrice;
	}
	
	public void setTicketPrice (Long ticketPrice) {
		this.ticketPrice =  ticketPrice;
	}
	
	public String getSkuCode () {
		return this.skuCode;
	}
	
	public void setSkuCode (String skuCode) {
		this.skuCode =  skuCode;
	}
	
	public String getOrderCode () {
		return this.orderCode;
	}
	
	public void setOrderCode (String orderCode) {
		this.orderCode =  orderCode;
	}
	
	public Long getOrderTime () {
		return this.orderTime;
	}
	
	public void setOrderTime (Long orderTime) {
		this.orderTime =  orderTime;
	}
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
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

	public Long getBranchesId(){return this.branchesId;}

	public void setBranchesId(Long branchesId){this.branchesId = branchesId;}
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("num = " +  this.getNum() + ",");
		entityStirngBuffer.append("surplusNum = " +  this.getSurplusNum() + ",");
		entityStirngBuffer.append("ticketPrice = " +  this.getTicketPrice() + ",");
		entityStirngBuffer.append("skuCode = " +  this.getSkuCode() + ",");
		entityStirngBuffer.append("orderCode = " +  this.getOrderCode() + ",");
		entityStirngBuffer.append("orderTime = " +  this.getOrderTime() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}