package com.gt.manager.product.repository;

import com.gt.manager.entity.goodsCategory.GoodsCategory;
import com.gt.manager.entity.gtopBrand.GtopBrand;
import com.gt.manager.entity.gtopProduct.GtopProduct;
import com.gt.manager.entity.wtadmin.GtopBranchesProductEntity;
import com.gt.manager.entity.wtadmin.WtBranchesProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package com.gt.manager.product.repository
 * @ClassName GtopBranchesProductDAO
 *  * @Description:
 * @Author towards
 * @Date 2018/8/1 16:06
 */
@Mapper
public interface GtopBranchesProductDAO {
    /**
     * 从基本商品库获取商品信息列表
     * @param gtopBranchesProductEntity
     * @return
     */

    public List<GtopBranchesProductEntity> selectListFromGtop(GtopBranchesProductEntity gtopBranchesProductEntity);

    /**
     * 从基本商品库获取商品信息
     * @param gtopBranchesProductEntity
     * @return
     */
    public GtopBranchesProductEntity selectProductFromGtop(GtopBranchesProductEntity gtopBranchesProductEntity);



    /**
     * 从机构商品库获取商品信息
     * @param branchesProductEntity
     * @return
     */
    public List<WtBranchesProductEntity> selectListFromGtWater(WtBranchesProductEntity branchesProductEntity);

    /**
     * 根据分类父id查询下级分类id
     * @param cacategoryId
     * @return
     */
    public List<GoodsCategory> getcategoryIdByParentCategoryId(Long cacategoryId);

    /**
     * 根据分类编号查询所包含品牌列表
     * @param categoryTypeCode
     * @return
     */
    public List<GtopBrand> getGtopBrandListByCategoryTypeCode(Long categoryTypeCode);


    /**
     * 根据分类编号，品牌id查询基本商品库商品列表
     * @param gtopProduct
     * @return
     */
    public List<GtopProduct> getGtopProductListByTypeCodeBrandId(GtopProduct gtopProduct);

    /**
     * 根据分类编号，品牌id,商品编号（spu）获取商品sku列表
     * @param gtopProduct
     * @return
     */
    public  List<String> getSkuListByTypeCodeBrandIdSpu(GtopProduct gtopProduct);


    /**
     * 根据sku查询详细商品信息
     * @param sku
     * @return
     */
    public GtopBranchesProductEntity getGtopBranchesproductEntityBySku(String sku);
}
