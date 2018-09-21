package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 机构套餐
 * @author why
 */
@Mapper
public interface WtOrgSetmealDAO {

	// Methods

   /**
	* 插入机构套餐
	* @param wtOrgSetmeal 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtOrgSetmeal wtOrgSetmeal);

   /**
	* 删除机构套餐
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新机构套餐
	* @param wtOrgSetmeal 参数实体
	*/
	public void update(WtOrgSetmeal wtOrgSetmeal);

   /**
	* 主键查询机构套餐
	* @param id 主键值
	* @return WtOrgSetmeal 实体
	*/
	public WtOrgSetmeal selectById(Long id);

   /**
	* 根据条件查询机构套餐列表
	* @param wtOrgSetmeal 参数实体
	* @return List<WtOrgSetmeal> 实体List
	*/
	public List<WtOrgSetmeal> selectList(WtOrgSetmeal wtOrgSetmeal);

}