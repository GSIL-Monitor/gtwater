package com.gt.manager.ticket.repository;

import com.gt.manager.entity.wtUserExtensioncode.WtUserExtensioncode;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户扫码记录表
 * @author why
 */
@Mapper
public interface WtUserExtensioncodeDAO {

	// Methods

   /**
	* 插入用户扫码记录表
	* @param wtUserExtensioncode 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtUserExtensioncode wtUserExtensioncode);

   /**
	* 删除用户扫码记录表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新用户扫码记录表
	* @param wtUserExtensioncode 参数实体
	*/
	public void update(WtUserExtensioncode wtUserExtensioncode);

   /**
	* 主键查询用户扫码记录表
	* @param id 主键值
	* @return WtUserExtensioncode 实体
	*/
	public WtUserExtensioncode selectById(Long id);

   /**
	* 根据条件查询用户扫码记录表列表
	* @param wtUserExtensioncode 参数实体
	* @return List<WtUserExtensioncode> 实体List
	*/
	public List<WtUserExtensioncode> selectList(WtUserExtensioncode wtUserExtensioncode);

}