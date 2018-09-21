package com.gt.manager.entity.productCategory;

import java.io.Serializable;

/**
 * 商品类别实体类
 *
 * @author why
 */

public class ProductCategory implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Fields

    private Long categoryId;// 类别ID[主键值]
    private Integer platformId;// 平台id
    private Long gtopCategoryId;//gtop类别ID
    private String categoryCode;// 类别编号
    private Integer categoryStatus;// 类别状态[1-可用 2-不可用]
    private Integer categoryType;// 品类类型[1-普通商品品类 2-预售商品品类 3-预置商品品类 4-预置套餐品类]
    private String categoryName;// 类别名称
    private Long parentCategoryId;// 父类别ID
    private Integer categoryLevel;// 类别级别[类别的级别1...2...3...]
    private String categoryDesc;// 类别描述
    private Integer customOrderby;// 排序字段[自定义显示顺序]
    private Long createTime;//
    private Long updateTime;//
    private Long createBy;// 创建者
    private Long updateBy;// 修改者
    private Long version;// 数据版本

    // Empty Constructor
    public ProductCategory() {
        super();
    }

    // Full Constructor
    public ProductCategory(Long categoryId, Long gtopCategoryId, Integer platformId, String categoryCode, Integer categoryStatus, Integer categoryType, String categoryName, Long parentCategoryId, Integer categoryLevel, String categoryDesc, Integer customOrderby, Long createTime, Long updateTime, Long createBy, Long updateBy, Long version) {
        this.categoryId = categoryId;
        this.gtopCategoryId = gtopCategoryId;
        this.platformId = platformId;
        this.categoryCode = categoryCode;
        this.categoryStatus = categoryStatus;
        this.categoryType = categoryType;
        this.categoryName = categoryName;
        this.parentCategoryId = parentCategoryId;
        this.categoryLevel = categoryLevel;
        this.categoryDesc = categoryDesc;
        this.customOrderby = customOrderby;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.version = version;
    }

    // Property accessors


    public Long getGtopCategoryId() {
        return gtopCategoryId;
    }

    public void setGtopCategoryId(Long gtopCategoryId) {
        this.gtopCategoryId = gtopCategoryId;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getCategoryStatus() {
        return this.categoryStatus;
    }

    public void setCategoryStatus(Integer categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public Integer getCategoryType() {
        return this.categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getParentCategoryId() {
        return this.parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getCategoryLevel() {
        return this.categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryDesc() {
        return this.categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Integer getCustomOrderby() {
        return this.customOrderby;
    }

    public void setCustomOrderby(Integer customOrderby) {
        this.customOrderby = customOrderby;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuffer entityStirngBuffer = new StringBuffer();
        entityStirngBuffer.append("categoryId = " + this.getCategoryId() + ",");
        entityStirngBuffer.append("gtopCategoryId = " + this.getGtopCategoryId() + ",");
        entityStirngBuffer.append("platformId = " + this.getPlatformId() + ",");
        entityStirngBuffer.append("categoryCode = " + this.getCategoryCode() + ",");
        entityStirngBuffer.append("categoryStatus = " + this.getCategoryStatus() + ",");
        entityStirngBuffer.append("categoryType = " + this.getCategoryType() + ",");
        entityStirngBuffer.append("categoryName = " + this.getCategoryName() + ",");
        entityStirngBuffer.append("parentCategoryId = " + this.getParentCategoryId() + ",");
        entityStirngBuffer.append("categoryLevel = " + this.getCategoryLevel() + ",");
        entityStirngBuffer.append("categoryDesc = " + this.getCategoryDesc() + ",");
        entityStirngBuffer.append("customOrderby = " + this.getCustomOrderby() + ",");
        entityStirngBuffer.append("createTime = " + this.getCreateTime() + ",");
        entityStirngBuffer.append("updateTime = " + this.getUpdateTime() + ",");
        entityStirngBuffer.append("createBy = " + this.getCreateBy() + ",");
        entityStirngBuffer.append("updateBy = " + this.getUpdateBy() + ",");
        entityStirngBuffer.append("version = " + this.getVersion() + ",");
        return entityStirngBuffer.toString();
    }

}