package com.gt.manager.banner.repository;

import com.gt.manager.entity.wtCarousel.WtCarousel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 水站轮播图
 * @author why
 */
@Mapper
public interface WtCarouselDAO {

	// Methods

   /**
	* 插入水站轮播图
	* @param wtCarousel 参数实体
	* @return id 插入后的数据库主键值
	*/
	public Long insert(WtCarousel wtCarousel);

   /**
	* 删除水站轮播图
	* @param id 主键值
	*/
	public void delete(Long id);

   /**
	* 更新水站轮播图
	* @param wtCarousel 参数实体
	*/
	public void update(WtCarousel wtCarousel);

   /**
	* 主键查询水站轮播图
	* @param id 主键值
	* @return WtCarousel 实体
	*/
	public WtCarousel selectById(Long id);

   /**
	* 根据条件查询水站轮播图列表
	* @param wtCarousel 参数实体
	* @return List<WtCarousel> 实体List
	*/
	public List<WtCarousel> selectList(WtCarousel wtCarousel);

	/**
	 * 查询所有轮播图信息
	 * @return
	 */
	public List<WtCarousel> selectAllList();

	/**
	 * 设置删除状态
	 */
	public void setDel();

}