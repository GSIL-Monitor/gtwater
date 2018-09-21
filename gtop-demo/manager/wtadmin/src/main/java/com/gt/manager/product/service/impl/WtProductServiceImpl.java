package com.gt.manager.product.service.impl;


import cn.gtop.api.goods.dto.GtopBrandDto;
import cn.gtop.api.goods.dto.GtopCategoryDto;
import cn.gtop.api.goods.dto.GtopSkuDto;
import cn.gtop.api.goods.dto.GtopSpuDto;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.entity.productCategory.ProductCategory;
import com.gt.manager.entity.wtBrand.WtBrand;
import com.gt.manager.entity.wtProduct.WtProduct;
import com.gt.manager.entity.wtSku.WtSku;
import com.gt.manager.entity.wtadmin.BranchesPriceEntity;
import com.gt.manager.entity.wtadmin.ProductDetailEntity;
import com.gt.manager.entity.wtadmin.ProductSimpleEntity;
import com.gt.manager.entity.wtadmin.WtBranchesProductEntity;
import com.gt.manager.product.repository.*;
import com.gt.manager.product.service.IGtopProductService;
import com.gt.manager.product.service.IWtProductService;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.product.service.impl
 * @ClassName WtProductServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/2 15:10
 */

@Service
public class WtProductServiceImpl implements IWtProductService {
    private static final Logger log = Logger.getLogger(WtProductServiceImpl.class);


    @Autowired
    WtBranchesProductDAO wtBranchesProductDAO;
    @Autowired
    WtSkuDAO wtSkuDAO;
    @Autowired
    WtWaterstoreSkuDAO wtWaterstoreSkuDAO;
    @Autowired
    ProductCategoryDAO productCategoryDAO;
    @Autowired
    WtBrandDAO wtBrandDAO;
    @Autowired
    WtProductDAO wtProductDAO;
    @Autowired
    IGtopProductService gtopProductService;
    @com.alibaba.dubbo.config.annotation.Reference
    IBranchesDubboService branchesDubboService;



    /**
     * 根据条件查询水管家商品列表
     *
     * @param
     * @param startTime
     * @param endTime
     * @param productName
     * @param status
     * @return
     */
    @Override
    public JSONObject getWtPproductList(Integer pageSize, Integer pageNo, Long startTime, Long endTime, String productName, Long categoryId, Integer goodsSource, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("productName", productName);
        map.put("categoryId", categoryId);
        map.put("goodsSource", goodsSource);
        map.put("status", status);
        boolean isPage = false;
        if (pageNo != null && pageSize != null) {
            isPage = true;
        }
        if (isPage) {
            PageHelper.startPage(pageNo, pageSize);
        }

        List<WtBranchesProductEntity> branchesProductList = wtBranchesProductDAO.getWtBranchesProdeuctCountNumList(map);
        JSONObject json = new JSONObject();
        json.put("branchesProductList", branchesProductList);
        if (isPage) {
            PageInfo<WtBranchesProductEntity> pageInfo = new PageInfo<WtBranchesProductEntity>(branchesProductList);
            JSONObject page = new JSONObject();
            page.put("pageNo", pageNo);
            page.put("total", pageInfo.getPages());
            page.put("count", pageInfo.getTotal());
            json.put("page", page);
        }

        return json;
    }


    /**
     * 根据sku返回商品对应分支机构价格
     *
     * @param skuCode
     * @return
     */
    //根据sku 查询所属分支机构id
    //根据分支机构id查询分支机构信息
    //返回分支机构名称，分支机构id，对应的价格
    @Override
    public List<BranchesPriceEntity> getBranchesListBySkuCode(String skuCode, Long orgId) throws Exception {
        if (StringUtils.isEmpty(skuCode)) {
            throw new GtopException("sku编号不能为空");
        }

        List<BranchesPriceEntity> allBranchesPriceList = new ArrayList<>();
        List<OpenBranches> branchesList = null;

        try {
            branchesList = branchesDubboService.getFilialeByOrg(orgId);
        } catch (Exception ex) {
            log.error("gtop没有相关区域信息", ex);
            return null;
        }
        if (CollectionUtils.isEmpty(branchesList)) {
            log.info("此组织下没有分支机构");
            return null;
        }
        log.info("branches.size:" + branchesList.size());
        //查询此商品已设置的区域价格
        List<BranchesPriceEntity> branchesPriceEntityList = wtSkuDAO.getBranchesPriceListBySku(skuCode);

        //如果没有设置，则返回所有城市级别分支机构信息
        if (CollectionUtils.isEmpty(branchesPriceEntityList)) {
            for (int i = 0; i < branchesList.size(); i++) {
                BranchesPriceEntity b = new BranchesPriceEntity();
                b.setBranchesId(branchesList.get(i).getId());
                b.setBranchesName(branchesList.get(i).getMecName());
                b.setFlag(2);
                allBranchesPriceList.add(b);
            }
            return allBranchesPriceList;
        }
        List<Long> integerList = new ArrayList<>();

        for (BranchesPriceEntity branches : branchesPriceEntityList
                ) {

            integerList.add(branches.getBranchesId());

        }
        //开放平台接口--返回分支机构编号，名称，ID(所有城市级别)
        //List<BranchesEntity> branchesList = service.getBrancheList(integerList);


        //List<BranchesEntity> branchesList = null;
        //将机构名称添加到返回信息中

        for (int i = 0; i < branchesList.size(); i++) {
            //log.info("branId:"+branchesList.get(i).getId());
            BranchesPriceEntity temp = new BranchesPriceEntity();
            temp.setBranchesId(branchesList.get(i).getId());
            temp.setBranchesName(branchesList.get(i).getMecName());
            temp.setFlag(2);
            for (int j = 0; j < branchesPriceEntityList.size(); j++) {
                if (branchesList.get(i).getId().equals(branchesPriceEntityList.get(j).getBranchesId())) {
                    // if (branchesPriceEntityList.get(j).getSkuCode() != null) {
                    temp.setSellPrice(branchesPriceEntityList.get(j).getSellPrice());
                    temp.setFlag(1);
                    //}
                    temp.setSkuCode(branchesPriceEntityList.get(j).getSkuCode());
                }

            }
            allBranchesPriceList.add(temp);
        }
        return allBranchesPriceList;
    }

    /**
     * 批量更新sku商品状态（下架级联）
     *
     * @param status      状态[1销售中（上架），2下架]
     * @param skuCodeList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setSkuStatusBySkuCodeList(Long loginId, String loginName, Integer status, List<String> skuCodeList) throws Exception {
        //批量下架
        Long updateTime = System.currentTimeMillis();
        Integer delState = null;
        Map<String, Object> map = new HashMap<>();
        map.put("loginId", loginId);
        map.put("updateTime", updateTime);
        map.put("skuCodeList", skuCodeList);
        map.put("status", status);
        map.put("loginName", loginName);
        wtSkuDAO.setSkuStatusBySkuCodeList(map);

        //下架
        /*if (status == 0) {
            delState = 0;

        }*/
        //批量上架
        if (status == 1) {
            delState = 1;
            map.remove("status");
        }else {//下架
            status=0;
            delState = 0;
            map.put("status",status);
        }
        map.put("delState", delState);

        List<Long> idList=wtSkuDAO.selectIdListBySkuCodeList(map);
        map.remove("skuCodeList");
        map.put("idList",idList);
        wtWaterstoreSkuDAO.setSkuStatusByIdList(map);

    }

    /**
     * 批量删除商品（删除级联）
     *
     * @param delStata    删除状态  【1正常---0删除】
     * @param skuCodeList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setSkuDelStataBySkuCodeList(Long loginId, String loginName, Integer delStata, List<String> skuCodeList) throws Exception {
        //设置商品为下架状态status  状态[1销售中（上架），2下架]
        if (loginId == null) {
            throw new GtopException("没有登录");
        }
        if (delStata == null) {
            throw new GtopException("没有设置状态");
        }
        if (CollectionUtils.isEmpty(skuCodeList)) {
            throw new GtopException("没有目标商品");
        }
        Long updateTime = System.currentTimeMillis();
        Integer status = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("loginId", loginId);

        map.put("skuCodeList", skuCodeList);
        map.put("updateTime", updateTime);
        map.put("status", status);
        map.put("loginName", loginName);
        log.info("map:" + map.toString());
        //批量删除
       /* if (delStata == 0) {


        }*/

        //批量恢复
        if (delStata == 1) {
            map.put("delState", delStata);
            //恢复水管家商品库
            wtSkuDAO.setSkuDelStataBySkuCodeList(map);
            //恢复水站商品库
            wtWaterstoreSkuDAO.setSkuDelStataBySkuCodeList(map);
        } else {
            delStata = 0;
            map.put("delState", delStata);
            //更改水管家商品库删除状态(同时改为下架)
            wtSkuDAO.setSkuDelStataBySkuCodeList(map);
            //更改水站商品库删除状态（同时改为下架）
            wtWaterstoreSkuDAO.setSkuDelStataBySkuCodeList(map);
        }
    }

    /**
     * 批量更新sku商品状态（级联）(暂未使用)
     * 状态[1销售中（上架），2下架]
     *
     * @param status    状态
     * @param wtSkuList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setSkuStatusByWtSkuList(Long loginId, Integer status, List<WtSku> wtSkuList) throws Exception {
        //批量下架
        Long updateTime = System.currentTimeMillis();
        if (status == 2) {
            wtSkuDAO.setSkuStatusByWtSkuList(loginId, updateTime, status, wtSkuList);
            wtWaterstoreSkuDAO.setSkuStatusByWtSkuList(loginId, updateTime, status, wtSkuList);
        }
        //批量上架
        if (status == 1) {
            wtSkuDAO.setSkuStatusByWtSkuList(loginId, updateTime, status, wtSkuList);
        }
    }

    /**
     * 批量删除商品（级联）
     *
     * @param delStata  删除状态  【1正常---0删除】
     * @param wtSkuList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setSkuDelStataByWtSkuList(Long loginId, Integer delStata, List<WtSku> wtSkuList) throws Exception {
        //设置商品为下架状态status  状态[1销售中（上架），2下架]
        Long updateTime = System.currentTimeMillis();
        Integer status = 2;
        //批量删除
        if (delStata == 0) {
            //更改水管家商品库删除状态(同时改为下架)
            wtSkuDAO.setSkuDelStataByWtSkuList(loginId, updateTime, delStata, status, wtSkuList);
            //更改水站商品库删除状态（同时改为下架）
            wtWaterstoreSkuDAO.setSkuDelStataByWtSkuList(loginId, updateTime, delStata, status, wtSkuList);

        }

        //批量恢复
        if (delStata == 1) {

            //恢复水管家商品库
            wtSkuDAO.setSkuDelStataByWtSkuList(loginId, updateTime, delStata, status, wtSkuList);
            //恢复水站商品库
            wtWaterstoreSkuDAO.setSkuDelStataByWtSkuList(loginId, updateTime, delStata, status, wtSkuList);
        }
    }

    /**
     * 根据品牌ID获取品牌信息
     *
     * @param brandId
     * @return
     * @throws Exception
     */
    @Override
    public WtBrand getWtBrandByBrandId(Long brandId) throws Exception {
        if (brandId == null) {
            throw new GtopException("缺少品牌参数");
        }
        return wtBrandDAO.selectById(brandId);
    }

    /**
     * 根据分类id获取分类信息
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public ProductCategory getProductCatogoryByCatogeryId(Long categoryId) throws Exception {
        if (categoryId == null) {
            throw new GtopException("缺少分类参数");
        }
        return productCategoryDAO.selectById(categoryId);
    }

    /**
     * 根据商品ID获取商品信息
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public WtProduct getProductByProductId(Long productId) throws Exception {
        if (productId == null) {
            throw new GtopException("缺少商品参数");
        }
        return wtProductDAO.selectById(productId);
    }

    /**
     * 根据条件查询sku信息
     *
     * @param wtSku
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getWtSkuList(WtSku wtSku) throws Exception {
        if (wtSku == null) {
            throw new GtopException("缺少参数");
        }
        return wtSkuDAO.selectList(wtSku);
    }

    /**
     * 批量更新sku
     *
     * @param wtSkuList
     * @throws Exception
     */
    @Override
    public void updateSkuByWtSkuList(List<WtSku> wtSkuList) throws Exception {

    }

    /**
     * 根据分类级别获取二级分类
     *
     * @param level
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductCategory> getCategoriesListByLevel(Integer level) throws Exception {
        if (level == null) {
            throw new GtopException("请输入级别");
        }
        List<ProductCategory> productCategories = productCategoryDAO.getCategoriesListByLevel(level);
        return productCategories;
    }

    /**
     * 根据商品sku模糊查询sku信息，返回List
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getWtSkuListBySkuCodeBlur(String skuCode) throws Exception {
        if (StringUtils.isEmpty(skuCode)) {
            skuCode = null;
        } else {
            skuCode = skuCode.trim();
        }

        List<WtSku> wtSkuList = wtSkuDAO.getWtSkuListBySkuCodeBlur(skuCode);
        if (CollectionUtils.isEmpty(wtSkuList)) {
            log.info("没有这个商品，skuCode：" + skuCode);
            return null;
        }
        return wtSkuList;
    }

    /**
     * 根据商品skuCode查询商品简要信息
     *
     * @param skuCode
     * @return
     * @throws Exception
     */
    @Override
    public ProductSimpleEntity getProductSimpleEntityByProductSkuCode(String skuCode) throws Exception {
        if (StringUtils.isEmpty(skuCode)) {
            throw new GtopException("缺少产品参数");
        }
        skuCode = skuCode.trim();
        ProductSimpleEntity productSimpleEntity = wtSkuDAO.getProductSimpleEntityByProductSkuCode(skuCode);
        return productSimpleEntity;
    }

    /**
     * 根据平台ID查询一级分类信息
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductCategory> getParentCategoryListByPlatformId(Long platformId) throws Exception {
        List<ProductCategory> productCategories = productCategoryDAO.getParentCategoryListByPlatformId(platformId);
        if (CollectionUtils.isEmpty(productCategories)) {
            log.info("没有一级分类");
            return null;
        }
        return productCategories;
    }

    /**
     * 根据一级分类ID查询二级分类列表
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductCategory> getCategoryListByParentCategoryId(Long categoryId) throws Exception {
        if (categoryId == null) {
            throw new GtopException("缺少分类参数");
        }
        List<ProductCategory> productCategories = productCategoryDAO.getCategoryListByParentCategoryId(categoryId);
        if (CollectionUtils.isNotEmpty(productCategories)) {
            return productCategories;
        }

        return null;
    }

    /**
     * 根据二级分类编号查询所属品牌信息
     *
     * @param categoryCode
     * @return
     * @throws Exception
     */
    @Override
    public List<WtBrand> getWtBrandListByCategoryCode(String categoryCode) throws Exception {
        if (categoryCode == null) {
            throw new GtopException("缺少分类参数");
        }
        List<WtBrand> wtBrands = wtBrandDAO.getWtBrandListByCategoryCode(categoryCode);
        if (CollectionUtils.isNotEmpty(wtBrands)) {
            return wtBrands;
        }
        return null;
    }

    /**
     * 根据品牌ID和二级分类编号查询商品信息列表
     *
     * @param categoryCode
     * @param brandId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtProduct> getWtProductListByCategoryCodeBrandId(String categoryCode, Long brandId) throws Exception {
        if (StringUtils.isEmpty(categoryCode)) {
            throw new GtopException("缺少分类参数");
        }
        if (brandId == null) {
            throw new GtopException("缺少品牌参数");
        }
        List<WtProduct> wtProducts = wtProductDAO.getWtProductListByCategoryCodeBrandId(categoryCode, brandId);
        if (CollectionUtils.isNotEmpty(wtProducts)) {
            return wtProducts;
        }
        return null;
    }

    /**
     * 根据商品ID查询sku信息列表
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getWtSkuListByProductId(Long productId) throws Exception {
        if (productId == null) {
            throw new GtopException("缺少产品参数");
        }
        List<WtSku> wtSkuList = wtSkuDAO.getWtSkuListByProductId(productId);
        if (CollectionUtils.isNotEmpty(wtSkuList)) {
            return wtSkuList;
        }
        return null;
    }

    /**
     * 根据sku查询商品信息
     *
     * @param productSkuCode
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getWtSkuBySkuCode(String productSkuCode) throws Exception {
        if (StringUtils.isEmpty(productSkuCode.trim())) {
            throw new GtopException("缺少产品参数");
        }
        List<WtSku> wtSku = wtSkuDAO.getWtSkuBySkuCode(productSkuCode.trim());

        return wtSku;
    }

    /**
     * 根据套系ID查询赠品信息
     *
     * @param seriesSkuCode
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getWtSkuListBySeriesSkuCode(String seriesSkuCode) throws Exception {
        if (StringUtils.isEmpty(seriesSkuCode)) {
            throw new GtopException("缺少参数");
        }
        List<WtSku> wtSkuList = wtSkuDAO.getWtSkuListBySeriesSkuCode(seriesSkuCode.trim());
        if (CollectionUtils.isNotEmpty(wtSkuList)) {
            return wtSkuList;
        }
        return null;
    }

    /**
     * 更新分支机构价格信息
     *
     * @param branchesPriceEntityList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWtSku(String skuCode, Long loginId, String loginName, List<BranchesPriceEntity> branchesPriceEntityList) throws Exception {

        if (CollectionUtils.isEmpty(branchesPriceEntityList)) {
            throw new GtopException("缺少参数");
        }
        if (loginId == null) {
            throw new GtopException("没有登录");
        }
        List<WtSku> wtSkus = wtSkuDAO.getWtSkuBySkuCode(skuCode);
        if (CollectionUtils.isEmpty(wtSkus)) {
            log.info("没有查询到此skuCode对应商品");
            throw new GtopException("没有这个商品");
        }
        WtSku wtSku = wtSkuDAO.getWtSkuBySkuCode(skuCode).get(0);
        Long updateTime = System.currentTimeMillis();
        List<Long> newCopy1 = new ArrayList<>();
        List<Long> newCopy2 = new ArrayList<>();
        List<Long> newCopy3 = new ArrayList<>();
        List<Long> oldCopy1 = new ArrayList<>();
        List<Long> oldCopy2 = new ArrayList<>();
        List<Long> oldCopy3 = new ArrayList<>();
        //编辑页面list
        List<Long> newList = new ArrayList<>();
        for (int i = 0; i < branchesPriceEntityList.size(); i++) {
            newList.add(branchesPriceEntityList.get(i).getBranchesId());
            newCopy1.add(branchesPriceEntityList.get(i).getBranchesId());
            newCopy2.add(branchesPriceEntityList.get(i).getBranchesId());
            newCopy3.add(branchesPriceEntityList.get(i).getBranchesId());
        }
        log.info("new1---:" + newCopy3.toString());
        log.info("new2---:" + newCopy3.toString());
        log.info("new3---:" + newCopy3.toString());
        List<BranchesPriceEntity> wtBranchesPriceList = wtSkuDAO.getBranchesPriceListBySku(skuCode);
        if (CollectionUtils.isEmpty(wtBranchesPriceList)) {
            log.info("没有查询到此商品数据");
            throw new GtopException("没有商品数据");
        }
        log.info("wtBranches.size:" + wtBranchesPriceList.size());
        List<Long> oldList = new ArrayList<>();
        for (int i = 0; i < wtBranchesPriceList.size(); i++) {
            //if (wtBranchesPriceList.get(i).getPrice()!=null) {
            oldList.add(wtBranchesPriceList.get(i).getBranchesId());
            oldCopy1.add(wtBranchesPriceList.get(i).getBranchesId());
            oldCopy2.add(wtBranchesPriceList.get(i).getBranchesId());
            oldCopy3.add(wtBranchesPriceList.get(i).getBranchesId());
            //}
        }
        log.info("old1---:" + oldCopy3.toString());
        log.info("old2---:" + oldCopy3.toString());
        log.info("old3---:" + oldCopy3.toString());

        //判断是否有重合数据  flag：0-没有重合数据；1--有重合数据
        int flag = 0;
        for (int i = 0; i < oldList.size(); i++) {
            if (newList.contains(oldList.get(i))) {
                log.info("第一个重复数据：" + oldList.get(i));
                flag = 1;
                break;
            }
        }
        log.info("flag:===" + flag);
        if (flag == 1) {//有重合数据

            //add
            if (newCopy1.removeAll(oldCopy1) && newCopy1.size() > 0) {
                log.info("new1add:" + newCopy1.toString());
                List<WtSku> wtSkuList = new ArrayList<>();
                for (int i = 0; i < newCopy1.size(); i++) {
                    for (int j = 0; j < branchesPriceEntityList.size(); j++) {
                        if (newCopy1.get(i).equals(branchesPriceEntityList.get(j).getBranchesId())) {
                            WtSku wtSku1 = new WtSku();
                            wtSku1 = wtSku;
                            wtSku1.setSellPrice(branchesPriceEntityList.get(j).getSellPrice());
                            wtSku1.setBranchesId(branchesPriceEntityList.get(j).getBranchesId());
                            wtSku1.setShelfOnTime(updateTime);
                            wtSku1.setCreateTime(updateTime);
                            wtSku1.setCreateId(loginId);
                            wtSku1.setUpdateId(loginId);
                            wtSku1.setUpdateName(loginName);
                            wtSku1.setUpdateTime(updateTime);
                            wtSku1.setId(null);
                            wtSkuList.add(wtSku1);

                        }


                    }
                }
                wtSkuDAO.insertBatch(wtSkuList);


            }
            //delete
            if (oldCopy2.removeAll(newCopy2) && oldCopy2.size() > 0) {
                log.info("old2del:" + oldCopy2.toString());
                Integer delStatus = 0;
                Map<String, Object> map = new HashMap<>();
                map.put("skuCode", skuCode);
                map.put("loginId", loginId);
                map.put("updateTime", updateTime);
                map.put("delState", delStatus);
                map.put("branchesList", oldCopy2);
                map.put("loginName", loginName);

                wtSkuDAO.setDelStatusBySkuCodeBranchesId(map);
                //查询水站机构ID
               /* List<Long> branchesList= selectWaterBranchesIdListByCityBranchesList(oldCopy2);
                for (int i = 0; i <branchesList.size() ; i++) {
                    log.info("v----:"+branchesList.get(i));
                    
                }
                map.put("branchesList", branchesList);
                wtWaterstoreSkuDAO.setDelStatusBySkuCodeBranchesId(map);*/
                map = null;

            }
            //update
            boolean isTrue = newCopy3.retainAll(oldCopy3);
            log.info("是否合并成功：" + isTrue);
            if (isTrue) {
                log.info("已重合数据：" + newCopy3.toString());
                log.info("原数据：" + oldCopy3.toString());
            } else {
                log.info("未重合数据：" + newCopy3.toString());
                log.info("原数据：" + oldCopy3.toString());
            }

            List<Long> upList = new ArrayList<>();
            for (int i = 0; i < newCopy3.size(); i++) {
                for (int j = 0; j < oldList.size(); j++) {
                    if (newCopy3.get(i).equals(oldCopy3.get(j))) {
                        upList.add(newCopy3.get(i));
                    }
                }
            }
            //if (newCopy3.retainAll(oldCopy3)) {
            if (true) {
                //int delState = 1;
                log.info("new3up:" + upList.toString());
                Map<String, Object> map = new HashMap<>();
                map.put("skuCode", skuCode);
                map.put("loginId", loginId);
                map.put("updateTime", updateTime);
                //map.put("delState", delState);
                map.put("loginName", loginName);


                for (int i = 0; i < upList.size(); i++) {
                    for (int j = 0; j < branchesPriceEntityList.size(); j++) {
                        if (upList.get(i).equals(branchesPriceEntityList.get(j).getBranchesId())) {
                            log.info("bId:" + upList.get(i));
                            log.info("b---Id:" + branchesPriceEntityList.get(j).getBranchesId());
                            map.put("branchesId", upList.get(i));
                            map.put("price", branchesPriceEntityList.get(j).getSellPrice());
                            wtSkuDAO.setBranchesPriceBySkuCodeBranchesId(map);
                            //wtWaterstoreSkuDAO.setDelStatusBySkuCodeBranchesId(map);
                        }
                    }

                }
                map = null;
            }
        } else {

            //提交前后没有重合数据
            //新增
            List<WtSku> wtSkuList = new ArrayList<>();

            for (int j = 0; j < branchesPriceEntityList.size(); j++) {
                log.info("new branchesId:" + branchesPriceEntityList.get(j).getBranchesId());
                WtSku wtSku1 = new WtSku();
                wtSku1 = wtSku;
                wtSku1.setSellPrice(branchesPriceEntityList.get(j).getSellPrice());
                wtSku1.setBranchesId(branchesPriceEntityList.get(j).getBranchesId());
                wtSku1.setShelfOnTime(updateTime);
                wtSku1.setCreateTime(updateTime);
                wtSku1.setCreateId(loginId);
                wtSku1.setId(null);
                wtSku1.setUpdateTime(updateTime);
                wtSku1.setUpdateName(loginName);
                wtSku1.setUpdateId(loginId);
                wtSkuList.add(wtSku1);

            }
            try {
                wtSkuDAO.insertBatch(wtSkuList);
            } catch (GtopException ge) {
                log.error("编辑失败", ge);
                throw new GtopException("更新失败，请重试");
            }

            //删除
            int delState = 0;
            Map<String, Object> map = new HashMap<>();
            map.put("skuCode", skuCode);
            map.put("loginId", loginId);
            map.put("updateTime", updateTime);
            map.put("delState", delState);
            map.put("branchesList", oldCopy1);
            map.put("loginName", loginName);

            wtSkuDAO.setDelStatusBySkuCodeBranchesId(map);
            //查询水站机构ID
            /*List<Long> branchesList= selectWaterBranchesIdListByCityBranchesList(oldCopy1);
            map.put("branchesList", branchesList);
            wtWaterstoreSkuDAO.setDelStatusBySkuCodeBranchesId(map);*/
            map = null;
        }
    }

    /**
     * 根据分支机构ID和商品ID查询sku信息
     *
     * @param branchesId
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getProductSkuByBranchesIdProductId(Long branchesId, Long productId) throws Exception {
        if (branchesId == null) {
            throw new GtopException("缺少机构参数");
        }
        if (productId == null) {
            throw new GtopException("缺少产品参数");
        }
        List<WtSku> wtSkuList = wtSkuDAO.getProductSkuByBranchesIdProductId(branchesId, productId);
        return wtSkuList;
    }

    /**
     * 根据组织机构ID获取商品信息
     *
     * @param branchesId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtProduct> getProductListByBranchesId(Long branchesId) throws Exception {
        if (branchesId == null) {
            throw new GtopException("缺少机构参数");
        }
        List<WtProduct> wtProducts = wtProductDAO.getProductListByBranchesId(branchesId);
        return wtProducts;
    }

    /**
     * 根据用户ID获取商品信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtProduct> getProductListByUserId(Long userId) throws Exception {
        if (userId == null) {
            throw new GtopException("缺少用户信息");
        }

        return wtProductDAO.getProductListByUserId(userId);
    }

    /**
     * 根据 用户ID和商品ID获取sku信息
     *
     * @param userId
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public List<WtSku> getWtSkuListByUserIdProductId(Long userId, Long productId) throws Exception {
        if (userId == null) {
            throw new GtopException("缺少用户参数");
        }
        if (productId == null) {
            throw new GtopException("缺少商品参数");
        }
        List<WtSku> wtSkuList = wtSkuDAO.getWtSkuByUserIdProductId(userId, productId);
        return wtSkuList;
    }

    /**
     * 根据skucode查询详细信息
     *
     * @param skuCode
     * @param orgId
     * @return
     * @throws Exception
     */
    @Override
    public ProductDetailEntity selectDetailProductBySkuCode(String skuCode, Long orgId) throws Exception {
        if (StringUtils.isEmpty(skuCode)) {
            throw new GtopException("缺少产品参数");
        }
        skuCode = skuCode.trim();
        log.info("skuCode:=====" + skuCode);

        if (orgId == null) {
            throw new GtopException("缺少组织参数");
        }
        log.info("组织ID：" + orgId);
        List<BranchesPriceEntity> branchesPriceEntities = this.getBranchesListBySkuCode(skuCode, orgId);


        ProductDetailEntity productDetailEntity = wtSkuDAO.selectDetailProductBySkuCode(skuCode);
        if (productDetailEntity == null) {
            throw new GtopException("没有此商品信息");
        }
        productDetailEntity.setBranchesPriceEntities(branchesPriceEntities);


        return productDetailEntity;
    }


    /**
     * 根据品牌id，商品id，分类id，skuid，父分类id，将基本商品库商品信息抓取到水管家商品库
     *
     * @param brandId
     * @param categoryId
     * @param parentCategoryId
     * @param skuId
     * @param productId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void creatWtProduct(List<BranchesPriceEntity> branchesPriceEntityList, Long brandId, Long productId, Long categoryId, Long parentCategoryId, Long skuId, Long loginId, String loginName) throws Exception {
        if (brandId == null) {
            throw new GtopException("品牌ID不能为空");
        }
        if (categoryId == null) {
            throw new GtopException("分类ID不能为空");
        }
        if (parentCategoryId == null) {
            throw new GtopException("分类父级ID不能为空");
        }
        if (skuId == null) {
            throw new GtopException("skuID不能为空");
        }
        if (productId == null) {
            throw new GtopException("商品ID不能为空");
        }
        Long createTime = System.currentTimeMillis();

        //根据品牌ID获取品牌信息---goods接口
        //判断是否已有此品牌
        //将品牌信息写入水管家商品库
        WtBrand wtBrand = null;


        wtBrand = wtBrandDAO.getWtBrandByGtopBrandId(brandId);
        if (null == wtBrand) {
            GtopBrandDto gtopBrand = gtopProductService.getGtopBrandByGtopBrandId(Integer.valueOf(brandId.intValue()));
            if (gtopBrand == null) {
                throw new GtopException("没有此品牌信息");
            }
            wtBrand = new WtBrand();
            wtBrand.setCreateId(loginId);
            wtBrand.setCreateTime(createTime);
            wtBrand.setMessages(gtopBrand.getMessage());
            wtBrand.setName(gtopBrand.getName());
            wtBrand.setGtopBrandId(gtopBrand.getId());
            wtBrandDAO.insert(wtBrand);
        }
        brandId = wtBrand.getId();


        wtBrand = null;


        //根据一级分类ID获取分类信息----goods
        //判断是否已有此分类
        //将分类信息写入水管家商品库
        ProductCategory productCategory = null;
        String categoryCode = "";

        productCategory = productCategoryDAO.getProductCategoryByGtopCategoryId(parentCategoryId);
        if (productCategory == null) {
            GtopCategoryDto goodsCategory = gtopProductService.getGoodsCategoryByCategoryId(parentCategoryId);
            if (goodsCategory == null) {
                throw new GtopException("没有这个分类");
            }
            productCategory = new ProductCategory();
            productCategory.setCategoryCode(goodsCategory.getCategoryCode());
            productCategory.setCategoryDesc(goodsCategory.getCategoryDesc());
            productCategory.setCategoryLevel(goodsCategory.getCategoryLevel());
            productCategory.setCategoryName(goodsCategory.getCategoryName());
            productCategory.setCategoryType(goodsCategory.getCategoryType());
            productCategory.setCreateBy(loginId);
            productCategory.setCreateTime(createTime);
            productCategory.setGtopCategoryId(goodsCategory.getCategoryId());
            productCategory.setParentCategoryId(null);
            productCategory.setPlatformId(goodsCategory.getPlatformId());
            productCategory.setCustomOrderby(goodsCategory.getCustomOrderby());
            productCategory.setVersion(goodsCategory.getVersion());
            productCategory.setUpdateTime(createTime);
            productCategory.setUpdateBy(loginId);
            productCategoryDAO.insert(productCategory);


        }
        parentCategoryId = productCategory.getCategoryId();


        productCategory = null;

        //根据二级分类ID获取分类信息----goods
        //判断是否已有此分类
        //将二级分类信息写入水管家商品库
        productCategory = productCategoryDAO.getProductCategoryByGtopCategoryId(categoryId);
        if (productCategory == null) {
            GtopCategoryDto goodsCategory = gtopProductService.getGoodsCategoryByCategoryId(categoryId);
            if (goodsCategory == null) {
                throw new GtopException("没有这个分类");
            }
            productCategory = new ProductCategory();
            productCategory.setCategoryCode(goodsCategory.getCategoryCode());
            productCategory.setCategoryDesc(goodsCategory.getCategoryDesc());
            productCategory.setCategoryLevel(goodsCategory.getCategoryLevel());
            productCategory.setCategoryName(goodsCategory.getCategoryName());
            productCategory.setCategoryType(goodsCategory.getCategoryType());
            productCategory.setCreateBy(loginId);
            productCategory.setCreateTime(createTime);
            productCategory.setGtopCategoryId(goodsCategory.getCategoryId());
            productCategory.setParentCategoryId(parentCategoryId);
            productCategory.setPlatformId(goodsCategory.getPlatformId());
            productCategory.setCustomOrderby(goodsCategory.getCustomOrderby());
            productCategory.setVersion(goodsCategory.getVersion());
            productCategory.setUpdateBy(loginId);
            productCategory.setUpdateTime(createTime);
            productCategoryDAO.insert(productCategory);


        }
        categoryId = productCategory.getCategoryId();
        categoryCode = productCategory.getCategoryCode();

        productCategory = null;

        //根据商品ID获取商品信息------goods
        //判断商品信息是否已存在
        //将商品信息写入水管家商品库
        WtProduct wtProduct = null;


        wtProduct = wtProductDAO.getWtProductByGtopGoodsId(productId);
        if (wtProduct == null) {
            GtopSpuDto gtopProduct = gtopProductService.getGtopProductByGtopProductId(productId);
            if (gtopProduct == null) {
                throw new GtopException("没有这个商品");
            }
            wtProduct = new WtProduct();
            wtProduct.setBrandId(brandId);
            wtProduct.setCreateId(loginId);
            wtProduct.setCreateTime(createTime);
            wtProduct.setGoodsName(gtopProduct.getGoodsName());
            wtProduct.setGoodsPhotos(gtopProduct.getGoodsPhotos());
            wtProduct.setGoodsProfile(gtopProduct.getGoodsProfile());
            wtProduct.setGoodsSource(gtopProduct.getGoodsSource());
            wtProduct.setGtopGoodsId(gtopProduct.getId());
            wtProduct.setSpuCode(gtopProduct.getSpuCode());
            wtProduct.setTypeCode(categoryCode);
            wtProduct.setUpdateId(loginId);
            wtProduct.setUpdateTime(createTime);
            wtProductDAO.insert(wtProduct);
        }
        productId = wtProduct.getId();
        String spuCode = wtProduct.getSpuCode();

        wtProduct = null;


        //根据skuid获取sku信息-----goods
        //将sku信息写入水管家商品库
        GtopSkuDto gtopSku = null;
        gtopSku = gtopProductService.getGtopSkuByGtopSkuId(skuId);
        if (gtopSku == null) {
            throw new GtopException("没有此商品sku信息");
        }
        log.info("goodsName:" + gtopSku.getGoodsName());
        List<WtSku> wtSkuList = new ArrayList<>();
        for (int i = 0; i < branchesPriceEntityList.size(); i++) {
            WtSku wtSku = new WtSku();

            wtSku.setBranchesId(branchesPriceEntityList.get(i).getBranchesId());
            wtSku.setBrandId(brandId);
            wtSku.setCostPrice((gtopSku.getCostPrice().multiply(new BigDecimal(100))).longValue());
            wtSku.setCreateId(loginId);
            wtSku.setCreateTime(createTime);
            wtSku.setGoodsBar(gtopSku.getGoodsBarcode());
            wtSku.setGoodsCode(spuCode);
            wtSku.setGoodsColor(gtopSku.getColor());
            wtSku.setGtopSkuId(gtopSku.getGoodsId());
            wtSku.setGoodsSpec(gtopSku.getGoodsSpecification());
            wtSku.setGoodsSize(gtopSku.getGoodsSize());
            wtSku.setGoodsPic(gtopSku.getCartPhoto());
            wtSku.setPId(productId);
            wtSku.setPrice(gtopSku.getPrice().multiply(new BigDecimal(100)).longValue());
            wtSku.setSellPrice(branchesPriceEntityList.get(i).getSellPrice());
            wtSku.setShelfOnTime(createTime);
            wtSku.setSkuCode(gtopSku.getGoodsCode());
            wtSku.setSkuName(gtopSku.getGoodsName() + gtopSku.getGoodsSpecification());
            log.info("skuName:" + wtSku.getSkuName());
            wtSku.setTypeCode(categoryCode);
            wtSku.setUpdateId(loginId);
            wtSku.setUpdateTime(createTime);
            wtSku.setUpdateName(loginName);

            wtSkuList.add(wtSku);
        }
        wtSkuDAO.insertBatch(wtSkuList);


    }

    /**
     * 新增sku记录
     *
     * @param wtSku
     * @throws Exception
     */
    private void creatSku(WtSku wtSku) throws Exception {
        if (wtSku == null) {
            throw new GtopException("sku信息为空");
        }
        wtSkuDAO.insert(wtSku);
    }

    /**
     * 添加商品
     *
     * @param wtProduct
     * @throws Exception
     */
    private void creatProduct(WtProduct wtProduct) throws Exception {
        if (wtProduct == null) {
            throw new GtopException("商品信息为空");
        }
        wtProductDAO.insert(wtProduct);
    }

    /**
     * 添加分类
     *
     * @param productCategory
     * @throws Exception
     */

    private void creatCategory(ProductCategory productCategory) throws Exception {
        if (productCategory == null) {
            throw new GtopException("分类信息为空");
        }
        productCategoryDAO.insert(productCategory);
    }

    /**
     * 添加品牌
     *
     * @param wtBrand
     * @throws Exception
     */
    private void creatBrand(WtBrand wtBrand) throws Exception {
        if (wtBrand == null) {
            throw new GtopException("品牌信息为空");

        }
        wtBrandDAO.insert(wtBrand);
    }
    @Override
    public List<Long> selectWaterBranchesIdListByCityBranchesList(List<Long> branchesList){

        Map<String,Object> map= new HashMap<>();
        map.put("branchesList",branchesList);
            return wtWaterstoreSkuDAO.selectWaterBranchesIdListByCityBranchesId(map);
    }


}
