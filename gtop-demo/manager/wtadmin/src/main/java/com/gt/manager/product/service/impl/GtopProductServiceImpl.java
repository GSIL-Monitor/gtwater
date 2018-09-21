package com.gt.manager.product.service.impl;

import cn.gtop.api.goods.WaterGoodsRpcService;
import cn.gtop.api.goods.dto.GtopBrandDto;
import cn.gtop.api.goods.dto.GtopCategoryDto;
import cn.gtop.api.goods.dto.GtopSkuDto;
import cn.gtop.api.goods.dto.GtopSpuDto;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.gt.manager.entity.wtadmin.BranchesPriceEntity;
import com.gt.manager.entity.wtadmin.GtopBranchesProductEntity;
import com.gt.manager.product.repository.GtopBrandDAO;
import com.gt.manager.product.repository.GtopGoodsCategoryDAO;
import com.gt.manager.product.repository.GtopProductDAO;
import com.gt.manager.product.repository.GtopSkuDAO;
import com.gt.manager.product.service.IGtopProductService;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Package com.gt.manager.product.service.impl
 * @ClassName GtopProductServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/3 14:39
 */

@Service

public class GtopProductServiceImpl implements IGtopProductService {

    private static final Logger log = Logger.getLogger(GtopProductServiceImpl.class);
    @Autowired
    GtopGoodsCategoryDAO gtopGoodsCategoryDAO;
    @Autowired
    GtopBrandDAO gtopBrandDAO;
    @Autowired
    GtopProductDAO gtopProductDAO;
    @Autowired
    GtopSkuDAO gtopSkuDAO;
    @Reference
    WaterGoodsRpcService waterGoodsRpcService;



    /**
     * 根据sku获取基本库基本信息
     * 用于添加水管家商品页面显示信息
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    @Override
    public GtopBranchesProductEntity getGoodByGoodSkuCode(String skuCode) throws Exception {
        return null;
    }

    /**
     * 根据skuCode查询公品库sku信息（模糊查询）
     *
     * @param skuCode@return
     * @throws Exception
     */
    @Override
    public List<GtopSkuDto> getGtopSkuListBySkuCode(String skuCode) throws Exception {
        List<GtopSkuDto> gtopSkuDtos;
        try {
            gtopSkuDtos=waterGoodsRpcService.listSkuByGoodsCode(skuCode.trim());
            log.info("返回值长度："+gtopSkuDtos.size());
            return gtopSkuDtos;
        }catch (Exception e){
            log.error("没有信息",e);
            throw new GtopException("没有查到相关信息");
        }

    }

    /**
     * 根据skuId查询公品库详细信息，其信息用于增加商品到水管家
     *
     * @param skuId@return
     * @throws Exception
     */
    @Override
    public GtopSpuDto getGtopProductSkuBySkuId(Long skuId) throws Exception {
        GtopSpuDto gtopSpuDto;
        try {
            gtopSpuDto=waterGoodsRpcService.getSkuById(skuId);
            return gtopSpuDto;
        }catch (Exception e){
            log.error("没有商品信息");
            throw new GtopException("没有查询到商品信息");
        }
    }

    /**
     * 根据sku获取机构信息
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    @Override
    public List<BranchesPriceEntity> getBranchesPriceList(String skuCode) throws Exception {
        return null;
    }

    /**
     * 根据平台id查询一级分类列表
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<GtopCategoryDto> getGoodesCategoryListByPlatformId() throws Exception {


        List<GtopCategoryDto> gtopGoodsCategories=null;
        try {
            gtopGoodsCategories = waterGoodsRpcService.listFirstLevelCategory();

        }catch (Exception e){
            log.error("没有查询到信息",e);
            return null;
        }
        if (CollectionUtils.isEmpty(gtopGoodsCategories)) {
            return null;
        }
        log.info("gtopGoodsCategories.size:"+gtopGoodsCategories.size());
        return gtopGoodsCategories;
    }

    /**
     * 根据上级分类id查询分类信息列表
     *
     * @param parentCategoryId
     * @return
     * @throws Exception
     */
    @Override
    public List<GtopCategoryDto> getGoodesCategoryListByParentId(Long parentCategoryId) throws Exception {
        if (parentCategoryId == null) {
            throw new GtopException("缺少分类参数");
        }
        //调用公品库接口
        List<GtopCategoryDto> gtopGoodsCategories=null;
        try {
            gtopGoodsCategories  = waterGoodsRpcService.listSecondaryLevelCategory(parentCategoryId);
        }catch (Exception e){
            log.error("查询失败",e);
            return null;
        }
        if (CollectionUtils.isEmpty(gtopGoodsCategories)) {
            return null;
        }

        return gtopGoodsCategories;
    }

    /**
     * 根据二级分类Code查询所属品牌信息列表
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public List<GtopBrandDto> getBrandListByCategoryId(Long categoryId) throws Exception {
        if (categoryId == null) {
            throw new GtopException("缺少分类参数");
        }
        //List<GtopBrand> gtopBrands = gtopBrandDAO.getWtBrandListByCategoryCode(categoryCode);
        List<GtopBrandDto> gtopBrands=null;
        try {

            gtopBrands   = waterGoodsRpcService.listBrandBySecondaryCategory(categoryId);
        }catch (Exception e){
            log.error("没有相关数据",e);
            throw new GtopException("没有相关数据");
        }
        log.info("size:"+gtopBrands.size());
        if (CollectionUtils.isEmpty(gtopBrands)) {
            return null;
        }
        return gtopBrands;
    }

    /**
     * 根据分类id查询分类信息
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public GtopCategoryDto getGoodsCategoryByCategoryId(Long categoryId) throws Exception {
        GtopCategoryDto gtopCatogory=null;
        try {
             gtopCatogory = waterGoodsRpcService.getCategoryById(categoryId);
        }catch (Exception e){
            log.error("没有查询到信息",e);
            return null;
        }
        return gtopCatogory;
    }

    /**
     * 根据品牌id查询品牌信息
     *
     * @param gtopBrandId
     * @return
     * @throws Exception
     */
    @Override
    public GtopBrandDto getGtopBrandByGtopBrandId(Integer gtopBrandId) throws Exception {
        GtopBrandDto gtopBrand=null;
        try {
            gtopBrand = waterGoodsRpcService.getBrandById(gtopBrandId);
        }catch (Exception e){
            log.error("没有查询到信息",e);
            return null;
        }
        return gtopBrand;
    }

    /**
     * 根据sku查询sku信息
     *
     * @param gtopSkuId
     * @return
     * @throws Exception
     */
    @Override
    public GtopSkuDto getGtopSkuByGtopSkuId(Long gtopSkuId) throws Exception {
        GtopSkuDto gtopSku=null;
        try {
            gtopSku = waterGoodsRpcService.getSkuBySkuId(gtopSkuId);
        }catch (Exception e){
            log.error("没有查询到信息",e);
            return null;
        }
        return gtopSku;
    }

    /**
     * 根据商品id查询商品信息
     *
     * @param gtopProductId
     * @return
     * @throws Exception
     */
    @Override
    public GtopSpuDto getGtopProductByGtopProductId(Long gtopProductId) throws Exception {
        GtopSpuDto gtopProduct=null;
        try {
            gtopProduct = waterGoodsRpcService.getSpuById(gtopProductId);
        }catch (Exception e){
            log.error("没有查询到信息",e);
            return null;
        }
        return gtopProduct;
    }

    /**
     * 根据二级分类和品牌查询商品名称
     *
     * @param categoryId
     * @param brandId
     * @return List<GtopProduct>
     * @throws Exception
     */
    public List<GtopSpuDto> getGtopProductListByCategoryCodeBrandId(Long categoryId, Integer brandId) throws Exception {
        if (categoryId==null) {
            throw new GtopException("缺少分类参数");
        }
        if (brandId == null) {
            throw new GtopException("缺少品牌参数");
        }
        List<GtopSpuDto> gtopProducts=null;
        try {
            gtopProducts = waterGoodsRpcService.listSpuBySecondaryCategoryAndBrand(categoryId,brandId);
        }catch (Exception e){
            log.error("没有相关信息",e);
            return null;
        }
        if (CollectionUtils.isEmpty(gtopProducts)) {
            return null;
        }
        return gtopProducts;
    }

    /**
     * 根据商品id查询所属skuCode
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getGtopSkuCodeListByProductId(Long productId) throws Exception {
        return null;
    }

    /**
     * 根据商品id查询所属sku列表
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public List<GtopSkuDto> getGtopSkuListByProductId(Long productId) throws Exception {
        if (productId == null) {
            throw new GtopException("缺少产品参数");
        }
        List<GtopSkuDto> gtopSkus=null;
        try {
            gtopSkus = waterGoodsRpcService.listSkuBySpuId(productId);
        }catch (Exception e){
            log.error("没有查询到信息",e);
            return null;
        }
        if (CollectionUtils.isEmpty(gtopSkus)) {
            return null;
        }
        return gtopSkus;
    }
}
