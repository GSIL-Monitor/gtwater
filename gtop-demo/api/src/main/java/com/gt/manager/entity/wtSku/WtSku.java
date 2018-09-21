package com.gt.manager.entity.wtSku;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品sku表
 *
 * @author why
 */

public class WtSku implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Fields


    private Long id;// 主键
    private Long gtopSkuId;//gtop skuID
    private Long branchesId;// 机构(城市)ID
    private Long pId;// 商品ID
    private String goodsCode;// 商品编号（SPU编号）
    private Long brandId;// 品牌ID
    private String skuName;// SKU名称
    private String skuCode;// SKU编号
    private String goodsBar;// 商品条形码
    private String typeCode;// 类别编号，多级 ，‘000005000005’ 6位代表一级编号
    private String goodsSpec;// 商品规格
    private BigDecimal goodsWeight;// 商品的重量
    private Integer goodsUtil;// 单位值（1:g, 2:kg, 3：ml， 4:L)
    private String goodsSize;//
    private String goodsColor;//
    private Integer status;// 状态[1销售中（上架），2下架,3审核中，4审核不通过]
    private Long costPrice;// 成本价格，（平台商品取自平台，自定义商品0）
    private Long price;// 市场价
    private Integer onSales;// 是否为特价【1是特价、0不是】
    private Long sellPrice;// 水站销售价格
    private Long shelfOnTime;// 上架时间
    private String shellOffReason;// 下架原因
    private Long createId;// 创建者
    private Long createTime;// 创建时间
    private Long updateId;// 修改者
    private Long updateTime;// 修改时间
    private String updateName;//修改者名称
    private Integer delState;// 删除状态【1正常，0删除】
    private Long version;// 数据版本

    private String goodsPic;//商品图片

    // Empty Constructor
    public WtSku() {
        super();
    }

    // Full Constructor
    public WtSku(Long id, Long gtopSkuId, Long branchesId, Long pId, String goodsCode, Long brandId, String skuName, String skuCode, String goodsBar, String typeCode, String goodsSpec, BigDecimal goodsWeight, Integer goodsUtil, String goodsSize, String goodsColor, Integer status, Long costPrice, Long price, Integer onSales, Long sellPrice, Long shelfOnTime, String shellOffReason, Long createId, Long createTime, Long updateId, Long updateTime, String updateName, Integer delState, Long version, String goodsPic) {
        this.id = id;
        this.gtopSkuId = gtopSkuId;
        this.branchesId = branchesId;
        this.pId = pId;
        this.goodsCode = goodsCode;
        this.brandId = brandId;
        this.skuName = skuName;
        this.skuCode = skuCode;
        this.goodsBar = goodsBar;
        this.typeCode = typeCode;
        this.goodsSpec = goodsSpec;
        this.goodsWeight = goodsWeight;
        this.goodsUtil = goodsUtil;
        this.goodsSize = goodsSize;
        this.goodsColor = goodsColor;
        this.status = status;
        this.costPrice = costPrice;
        this.price = price;
        this.onSales = onSales;
        this.sellPrice = sellPrice;
        this.shelfOnTime = shelfOnTime;
        this.shellOffReason = shellOffReason;
        this.createId = createId;
        this.createTime = createTime;
        this.updateId = updateId;
        this.updateTime = updateTime;
        this.updateName = updateName;
        this.delState = delState;
        this.version = version;
        this.goodsPic = goodsPic;
    }

    // Property accessors


    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchesId() {
        return this.branchesId;
    }

    public void setBranchesId(Long branchesId) {
        this.branchesId = branchesId;
    }

    public Long getPId() {
        return this.pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }

    public String getGoodsCode() {
        return this.goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Long getBrandId() {
        return this.brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getGoodsBar() {
        return this.goodsBar;
    }

    public void setGoodsBar(String goodsBar) {
        this.goodsBar = goodsBar;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getGoodsSpec() {
        return this.goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public BigDecimal getGoodsWeight() {
        return this.goodsWeight;
    }

    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Integer getGoodsUtil() {
        return this.goodsUtil;
    }

    public void setGoodsUtil(Integer goodsUtil) {
        this.goodsUtil = goodsUtil;
    }

    public String getGoodsSize() {
        return this.goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getGoodsColor() {
        return this.goodsColor;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getOnSales() {
        return this.onSales;
    }

    public void setOnSales(Integer onSales) {
        this.onSales = onSales;
    }

    public Long getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getShelfOnTime() {
        return this.shelfOnTime;
    }

    public void setShelfOnTime(Long shelfOnTime) {
        this.shelfOnTime = shelfOnTime;
    }

    public String getShellOffReason() {
        return this.shellOffReason;
    }

    public void setShellOffReason(String shellOffReason) {
        this.shellOffReason = shellOffReason;
    }

    public Long getCreateId() {
        return this.createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateId() {
        return this.updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelState() {
        return this.delState;
    }

    public void setDelState(Integer delState) {
        this.delState = delState;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getGtopSkuId() {
        return gtopSkuId;
    }

    public void setGtopSkuId(Long gtopSkuId) {
        this.gtopSkuId = gtopSkuId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    @Override
    public String toString() {
        StringBuffer entityStirngBuffer = new StringBuffer();
        entityStirngBuffer.append("id = " + this.getId() + ",");
        entityStirngBuffer.append("gtopSkuId = " + this.getGtopSkuId() + ",");
        entityStirngBuffer.append("branchesId = " + this.getBranchesId() + ",");
        entityStirngBuffer.append("pId = " + this.getPId() + ",");
        entityStirngBuffer.append("goodsCode = " + this.getGoodsCode() + ",");
        entityStirngBuffer.append("brandId = " + this.getBrandId() + ",");
        entityStirngBuffer.append("skuName = " + this.getSkuName() + ",");
        entityStirngBuffer.append("skuCode = " + this.getSkuCode() + ",");
        entityStirngBuffer.append("goodsBar = " + this.getGoodsBar() + ",");
        entityStirngBuffer.append("typeCode = " + this.getTypeCode() + ",");
        entityStirngBuffer.append("goodsSpec = " + this.getGoodsSpec() + ",");
        entityStirngBuffer.append("goodsWeight = " + this.getGoodsWeight() + ",");
        entityStirngBuffer.append("goodsUtil = " + this.getGoodsUtil() + ",");
        entityStirngBuffer.append("goodsSize = " + this.getGoodsSize() + ",");
        entityStirngBuffer.append("goodsColor = " + this.getGoodsColor() + ",");
        entityStirngBuffer.append("status = " + this.getStatus() + ",");
        entityStirngBuffer.append("costPrice = " + this.getCostPrice() + ",");
        entityStirngBuffer.append("price = " + this.getPrice() + ",");
        entityStirngBuffer.append("onSales = " + this.getOnSales() + ",");
        entityStirngBuffer.append("sellPrice = " + this.getSellPrice() + ",");
        entityStirngBuffer.append("shelfOnTime = " + this.getShelfOnTime() + ",");
        entityStirngBuffer.append("shellOffReason = " + this.getShellOffReason() + ",");
        entityStirngBuffer.append("createId = " + this.getCreateId() + ",");
        entityStirngBuffer.append("createTime = " + this.getCreateTime() + ",");
        entityStirngBuffer.append("updateId = " + this.getUpdateId() + ",");
        entityStirngBuffer.append("updateTime = " + this.getUpdateTime() + ",");
        entityStirngBuffer.append("updateName = " + this.getUpdateName() + ",");
        entityStirngBuffer.append("delState = " + this.getDelState() + ",");
        entityStirngBuffer.append("version = " + this.getVersion() + ",");
        return entityStirngBuffer.toString();
    }

}