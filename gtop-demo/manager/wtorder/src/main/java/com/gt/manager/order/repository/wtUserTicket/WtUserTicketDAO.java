package com.gt.manager.order.repository.wtUserTicket;

import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
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

	public List<Map> selectMyTicket(HashMap<String, Object> params);

	public List<WtUserTicket> selectByUseridAndSkuCodeAsc(HashMap<String, Object> params);

	public List<WtUserTicket> selectByUseridAndSkuCodeDesc(HashMap<String, Object> params);



	/**
	 * 插入用户水票表
	 */
	Long insertLog(WtTicketLog wt);
	/**
	 * 修改回填水票余量
	 */
	Long updateUserTick (WtUserTicket wt);

	public void updateMinusById(WtUserTicket wtUserTicket);

	public void updateAddById(WtUserTicket wtUserTicket);


}