package com.gt.manager.user.repository.wtPartner;

import com.gt.manager.entity.wtPartner.WtPartner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合伙人表
 * @author why
 */
@Mapper
public interface WtPartnerDAO {

	// Methods

   /**
	* 插入合伙人表
	* @param wtPartner 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtPartner wtPartner);

   /**
	* 删除合伙人表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新合伙人表
	* @param wtPartner 参数实体
	*/
	public void update(WtPartner wtPartner);

   /**
	* 主键查询合伙人表
	* @param id 主键值
	* @return WtPartner 实体
	*/
	public WtPartner selectById(Long id);

   /**
	* 根据条件查询合伙人表列表
	* @param wtPartner 参数实体
	* @return List<WtPartner> 实体List
	*/
	public List<WtPartner> selectList(WtPartner wtPartner);

}