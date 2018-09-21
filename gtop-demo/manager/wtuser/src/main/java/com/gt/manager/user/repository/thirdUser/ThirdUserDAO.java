package com.gt.manager.user.repository.thirdUser;

import java.util.List;

import com.gt.manager.entity.thirdUser.ThirdUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 第三方鉴权对照表
 * @author why
 */
@Mapper
public interface ThirdUserDAO {

	// Methods

   /**
	* 插入第三方鉴权对照表
	* @param thirdUser 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(ThirdUser thirdUser);

   /**
	* 删除第三方鉴权对照表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新第三方鉴权对照表
	* @param thirdUser 参数实体
	*/
	public void update(ThirdUser thirdUser);

   /**
	* 主键查询第三方鉴权对照表
	* @param id 主键值
	* @return ThirdUser 实体
	*/
	public ThirdUser selectById(Long id);

   /**
	* 根据条件查询第三方鉴权对照表列表
	* @param thirdUser 参数实体
	* @return List<ThirdUser> 实体List
	*/
	public List<ThirdUser> selectList(ThirdUser thirdUser);

}