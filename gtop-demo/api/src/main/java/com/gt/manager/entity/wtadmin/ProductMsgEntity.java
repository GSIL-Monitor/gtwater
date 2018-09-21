package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.order.entity
 * @ClassName ProductMsgEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/13 9:24
 */
public class ProductMsgEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brandName;//品牌名称
    private String productName;//商品名称
    private String productSpec;//商品规格
    private String skuCode;//商品编号
    private Long price;//商品售价
    private Integer count;//购买数量
    private String productPic;//商品图片
    private Integer type;//类型【1水票、2水、3其它】


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
