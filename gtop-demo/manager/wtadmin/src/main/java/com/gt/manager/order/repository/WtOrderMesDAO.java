package com.gt.manager.order.repository;

import com.gt.manager.entity.wtOrderMes.WtOrderMes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单详情
 * @author why
 */
@Mapper
public interface WtOrderMesDAO {

	// Methods

   /**
	* 插入订单详情
	* @param wtOrderMes 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtOrderMes wtOrderMes);

   /**
	* 删除订单详情
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新订单详情
	* @param wtOrderMes 参数实体
	*/
	public void update(WtOrderMes wtOrderMes);

   /**
	* 主键查询订单详情
	* @param id 主键值
	* @return WtOrderMes 实体
	*/
	public WtOrderMes selectById(Long id);

   /**
	* 根据条件查询订单详情列表
	* @param wtOrderMes 参数实体
	* @return List<WtOrderMes> 实体List
	*/
	public List<WtOrderMes> selectList(WtOrderMes wtOrderMes);


	/**
	 * 根据订单编号查询订单详情
	 * @param orderCode
	 * @return
	 */
	public List<WtOrderMes> selectByOrderCode(String orderCode);
}