package com.gt.manager.entity.sysRegion;

import java.io.Serializable;

/**
 * 省市区实体类
 * @author why
 */

public class SysRegion implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private String regionId;// 主键值
	private String regionName;// 区域名称
	private String regionCode;// 区域code
	private String isParent;// 是否为父级区域
	
	// Empty Constructor
	public SysRegion() {
		super();
	}
	
	// Full Constructor
	public SysRegion(String regionId, String regionName, String regionCode, String isParent) {
		this.regionId =  regionId;
		this.regionName =  regionName;
		this.regionCode =  regionCode;
		this.isParent =  isParent;
	}

	// Property accessors

	public String getRegionId () {
		return this.regionId;
	}
	
	public void setRegionId (String regionId) {
		this.regionId =  regionId;
	}
	
	public String getRegionName () {
		return this.regionName;
	}
	
	public void setRegionName (String regionName) {
		this.regionName =  regionName;
	}
	
	public String getRegionCode () {
		return this.regionCode;
	}
	
	public void setRegionCode (String regionCode) {
		this.regionCode =  regionCode;
	}
	
	public String getIsParent () {
		return this.isParent;
	}
	
	public void setIsParent (String isParent) {
		this.isParent =  isParent;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("regionId = " +  this.getRegionId() + ",");
		entityStirngBuffer.append("regionName = " +  this.getRegionName() + ",");
		entityStirngBuffer.append("regionCode = " +  this.getRegionCode() + ",");
		entityStirngBuffer.append("isParent = " +  this.getIsParent() + ",");
		return entityStirngBuffer.toString();
	}
	
}