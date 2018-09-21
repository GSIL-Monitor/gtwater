package com.gt.manager.ticket.repository;

import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (杂货铺)买家收货地址表
 * @author why
 */
@Mapper
public interface ReceiveAddressDAO {

	// Methods

   /**
	* 插入(杂货铺)买家收货地址表
	* @param receiveAddress 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(ReceiveAddress receiveAddress);

   /**
	* 删除(杂货铺)买家收货地址表
	* @param id 主键值
	*/
	public int delete(Long id);

   /**
	* 更新(杂货铺)买家收货地址表
	* @param receiveAddress 参数实体
	*/
	public int update(ReceiveAddress receiveAddress);

   /**
	* 主键查询(杂货铺)买家收货地址表
	* @param id 主键值
	* @return ReceiveAddress 实体
	*/
	public ReceiveAddress selectById(Long id);

   /**
	* 根据条件查询(杂货铺)买家收货地址表列表
	* @param receiveAddress 参数实体
	* @return List<ReceiveAddress> 实体List
	*/
	public List<ReceiveAddress> selectList(ReceiveAddress receiveAddress);

	/**
	 * 根据用户ID获取用户联系方式
	 * @param userId
	 * @return
	 */
	public ReceiveAddress selectByUserId(
			@Param("userId") Long userId,
			@Param("phone")String phone,
			@Param("address") String address);

	/**
	 * 根据电话查询用户信息
	 * @param phone
	 * @return
	 */
	public List<ReceiveAddress> selectListByPhone(String phone);

}