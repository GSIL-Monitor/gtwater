package com.gt.manager.entity.wtadmin;

import java.io.Serializable;
import java.util.List;

/**
 * @Package com.gt.manager.product.entity
 * @ClassName ProductDetailEntity
 * @Description:
 * @Author towards
 * @Date 2018/9/5 16:47
 */
public class ProductDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long parentCategoryId;//一级分类id
    private String parentCategoryName;//一级分类名称
    private String parentCategoryCode;//一级分类编号
    private Long categoryId;//二级分类ID
    private String categtoryName;//二级分类名称
    private String categoryCode;//二级分类编号
    private Long brandId;//品牌ID
    private String brandName;//品牌名称
    private Long productId;//商品ID
    private String productName;//商品名称
    private String productSkuCode;//商品编号
    private String productPic;//商品图片
    private String productProfile;//商品图文详情
    private String goodsCarPic;//购物车图片
    private String skuName;//sku名称
    private String skuCode;//
    private String skuId;//
    private List<BranchesPriceEntity> branchesPriceEntities;//区域价格信息

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

    public String getCategtoryName() {
        return categtoryName;
    }

    public void setCategtoryName(String categtoryName) {
        this.categtoryName = categtoryName;
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

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductProfile() {
        return productProfile;
    }

    public void setProductProfile(String productProfile) {
        this.productProfile = productProfile;
    }

    public String getGoodsCarPic() {
        return goodsCarPic;
    }

    public void setGoodsCarPic(String goodsCarPic) {
        this.goodsCarPic = goodsCarPic;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public List<BranchesPriceEntity> getBranchesPriceEntities() {
        return branchesPriceEntities;
    }

    public void setBranchesPriceEntities(List<BranchesPriceEntity> branchesPriceEntities) {
        this.branchesPriceEntities = branchesPriceEntities;
    }
}
