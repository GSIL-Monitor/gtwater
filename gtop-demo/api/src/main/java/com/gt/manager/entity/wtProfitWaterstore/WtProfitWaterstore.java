package com.gt.manager.entity.wtProfitWaterstore;

import java.io.Serializable;

/**
 * 分佣(水站)表
 * @author why
 */

public class WtProfitWaterstore implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键
	private Long profitId;// 分佣ID
	private String orderCode;// 订单明细编号
	private Integer orderNum;// 订单数量
	private Long unitPrice;// 商品单价
	private Long waterstoreId;// 水站ID
	private Long branchesId;// 开放平台水站ID
	private String waterName;// 水站名称
	private String waterstoreNo;// 水站编号
	private String skuCode;// SKU编号
	private Integer proportion;// 分佣比例
	private Long profitMoney;// 分佣金额
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delStatus;// 删除状态
	
	// Empty Constructor
	public WtProfitWaterstore() {
		super();
	}
	
	// Full Constructor
	public WtProfitWaterstore(Long id, Long profitId, String orderCode, Integer orderNum, Long unitPrice, Long waterstoreId, Long branchesId, String waterName, String waterstoreNo, String skuCode, Integer proportion, Long profitMoney, Long createTime, Long createId, Integer delStatus) {
		this.id =  id;
		this.profitId =  profitId;
		this.orderCode =  orderCode;
		this.orderNum =  orderNum;
		this.unitPrice =  unitPrice;
		this.waterstoreId =  waterstoreId;
		this.branchesId =  branchesId;
		this.waterName =  waterName;
		this.waterstoreNo =  waterstoreNo;
		this.skuCode =  skuCode;
		this.proportion =  proportion;
		this.profitMoney =  profitMoney;
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
	
	public Long getWaterstoreId () {
		return this.waterstoreId;
	}
	
	public void setWaterstoreId (Long waterstoreId) {
		this.waterstoreId =  waterstoreId;
	}
	
	public Long getBranchesId () {
		return this.branchesId;
	}
	
	public void setBranchesId (Long branchesId) {
		this.branchesId =  branchesId;
	}
	
	public String getWaterName () {
		return this.waterName;
	}
	
	public void setWaterName (String waterName) {
		this.waterName =  waterName;
	}
	
	public String getWaterstoreNo () {
		return this.waterstoreNo;
	}
	
	public void setWaterstoreNo (String waterstoreNo) {
		this.waterstoreNo =  waterstoreNo;
	}
	
	public String getSkuCode () {
		return this.skuCode;
	}
	
	public void setSkuCode (String skuCode) {
		this.skuCode =  skuCode;
	}
	
	public Integer getProportion () {
		return this.proportion;
	}
	
	public void setProportion (Integer proportion) {
		this.proportion =  proportion;
	}
	
	public Long getProfitMoney () {
		return this.profitMoney;
	}
	
	public void setProfitMoney (Long profitMoney) {
		this.profitMoney =  profitMoney;
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
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("waterName = " +  this.getWaterName() + ",");
		entityStirngBuffer.append("waterstoreNo = " +  this.getWaterstoreNo() + ",");
		entityStirngBuffer.append("skuCode = " +  this.getSkuCode() + ",");
		entityStirngBuffer.append("proportion = " +  this.getProportion() + ",");
		entityStirngBuffer.append("profitMoney = " +  this.getProfitMoney() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delStatus = " +  this.getDelStatus() + ",");
		return entityStirngBuffer.toString();
	}
	
}