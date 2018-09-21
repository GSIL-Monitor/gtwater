package com.gt.manager.order.repository.receiveAddress;

import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 买家收货地址表
 * @author why
 */
@Mapper
public interface ReceiveAddressDAO {

	// Methods

   /**
	* 插入买家收货地址表
	* @param receiveAddress 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(ReceiveAddress receiveAddress);

   /**
	* 删除买家收货地址表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新买家收货地址表
	* @param receiveAddress 参数实体
	*/
	public void update(ReceiveAddress receiveAddress);

   /**
	* 主键查询买家收货地址表
	* @param id 主键值
	* @return ReceiveAddress 实体
	*/
	public ReceiveAddress selectById(Long id);

   /**
	* 根据条件查询买家收货地址表列表
	* @param receiveAddress 参数实体
	* @return List<ReceiveAddress> 实体List
	*/
	public List<ReceiveAddress> selectList(ReceiveAddress receiveAddress);

}