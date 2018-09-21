package com.gt.manager.order.resource.setMeal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gt.manager.BaseResource;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.Result;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.order.service.setMeal.impl.SetMealServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("/setMeal")
@Api(value = "/SetMeal", description = "套餐" + "")
public class SetMealResources extends BaseResource {

    private static Logger log = Logger.getLogger(SetMealResources.class);

    @Autowired
    private SetMealServiceImpl service;

    /**
     * @Description  根据套餐ID来查找套餐详情
     * @author
     * @return
     */
    @GET
    @Path("/getSetMeal")
    @ApiOperation(value = "/getSetMeal", notes = "展示用户下的所有水票信息")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getOrderPage(ReqData re) {
        DataMessage res = null;
        WtOrgSetmeal ut = new WtOrgSetmeal();
        JSONObject json = JSON.parseObject(re.getParams());
        ut.setSetmealCode(json.getString("setmealCode"));
        log.info("WtOrgSetmeal======"+ut);
        try {
            List<WtOrgSetmeal> response = service.selectSetMeal(ut);
            res = new DataMessage(Result.SUCCESS.getCode(),response,null);
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }
}
