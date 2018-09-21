package com.gt.manager.ticket.repository;

import com.gt.manager.entity.sysRegion.SysRegion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 省市区DAO接口
 * @author why
 */
@Mapper
public interface SysRegionDAO {

	// Methods

   /**
	* 插入省市区
	* @param sysRegion 参数实体
	* @return regionId 插入后的数据库主键值
	*/
	public Long insert(SysRegion sysRegion);

   /**
	* 删除省市区
	* @param regionId 主键值
	*/
	public int delete(Long regionId);

   /**
	* 更新省市区
	* @param sysRegion 参数实体
	*/
	public int update(SysRegion sysRegion);

   /**
	* 主键查询省市区
	* @param regionId 主键值
	* @return SysRegion 实体
	*/
	public SysRegion selectById(Long regionId);

   /**
	* 根据条件查询省市区列表
	* @param sysRegion 参数实体
	* @return List<SysRegion> 实体List
	*/
	public List<SysRegion> selectList(SysRegion sysRegion);

	/**
	 * 根据上级ID查询所属区域信息
	 * @param parentId
	 * @return
	 */
	public List<SysRegion> selectListByParentId(Map<String,Object> map);

}