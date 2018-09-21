package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.product.entity
 * @ClassName GtBranchesProductEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/1 20:09
 */
public class GtopBranchesProductEntity implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    //商品
    private Long id;// 商品id
    private Long gtopGoodsId;// 商品（公共）id
    private String goodsCode;// 商品编号（SPU编号）
    private Long brandId;// 品牌ID
    private String typeCode;// 类别编号
    private String goodsName;// 商品名称
    private String goodsPhotos;// 商品图片（多个）

    //分类
    private Long categoryId;// 类别ID[主键值]
    private String categoryCode;// 类别编号
    private String categoryName;// 类别名称
    private Long parentCategoryId;// 父类别ID
    private Integer categoryLevel;// 类别级别[类别的级别1...2...3...]

    //sku
    private Long pId;// 商品ID
    private Long skuId;//skuID
    private String skuCode;// SKU编号
    private Long costPrice;// 成本价格，（平台商品取自平台，自定义商品0）
    private Long price;// 市场价

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGtopGoodsId() {
        return gtopGoodsId;
    }

    public void setGtopGoodsId(Long gtopGoodsId) {
        this.gtopGoodsId = gtopGoodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
