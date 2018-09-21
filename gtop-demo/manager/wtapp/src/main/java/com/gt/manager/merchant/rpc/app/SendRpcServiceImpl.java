package com.gt.manager.merchant.rpc.app;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
//import com.gt.manager.DataMessage;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.merchant.entity.response.WtSendResponse;
import com.gt.manager.merchant.service.WtSendService;
import com.gt.manager.rpc.app.ISendRpcService;
import com.gt.util.exception.GtopException;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author fengyueli
 * @date 2018/8/3 13:44
 */
@Service
public class SendRpcServiceImpl implements ISendRpcService {
    @Autowired
    private WtSendService wtSendService;

    /**
     * 根据商家平台机构编号branchesId及状态（可空为全部），获取水管家对应个体商家的派送单（状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】）
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSendByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            Long branchesId = jsonObject.getLong("branchesId");
            Integer status = jsonObject.getInteger("status");
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
            PageInfo<WtSendResponse> sendPage = wtSendService.getSendPage(pageNo, pageSize, branchesId, status);


            return new DataMessage(DataMessage.RESULT_SUCESS, sendPage, "查询成功");


    }

    /**
     * 根据派送单编号获取派送单详情
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSendDetailByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {

           String sendNo=jsonObject.getString("sendNo");
            if(sendNo==null){
                throw new GtopException("派单编号不能为空");
            }

            WtSendResponse sendDetailByBranchesId = wtSendService.getSendDetailByBranchesId(sendNo);


            return new DataMessage(DataMessage.RESULT_SUCESS, sendDetailByBranchesId, "查询成功");


    }

    /**
     * 根据派送单编号修改派单状态，接单
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage orderReceiving(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            String sendNo=jsonObject.getString("sendNo");
            if(sendNo==null){
                throw new GtopException("派单编号不能为空");
            }

            wtSendService.orderReceiving(sendNo);
            return new DataMessage(DataMessage.RESULT_SUCESS, "接单成功", "接单成功");

    }

    /**
     * 根据派送单编号修改派单状态，确认送达
     *    {
     *                 "sendNo": 1,
     *                 "detail": {
     *                 "123": 2,
     *                 "345": 4
     *                 }
     *                 }
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage confirmDelivery(HttpServletRequest request, JSONObject jsonObject) throws Exception {

            String sendNo=jsonObject.getString("sendNo");
            Long orgId=jsonObject.getLong("orgId");
            Long branchesId=jsonObject.getLong("branchesId");
            Long loginId=jsonObject.getLong("loginId");
            Map detail = jsonObject.getObject("detail", Map.class);
            if(sendNo==null){
                throw new GtopException("派单编号不能为空");
            }
            if(orgId==null){
                throw new GtopException("组织id不能为空");
            }
            if(branchesId==null){
                throw new GtopException("水站机构编码id不能为空");
            }
            if(loginId==null){
                throw new GtopException("操作人id不能为空");
            }


        synchronized(sendNo.intern()) {
            wtSendService.confirmDelivery(orgId, branchesId, sendNo, loginId, detail);
        }

            return new DataMessage(DataMessage.RESULT_SUCESS, "确认收货成功", "确认收货成功");

    }

    /**
     * 派单不同状态的总数量
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage sendStatusNum(HttpServletRequest request, JSONObject jsonObject) throws Exception {


            Long branchesId=jsonObject.getLong("branchesId");

            if(branchesId==null){
                throw new GtopException("水站机构编码id不能为空");
            }
            Map<String, Integer> stringIntegerMap = wtSendService.sendStatusNum(branchesId);

            return new DataMessage(DataMessage.RESULT_SUCESS, stringIntegerMap, "状态数量查询成功");


    }
}
