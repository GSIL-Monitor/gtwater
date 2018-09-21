package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.product.entity
 * @ClassName BranchesProductEntity
 * @Description: 机构商品实体类
 * @Author towards
 * @Date 2018/8/1 15:44
 */
public class WtBranchesProductEntity implements Serializable {
    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    //商品
    private Long id;// 商品id
    private Long gtopGoodsId;// 商品（公共）id
    private String spuCode;// SKU编号
    private Long brandId;// 品牌ID
    private String typeCode;// 类别编号
    private String goodsName;// 商品名称
    private String goodsPhotos;// 商品图片（多个）
    private Integer goodsSource;// 商品来源（1：平台商品，2：店铺自定义商品）
    private Integer delState;// 删除状态【1正常，0删除】

    //分类
    private Long categoryId;// 类别ID[主键值]
    private Integer categoryType;// 品类类型[1-普通商品品类 2-预售商品品类 3-预置商品品类 4-预置套餐品类]
    private String categoryName;// 类别名称
    private Long parentCategoryId;// 父类别ID
    private String parentCategoryName;//父类名称

    //品牌
    private String name;//

    //sku
    private String skuCode;// SKU编号
    private Long branchesId;//机构（城市）id
    private String goodsSpec;// 商品规格
    private Integer status;// 状态[1销售中（上架），2下架,3审核中，4审核不通过]
    private Long costPrice;// 成本价格，（平台商品取自平台，自定义商品0）
    private Long price;// 市场价
    private Long updateId;// 修改者
    private String updateName;//修改者姓名
    private Long updateTime;// 修改时间
    private Long createTime;//创建时间

    //包含区域数量
    private Integer count;//包含区域数量

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
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

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
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

    public Integer getGoodsSource() {
        return goodsSource;
    }

    public void setGoodsSource(Integer goodsSource) {
        this.goodsSource = goodsSource;
    }

    public Integer getDelState() {
        return delState;
    }

    public void setDelState(Integer delState) {
        this.delState = delState;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
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

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
