package com.gt.manager.product.repository;

import com.gt.manager.entity.gtopProduct.GtopProduct;
import java.util.List;

import com.gt.manager.entity.wtProduct.WtProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构_商品_价格表
 * @author why
 */
@Mapper
public interface GtopProductDAO {

	// Methods

   /**
	* 插入机构_商品_价格表
	* @param gtopProduct 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(GtopProduct gtopProduct);

   /**
	* 删除机构_商品_价格表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新机构_商品_价格表
	* @param gtopProduct 参数实体
	*/
	public void update(GtopProduct gtopProduct);

   /**
	* 主键查询机构_商品_价格表
	* @param id 主键值
	* @return GtopProduct 实体
	*/
	public GtopProduct selectById(Long id);

   /**
	* 根据条件查询机构_商品_价格表列表
	* @param gtopProduct 参数实体
	* @return List<GtopProduct> 实体List
	*/
	public List<GtopProduct> selectList(GtopProduct gtopProduct);
	/**
	 * 根据品牌ID和二级分类编号查询商品信息列表
	 *
	 * @param categoryId
	 * @param brandId
	 * @return
	 * @throws Exception
	 */

	public List<GtopProduct> getWtProductListByCategoryCodeBrandId(@Param("categoryCode")String categoryCode, @Param("brandId")Long brandId);

}