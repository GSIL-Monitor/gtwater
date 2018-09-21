package com.gt.manager.entity.wtadmin;


import java.io.Serializable;

/**
 * @Package com.gt.manager.setmeal.entity
 * @ClassName GiftEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/6 17:54
 */
public class GiftEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String giftSkuCode;//商品sku
    private String productSpec;//商品规格
    private Integer giftNum;//数量
    private String seriesSkuCode;//套系skuCode
    private Long giftId;//赠品ID
    private Integer giftType;//赠品类型【1水票、2水、3其它】
    private Long pick;//赠品当前价格
    private ProductSimpleEntity productSimpleEntity;//赠品简要信息


    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public Long getPick() {
        return pick;
    }

    public void setPick(Long pick) {
        this.pick = pick;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public String getSeriesSkuCode() {
        return seriesSkuCode;
    }

    public void setSeriesSkuCode(String seriesSkuCode) {
        this.seriesSkuCode = seriesSkuCode;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public String getGiftSkuCode() {
        return giftSkuCode;
    }

    public void setGiftSkuCode(String giftSkuCode) {
        this.giftSkuCode = giftSkuCode;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    public ProductSimpleEntity getProductSimpleEntity() {
        return productSimpleEntity;
    }

    public void setProductSimpleEntity(ProductSimpleEntity productSimpleEntity) {
        this.productSimpleEntity = productSimpleEntity;
    }
}
