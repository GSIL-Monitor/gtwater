package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtProfitWaterstore.WtProfitWaterstore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分佣(水站)表
 * @author why
 */
@Mapper
public interface WtProfitWaterstoreDAO {

	// Methods

   /**
	* 插入分佣(水站)表
	* @param wtProfitWaterstore 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtProfitWaterstore wtProfitWaterstore);

   /**
	* 删除分佣(水站)表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新分佣(水站)表
	* @param wtProfitWaterstore 参数实体
	*/
	public void update(WtProfitWaterstore wtProfitWaterstore);

   /**
	* 主键查询分佣(水站)表
	* @param id 主键值
	* @return WtProfitWaterstore 实体
	*/
	public WtProfitWaterstore selectById(Long id);

   /**
	* 根据条件查询分佣(水站)表列表
	* @param wtProfitWaterstore 参数实体
	* @return List<WtProfitWaterstore> 实体List
	*/
	public List<WtProfitWaterstore> selectList(WtProfitWaterstore wtProfitWaterstore);

    void batchInsert(List<WtProfitWaterstore> wtProfitWaterstoreArrayList);
}