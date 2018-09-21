package com.gt.manager.entity.wtSendMes;

import java.io.Serializable;

/**
 * 派送详情
 * @author why
 */

public class WtSendMes implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 详情ID
	private String sendCode;// 派送单编号
	private String sendMesCode;// 派送单详情编号
	private String skuCode;// SKU编号
	private String skuName;// SKU名称
	private Integer num;// 数量
	private Integer deliveryNum;//实际送达数量
	private String orderMesCode;// 订单明细编号
	private Integer type;// 1水票|| 2桶水
	private String sequence;// 商品序列
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	private String goodsSpec;//商品规格
	private String brandName;//商品品牌

	/**
	 * 1.用钱买水    计算方式    商品单价*购买数量
	 * 2.水票购买    计算方式    水票一使用数量*单价+水票二使用数量*单价.....+水票N使用数量*单价    依次类推
	 * 3.混合支付    计算方式    水票一使用数量*单价+水票二使用数量*单价.....+水票N使用数量*单价    依次类推        +商品单价*购买数量
	 */
	private Long sendMesMoney;//派送单明细结算价格

	// Empty Constructor
	public WtSendMes() {
		super();
	}
	
	// Full Constructor

	public WtSendMes(Long id, String sendCode, String sendMesCode, String skuCode, String skuName, Integer num, Integer deliveryNum, String orderMesCode, Integer type, String sequence, Long createTime, Long createId, Integer delState,String goodsSpec,String brandName) {
		this.id = id;
		this.sendCode = sendCode;
		this.sendMesCode = sendMesCode;
		this.skuCode = skuCode;
		this.skuName = skuName;
		this.num = num;
		this.deliveryNum = deliveryNum;
		this.orderMesCode = orderMesCode;
		this.type = type;
		this.sequence = sequence;
		this.createTime = createTime;
		this.createId = createId;
		this.delState = delState;
		this.goodsSpec=goodsSpec;
		this.brandName=brandName;
	}


	// Property accessors


	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}


	public String getOrderMesCode() {
		return orderMesCode;
	}

	public void setOrderMesCode(String orderMesCode) {
		this.orderMesCode = orderMesCode;
	}

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public String getSendCode () {
		return this.sendCode;
	}
	
	public void setSendCode (String sendCode) {
		this.sendCode =  sendCode;
	}
	
	public String getSendMesCode () {
		return this.sendMesCode;
	}
	
	public void setSendMesCode (String sendMesCode) {
		this.sendMesCode =  sendMesCode;
	}
	
	public String getSkuCode () {
		return this.skuCode;
	}
	
	public void setSkuCode (String skuCode) {
		this.skuCode =  skuCode;
	}
	
	public String getSkuName () {
		return this.skuName;
	}
	
	public void setSkuName (String skuName) {
		this.skuName =  skuName;
	}
	
	public Integer getNum () {
		return this.num;
	}
	
	public void setNum (Integer num) {
		this.num =  num;
	}
	
	public Integer getType () {
		return this.type;
	}
	
	public void setType (Integer type) {
		this.type =  type;
	}
	
	public String getSequence () {
		return this.sequence;
	}
	
	public void setSequence (String sequence) {
		this.sequence =  sequence;
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

	public Long getSendMesMoney() {
		return sendMesMoney;
	}

	public void setSendMesMoney(Long sendMesMoney) {
		this.sendMesMoney = sendMesMoney;
	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("sendCode = " +  this.getSendCode() + ",");
		entityStirngBuffer.append("sendMesCode = " +  this.getSendMesCode() + ",");
		entityStirngBuffer.append("skuCode = " +  this.getSkuCode() + ",");
		entityStirngBuffer.append("skuName = " +  this.getSkuName() + ",");
		entityStirngBuffer.append("num = " +  this.getNum() + ",");
		entityStirngBuffer.append("deliveryNum = " +  this.getDeliveryNum() + ",");
		entityStirngBuffer.append("type = " +  this.getType() + ",");
		entityStirngBuffer.append("sequence = " +  this.getSequence() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("goodsSpec = " +  this.getGoodsSpec() + ",");
		entityStirngBuffer.append("brandName = " +  this.getBrandName() + ",");
		return entityStirngBuffer.toString();
	}
	
}