package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtProfitSystem.WtProfitSystem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 平台分佣表
 * @author why
 */
@Mapper
public interface WtProfitSystemDAO {

	// Methods

   /**
	* 插入平台分佣表
	* @param wtProfitSystem 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtProfitSystem wtProfitSystem);

   /**
	* 删除平台分佣表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新平台分佣表
	* @param wtProfitSystem 参数实体
	*/
	public void update(WtProfitSystem wtProfitSystem);

   /**
	* 主键查询平台分佣表
	* @param id 主键值
	* @return WtProfitSystem 实体
	*/
	public WtProfitSystem selectById(Long id);

   /**
	* 根据条件查询平台分佣表列表
	* @param wtProfitSystem 参数实体
	* @return List<WtProfitSystem> 实体List
	*/
	public List<WtProfitSystem> selectList(WtProfitSystem wtProfitSystem);

	/**
	 * 平台分拥明细批量插入
	 * @param wtProfitSystemList
	 */
    void batchInsert(List<WtProfitSystem> wtProfitSystemList);
}