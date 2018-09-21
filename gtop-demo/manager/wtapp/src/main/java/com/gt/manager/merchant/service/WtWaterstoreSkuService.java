package com.gt.manager.merchant.service;

import com.github.pagehelper.PageInfo;
import com.gt.manager.merchant.entity.response.WtWaterstoreSkuResponse;

import java.util.List;
import java.util.Map;

/**
 *商家app商品管理
 * @author fengyueli
 * @date 2018/7/31 11:17
 */
public interface WtWaterstoreSkuService {
    /**
     * 分页查询，商家已添加未删除商品
     * @param pageNumber
     * @param pageSize
     * @param values    商家branchesId,key
     * @return
     */
    PageInfo<WtWaterstoreSkuResponse> findPage(Integer pageNumber, Integer pageSize, Map<String, String> values) ;

    /**
     * 商家已添加的所有商品信息
     * @param pageNumber
     * @param pageSize
     * @param branchesId 水站机构编，模糊关键字
     * @return
     */
    PageInfo<WtWaterstoreSkuResponse> findAllSkuAndSetmeal(Integer pageNumber, Integer pageSize, Long branchesId,String key);
    /**
     * 商家可添加且未添加过的的所有机构商品套餐信息
     * @return
     */
    PageInfo<WtWaterstoreSkuResponse> findAllAddibleSkuAndSetmeal(Integer pageNo, Integer pageSize, Long orgId, Long branchesId, String key) throws Exception;
    /**
     * 上下架商品
     * 修改状态

     * @param param map封装信息，店铺商品表id集合，status
    */
    boolean updateStatus(Integer status, List<Map<String, Object>> param);

    /**
     *可添加商品列表
     * @param pageNo
     * @param pageSize
     * @param param 包含参数，组织id,水站平台机构编号，商品编号或名称
     * @return
     */

    PageInfo<WtWaterstoreSkuResponse> findAddibleGoodsPage(Integer pageNo, Integer pageSize, Map<String, String> param);

    /**
     * 保存商品到水站商品表
     * @param ids 城市机构商品表主键id
     * @param branchesId 水站平台机构编码
     */
    void save(List<Map<String, Object>> ids, Long branchesId, Long creatorId);




    /**
     * 获取商品的详情
     * @param skuId 机构商品主键id
     * @param type 类型，0为商品，1为套餐
     * @return
     */
    Map<String,Object> orgSkuDetail(Long skuId, Integer type);



}
