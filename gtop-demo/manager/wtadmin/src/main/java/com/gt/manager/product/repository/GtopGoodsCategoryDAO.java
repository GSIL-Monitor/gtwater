package com.gt.manager.product.repository;

import com.gt.manager.entity.gtopGoodsCategory.GtopGoodsCategory;
import java.util.List;

import com.gt.manager.entity.productCategory.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类别DAO接口
 * @author why
 */
@Mapper
public interface GtopGoodsCategoryDAO {

	// Methods

   /**
	* 插入商品类别
	* @param gtopGoodsCategory 参数实体
	* @return categoryId 插入后的数据库主键值
	*/
	public Long insert(GtopGoodsCategory gtopGoodsCategory);

   /**
	* 删除商品类别
	* @param categoryId 主键值
	*/
	public void delete(Long categoryId);

   /**
	* 更新商品类别
	* @param gtopGoodsCategory 参数实体
	*/
	public void update(GtopGoodsCategory gtopGoodsCategory);

   /**
	* 主键查询商品类别
	* @param categoryId 主键值
	* @return GtopGoodsCategory 实体
	*/
	public GtopGoodsCategory selectById(Long categoryId);

   /**
	* 根据条件查询商品类别列表
	* @param gtopGoodsCategory 参数实体
	* @return List<GtopGoodsCategory> 实体List
	*/
	public List<GtopGoodsCategory> selectList(GtopGoodsCategory gtopGoodsCategory);

	/**
	 * 根据平台ID查询一级分类列表
	 * @param platformId
	 * @return
	 */
	public List<GtopGoodsCategory> getParentCategoryListByPlatformId(Integer platformId);

	/**
	 * 根据一级分类ID查询二级分类列表
	 *
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<GtopGoodsCategory> getCategoryListByParentCategoryId(Long categoryId);

}