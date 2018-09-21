package com.gt.manager.user.service.WtProductService.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.user.entity.Goods;
import com.gt.manager.user.repository.wtProductDAO.WtProductDAO;
import com.gt.manager.user.service.WtProductService.WtProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSONObject;



@Service
public class WtProductServiceImpl implements WtProductService {
    private static Logger log = Logger.getLogger(WtProductServiceImpl.class);

    @Autowired
    private WtProductDAO wtProductDAO;

    @Reference
    IBranchesDubboService iBranchesDubboService;

    @Override
    public Map<String, Object> selectproduct(JSONObject json) {
        if (json.getString("id") == null) {
            log.error("水站ID不能为空");
            throw new RuntimeException("水站ID不能为空");
        }
        List<Map<String, Object>> list =  wtProductDAO.selectByIds(json);
        List<Map<String, Object>> list2 =  wtProductDAO.selectSetmeal(json);

        if(list.size()<1){
        }
        List datasList2 = new ArrayList();
        for(int i=0;i<list.size();i++){
            Map maps2 = new HashMap();
            maps2.put("data",list.get(i));
            List setmealList = new ArrayList();
            for(int j=0;j<list2.size();j++){
                Map maps3 = new HashMap();
                if(String.valueOf(list.get(i).get("skuCode")).equals(String.valueOf(list2.get(j).get("skuCode")))){
                    Map map = new HashMap();
                    map.put("seriesSkuCode",list2.get(j).get("seriesSkuCode"));
                    Map mapPro = new HashMap();
                    mapPro.put("skuCode", list2.get(j).get("skuCode"));
                    mapPro.put("waterstoreId", json.getString("id") );
                    Map sequences = wtProductDAO.selectProducts(mapPro);
                    Goods gods = mapToGoods(sequences);
                    list2.get(j).put("sequences",JSONObject.toJSONString(gods));
                    list2.get(j).put("gift",wtProductDAO.queryGift(map));
                    setmealList.add(list2.get(j));
                }
            }
            maps2.put("setmeaData",setmealList);
            datasList2.add(maps2);
        }
        log.info("selectproduct==============="+datasList2);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("data",datasList2);
        return maps;
    }

    /**
     * @Description 首页banner图
     * @author
     * @return
     */
    @Override
    public Map<String, Object> selectBanner() throws Exception {
        List<Map<String, Object>> list =  wtProductDAO.selectBanner();
        log.info("selectSetmeal==============="+list);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("data",list);
        return maps;
    }
    /**
     * @Description 范围内最近水站信息
     * @author
     * @return
     */
    @Override
    public  Map <String, Object> getListBySceneIdInLocation(Long orgId, Integer sceneId, String address) throws Exception {
        HashMap<String,Object>  map = new HashMap<String,Object>();
        log.info("第二个  orgId === "+orgId+" sceneId ===="+sceneId+" address====="+address);
        List<OpenBranches> ListBySceneIdInLocation = null;
        try {
            ListBySceneIdInLocation = iBranchesDubboService.getListBySceneIdInLocation(orgId,sceneId,address);
        }catch (Exception e){
            log.error("接口未通");
            e.printStackTrace();
        }

        log.info("ListBySceneIdInLocation=========================="+ListBySceneIdInLocation);
            if(ListBySceneIdInLocation != null && !ListBySceneIdInLocation.isEmpty() ){
                OpenBranches datas = ListBySceneIdInLocation.get(0);
                map.put("data",datas);
               // JSONObject json = JSONObject.parseObject(String.valueOf(ListBySceneIdInLocation.get(0)));
                //int id = 1001;//String.valueOf(json.get("id"));
                HashMap has = new HashMap();
                 has.put("branchesId",datas.getId());
                HashMap maps = wtProductDAO.queryWaterstoreId(has);
                if( "".equals(maps) || maps == null || maps.size() <1  ){
                    log.error("该地址没有水站");
                    throw new RuntimeException("该地址没有水站");
                }
                log.info("maps========"+maps);
                JSONObject json2 = new JSONObject();
                json2.put("id",maps.get("waterstoreId"));
                List<Map<String, Object>> list =  wtProductDAO.selectByIds(json2); //查询商品
                List<Map<String, Object>> list2 =  wtProductDAO.selectSetmeal(json2);//查询套餐
                log.info("list========"+list);
                log.info("list2========"+list2);
                List datasList = new ArrayList();
                if(list != null && !list.isEmpty()){
                    for(int i = 0;i<list.size();i++){
                        Map maps2 = new HashMap();
                        maps2.put("data",list.get(i));
                        List setmealList = new ArrayList();
                        if(list2 != null && !list2.isEmpty()) {
                            for (int j = 0; j < list2.size(); j++) {
                                if (String.valueOf(list.get(i).get("skuCode")).equals(String.valueOf(list2.get(j).get("skuCode")))) {
                                    Map map42 = new HashMap();
                                    Map mapPro = new HashMap();
                                    mapPro.put("skuCode", list2.get(j).get("skuCode"));
                                    mapPro.put("waterstoreId", maps.get("waterstoreId"));
                                    Map sequences = wtProductDAO.selectProducts(mapPro);
                                    Goods gods = mapToGoods(sequences);
                                    list2.get(j).put("sequences",JSONObject.toJSONString(gods));
                                    map42.put("seriesSkuCode", list2.get(j).get("seriesSkuCode"));
                                    if( Long.parseLong(list2.get(j).get("isGift")+"") == 1 ){
                                         list2.get(j).put("gift", wtProductDAO.queryGift(map42));
                                    }else {
                                        list2.get(j).put("gift",new ArrayList());
                                    }
                                    setmealList.add(list2.get(j));
                                }
                            }
                        }
                        maps2.put("setmeaData",setmealList);
                        datasList.add(maps2);
                    }
                }

                log.info("datasList========"+datasList);
                map.put("data",datasList);
                map.put("waterstoreId",maps.get("waterstoreId")) ;
              List<Map<String,Object>> queryCategory = wtProductDAO.queryCategory(maps);
                if( queryCategory == null || queryCategory.isEmpty()){
                    queryCategory = null;
                }
                List<Map<String,Object>> queryBrand = wtProductDAO.queryBrand(maps);
                if(queryBrand == null || queryBrand.isEmpty()){
                    queryBrand = null;
                }
                map.put("dataCategory",queryCategory);
                map.put("dataBrand",queryBrand);
           }else{
                Map map2s = new HashMap();
                List list = new ArrayList();
                map2s.put("data",list);
                map2s.put("dataBrand",list);
                map2s.put("dataCatepory",list);
                map2s.put("waterstoreId",list);
                map.put("data",map2s) ;

            }
        return map;
    }
    public static Goods mapToGoods(Map map){
        Goods g = new Goods();
        //序列
        g.setId(Long.parseLong(map.get("id")+""));
        g.setBranchesId(Long.parseLong(map.get("branches_id")+""));
        g.setPId(Long.parseLong(map.get("p_id")+""));
        g.setGoodsCode(map.get("goods_code")+"");
        g.setBrandId(Long.parseLong(map.get("brand_id")+""));
        g.setSkuName(map.get("sku_name")+"");
        g.setSkuCode(map.get("sku_code")+"");
        g.setGoodsBar(map.get("goods_bar")+"");
        g.setTypeCode(map.get("type_code")+"");
        g.setGoodsSpec(map.get("goods_spec")+"");
//        g.setGoodsWeight(new BigDecimal(map.get("goods_weight")+""));
//        g.setGoodsUtil(Integer.parseInt(map.get("goods_util")+""));
        g.setGoodsSize(map.get("goods_size")+"");
        g.setGoodsColor(map.get("goods_color")+"");
        g.setStatus(Integer.parseInt(map.get("status")+""));
        g.setCostPrice(Long.parseLong(map.get("cost_price")+""));
        g.setPrice(Long.parseLong(map.get("price")+""));
//        g.setOnSales(Integer.parseInt(map.get("on_sales")+""));
        g.setSellPrice(Long.parseLong(map.get("sell_price")+""));
        g.setShelfOnTime(Long.parseLong(map.get("shelf_on_time")+""));
        g.setShellOffReason(map.get("shell_off_reason")+"");
//        g.setCreateTime(Long.parseLong(map.get("create_time")+""));
//        g.setUpdateTime(Long.parseLong(map.get("update_time")+""));
        g.setDelState(Integer.parseInt(map.get("del_state")+""));
//        g.setVersion(Long.parseLong(map.get("version")+""));
        g.setGoodsName(map.get("goods_name")+"");
        g.setGoodsPic(map.get("goods_pic")+"");
        g.setBrandName(map.get("name")+"");
        return g;
    }
    /**
     * 根据用户id 查找用户头像和昵称
     */
    @Override
    public Map <String, Object> selectUserNickNameIcon(Long id) {
        HashMap<String,Object>  map = new HashMap<String,Object>();
        map.put("id",id);

        HashMap<String,Object>  maps = wtProductDAO.selectUserNickNameIcon(map);
        return maps;
    }

    /**
     * 根据用户id 修改用户头像和昵称
     */
    @Override
    public Long UpdateUserNickNameIcon(String id,String nickname,String icon) {
        HashMap<String,Object>  map = new HashMap<String,Object>();
        map.put("id",Long.valueOf(id));
        map.put("icon",icon);
        map.put("nickname",nickname);

        Long  maps = wtProductDAO.updateUserNickNameIcon(map);
        if(maps < 1){
            log.error("修改失败");
            throw new RuntimeException("修改失败");
        }
        return maps;
    }

    /**
     * 查询用户默认地址
     * @param has
     * @return
     */
    @Override
    public Map <String, Object> selectUserAddress(HashMap <String, Object> has) {
        Map <String, Object> maps =  wtProductDAO.selectUserAddress(has);
        return maps;
    }

    /**
     * 修改并添加用户默认地址
     * @param has
     * @return
     */
    @Override
    public Long saveUserAddress(HashMap <String, Object> has) {

        Long upd = wtProductDAO.updateUserAddress(has);
        if (upd <1) {
            log.error("用户地址修改有误");
            throw new RuntimeException("用户地址修改有误");
        }
        Long saveUserAdd =  wtProductDAO.saveUserAddress(has);
        if (saveUserAdd <1) {
            log.error("用户地址添加有误");
            throw new RuntimeException("用户地址添加有误");
        }
        return saveUserAdd;
    }

    /**
     * 查找品牌类目
     */
    @Override
    public Map<String, Object> queryBrandCategory(HashMap<String,Object> has) {
        Map map = new HashMap();
        map.put("dataCategory",wtProductDAO.queryCategory(has));
        map.put("dataBrand",wtProductDAO.queryBrand(has));
        return map;
    }

    /**
     * 通过skucode和水站ID 查找商品详情
     */
    @Override
    public Map <String, Object> queryProductMes(Map <String, Object> has) {
        return wtProductDAO.queryProductMes(has);
    }


}
