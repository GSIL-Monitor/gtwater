package com.gt.manager.function.repository;

import com.gt.manager.entity.wtFunction.WtFunction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 水管家功能开关表
 * @author why
 */
@Mapper
public interface WtFunctionDAO {

	// Methods

   /**
	* 插入水管家功能开关表
	* @param wtFunction 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtFunction wtFunction);

   /**
	* 删除水管家功能开关表
	* @param id 主键值
	*/
	public int delete(Long id);

   /**
	* 更新水管家功能开关表
	* @param wtFunction 参数实体
	*/
	public int update(WtFunction wtFunction);

   /**
	* 主键查询水管家功能开关表
	* @param id 主键值
	* @return WtFunction 实体
	*/
	public WtFunction selectById(Long id);

   /**
	* 根据条件查询水管家功能开关表列表
	* @param wtFunction 参数实体
	* @return List<WtFunction> 实体List
	*/
	public List<WtFunction> selectList(WtFunction wtFunction);

	/**
	 * 返回所有配置
	 * @return
	 */
	public List<WtFunction> getFunctionlist();

	/**
	 * 更新配置状态
	 * @param map
	 */
	public int updateFunctionStata(Map<String,Object> map);


}