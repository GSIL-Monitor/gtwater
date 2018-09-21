package com.gt.manager.entity.wtTicketLog;

import java.io.Serializable;

/**
 * 水票消费记录表
 * @author why
 */

public class WtTicketLog implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 记录ID
	private Long ticketId;// 水票ID
	private String skuName;// SKU名称
	private String skuCode;// SKU编号
	private Integer num;// 数量
	private Long ticketPrice;// 水票单价
	private Integer operation;// 增/减 【1增、-1减】
	private Integer type;// 订单/派送单
	private Long userId;// 用户(客户)ID
	private String userName;// 用户（客户）名称
	private String address;// 地址
	private String sendMesCode;// 派送单明细code
	private String orderMesCode;// 订单明细code
	private Long logTime;// 消费时间
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtTicketLog() {
		super();
	}
	
	// Full Constructor
	public WtTicketLog(Long id, Long ticketId, String skuName, String skuCode, Integer num, Long ticketPrice,Integer operation, Integer type, Long userId, String userName, String address, String sendMesCode, String orderMesCode, Long logTime, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.ticketId =  ticketId;
		this.skuName =  skuName;
		this.skuCode =  skuCode;
		this.num =  num;
		this.ticketPrice=ticketPrice;
		this.operation =  operation;
		this.type =  type;
		this.userId =  userId;
		this.userName =  userName;
		this.address =  address;
		this.sendMesCode =  sendMesCode;
		this.orderMesCode =  orderMesCode;
		this.logTime =  logTime;
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
	
	public Long getTicketId () {
		return this.ticketId;
	}
	
	public void setTicketId (Long ticketId) {
		this.ticketId =  ticketId;
	}
	
	public String getSkuName () {
		return this.skuName;
	}
	
	public void setSkuName (String skuName) {
		this.skuName =  skuName;
	}
	
	public String getSkuCode () {
		return this.skuCode;
	}
	
	public void setSkuCode (String skuCode) {
		this.skuCode =  skuCode;
	}
	
	public Integer getNum () {
		return this.num;
	}
	
	public void setNum (Integer num) {
		this.num =  num;
	}

	public Long getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Long ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getOperation () {
		return this.operation;
	}
	
	public void setOperation (Integer operation) {
		this.operation =  operation;
	}
	
	public Integer getType () {
		return this.type;
	}
	
	public void setType (Integer type) {
		this.type =  type;
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
	
	public String getAddress () {
		return this.address;
	}
	
	public void setAddress (String address) {
		this.address =  address;
	}
	
	public String getSendMesCode () {
		return this.sendMesCode;
	}
	
	public void setSendMesCode (String sendMesCode) {
		this.sendMesCode =  sendMesCode;
	}
	
	public String getOrderMesCode () {
		return this.orderMesCode;
	}
	
	public void setOrderMesCode (String orderMesCode) {
		this.orderMesCode =  orderMesCode;
	}
	
	public Long getLogTime () {
		return this.logTime;
	}
	
	public void setLogTime (Long logTime) {
		this.logTime =  logTime;
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
		entityStirngBuffer.append("ticketId = " +  this.getTicketId() + ",");
		entityStirngBuffer.append("skuName = " +  this.getSkuName() + ",");
		entityStirngBuffer.append("skuCode = " +  this.getSkuCode() + ",");
		entityStirngBuffer.append("num = " +  this.getNum() + ",");
		entityStirngBuffer.append("operation = " +  this.getOperation() + ",");
		entityStirngBuffer.append("type = " +  this.getType() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("userName = " +  this.getUserName() + ",");
		entityStirngBuffer.append("address = " +  this.getAddress() + ",");
		entityStirngBuffer.append("sendMesCode = " +  this.getSendMesCode() + ",");
		entityStirngBuffer.append("orderMesCode = " +  this.getOrderMesCode() + ",");
		entityStirngBuffer.append("logTime = " +  this.getLogTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}