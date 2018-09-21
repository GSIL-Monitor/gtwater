package com.gt.manager.order.repository;

import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 水站表
 * @author why
 */
@Mapper
public interface WtWaterstoreDAO {

	// Methods

   /**
	* 插入水站表
	* @param wtWaterstore 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtWaterstore wtWaterstore);

   /**
	* 删除水站表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新水站表
	* @param wtWaterstore 参数实体
	*/
	public void update(WtWaterstore wtWaterstore);

   /**
	* 主键查询水站表
	* @param id 主键值
	* @return WtWaterstore 实体
	*/
	public WtWaterstore selectById(Long id);

   /**
	* 根据条件查询水站表列表
	* @param wtWaterstore 参数实体
	* @return List<WtWaterstore> 实体List
	*/
	public List<WtWaterstore> selectList(WtWaterstore wtWaterstore);

	/**
	 * 根据skuCode和机构list查询水站信息
	 * @param skuCode
	 * @param openBranches
	 * @return
	 */
	public List<WtWaterstore> getBranchesListBySkuCodeBranches(@Param("skuCode") String skuCode,
															   @Param("branchesList")List<Long> longs);


	/**
	 * 根据水站名称查询水站信息
	 * @param waterName
	 * @return
	 */
	public List<WtWaterstore> getWaterStoreListByName(Map<String,Object> map);

}