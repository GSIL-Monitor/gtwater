package com.gt.manager.user.repository.sysRegion;

import java.util.List;

import com.gt.manager.entity.sysRegion.SysRegion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
	public void delete(Long regionId);

   /**
	* 更新省市区
	* @param sysRegion 参数实体
	*/
	public void update(SysRegion sysRegion);

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

	public List<SysRegion> selectProvince();

}