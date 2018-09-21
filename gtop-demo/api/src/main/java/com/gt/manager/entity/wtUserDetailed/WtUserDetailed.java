package com.gt.manager.entity.wtUserDetailed;

import java.io.Serializable;

/**
 * 用户发票信息表
 * @author why
 */

public class WtUserDetailed implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// ID
	private Long userId;// user_id
	private String title;// title
	private String dutyNo;// 税号
	private Integer isDefault;// 是否默认【1是、0否】
	private String remarks;// 备注
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtUserDetailed() {
		super();
	}
	
	// Full Constructor
	public WtUserDetailed(Long id, Long userId, String title, String dutyNo, Integer isDefault, String remarks, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.userId =  userId;
		this.title =  title;
		this.dutyNo =  dutyNo;
		this.isDefault =  isDefault;
		this.remarks =  remarks;
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
	
	public Long getUserId () {
		return this.userId;
	}
	
	public void setUserId (Long userId) {
		this.userId =  userId;
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
	
	public Integer getIsDefault () {
		return this.isDefault;
	}
	
	public void setIsDefault (Integer isDefault) {
		this.isDefault =  isDefault;
	}
	
	public String getRemarks () {
		return this.remarks;
	}
	
	public void setRemarks (String remarks) {
		this.remarks =  remarks;
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
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("title = " +  this.getTitle() + ",");
		entityStirngBuffer.append("dutyNo = " +  this.getDutyNo() + ",");
		entityStirngBuffer.append("isDefault = " +  this.getIsDefault() + ",");
		entityStirngBuffer.append("remarks = " +  this.getRemarks() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}