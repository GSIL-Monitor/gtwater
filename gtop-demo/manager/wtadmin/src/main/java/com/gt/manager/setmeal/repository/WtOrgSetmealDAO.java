package com.gt.manager.setmeal.repository;

import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.entity.wtadmin.ProductMsgEntity;
import com.gt.manager.entity.wtadmin.SeriesBranchesEntity;
import com.gt.manager.entity.wtadmin.SeriesViewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构套餐
 *
 * @author why
 */
@Mapper
public interface WtOrgSetmealDAO {

    // Methods

    /**
     * 插入机构套餐
     *
     * @param wtOrgSetmeal 参数实体
     * @return id 插入后的数据库主键值
     */
    public Long insert(WtOrgSetmeal wtOrgSetmeal);

    /**
     * 删除机构套餐
     *
     * @param id 主键值
     */
    public void delete(Long id);

    /**
     * 更新机构套餐
     *
     * @param wtOrgSetmeal 参数实体
     */
    public int update(WtOrgSetmeal wtOrgSetmeal);

    /**
     * 批量增加套系信息
     * @param wtOrgSetmeals
     */
    public int insertBatch(List<WtOrgSetmeal> wtOrgSetmeals);

    /**
     * 主键查询机构套餐
     *
     * @param id 主键值
     * @return WtOrgSetmeal 实体
     */
    public WtOrgSetmeal selectById(Long id);

    /**
     * 根据条件查询机构套餐列表
     *
     * @param wtOrgSetmeal 参数实体
     * @return List<WtOrgSetmeal> 实体List
     */
    public List<WtOrgSetmeal> selectList(WtOrgSetmeal wtOrgSetmeal);

    /**
     * 根据套餐名称和套系名称查询套系机构价格信息
     *
     * @param setmealName
     * @param seriesName
     * @return
     * @throws Exception
     */
    public List<SeriesBranchesEntity> getSeriesBranchesListBySetmealNameSeriesName(
            @Param("setmealName") String setmealName,
            @Param("seriesName") String seriesName);

    /**
     * 根据状态和套餐名称查询套餐信息
     *
     * @param -setmealName
     * @param -status
     * @return
     * @throws Exception
     */

    public List<SeriesViewEntity> getSeriesViewlistByStatusSetmealName(Map<String,Object> map);

    /**
     * 更新套系上下架状态
     * @param -seriesSkuCode
     * @param -loginId
     * @param -updateTime
     * @param -status
     */
    public void updateStatusBySeriesSkuCode(Map<String,Object> map);

    /**
     * 根据套系编号更新删除状态
     * 删除状态 1正常、0删除
     *
     * @param -seriesSkuCode
     * @param -loginId
     * @throws Exception
     */

    public void updateDelStatusBySeriesSkuCode(Map<String,Object> map);

    /**
     * 根据套系编号和区域批量更新套系删除状态
     * @param map
     */
    public void updateDelStatusBySeriesSkuCodeBrandchesList(Map<String,Object> map);


    /**
     * 更新套系上下架状态
     * @param -seriesSkuCode
     * @param -loginId
     * @param -updateTime
     * @param -status
     */
    public void updateWaterStorStatusBySeriesSkuCode(Map<String,Object> map);

    /**
     * 根据套系编号更新删除状态
     * 删除状态 1正常、0删除
     *
     * @param -seriesSkuCode
     * @param -loginId
     * @throws Exception
     */

    public void updateWaterStorDelStatusBySeriesSkuCode(Map<String,Object> map);


    /**
     * 根据套系ID更新app套餐状态
     * @param map
     */
    public void updateWaterStorDelStatusBySeriesIdList(Map<String,Object> map);


    /**
     * 根据套系编号查询套系生效中套系ID
     * @param map
     * @return
     */
    public List<Long> selectIdBySeriesCode(String seriesSkuCode);

    /**
     * 批量更新水站套系删除状态
     * @param map
     */
    public void updateWaterStorDelStatusBySeriesSkuCodeBranchesList(Map<String,Object> map);


    /**
     * 根据套系skuCode查询机构价格信息
     * @param skuCode
     * @return
     */
    public List<SeriesBranchesEntity> getSeriesBranchesListBySeriesSkuCode(String skuCode);


    /**
     * 根据套系sku编号查询套系信息
     * @param skuCode
     * @return
     */
    public List<WtOrgSetmeal> getWtOrgSetmealListBySeriesSkuCode(String skuCode);


    /**
     * 根据分支机构ID和套系skucode更新套系
     * @param map
     */
    public int updateSeriesByBranchesIdSeriesSkuCode(Map<String,Object> map);


    /**
     * 根据订单号查询套系信息
     * @param orderCode
     * @return
     */
    public List<WtOrgSetmeal> getSetmealListByOrderCode(String orderCode);


    /**
     * 根据套系sku和组织ID删除套系
     * @param map
     */
    public void updateWaterStorDelStatusBySeriesSkuCodeBranchesId(Map<String,Object> map);
    //public void updateDelStatusBySeriesSkuCodeBrachesId(Map<String,Object> map);

    /**
     * 根据套系编号查询主商品信息
     * @param seriesSkuCode
     * @return
     * @throws Exception
     */
    public List<ProductMsgEntity> getMainProductMsgBySeriesSkuCode(String seriesSkuCode);


    /**
     * 根据套系编号查询区域价格信息
     * @param seriesCode
     * @return
     */
    public List<SeriesBranchesEntity> getSeriesBranchesListBySeriesCode(String seriesCode);

}