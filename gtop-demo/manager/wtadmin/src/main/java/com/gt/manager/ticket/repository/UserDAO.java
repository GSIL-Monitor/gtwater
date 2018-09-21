package com.gt.manager.ticket.repository;

import com.gt.manager.entity.user.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * (杂货铺)用户信息表
 * @author why
 */
@Mapper
public interface UserDAO {

	// Methods

   /**
	* 插入(杂货铺)用户信息表
	* @param user 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(User user);

   /**
	* 删除(杂货铺)用户信息表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新(杂货铺)用户信息表
	* @param user 参数实体
	*/
	public void update(User user);

   /**
	* 主键查询(杂货铺)用户信息表
	* @param id 主键值
	* @return User 实体
	*/
	public User selectById(Long id);

   /**
	* 根据条件查询(杂货铺)用户信息表列表
	* @param user 参数实体
	* @return List<User> 实体List
	*/
	public List<User> selectList(User user);

}