package com.gt.manager.user.repository.wtExtensioncode;

import com.gt.manager.entity.wtExtensioncode.WtExtensioncode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 推广码信息表
 * @author why
 */
@Mapper
public interface WtExtensioncodeDAO {

	// Methods

   /**
	* 插入推广码信息表
	* @param wtExtensioncode 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtExtensioncode wtExtensioncode);

   /**
	* 删除推广码信息表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新推广码信息表
	* @param wtExtensioncode 参数实体
	*/
	public void update(WtExtensioncode wtExtensioncode);

   /**
	* 主键查询推广码信息表
	* @param id 主键值
	* @return WtExtensioncode 实体
	*/
	public WtExtensioncode selectById(Long id);

   /**
	* 根据条件查询推广码信息表列表
	* @param wtExtensioncode 参数实体
	* @return List<WtExtensioncode> 实体List
	*/
	public List<WtExtensioncode> selectList(WtExtensioncode wtExtensioncode);

}