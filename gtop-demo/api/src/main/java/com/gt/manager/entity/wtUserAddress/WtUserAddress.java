package com.gt.manager.entity.wtUserAddress;

import java.io.Serializable;

/**
 * 用户地址表
 * @author why
 */

public class WtUserAddress implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// ID
	private Long userId;// user_id
	private String title;// title
	private String address;// 地址
	private Integer isDefault;// 是否默认【1默认、0否】
	private String provinceId;// 省ID
	private String province;// 省
	private String cityId;// 市ID
	private String city;// 市
	private String areaId;// 区ID
	private String area;// 区
	private String remark;// 备注
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtUserAddress() {
		super();
	}
	
	// Full Constructor
	public WtUserAddress(Long id, Long userId, String title, String address, Integer isDefault, String provinceId, String province, String cityId, String city, String areaId, String area, String remark, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.userId =  userId;
		this.title =  title;
		this.address =  address;
		this.isDefault =  isDefault;
		this.provinceId =  provinceId;
		this.province =  province;
		this.cityId =  cityId;
		this.city =  city;
		this.areaId =  areaId;
		this.area =  area;
		this.remark =  remark;
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
	
	public String getAddress () {
		return this.address;
	}
	
	public void setAddress (String address) {
		this.address =  address;
	}
	
	public Integer getIsDefault () {
		return this.isDefault;
	}
	
	public void setIsDefault (Integer isDefault) {
		this.isDefault =  isDefault;
	}
	
	public String getProvinceId () {
		return this.provinceId;
	}
	
	public void setProvinceId (String provinceId) {
		this.provinceId =  provinceId;
	}
	
	public String getProvince () {
		return this.province;
	}
	
	public void setProvince (String province) {
		this.province =  province;
	}
	
	public String getCityId () {
		return this.cityId;
	}
	
	public void setCityId (String cityId) {
		this.cityId =  cityId;
	}
	
	public String getCity () {
		return this.city;
	}
	
	public void setCity (String city) {
		this.city =  city;
	}
	
	public String getAreaId () {
		return this.areaId;
	}
	
	public void setAreaId (String areaId) {
		this.areaId =  areaId;
	}
	
	public String getArea () {
		return this.area;
	}
	
	public void setArea (String area) {
		this.area =  area;
	}
	
	public String getRemark () {
		return this.remark;
	}
	
	public void setRemark (String remark) {
		this.remark =  remark;
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
		entityStirngBuffer.append("address = " +  this.getAddress() + ",");
		entityStirngBuffer.append("isDefault = " +  this.getIsDefault() + ",");
		entityStirngBuffer.append("provinceId = " +  this.getProvinceId() + ",");
		entityStirngBuffer.append("province = " +  this.getProvince() + ",");
		entityStirngBuffer.append("cityId = " +  this.getCityId() + ",");
		entityStirngBuffer.append("city = " +  this.getCity() + ",");
		entityStirngBuffer.append("areaId = " +  this.getAreaId() + ",");
		entityStirngBuffer.append("area = " +  this.getArea() + ",");
		entityStirngBuffer.append("remark = " +  this.getRemark() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}