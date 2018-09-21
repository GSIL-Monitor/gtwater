package com.gt.manager.product.repository;

import com.gt.manager.entity.wtProduct.WtProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构_商品_价格表
 * @author why
 */
@Mapper
public interface WtProductDAO {

	// Methods

   /**
	* 插入机构_商品_价格表
	* @param wtProduct 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtProduct wtProduct);

   /**
	* 删除机构_商品_价格表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新机构_商品_价格表
	* @param wtProduct 参数实体
	*/
	public void update(WtProduct wtProduct);

   /**
	* 主键查询机构_商品_价格表
	* @param id 主键值
	* @return WtProduct 实体
	*/
	public WtProduct selectById(Long id);

   /**
	* 根据条件查询机构_商品_价格表列表
	* @param wtProduct 参数实体
	* @return List<WtProduct> 实体List
	*/
	public List<WtProduct> selectList(WtProduct wtProduct);

	/**
	 * 根据平台商品id查询水管家商品信息
	 * @param gtopGoodsId
	 * @return
	 */
	public WtProduct getWtProductByGtopGoodsId(Long gtopGoodsId);

	/**
	 * 根据品牌ID和二级分类编号查询商品信息列表
	 *
	 * @param categoryId
	 * @param brandId
	 * @return
	 * @throws Exception
	 */

	public List<WtProduct> getWtProductListByCategoryCodeBrandId(@Param("categoryCode")String categoryCode, @Param("brandId")Long brandId);

	/**
	 * 根据组织ID查询商品信息
	 * @param branchesId
	 * @return
	 */
	public List<WtProduct> getProductListByBranchesId(Long branchesId);

	/**
	 * 根据用户ID获取商品信息
	 * @param userId
	 * @return
	 */
	public List<WtProduct> getProductListByUserId(Long userId);

}