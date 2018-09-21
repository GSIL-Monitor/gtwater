package com.gt.manager.product.rpc;

import cn.gtop.api.goods.dto.GtopBrandDto;
import cn.gtop.api.goods.dto.GtopCategoryDto;
import cn.gtop.api.goods.dto.GtopSkuDto;
import cn.gtop.api.goods.dto.GtopSpuDto;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.productCategory.ProductCategory;
import com.gt.manager.entity.wtadmin.BranchesPriceEntity;
import com.gt.manager.entity.wtadmin.GtopBranchesProductEntity;
import com.gt.manager.entity.wtadmin.ProductDetailEntity;
import com.gt.manager.product.service.IGtopProductService;
import com.gt.manager.product.service.IWtProductService;
import com.gt.manager.rpc.product.IProductRpcService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Package com.gt.gtop.service.sys.gtwater.impl
 * @ClassName ProductServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/7/31 13:29
 */
@com.alibaba.dubbo.config.annotation.Service
public class ProductRpcServiceImpl implements IProductRpcService {
    private static final Logger log = Logger.getLogger(ProductRpcServiceImpl.class);

    @Autowired
    private IGtopProductService gtopProductService;
    @Autowired
    private IWtProductService wtProductService;


    /**
     * 编辑水管家机构商品
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage updateSkuBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        return null;
    }

    /**
     * 删除(恢复)sku
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage setSkuDelStataBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        return null;
    }

    /**
     * 下架(上架)product
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage setSkuStatusBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        return null;
    }

    /**
     * 根据skuCode查询公品库sku信息（模糊查询）
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGtopSkuListBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode.trim())) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("skuCode：" + skuCode);
        List<GtopSkuDto> gtopSkuDtos = gtopProductService.getGtopSkuListBySkuCode(skuCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, gtopSkuDtos, "请求成功");
    }

    /**
     * 根据skuId查询公品库详细信息，其信息用于增加商品到水管家
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGtopProductSkuBySkuId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long skuId = jsonObject.getLong("skuId");
        if (skuId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("skuId:" + skuId);
        GtopSpuDto gtopSpuDto = gtopProductService.getGtopProductSkuBySkuId(skuId);
        return new DataMessage(DataMessage.RESULT_SUCESS, gtopSpuDto, "请求成功");
    }

    /**
     * 根据平台id查询一级分类列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGoodesCategoryListByPlatformId(HttpServletRequest request, JSONObject jsonObject) throws Exception {

        //获取一级分类列表
        List<GtopCategoryDto> goodsCategoryList;

        try {
            goodsCategoryList = gtopProductService.getGoodesCategoryListByPlatformId();
            return new DataMessage(DataMessage.RESULT_SUCESS, goodsCategoryList, "请求成功");
        } catch (Exception e) {
            log.error("查询公品库分类失败", e);
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求失败");

        }


    }

    /**
     * 根据上级分类id查询分类信息列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGoodesCategoryListByParentId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long parentCategoryId = jsonObject.getLong("parentCategoryId");
        if (parentCategoryId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //获取二级分类列表
        log.info("parentId:" + parentCategoryId);
        List<GtopCategoryDto> goodsCategoryList = gtopProductService.getGoodesCategoryListByParentId(parentCategoryId);
        return new DataMessage(DataMessage.RESULT_SUCESS, goodsCategoryList, "请求成功");
    }

    /**
     * 根据二级分类查询所属品牌信息列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getBrandListByCategoryId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long categoryId = jsonObject.getLong("categoryId");
        //String categoryCode= jsonObject.getString("categoryCode");
        if (categoryId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //获取所属品牌列表
        List<GtopBrandDto> gtopBrands = gtopProductService.getBrandListByCategoryId(categoryId);
        return new DataMessage(DataMessage.RESULT_SUCESS, gtopBrands, "请求成功");
    }

    /**
     * 根据二级分类和品牌查询商品名称
     *
     * @param request
     * @param jsonObject
     * @param *categoryId
     * @param *brandId
     * @param *List<GtopProduct>
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGtopProductListByCategoryIdBrandId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long categoryId = jsonObject.getLong("categoryId");
        //String categoryCode= jsonObject.getString("categoryCode");
        if (categoryId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        Integer brandId = jsonObject.getInteger("brandId");
        if (brandId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        //根据分类id和品牌id 获取商品列表
        List<GtopSpuDto> gtopProducts = gtopProductService.getGtopProductListByCategoryCodeBrandId(categoryId, brandId);
        return new DataMessage(DataMessage.RESULT_SUCESS, gtopProducts, "请求成功");
    }

    /**
     * 根据商品ID查询skuCode列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGtopSkuCodeListByProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long productId = jsonObject.getLong("productId");
        if (productId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //获取基本商品库skuCode列表
        List<String> skuCodeList = gtopProductService.getGtopSkuCodeListByProductId(productId);

        return new DataMessage(DataMessage.RESULT_SUCESS, skuCodeList, "请求成功");
    }

    /**
     * 根据商品ID查询sku列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGtopSkuListByProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long productId = jsonObject.getLong("productId");
        if (productId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<GtopSkuDto> skuList = gtopProductService.getGtopSkuListByProductId(productId);
        return new DataMessage(DataMessage.RESULT_SUCESS, skuList, "请求成功");
    }

    /**
     * 根据sku获取基本库基本信息
     * 用于添加水管家商品页面显示信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGtopBranchesProductBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        GtopBranchesProductEntity gtopBranchesProductEntity = gtopProductService.getGoodByGoodSkuCode(skuCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, gtopBranchesProductEntity, "请求成功");
    }

    /**
     * 根据sku返回商品对应分支机构价格
     *
     * @param request
     * @param jsonObject
     * @return
     */
    @Override
    public DataMessage getBranchesListBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Long orgId = jsonObject.getLong("orgId");
        if (orgId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //获取机构价格状态列表setFlag;//是否设置价格标示：1--已设置；2---未设置
        List<BranchesPriceEntity> branchesPriceEntityList = wtProductService.getBranchesListBySkuCode(skuCode, orgId);
        for (BranchesPriceEntity b :branchesPriceEntityList
             ) {
            log.info("price:"+b.getSellPrice());
            log.info("falg---:"+b.getFlag());
            log.info("branchesId:--:"+b.getBranchesId());
            log.info("branchesName:"+b.getBranchesName());
        }
        return new DataMessage(DataMessage.RESULT_SUCESS, branchesPriceEntityList, "请求成功");
    }

    /**
     * 根据分支机构id，品牌id，商品id，分类id，skuid，父分类id，价格将基本商品库商品信息抓取到水管家商品库
     * Long branchesId,Long brandId,Long productId, Long categoryId, Long parentCategoryId, Long skuId, Long price,Long loginId
     *
     * @param request
     * @param jsonObject
     * @throws Exception
     */
    @Override
    public DataMessage creatWtProduct(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        JSONArray branchesPrice = jsonObject.getJSONArray("branchesPrice");
        List<BranchesPriceEntity> branchesPriceEntityList = JSONObject.parseArray(branchesPrice.toJSONString(), BranchesPriceEntity.class);
        if (CollectionUtils.isEmpty(branchesPriceEntityList)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        Long brandId = jsonObject.getLong("brandId");
        if (brandId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Long productId = jsonObject.getLong("productId");
        if (productId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        Long categoryId = jsonObject.getLong("categoryId");
        if (categoryId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        Long parentCategoryId = jsonObject.getLong("parentCategoryId");
        if (parentCategoryId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        Long skuId = jsonObject.getLong("skuId");
        if (skuId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }


        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        String loginName = jsonObject.getString("loginName");
        if (StringUtils.isEmpty(loginName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        loginName = loginName.trim();
        log.info("登录用户名：" + loginName);

        wtProductService.creatWtProduct(branchesPriceEntityList, brandId, productId, categoryId, parentCategoryId, skuId, loginId, loginName);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 根据条件查询机构商品列表
     *
     * @param request
     * @param jsonObject
     * @return Long startTime, Long endTime, String productName, String typeCode, Integer status
     * @throws Exception
     */
    @Override
    public DataMessage getProductList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer pageNo = jsonObject.getInteger("pageNo");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Long startTime = jsonObject.getLong("startTime");
        Long endTime = jsonObject.getLong("endTime");
        String productName = jsonObject.getString("productName");
        Long categoryId = jsonObject.getLong("categoryId");
        Integer goodsSource = jsonObject.getInteger("goodsSource");
        Integer status = jsonObject.getInteger("status");
        if (startTime != null && endTime != null && startTime > endTime) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "开始时间不能大于结束时间");
        }
        JSONObject json = wtProductService.getWtPproductList(pageSize, pageNo, startTime, endTime, productName, categoryId, goodsSource, status);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }

    /**
     * 批量更新sku商品状态（级联）
     * 状态[1销售中（上架），2下架]
     *
     * @param request
     * @param jsonObject
     * @throws Exception
     */
    @Override
    public DataMessage setSkuStatusBySkuList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer status = jsonObject.getInteger("status");
        if (status == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("skuCodeList");
        List<String> skuCodeList = JSONArray.parseArray(jsonArray.toJSONString(), String.class);
        if (skuCodeList == null || skuCodeList.isEmpty()) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        String loginName = jsonObject.getString("loginName");
        if (StringUtils.isEmpty(loginName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        loginName = loginName.trim();
        log.info("登录用户名：" + loginName);
        wtProductService.setSkuStatusBySkuCodeList(loginId, loginName, status, skuCodeList);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 批量删除(恢复)商品（级联）
     *
     * @param request
     * @param jsonObject
     * @throws Exception
     */
    @Override
    public DataMessage setSkuDelStataBySkuList(HttpServletRequest request, JSONObject jsonObject) throws Exception {

        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Integer delStata = jsonObject.getInteger("delStata");
        if (delStata == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("skuCodeList");
        List<String> skuCodeList = JSONArray.parseArray(jsonArray.toJSONString(), String.class);
        if (skuCodeList == null || skuCodeList.isEmpty()) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        String loginName = jsonObject.getString("loginName");
        if (StringUtils.isEmpty(loginName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        loginName = loginName.trim();
        log.info("登录用户名：" + loginName);
        wtProductService.setSkuDelStataBySkuCodeList(loginId, loginName, delStata, skuCodeList);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 批量更新sku商品信息（不包含上下架状态和删除状态）
     *
     * @param request
     * @param jsonObject
     */
    @Override
    public DataMessage updateSkuByWtSkuList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        return null;
    }

    /**
     * 根据级别获取分类列表
     *
     * @param request
     * @param jsonObject
     * @return
     */
    @Override
    public DataMessage getSecondLevelCategories(HttpServletRequest request, JSONObject jsonObject) {

        try {
            Integer level = jsonObject.getInteger("level");
            if (level == null) {
                return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
            }
            List<ProductCategory> list = wtProductService.getCategoriesListByLevel(level);
            return new DataMessage(DataMessage.RESULT_SUCESS, list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, null, e.getMessage());
        }
    }

    /**
     * 更新sku区域价格
     *
     * @param request
     * @param jsonObject
     * @return
     */
    @Override
    public DataMessage updateWtSku(HttpServletRequest request, JSONObject jsonObject) {
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("branchesPriceEntityList");
        List<BranchesPriceEntity> branchesPriceEntityList = JSONArray.parseArray(jsonArray.toJSONString(), BranchesPriceEntity.class);
        if (CollectionUtils.isEmpty(branchesPriceEntityList)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        String loginName = jsonObject.getString("loginName");
        if (StringUtils.isEmpty(loginName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        loginName = loginName.trim();
        log.info("登录用户名：" + loginName);
        try {
            wtProductService.updateWtSku(skuCode, loginId, loginName, branchesPriceEntityList);
            return new DataMessage(DataMessage.RESULT_SUCESS, null, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, null, e.getMessage());
        }

    }

    /**
     * 根据skuCode查询详细信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage  selectDetailProductBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "缺少商品信息！");
        }
        skuCode = skuCode.trim();
        log.info("skuCode:====" + skuCode);

        //组织ID
        Long orgId = jsonObject.getLong("orgId");
        if (orgId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "缺少组织参数！");
        }
        log.info("组织ID :" + orgId);
        ProductDetailEntity productDetailEntity=wtProductService.selectDetailProductBySkuCode(skuCode,orgId);

        return new DataMessage(DataMessage.RESULT_SUCESS, productDetailEntity, "查询成功");

    }


}
