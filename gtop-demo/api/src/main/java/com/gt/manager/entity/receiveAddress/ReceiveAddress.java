package com.gt.manager.entity.receiveAddress;

import java.io.Serializable;
import java.util.Date;

/**
 * 买家收货地址表
 * @author why
 */

public class ReceiveAddress implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;//
	private String addId;//地址id
	private Long userid;// 系统唯一id
	private String userId2;//用户id
	private String name;// 收货人姓名
	private String phone;// 收货人电话
	private String address;// 收货地址
	private Integer isDefault;// 是否是默认收货地址（1 是 2否）
	private Long provinceId;// 省id
	private String provinceName;// 省名称
	private Long cityId;// 市id
	private String cityName;// 市名称
	private Long districtId;// 区id
	private String districtName;// 区名称
	private String longitude;// 经度
	private String latitude;// 纬度
	private Date createTime;// 创建时间
	private Integer isDelete;// 删除标记位 1是删除 2是未删除
	private String houseNumber;// 门牌号
	private Integer addressType;// 地址类型  1:杂货铺  2:指尖快递  3:水管家
	
	// Empty Constructor
	public ReceiveAddress() {
		super();
	}
	
	// Full Constructor
	public ReceiveAddress(Long id, Long userid, String name, String phone, String address, Integer isDefault, Long provinceId, String provinceName, Long cityId, String cityName, Long districtId, String districtName, String longitude, String latitude, Date createTime, Integer isDelete, String houseNumber, Integer addressType) {
		this.id =  id;
		this.userid =  userid;
		this.name =  name;
		this.phone =  phone;
		this.address =  address;
		this.isDefault =  isDefault;
		this.provinceId =  provinceId;
		this.provinceName =  provinceName;
		this.cityId =  cityId;
		this.cityName =  cityName;
		this.districtId =  districtId;
		this.districtName =  districtName;
		this.longitude =  longitude;
		this.latitude =  latitude;
		this.createTime =  createTime;
		this.isDelete =  isDelete;
		this.houseNumber =  houseNumber;
		this.addressType =  addressType;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Long getUserid () {
		return this.userid;
	}
	
	public void setUserid (Long userid) {
		this.userid =  userid;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name =  name;
	}
	
	public String getPhone () {
		return this.phone;
	}
	
	public void setPhone (String phone) {
		this.phone =  phone;
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
	
	public Long getProvinceId () {
		return this.provinceId;
	}
	
	public void setProvinceId (Long provinceId) {
		this.provinceId =  provinceId;
	}
	
	public String getProvinceName () {
		return this.provinceName;
	}
	
	public void setProvinceName (String provinceName) {
		this.provinceName =  provinceName;
	}
	
	public Long getCityId () {
		return this.cityId;
	}
	
	public void setCityId (Long cityId) {
		this.cityId =  cityId;
	}
	
	public String getCityName () {
		return this.cityName;
	}
	
	public void setCityName (String cityName) {
		this.cityName =  cityName;
	}
	
	public Long getDistrictId () {
		return this.districtId;
	}
	
	public void setDistrictId (Long districtId) {
		this.districtId =  districtId;
	}
	
	public String getDistrictName () {
		return this.districtName;
	}
	
	public void setDistrictName (String districtName) {
		this.districtName =  districtName;
	}
	
	public String getLongitude () {
		return this.longitude;
	}
	
	public void setLongitude (String longitude) {
		this.longitude =  longitude;
	}
	
	public String getLatitude () {
		return this.latitude;
	}
	
	public void setLatitude (String latitude) {
		this.latitude =  latitude;
	}
	
	public Date getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Date createTime) {
		this.createTime =  createTime;
	}
	
	public Integer getIsDelete () {
		return this.isDelete;
	}
	
	public void setIsDelete (Integer isDelete) {
		this.isDelete =  isDelete;
	}
	
	public String getHouseNumber () {
		return this.houseNumber;
	}
	
	public void setHouseNumber (String houseNumber) {
		this.houseNumber =  houseNumber;
	}
	
	public Integer getAddressType () {
		return this.addressType;
	}
	
	public void setAddressType (Integer addressType) {
		this.addressType =  addressType;
	}

	public String getUserId2() {
		return userId2;
	}

	public void setUserId2(String userId2) {
		this.userId2 = userId2;
	}

	public String getAddId() {
		return addId;
	}

	public void setAddId(String addId) {
		this.addId = addId;
	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("userid = " +  this.getUserid() + ",");
		entityStirngBuffer.append("name = " +  this.getName() + ",");
		entityStirngBuffer.append("phone = " +  this.getPhone() + ",");
		entityStirngBuffer.append("address = " +  this.getAddress() + ",");
		entityStirngBuffer.append("isDefault = " +  this.getIsDefault() + ",");
		entityStirngBuffer.append("provinceId = " +  this.getProvinceId() + ",");
		entityStirngBuffer.append("provinceName = " +  this.getProvinceName() + ",");
		entityStirngBuffer.append("cityId = " +  this.getCityId() + ",");
		entityStirngBuffer.append("cityName = " +  this.getCityName() + ",");
		entityStirngBuffer.append("districtId = " +  this.getDistrictId() + ",");
		entityStirngBuffer.append("districtName = " +  this.getDistrictName() + ",");
		entityStirngBuffer.append("longitude = " +  this.getLongitude() + ",");
		entityStirngBuffer.append("latitude = " +  this.getLatitude() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("isDelete = " +  this.getIsDelete() + ",");
		entityStirngBuffer.append("houseNumber = " +  this.getHouseNumber() + ",");
		entityStirngBuffer.append("addressType = " +  this.getAddressType() + ",");
		return entityStirngBuffer.toString();
	}
	
}