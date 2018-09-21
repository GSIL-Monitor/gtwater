package com.gt.manager.order.repository;

import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtadmin.OrderRecordEntity;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 订单
 * @author why
 */
@Mapper
public interface WtOrderDAO {

	// Methods

   /**
	* 插入订单
	* @param wtOrder 参数实体
	* @return null 插入后的数据库主键值
	*/
	public Long insert(WtOrder wtOrder);

   /**
	* 删除订单
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新订单
	* @param wtOrder 参数实体
	*/
	public void update(WtOrder wtOrder);

   /**
	* 主键查询订单
	* @param id 主键值
	* @return WtOrder 实体
	*/
	public WtOrder selectById(Long id);

   /**
	* 根据条件查询订单列表
	* @param wtOrder 参数实体
	* @return List<WtOrder> 实体List
	*/
	public List<WtOrder> selectList(WtOrder wtOrder);

	/**
	 * 根据条件查询订单信息
	 * @param map
	 * @return
	 */
	public List<OrderRecordEntity> getOrderRecordList(Map<String,Object> map);

	/**
	 * 根据订单编号获取商品信息
	 * @param orderCode
	 * @return
	 */
	public List<ProductMsgEntity> getProductListByOrderCode(String orderCode);
}