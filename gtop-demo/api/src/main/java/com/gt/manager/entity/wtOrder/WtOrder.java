package com.gt.manager.entity.wtOrder;

import com.gt.manager.entity.wtInvoice.WtInvoice;
import com.gt.manager.entity.wtOrderMes.WtOrderMes;

import java.io.Serializable;
import java.util.List;

/**
 * 订单
 * @author why
 */

public class WtOrder implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 订单ID
	private String orderNo;// 订单编号
	private Long waterstoreId;// 水站ID
	private Long userId;// 用户（客户）ID
	private String userId2;//用户id
	private String contacts;
	private String phone;//电话
	private String provinceId;// 省ID
	private String province;// 省
	private String cityId;// 市ID
	private String city;// 市
	private String areaId;// 区ID
	private String area;// 区
	private String address;// 详细地址
	private Long money;// 订单金额
	private String remarks;// 买家留言
	private Integer isInvoice;// 是否发票
	private Integer isSetmeal;// 是否有套餐
	private Integer isTicket;// 是否水票抵扣
	private Long ticketMoney;// 水票抵扣金额
	private Long paymentMoney;// 付款金额
	private String paymentCode;// gtpay生成的订单号
	private Integer paymentType;// 付款方式【1微信】
	private Integer orderState;// 订单状态【1订单确认、2代付款、3完成】
	private Long orderTime;// 下单时间
	private Long paymentTime;// 付款时间
	private Long extensioncodeId;// 推广码ID
	private Long createTime;// 创建时间
	private Long createId;// 创建人
	private Integer delState;// 删除状态 1正常、0删除
	private Long updateTime;// 修改时间
	private Long appointmentTime;//预约时间
	private String openId;//
	private List<WtOrderMes> wtOrderMes;//订单详情
	private List<WtInvoice> wtInvoice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getWaterstoreId() {
		return waterstoreId;
	}

	public void setWaterstoreId(Long waterstoreId) {
		this.waterstoreId = waterstoreId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserId2() {
		return userId2;
	}

	public void setUserId2(String userId2) {
		this.userId2 = userId2;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}

	public Integer getIsSetmeal() {
		return isSetmeal;
	}

	public void setIsSetmeal(Integer isSetmeal) {
		this.isSetmeal = isSetmeal;
	}

	public Integer getIsTicket() {
		return isTicket;
	}

	public void setIsTicket(Integer isTicket) {
		this.isTicket = isTicket;
	}

	public Long getTicketMoney() {
		return ticketMoney;
	}

	public void setTicketMoney(Long ticketMoney) {
		this.ticketMoney = ticketMoney;
	}

	public Long getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(Long paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Long getExtensioncodeId() {
		return extensioncodeId;
	}

	public void setExtensioncodeId(Long extensioncodeId) {
		this.extensioncodeId = extensioncodeId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Integer getDelState() {
		return delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

	public List <WtOrderMes> getWtOrderMes() {
		return wtOrderMes;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public void setWtOrderMes(List <WtOrderMes> wtOrderMes) {
		this.wtOrderMes = wtOrderMes;
	}

	public List <WtInvoice> getWtInvoice() {
		return wtInvoice;
	}

	public void setWtInvoice(List <WtInvoice> wtInvoice) {
		this.wtInvoice = wtInvoice;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}


	public Long getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Long appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	// Empty Constructor
	public WtOrder() {
		super();
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	// Full Constructor
	public WtOrder(Long id, String orderNo, Long waterstoreId, Long userId, String contacts, String phone, String provinceId, String province, String cityId, String city, String areaId, String area, String address, Long money, String remarks, Integer isInvoice, Integer isSetmeal, Integer isTicket, Long ticketMoney, Long paymentMoney, Integer paymentType, String paymentCode, Integer orderState, Long orderTime, Long paymentTime, Long extensioncodeId, Long createTime, Long createId, Integer delState, List<WtOrderMes> wtOrderMes, List<WtInvoice> wtInvoice,String openId) {
		this.id =  id;
		this.orderNo =  orderNo;
		this.waterstoreId =  waterstoreId;
		this.userId =  userId;
		this.provinceId =  provinceId;
		this.province =  province;
		this.cityId =  cityId;
		this.city =  city;
		this.areaId =  areaId;
		this.area =  area;
		this.address =  address;
		this.money =  money;
		this.remarks =  remarks;
		this.isInvoice =  isInvoice;
		this.isSetmeal =  isSetmeal;
		this.isTicket =  isTicket;
		this.ticketMoney =  ticketMoney;
		this.paymentMoney =  paymentMoney;
		this.paymentType =  paymentType;
		this.paymentCode =  paymentCode;
		this.orderState =  orderState;
		this.orderTime =  orderTime;
		this.paymentTime =  paymentTime;
		this.extensioncodeId =  extensioncodeId;
		this.createTime =  createTime;
		this.createId =  createId;
		this.delState =  delState;
		this.wtOrderMes = wtOrderMes;
		this.wtInvoice = wtInvoice;
		this.contacts = contacts;
		this.phone = phone;
		this.openId = openId;

	}

	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("orderNo = " +  this.getOrderNo() + ",");
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("userId = " +  this.getUserId() + ",");
		entityStirngBuffer.append("provinceId = " +  this.getProvinceId() + ",");
		entityStirngBuffer.append("province = " +  this.getProvince() + ",");
		entityStirngBuffer.append("cityId = " +  this.getCityId() + ",");
		entityStirngBuffer.append("city = " +  this.getCity() + ",");
		entityStirngBuffer.append("areaId = " +  this.getAreaId() + ",");
		entityStirngBuffer.append("area = " +  this.getArea() + ",");
		entityStirngBuffer.append("address = " +  this.getAddress() + ",");
		entityStirngBuffer.append("money = " +  this.getMoney() + ",");
		entityStirngBuffer.append("remarks = " +  this.getRemarks() + ",");
		entityStirngBuffer.append("isInvoice = " +  this.getIsInvoice() + ",");
		entityStirngBuffer.append("isSetmeal = " +  this.getIsSetmeal() + ",");
		entityStirngBuffer.append("isTicket = " +  this.getIsTicket() + ",");
		entityStirngBuffer.append("ticketMoney = " +  this.getTicketMoney() + ",");
		entityStirngBuffer.append("paymentMoney = " +  this.getPaymentMoney() + ",");
		entityStirngBuffer.append("paymentType = " +  this.getPaymentType() + ",");
		entityStirngBuffer.append("orderState = " +  this.getOrderState() + ",");
		entityStirngBuffer.append("orderTime = " +  this.getOrderTime() + ",");
		entityStirngBuffer.append("paymentTime = " +  this.getPaymentTime() + ",");
		entityStirngBuffer.append("extensioncodeId = " +  this.getExtensioncodeId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("wtOrderMes = " +  this.getWtOrderMes() + ",");
		entityStirngBuffer.append("paymentCode = " +  this.getPaymentCode() + ",");
		entityStirngBuffer.append("wtInvoice = " +  this.getWtInvoice() + ",");
		entityStirngBuffer.append("phone = " +  this.getPhone() + ",");
		entityStirngBuffer.append("openId = " +  this.getOpenId() + ",");
		return entityStirngBuffer.toString();
	}

}