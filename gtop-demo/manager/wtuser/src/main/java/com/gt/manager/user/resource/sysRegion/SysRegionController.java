package com.gt.manager.user.resource.sysRegion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.common.RequestCodeEnum;
import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.user.service.sysRegion.SysRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/sysRegion")
@Api(value = "/sysRegion", description = "省市区信息")
public class SysRegionController {
    private Logger logger = LoggerFactory.getLogger(SysRegionService.class);
    @Autowired
    private SysRegionService sysRegionService;

    @POST
    @Path("/getPCD")
    @ApiOperation(value = "/getPCD", notes = "获取省市区控件数据")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage regionBusiness(ReqData r){

        String platform = r.getPlatform();
        String requestCode = r.getRequestCode();
        String params = r.getParams();
        DataMessage dm = new DataMessage();

        try {
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else if(RequestCodeEnum.获取全部省.req_code.equals(requestCode)){
                List<SysRegion> list = this.sysRegionService.queryProvince();
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(list);
                dm.setMessage("获取成功");
            }else if(RequestCodeEnum.获取省下面市.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                if(jsonObject.containsKey("regionCode")){
                    List<SysRegion> list = this.sysRegionService.queryCity(jsonObject.getString("regionCode"));
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(list);
                    dm.setMessage("获取成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else if(RequestCodeEnum.获取市下面的区.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                if(jsonObject.containsKey("regionCode")){
                    List<SysRegion> list = this.sysRegionService.queryDistrict(jsonObject.getString("regionCode"));
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(list);
                    dm.setMessage("获取成功");
                }else{
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("参数错误");
                }
            }else if(RequestCodeEnum.获取省市区.req_code.equals(requestCode)){
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(this.sysRegionService.queryAll());
                dm.setMessage(null);
            }else{
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("请求码错误");
            }
        }catch (Exception e){
            logger.error("SysRegionController异常",e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("程序异常");
        }
        return dm;
    }

}
