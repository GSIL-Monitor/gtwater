package com.gt.manager.user.resource.homePage;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.BaseResource;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.Result;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.user.service.WtProductService.impl.WtProductServiceImpl;
import com.gt.manager.util.fileUpload.OssImageUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

//import net.sf.json.JSONObject;

@Component
@Path("/homepage")
@Api(value = "/HomePageResources", description = "首页信息" + "")
public class HomePageResources extends BaseResource {

    private static Logger log = Logger.getLogger(HomePageResources.class);

    @Value("${myYml.orgid}")
    private Long orgid;
    @Autowired
    private WtProductServiceImpl homePageService;

    /**
     * @Description 首页商品信息展示
     * @author
     * @return
     */
    @POST
    @Path("/getHomePage")
    @CrossOrigin
    @ApiOperation(value = "/getHomePage", notes = "根据用户id获取用户信息")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getUserById(ReqData re) {
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            log.info("json==========="+json);
            Map<String,Object> ms =  homePageService.selectproduct(json);
            log.info("ms==========="+ms);

            res = new DataMessage(Result.SUCCESS.getCode(),ms,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 首页banner图
     * @author

     * @return
     */
    @POST
    @Path("/getBanner")
    @CrossOrigin
    @ApiOperation(value = "/getBanner", notes = "根据水站id获取水站banner图")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    //@Consumes("application/x-www-form-urlencoded")
    public DataMessage getBanner(ReqData re) {
        DataMessage res = null;
        try {
            Map<String,Object> ms =  homePageService.selectBanner();
            res = new DataMessage(Result.SUCCESS.getCode(),ms,"查询成功");
        } catch (Exception ex) {

            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description 范围内最近水站信息
     * @author
     * @return
     */
    @POST
    @Path("/getWaterPosition")
    @ApiOperation(value = "/getWaterPosition", notes = "范围内最近水站信息")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getWaterPosition(ReqData re) {
        JSONObject json = JSONObject.parseObject(re.getParams());
        DataMessage res = null;
        try {
            Integer sceneId = json.getInteger("sceneId");
            String address = json.getString("address");
            log.info("第一 sceneId ===="+sceneId+" address====="+address);
            if(sceneId != null){
                Map getListBySceneIdInLocation = homePageService.getListBySceneIdInLocation(orgid, sceneId, address);
                res = new DataMessage(Result.SUCCESS.getCode(),getListBySceneIdInLocation,"查询成功");
            }
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description 根据用户id 查找用户头像和昵称
     * @author
     * @return
     */
    @POST
    @Path("/getUserNickNameIcon")
    @ApiOperation(value = "/getUserNickNameIcon", notes = "根据用户id 查找用户头像和昵称")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getUserNickNameIcon(ReqData re) {
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Long id = Long.parseLong(json.getString("Id"));
            Map<String,Object> ms =  homePageService.selectUserNickNameIcon(id);
            res = new DataMessage(Result.SUCCESS.getCode(),ms,"查询成功");

        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description 根据用户id 修改用户头像和昵称
     * @author
     * @return
     */
    @POST
    @Path("/getUpdateUserNickNameIcon")
    @ApiOperation(value = "/getUpdateUserNickNameIcon", notes = "根据用户id 查找用户头像和昵称")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getUpdateUserNickNameIcon(ReqData re) {
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            String id = json.getString("id");
            String nickname = json.getString("nickname");
            String icon = json.getString("icon");
            Long ms =  homePageService.UpdateUserNickNameIcon(id,nickname,icon);
            res = new DataMessage(Result.SUCCESS.getCode(),"1","修改成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"修改失败");
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 上传头像
     * @author
     * @return
     */
    @POST
    @Path("/upLodafmFile")
    @ApiOperation(value = "/upLodafmFile", notes = " 上传头像")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage upLodafmFile(ReqData re){
        DataMessage res = null;
        try {
            com.gt.gtop.entity.base.DataMessage dm2 = null;
            try {
                log.info("fmFile==============="+re.getParams());
                dm2 = OssImageUploadUtil.uploadBase64Image(re.getParams(),"gtwater/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(dm2.getResult() != 0){
                log.error("图片上传失败");
                throw new RuntimeException("图片上传失败");
            }
            log.info("dm2==============="+dm2.getData());
            res = new DataMessage(Result.SUCCESS.getCode(),dm2.getData(),"图片上传成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"图片上传失败");
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 查询用户默认地址
     * @author
     * @return
     */
    @POST
    @Path("/getUserAddress")
    @ApiOperation(value = "/getUserAddress", notes = "根据用户id 查询用户默认地址")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage selectUserAddress(ReqData re) {
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            HashMap<String,Object> hashMap = new HashMap <>();
            hashMap.put("userId",Long.parseLong(json.getString("userId")));
            Map ms =  homePageService.selectUserAddress(hashMap);
            res = new DataMessage(Result.SUCCESS.getCode(),ms,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description 添加用户默认地址
     * @author
     * @return
     */
    @POST
    @Path("/getSaveUserAddress")
    @ApiOperation(value = "/getSaveUserAddress", notes = " 添加用户默认地址")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getSaveUserAddress(ReqData re) {
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            HashMap<String,Object> hashMap = new HashMap <>();
            hashMap.put("userId",json.get("userId"));
            hashMap.put("title",json.get("title"));
            hashMap.put("address",json.get("address"));
            hashMap.put("is_default",json.get("isDefault"));
            hashMap.put("province_id",json.get("provinceId"));
            hashMap.put("province",json.get("province"));
            hashMap.put("city_id",json.get("cityId"));
            hashMap.put("city",json.get("city"));
            hashMap.put("area_Id",json.get("areaId"));
            hashMap.put("area",json.get("area"));
            hashMap.put("remark",json.get("remark"));
            hashMap.put("create_time",System.currentTimeMillis());
            hashMap.put("create_id",json.get("userId"));
            hashMap.put("del_state",1);
            Long ms =  homePageService.saveUserAddress(hashMap);
            res = new DataMessage(Result.SUCCESS.getCode(),ms,"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * 查找品牌类目
     */
    @POST
    @Path("/getBrandCategory")
    @ApiOperation(value = "/getBrandCategory", notes = "查找品牌类目")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getBrandCategory(ReqData re) {
        DataMessage res = null;
        try {
            JSONObject json = JSONObject.parseObject(re.getParams());
            HashMap hashMap = new HashMap();
            hashMap.put("waterstoreId",json.get("waterstoreId"));
            res = new DataMessage(Result.SUCCESS.getCode(),homePageService.queryBrandCategory(hashMap),"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * 通过skucode和水站ID 查找商品详情
     */
    @POST
    @Path("/queryProductMes")
    @ApiOperation(value = "/queryProductMes", notes = "通过skucode和水站ID 查找商品详情")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage queryProductMes(ReqData re) {
        DataMessage res = null;
        try {
            JSONObject json = JSONObject.parseObject(re.getParams());
            Map hashMap = new HashMap();
            hashMap.put("waterstoreId",json.get("waterstoreId"));
            hashMap.put("skuCode",json.get("skuCode"));
            res = new DataMessage(Result.SUCCESS.getCode(),homePageService.queryProductMes(hashMap),"查询成功");
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),"查询失败");
            ex.printStackTrace();
        }
        return res;
    }

}
