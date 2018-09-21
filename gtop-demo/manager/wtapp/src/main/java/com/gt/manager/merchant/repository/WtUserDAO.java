package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtUser.WtUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息表
 * @author why
 */
@Mapper
public interface WtUserDAO {

	// Methods

   /**
	* 插入用户信息表
	* @param wtUser 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtUser wtUser);

   /**
	* 删除用户信息表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新用户信息表
	* @param wtUser 参数实体
	*/
	public void update(WtUser wtUser);

   /**
	* 主键查询用户信息表
	* @param id 主键值
	* @return WtUser 实体
	*/
	public WtUser selectById(Long id);

   /**
	* 根据条件查询用户信息表列表
	* @param wtUser 参数实体
	* @return List<WtUser> 实体List
	*/
	public List<WtUser> selectList(WtUser wtUser);

}