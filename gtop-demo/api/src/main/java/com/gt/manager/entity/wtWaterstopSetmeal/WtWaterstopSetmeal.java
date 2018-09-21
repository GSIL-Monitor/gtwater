package com.gt.manager.entity.wtWaterstopSetmeal;

import java.io.Serializable;

/**
 * 水站套餐
 * @author why
 */

public class WtWaterstopSetmeal implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键
	private Long setmealId;// 机构套餐ID
	private Long branchesId;// 机构(水站)ID
	private Long waterstoreId;// 
	private Integer onshelfState;// 上架状态【1上架、0下架】
	private Long onshelfTime;// 上架时间
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	private String seriesSkuCode;// 套系sku编号

	// Empty Constructor
	public WtWaterstopSetmeal() {
		super();
	}
	
	// Full Constructor
	public WtWaterstopSetmeal(Long id, Long setmealId, Long branchesId, Long waterstoreId, Integer onshelfState, Long onshelfTime, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.setmealId =  setmealId;
		this.branchesId =  branchesId;
		this.waterstoreId =  waterstoreId;
		this.onshelfState =  onshelfState;
		this.onshelfTime =  onshelfTime;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delState =  delState;
	}

	// Property accessors


	public String getSeriesSkuCode() {
		return seriesSkuCode;
	}

	public void setSeriesSkuCode(String seriesSkuCode) {
		this.seriesSkuCode = seriesSkuCode;
	}

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Long getSetmealId () {
		return this.setmealId;
	}
	
	public void setSetmealId (Long setmealId) {
		this.setmealId =  setmealId;
	}
	
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
	
	public Integer getOnshelfState () {
		return this.onshelfState;
	}
	
	public void setOnshelfState (Integer onshelfState) {
		this.onshelfState =  onshelfState;
	}
	
	public Long getOnshelfTime () {
		return this.onshelfTime;
	}
	
	public void setOnshelfTime (Long onshelfTime) {
		this.onshelfTime =  onshelfTime;
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
		entityStirngBuffer.append("setmealId = " +  this.getSetmealId() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("onshelfState = " +  this.getOnshelfState() + ",");
		entityStirngBuffer.append("onshelfTime = " +  this.getOnshelfTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("seriesSkuCode = " +  this.getSeriesSkuCode() + ",");
		return entityStirngBuffer.toString();
	}
	
}