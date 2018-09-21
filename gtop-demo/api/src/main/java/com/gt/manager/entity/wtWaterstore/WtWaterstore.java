package com.gt.manager.entity.wtWaterstore;

import java.io.Serializable;
import java.sql.Time;

/**
 * 水站表
 * @author why
 */

public class WtWaterstore implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// ID
	private Long branchesId;// 开放平台机构编号
	private Long cityBranchesId;//城市机构id
	private String waterName;// 水站名称
	private String showName;// 水站显示名称
	private Long orgId;// 组织机构ID
	private String waterstoreNo;// 水站编号
	private String tel;// 站点电话
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delStatus;// 删除状态
	private String orgCode;// 组织机构编号
	private Long parentId;// 父级ID
	private Integer orgState;// 状态 0待运营、1运营中、2已关闭
	private Long auditRequestTime;// 提交审核时间
	private Long auditTime;// 审核时间
	private Long auditorId;// 审核人
	private Integer auditState;// 审核状态 ：-1审核中、0审核失败、1审核成功
	private String approveCode;// 审批编号
	private Long createId2;// 创建人
	private Long updateId;// 修改人
	private Long updateTime;// 修改时间
	private Integer isDelete;// 是否删除0删除，1正常
	private Integer selfLifeState;// 是否可以自提，1是自提，0与null非自提
	private String selfLifeAddress;// 自提地址
	private String selfLifePhone;// 自提手机号,多个手机号用","号分割
	private String selfLifeTime;// 自提时间格式hh:mm-hh:mm
	private String serviceRange;// 服务范围(派送范围的经纬度)
	private String serviceRangeMark;// 服务范围备注信息
	private String bankAccountName;// 银行账号姓名（户名）
	private String openBank;// 开户行
	private String bankAccount;// 银行账号
	private String bankName;// 银行名称
	private String serviceStationLocation;// 服务站位置
	private String serviceStationArea;// 服务站面积
	private Integer serviceStationType;// 服务站类型1-居民楼，2-办公楼，3-社区，4-校园
	private Integer branchesType;// 门店类型：1直营，2加盟商
	private String branchesImg;// 门店照片
	private Integer branchesSort;// 门店排序
	private Integer isShow;// 是否展示1展示，0不展示
	private String linkPhone;// 联系电话
	private String linkName;// 
	private String sendTime;// 配送时间
	private String operateEndTime;// 每天运营结束时间
	private String operateStartTime;// 每天运营开始时间
	private String operateWeek;// 运营时间周 【0,0,0,0,0,0,0】7位字符串，从周一至周日每一位代表一天，1代表上班，0代表休息，以英文逗号分隔
	private String mail;// Mail
	
	// Empty Constructor
	public WtWaterstore() {
		super();
	}
	
	// Full Constructor
	public WtWaterstore(Long id, Long branchesId, String waterName, String showName, Long orgId, String waterstoreNo, String tel, Long createTime, Long createId, Integer delStatus, String orgCode, Long parentId, Integer orgState, Long auditRequestTime, Long auditTime, Long auditorId, Integer auditState, String approveCode, Long createId2, Long updateId, Long updateTime, Integer isDelete, Integer selfLifeState, String selfLifeAddress, String selfLifePhone, String selfLifeTime, String serviceRange, String serviceRangeMark, String bankAccountName, String openBank, String bankAccount, String bankName, String serviceStationLocation, String serviceStationArea, Integer serviceStationType, Integer branchesType, String branchesImg, Integer branchesSort, Integer isShow, String linkPhone, String linkName, String sendTime, String operateEndTime, String operateStartTime, String operateWeek, String mail) {
		this.id =  id;
		this.branchesId =  branchesId;
		this.waterName =  waterName;
		this.showName =  showName;
		this.orgId =  orgId;
		this.waterstoreNo =  waterstoreNo;
		this.tel =  tel;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delStatus =  delStatus;
		this.orgCode =  orgCode;
		this.parentId =  parentId;
		this.orgState =  orgState;
		this.auditRequestTime =  auditRequestTime;
		this.auditTime =  auditTime;
		this.auditorId =  auditorId;
		this.auditState =  auditState;
		this.approveCode =  approveCode;
		this.createId2 =  createId2;
		this.updateId =  updateId;
		this.updateTime =  updateTime;
		this.isDelete =  isDelete;
		this.selfLifeState =  selfLifeState;
		this.selfLifeAddress =  selfLifeAddress;
		this.selfLifePhone =  selfLifePhone;
		this.selfLifeTime =  selfLifeTime;
		this.serviceRange =  serviceRange;
		this.serviceRangeMark =  serviceRangeMark;
		this.bankAccountName =  bankAccountName;
		this.openBank =  openBank;
		this.bankAccount =  bankAccount;
		this.bankName =  bankName;
		this.serviceStationLocation =  serviceStationLocation;
		this.serviceStationArea =  serviceStationArea;
		this.serviceStationType =  serviceStationType;
		this.branchesType =  branchesType;
		this.branchesImg =  branchesImg;
		this.branchesSort =  branchesSort;
		this.isShow =  isShow;
		this.linkPhone =  linkPhone;
		this.linkName =  linkName;
		this.sendTime =  sendTime;
		this.operateEndTime =  operateEndTime;
		this.operateStartTime =  operateStartTime;
		this.operateWeek =  operateWeek;
		this.mail =  mail;
	}

	// Property accessors


	public Long getCityBranchesId() {
		return cityBranchesId;
	}

	public void setCityBranchesId(Long cityBranchesId) {
		this.cityBranchesId = cityBranchesId;
	}

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Long getBranchesId () {
		return this.branchesId;
	}
	
	public void setBranchesId (Long branchesId) {
		this.branchesId =  branchesId;
	}
	
	public String getWaterName () {
		return this.waterName;
	}
	
	public void setWaterName (String waterName) {
		this.waterName =  waterName;
	}
	
	public String getShowName () {
		return this.showName;
	}
	
	public void setShowName (String showName) {
		this.showName =  showName;
	}
	
	public Long getOrgId () {
		return this.orgId;
	}
	
	public void setOrgId (Long orgId) {
		this.orgId =  orgId;
	}
	
	public String getWaterstoreNo () {
		return this.waterstoreNo;
	}
	
	public void setWaterstoreNo (String waterstoreNo) {
		this.waterstoreNo =  waterstoreNo;
	}
	
	public String getTel () {
		return this.tel;
	}
	
	public void setTel (String tel) {
		this.tel =  tel;
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
	
	public Integer getDelStatus () {
		return this.delStatus;
	}
	
	public void setDelStatus (Integer delStatus) {
		this.delStatus =  delStatus;
	}
	
	public String getOrgCode () {
		return this.orgCode;
	}
	
	public void setOrgCode (String orgCode) {
		this.orgCode =  orgCode;
	}
	
	public Long getParentId () {
		return this.parentId;
	}
	
	public void setParentId (Long parentId) {
		this.parentId =  parentId;
	}
	
	public Integer getOrgState () {
		return this.orgState;
	}
	
	public void setOrgState (Integer orgState) {
		this.orgState =  orgState;
	}
	
	public Long getAuditRequestTime () {
		return this.auditRequestTime;
	}
	
	public void setAuditRequestTime (Long auditRequestTime) {
		this.auditRequestTime =  auditRequestTime;
	}
	
	public Long getAuditTime () {
		return this.auditTime;
	}
	
	public void setAuditTime (Long auditTime) {
		this.auditTime =  auditTime;
	}
	
	public Long getAuditorId () {
		return this.auditorId;
	}
	
	public void setAuditorId (Long auditorId) {
		this.auditorId =  auditorId;
	}
	
	public Integer getAuditState () {
		return this.auditState;
	}
	
	public void setAuditState (Integer auditState) {
		this.auditState =  auditState;
	}
	
	public String getApproveCode () {
		return this.approveCode;
	}
	
	public void setApproveCode (String approveCode) {
		this.approveCode =  approveCode;
	}
	
	public Long getCreateId2 () {
		return this.createId2;
	}
	
	public void setCreateId2 (Long createId2) {
		this.createId2 =  createId2;
	}
	
	public Long getUpdateId () {
		return this.updateId;
	}
	
	public void setUpdateId (Long updateId) {
		this.updateId =  updateId;
	}
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
	}
	
	public Integer getIsDelete () {
		return this.isDelete;
	}
	
	public void setIsDelete (Integer isDelete) {
		this.isDelete =  isDelete;
	}
	
	public Integer getSelfLifeState () {
		return this.selfLifeState;
	}
	
	public void setSelfLifeState (Integer selfLifeState) {
		this.selfLifeState =  selfLifeState;
	}
	
	public String getSelfLifeAddress () {
		return this.selfLifeAddress;
	}
	
	public void setSelfLifeAddress (String selfLifeAddress) {
		this.selfLifeAddress =  selfLifeAddress;
	}
	
	public String getSelfLifePhone () {
		return this.selfLifePhone;
	}
	
	public void setSelfLifePhone (String selfLifePhone) {
		this.selfLifePhone =  selfLifePhone;
	}
	
	public String getSelfLifeTime () {
		return this.selfLifeTime;
	}
	
	public void setSelfLifeTime (String selfLifeTime) {
		this.selfLifeTime =  selfLifeTime;
	}
	
	public String getServiceRange () {
		return this.serviceRange;
	}
	
	public void setServiceRange (String serviceRange) {
		this.serviceRange =  serviceRange;
	}
	
	public String getServiceRangeMark () {
		return this.serviceRangeMark;
	}
	
	public void setServiceRangeMark (String serviceRangeMark) {
		this.serviceRangeMark =  serviceRangeMark;
	}
	
	public String getBankAccountName () {
		return this.bankAccountName;
	}
	
	public void setBankAccountName (String bankAccountName) {
		this.bankAccountName =  bankAccountName;
	}
	
	public String getOpenBank () {
		return this.openBank;
	}
	
	public void setOpenBank (String openBank) {
		this.openBank =  openBank;
	}
	
	public String getBankAccount () {
		return this.bankAccount;
	}
	
	public void setBankAccount (String bankAccount) {
		this.bankAccount =  bankAccount;
	}
	
	public String getBankName () {
		return this.bankName;
	}
	
	public void setBankName (String bankName) {
		this.bankName =  bankName;
	}
	
	public String getServiceStationLocation () {
		return this.serviceStationLocation;
	}
	
	public void setServiceStationLocation (String serviceStationLocation) {
		this.serviceStationLocation =  serviceStationLocation;
	}
	
	public String getServiceStationArea () {
		return this.serviceStationArea;
	}
	
	public void setServiceStationArea (String serviceStationArea) {
		this.serviceStationArea =  serviceStationArea;
	}
	
	public Integer getServiceStationType () {
		return this.serviceStationType;
	}
	
	public void setServiceStationType (Integer serviceStationType) {
		this.serviceStationType =  serviceStationType;
	}
	
	public Integer getBranchesType () {
		return this.branchesType;
	}
	
	public void setBranchesType (Integer branchesType) {
		this.branchesType =  branchesType;
	}
	
	public String getBranchesImg () {
		return this.branchesImg;
	}
	
	public void setBranchesImg (String branchesImg) {
		this.branchesImg =  branchesImg;
	}
	
	public Integer getBranchesSort () {
		return this.branchesSort;
	}
	
	public void setBranchesSort (Integer branchesSort) {
		this.branchesSort =  branchesSort;
	}
	
	public Integer getIsShow () {
		return this.isShow;
	}
	
	public void setIsShow (Integer isShow) {
		this.isShow =  isShow;
	}
	
	public String getLinkPhone () {
		return this.linkPhone;
	}
	
	public void setLinkPhone (String linkPhone) {
		this.linkPhone =  linkPhone;
	}
	
	public String getLinkName () {
		return this.linkName;
	}
	
	public void setLinkName (String linkName) {
		this.linkName =  linkName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getOperateEndTime() {
		return operateEndTime;
	}

	public void setOperateEndTime(String operateEndTime) {
		this.operateEndTime = operateEndTime;
	}

	public String getOperateStartTime() {
		return operateStartTime;
	}

	public void setOperateStartTime(String operateStartTime) {
		this.operateStartTime = operateStartTime;
	}

	public String getOperateWeek () {
		return this.operateWeek;
	}
	
	public void setOperateWeek (String operateWeek) {
		this.operateWeek =  operateWeek;
	}
	
	public String getMail () {
		return this.mail;
	}
	
	public void setMail (String mail) {
		this.mail =  mail;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("waterName = " +  this.getWaterName() + ",");
		entityStirngBuffer.append("showName = " +  this.getShowName() + ",");
		entityStirngBuffer.append("orgId = " +  this.getOrgId() + ",");
		entityStirngBuffer.append("waterstoreNo = " +  this.getWaterstoreNo() + ",");
		entityStirngBuffer.append("tel = " +  this.getTel() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delStatus = " +  this.getDelStatus() + ",");
		entityStirngBuffer.append("orgCode = " +  this.getOrgCode() + ",");
		entityStirngBuffer.append("parentId = " +  this.getParentId() + ",");
		entityStirngBuffer.append("orgState = " +  this.getOrgState() + ",");
		entityStirngBuffer.append("auditRequestTime = " +  this.getAuditRequestTime() + ",");
		entityStirngBuffer.append("auditTime = " +  this.getAuditTime() + ",");
		entityStirngBuffer.append("auditorId = " +  this.getAuditorId() + ",");
		entityStirngBuffer.append("auditState = " +  this.getAuditState() + ",");
		entityStirngBuffer.append("approveCode = " +  this.getApproveCode() + ",");
		entityStirngBuffer.append("createId2 = " +  this.getCreateId2() + ",");
		entityStirngBuffer.append("updateId = " +  this.getUpdateId() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("isDelete = " +  this.getIsDelete() + ",");
		entityStirngBuffer.append("selfLifeState = " +  this.getSelfLifeState() + ",");
		entityStirngBuffer.append("selfLifeAddress = " +  this.getSelfLifeAddress() + ",");
		entityStirngBuffer.append("selfLifePhone = " +  this.getSelfLifePhone() + ",");
		entityStirngBuffer.append("selfLifeTime = " +  this.getSelfLifeTime() + ",");
		entityStirngBuffer.append("serviceRange = " +  this.getServiceRange() + ",");
		entityStirngBuffer.append("serviceRangeMark = " +  this.getServiceRangeMark() + ",");
		entityStirngBuffer.append("bankAccountName = " +  this.getBankAccountName() + ",");
		entityStirngBuffer.append("openBank = " +  this.getOpenBank() + ",");
		entityStirngBuffer.append("bankAccount = " +  this.getBankAccount() + ",");
		entityStirngBuffer.append("bankName = " +  this.getBankName() + ",");
		entityStirngBuffer.append("serviceStationLocation = " +  this.getServiceStationLocation() + ",");
		entityStirngBuffer.append("serviceStationArea = " +  this.getServiceStationArea() + ",");
		entityStirngBuffer.append("serviceStationType = " +  this.getServiceStationType() + ",");
		entityStirngBuffer.append("branchesType = " +  this.getBranchesType() + ",");
		entityStirngBuffer.append("branchesImg = " +  this.getBranchesImg() + ",");
		entityStirngBuffer.append("branchesSort = " +  this.getBranchesSort() + ",");
		entityStirngBuffer.append("isShow = " +  this.getIsShow() + ",");
		entityStirngBuffer.append("linkPhone = " +  this.getLinkPhone() + ",");
		entityStirngBuffer.append("linkName = " +  this.getLinkName() + ",");
		entityStirngBuffer.append("sendTime = " +  this.getSendTime() + ",");
		entityStirngBuffer.append("operateEndTime = " +  this.getOperateEndTime() + ",");
		entityStirngBuffer.append("operateStartTime = " +  this.getOperateStartTime() + ",");
		entityStirngBuffer.append("operateWeek = " +  this.getOperateWeek() + ",");
		entityStirngBuffer.append("mail = " +  this.getMail() + ",");
		return entityStirngBuffer.toString();
	}
	
}