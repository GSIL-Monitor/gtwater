package com.gt.manager.entity.wtadmin;

import java.io.Serializable;

/**
 * @Package com.gt.manager.product.entity
 * @ClassName BranchesPriceEntity
 * @Description:
 * @Author towards
 * @Date 2018/8/2 15:22
 */
public class BranchesPriceEntity implements Serializable {
    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private Long branchesId;//分支机构id
    private String branchesName;//分支机构名称
    private Long sellPrice;//价格
    private String skuCode;//sku编号
    private Integer flag;//是否设置价格标示：1--已设置；2---未设置

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getBranchesId() {
        return branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public String getBranchesName() {
        return branchesName;
    }

    public void setBranchesName(String branchesName) {
        this.branchesName = branchesName;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
