package com.gt.manager.rpc.setmeal;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.setmenu
 * @ClassName ISetMenuRpcService
 * @Description: 套餐dubbo接口
 * @Author towards
 * @Date 2018/8/1 11:07
 */
public interface ISetmealRpcService {


    //---------------------------creat---start----------------------------------------

    /**
     * 根据skuCode查询商品简要信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getProductSimpleEntityByProductSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据平台id返回一级分类列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getParentCategoryByPlatformId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据一级分类ID查询查询二级分类列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getCategoryListByParentCategoryId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据二级分类查询所属品牌列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getBrandListByCategoryId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据二级分类和品牌id 查询商品信息列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getWtProductListByCategoryIdBrandId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据商品ID查询商品信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getWtProductListByProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据套餐名称，套系名称返回机构套系信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getSeriesBranchesListBySeriesName(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 上传图片
     *
     *
     * @param request
     * @param
     * @return
     * @throws Exception
     */
    DataMessage uploadImg(HttpServletRequest request,JSONObject jsonObject) throws Exception;


    /**
     * 创建套餐
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     * {
     * 	setmealName：xxx，profile：xxx，series：{
     * 		{
     * 			seriesName: xxx,
     * 			productSKU: xxx,
     * 			productnum: 555,
     * 			branchesID: 333,
     * 			price: 333,
     * 			setmealImg：xxx,
     * 			shopImg: xxx,
     * 			giftSku: xxx,
     * 			giftNum: 222
     * 		},{
     * 			seriesName: xxx,
     * 			productSKU: xxx,
     * 			productnum: 555,
     * 			branchesID: 333,
     * 			price: 333,
     * 			setmealImg：xxx,
     * 			shopImg: xxx,
     * 			giftSku: xxx,
     * 			giftNum: 222
     * 		}
     * 	}
     * }
     */
    DataMessage creatSetmeal(HttpServletRequest request, JSONObject jsonObject) throws Exception;


//---------------------------------------creat---end-----------------------------------

    //------------------------------manage-----start--------------------------------
    /**
     * 套餐列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getSetMealList(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据套系编号更新上下架状态
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage updateSetmealStatusBySeriesCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据套系编号更新删除状态
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage updateSetmealDelStataBySeriesCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据套系skuCode更新套系信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage updateSeriesBySeriesSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据套系编号查询主商品信息
     * @param
     * @return
     * @throws Exception
     */
    DataMessage getMainProductMsgBySeriesSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据套系编号查询赠品信息
     * @param
     * @return
     * @throws Exception
     */
    DataMessage getGiftProductMsgBySeriesSkuCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据套系编号查询套系详细信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getSeriesEntityBySeriesCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;



}
