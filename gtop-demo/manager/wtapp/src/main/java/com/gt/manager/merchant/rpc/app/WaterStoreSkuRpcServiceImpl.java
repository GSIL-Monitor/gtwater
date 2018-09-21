package com.gt.manager.merchant.rpc.app;



import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
//import com.gt.manager.DataMessage;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.merchant.entity.response.WtWaterstoreSkuResponse;
import com.gt.manager.merchant.service.WtWaterstoreSkuService;
import com.gt.manager.rpc.app.IWaterStoreSkuRpcService;
import com.gt.util.exception.GtopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



import javax.servlet.http.*;
import java.util.*;

/**
 * @author fengyueli
 * @date 2018/8/2 14:05
 * 水管家商家app商品接口
 *
 */
@Service
public class WaterStoreSkuRpcServiceImpl implements IWaterStoreSkuRpcService {
    private static final Logger log = LoggerFactory.getLogger(WaterStoreSkuRpcServiceImpl.class);

    @Autowired
    private WtWaterstoreSkuService wtWaterstoreSkuService;


    /**
     * 根据商家平台机构编号branchesId，模糊查询条件key，获取水管家对应个体商家的未删除商品数据
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWaterProductByBranchesIdAndKey(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long branchesId=jsonObject.getLong("branchesId");
            String key=jsonObject.getString("key");
            Integer pageNo=jsonObject.getInteger("pageNo");
            Integer pageSize=jsonObject.getInteger("pageSize");
            if(branchesId==null){
                throw new GtopException("水站机构编码不能为空");
            }
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            PageInfo<WtWaterstoreSkuResponse> wtWaterstoreSkuResponses= wtWaterstoreSkuService.findAllSkuAndSetmeal(pageNo,pageSize,branchesId,key);
            return new DataMessage(DataMessage.RESULT_SUCESS, wtWaterstoreSkuResponses, "查询成功");

    }

    /**
     * 根据商家平台机构编号branchesId，获取商家所在1、城市下，2、未曾添加过的，3、未删除，4、已上架 5、模糊查询条件key，获取商家本身可从机构表添加的商品列表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getAddibleWaterProductByBranchesIdAndKey(HttpServletRequest request, JSONObject jsonObject) throws Exception{

            Long branchesId=jsonObject.getLong("branchesId");
            Long orgId=jsonObject.getLong("orgId");
            String key=jsonObject.getString("key");
            Integer pageNo=jsonObject.getInteger("pageNo");
            Integer pageSize=jsonObject.getInteger("pageSize");
            if(branchesId==null){
                throw new GtopException("水站机构编码不能为空");
            }
            if(orgId==null){
                throw new GtopException("组织编码不能为空");
            }
            if(pageNo==null){
                pageNo=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            PageInfo<WtWaterstoreSkuResponse> addibleGoodsPage = wtWaterstoreSkuService.findAllAddibleSkuAndSetmeal(pageNo, pageSize, orgId,branchesId,key);
            return new DataMessage(DataMessage.RESULT_SUCESS, addibleGoodsPage, "查询成功");


    }


    /**
     * 上下架商家自己店铺的商品，根据商家商品id，及上下架的status
     * @param request
     * @param jsonObject  status为上下架状态，1为上架，0为下架，id为商品id,type为类型0为商品，1为套餐
     * {
    "status": 1,
    "detail": [{
    "id": 1,
    "type": 0
    }, {
    "id": 1,
    "type": 1
    }]
    }
     * @return
     * @throws Exception
     */
    public DataMessage updateWaterProductStatus(HttpServletRequest request, JSONObject jsonObject) throws Exception{

            Integer status = jsonObject.getInteger("status");
            JSONArray detail = jsonObject.getJSONArray("detail");
            List maps = detail.toJavaList(Map.class);
            wtWaterstoreSkuService.updateStatus(status,maps);
            return new DataMessage(DataMessage.RESULT_SUCESS, "更新成功", "更新成功");


    }

    /**
     * 根据商家平台机构编号branchesId，商品编号，添加商品到店铺
     * @param request
     * @param jsonObject
     *  {
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
     * @return
     * @throws Exception
     */
    public DataMessage saveWaterProduct(HttpServletRequest request, JSONObject jsonObject) throws Exception{

            Long branchesId = jsonObject.getLong("branchesId");
            Long creatorId = jsonObject.getLong("creatorId");
            if(branchesId==null){
                throw new GtopException("水站机构编码不能为空");
            }
            if(creatorId==null){
                throw new GtopException("操作人不能为空");
            }
            JSONArray detail = jsonObject.getJSONArray("detail");
            if(detail==null){
                throw new GtopException("添加商品不能为空");
            }
            List maps = detail.toJavaList(Map.class);
            wtWaterstoreSkuService.save(maps,branchesId,creatorId);
            return new DataMessage(DataMessage.RESULT_SUCESS, "保存成功", "保存成功");


    }



    /**
     * 获取商品的详情
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage orgSkuDetail(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long id = jsonObject.getLong("skuId");
            Integer type = jsonObject.getInteger("type");//0为商品，1为套餐
            if(id==null){
                throw new GtopException("机构商品id不能为空");
            }
            if(type==null){
                throw new GtopException("机构商品类型不能为空");
            }

            Map<String, Object> map = wtWaterstoreSkuService.orgSkuDetail(id, type);
            return new DataMessage(DataMessage.RESULT_SUCESS, map, "查询成功");


    }

    public static void main(String[] args) throws Exception {
        WaterStoreSkuRpcServiceImpl waterStoreSkuRpcService=new WaterStoreSkuRpcServiceImpl();


        JSONObject jsonObject=new JSONObject();
        String s="{\n" +
                "    \"status\": 1,\n" +
                "    \"detail\": [{\n" +
                "    \"id\": 1,\n" +
                "    \"type\": 0\n" +
                "    }, {\n" +
                "    \"id\": 1,\n" +
                "    \"type\": 1\n" +
                "    }]\n" +
                "    }";
        JSONObject parse = (JSONObject) JSONObject.parse(s);



       // waterStoreSkuRpcService.updateWaterProductStatus(new HttpServletRequest(),parse);
        JSONArray detail = parse.getJSONArray("detail");
        List<Map> maps = detail.toJavaList(Map.class);
        System.out.println(maps);


    }
}
