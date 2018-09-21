package com.gt.manager.product.repository;

import com.gt.manager.entity.wtSku.WtSku;
import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 水站商品sku表
 * @author why
 */
@Mapper
public interface WtWaterstoreSkuDAO {

	// Methods

   /**
	* 插入水站商品sku表
	* @param wtWaterstoreSku 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtWaterstoreSku wtWaterstoreSku);

   /**
	* 删除水站商品sku表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新水站商品sku表
	* @param wtWaterstoreSku 参数实体
	*/
	public void update(WtWaterstoreSku wtWaterstoreSku);

   /**
	* 主键查询水站商品sku表
	* @param id 主键值
	* @return WtWaterstoreSku 实体
	*/
	public WtWaterstoreSku selectById(Long id);

   /**
	* 根据条件查询水站商品sku表列表
	* @param wtWaterstoreSku 参数实体
	* @return List<WtWaterstoreSku> 实体List
	*/
	public List<WtWaterstoreSku> selectList(WtWaterstoreSku wtWaterstoreSku);

	/**
	 * 批量上下架
	 * 状态[1销售中（上架），2下架]
	 * @param status 状态
	 * @param skuList
	 */
	public void setSkuStatusBySkuCodeList(Map<String,Object> map);


	/**
	 * 根据skuId批量更新状态
	 * @param map
	 */
	public void setSkuStatusByIdList(Map<String,Object> map);

	/**
	 * 批量更改删除状态
	 * 状态[1销售中（上架），2下架]
	 * @param status 状态
	 * @param delStata   删除状态  【1正常---0删除】
	 * @param skuList
	 */
	public void setSkuDelStataBySkuCodeList(Map<String,Object> map);

	/**
	 * 批量上下架
	 * 状态[1销售中（上架），2下架]
	 * @param status 状态
	 * @param skuList
	 */
	public void setSkuStatusByWtSkuList(@Param("loginId")Long loginId,@Param("updateTime")Long updateTime,@Param("status")Integer status, @Param("skuList")List<WtSku> wtSkuList);

	/**
	 * 批量更改删除状态
	 * 状态[1销售中（上架），2下架]
	 * @param status 状态
	 * @param delStata   删除状态  【1正常---0删除】
	 * @param skuList
	 */
	public void setSkuDelStataByWtSkuList(@Param("loginId")Long loginId,@Param("updateTime")Long updateTime,@Param("delStata") Integer delStata,@Param("status")Integer status,@Param("skuList")List<WtSku> wtSkuList);


	public void setDelStatusBySkuCodeBranchesId(Map<String,Object> map);

	/**
	 * 根据城市机构ID查询水站机构ID列表
	 * @param cityBranchesId
	 * @return
	 */
	public List<Long> selectWaterBranchesIdListByCityBranchesId(Map<String,Object> map);
}