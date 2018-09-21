package com.gt.manager.setmeal.rpc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.productCategory.ProductCategory;
import com.gt.manager.entity.wtBrand.WtBrand;
import com.gt.manager.entity.wtProduct.WtProduct;
import com.gt.manager.entity.wtSku.WtSku;
import com.gt.manager.entity.wtadmin.*;
import com.gt.manager.product.service.IWtProductService;
import com.gt.manager.rpc.setmeal.ISetmealRpcService;
import com.gt.manager.setmeal.service.ISetmealService;
import com.gt.manager.util.OssImageUploadUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.gt.manager.setmeal.rpc
 * @ClassName SetMealRpcServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/6 16:06
 */
@com.alibaba.dubbo.config.annotation.Service
public class SetmealRpcServiceImpl implements ISetmealRpcService {
    private static final Logger log = Logger.getLogger(SetmealRpcServiceImpl.class);

    @Autowired
    IWtProductService wtProductService;
    @Autowired
    ISetmealService setmealService;

    /**
     * 根据skuCode查询商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getProductSimpleEntityByProductSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        ProductSimpleEntity productSimpleEntity = wtProductService.getProductSimpleEntityByProductSkuCode(skuCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, productSimpleEntity, "请求成功");
    }

    /**
     * 根据平台id返回一级分类列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getParentCategoryByPlatformId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //赠品单独设置分类，单独设置platformId；
        //添加套餐是根据是否是赠品来输入参数，如果不是赠品则参数为空，否则根据类别设置的platformId来输入
        Long platformId = jsonObject.getLong("platformId");

        List<ProductCategory> productCategories = wtProductService.getParentCategoryListByPlatformId(platformId);
        return new DataMessage(DataMessage.RESULT_SUCESS, productCategories, "请求成功");
    }

    /**
     * 根据一级分类ID查询查询二级分类列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getCategoryListByParentCategoryId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long categoryId = jsonObject.getLong("categoryId");
        if (categoryId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<ProductCategory> productCategories = wtProductService.getCategoryListByParentCategoryId(categoryId);
        return new DataMessage(DataMessage.RESULT_SUCESS, productCategories, "请求成功");
    }

    /**
     * 根据二级分类查询所属品牌列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getBrandListByCategoryId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String categoryCode = jsonObject.getString("categoryCode");
        if (StringUtils.isEmpty(categoryCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("分类ID：" + categoryCode);
        categoryCode = categoryCode.trim();
        List<WtBrand> wtBrands = wtProductService.getWtBrandListByCategoryCode(categoryCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtBrands, "请求成功");
    }

    /**
     * 根据二级分类和品牌id 查询商品信息列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWtProductListByCategoryIdBrandId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String categoryCode = jsonObject.getString("categoryCode");
        if (StringUtils.isEmpty(categoryCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("分类ID：" + categoryCode);
        categoryCode = categoryCode.trim();
        Long brandId = jsonObject.getLong("brandId");
        if (brandId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        List<WtProduct> wtProducts = wtProductService.getWtProductListByCategoryCodeBrandId(categoryCode, brandId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtProducts, "请求成功");
    }

    /**
     * 根据商品ID查询商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWtProductListByProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long productId = jsonObject.getLong("productId");
        if (productId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<WtSku> wtSkuList = wtProductService.getWtSkuListByProductId(productId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtSkuList, "请求成功");
    }

    /**
     * 根据套餐名称，套系名称返回机构套系信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSeriesBranchesListBySeriesName(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //套餐名称
        String setmealName = jsonObject.getString("setmealName");
        if (StringUtils.isEmpty(setmealName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套餐名称：" + setmealName);
        setmealName = setmealName.trim();

        //套系名称
        String seriesName = jsonObject.getString("seriesName");
        if (StringUtils.isEmpty(seriesName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套系名称：" + seriesName);
        seriesName = seriesName.trim();
        Long orgId = jsonObject.getLong("orgId");
        if (orgId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<SeriesBranchesEntity> seriesBranchesEntities = setmealService.getSeriesBranchesListBySetmealNameSeriesName(setmealName, seriesName, orgId);

        return new DataMessage(DataMessage.RESULT_SUCESS, seriesBranchesEntities, "请求成功");
    }

    /**
     * 上传图片
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage uploadImg(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //图片文件
        String base64Data = jsonObject.getString("base64Data");
        if (StringUtils.isEmpty(base64Data)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("w文件长度：" + base64Data.length());
        return OssImageUploadUtil.uploadBase64Image(base64Data, OssImageUploadUtil.OSS_GTWATER_PATH);

    }

    /**
     * 创建套餐
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception {
     *                   setmealName：xxx，profile：xxx，series：{
     *                   {
     *                   seriesName: xxx,
     *                   productSKU: xxx,
     *                   productnum: 555,
     *                   branchesID: 333,
     *                   price: 333,
     *                   setmealImg：xxx,
     *                   shopImg: xxx,
     *                   giftSku: xxx,
     *                   giftNum: 222
     *                   },{
     *                   seriesName: xxx,
     *                   productSKU: xxx,
     *                   productnum: 555,
     *                   branchesID: 333,
     *                   price: 333,
     *                   setmealImg：xxx,
     *                   shopImg: xxx,
     *                   giftSku: xxx,
     *                   giftNum: 222
     *                   }
     *                   }
     *                   }
     */
    @Override
    public DataMessage creatSetmeal(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        /**
         * Long loginId, String setmealName, String
         *             profile, List<SeriesEntity> seriesEntityList
         */

        System.out.println(jsonObject.toString());
        //登录用户ID
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "没有登录！");
        }
        log.info("loginId:" + loginId);

        //套餐名称
        String setmealName = jsonObject.getString("setmealName");
        if (StringUtils.isEmpty(setmealName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请输入套餐名称！");
        }
        log.info("setmealName:" + setmealName);
        setmealName = setmealName.trim();

        /*//套餐简介
        String  setmealIntroduction= jsonObject.getString("setmealIntroduction");
        if (StringUtils.isEmpty(setmealName)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请输入套餐名称！");
        }
        log.info("setmealIntroduction:" + setmealIntroduction);
        setmealIntroduction = setmealIntroduction.trim();*/

        //图文详情
        String profile = jsonObject.getString("profile");
        if (StringUtils.isEmpty(profile)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请编辑图文详情！");
        }
        profile = profile.trim();
        JSONArray seriesList = jsonObject.getJSONArray("seriesList");
        if (CollectionUtils.isEmpty(seriesList)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("se.size:+" + seriesList.size());
        List<SeriesEntity> seriesEntities = new ArrayList<>();
        for (int i = 0; i < seriesList.size(); i++) {
            SeriesEntity seriesEntity = new SeriesEntity();
            seriesEntity.setBrandId(seriesList.getJSONObject(i).getLong("brandId"));
            seriesEntity.setCategoryCode(seriesList.getJSONObject(i).getString("categoryCode"));
            seriesEntity.setProductNum(seriesList.getJSONObject(i).getInteger("productNum"));
            seriesEntity.setProductSkuCode(seriesList.getJSONObject(i).getString("productSkuCode"));
            seriesEntity.setSeriesImg(seriesList.getJSONObject(i).getString("seriesImg"));
            seriesEntity.setSeriesName(seriesList.getJSONObject(i).getString("seriesName"));
            seriesEntity.setShopImg(seriesList.getJSONObject(i).getString("shopImg"));
            seriesEntity.setProductSpec(seriesList.getJSONObject(i).getString("productSpec"));
            //赠品
            List<GiftEntity> giftEntities = new ArrayList<>();
            JSONArray gifts = seriesList.getJSONObject(i).getJSONArray("giftList");
            if (CollectionUtils.isEmpty(gifts)) {
                log.info("赠品信息 为空");
                giftEntities = null;
            } else {
                log.info("包含赠品");
                log.info("赠品数据接收："+gifts.toString());
                for (int j = 0; j < gifts.size(); j++) {
                    GiftEntity giftEntity = new GiftEntity();
                    giftEntity.setGiftNum(gifts.getJSONObject(j).getInteger("giftNum"));
                    giftEntity.setGiftId(gifts.getJSONObject(j).getLong("giftId"));
                    giftEntity.setGiftSkuCode(gifts.getJSONObject(j).getString("giftSkuCode"));
                    giftEntity.setGiftType(gifts.getJSONObject(j).getInteger("giftType"));
                    giftEntity.setPick(gifts.getJSONObject(j).getLong("pick"));
                    //giftEntity.setSeriesSkuCode(gifts.getJSONObject(j).getString("seriesSkuCode"));
                    giftEntities.add(giftEntity);
                    log.info("giftSize:+" + giftEntities.size());
                    log.info("giftId:" + giftEntities.get(0).getGiftId());
                }
            }
            seriesEntity.setGiftList(giftEntities);
            //机构价格
            JSONArray seriesBranchesList = seriesList.getJSONObject(i).getJSONArray("seriesBranchesEntityList");
            if (CollectionUtils.isEmpty(seriesBranchesList)) {
                log.info("没有设置区域价格信息");
                return new DataMessage(DataMessage.RESULT_FAILED, null, "没有设置区域价格信息！");
            }
            List<SeriesBranchesEntity> seriesBranchesEntities = new ArrayList<>();
            for (int j = 0; j < seriesBranchesList.size(); j++) {
                SeriesBranchesEntity seriesBranchesEntity = new SeriesBranchesEntity();
                seriesBranchesEntity.setPrice(seriesBranchesList.getJSONObject(j).getLong("price"));
                seriesBranchesEntity.setBranchesId(seriesBranchesList.getJSONObject(j).getLong("branchesId"));
                //seriesBranchesEntity.setSeriesIntroduction(seriesBranchesList.getJSONObject(j).getString("seriesIntroduction"));
                seriesBranchesEntities.add(seriesBranchesEntity);
            }
            seriesEntity.setSeriesBranchesEntityList(seriesBranchesEntities);
            seriesEntities.add(seriesEntity);


        }
        setmealService.creatSetmeal(loginId, setmealName, profile, seriesEntities);


        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 套餐列表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSetMealList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String setmealName = jsonObject.getString("setmealName");
        Integer status = jsonObject.getInteger("status");
        Integer pageNo = jsonObject.getInteger("pageNo");
        Integer pageSize = jsonObject.getInteger("pageSize");
        JSONObject json = setmealService.getSeriesViewlistBySetmealNameStatus(pageNo, pageSize, setmealName, status);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }

    /**
     * 根据套系编号更新上下架状态
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage updateSetmealStatusBySeriesCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //套系编号
        String seriesSkuCode = jsonObject.getString("seriesSkuCode");
        if (StringUtils.isEmpty(seriesSkuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套系编号：" + seriesSkuCode);
        seriesSkuCode = seriesSkuCode.trim();

        //登录用户ID
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        //套系状态
        Integer status = jsonObject.getInteger("status");
        if (status == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("状态值：" + status + "-------上架状态【1上架、0下架】");
        setmealService.updateStatusBySeriesSkuCode(seriesSkuCode, loginId, status);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 根据套系编号更新删除状态
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage updateSetmealDelStataBySeriesCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String seriesSkuCode = jsonObject.getString("seriesSkuCode");
        if (StringUtils.isEmpty(seriesSkuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套系编号：" + seriesSkuCode);
        seriesSkuCode = seriesSkuCode.trim();

        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Integer delStatus = jsonObject.getInteger("delStatus");
        if (delStatus == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("删除状态：" + delStatus + "--------删除状态 1正常、0删除");
        setmealService.updateDelStatusBySeriesSkuCode(seriesSkuCode, loginId, delStatus);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 根据套系skuCode更新套系信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage updateSeriesBySeriesSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {

        /**
         * String seriesSkuCode, String shopImg, String seriesImg, String
         *             profile, List<SeriesBranchesEntity> seriesBranchesEntities, Long loginId
         */

        String seriesSkuCode = jsonObject.getString("seriesSkuCode");
        if (StringUtils.isEmpty(seriesSkuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套系编号：" + seriesSkuCode);
        seriesSkuCode = seriesSkuCode.trim();

        //套系图片地址
        String seriesImg = jsonObject.getString("seriesImg");
        if (StringUtils.isEmpty(seriesImg)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套系图片地址：" + seriesImg);
        seriesImg = seriesImg.trim();

        //购物车图片
        String shopImg = jsonObject.getString("shopImg");
        if (StringUtils.isEmpty(shopImg)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("购物车图片：" + shopImg);
        shopImg = shopImg.trim();

        //套系图文
        String profile = jsonObject.getString("profile");
        if (StringUtils.isEmpty(profile)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("套系图文长度：" + profile.length());
        profile = profile.trim();

        //登录用户ID
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("登录用户ID：" + loginId);

        JSONArray seriesBranchesList = jsonObject.getJSONArray("seriesBranchesList");
        if (CollectionUtils.isEmpty(seriesBranchesList)) {
            log.info("没有设置区域价格");
            return new DataMessage(DataMessage.RESULT_FAILED, null, "没有设置区域价格！");
        }
        List<SeriesBranchesEntity> seriesBranchesEntities = JSONObject.parseArray(seriesBranchesList.toJSONString(), SeriesBranchesEntity.class);
        if (CollectionUtils.isEmpty(seriesBranchesEntities)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        setmealService.updateSeriesBySeriesSkuCode(seriesSkuCode, shopImg, seriesImg, profile, seriesBranchesEntities, loginId);

        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 根据套系编号查询主商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getMainProductMsgBySeriesSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String seriesSkuCode = jsonObject.getString("seriesSkuCode");
        if (StringUtils.isEmpty(seriesSkuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        seriesSkuCode = seriesSkuCode.trim();
        log.info("套系编号：" + seriesSkuCode);
        ProductMsgEntity productMsgEntity = setmealService.getMainProductMsgBySeriesSkuCode(seriesSkuCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, productMsgEntity, "请求成功");
    }

    /**
     * 根据套系编号查询赠品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getGiftProductMsgBySeriesSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String seriesSkuCode = jsonObject.getString("seriesSkuCode");
        if (StringUtils.isEmpty(seriesSkuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        seriesSkuCode = seriesSkuCode.trim();
        log.info("套系编号：" + seriesSkuCode);
        List<ProductMsgEntity> productMsgEntity = setmealService.getGiftProductMsgBySeriesSkuCode(seriesSkuCode);
        return new DataMessage(DataMessage.RESULT_SUCESS, productMsgEntity, "请求成功");
    }

    /**
     * 根据套系编号查询套系详细信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSeriesEntityBySeriesCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String seriesCode = jsonObject.getString("seriesCode");
        if (StringUtils.isEmpty(seriesCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        seriesCode = seriesCode.trim();
        log.info("套系编号：" + seriesCode);

        Long orgId= jsonObject.getLong("orgId");
        if (orgId==null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("组织ID："+orgId);
        SeriesEntity seriesEntity = setmealService.getSeriesEntityBySeriesCode(seriesCode,orgId);
        return new DataMessage(DataMessage.RESULT_SUCESS, seriesEntity, "请求成功");

    }
}
