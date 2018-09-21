package com.gt.manager.rpc.product;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.product
 * @ClassName IProductRpcService
 * @Description:  水管家产品dubbo接口
 * @Author towards
 * @Date 2018/7/31 13:19
 */
public interface IProductRpcService {


    //-----------------alone--------------start--------------
    /**
     * 编辑水管家机构商品
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage updateSkuBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;



    /**
     * 删除(恢复)sku
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage setSkuDelStataBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 下架(上架)product
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage setSkuStatusBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    //------------------------------alone------------end-----------------------------------------


    //----------------------------------start----------------------------------------------------------
    //添加商品到水管家商品库

    /**
     * 根据skuCode查询公品库sku信息（模糊查询）
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getGtopSkuListBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据skuId查询公品库详细信息，其信息用于增加商品到水管家
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getGtopProductSkuBySkuId(HttpServletRequest request, JSONObject jsonObject) throws Exception;



    /**
     * 根据平台id查询一级分类列表
     *
     * @param platformId
     * @return
     * @throws Exception
     */

    DataMessage getGoodesCategoryListByPlatformId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据上级分类id查询分类信息列表
     *
     * @param parentCategoryId
     * @return
     * @throws Exception
     */
    DataMessage getGoodesCategoryListByParentId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据二级分类查询所属品牌信息列表
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    DataMessage getBrandListByCategoryId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据二级分类和品牌查询商品名称
     * @param categoryId
     * @param brandId
     * @return
     * @throws Exception
     */
    DataMessage getGtopProductListByCategoryIdBrandId(HttpServletRequest request, JSONObject jsonObject) throws Exception;



    /**
     * 根据商品ID查询skuCode列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getGtopSkuCodeListByProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据商品ID查询sku列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getGtopSkuListByProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception;



    /**
     * 根据sku获取基本库基本信息
     * 用于添加水管家商品页面显示信息
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    DataMessage getGtopBranchesProductBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据sku返回商品对应分支机构价格
     *
     * @param skuCode
     * @return
     */
    DataMessage getBranchesListBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据分支机构id，品牌id，商品id，分类id，skuid，父分类id，价格将基本商品库商品信息抓取到水管家商品库
     *
     * @param brandId
     * @param categoryId
     * @param parentCategoryId
     * @param skuId
     * @param productId
     * @throws Exception
     */
    DataMessage creatWtProduct(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    //-------------------------end--------------------

    //商品管理
    //--------------------------------start--------------------

    /**
     * 根据条件查询机构商品列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getProductList(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 批量更新sku商品状态（级联）
     * 状态[1销售中（上架），2下架]
     *
     * @param skuCodeList
     * @param status  状态
     * @throws Exception
     */
    DataMessage setSkuStatusBySkuList(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 批量删除(恢复)商品（级联）
     *
     * @param skuCList
     * @param delStata 删除状态  【1正常---0删除】
     * @throws Exception
     */
    DataMessage setSkuDelStataBySkuList(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 批量更新sku商品信息（不包含上下架状态和删除状态）
     *
     *
     * @param skuList--List<WtSku>
     * @param status  状态 Exception
     */
    DataMessage updateSkuByWtSkuList(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 获取二级分类列表
     * @param request
     * @param jsonObject
     * @return
     */
    DataMessage getSecondLevelCategories(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 更新sku区域价格
     * @param request
     * @param jsonObject
     * @return
     */
    DataMessage updateWtSku(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据skuCode查询详细信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage selectDetailProductBySkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;
    //---------------------------------end-----------------------

}
