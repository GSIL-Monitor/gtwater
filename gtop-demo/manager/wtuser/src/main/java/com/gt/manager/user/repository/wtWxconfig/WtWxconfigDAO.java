package com.gt.manager.user.repository.wtWxconfig;

import com.gt.manager.entity.wtWxconfig.WtWxconfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 水管家微信配置信息表
 * @author why
 */
@Mapper
public interface WtWxconfigDAO {

	// Methods

   /**
	* 插入水管家微信配置信息表
	* @param wtWxconfig 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtWxconfig wtWxconfig);

   /**
	* 删除水管家微信配置信息表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新水管家微信配置信息表
	* @param wtWxconfig 参数实体
	*/
	public void update(WtWxconfig wtWxconfig);

   /**
	* 主键查询水管家微信配置信息表
	* @param id 主键值
	* @return WtWxconfig 实体
	*/
	public WtWxconfig selectById(Long id);

   /**
	* 根据条件查询水管家微信配置信息表列表
	* @param wtWxconfig 参数实体
	* @return List<WtWxconfig> 实体List
	*/
	public List<WtWxconfig> selectList(WtWxconfig wtWxconfig);

}