package com.gt.manager.entity.wtFunction;

import java.io.Serializable;

/**
 * 水管家功能开关表
 * @author why
 */

public class WtFunction implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键
	private Integer type;// 1：发票 2：待定
	private Integer state;// 1：开 0：关
	private Long createTime;// 创建时间
	private Long createId;// 创建人id
	private Long updateTime;// 修改时间
	private Long updateId;// 修改人id
	
	// Empty Constructor
	public WtFunction() {
		super();
	}
	
	// Full Constructor
	public WtFunction(Long id, Integer type, Integer state, Long createTime, Long createId, Long updateTime, Long updateId) {
		this.id =  id;
		this.type =  type;
		this.state =  state;
		this.createTime =  createTime;
		this.createId =  createId;
		this.updateTime =  updateTime;
		this.updateId =  updateId;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Integer getType () {
		return this.type;
	}
	
	public void setType (Integer type) {
		this.type =  type;
	}
	
	public Integer getState () {
		return this.state;
	}
	
	public void setState (Integer state) {
		this.state =  state;
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
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
	}
	
	public Long getUpdateId () {
		return this.updateId;
	}
	
	public void setUpdateId (Long updateId) {
		this.updateId =  updateId;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("type = " +  this.getType() + ",");
		entityStirngBuffer.append("state = " +  this.getState() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("updateId = " +  this.getUpdateId() + ",");
		return entityStirngBuffer.toString();
	}
	
}