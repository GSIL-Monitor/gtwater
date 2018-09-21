package com.gt.manager.entity.wtPartner;

import java.io.Serializable;

/**
 * 合伙人表
 * @author why
 */

public class WtPartner implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// partner_id
	private Long userId;// 用户ID
	private String openId;// 开放平台用户ID
	private String phone;// 电话号码
	private String userName;// 用户名
	private String idcard;// 身份证号
	private String cardFront;// 身份证正面照
	private String cardBack;// 身份证反面照
	private String nickname;// 昵称
	private Integer sex;// 性别(1男、0女)
	private String country;// 国家
	private String provinceId;// 省ID
	private String province;// 省
	private String cityId;// 市ID
	private String city;// 市
	private String areaId;// 区ID
	private String area;// 区
	private String address;// 详细地址
	private String language;// 语言
	private Long registerDate;// 注册时间
	private Long incomeMoney;// 累计收入
	private Long totalMoney;// 总金额
	private Long extractMoney;// 提现金额
	private Long customerNum;// 客户数量
	private String qrCode;// 二维码内容
	private Long qrCodeId;//二维码id
	private String portrait;// 用户头像
	private Long updateTime;// 更新时间
	private String remark;// 备注
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	
	// Empty Constructor
	public WtPartner() {
		super();
	}
	
	// Full Constructor
	public WtPartner(Long id, Long userId, String openId, String phone, String userName, String idcard, String cardFront, String cardBack, String nickname, Integer sex, String country, String provinceId, String province, String cityId, String city, String areaId, String area, String address, String language, Long registerDate, Long incomeMoney, Long totalMoney, Long extractMoney, Long customerNum, String qrCode, Long qrCodeId, String portrait, Long updateTime, String remark, Long createTime, Long createId, Integer delState) {
		this.id =  id;
		this.userId =  userId;
		this.openId =  openId;
		this.phone =  phone;
		this.userName =  userName;
		this.idcard =  idcard;
		this.cardFront =  cardFront;
		this.cardBack =  cardBack;
		this.nickname =  nickname;
		this.sex =  sex;
		this.country =  country;
		this.provinceId =  provinceId;
		this.province =  province;
		this.cityId =  cityId;
		this.city =  city;
		this.areaId =  areaId;
		this.area =  area;
		this.address =  address;
		this.language =  language;
		this.registerDate =  registerDate;
		this.incomeMoney =  incomeMoney;
		this.totalMoney =  totalMoney;
		this.extractMoney =  extractMoney;
		this.customerNum =  customerNum;
		this.qrCode =  qrCode;
		this.qrCodeId = qrCodeId;
		this.portrait =  portrait;
		this.updateTime =  updateTime;
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
	
	public String getOpenId () {
		return this.openId;
	}
	
	public void setOpenId (String openId) {
		this.openId =  openId;
	}
	
	public String getPhone () {
		return this.phone;
	}
	
	public void setPhone (String phone) {
		this.phone =  phone;
	}
	
	public String getUserName () {
		return this.userName;
	}
	
	public void setUserName (String userName) {
		this.userName =  userName;
	}
	
	public String getIdcard () {
		return this.idcard;
	}
	
	public void setIdcard (String idcard) {
		this.idcard =  idcard;
	}
	
	public String getCardFront () {
		return this.cardFront;
	}
	
	public void setCardFront (String cardFront) {
		this.cardFront =  cardFront;
	}
	
	public String getCardBack () {
		return this.cardBack;
	}
	
	public void setCardBack (String cardBack) {
		this.cardBack =  cardBack;
	}
	
	public String getNickname () {
		return this.nickname;
	}
	
	public void setNickname (String nickname) {
		this.nickname =  nickname;
	}
	
	public Integer getSex () {
		return this.sex;
	}
	
	public void setSex (Integer sex) {
		this.sex =  sex;
	}
	
	public String getCountry () {
		return this.country;
	}
	
	public void setCountry (String country) {
		this.country =  country;
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
	
	public String getAddress () {
		return this.address;
	}
	
	public void setAddress (String address) {
		this.address =  address;
	}
	
	public String getLanguage () {
		return this.language;
	}
	
	public void setLanguage (String language) {
		this.language =  language;
	}
	
	public Long getRegisterDate () {
		return this.registerDate;
	}
	
	public void setRegisterDate (Long registerDate) {
		this.registerDate =  registerDate;
	}
	
	public Long getIncomeMoney () {
		return this.incomeMoney;
	}
	
	public void setIncomeMoney (Long incomeMoney) {
		this.incomeMoney =  incomeMoney;
	}
	
	public Long getTotalMoney () {
		return this.totalMoney;
	}
	
	public void setTotalMoney (Long totalMoney) {
		this.totalMoney =  totalMoney;
	}
	
	public Long getExtractMoney () {
		return this.extractMoney;
	}
	
	public void setExtractMoney (Long extractMoney) {
		this.extractMoney =  extractMoney;
	}
	
	public Long getCustomerNum () {
		return this.customerNum;
	}
	
	public void setCustomerNum (Long customerNum) {
		this.customerNum =  customerNum;
	}
	
	public String getQrCode () {
		return this.qrCode;
	}
	
	public void setQrCode (String qrCode) {
		this.qrCode =  qrCode;
	}

	public Long getQrCodeId () { return this.qrCodeId;}

	public void setQrCodeId (Long qrCodeId){this.qrCodeId = qrCodeId;}
	
	public String getPortrait () {
		return this.portrait;
	}
	
	public void setPortrait (String portrait) {
		this.portrait =  portrait;
	}
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
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
		entityStirngBuffer.append("openId = " +  this.getOpenId() + ",");
		entityStirngBuffer.append("phone = " +  this.getPhone() + ",");
		entityStirngBuffer.append("userName = " +  this.getUserName() + ",");
		entityStirngBuffer.append("idcard = " +  this.getIdcard() + ",");
		entityStirngBuffer.append("cardFront = " +  this.getCardFront() + ",");
		entityStirngBuffer.append("cardBack = " +  this.getCardBack() + ",");
		entityStirngBuffer.append("nickname = " +  this.getNickname() + ",");
		entityStirngBuffer.append("sex = " +  this.getSex() + ",");
		entityStirngBuffer.append("country = " +  this.getCountry() + ",");
		entityStirngBuffer.append("provinceId = " +  this.getProvinceId() + ",");
		entityStirngBuffer.append("province = " +  this.getProvince() + ",");
		entityStirngBuffer.append("cityId = " +  this.getCityId() + ",");
		entityStirngBuffer.append("city = " +  this.getCity() + ",");
		entityStirngBuffer.append("areaId = " +  this.getAreaId() + ",");
		entityStirngBuffer.append("area = " +  this.getArea() + ",");
		entityStirngBuffer.append("address = " +  this.getAddress() + ",");
		entityStirngBuffer.append("language = " +  this.getLanguage() + ",");
		entityStirngBuffer.append("registerDate = " +  this.getRegisterDate() + ",");
		entityStirngBuffer.append("incomeMoney = " +  this.getIncomeMoney() + ",");
		entityStirngBuffer.append("totalMoney = " +  this.getTotalMoney() + ",");
		entityStirngBuffer.append("extractMoney = " +  this.getExtractMoney() + ",");
		entityStirngBuffer.append("customerNum = " +  this.getCustomerNum() + ",");
		entityStirngBuffer.append("qrCode = " +  this.getQrCode() + ",");
		entityStirngBuffer.append("portrait = " +  this.getPortrait() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("remark = " +  this.getRemark() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		return entityStirngBuffer.toString();
	}
	
}