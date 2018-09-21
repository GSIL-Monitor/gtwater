package com.gt.manager.entity.wtUserExtensioncode;

import java.io.Serializable;

/**
 * 用户扫码记录表
 * @author why
 */

public class WtUserExtensioncode implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 记录ID
	private Long codeId;// 二维码ID
	private Long userId;// 用户ID
	private String userName;// 用户名称
	private Long regeditTime;// 扫码时间
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtUserExtensioncode() {
		super();
	}
	
	// Full Constructor
	public WtUserExtensioncode(Long id, Long codeId, Long userId, String userName, Long regeditTime, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.codeId =  codeId;
		this.userId =  userId;
		this.userName =  userName;
		this.regeditTime =  regeditTime;
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
	
	public Long getCodeId () {
		return this.codeId;
	}
	
	public void setCodeId (Long codeId) {
		this.codeId =  codeId;
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
	
	public Long getRegeditTime () {
		return this.regeditTime;
	}
	
	public void setRegeditTime (Long regeditTime) {
		this.regeditTime =  regeditTime;
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
		entityStirngBuffer.append("codeId = " +  this.getCodeId() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("userName = " +  this.getUserName() + ",");
		entityStirngBuffer.append("regeditTime = " +  this.getRegeditTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}