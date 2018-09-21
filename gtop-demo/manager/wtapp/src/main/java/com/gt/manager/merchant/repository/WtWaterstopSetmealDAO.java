package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtWaterstopSetmeal.WtWaterstopSetmeal;
import java.util.List;
import java.util.Map;

import com.gt.manager.merchant.entity.response.WtWaterstoreSkuResponse;
import org.apache.ibatis.annotations.Mapper;

/**
 * 水站套餐
 * @author why
 */
@Mapper
public interface WtWaterstopSetmealDAO {

	// Methods

   /**
	* 插入水站套餐
	* @param wtWaterstopSetmeal 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtWaterstopSetmeal wtWaterstopSetmeal);

   /**
	* 删除水站套餐
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新水站套餐
	* @param wtWaterstopSetmeal 参数实体
	*/
	public void update(WtWaterstopSetmeal wtWaterstopSetmeal);

   /**
	* 主键查询水站套餐
	* @param id 主键值
	* @return WtWaterstopSetmeal 实体
	*/
	public WtWaterstopSetmeal selectById(Long id);

   /**
	* 根据条件查询水站套餐列表
	* @param wtWaterstopSetmeal 参数实体
	* @return List<WtWaterstopSetmeal> 实体List
	*/
	public List<WtWaterstopSetmeal> selectList(WtWaterstopSetmeal wtWaterstopSetmeal);

	/**
	 *商家套餐数据查找
	 * @param values
	 * @return
	 */
	List<WtWaterstoreSkuResponse> findPage(Map<String, String> values);

	/**
	 * 套餐上下架
	 * @param values
	 */

    void updateStatus(Map<String, Object> values);

	/**
	 * 商家可从商品库添加的商品套餐
	 * @param param
	 * @return
	 */
	List<WtWaterstoreSkuResponse> findAddibleGoodsPage(Map<String, String> param);


	/**
	 * 水站套餐详情
	 * @param id 水商套餐主键id
	 * @return
	 */
    Map<String,Object> getWaterstoreSetmealDetail(Long id);

	/**
	 * 机构套餐详情
	 * @param id 机构套餐主键id
	 * @return
	 */
	Map<String,Object> getOrgSetmealDetail(Long id);

	/**
	 * 根据id获取套系编号
	 * @param id
	 * @return
	 */
    String selectSkuCodeById(Long id);
}