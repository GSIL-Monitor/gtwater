package com.gt.manager.order.repository.wtSku;

import com.gt.manager.entity.wtSku.WtSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品sku表
 * @author why
 */
@Mapper
public interface WtSkuDAO {

	// Methods

   /**
	* 插入商品sku表
	* @param wtSku 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtSku wtSku);

   /**
	* 删除商品sku表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新商品sku表
	* @param wtSku 参数实体
	*/
	public void update(WtSku wtSku);

   /**
	* 主键查询商品sku表
	* @param id 主键值
	* @return WtSku 实体
	*/
	public WtSku selectById(Long id);

   /**
	* 根据条件查询商品sku表列表
	* @param wtSku 参数实体
	* @return List<WtSku> 实体List
	*/
	public List<WtSku> selectList(WtSku wtSku);

	public Map selectGoods(HashMap<String, Object> params);

}