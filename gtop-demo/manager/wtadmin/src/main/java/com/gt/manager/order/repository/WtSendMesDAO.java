package com.gt.manager.order.repository;

import com.gt.manager.entity.wtSendMes.WtSendMes;

import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 派送详情
 * @author why
 */
@Mapper
public interface WtSendMesDAO {

	// Methods

   /**
	* 插入派送详情
	* @param wtSendMes 参数实体
	* @return null 插入后的数据库主键值
	*/
	public Long insert(WtSendMes wtSendMes);

   /**
	* 删除派送详情
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新派送详情
	* @param wtSendMes 参数实体
	*/
	public void update(WtSendMes wtSendMes);

   /**
	* 主键查询派送详情
	* @param id 主键值
	* @return WtSendMes 实体
	*/
	public WtSendMes selectById(Long id);

   /**
	* 根据条件查询派送详情列表
	* @param wtSendMes 参数实体
	* @return List<WtSendMes> 实体List
	*/
	public List<WtSendMes> selectList(WtSendMes wtSendMes);

	/**
	 * 根据配送编号查询商品基本信息
	 * @param sendCode
	 * @return
	 */
	public List<ProductMsgEntity> getProductListBySendCode(String sendCode);

	/**
	 * 改派（详情）
	 * @param map
	 */
	public int changeSend(Map<String,Object> map);


	/**
	 * 根据派送单号查询派送详情
	 * @param sendCode
	 * @return
	 */
	public List<WtSendMes> selectBySendCode(String sendCode );
}