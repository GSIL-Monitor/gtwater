package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 水票消费记录表
 * @author why
 */
@Mapper
public interface WtTicketLogDAO {

	// Methods

   /**
	* 插入水票消费记录表
	* @param wtTicketLog 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtTicketLog wtTicketLog);

   /**
	* 删除水票消费记录表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新水票消费记录表
	* @param wtTicketLog 参数实体
	*/
	public void update(WtTicketLog wtTicketLog);

   /**
	* 主键查询水票消费记录表
	* @param id 主键值
	* @return WtTicketLog 实体
	*/
	public WtTicketLog selectById(Long id);

   /**
	* 根据条件查询水票消费记录表列表
	* @param wtTicketLog 参数实体
	* @return List<WtTicketLog> 实体List
	*/
	public List<WtTicketLog> selectList(WtTicketLog wtTicketLog);

	/**
	 * 根据派单明细得到消费的水票记录的集合，且按订单下单时间倒序
	 * @param wtTicketLog
	 * @return
	 */
    List<WtTicketLog> selectListOrderByOrderTimeDesc(WtTicketLog wtTicketLog);
}