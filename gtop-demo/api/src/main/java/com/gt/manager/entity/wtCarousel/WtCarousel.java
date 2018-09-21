package com.gt.manager.entity.wtCarousel;

import java.io.Serializable;

/**
 * 水站轮播图
 * @author why
 */

public class WtCarousel implements Serializable {

   /**
	* default SerialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	// Fields

	private Long id;// 主键
	private Long waterstoreId;// 
	private String accessUrl;// 访问地址
	private String carouselPic;// 轮播图片
	private Long createId;// 创建者
	private Long createTime;// 创建时间
	private Long updateBy;// 修改者
	private Long updateTime;// 修改时间
	private Integer delState;// 删除标记
	private Long version;// 数据版本
	
	// Empty Constructor
	public WtCarousel() {
		super();
	}
	
	// Full Constructor
	public WtCarousel(Long id, Long waterstoreId, String accessUrl, String carouselPic, Long createId, Long createTime, Long updateBy, Long updateTime, Integer delState, Long version) {
		this.id =  id;
		this.waterstoreId =  waterstoreId;
		this.accessUrl =  accessUrl;
		this.carouselPic =  carouselPic;
		this.createId =  createId;
		this.createTime =  createTime;
		this.updateBy =  updateBy;
		this.updateTime =  updateTime;
		this.delState =  delState;
		this.version =  version;
	}

	// Property accessors

	public Long getId () {
		return this.id;
	}
	
	public void setId (Long id) {
		this.id =  id;
	}
	
	public Long getWaterstoreId () {
		return this.waterstoreId;
	}
	
	public void setWaterstoreId (Long waterstoreId) {
		this.waterstoreId =  waterstoreId;
	}
	
	public String getAccessUrl () {
		return this.accessUrl;
	}
	
	public void setAccessUrl (String accessUrl) {
		this.accessUrl =  accessUrl;
	}
	
	public String getCarouselPic () {
		return this.carouselPic;
	}
	
	public void setCarouselPic (String carouselPic) {
		this.carouselPic =  carouselPic;
	}
	
	public Long getCreateId () {
		return this.createId;
	}
	
	public void setCreateId (Long createId) {
		this.createId =  createId;
	}
	
	public Long getCreateTime () {
		return this.createTime;
	}
	
	public void setCreateTime (Long createTime) {
		this.createTime =  createTime;
	}
	
	public Long getUpdateBy () {
		return this.updateBy;
	}
	
	public void setUpdateBy (Long updateBy) {
		this.updateBy =  updateBy;
	}
	
	public Long getUpdateTime () {
		return this.updateTime;
	}
	
	public void setUpdateTime (Long updateTime) {
		this.updateTime =  updateTime;
	}
	
	public Integer getDelState () {
		return this.delState;
	}
	
	public void setDelState (Integer delState) {
		this.delState =  delState;
	}
	
	public Long getVersion () {
		return this.version;
	}
	
	public void setVersion (Long version) {
		this.version =  version;
	}
	
	@Override
	public String toString () {
		StringBuffer entityStirngBuffer = new StringBuffer();
		entityStirngBuffer.append("id = " +  this.getId() + ",");
		entityStirngBuffer.append("waterstoreId = " +  this.getWaterstoreId() + ",");
		entityStirngBuffer.append("accessUrl = " +  this.getAccessUrl() + ",");
		entityStirngBuffer.append("carouselPic = " +  this.getCarouselPic() + ",");
		entityStirngBuffer.append("createId = " +  this.getCreateId() + ",");
		entityStirngBuffer.append("createTime = " +  this.getCreateTime() + ",");
		entityStirngBuffer.append("updateBy = " +  this.getUpdateBy() + ",");
		entityStirngBuffer.append("updateTime = " +  this.getUpdateTime() + ",");
		entityStirngBuffer.append("delState = " +  this.getDelState() + ",");
		entityStirngBuffer.append("version = " +  this.getVersion() + ",");
		return entityStirngBuffer.toString();
	}
	
}