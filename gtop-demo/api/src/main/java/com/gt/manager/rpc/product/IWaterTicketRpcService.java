package com.gt.manager.rpc.product;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import sun.management.resources.agent;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.product
 * @ClassName IWaterTicketRpcService
 * @Description: 水票dubbo 接口
 * @Author towards
 * @Date 2018/8/1 11:09
 */
public interface IWaterTicketRpcService {

    /**
     * 根据条件查询水票记录（分页）
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getTicketRecord(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据条件导出水票记录
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage exportTicketRecord(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 增加水票
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage addTicket(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 减少水票
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage deleteTicket(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据用户ID获取所拥有水票商品信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getProductByUserId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据用户ID和商品ID获取sku信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getWtSkuByUserIdProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据客户id查询水票简单信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getTicketSimple(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据电话获取用户简单信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getUserSimple(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据电话获取用户收货信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getUserAddressList(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 获取所有城市级别组织机构id
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getAllBranchesList(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据组织ID获取所属商品信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getProductByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 根据商品ID和组织机构ID获取sku信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getProductSkuByBranchesIdProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据订单编号获取套系信息
     * @param orderCode
     * @return
     * @throws Exception
     */

    DataMessage getSetmealListByOrderCode(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 根据上级区域ID查询所属区域信息
     * @param parentId
     * @return
     * @throws Exception
     */

    DataMessage getSysRegionByParentId(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 代客下单
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage proxyCreateOrdreForUser(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 增加客户地址
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage addAddressForUser(HttpServletRequest request, JSONObject jsonObject) throws Exception;









}
