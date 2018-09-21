package com.gt.manager.merchant.entity.response;

import java.io.Serializable;

/**
 * @author fengyueli
 * @date 2018/8/1 18:33
 */
public class WtWaterstoreSkuResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;//水管家商品唯一id
    private Long skuId;//机构商口id
    private String spuCode;// 商品编号(spu编号)
    private String goodsPic;// 商品购物车图片
    private Integer status;// 状态[1销售中（上架），2下架]
    private String goodsName;// 商品名称
    private String brandName;//品牌名称
    private String goodsSpec;// 商品规格

    private Long sellPrice;// 水站销售价格
    private Integer type;//0为商品1为套餐自定义区分
    private Long onshelfTime;//机构商品上架时间
    private Long createTime;//商家自己商品添加时间


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getOnshelfTime() {
        return onshelfTime;
    }

    public void setOnshelfTime(Long onshelfTime) {
        this.onshelfTime = onshelfTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getGoodsSpec() {


        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }



    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }



    @Override
    public String toString() {
        return "WtWaterstoreSkuResponse{" +
                "id='" + id + '\'' +
                "skuId='" + skuId + '\'' +
                ", spuCode=" + spuCode +
                ", goodsPic='" + goodsPic + '\'' +
                ", status=" + status +
                ", goodsName='" + goodsName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
