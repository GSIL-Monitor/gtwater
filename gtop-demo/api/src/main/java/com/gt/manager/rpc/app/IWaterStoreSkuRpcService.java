package com.gt.manager.rpc.app;


import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
//import com.gt.manager.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.app.product
 * @ClassName IWaterStoreSkuRpcService
 * @Description:  水管家商家app产品dubbo接口
 * @Author fengyueli
 * @Date 2018/7/31 13:19
 */
public interface IWaterStoreSkuRpcService {
    /**
     * 根据商家平台机构编号branchesId，模糊查询条件key，获取水管家对应个体商家的未删除商品数据
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getWaterProductByBranchesIdAndKey(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据商家平台机构编号branchesId，获取商家所在1、城市下，2、未曾添加过的，3、未删除，4、已上架 5、模糊查询条件key，获取商家本身可从机构表添加的商品列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getAddibleWaterProductByBranchesIdAndKey(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 上下架商家自己店铺的商品，根据商家商品id，及上下架的status
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage updateWaterProductStatus(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据商家平台机构编号branchesId，商品编号，添加商品到店铺
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage saveWaterProduct(HttpServletRequest request, JSONObject jsonObject) throws Exception;



    /**
     * 获取商品的详情
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage orgSkuDetail(HttpServletRequest request, JSONObject jsonObject) throws Exception;

}
