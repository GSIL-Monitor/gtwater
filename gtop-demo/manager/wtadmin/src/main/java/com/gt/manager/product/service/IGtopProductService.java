package com.gt.manager.product.service;

import cn.gtop.api.goods.dto.GtopBrandDto;
import cn.gtop.api.goods.dto.GtopCategoryDto;
import cn.gtop.api.goods.dto.GtopSkuDto;
import cn.gtop.api.goods.dto.GtopSpuDto;
import com.gt.manager.entity.wtadmin.BranchesPriceEntity;
import com.gt.manager.entity.wtadmin.GtopBranchesProductEntity;


import java.util.List;

/**
 * @Package com.gt.manager.product.service
 * @ClassName IGtopProductService
 * @Description:
 * @Author towards
 * @Date 2018/8/3 10:27
 */
public interface IGtopProductService {

    /**
     * 根据sku获取基本库基本信息
     * 用于添加水管家商品页面显示信息
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    public GtopBranchesProductEntity getGoodByGoodSkuCode(String skuCode) throws Exception;


    /**
     * 根据skuCode查询公品库sku信息（模糊查询）
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public List<GtopSkuDto> getGtopSkuListBySkuCode(String skuCode) throws Exception;
    /**
     * 根据skuId查询公品库详细信息，其信息用于增加商品到水管家
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public GtopSpuDto getGtopProductSkuBySkuId(Long skuId) throws Exception;
    /**
     * 根据sku获取机构信息
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    public List<BranchesPriceEntity> getBranchesPriceList(String skuCode) throws Exception;


    /**
     * 根据平台id查询一级分类列表
     *
     * @param platformId
     * @return
     * @throws Exception
     */
    public List<GtopCategoryDto> getGoodesCategoryListByPlatformId() throws Exception;


    /**
     * 根据上级分类id查询分类信息列表
     *
     * @param parentCategoryId
     * @return
     * @throws Exception
     */
    public List<GtopCategoryDto> getGoodesCategoryListByParentId(Long parentCategoryId) throws Exception;


    /**
     * 根据二级分类查询所属品牌信息列表
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    public List<GtopBrandDto> getBrandListByCategoryId(Long categoryId) throws Exception;



    /**
     * 根据分类id查询分类信息
     * @param categoryId
     * @return
     * @throws Exception
     */
    public GtopCategoryDto getGoodsCategoryByCategoryId(Long categoryId) throws Exception;


    /**
     * 根据品牌id查询品牌信息
     * @param gtopBrandId
     * @return
     * @throws Exception
     */
    public GtopBrandDto getGtopBrandByGtopBrandId(Integer gtopBrandId) throws Exception;


    /**
     * 根据sku查询sku信息
     * @param gtopSkuId
     * @return
     * @throws Exception
     */
    public GtopSkuDto getGtopSkuByGtopSkuId(Long gtopSkuId) throws Exception;


    /**
     * 根据商品id查询商品信息
     * @param gtopProductId
     * @return
     * @throws Exception
     */
    public GtopSpuDto getGtopProductByGtopProductId(Long gtopProductId) throws Exception;


    /**
     * 根据二级分类和品牌查询商品名称
     * @param categoryId
     * @param brandId
     * @return  List<GtopProduct>
     * @throws Exception
     */
    public List<GtopSpuDto> getGtopProductListByCategoryCodeBrandId(Long categoryId, Integer brandId) throws Exception;


    /**
     *根据商品id查询所属skuCode
     * @param productId
     * @return
     * @throws Exception
     */
    public List<String> getGtopSkuCodeListByProductId(Long productId) throws Exception;

    /**
     * 根据商品id查询所属sku列表
     * @param productId
     * @return
     * @throws Exception
     */
    public List<GtopSkuDto> getGtopSkuListByProductId(Long productId) throws Exception;

}
