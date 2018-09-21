package com.gt.manager.account.repository;

import com.gt.manager.entity.wtBranchesAccount.WtBranchesAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 钱包表
 * @author why
 */
@Mapper
@Repository
public interface WtBranchesAccountDAO {

	// Methods

   /**
	* 插入钱包表
	* @param wtBranchesAccount 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtBranchesAccount wtBranchesAccount);

   /**
	* 删除钱包表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新钱包表
	* @param wtBranchesAccount 参数实体
	*/
	public void update(WtBranchesAccount wtBranchesAccount);

   /**
	* 主键查询钱包表
	* @param id 主键值
	* @return WtBranchesAccount 实体
	*/
	public WtBranchesAccount selectById(Long id);

   /**
	* 根据条件查询钱包表列表
	* @param wtBranchesAccount 参数实体
	* @return List<WtBranchesAccount> 实体List
	*/
	public List<WtBranchesAccount> selectList(WtBranchesAccount wtBranchesAccount);

	/**
	 * 通过机构id查询水站或城市的余额
	 * @param branchesId
	 * @return
	 */
    Long getAccountByBranchesId(@Param("branchesId") Long branchesId, @Param("userType") Integer userType);

	public Long insertSelective(WtBranchesAccount account);
}