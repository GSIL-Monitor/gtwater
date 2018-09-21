package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.merchant.entity.response.WtSendResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

	/**
	 * 根据派送编号获取派送信息
	 * @param sendCode
	 * @return
	 */
    WtSendResponse selectBySendCode(String sendCode);

	/**
	 * 根据指定的派单编号进行接单操作状态
	 * @param sendCode
	 */
	void updateOrderReceivingStatus(String sendCode);

	/**
	 * 确认派单状态和送达时间
	 * @param map
	 */
	void updateConfirmStatus(Map<String, Object> map);

	/**
	 * 查看不同状态的订单的总数量
	 * @param status
	 * @return
	 */
    Integer selectCountByStatus(@Param("status") Integer status, @Param("waterstoreId") Long waterstoreId);

	/**
	 * 查询带催单次数的列表
	 * @param wtSend
	 * @return
	 */
	List<WtSendResponse> selectListAndUrgeTimes(WtSend wtSend);
}