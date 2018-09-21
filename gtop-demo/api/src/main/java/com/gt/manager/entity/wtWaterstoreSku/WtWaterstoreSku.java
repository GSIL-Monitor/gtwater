package com.gt.manager.entity.wtWaterstoreSku;

import java.io.Serializable;

/**
 * 水站商品sku表
 * @author why
 */

public class WtWaterstoreSku implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键
	private Long skuId;// 城市商品ID(sku表中的ID)
	private String skuCode;//sku编码
	private Long branchesId;// 机构(水站)ID
	private Long waterstoreId;//
	private Integer status;// 状态[1销售中（上架），2下架]
	private Long shelfOnTime;// 上架时间
	private String shellOffReason;// 下架原因
	private Long createId;// 创建者
	private Long createTime;// 创建时间
	private Long updateId;// 修改者
	private Long updateTime;// 修改时间
	private Integer delState;// 删除状态【1正常，0删除】
	private Long version;// 数据版本

	// Empty Constructor
	public WtWaterstoreSku() {
		super();
	}
	
	// Full Constructor
	public WtWaterstoreSku(Long id, Long skuId, String skuCode, Long branchesId, Long waterstoreId, Integer status, Long shelfOnTime, String shellOffReason, Long createId, Long createTime, Long updateId, Long updateTime, Integer delState, Long version) {
		this.id =  id;
		this.skuId =  skuId;
		this.skuCode = skuCode;
		this.branchesId =  branchesId;
		this.waterstoreId =  waterstoreId;
		this.status =  status;
		this.shelfOnTime =  shelfOnTime;
		this.shellOffReason =  shellOffReason;
		this.createId =  createId;
		this.createTime =  createTime;
		this.updateId =  updateId;
		this.updateTime =  updateTime;
		this.delState =  delState;
		this.version =  version;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Long getSkuId () {
		return this.skuId;
	}
	
	public void setSkuId (Long skuId) {
		this.skuId =  skuId;
	}

	public String getSkuCode(){return this.skuCode;}

	public void setSkuCode(String skuCode){this.skuCode = skuCode;}

	public Long getBranchesId () {
		return this.branchesId;
	}
	
	public void setBranchesId (Long branchesId) {
		this.branchesId =  branchesId;
	}
	
	public Long getWaterstoreId () {
		return this.waterstoreId;
	}
	
	public void setWaterstoreId (Long waterstoreId) {
		this.waterstoreId =  waterstoreId;
	}
	
	public Integer getStatus () {
		return this.status;
	}
	
	public void setStatus (Integer status) {
		this.status =  status;
	}
	
	public Long getShelfOnTime () {
		return this.shelfOnTime;
	}
	
	public void setShelfOnTime (Long shelfOnTime) {
		this.shelfOnTime =  shelfOnTime;
	}
	
	public String getShellOffReason () {
		return this.shellOffReason;
	}
	
	public void setShellOffReason (String shellOffReason) {
		this.shellOffReason =  shellOffReason;
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
	
	public Long getVersion () {
		return this.version;
	}
	
	public void setVersion (Long version) {
		this.version =  version;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("skuId = " +  this.getSkuId() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("status = " +  this.getStatus() + ",");
		entityStirngBuffer.append("shelfOnTime = " +  this.getShelfOnTime() + ",");
		entityStirngBuffer.append("shellOffReason = " +  this.getShellOffReason() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateId = " +  this.getUpdateId() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("version = " +  this.getVersion() + ",");
		return entityStirngBuffer.toString();
	}
	
}