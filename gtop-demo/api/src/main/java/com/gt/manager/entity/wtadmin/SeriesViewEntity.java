package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.setmeal.entity
 * @ClassName SeriesViewEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/6 19:33
 */
public class SeriesViewEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String setmealName;//套餐名称
    private String seriesName;//套系名称
    private String seriesSkuCode;//套系编号
    private String productSkuCode;//主商品编号
    private Integer status;//上架状态【1上架、0下架】
    private Integer isGift;//是否含有套餐【1有，0无】

    public Integer getIsGift() {
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
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

    public String getSeriesSkuCode() {
        return seriesSkuCode;
    }

    public void setSeriesSkuCode(String seriesSkuCode) {
        this.seriesSkuCode = seriesSkuCode;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
