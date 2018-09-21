package com.gt.manager.order.repository.wtSend;

import com.gt.manager.entity.wtSend.WtSend;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 派送订单
 * @author why
 */
@Mapper
public interface WtSendDAO {

	// Methods

   /**
	* 插入派送订单
	* @param wtSend 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtSend wtSend);

   /**
	* 删除派送订单
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新派送订单
	* @param wtSend 参数实体
	*/
	public void update(WtSend wtSend);

   /**
	* 主键查询派送订单
	* @param id 主键值
	* @return WtSend 实体
	*/
	public WtSend selectById(Long id);

   /**
	* 根据条件查询派送订单列表
	* @param wtSend 参数实体
	* @return List<WtSend> 实体List
	*/
	public List<WtSend> selectList(WtSend wtSend);

}