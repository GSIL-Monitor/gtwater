package com.gt.manager.entity.wtExtensioncode;

import java.io.Serializable;

/**
 * 推广码信息表
 * @author why
 */

public class WtExtensioncode implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 推广码ID
	private String messages;// 推广码内容
	private String url;//扫描推广码打开页面url，参数messages
	private Long partnerId;// 绑定合伙人ID
	private Long registerTime;// 绑定时间
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除

	private Long startTime;

	private Long endTime;
	
	// Empty Constructor
	public WtExtensioncode() {
		super();
	}
	
	// Full Constructor
	public WtExtensioncode(Long id, String messages, String url,Long partnerId, Long registerTime, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.messages =  messages;
		this.url = url;
		this.partnerId =  partnerId;
		this.registerTime =  registerTime;
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
	
	public String getMessages () {
		return this.messages;
	}
	
	public void setMessages (String messages) {
		this.messages =  messages;
	}

	public String getUrl(){return this.url;}

	public void setUrl(String url){ this.url = url;}
	
	public Long getPartnerId () {
		return this.partnerId;
	}
	
	public void setPartnerId (Long partnerId) {
		this.partnerId =  partnerId;
	}
	
	public Long getRegisterTime () {
		return this.registerTime;
	}
	
	public void setRegisterTime (Long registerTime) {
		this.registerTime =  registerTime;
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

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("messages = " +  this.getMessages() + ",");
		entityStirngBuffer.append("url = " +  this.getUrl() + ",");
		entityStirngBuffer.append("partnerId = " +  this.getPartnerId() + ",");
		entityStirngBuffer.append("registerTime = " +  this.getRegisterTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}