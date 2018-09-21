package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtProfit.WtProfit;
import com.gt.manager.merchant.entity.response.WaterstoreSaleReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    Long selectByUserId(Long userId);

	/**
	 * 得到水站老板自己的当天派送量及收益
	 * @param branchesId
	 * @return
	 */

	Map<String,Object> getWaterstoreProfitByDay(Long branchesId);

	/**
	 * 得到水站老板每周的派送量及收益
	 * @param branchesId
	 * @return
	 */

	Map<String,Object> getWaterstoreProfitByWeek(Long branchesId);

	/**
	 * 得到水站老板老板每月的派送量及收益
	 * @param branchesId
	 * @return
	 */

	Map<String,Object> getWaterstoreProfitByMonth(Long branchesId);

	/**
	 * 得到城市总自己的当天派送量及收益
	 * @param branchesId
	 * @return
	 */

	Map<String,Object> getCityProfitByDay(Long branchesId);
	/**
	 * 得到城市总自己的本周派送量及收益
	 * @param branchesId
	 * @return
	 */
	Map<String,Object> getCityProfitByWeek(Long branchesId);

	/**
	 * 得到城市总自己的本月派送量及收益
	 * @param branchesId
	 * @return
	 */
	Map<String,Object> getCityProfitByMonth(Long branchesId);

	/**
	 * 根据城市机构平台编号获取平台下所有水站的默认当天的，可选择时间段询，每个店铺的流水与分佣总额集合
	 * @param ids
	 * @return
	 */
    List<Map<String,Object>> getCityProfitDetailByBranchesId(@Param("list") List<Long> ids, @Param("startTime") Long startTime,
															 @Param("endTime") Long endTime);

	/**
	 * 根据水站branchesId下每个派单的销售信息,可按品牌查询、日期查询
	 * @param waterstoreId
	 * @param brandName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    List<WaterstoreSaleReport> getWaterstoreSaleReport(@Param("waterstoreId") Long waterstoreId, @Param("brandName") String brandName,
													   @Param("startTime") Long startTime, @Param("endTime") Long endTime);
}