package com.gt.manager.product.repository;

import com.gt.manager.entity.productCategory.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品类别DAO接口
 * @author why
 */
@Mapper
public interface ProductCategoryDAO {

	// Methods

   /**
	* 插入商品类别
	* @param productCategory 参数实体
	* @return categoryId 插入后的数据库主键值
	*/
	public Long insert(ProductCategory productCategory);

   /**
	* 删除商品类别
	* @param categoryId 主键值
	*/
	public void delete(Long categoryId);

   /**
	* 更新商品类别
	* @param productCategory 参数实体
	*/
	public void update(ProductCategory productCategory);

   /**
	* 主键查询商品类别
	* @param categoryId 主键值
	* @return GoodsCategory 实体
	*/
	public ProductCategory selectById(Long categoryId);

   /**
	* 根据条件查询商品类别列表
	* @param productCategory 参数实体
	* @return List<GoodsCategory> 实体List
	*/
	public List<ProductCategory> selectList(ProductCategory productCategory);

	/**
	 * 根据gtop分类ID查询水管家分类信息
	 * @param gtopCategoryId
	 * @return
	 */
	public ProductCategory getProductCategoryByGtopCategoryId(Long gtopCategoryId);

	/**
	 * 获取二级分类
	 *
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategory> getCategoriesListByLevel(@Param("level") Integer level);

	/**
	 * 根据平台ID查询一级分类列表
	 * @param platformId
	 * @return
	 */
	public List<ProductCategory> getParentCategoryListByPlatformId(Long platformId);

	/**
	 * 根据一级分类ID查询二级分类列表
	 *
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<ProductCategory> getCategoryListByParentCategoryId(Long categoryId);

}