package com.gt.manager.user.resource.receiveAddress;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.common.RequestCodeEnum;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.user.service.receiveAddress.ReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理
 */
@CrossOrigin
@Path("/receiveAddress")
@Api(value = "/receiveAddress", description = "地址")
public class ReceiveAddressController {
    private static Logger logger = LoggerFactory.getLogger(ReceiveAddressController.class);
    @Autowired
    private ReceiveAddressService receiveAddressService;

    @POST
    @Path("/addressBusiness")
    @ApiOperation(value = "/addressBusiness", notes = "地址管理")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage addressBusiness(ReqData r){
        String platform = r.getPlatform();
        String requestCode = r.getRequestCode();
        String params = r.getParams();

        DataMessage dm = new DataMessage();
        try {
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else if(RequestCodeEnum.添加地址.req_code.equals(requestCode)){
                ReceiveAddress add = JSONObject.parseObject(params, new TypeReference<ReceiveAddress>(){});
                Long id = this.receiveAddressService.add(add);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(id);
                dm.setMessage("添加成功");
            }else if(RequestCodeEnum.修改地址.req_code.equals(requestCode)){
                ReceiveAddress edit = JSONObject.parseObject(params, new TypeReference<ReceiveAddress>(){});
                this.receiveAddressService.edit(edit);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("修改成功");
            }else if(RequestCodeEnum.删除地址.req_code.equals(requestCode)){
                JSONObject json = JSONObject.parseObject(params);
                Long id = json.getLong("id");
                this.receiveAddressService.delete(id);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("删除成功");
            }else if(RequestCodeEnum.设置默认地址.req_code.equals(requestCode)){
                JSONObject json = JSONObject.parseObject(params);
                Long id = Long.parseLong(json.getString("id"));
                this.receiveAddressService.updateDefault(id);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("设置成功");
            }else if(RequestCodeEnum.查询地址列表.req_code.equals(requestCode)){
                JSONObject json = JSONObject.parseObject(params);
                int pageSize = json.getInteger("pageSize");
                int pageNo = json.getInteger("pageNo");
                Long userId = null;
                if(json.containsKey("userId")){
                    userId = Long.parseLong(json.getString("userId"));
                    if(userId == null || userId.longValue() == 0L){
                        dm.setResult(DataMessage.RESULT_FAILED);
                        dm.setData(null);
                        dm.setMessage("参数错误");
                    }else{
                        List<ReceiveAddress> result = this.receiveAddressService.queryMyList(userId, pageSize, pageNo);
                        List<ReceiveAddress> newList = new ArrayList<>();
                        result.forEach(item ->{
                            item.setUserId2(item.getUserid()+"");
                            item.setAddId(item.getId()+"");
                        });
                        dm.setResult(DataMessage.RESULT_SUCESS);
                        dm.setData(result);
                        dm.setMessage("查询成功");
                    }
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else if(RequestCodeEnum.根据地址id获取地址.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                if(jsonObject.containsKey("id")){
                    String id = jsonObject.getString("id");
                    ReceiveAddress ra = this.receiveAddressService.queryById(Long.parseLong(id));
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(ra);
                    dm.setMessage("获取成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else if(RequestCodeEnum.根据用户id获取默认地址.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                if(jsonObject.containsKey("userId")){
                    String userId = jsonObject.getString("userId");
                    ReceiveAddress query = new ReceiveAddress();
                    query.setUserid(Long.parseLong(userId));
                    query.setIsDelete(2);
                    query.setIsDefault(1);
                    List<ReceiveAddress> list = this.receiveAddressService.queryList(query);
                    if(null != list && !list.isEmpty()){
                        dm.setResult(DataMessage.RESULT_SUCESS);
                        dm.setData(list.get(0));
                        dm.setMessage("获取成功");
                    }else{
                        dm.setResult(DataMessage.RESULT_FAILED);
                        dm.setData(null);
                        dm.setMessage("尚未设置默认地址");
                    }
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else if("1011".equals(requestCode)){
                //添加地址新
                ReceiveAddress add = JSONObject.parseObject(params, new TypeReference<ReceiveAddress>(){});
                Long id = this.receiveAddressService.addNew(add);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(id);
                dm.setMessage("添加成功");
            }else if("1012".equals(requestCode)){
                //编辑地址新
                ReceiveAddress edit = JSONObject.parseObject(params, new TypeReference<ReceiveAddress>(){});
                this.receiveAddressService.editNew(edit);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(null);
                dm.setMessage("修改成功");
            }else{
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("请求码错误");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("地址管理接口异常"+ex.getMessage());
            logger.error("地址管理接口异常", ex);
            return dm;
        }
        logger.info("地址管理={}",JSONObject.toJSONString(dm));
        return dm;
    }



}
