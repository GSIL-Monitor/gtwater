package com.gt.manager.entity.wtProfit;

import com.gt.manager.entity.wtProfitPartner.WtProfitPartner;

import java.io.Serializable;
import java.util.List;

/**
 * 分佣表
 * @author why
 */

public class WtProfit implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 
	private String sendNo;// 派送编号
	private Long userId;// 用户ID
	private Long waterstoreId;// 水站ID
	private Long partnerId;// 合伙人ID
	private Integer sendNum;// 派送总量
	private Long profitMoney;// 派单金额
	private Long paymentTime;// 付款时间
	private Long sendTime;// 派送时间
	private Long createTime;// 创建时间


	// Empty Constructor
	public WtProfit() {
		super();
	}
	
	// Full Constructor
	public WtProfit(Long id, String sendNo, Long userId, Long waterstoreId, Long partnerId, Integer sendNum, Long profitMoney, Long paymentTime, Long sendTime, Long createTime) {
		this.id =  id;
		this.sendNo =  sendNo;
		this.userId =  userId;
		this.waterstoreId =  waterstoreId;
		this.partnerId =  partnerId;
		this.sendNum =  sendNum;
		this.profitMoney =  profitMoney;
		this.paymentTime =  paymentTime;
		this.sendTime =  sendTime;
		this.createTime =  createTime;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public String getSendNo () {
		return this.sendNo;
	}
	
	public void setSendNo (String sendNo) {
		this.sendNo =  sendNo;
	}
	
	public Long getUserId () {
		return this.userId;
	}
	
	public void setUserId (Long userId) {
		this.userId =  userId;
	}
	
	public Long getWaterstoreId () {
		return this.waterstoreId;
	}
	
	public void setWaterstoreId (Long waterstoreId) {
		this.waterstoreId =  waterstoreId;
	}
	
	public Long getPartnerId () {
		return this.partnerId;
	}
	
	public void setPartnerId (Long partnerId) {
		this.partnerId =  partnerId;
	}
	
	public Integer getSendNum () {
		return this.sendNum;
	}
	
	public void setSendNum (Integer sendNum) {
		this.sendNum =  sendNum;
	}
	
	public Long getProfitMoney () {
		return this.profitMoney;
	}
	
	public void setProfitMoney (Long profitMoney) {
		this.profitMoney =  profitMoney;
	}
	
	public Long getPaymentTime () {
		return this.paymentTime;
	}
	
	public void setPaymentTime (Long paymentTime) {
		this.paymentTime =  paymentTime;
	}
	
	public Long getSendTime () {
		return this.sendTime;
	}
	
	public void setSendTime (Long sendTime) {
		this.sendTime =  sendTime;
	}
	
	public Long getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Long createTime) {
		this.createTime =  createTime;
	}



	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("sendNo = " +  this.getSendNo() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("partnerId = " +  this.getPartnerId() + ",");
		entityStirngBuffer.append("sendNum = " +  this.getSendNum() + ",");
		entityStirngBuffer.append("profitMoney = " +  this.getProfitMoney() + ",");
		entityStirngBuffer.append("paymentTime = " +  this.getPaymentTime() + ",");
		entityStirngBuffer.append("sendTime = " +  this.getSendTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		return entityStirngBuffer.toString();
	}
	
}