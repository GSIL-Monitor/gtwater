package com.gt.manager.order.repository;

import com.gt.manager.entity.wtSend.WtSend;

import com.gt.manager.entity.wtadmin.ChangeSendReport;
import com.gt.manager.entity.wtadmin.SendRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 派送订单
 * @author why
 */
@Mapper
public interface WtSendDAO {

	// Methods

   /**
	* 插入派送订单
	* @param wtSend 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtSend wtSend);

   /**
	* 删除派送订单
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新派送订单
	* @param wtSend 参数实体
	*/
	public void update(WtSend wtSend);

   /**
	* 主键查询派送订单
	* @param id 主键值
	* @return WtSend 实体
	*/
	public WtSend selectById(Long id);

   /**
	* 根据条件查询派送订单列表
	* @param wtSend 参数实体
	* @return List<WtSend> 实体List
	*/
	public List<WtSend> selectList(WtSend wtSend);

	/**
	 * 查询超时未接单派送单
	 * @param time(时间戳)  time=当前时间-30分钟
	 * @return
	 */
	public List<WtSend> selectSendListWithTimeOut(Long time);

	/**
	 * 根据条件查询派送信息
	 * @param map
	 * @return
	 */
	public List<SendRecordEntity>  getSendRecordList(Map<String,Object> map);

	/**
	 * 根据条件导出派送信息
	 * @param map
	 * @return
	 */
	public List<SendRecordEntity> exportSendRecordList(Map<String,Object> map);

	/**
	 * 改派
	 * @param map
	 */
	public void  changeSend(Map<String,Object> map);

	/**
	 * 查询改派信息
	 * @param map
	 * @return
	 */
	public List<ChangeSendReport> getchangeSendReport(Map<String,Object> map);

}