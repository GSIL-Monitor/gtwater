package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.setmenu.rpc.service.entity
 * @ClassName SeriesBranchesEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/6 13:37
 */
public class SeriesBranchesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long branchesId;//分支机构ID
    private String branchesName;//分支机构名称
    private  Long price;//价格
    private String setmealName;//套餐名称
    private String setmealCode;//套餐编号
    private String seriesName;//套系名称
    private String seriesSkuCode;//套系编号
    private Long seriesId;//套系ID
    private Integer flag;//价格设置状态---1：已设置；2--未设置

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }

    public String getSetmealCode() {
        return setmealCode;
    }

    public void setSetmealCode(String setmealCode) {
        this.setmealCode = setmealCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getBranchesName() {
        return branchesName;
    }

    public void setBranchesName(String branchesName) {
        this.branchesName = branchesName;
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

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
