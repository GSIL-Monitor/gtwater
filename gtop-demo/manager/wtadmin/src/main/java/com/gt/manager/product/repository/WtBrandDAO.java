package com.gt.manager.product.repository;


import com.gt.manager.entity.wtBrand.WtBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌表
 * @author why
 */
@Mapper
public interface WtBrandDAO {

	// Methods

   /**
	* 插入品牌表
	* @param wtBrand 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtBrand wtBrand);

   /**
	* 删除品牌表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新品牌表
	* @param wtBrand 参数实体
	*/
	public void update(WtBrand wtBrand);

   /**
	* 主键查询品牌表
	* @param id 主键值
	* @return WtBrand 实体
	*/
	public WtBrand selectById(Long id);

   /**
	* 根据条件查询品牌表列表
	* @param wtBrand 参数实体
	* @return List<WtBrand> 实体List
	*/
	public List<WtBrand> selectList(WtBrand wtBrand);

	/**
	 * 根据gtop品牌ID查询水管家品牌信息
	 * @param gtopBrandId
	 * @return
	 */
	public WtBrand getWtBrandByGtopBrandId(Long gtopBrandId);

	/**
	 * 根据二级分类编号查询所属品牌信息
	 * @param categoryCode
	 * @return
	 */
	public List<WtBrand> getWtBrandListByCategoryCode(String categoryCode);

}