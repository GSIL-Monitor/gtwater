package com.gt.manager.entity.gtopProduct;

import java.io.Serializable;

/**
 * 机构_商品_价格表
 * @author why
 */

public class GtopProduct implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 商品id
	private Long branchesId;// 机构ID
	private String goodsCode;// 商品编号（SPU编号）
	private Long brandId;// 品牌ID
	private String typeCode;// 类别编号
	private String goodsName;// 商品名称
	private String goodsPhotos;// 商品图片（多个）
	private String goodsPic;// 商品购物车图片
	private Integer goodsSource;// 商品来源（1：平台商品，2：店铺自定义商品）
	private String goodsProfile;// 商品图文
	private Long createId;// 创建者
	private Long createTime;// 创建时间
	private Long updateId;// 修改者
	private Long updateTime;// 修改时间
	private Integer delState;// 删除状态【1正常，0删除】
	private Long version;// 数据版本
	
	// Empty Constructor
	public GtopProduct() {
		super();
	}
	
	// Full Constructor
	public GtopProduct(Long id, Long branchesId, String goodsCode, Long brandId, String typeCode, String goodsName, String goodsPhotos, String goodsPic, Integer goodsSource, String goodsProfile, Long createId, Long createTime, Long updateId, Long updateTime, Integer delState, Long version) {
		this.id =  id;
		this.branchesId =  branchesId;
		this.goodsCode =  goodsCode;
		this.brandId =  brandId;
		this.typeCode =  typeCode;
		this.goodsName =  goodsName;
		this.goodsPhotos =  goodsPhotos;
		this.goodsPic =  goodsPic;
		this.goodsSource =  goodsSource;
		this.goodsProfile =  goodsProfile;
		this.createId =  createId;
		this.createTime =  createTime;
		this.updateId =  updateId;
		this.updateTime =  updateTime;
		this.delState =  delState;
		this.version =  version;
	}

	// Property accessors

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
	
	public String getGoodsCode () {
		return this.goodsCode;
	}
	
	public void setGoodsCode (String goodsCode) {
		this.goodsCode =  goodsCode;
	}
	
	public Long getBrandId () {
		return this.brandId;
	}
	
	public void setBrandId (Long brandId) {
		this.brandId =  brandId;
	}
	
	public String getTypeCode () {
		return this.typeCode;
	}
	
	public void setTypeCode (String typeCode) {
		this.typeCode =  typeCode;
	}
	
	public String getGoodsName () {
		return this.goodsName;
	}
	
	public void setGoodsName (String goodsName) {
		this.goodsName =  goodsName;
	}
	
	public String getGoodsPhotos () {
		return this.goodsPhotos;
	}
	
	public void setGoodsPhotos (String goodsPhotos) {
		this.goodsPhotos =  goodsPhotos;
	}
	
	public String getGoodsPic () {
		return this.goodsPic;
	}
	
	public void setGoodsPic (String goodsPic) {
		this.goodsPic =  goodsPic;
	}
	
	public Integer getGoodsSource () {
		return this.goodsSource;
	}
	
	public void setGoodsSource (Integer goodsSource) {
		this.goodsSource =  goodsSource;
	}
	
	public String  getGoodsProfile () {
		return this.goodsProfile;
	}
	
	public void setGoodsProfile (String goodsProfile) {
		this.goodsProfile =  goodsProfile;
	}
	
	public Long getCreateId () {
		return this.createId;
	}
	
	public void setCreateId (Long createId) {
		this.createId =  createId;
	}
	
	public Long getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Long createTime) {
		this.createTime =  createTime;
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
	
	public Integer getDelState () {
		return this.delState;
	}
	
	public void setDelState (Integer delState) {
		this.delState =  delState;
	}
	
	public Long getVersion () {
		return this.version;
	}
	
	public void setVersion (Long version) {
		this.version =  version;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("goodsCode = " +  this.getGoodsCode() + ",");
		entityStirngBuffer.append("brandId = " +  this.getBrandId() + ",");
		entityStirngBuffer.append("typeCode = " +  this.getTypeCode() + ",");
		entityStirngBuffer.append("goodsName = " +  this.getGoodsName() + ",");
		entityStirngBuffer.append("goodsPhotos = " +  this.getGoodsPhotos() + ",");
		entityStirngBuffer.append("goodsPic = " +  this.getGoodsPic() + ",");
		entityStirngBuffer.append("goodsSource = " +  this.getGoodsSource() + ",");
		entityStirngBuffer.append("goodsProfile = " +  this.getGoodsProfile() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateId = " +  this.getUpdateId() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("version = " +  this.getVersion() + ",");
		return entityStirngBuffer.toString();
	}
	
}