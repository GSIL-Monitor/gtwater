package com.gt.manager.product.service;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.productCategory.ProductCategory;
import com.gt.manager.entity.wtBrand.WtBrand;
import com.gt.manager.entity.wtProduct.WtProduct;
import com.gt.manager.entity.wtSku.WtSku;
import com.gt.manager.entity.wtadmin.BranchesPriceEntity;
import com.gt.manager.entity.wtadmin.ProductDetailEntity;
import com.gt.manager.entity.wtadmin.ProductSimpleEntity;

import java.util.List;

/**
 * @Package com.gt.manager.product.service.impl
 * @ClassName IWtProductService
 * @Description:
 * @Author towards
 * @Date 2018/8/2 15:04
 */
public interface IWtProductService {

    /**
     * 根据条件查询水管家商品列表
     *
     * @param startTime
     * @param endTime
     * @param productName
     * @param categoryId
     * @param goodsSource
     * @param status
     * @return
     */
    public JSONObject getWtPproductList(Integer pageSize, Integer pageNo, Long startTime, Long endTime,
                                        String productName, Long categoryId,
                                        Integer goodsSource,
                                        Integer status) throws Exception;


    /**
     * 根据sku返回商品对应分支机构价格
     *
     * @param skuCode
     * @return
     */
    public List<BranchesPriceEntity> getBranchesListBySkuCode(String skuCode, Long orgId) throws Exception;


    /**
     * 批量更新sku商品状态（级联）
     * 状态[1销售中（上架），2下架]
     *
     * @param skuCodeList
     * @param status  状态
     * @throws Exception
     */
    public void setSkuStatusBySkuCodeList(Long loginId, String loginName,Integer status, List<String> skuCodeList) throws Exception;

    /**
     * 批量删除(恢复)商品（级联）
     *
     * @param skuCodeList
     * @param delStata 删除状态  【1正常---0删除】
     * @throws Exception
     */
    public void setSkuDelStataBySkuCodeList(Long loginId,String loginName,Integer delStata, List<String> skuCodeList) throws Exception;

    /**
     * 批量更新sku商品状态（级联）
     * 状态[1销售中（上架），2下架]
     *
     * @param wtSkuList
     * @param status  状态
     * @throws Exception
     */
    public void setSkuStatusByWtSkuList(Long loginId ,Integer status, List<WtSku> wtSkuList) throws Exception;

    /**
     * 批量删除商品（级联）
     *
     * @param wtSkuList
     * @param delStata 删除状态  【1正常---0删除】
     * @throws Exception
     */
    public void setSkuDelStataByWtSkuList(Long loginId, Integer delStata, List<WtSku> wtSkuList) throws Exception;

    /**
     * 根据分支机构id，品牌id，商品id，分类id，父分类id，skuid，价格,用户ID将基本商品库商品信息抓取到水管家商品库
     *
     * @param brandId
     * @param categoryId
     * @param parentCategoryId
     * @param skuId
     * @param productId
     * @throws Exception
     */
    public void creatWtProduct(List<BranchesPriceEntity> branchesPriceEntityList,Long brandId,Long productId, Long categoryId, Long parentCategoryId, Long skuId,Long loginId,String loginName) throws Exception;


    /**
     * 根据品牌ID获取品牌信息
     *
     * @param brandId
     * @return
     * @throws Exception
     */
    public WtBrand getWtBrandByBrandId(Long brandId) throws Exception;


    /**
     * 根据分类id获取分类信息
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public ProductCategory getProductCatogoryByCatogeryId(Long categoryId) throws Exception;


    /**
     * 根据商品ID获取商品信息
     *
     * @param productId
     * @return
     * @throws Exception
     */
    public WtProduct getProductByProductId(Long productId) throws Exception;


    /**
     * 根据条件查询sku信息
     *
     * @param wtSku
     * @return
     * @throws Exception
     */
    public List<WtSku> getWtSkuList(WtSku wtSku) throws Exception;

    /**
     * 批量更新sku
     * @param wtSkuList
     * @throws Exception
     */
    public void updateSkuByWtSkuList(List<WtSku> wtSkuList) throws Exception;


    /**
     * 根据分类级别获取二级分类
     * @param level
     * @return
     * @throws Exception
     */
    public List<ProductCategory>  getCategoriesListByLevel(Integer level)throws Exception;

    /**
     * 根据商品sku模糊查询sku信息，返回List
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    public List<WtSku> getWtSkuListBySkuCodeBlur(String skuCode) throws Exception;

    /**
     * 根据商品skuCode查询商品简要信息
     * @param skuCode
     * @return
     * @throws Exception
     */
    public ProductSimpleEntity getProductSimpleEntityByProductSkuCode(String skuCode) throws Exception;


    /**
     * 根据平台ID查询一级分类信息
     *
     * @param platformId
     * @return
     * @throws Exception
     */
    public List<ProductCategory> getParentCategoryListByPlatformId(Long platformId) throws Exception;


    /**
     * 根据一级分类ID查询二级分类列表
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public List<ProductCategory> getCategoryListByParentCategoryId(Long categoryId) throws Exception;


    /**
     * 根据二级分类编号查询所属品牌信息
     *
     * @param categoryCode
     * @return
     * @throws Exception
     */
    public List<WtBrand> getWtBrandListByCategoryCode(String categoryCode) throws Exception;

    /**
     * 根据品牌ID和二级分类编号查询商品信息列表
     *
     * @param categoryCode
     * @param brandId
     * @return
     * @throws Exception
     */
    public List<WtProduct> getWtProductListByCategoryCodeBrandId(String categoryCode, Long brandId) throws Exception;


    /**
     * 根据商品ID查询sku信息列表
     *
     * @param productId
     * @return
     * @throws Exception
     */
    public List<WtSku> getWtSkuListByProductId(Long productId) throws Exception;

    /**
     * 根据sku查询商品信息
     * @param productSkuCode
     * @return
     * @throws Exception
     */
    public List<WtSku> getWtSkuBySkuCode(String productSkuCode) throws Exception;


    /**
     * 根据套系ID查询赠品信息
     * @param SeriesSkuCode
     * @return
     * @throws Exception
     */
    public List<WtSku> getWtSkuListBySeriesSkuCode(String SeriesSkuCode) throws Exception;

    /**
     * 更新分支机构价格信息
     * @param branchesPriceEntityList
     * @throws Exception
     */
    public void  updateWtSku(String skuCode,Long loginId,String loginName,List<BranchesPriceEntity> branchesPriceEntityList) throws Exception;


    /**
     * 根据分支机构ID和商品ID查询sku信息
     * @param branchesId
     * @param productId
     * @return
     * @throws Exception
     */
    public List<WtSku> getProductSkuByBranchesIdProductId(Long branchesId,Long productId) throws Exception;

    /**
     * 根据组织机构ID获取商品信息
     * @param branchesId
     * @return
     * @throws Exception
     */
    public List<WtProduct> getProductListByBranchesId(Long branchesId) throws Exception;

    /**
     * 根据用户ID获取商品信息
     * @param userId
     * @return
     * @throws Exception
     */
    public List<WtProduct> getProductListByUserId(Long userId) throws Exception;

    /**
     * 根据 用户ID和商品ID获取sku信息
     * @param userId
     * @param productId
     * @return
     * @throws Exception
     */
    public List<WtSku> getWtSkuListByUserIdProductId(Long userId,Long productId) throws Exception;


    /**
     * 根据skucode查询详细信息
     * @param skuCode
     * @param orgId
     * @return
     * @throws Exception
     */
    public ProductDetailEntity selectDetailProductBySkuCode(String skuCode, Long orgId) throws Exception;

    /**
     * 根据城市机构ID查询水站机构ID
     * @param branchesList
     * @return
     * @throws Exception
     */
    public List<Long> selectWaterBranchesIdListByCityBranchesList(List<Long> branchesList) throws Exception;
}
