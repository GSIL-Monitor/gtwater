package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户水票表
 * @author why
 */
@Mapper
public interface WtUserTicketDAO {

	// Methods

   /**
	* 插入用户水票表
	* @param wtUserTicket 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtUserTicket wtUserTicket);

   /**
	* 删除用户水票表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新用户水票表
	* @param wtUserTicket 参数实体
	*/
	public void update(WtUserTicket wtUserTicket);

   /**
	* 主键查询用户水票表
	* @param id 主键值
	* @return WtUserTicket 实体
	*/
	public WtUserTicket selectById(Long id);

   /**
	* 根据条件查询用户水票表列表
	* @param wtUserTicket 参数实体
	* @return List<WtUserTicket> 实体List
	*/
	public List<WtUserTicket> selectList(WtUserTicket wtUserTicket);

	/**
	 * 更新用户水票余量在原基础上累加
	 * @param condition
	 */
    void updateSurplusNum(Map<String, Object> condition);
}