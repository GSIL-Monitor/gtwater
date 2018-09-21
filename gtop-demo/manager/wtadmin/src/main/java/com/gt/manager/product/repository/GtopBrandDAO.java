package com.gt.manager.product.repository;

import com.gt.manager.entity.gtopBrand.GtopBrand;
import java.util.List;

import com.gt.manager.entity.wtBrand.WtBrand;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌表
 * @author why
 */
@Mapper
public interface GtopBrandDAO {

	// Methods

   /**
	* 插入品牌表
	* @param gtopBrand 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(GtopBrand gtopBrand);

   /**
	* 删除品牌表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新品牌表
	* @param gtopBrand 参数实体
	*/
	public void update(GtopBrand gtopBrand);

   /**
	* 主键查询品牌表
	* @param id 主键值
	* @return GtopBrand 实体
	*/
	public GtopBrand selectById(Long id);

   /**
	* 根据条件查询品牌表列表
	* @param gtopBrand 参数实体
	* @return List<GtopBrand> 实体List
	*/
	public List<GtopBrand> selectList(GtopBrand gtopBrand);
	/**
	 * 根据二级分类编号查询所属品牌信息
	 * @param categoryCode
	 * @return
	 */
	public List<GtopBrand> getWtBrandListByCategoryCode(String categoryCode);

}