package com.gt.manager.setmeal.service.impl;

import cn.gtop.api.goods.dto.GtopCategoryDto;
import cn.gtop.api.util.GoodsCodeGenUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.entity.wtGift.WtGift;
import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.entity.wtadmin.*;
import com.gt.manager.product.repository.WtSkuDAO;
import com.gt.manager.product.service.IWtProductService;
import com.gt.manager.setmeal.repository.WtGiftDAO;
import com.gt.manager.setmeal.repository.WtOrgSetmealDAO;
import com.gt.manager.setmeal.service.ISetmealService;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.setmeal.service.impl
 * @ClassName SetmealServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/6 16:34
 */
@com.alibaba.dubbo.config.annotation.Service
public class SetmealServiceImpl implements ISetmealService {
    private static final Logger log = Logger.getLogger(SetmealServiceImpl.class);


    @Autowired
    WtSkuDAO wtSkuDAO;
    @Autowired
    WtOrgSetmealDAO wtOrgSetmealDAO;
    @Autowired
    WtGiftDAO wtGiftDAO;
    @com.alibaba.dubbo.config.annotation.Reference
    IBranchesDubboService branchesDubboService;
    @Autowired
    IWtProductService wtProductService;


    /**
     * 根据套餐名称和套系名称查询套系机构价格信息
     *
     * @param setmealName
     * @param seriesName
     * @return
     * @throws Exception
     */
    @Override
    public List<SeriesBranchesEntity> getSeriesBranchesListBySetmealNameSeriesName(String setmealName, String
            seriesName, Long orgId) throws Exception {
        if (StringUtils.isEmpty(setmealName)) {
            throw new GtopException("套餐信息缺失");
        }
        setmealName = setmealName.trim();
        log.info("套餐名称：" + setmealName);

        if (StringUtils.isEmpty(seriesName)) {
            throw new GtopException("套系信息缺失");
        }
        seriesName = seriesName.trim();
        log.info("套系名称：" + seriesName);
        //查询已有套系
        List<SeriesBranchesEntity> seriesBranchesEntities = wtOrgSetmealDAO.getSeriesBranchesListBySetmealNameSeriesName(setmealName.trim(), seriesName.trim());
        /*if (CollectionUtils.isEmpty(seriesBranchesEntities)){
            return null;
        }*/
        if (CollectionUtils.isNotEmpty(seriesBranchesEntities)) {
            for (SeriesBranchesEntity ss : seriesBranchesEntities
                    ) {
                log.info("id:---" + ss.getBranchesId());

            }
        }

        //开放平台接口--返回分支机构编号，名称，ID(所有城市级别)
        List<OpenBranches> branchesList = null;
        try {
            branchesList = branchesDubboService.getFilialeByOrg(orgId);

        } catch (Exception e) {
            log.info("此区域没有信息", e);
            throw new GtopException("此区域没有机构");

        }

        if (CollectionUtils.isEmpty(branchesList)) {
            return seriesBranchesEntities;
        }
        for (OpenBranches p : branchesList
                ) {
            log.info("gtopbrancheaId:" + p.getId());

        }
        //List<BranchesEntity> branchesList = null;
        List<SeriesBranchesEntity> allSeriesBranchesEntities = new ArrayList<>();
        Integer flag;
        if (CollectionUtils.isEmpty(seriesBranchesEntities)) {
            flag = 2;
            for (int i = 0; i < branchesList.size(); i++) {
                SeriesBranchesEntity temp = new SeriesBranchesEntity();
                temp.setBranchesId(branchesList.get(i).getId());
                temp.setBranchesName(branchesList.get(i).getMecName());
                temp.setFlag(flag);
                allSeriesBranchesEntities.add(temp);
            }
        } else {
            for (int i = 0; i < branchesList.size(); i++) {
                SeriesBranchesEntity temp = new SeriesBranchesEntity();
                temp.setBranchesId(branchesList.get(i).getId());
                temp.setBranchesName(branchesList.get(i).getMecName());
                for (int j = 0; j < seriesBranchesEntities.size(); j++) {
                    //设置价格设置状态  ---1：已设置；2---未设置
                    if (seriesBranchesEntities.get(j).getPrice() == null) {
                        flag = 2;
                    } else {
                        flag = 1;
                    }
                    if (branchesList.get(i).getId().equals(seriesBranchesEntities.get(j).getBranchesId())) {
                        temp.setFlag(flag);
                        temp.setPrice(seriesBranchesEntities.get(j).getPrice());
                        temp.setSeriesId(seriesBranchesEntities.get(j).getSeriesId());
                        temp.setSeriesName(seriesBranchesEntities.get(j).getSeriesName());
                        temp.setSeriesSkuCode(seriesBranchesEntities.get(j).getSeriesSkuCode());
                        temp.setSetmealCode(seriesBranchesEntities.get(j).getSetmealCode());
                        temp.setSetmealName(seriesBranchesEntities.get(j).getSetmealName());
                        //break;
                    }
                }
                allSeriesBranchesEntities.add(temp);
            }
        }
        log.info("size:" + allSeriesBranchesEntities.size());
        return allSeriesBranchesEntities;
    }

    /**
     * 创建套系接口
     *
     * @param loginId
     * @param setmealName
     * @param profile
     * @param seriesEntityList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void creatSetmeal(Long loginId, String setmealName, String profile, List<SeriesEntity> seriesEntityList) throws Exception {
        if (loginId == null) {
            throw new GtopException("缺少登录参数");
        }
        if (StringUtils.isEmpty(setmealName)) {
            throw new GtopException("套餐名称不能为空");
        }
        setmealName = setmealName.trim();
        if (CollectionUtils.isEmpty(seriesEntityList)) {
            throw new GtopException("套餐信息为空");
        }
        for (int i = 0; i < seriesEntityList.size(); i++) {
            Integer isGift;
            String seriesSku = GoodsCodeGenUtil.createGoodsCode(GtopCategoryDto.Platform.WATER.getValue());
            Long creatTime = System.currentTimeMillis();
            List<GiftEntity> giftEntities = seriesEntityList.get(i).getGiftList();


            if (CollectionUtils.isEmpty(giftEntities)) {
                log.info("没有赠品");
                isGift = 0;
            } else {
                log.info("有增品");
                isGift = 1;
                List<WtGift> wtGifts = new ArrayList<>();
                for (int j = 0; j < giftEntities.size(); j++) {
                    WtGift wtGift = new WtGift();
                    wtGift.setCreateId(loginId);
                    wtGift.setCreateTime(creatTime);
                    wtGift.setProductId(giftEntities.get(j).getGiftId());
                    wtGift.setSeriesSkuCode(seriesSku);
                    wtGift.setSkuCode(giftEntities.get(j).getGiftSkuCode());
                    wtGift.setGiftType(giftEntities.get(j).getGiftType());
                    wtGift.setNum(giftEntities.get(j).getGiftNum());
                    wtGift.setPick(giftEntities.get(j).getPick());
                    log.info("wtgift----" + wtGift.toString());
                    wtGifts.add(wtGift);

                }

                Map<String, Object> map = new HashMap<>();
                map.put("wtGifts", wtGifts);

                wtGiftDAO.insertBatch(map);
            }
            List<SeriesBranchesEntity> seriesBranchesList = seriesEntityList.get(i).getSeriesBranchesEntityList();
            if (CollectionUtils.isEmpty(seriesBranchesList)) {
                log.info("没有接收到区域价格信息");
                throw new GtopException("没有接收到区域价格信息");
            }
            List<WtOrgSetmeal> wtOrgSetmeals = new ArrayList<>();
            for (int j = 0; j < seriesBranchesList.size(); j++) {
                WtOrgSetmeal wtOrgSetmeal = new WtOrgSetmeal();
                wtOrgSetmeal.setName(setmealName);
                wtOrgSetmeal.setGoodsProfile(profile);
                wtOrgSetmeal.setBranchesId(seriesBranchesList.get(j).getBranchesId());
                wtOrgSetmeal.setBrandId(seriesEntityList.get(i).getBrandId());
                wtOrgSetmeal.setCreateId(loginId);
                wtOrgSetmeal.setCreateTime(creatTime);
                wtOrgSetmeal.setNum(seriesEntityList.get(i).getProductNum());
                wtOrgSetmeal.setPrice(seriesBranchesList.get(j).getPrice());
                wtOrgSetmeal.setSeriesName(seriesEntityList.get(i).getSeriesName());
                wtOrgSetmeal.setSetmealCode(GoodsCodeGenUtil.createSpuCode());
                wtOrgSetmeal.setSeriesSkuCode(seriesSku);
                wtOrgSetmeal.setSetmealImg(seriesEntityList.get(i).getSeriesImg());
                wtOrgSetmeal.setShopcartImg(seriesEntityList.get(i).getShopImg());
                wtOrgSetmeal.setSkuCode(seriesEntityList.get(i).getProductSkuCode());
                wtOrgSetmeal.setTypeCode(seriesEntityList.get(i).getCategoryCode());
                wtOrgSetmeal.setIsGift(isGift);
                wtOrgSetmeal.setGoodsSpec(seriesEntityList.get(i).getProductSpec());
                wtOrgSetmeals.add(wtOrgSetmeal);
            }
            wtOrgSetmealDAO.insertBatch(wtOrgSetmeals);
        }
    }

    /**
     * 根据状态和套餐名称查询套餐信息
     *
     * @param setmealName
     * @param status
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getSeriesViewlistBySetmealNameStatus(Integer pageNo, Integer pageSize, String setmealName, Integer status) throws
            Exception {
        if (StringUtils.isEmpty(setmealName)) {
            setmealName = null;
        } else {
            setmealName = setmealName.trim();
        }
        boolean isPage = false;
        if (pageNo != null && pageSize != null) {
            isPage = true;
        }
        if (isPage) {
            PageHelper.startPage(pageNo, pageSize);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("setmealName", setmealName);
        map.put("status", status);
        List<SeriesViewEntity> seriesViewEntities = wtOrgSetmealDAO.getSeriesViewlistByStatusSetmealName(map);
        for (SeriesViewEntity s : seriesViewEntities
                ) {
            log.info("isGift:" + s.getIsGift());
        }
        JSONObject json = new JSONObject();
        json.put("seriesViewEntities", seriesViewEntities);
        if (isPage) {
            PageInfo<SeriesViewEntity> pageInfo = new PageInfo<SeriesViewEntity>(seriesViewEntities);
            JSONObject page = new JSONObject();
            page.put("pageNo", pageNo);
            page.put("total", pageInfo.getPages());
            page.put("count", pageInfo.getTotal());
            json.put("page", page);
        }

        return json;
    }


    /**
     * 根据套系编号更新上下架状态
     * 上架状态【1上架、0下架】
     *
     * @param seriesSkuCode
     * @param loginId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusBySeriesSkuCode(String seriesSkuCode, Long loginId, Integer status) throws Exception {

        Long updateTime = System.currentTimeMillis();
        if (StringUtils.isEmpty(seriesSkuCode)) {
            throw new GtopException("缺少套系参数");
        }
        log.info("套系编号：" + seriesSkuCode);
        seriesSkuCode = seriesSkuCode.trim();

        if (loginId == null) {
            throw new GtopException("缺少登录参数");
        }
        if (status == null) {
            throw new GtopException("缺少状态参数");
        }

        int delState = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("seriesSkuCode", seriesSkuCode);
        map.put("loginId", loginId);
        map.put("updateTime", updateTime);
        map.put("status", status);
        wtOrgSetmealDAO.updateStatusBySeriesSkuCode(map);
        //根据状态值上下架  1：上架；0：下架（级联）
        if (status == 1) {
            //套系恢复（保持下架状态）
            delState = 1;
        }
        //更新水站套系状态
        map.put("delState", delState);
        List<Long> idList = wtOrgSetmealDAO.selectIdBySeriesCode(seriesSkuCode);
        map.put("idList", idList);
        wtOrgSetmealDAO.updateWaterStorDelStatusBySeriesIdList(map);

    }

    /**
     * 根据套系编号更新删除状态
     * 删除状态 1正常、0删除
     *
     * @param seriesSkuCode
     * @param loginId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDelStatusBySeriesSkuCode(String seriesSkuCode, Long loginId, Integer delStatus) throws Exception {
        Long updateTime = System.currentTimeMillis();
        Integer status = 0;
        if (StringUtils.isEmpty(seriesSkuCode)) {
            throw new GtopException("缺少套系参数");
        }
        seriesSkuCode = seriesSkuCode.trim();

        if (loginId == null) {
            throw new GtopException("缺少登录参数");
        }
        if (delStatus == null) {
            throw new GtopException("缺少状态参数");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("seriesSkuCode", seriesSkuCode.trim());
        map.put("loginId", loginId);
        map.put("updateTime", updateTime);
        map.put("status", status);
        map.put("delState", delStatus);

        //更新套系删除状态
        wtOrgSetmealDAO.updateDelStatusBySeriesSkuCode(map);
        //更新水站套系删除状态
        wtOrgSetmealDAO.updateWaterStorDelStatusBySeriesSkuCode(map);

    }

    /**
     * 根据套系编号更新套系信息()
     *
     * @param seriesSkuCode
     * @param loginId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSeriesBySeriesSkuCode(String seriesSkuCode, String shopImg, String seriesImg, String
            profile, List<SeriesBranchesEntity> seriesBranchesEntities, Long loginId) throws Exception {


        WtOrgSetmeal wtOrgSetmeal = wtOrgSetmealDAO.getWtOrgSetmealListBySeriesSkuCode(seriesSkuCode.trim()).get(0);
        if (wtOrgSetmeal == null) {
            log.info("没有这个套系");
            throw new GtopException("没有这个套系");
        }
        Long updateTime = System.currentTimeMillis();
        List<Long> newCopy1 = new ArrayList<>();
        List<Long> newCopy2 = new ArrayList<>();
        List<Long> newCopy3 = new ArrayList<>();
        List<Long> oldCopy1 = new ArrayList<>();
        List<Long> oldCopy2 = new ArrayList<>();
        List<Long> oldCopy3 = new ArrayList<>();
        //编辑页面branchesList
        List<Long> newList = new ArrayList<>();
        if (CollectionUtils.isEmpty(seriesBranchesEntities)) {
            log.info("没有接收到区域价格信息");
            throw new GtopException("没有接收到区域价格信息");
        }
        System.out.println("sixw:" + seriesBranchesEntities.size());
        for (int i = 0; i < seriesBranchesEntities.size(); i++) {
            newList.add(seriesBranchesEntities.get(i).getBranchesId());
            newCopy1.add(seriesBranchesEntities.get(i).getBranchesId());
            System.out.println("new1:" + newCopy1.toString());
            newCopy2.add(seriesBranchesEntities.get(i).getBranchesId());
            System.out.println("new2:" + newCopy2.toString());
            newCopy3.add(seriesBranchesEntities.get(i).getBranchesId());
            System.out.println("new3:" + newCopy3.toString());
        }
        //原有branchesList
        List<SeriesBranchesEntity> se = wtOrgSetmealDAO.getSeriesBranchesListBySeriesSkuCode(seriesSkuCode);
        if (CollectionUtils.isEmpty(se)) {
            log.info("没有查询到已有信息");
            throw new GtopException("没有查询到已有信息");
        }
        List<Long> oldList = new ArrayList<>();
        for (int i = 0; i < se.size(); i++) {
            oldList.add(se.get(i).getBranchesId());
            oldCopy1.add(se.get(i).getBranchesId());
            System.out.println("old1:" + oldCopy1.toString());
            oldCopy2.add(se.get(i).getBranchesId());
            System.out.println("old2:" + oldCopy2.toString());
            oldCopy3.add(se.get(i).getBranchesId());
            System.out.println("old3:" + oldCopy3.toString());
        }


        //判断是否有重合数据
        int flag = 0;
        for (int i = 0; i < oldList.size(); i++) {
            if (newList.contains(oldList.get(i))) {
                flag = 1;
                break;
            }
        }

        if (flag == 1) {


            //add
            if (newCopy1.removeAll(oldCopy1) && newCopy1.size() > 0) {
                log.info("newcopy；" + newCopy1.toString());
                List<WtOrgSetmeal> wtOrgSetmeals = new ArrayList<>();
                for (int i = 0; i < newCopy1.size(); i++) {
                    for (int j = 0; j < seriesBranchesEntities.size(); j++) {
                        if (newCopy1.get(i).equals(seriesBranchesEntities.get(j).getBranchesId())) {
                            WtOrgSetmeal wtOrgSetmeal1 = new WtOrgSetmeal();
                            wtOrgSetmeal1 = wtOrgSetmeal;
                            wtOrgSetmeal1.setShopcartImg(shopImg);
                            wtOrgSetmeal1.setSetmealImg(seriesImg);
                            wtOrgSetmeal1.setPrice(seriesBranchesEntities.get(j).getPrice());
                            wtOrgSetmeal1.setCreateTime(updateTime);
                            wtOrgSetmeal1.setCreateId(loginId);
                            wtOrgSetmeal1.setGoodsProfile(profile);
                            wtOrgSetmeal1.setOnshelfState(0);
                            wtOrgSetmeal1.setId(null);
                            wtOrgSetmeal1.setBranchesId(seriesBranchesEntities.get(j).getBranchesId());
                            wtOrgSetmeals.add(wtOrgSetmeal1);

                        }
                    }
                }
                wtOrgSetmealDAO.insertBatch(wtOrgSetmeals);

            }
            //delete
            if (oldCopy2.removeAll(newCopy2) && oldCopy2.size() > 0) {
                log.info("oldcopy2:" + oldCopy2.toString());
                Integer status = 0;
                Integer delStatus = 0;
                Map<String, Object> map = new HashMap<>();
                map.put("seriesSkuCode", seriesSkuCode);
                map.put("loginId", loginId);
                map.put("updateTime", updateTime);
                map.put("status", status);
                map.put("delState", delStatus);
                map.put("branchesList", oldCopy2);


                wtOrgSetmealDAO.updateDelStatusBySeriesSkuCodeBrandchesList(map);
                //查询水站机构ID
                // List<Long> branchesList = wtProductService.selectWaterBranchesIdListByCityBranchesList(oldCopy2);
                //map.put("branchesList", branchesList);
                //wtOrgSetmealDAO.updateWaterStorDelStatusBySeriesSkuCodeBranchesList(map);

            }

            //update
            newCopy3.retainAll(oldCopy3);
            if (true) {
                log.info("newcopy3:" + newCopy3.toString());
                int delState = 1;
                Map<String, Object> map = new HashMap<>();
                map.put("setmealImg", seriesImg);
                map.put("shopcartImg", shopImg);
                map.put("goodsProfile", profile);
                map.put("updateId", loginId);
                map.put("updateTime", updateTime);
                map.put("seriesSkuCode", seriesSkuCode);
                map.put("delState", delState);
                for (int i = 0; i < newCopy3.size(); i++) {
                    for (int j = 0; j < seriesBranchesEntities.size(); j++) {
                        if (newCopy3.get(i).equals(seriesBranchesEntities.get(j).getBranchesId())) {
                            map.put("price", seriesBranchesEntities.get(i).getPrice());
                            map.put("branchesId", seriesBranchesEntities.get(j).getBranchesId());
                            wtOrgSetmealDAO.updateSeriesByBranchesIdSeriesSkuCode(map);
                            wtOrgSetmealDAO.updateWaterStorDelStatusBySeriesSkuCodeBranchesId(map);

                            break;
                        }
                    }
                }
            }
        } else {
            //没有重合数据
            //add新增数据
            List<WtOrgSetmeal> wtOrgSetmeals = new ArrayList<>();

            for (int j = 0; j < seriesBranchesEntities.size(); j++) {

                WtOrgSetmeal wtOrgSetmeal1 = new WtOrgSetmeal();
                wtOrgSetmeal1 = wtOrgSetmeal;
                wtOrgSetmeal1.setShopcartImg(shopImg);
                wtOrgSetmeal1.setSetmealImg(seriesImg);
                wtOrgSetmeal1.setPrice(seriesBranchesEntities.get(j).getPrice());
                wtOrgSetmeal1.setCreateTime(updateTime);
                wtOrgSetmeal1.setCreateId(loginId);
                wtOrgSetmeal1.setGoodsProfile(profile);
                wtOrgSetmeal1.setOnshelfState(0);
                wtOrgSetmeal1.setId(null);
                wtOrgSetmeal1.setBranchesId(seriesBranchesEntities.get(j).getBranchesId());
                wtOrgSetmeals.add(wtOrgSetmeal1);
            }
            wtOrgSetmealDAO.insertBatch(wtOrgSetmeals);

            //删除已设置数据
            int status = 0;
            int delStatus = 0;
            Map<String, Object> map = new HashMap<>();
            map.put("seriesSkuCode", seriesSkuCode);
            map.put("loginId", loginId);
            map.put("updateTime", updateTime);
            map.put("status", status);
            map.put("delState", delStatus);
            map.put("branchesList", oldCopy1);


            wtOrgSetmealDAO.updateDelStatusBySeriesSkuCodeBrandchesList(map);
            //查询水站机构ID
            //List<Long> branchesList = wtProductService.selectWaterBranchesIdListByCityBranchesList(oldCopy1);
            //map.put("branchesList", branchesList);
            //wtOrgSetmealDAO.updateDelStatusBySeriesSkuCode(map);


        }


    }

    /**
     * 根据套系编号查询主商品信息
     *
     * @param seriesSkuCode
     * @return
     * @throws Exception
     */
    @Override
    public ProductMsgEntity getMainProductMsgBySeriesSkuCode(String seriesSkuCode) throws Exception {
        if (StringUtils.isEmpty(seriesSkuCode)) {
            throw new GtopException("套餐参数缺失");
        }
        seriesSkuCode = seriesSkuCode.trim();
        log.info("套系编号：" + seriesSkuCode);
        List<ProductMsgEntity> productMsgEntities = wtOrgSetmealDAO.getMainProductMsgBySeriesSkuCode(seriesSkuCode);
        if (CollectionUtils.isEmpty(productMsgEntities)) {
            throw new GtopException("没有商品信息");
        }
        return productMsgEntities.get(0);
    }

    /**
     * 根据套系编号查询赠品信息
     *
     * @param seriesSkuCode
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductMsgEntity> getGiftProductMsgBySeriesSkuCode(String seriesSkuCode) throws Exception {
        if (StringUtils.isEmpty(seriesSkuCode)) {
            throw new GtopException("套餐参数缺失");
        }
        seriesSkuCode = seriesSkuCode.trim();
        log.info("套系编号：" + seriesSkuCode);
        List<ProductMsgEntity> productMsgEntities = wtGiftDAO.getGiftProductMsgBySeriesSkuCode(seriesSkuCode);
        return productMsgEntities;
    }

    /**
     * 根据套系编号查询套系详细信息
     *
     * @param seriesCode
     * @return
     * @throws Exception
     */
    @Override
    public SeriesEntity getSeriesEntityBySeriesCode(String seriesCode, Long orgId) throws Exception {
        //创建新的套系详情对象
        SeriesEntity seriesEntity = new SeriesEntity();
        //查询套系信息
        List<WtOrgSetmeal> wtOrgSetmeals = wtOrgSetmealDAO.getWtOrgSetmealListBySeriesSkuCode(seriesCode);
        if (CollectionUtils.isEmpty(wtOrgSetmeals)) {
            throw new GtopException("没有这个套餐");
        }
        WtOrgSetmeal wtOrgSetmeal = wtOrgSetmeals.get(0);
        log.info("商品编号：" + wtOrgSetmeal.getSkuCode());

        ProductMsgEntity productMsgEntity = getMainProductMsgBySeriesSkuCode(seriesCode);

        //根据套系信息中的主商品skuCode查询主商品简要信息
        ProductSimpleEntity productSimpleEntity = wtSkuDAO.getProductSimpleEntityByProductSkuCode(wtOrgSetmeal.getSkuCode());


        //根据套系编号查询赠品信息
        List<GiftEntity> giftEntities;
        List<ProductMsgEntity> productMsgEntities;
        if (wtOrgSetmeal.getIsGift() == 0) {
            giftEntities = null;
        } else {
            giftEntities = new ArrayList<>();
            productMsgEntities = getGiftProductMsgBySeriesSkuCode(seriesCode);

            //根据赠品中的skuCode查询赠品简要信息
            if (CollectionUtils.isNotEmpty(productMsgEntities)) {
                for (int i = 0; i < productMsgEntities.size(); i++) {
                    GiftEntity giftEntity = new GiftEntity();
                    giftEntity.setGiftSkuCode(productMsgEntities.get(i).getSkuCode());
                    giftEntity.setGiftNum(productMsgEntities.get(i).getCount());
                    giftEntity.setProductSpec(productMsgEntities.get(i).getProductSpec());
                    giftEntity.setProductSimpleEntity(wtSkuDAO.getProductSimpleEntityByProductSkuCode(productMsgEntities.get(i).getSkuCode()));
                    giftEntity.setGiftType(productMsgEntities.get(i).getType());
                    giftEntities.add(giftEntity);

                }
            }
        }

        //根据套系编号查询区域价格信息
        List<SeriesBranchesEntity> seriesBranchesEntities = getSeriesBranchesListBySeriesCode(seriesCode, orgId);
        //装载套系详细信息
        seriesEntity.setShopImg(wtOrgSetmeal.getShopcartImg());
        seriesEntity.setProductSpec(productMsgEntity.getProductSpec());
        seriesEntity.setSeriesName(wtOrgSetmeal.getSeriesName());
        seriesEntity.setSeriesImg(wtOrgSetmeal.getSetmealImg());
        seriesEntity.setProfile(wtOrgSetmeal.getGoodsProfile());
        seriesEntity.setShopImg(wtOrgSetmeal.getShopcartImg());
        seriesEntity.setSeriesBranchesEntityList(seriesBranchesEntities);
        seriesEntity.setProductSkuCode(wtOrgSetmeal.getSkuCode());
        seriesEntity.setProductNum(wtOrgSetmeal.getNum());
        seriesEntity.setGiftList(giftEntities);
        seriesEntity.setSetmealName(wtOrgSetmeal.getName());
        seriesEntity.setProductSimpleEntity(productSimpleEntity);

        return seriesEntity;
    }

    /**
     * 根据套系编号查询套系机构价格信息
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */

    private List<SeriesBranchesEntity> getSeriesBranchesListBySeriesCode(String seriesCode, Long orgId) throws Exception {

        if (StringUtils.isEmpty(seriesCode)) {
            throw new GtopException("套系信息缺失");
        }
        seriesCode = seriesCode.trim();
        //查询已有套系
        List<SeriesBranchesEntity> seriesBranchesEntities = wtOrgSetmealDAO.getSeriesBranchesListBySeriesCode(seriesCode);
        /*if (CollectionUtils.isEmpty(seriesBranchesEntities)){
            return null;
        }*/
        if (CollectionUtils.isNotEmpty(seriesBranchesEntities)) {
            for (SeriesBranchesEntity ss : seriesBranchesEntities
                    ) {
                log.info("id:---" + ss.getBranchesId());

            }
        }

        //开放平台接口--返回分支机构编号，名称，ID(所有城市级别)
        List<OpenBranches> branchesList = null;
        try {
            branchesList = branchesDubboService.getFilialeByOrg(orgId);

        } catch (Exception e) {
            log.info("此区域没有信息", e);
            throw new GtopException("此区域没有机构");

        }

        if (CollectionUtils.isEmpty(branchesList)) {
            return seriesBranchesEntities;
        }
        for (OpenBranches p : branchesList
                ) {
            log.info("gtopbrancheaId:" + p.getId());

        }
        //List<BranchesEntity> branchesList = null;
        List<SeriesBranchesEntity> allSeriesBranchesEntities = new ArrayList<>();
        Integer flag;
        if (CollectionUtils.isEmpty(seriesBranchesEntities)) {
            flag = 2;
            for (int i = 0; i < branchesList.size(); i++) {
                SeriesBranchesEntity temp = new SeriesBranchesEntity();
                temp.setBranchesId(branchesList.get(i).getId());
                temp.setBranchesName(branchesList.get(i).getMecName());
                temp.setFlag(flag);
                allSeriesBranchesEntities.add(temp);
            }
        } else {
            for (int i = 0; i < branchesList.size(); i++) {
                SeriesBranchesEntity temp = new SeriesBranchesEntity();
                temp.setBranchesId(branchesList.get(i).getId());
                temp.setBranchesName(branchesList.get(i).getMecName());
                for (int j = 0; j < seriesBranchesEntities.size(); j++) {
                    //设置价格设置状态  ---1：已设置；2---未设置
                    if (seriesBranchesEntities.get(j).getPrice() == null) {
                        flag = 2;
                    } else {
                        flag = 1;
                    }
                    if (branchesList.get(i).getId().equals(seriesBranchesEntities.get(j).getBranchesId())) {
                        temp.setFlag(flag);
                        temp.setPrice(seriesBranchesEntities.get(j).getPrice());
                        temp.setSeriesId(seriesBranchesEntities.get(j).getSeriesId());
                        temp.setSeriesName(seriesBranchesEntities.get(j).getSeriesName());
                        temp.setSeriesSkuCode(seriesBranchesEntities.get(j).getSeriesSkuCode());
                        temp.setSetmealCode(seriesBranchesEntities.get(j).getSetmealCode());
                        temp.setSetmealName(seriesBranchesEntities.get(j).getSetmealName());
                        //break;
                    }
                }
                allSeriesBranchesEntities.add(temp);
            }
        }
        log.info("size:" + allSeriesBranchesEntities.size());
        return allSeriesBranchesEntities;
    }
}
