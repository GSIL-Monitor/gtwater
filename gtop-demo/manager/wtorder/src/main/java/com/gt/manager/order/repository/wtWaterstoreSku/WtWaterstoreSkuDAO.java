package com.gt.manager.order.repository.wtWaterstoreSku;

import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

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

}