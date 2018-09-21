package com.gt.manager.order.resource.wtSend;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.BaseResource;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.Result;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.order.service.wtSend.impl.WtSendServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Map;
import java.util.UUID;

//import net.sf.json.JSONObject;


@Path("/sends")
@Api(value = "/sends", description = "派单" + "")
public class WtSendResources extends BaseResource {

    private static Logger log = Logger.getLogger(WtSendResources.class);

    @Autowired
    private WtSendServiceImpl service;



    /**
     * @Description 回调函数 添加派单 与 派单详情
     * @author
     * @return
     */
    @POST
    @Path("/getSaveSend")
    @ApiOperation(value = "/getSaveSend", notes = "添加派单 与派单详情")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getSaveSend(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.insert(json);
            res = new DataMessage(Result.SUCCESS.getCode(),response,null);
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description 一键催单添加
     * @author
     * @return
     */
    @POST
    @Path("/getSaveUrge")
    @ApiOperation(value = "/getSaveUrge", notes = "一键催单添加")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage getSaveUrge(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.insertUrge(json);
            int Resultss = Integer.parseInt(String.valueOf(response.get("Result")));
            int ResultssSet = 0;
            String messa = "";
            if(Resultss == -1){
                ResultssSet = -1;
                messa = "失败";
            }else{
                ResultssSet = 1;
                messa = "成功";
            }
            res = new DataMessage(ResultssSet,response,messa);
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * @Description 推送
     * @author
     * @return
     */
    @GET
    @Path("/getPush")
    @ApiOperation(value = "/getPush", notes = "推送")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage   ceshi() throws Exception {
        JSONObject json = new JSONObject();
        json.put("receiveId","111111");//接受的分支机构id
        json.put("pushId","22222");//推送的分支机构ID
        json.put("title","测试");//消息题目
        json.put("typeCode","123");//编码
        json.put("content","ssssssssssssssssssssssss");//内容
        json.put("thenType","1");//0.为定时发送 1.立即发送
        json.put("type","1");//1:通知消息 2:系统消息
        json.put("sourceName","222");//	开放平台
        json.put("batchId", UUID.randomUUID().toString());//	批次号(batchId = UUID.randomUUID().toString())
        json.put("userIds", "123");//	接受分支机构你的下的所有用户ID

        DataMessage res = null;
        try {
             log.info("json.toString=============="+json.toJSONString());
            Map abc = service.tuisong(json);
             res = new DataMessage(Result.SUCCESS.getCode(),abc,null);
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * @Description  取消派单
     * @author
     * @return
     */
    @POST
    @Path("/cancelSend")
    @ApiOperation(value = "/cancelSend", notes = "取消派单")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage cancelSend(ReqData re){
        DataMessage res = null;
        JSONObject json = JSONObject.parseObject(re.getParams());
        try {
            Map response = service.cancelSend(json);
            res = new DataMessage(Result.SUCCESS.getCode(),response,null);
        } catch (Exception ex) {
            res = new DataMessage(Result.FAIL.getCode(), ex.getMessage().toString(),null);
            ex.printStackTrace();
        }
        return res;
    }



}
