package com.gt.manager.ticket.repository;

import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtadmin.TicketRecordEntity;
import com.gt.manager.entity.wtadmin.UserSimpleEntity;
import com.gt.manager.entity.wtadmin.WtTicketSimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	public int update(WtUserTicket wtUserTicket);

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
	 * 根据电话获取用户简单信息
	 * @param phone
	 * @return
	 */
	public UserSimpleEntity getUserSimpleByPhone(String phone);


	/**
	 * 根据用户ID查询用户水票简要信息
	 * @param userId
	 * @return
	 */
	public List<WtTicketSimple> getWtTicketSimpleByuserId(Long userId);

	/**
	 * 根据用户ID和skuCode获取水票信息
	 * @param userId
	 * @param skuCode
	 * @return
	 */
	public List<WtUserTicket> getWtUserTicketListByUserIdSkuCode(
			@Param("userId") Long userId,
			@Param("skuCode") String skuCode);


	//surplusNum
	public void updateTicketSurplusNum(@Param("surplusNum")Integer surplusNum,
									   @Param("updateTime")Long updateTime,
									   @Param("id") Long id);


	/**
	 * 根据条件查询水票信息
	 * @param map
	 * @return
	 */
	public List<TicketRecordEntity> getTicketRecordList(Map<String,Object> map);

	/**
	 * 根据条件导出水票信息
	 * @param map
	 * @return
	 */
	public List<TicketRecordEntity> exportTicketRecordList(Map<String,Object> map);
}