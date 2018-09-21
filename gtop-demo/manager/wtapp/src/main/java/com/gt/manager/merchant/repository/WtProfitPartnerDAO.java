package com.gt.manager.merchant.repository;

import com.gt.manager.entity.wtProfitPartner.WtProfitPartner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分佣(合伙人)表
 * @author why
 */
@Mapper
public interface WtProfitPartnerDAO {

	// Methods

   /**
	* 插入分佣(合伙人)表
	* @param wtProfitPartner 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtProfitPartner wtProfitPartner);

   /**
	* 删除分佣(合伙人)表
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新分佣(合伙人)表
	* @param wtProfitPartner 参数实体
	*/
	public void update(WtProfitPartner wtProfitPartner);

   /**
	* 主键查询分佣(合伙人)表
	* @param id 主键值
	* @return WtProfitPartner 实体
	*/
	public WtProfitPartner selectById(Long id);

   /**
	* 根据条件查询分佣(合伙人)表列表
	* @param wtProfitPartner 参数实体
	* @return List<WtProfitPartner> 实体List
	*/
	public List<WtProfitPartner> selectList(WtProfitPartner wtProfitPartner);

    void batchInsert(List<WtProfitPartner> wtProfitPartnerArrayList);

	/**
	 * 根据订单明细查询合伙人id
	 * @param orderCode
	 * @return
	 */
	Long selectPartnerIdByOrderMesCode(String orderCode);
}