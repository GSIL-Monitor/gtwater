package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
	 * 根据平台水站编号得到水站
	 * @param branchesId
	 * @return
	 */
	WtWaterstore selectByBranchesId(Long branchesId);

	/**
	 * 根据branchesId,得到水站ids
	 * @param ids
	 * @return
	 */
	List<Long> selectIdsByBranchesIds(@Param("ids") List<Long> ids);
}