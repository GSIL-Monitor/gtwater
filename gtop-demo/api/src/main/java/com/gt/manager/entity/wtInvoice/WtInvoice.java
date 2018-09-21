package com.gt.manager.entity.wtInvoice;

import java.io.Serializable;

/**
 * 发票
 * @author why
 */

public class WtInvoice implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 发票ID
	private String orderCode;// 订单ID
	private Long userId;// 用户（客户）ID
	private Long money;// 发票金额
	private Long invoiceDate;// 开票日期
	private Long waterstoreId;// 水站ID
	private Integer state;// 发票状态【1未送达、2已送达】
	private Long signDate;// 签收日期
	private String signName;// 签收人
	private String title;// 发票台头
	private String dutyNo;// 税号
	private String sendName;// 派送人
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态
	
	// Empty Constructor
	public WtInvoice() {
		super();
	}
	
	// Full Constructor
	public WtInvoice(Long id, String orderCode, Long userId, Long money, Long invoiceDate, Long waterstoreId, Integer state, Long signDate, String signName, String title, String dutyNo, String sendName, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.orderCode =  orderCode;
		this.userId =  userId;
		this.money =  money;
		this.invoiceDate =  invoiceDate;
		this.waterstoreId =  waterstoreId;
		this.state =  state;
		this.signDate =  signDate;
		this.signName =  signName;
		this.title =  title;
		this.dutyNo =  dutyNo;
		this.sendName =  sendName;
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
	
	public String getOrderCode () {
		return this.orderCode;
	}
	
	public void setOrderCode (String orderCode) {
		this.orderCode =  orderCode;
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
	
	public Long getInvoiceDate () {
		return this.invoiceDate;
	}
	
	public void setInvoiceDate (Long invoiceDate) {
		this.invoiceDate =  invoiceDate;
	}
	
	public Long getWaterstoreId () {
		return this.waterstoreId;
	}
	
	public void setWaterstoreId (Long waterstoreId) {
		this.waterstoreId =  waterstoreId;
	}
	
	public Integer getState () {
		return this.state;
	}
	
	public void setState (Integer state) {
		this.state =  state;
	}
	
	public Long getSignDate () {
		return this.signDate;
	}
	
	public void setSignDate (Long signDate) {
		this.signDate =  signDate;
	}
	
	public String getSignName () {
		return this.signName;
	}
	
	public void setSignName (String signName) {
		this.signName =  signName;
	}
	
	public String getTitle () {
		return this.title;
	}
	
	public void setTitle (String title) {
		this.title =  title;
	}
	
	public String getDutyNo () {
		return this.dutyNo;
	}
	
	public void setDutyNo (String dutyNo) {
		this.dutyNo =  dutyNo;
	}
	
	public String getSendName () {
		return this.sendName;
	}
	
	public void setSendName (String sendName) {
		this.sendName =  sendName;
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
		entityStirngBuffer.append("orderCode = " +  this.getOrderCode() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("money = " +  this.getMoney() + ",");
		entityStirngBuffer.append("invoiceDate = " +  this.getInvoiceDate() + ",");
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("state = " +  this.getState() + ",");
		entityStirngBuffer.append("signDate = " +  this.getSignDate() + ",");
		entityStirngBuffer.append("signName = " +  this.getSignName() + ",");
		entityStirngBuffer.append("title = " +  this.getTitle() + ",");
		entityStirngBuffer.append("dutyNo = " +  this.getDutyNo() + ",");
		entityStirngBuffer.append("sendName = " +  this.getSendName() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}