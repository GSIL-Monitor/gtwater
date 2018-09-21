package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import com.gt.manager.merchant.entity.response.WtWaterstoreSkuResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

	/**
	 * 根据水站平台id和关键字查询商家自己的商品列表
	 * @param values
	 * @return
	 */
	List<WtWaterstoreSkuResponse> findPage(Map<String, String> values);

	/**
	 * 根据水站平台id和关键字查询商家未添加且是所属城的商品列表
	 * @param param
	 * @return
	 */
    List<WtWaterstoreSkuResponse> findAddibleGoodsPage(Map<String, String> param);

	/**
	 * 更新商家商品上下架
	 * @param
	 * @return
	 */
	boolean updateStatus(Map<String,Object> map);

	/**
	 * 根据平台水站编号得到水站Id
	 * @param branchesId
	 * @return
	 */
	Long selectIdByBranchesId(Long branchesId);

	/**
	 * 水站商品详情
	 * @param id 水站商品id
	 * @return
	 */

    Map<String,Object> getWaterstoreSkuDetail(Long id);

	/**
	 * 机构商品详情
	 * @param id 机构商品id
	 * @return
	 */
	Map<String,Object> getOrgSkuDetail(Long id);

	/**
	 * 商家所有已添加的商品及套餐信息
	 * @param branchesId 水站id 模糊查询关键字
	 * @return
	 */
    List<WtWaterstoreSkuResponse> findAllSkuAndSetmeal(@Param("branchesId")Long branchesId,@Param("key")String key);

	/**
	 * 商家所有可添加的且未添加过的机构表的商品及套餐信息
	 * @param cityId
	 * @return
	 */
	List<WtWaterstoreSkuResponse> findAllAddibleSkuAndSetmeal(@Param("cityId") Long cityId,@Param("branchesId") Long branchesId,@Param("key") String key);

	/**
	 * 根据sku表的id，找到sku_code
	 * @param id
	 * @return
	 */
    String selectSkuCodeById(Long id);
}