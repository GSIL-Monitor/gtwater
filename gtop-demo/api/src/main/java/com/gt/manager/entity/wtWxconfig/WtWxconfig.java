package com.gt.manager.entity.wtWxconfig;

import java.io.Serializable;

/**
 * 水管家微信配置信息表
 * @author why
 */

public class WtWxconfig implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键值
	private String accessToken;// access_token
	private String apiTicket;// api_ticket 
	private Long createTime;// 创建时间
	private Long updateTime;// 修改时间
	
	// Empty Constructor
	public WtWxconfig() {
		super();
	}
	
	// Full Constructor
	public WtWxconfig(Long id, String accessToken, String apiTicket, Long createTime, Long updateTime) {
		this.id =  id;
		this.accessToken =  accessToken;
		this.apiTicket =  apiTicket;
		this.createTime =  createTime;
		this.updateTime =  updateTime;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public String getAccessToken () {
		return this.accessToken;
	}
	
	public void setAccessToken (String accessToken) {
		this.accessToken =  accessToken;
	}
	
	public String getApiTicket () {
		return this.apiTicket;
	}
	
	public void setApiTicket (String apiTicket) {
		this.apiTicket =  apiTicket;
	}
	
	public Long getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Long createTime) {
		this.createTime =  createTime;
	}
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("accessToken = " +  this.getAccessToken() + ",");
		entityStirngBuffer.append("apiTicket = " +  this.getApiTicket() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		return entityStirngBuffer.toString();
	}
	
}