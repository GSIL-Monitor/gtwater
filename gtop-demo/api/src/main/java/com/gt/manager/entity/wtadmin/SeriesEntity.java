package com.gt.manager.entity.wtadmin;



import java.io.Serializable;
import java.util.List;

/**
 * @Package com.gt.manager.setmeal.entity
 * @ClassName SeriesEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/6 17:34
 */
public class SeriesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private  String setmealName;//套餐名称
    private String seriesName;//套系名称

    private List<SeriesBranchesEntity> seriesBranchesEntityList;//机构价格集合
    private Long brandId;//品牌ID
    private String categoryCode;//类别编号
    private String shopImg;//购物车图片
    private String seriesImg;//套系图片
    private String productSkuCode;//主商品sku
    private Integer productNum;//主商品数量
    private String productSpec;//主商品规格
    private String profile;//图文详情；
    private List<GiftEntity> giftList;//赠品集合
    private ProductSimpleEntity productSimpleEntity;//主商品简要信息


    public ProductSimpleEntity getProductSimpleEntity() {
        return productSimpleEntity;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setProductSimpleEntity(ProductSimpleEntity productSimpleEntity) {
        this.productSimpleEntity = productSimpleEntity;
    }

    public List<SeriesBranchesEntity> getSeriesBranchesEntityList() {
        return seriesBranchesEntityList;
    }

    public void setSeriesBranchesEntityList(List<SeriesBranchesEntity> seriesBranchesEntityList) {
        this.seriesBranchesEntityList = seriesBranchesEntityList;
    }


    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getSeriesImg() {
        return seriesImg;
    }

    public void setSeriesImg(String seriesImg) {
        this.seriesImg = seriesImg;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public List<GiftEntity> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftEntity> giftList) {
        this.giftList = giftList;
    }
}
