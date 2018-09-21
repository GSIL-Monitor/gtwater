package com.gt.manager.merchant.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.gt.gtop.entity.sys.branches.OpenBranches;
//import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.entity.wtWaterstopSetmeal.WtWaterstopSetmeal;
import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import com.gt.manager.merchant.entity.response.WtWaterstoreSkuResponse;
import com.gt.manager.merchant.repository.WtWaterstopSetmealDAO;
import com.gt.manager.merchant.repository.WtWaterstoreSkuDAO;
import com.gt.manager.merchant.service.WtWaterstoreSkuService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author fengyueli
 * @date 2018/7/31 12:38
 */
@Service
public class WtWaterstoreSkuServiceImpl implements WtWaterstoreSkuService {
    @Autowired
    private WtWaterstoreSkuDAO wtWaterstoreSkuDAO;

    @Autowired
    private WtWaterstopSetmealDAO wtWaterstopSetmealDAO;
    @Reference
    private IBranchesDubboService iBranchesDubboService;


    /**
     * 分页查询，商家所有已添加的商品和套餐信息
     * @param pageNumber
     * @param pageSize
     * @param branchesId    商家branchesId,key
     * @return
     *
     */
    @Override
    public  PageInfo<WtWaterstoreSkuResponse> findAllSkuAndSetmeal(Integer pageNumber, Integer pageSize,Long branchesId,String key) {
        PageHelper.startPage(pageNumber, pageSize);
        //查询商品
        List<WtWaterstoreSkuResponse> list = wtWaterstoreSkuDAO.findAllSkuAndSetmeal(branchesId,key);
        PageInfo<WtWaterstoreSkuResponse> pageInfo=new PageInfo<WtWaterstoreSkuResponse>(list);
        return pageInfo;
    }

    /**
     *可添加商品列表
     * @param pageNo
     * @param pageSize
     * @param orgId 包含参数，组织id,水站平台机构编号，商品编号或名称key
     * @return
     */
    @Override
    public  PageInfo<WtWaterstoreSkuResponse> findAllAddibleSkuAndSetmeal(Integer pageNo, Integer pageSize,Long orgId,Long branchesId,String key) throws Exception {
        //根据组织D，水站 平台机构编码调接口得到城市机构id
        Long cityId = null;
        try {
            OpenBranches filialeByBranches = iBranchesDubboService.getFilialeByBranches(orgId,branchesId);
            cityId = filialeByBranches.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //System.out.println(orgId);
          //Long cityId=111l;
        PageHelper.startPage(pageNo, pageSize);
        List<WtWaterstoreSkuResponse> list=wtWaterstoreSkuDAO.findAllAddibleSkuAndSetmeal(cityId,branchesId,key);
        PageInfo<WtWaterstoreSkuResponse> pageInfo=new PageInfo<WtWaterstoreSkuResponse>(list);
        return pageInfo;

    }
    /**
     * 上下架商品
     * 修改状态

     * @param param map封装信息，店铺商品及套餐的主键id集合，status
     *{
    "status": 1,
    "detail": [{
    "id": 1,
    "type": 0
    }, {
    "id": 1,
    "type": 1
    }]
    }
     */
    @Override
    public boolean updateStatus(Integer status,List<Map<String,Object>> param) {

        Map<String,Object> values=new HashMap<>();

        Long currTime=new Date().getTime();
        if(status==1){
            values.put("shelfOnTime",currTime);
            values.put("updateTime",currTime);
        }else {
            values.put("updateTime",currTime);
        }
        values.put("status",status);

        List<String> skuList=new ArrayList<>();
        List<String> setmealList=new ArrayList<>();
        for (Map<String, Object> p : param) {
            if(p.get("type").toString().equals("0")){
                skuList.add(p.get("id").toString());
            }else{
                setmealList.add(p.get("id").toString());
            }
        }
        values.put("skuList",skuList);
        values.put("setmealList",setmealList);
        if(skuList.size()>0){
            wtWaterstoreSkuDAO.updateStatus(values);

        }
        if(setmealList.size()>0){
            wtWaterstopSetmealDAO.updateStatus(values);
        }


        return true;

    }




      /**
         *可添加商品列表，暂未使用
         * @param pageNo
         * @param pageSize
         * @param param 包含参数，组织id,水站平台机构编号，商品编号或名称key
         * @return
         */
        @Override
        public  PageInfo<WtWaterstoreSkuResponse> findAddibleGoodsPage(Integer pageNo, Integer pageSize, Map<String, String> param) {
            PageHelper.startPage(pageNo, pageSize);
            //根据组织D，水站 平台机构编码调接口得到城市机构id
            //分别从机构套餐与机构商品中根据关键字查询出来结果
            //合并数据，按机构商品上架时间倒序，返回数据
            List<WtWaterstoreSkuResponse> list=wtWaterstoreSkuDAO.findAddibleGoodsPage(param);
            List<WtWaterstoreSkuResponse> setmealList= wtWaterstopSetmealDAO.findAddibleGoodsPage(param);
            for (WtWaterstoreSkuResponse wtWaterstoreSkuResponse : setmealList) {
                wtWaterstoreSkuResponse.setType(1);
            }
            if(list!=null){
                list.addAll(setmealList);
            }else{
                list=setmealList;
            }

            //按商家添加商品的时间倒序，最晚的在最前面
            Collections.sort(list, new Comparator<WtWaterstoreSkuResponse>() {
                @Override
                public int compare(WtWaterstoreSkuResponse o1, WtWaterstoreSkuResponse o2) {
                    if(o1.getOnshelfTime() <= o2.getOnshelfTime()) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
            });
            PageInfo<WtWaterstoreSkuResponse> pageInfo=new PageInfo<WtWaterstoreSkuResponse>(list);
            return pageInfo;

        }

        /**
         * 分页查询，商家已添加未删除商品,暂未使用
         * @param pageNumber
         * @param pageSize
         * @param values    商家branchesId,key
         * @return
         *
         */
        @Override
        public  PageInfo<WtWaterstoreSkuResponse> findPage(Integer pageNumber, Integer pageSize, Map<String, String> values) {
            PageHelper.startPage(pageNumber, pageSize);
            //查询商品
            List<WtWaterstoreSkuResponse> list = wtWaterstoreSkuDAO.findPage(values);
            //查询套餐，合并后返回给前端
            List<WtWaterstoreSkuResponse> setmealList= wtWaterstopSetmealDAO.findPage(values);
            for (WtWaterstoreSkuResponse wtWaterstoreSkuResponse : setmealList) {
                wtWaterstoreSkuResponse.setType(1);
            }
            if(list!=null){
                list.addAll(setmealList);
            }else{
                list=setmealList;
            }

            //按商家添加商品的时间倒序，最晚的在最前面
            Collections.sort(list, new Comparator<WtWaterstoreSkuResponse>() {
                @Override
                public int compare(WtWaterstoreSkuResponse o1, WtWaterstoreSkuResponse o2) {
                    if(o1.getCreateTime() <= o2.getCreateTime()) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
            });
            PageInfo<WtWaterstoreSkuResponse> pageInfo=new PageInfo<WtWaterstoreSkuResponse>(list);
            return pageInfo;
        }
    /**
     * 保存商品到水站商品表
     * @param ids 城市机构商品表主键或套餐id
     * @param branchesId 水站平台机构编码
     * {
    "branchesId": 123,
    "creatorId": 10023,
    "detail": [{
    "id": 1,
    "type": 0
    },
    {
    "id": 1,
    "type": 1
    }
    ]
    }
     */
    @Override
    public void save(List<Map<String,Object>> ids,Long branchesId,Long creatorId) {
       Long waterstoreId =wtWaterstoreSkuDAO.selectIdByBranchesId(branchesId);

        List<Long> skuList=new ArrayList<>();
        List<Long> setmealList=new ArrayList<>();
        for (Map<String, Object> p : ids) {
            if(p.get("type").toString().equals("0")){
                skuList.add(Long.valueOf(p.get("id").toString()));
            }else{
                setmealList.add(Long.valueOf(p.get("id").toString()));
            }
        }
        Long currentTime=new Date().getTime();
        for (Long id : skuList) {
            WtWaterstoreSku wtWaterstoreSku=new WtWaterstoreSku();
            wtWaterstoreSku.setBranchesId(branchesId);//平台机构水站id
            wtWaterstoreSku.setSkuId(id);//区域商品表的id
            String skuCode=wtWaterstoreSkuDAO.selectSkuCodeById(id);
            wtWaterstoreSku.setSkuCode(skuCode);
            wtWaterstoreSku.setWaterstoreId(waterstoreId);
            wtWaterstoreSku.setStatus(0);//默认添加完是下架状态，需手动上架
            wtWaterstoreSku.setCreateTime(currentTime);
            wtWaterstoreSku.setDelState(1);//默认删除状态为正常
            wtWaterstoreSku.setCreateId(creatorId);
           // wtWaterstoreSku.setUpdateId(creatorId);
            wtWaterstoreSkuDAO.insert(wtWaterstoreSku);
        }
        for (Long id : setmealList) {
            WtWaterstopSetmeal wtWaterstopSetmeal=new WtWaterstopSetmeal();
            wtWaterstopSetmeal.setSetmealId(id);
            String skuCode=wtWaterstopSetmealDAO.selectSkuCodeById(id);
            wtWaterstopSetmeal.setSeriesSkuCode(skuCode);
            wtWaterstopSetmeal.setBranchesId(branchesId);
            wtWaterstopSetmeal.setWaterstoreId(waterstoreId);
            wtWaterstopSetmeal.setOnshelfState(0);
            wtWaterstopSetmeal.setCreateTime(currentTime);
            wtWaterstopSetmeal.setCreateId(creatorId);
            wtWaterstopSetmeal.setDelState(1);
            wtWaterstopSetmealDAO.insert(wtWaterstopSetmeal);

        }

    }



    /**
     * 获取机构未添加过的商品的详情
     *
     * @param id   机构商品主键id
     * @param type 类型，0为商品，1为套餐
     * @return
     */
    @Override
    public Map<String, Object> orgSkuDetail(Long id, Integer type) {
        Map<String, Object> map=new HashMap<>();
        if(type==0){
            map=wtWaterstoreSkuDAO.getOrgSkuDetail(id);
        }else{
            map =wtWaterstopSetmealDAO.getOrgSetmealDetail(id);
        }
        return map;
    }

    public static void main(String[] args) {
        Integer i=2;
      System.out.print(i.equals("2"));
        System.out.print(i.equals(2));
    }

}
