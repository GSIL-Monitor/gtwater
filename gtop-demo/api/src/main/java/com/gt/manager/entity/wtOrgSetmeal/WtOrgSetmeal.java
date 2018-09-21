package com.gt.manager.entity.wtOrgSetmeal;

import java.io.Serializable;

/**
 * 机构套餐
 * @author why
 */

public class WtOrgSetmeal implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键
	private Long branchesId;// 机构(城市)ID
	private Long brandId;// 品牌
	private String skuCode;// 商品SKU code
	private String setmealCode;// 套餐编号
	private String seriesSkuCode;// 套系编号（sku）
	private String name;// 套餐名称
	private String typeCode;// 类别编号
	private String seriesName;// 
	private String setmealImg;// 套餐宣传图片
	private String shopcartImg;// 购物车图片
	private String goodsProfile;// 商品图文
	private Integer num;// 数量
	private String introduce;// 套餐介绍
	private Long price;// 套餐价格
	private Integer isGift;// 是否有赠品【1有，0无】
	private String remark;// 备注
	private Integer onshelfState;// 上架状态【1上架、0下架】
	private Long onshelfTime;// 上架时间
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	private String goodsSpec;//主商品规格
	
	// Empty Constructor
	public WtOrgSetmeal() {
		super();
	}
	
	// Full Constructor
	public WtOrgSetmeal(Long id, Long branchesId, Long brandId, String skuCode, String setmealCode, String seriesSkuCode, String name, String typeCode, String seriesName, String setmealImg, String shopcartImg, String goodsProfile, Integer num, String introduce, Long price, Integer isGift, String remark, Integer onshelfState, Long onshelfTime, Long createTime, Long createId, Integer delState,String goodsSpec) {
		this.id =  id;
		this.branchesId =  branchesId;
		this.brandId =  brandId;
		this.skuCode =  skuCode;
		this.setmealCode =  setmealCode;
		this.seriesSkuCode =  seriesSkuCode;
		this.name =  name;
		this.typeCode =  typeCode;
		this.seriesName =  seriesName;
		this.setmealImg =  setmealImg;
		this.shopcartImg =  shopcartImg;
		this.goodsProfile =  goodsProfile;
		this.num =  num;
		this.introduce =  introduce;
		this.price =  price;
		this.isGift =  isGift;
		this.remark =  remark;
		this.onshelfState =  onshelfState;
		this.onshelfTime =  onshelfTime;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delState =  delState;
		this.goodsSpec= goodsSpec;
	}

	// Property accessors


	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
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
	
	public Long getBrandId () {
		return this.brandId;
	}
	
	public void setBrandId (Long brandId) {
		this.brandId =  brandId;
	}
	
	public String getSkuCode () {
		return this.skuCode;
	}
	
	public void setSkuCode (String skuCode) {
		this.skuCode =  skuCode;
	}
	
	public String getSetmealCode () {
		return this.setmealCode;
	}
	
	public void setSetmealCode (String setmealCode) {
		this.setmealCode =  setmealCode;
	}
	
	public String getSeriesSkuCode () {
		return this.seriesSkuCode;
	}
	
	public void setSeriesSkuCode (String seriesSkuCode) {
		this.seriesSkuCode =  seriesSkuCode;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name =  name;
	}
	
	public String getTypeCode () {
		return this.typeCode;
	}
	
	public void setTypeCode (String typeCode) {
		this.typeCode =  typeCode;
	}
	
	public String getSeriesName () {
		return this.seriesName;
	}
	
	public void setSeriesName (String seriesName) {
		this.seriesName =  seriesName;
	}
	
	public String getSetmealImg () {
		return this.setmealImg;
	}
	
	public void setSetmealImg (String setmealImg) {
		this.setmealImg =  setmealImg;
	}
	
	public String getShopcartImg () {
		return this.shopcartImg;
	}
	
	public void setShopcartImg (String shopcartImg) {
		this.shopcartImg =  shopcartImg;
	}
	
	public String getGoodsProfile () {
		return this.goodsProfile;
	}
	
	public void setGoodsProfile (String goodsProfile) {
		this.goodsProfile =  goodsProfile;
	}
	
	public Integer getNum () {
		return this.num;
	}
	
	public void setNum (Integer num) {
		this.num =  num;
	}
	
	public String getIntroduce () {
		return this.introduce;
	}
	
	public void setIntroduce (String introduce) {
		this.introduce =  introduce;
	}
	
	public Long getPrice () {
		return this.price;
	}
	
	public void setPrice (Long price) {
		this.price =  price;
	}
	
	public Integer getIsGift () {
		return this.isGift;
	}
	
	public void setIsGift (Integer isGift) {
		this.isGift =  isGift;
	}
	
	public String getRemark () {
		return this.remark;
	}
	
	public void setRemark (String remark) {
		this.remark =  remark;
	}
	
	public Integer getOnshelfState () {
		return this.onshelfState;
	}
	
	public void setOnshelfState (Integer onshelfState) {
		this.onshelfState =  onshelfState;
	}
	
	public Long getOnshelfTime () {
		return this.onshelfTime;
	}
	
	public void setOnshelfTime (Long onshelfTime) {
		this.onshelfTime =  onshelfTime;
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
		entityStirngBuffer.append("branchesId = " +  this.getBranchesId() + ",");
		entityStirngBuffer.append("brandId = " +  this.getBrandId() + ",");
		entityStirngBuffer.append("skuCode = " +  this.getSkuCode() + ",");
		entityStirngBuffer.append("setmealCode = " +  this.getSetmealCode() + ",");
		entityStirngBuffer.append("seriesSkuCode = " +  this.getSeriesSkuCode() + ",");
		entityStirngBuffer.append("name = " +  this.getName() + ",");
		entityStirngBuffer.append("typeCode = " +  this.getTypeCode() + ",");
		entityStirngBuffer.append("seriesName = " +  this.getSeriesName() + ",");
		entityStirngBuffer.append("setmealImg = " +  this.getSetmealImg() + ",");
		entityStirngBuffer.append("shopcartImg = " +  this.getShopcartImg() + ",");
		entityStirngBuffer.append("goodsProfile = " +  this.getGoodsProfile() + ",");
		entityStirngBuffer.append("num = " +  this.getNum() + ",");
		entityStirngBuffer.append("introduce = " +  this.getIntroduce() + ",");
		entityStirngBuffer.append("price = " +  this.getPrice() + ",");
		entityStirngBuffer.append("isGift = " +  this.getIsGift() + ",");
		entityStirngBuffer.append("remark = " +  this.getRemark() + ",");
		entityStirngBuffer.append("onshelfState = " +  this.getOnshelfState() + ",");
		entityStirngBuffer.append("onshelfTime = " +  this.getOnshelfTime() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("goodsSpec = " +  this.getGoodsSpec() + ",");
		return entityStirngBuffer.toString();
	}
	
}