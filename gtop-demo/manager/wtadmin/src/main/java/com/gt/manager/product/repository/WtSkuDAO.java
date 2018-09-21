package com.gt.manager.product.repository;

import com.gt.manager.entity.wtSku.WtSku;

import com.gt.manager.entity.wtadmin.BranchesPriceEntity;
import com.gt.manager.entity.wtadmin.ProductDetailEntity;
import com.gt.manager.entity.wtadmin.ProductSimpleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品sku表
 *
 * @author why
 */
@Mapper
public interface WtSkuDAO {

    // Methods

    /**
     * 插入商品sku表
     *
     * @param wtSku 参数实体
     * @return id 插入后的数据库主键值
     */
    public Long insert(WtSku wtSku);

    /**
     * 删除商品sku表
     *
     * @param id 主键值
     */
    public void delete(Long id);

    /**
     * 更新商品sku表
     *
     * @param wtSku 参数实体
     */
    public void update(WtSku wtSku);

    /**
     * 主键查询商品sku表
     *
     * @param id 主键值
     * @return WtSku 实体
     */
    public WtSku selectById(Long id);

    /**
     * 根据条件查询商品sku表列表
     *
     * @param wtSku 参数实体
     * @return List<WtSku> 实体List
     */
    public List<WtSku> selectList(WtSku wtSku);

    /**
     * 批量更新sku
     *
     * @param wtSkuList
     */
    public void batchUpdate(List<WtSku> wtSkuList);

    /**
     * 批量上下架
     * 状态[1销售中（上架），2下架]
     *
     * @param -status  状态
     * @param -skuList
     */
    public void setSkuStatusBySkuCodeList(Map<String,Object> map);


    /**
     * 根据skuCode列表查询ID列表
     * @param map
     */
    public List<Long> selectIdListBySkuCodeList(Map<String,Object> map);

    /**
     * 批量更改删除状态
     * 状态[1销售中（上架），2下架]
     *
     * @param -status   状态
     * @param -delStata 删除状态  【1正常---0删除】
     * @param
     */
    public void setSkuDelStataBySkuCodeList(Map<String,Object> map);

    /**
     * 根据sku查询机构价格信息列表
     *
     * @param sku
     * @return
     */
    public List<BranchesPriceEntity> getBranchesPriceListBySku(String sku);

    /**
     * 批量上下架
     * 状态[1销售中（上架），2下架]
     *
     * @param status  状态
     * @param skuList
     */
    public void setSkuStatusByWtSkuList(@Param("loginId") Long loginId, @Param("updateTime") Long updateTime, @Param("status") Integer status, @Param("skuList") List<WtSku> skuList);

    /**
     * 批量更改删除状态
     * 状态[1销售中（上架），2下架]
     *
     * @param status   状态
     * @param delStata 删除状态  【1正常---0删除】
     * @param skuList
     */
    public void setSkuDelStataByWtSkuList(@Param("loginId") Long loginId, @Param("updateTime") Long updateTime, @Param("delStata") Integer delStata, @Param("status") Integer status, @Param("skuList") List<WtSku> skuList);

    /**
     * 根据产品sku模糊查询信息
     *
     * @param productSkuCode
     * @return
     */
    public List<WtSku> getWtSkuListBySkuCodeBlur(String productSkuCode);


    /**
     * 根据商品ID查询sku信息列表
     *
     * @param productId
     * @return
     * @throws Exception
     */

    public List<WtSku> getWtSkuListByProductId(Long productId);

    /**
     * 根据sku查询商品信息
     *
     * @param productSkuCode
     * @return
     * @throws Exception
     */

    public List<WtSku> getWtSkuBySkuCode(String productSkuCode);

    /**
     * 根据套系ID查询赠品信息
     *
     * @param -SeriesSkuCode
     * @return
     * @throws Exception
     */

    public List<WtSku> getWtSkuListBySeriesSkuCode(String seriesSkuCode);


    /**
     * 批量增加sku信息
     *
     * @param wtSkuList
     */
    public void insertBatch(List<WtSku> wtSkuList);

    /**
     * 根据分支机构ID和商品sku编号更新删除状态
     *
     * @param -skuCode
     * @param -branchesId
     */
    public void setDelStatusBySkuCodeBranchesId(Map<String,Object> map);


    /**
     * 根据商品sku编号和分支机构ID更新分支机构价格
     *
     * @param -skuCode
     * @param -branchesId
     * @param -loginId
     * @param -updateTime
     * @param -price
     */
    public void setBranchesPriceBySkuCodeBranchesId(Map<String,Object> map);


    /**
     * 根据产品skuCode查询简要信息
     *
     * @param skuCode
     * @return
     */
    public ProductSimpleEntity getProductSimpleEntityByProductSkuCode(String skuCode);

    /**
     * 根据组织机构ID和产品ID查询sku信息
     *
     * @param branchesId
     * @param productId
     * @return
     */
    public List<WtSku> getProductSkuByBranchesIdProductId(@Param("branchesId") Long branchesId, @Param("productId") Long productId);

    /**
     * 根据分支机构ID和skuCode查询sku信息
     *
     * @param skuCode
     * @param branchesId
     * @return
     */
    public WtSku getWtSkuBySkuCodeBranchesId(
            @Param("skuCode") String skuCode,
            @Param("branchesId") Long branchesId);

    /**
     * 根据用户ID和商品ID查询sku信息
     * * @param userId
     *
     * @param productId
     * @return
     */
    public List<WtSku> getWtSkuByUserIdProductId(
            @Param("userId") Long userId,
            @Param("productId") Long productId);


    /**
     * 根据skuCode查询详细信息
     * @param skuCode
     * @return
     */
    public ProductDetailEntity selectDetailProductBySkuCode(String skuCode);
}