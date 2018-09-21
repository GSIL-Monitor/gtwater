package com.gt.manager.product.repository;

import com.gt.manager.entity.gtopSku.GtopSku;
import java.util.List;

import com.gt.manager.entity.wtSku.WtSku;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品sku表
 * @author why
 */
@Mapper
public interface GtopSkuDAO {

	// Methods

   /**
	* 插入商品sku表
	* @param gtopSku 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(GtopSku gtopSku);

   /**
	* 删除商品sku表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新商品sku表
	* @param gtopSku 参数实体
	*/
	public void update(GtopSku gtopSku);

   /**
	* 主键查询商品sku表
	* @param id 主键值
	* @return GtopSku 实体
	*/
	public GtopSku selectById(Long id);

   /**
	* 根据条件查询商品sku表列表
	* @param gtopSku 参数实体
	* @return List<GtopSku> 实体List
	*/
	public List<GtopSku> selectList(GtopSku gtopSku);
	/**
	 * 根据商品ID查询sku信息列表
	 *
	 * @param productId
	 * @return
	 * @throws Exception
	 */

	public List<GtopSku> getWtSkuListByProductId(Long productId);

}