package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtProfitCity.WtProfitCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 平台分佣表
 * @author why
 */
@Mapper
public interface WtProfitCityDAO {

	// Methods

   /**
	* 插入平台分佣表
	* @param wtProfitCity 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtProfitCity wtProfitCity);

   /**
	* 删除平台分佣表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新平台分佣表
	* @param wtProfitCity 参数实体
	*/
	public void update(WtProfitCity wtProfitCity);

   /**
	* 主键查询平台分佣表
	* @param id 主键值
	* @return WtProfitCity 实体
	*/
	public WtProfitCity selectById(Long id);

   /**
	* 根据条件查询平台分佣表列表
	* @param wtProfitCity 参数实体
	* @return List<WtProfitCity> 实体List
	*/
	public List<WtProfitCity> selectList(WtProfitCity wtProfitCity);

	/**
	 * 批量插入城市分佣
	 * @param wtProfitCityArrayList
	 */
    void batchInsert(List<WtProfitCity> wtProfitCityArrayList);
}