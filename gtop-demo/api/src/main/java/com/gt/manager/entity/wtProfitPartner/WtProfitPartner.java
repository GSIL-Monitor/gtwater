package com.gt.manager.entity.wtProfitPartner;

import com.gt.manager.entity.wtSku.WtSku;

import java.io.Serializable;

/**
 * 分佣(合伙人)表
 * @author why
 */

public class WtProfitPartner implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 
	private Long profitId;// 分佣ID
	private String orderCode;// 订单明细编号
	private Integer orderNum;// 订单数量
	private Long unitPrice;// 商品单价
	private String skuCode;// SKU编号
	private Long profitMoney;// 分佣金额
	private Long orderTime;// 下单时间
	private Integer proportion;// 分佣比例
	private Long branchesId;// 机构ID
	private Long partnerId;// 合伙人ID
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delStatus;// 删除状态


	// Empty Constructor
	public WtProfitPartner() {
		super();
	}
	
	// Full Constructor
	public WtProfitPartner(Long id, Long profitId, String orderCode, Integer orderNum, Long unitPrice, String skuCode, Long profitMoney, Long orderTime, Integer proportion, Long branchesId, Long partnerId, Long createTime, Long createId, Integer delStatus) {
		this.id =  id;
		this.profitId =  profitId;
		this.orderCode =  orderCode;
		this.orderNum =  orderNum;
		this.unitPrice =  unitPrice;
		this.skuCode =  skuCode;
		this.profitMoney =  profitMoney;
		this.orderTime =  orderTime;
		this.proportion =  proportion;
		this.branchesId =  branchesId;
		this.partnerId =  partnerId;
		this.createTime =  createTime;
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
	
	public Long getProfitId () {
		return this.profitId;
	}
	
	public void setProfitId (Long profitId) {
		this.profitId =  profitId;
	}
	
	public String getOrderCode () {
		return this.orderCode;
	}
	
	public void setOrderCode (String orderCode) {
		this.orderCode =  orderCode;
	}
	
	public Integer getOrderNum () {
		return this.orderNum;
	}
	
	public void setOrderNum (Integer orderNum) {
		this.orderNum =  orderNum;
	}
	
	public Long getUnitPrice () {
		return this.unitPrice;
	}
	
	public void setUnitPrice (Long unitPrice) {
		this.unitPrice =  unitPrice;
	}
	
	public String getSkuCode () {
		return this.skuCode;
	}
	
	public void setSkuCode (String skuCode) {
		this.skuCode =  skuCode;
	}
	
	public Long getProfitMoney () {
		return this.profitMoney;
	}
	
	public void setProfitMoney (Long profitMoney) {
		this.profitMoney =  profitMoney;
	}
	
	public Long getOrderTime () {
		return this.orderTime;
	}
	
	public void setOrderTime (Long orderTime) {
		this.orderTime =  orderTime;
	}
	
	public Integer getProportion () {
		return this.proportion;
	}
	
	public void setProportion (Integer proportion) {
		this.proportion =  proportion;
	}
	
	public Long getBranchesId () {
		return this.branchesId;
	}
	
	public void setBranchesId (Long branchesId) {
		this.branchesId =  branchesId;
	}
	
	public Long getPartnerId () {
		return this.partnerId;
	}
	
	public void setPartnerId (Long partnerId) {
		this.partnerId =  partnerId;
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
		entityStirngBuffer.append("profitId = " +  this.getProfitId() + ",");
		entityStirngBuffer.append("orderCode = " +  this.getOrderCode() + ",");
		entityStirngBuffer.append("orderNum = " +  this.getOrderNum() + ",");
		entityStirngBuffer.append("unitPrice = " +  this.getUnitPrice() + ",");
		entityStirngBuffer.append("skuCode = " +  this.getSkuCode() + ",");
		entityStirngBuffer.append("profitMoney = " +  this.getProfitMoney() + ",");
		entityStirngBuffer.append("orderTime = " +  this.getOrderTime() + ",");
		entityStirngBuffer.append("proportion = " +  this.getProportion() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("partnerId = " +  this.getPartnerId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delStatus = " +  this.getDelStatus() + ",");
		return entityStirngBuffer.toString();
	}
	
}