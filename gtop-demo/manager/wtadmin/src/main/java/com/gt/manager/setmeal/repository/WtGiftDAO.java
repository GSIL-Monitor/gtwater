package com.gt.manager.setmeal.repository;

import com.gt.manager.entity.wtGift.WtGift;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 赠品记录
 * @author why
 */
@Mapper
public interface WtGiftDAO {

	// Methods

   /**
	* 插入赠品记录
	* @param wtGift 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtGift wtGift);

   /**
	* 删除赠品记录
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新赠品记录
	* @param wtGift 参数实体
	*/
	public int update(WtGift wtGift);

   /**
	* 主键查询赠品记录
	* @param id 主键值
	* @return WtGift 实体
	*/
	public WtGift selectById(Long id);

   /**
	* 根据条件查询赠品记录列表
	* @param wtGift 参数实体
	* @return List<WtGift> 实体List
	*/
	public List<WtGift> selectList(WtGift wtGift);

	/**
	 * 批量插入赠品记录
	 * @param -wtGift 参数实体
	 * @return id 插入后的数据库主键值
	 */
	public Long insertBatch(Map<String,Object> map);

	/**
	 * 根据套系编号查询赠品信息
	 * @param seriesSkuCode
	 * @return
	 * @throws Exception
	 */
	public List<ProductMsgEntity> getGiftProductMsgBySeriesSkuCode(String seriesSkuCode);

}