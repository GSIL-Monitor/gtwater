package com.gt.manager.account.repository;

import com.gt.manager.entity.wtBranchesAccountStatement.WtBranchesAccountStatement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 钱包操作记录表
 * @author why
 */
@Mapper
public interface WtBranchesAccountStatementDAO {

	// Methods

   /**
	* 插入钱包操作记录表
	* @param wtBranchesAccountStatement 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtBranchesAccountStatement wtBranchesAccountStatement);

   /**
	* 删除钱包操作记录表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新钱包操作记录表
	* @param wtBranchesAccountStatement 参数实体
	*/
	public void update(WtBranchesAccountStatement wtBranchesAccountStatement);

   /**
	* 主键查询钱包操作记录表
	* @param id 主键值
	* @return WtBranchesAccountStatement 实体
	*/
	public WtBranchesAccountStatement selectById(Long id);

   /**
	* 根据条件查询钱包操作记录表列表
	* @param wtBranchesAccountStatement 参数实体
	* @return List<WtBranchesAccountStatement> 实体List
	*/
	public List<WtBranchesAccountStatement> selectList(WtBranchesAccountStatement wtBranchesAccountStatement);

	public Long insertSelective(WtBranchesAccountStatement accountStatement);
}