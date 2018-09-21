package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.product.entity
 * @ClassName ProductSimpleEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/8 19:41
 */
public class ProductSimpleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long parentCategoryId;//一级分类id
    private String parentCategoryName;//一级分类名称
    private String parentCategoryCode;//一级分类编号
    private Long categoryId;//二级分类ID
    private String categoryName;//二级分类名称
    private String categoryCode;//二级分类编号
    private Long brandId;//品牌ID
    private String brandName;//品牌名称
    private Long productId;//商品ID
    private String productName;//商品名称
    private String productSkuCode;//商品编号

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getParentCategoryCode() {
        return parentCategoryCode;
    }

    public void setParentCategoryCode(String parentCategoryCode) {
        this.parentCategoryCode = parentCategoryCode;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }
}
