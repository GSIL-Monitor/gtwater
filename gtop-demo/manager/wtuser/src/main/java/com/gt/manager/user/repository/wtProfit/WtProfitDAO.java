package com.gt.manager.user.repository.wtProfit;

import com.gt.manager.entity.wtProfit.WtProfit;
import com.gt.manager.user.entity.profit.ProfitDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分佣表
 * @author why
 */
@Mapper
public interface WtProfitDAO {

	// Methods

   /**
	* 插入分佣表
	* @param wtProfit 参数实体
	* @return null 插入后的数据库主键值
	*/
	public Long insert(WtProfit wtProfit);

   /**
	* 删除分佣表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新分佣表
	* @param wtProfit 参数实体
	*/
	public void update(WtProfit wtProfit);

   /**
	* 主键查询分佣表
	* @param id 主键值
	* @return WtProfit 实体
	*/
	public WtProfit selectById(Long id);

   /**
	* 根据条件查询分佣表列表
	* @param wtProfit 参数实体
	* @return List<WtProfit> 实体List
	*/
	public List<WtProfit> selectList(WtProfit wtProfit);

	public List<Map> selectByCondition(HashMap<String, Object> params);

}