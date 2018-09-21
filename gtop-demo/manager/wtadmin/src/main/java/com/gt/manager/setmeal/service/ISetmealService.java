package com.gt.manager.setmeal.service;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import com.gt.manager.entity.wtadmin.SeriesBranchesEntity;
import com.gt.manager.entity.wtadmin.SeriesEntity;

import java.util.List;

/**
 * @Package com.gt.manager.setmeal.service
 * @ClassName ISetmealService
 * @Description:
 * @Author towards
 * @Date 2018/8/6 16:33
 */
public interface ISetmealService {


    //---------------------creat-----start--------------------------------------------


    /**
     * 根据套餐名称和套系名称查询套系机构价格信息
     * @param setmealName
     * @param seriesName
     * @return
     * @throws Exception
     */
    public List<SeriesBranchesEntity> getSeriesBranchesListBySetmealNameSeriesName(String setmealName, String seriesName,Long orgId) throws Exception;


    /**
     * 创建套系接口
     * @param loginId
     * @param setmealName
     * @param profile
     * @param seriesEntityList
     * @throws Exception
     */
    public void creatSetmeal(Long loginId, String setmealName, String profile, List<SeriesEntity> seriesEntityList) throws Exception;


    //--------------------creat-----------------end----------------------------------

    //-------------------------manage--------------start------------------------


    /**
     * 根据状态和套餐名称查询套餐信息
     * @param setmealName
     * @param status
     * @return
     * @throws Exception
     */
    public JSONObject getSeriesViewlistBySetmealNameStatus(Integer pageNo, Integer pageSize, String setmealName, Integer status) throws  Exception;




    /**
     * 根据套系编号更新上下架状态
     * 上架状态【1上架、0下架】
     * @param seriesSkuCode
     * @param loginId
     * @throws Exception
     */
    public void updateStatusBySeriesSkuCode(String seriesSkuCode,Long loginId,Integer status) throws Exception;


    /**
     * 根据套系编号更新删除状态
     * 删除状态 1正常、0删除
     * @param seriesSkuCode
     * @param loginId
     * @throws Exception
     */
    public void updateDelStatusBySeriesSkuCode(String seriesSkuCode,Long loginId,Integer delStatus) throws Exception;


    /**
     * 根据套系编号更新套系信息
     * @param seriesSkuCode
     * @param loginId
     * @throws Exception
     */
    public void updateSeriesBySeriesSkuCode(String seriesSkuCode, String shopImg, String seriesImg, String
            profile, List<SeriesBranchesEntity> seriesBranchesEntities, Long loginId) throws Exception;


    /**
     * 根据套系编号查询主商品信息
     * @param seriesSkuCode
     * @return
     * @throws Exception
     */
    public ProductMsgEntity getMainProductMsgBySeriesSkuCode(String seriesSkuCode) throws Exception;


    /**
     * 根据套系编号查询赠品信息
     * @param seriesSkuCode
     * @return
     * @throws Exception
     */
    public List<ProductMsgEntity> getGiftProductMsgBySeriesSkuCode(String seriesSkuCode) throws Exception;


    /**
     * 根据套系编号查询套系详细信息
     * @param seriesCode
     * @return
     * @throws Exception
     */
    public SeriesEntity getSeriesEntityBySeriesCode(String seriesCode, Long orgId) throws Exception;

    //---------------------------manage----------------end-----------------------

}
