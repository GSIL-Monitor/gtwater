package com.gt.manager.ticket.repository;

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
	public int delete(Long id);

   /**
	* 更新水票消费记录表
	* @param wtTicketLog 参数实体
	*/
	public int update(WtTicketLog wtTicketLog);

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

}