package com.gt.manager.account.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.account.service.WtBranchesAccountService;
import com.gt.manager.rpc.app.WaterAccountRpcService;
import com.gt.util.exception.GtopException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
@Service
public class WaterAccountRpcServiceImpl implements WaterAccountRpcService {

    @Autowired
    private WtBranchesAccountService wtBranchesAccountService;

    @Override
    public DataMessage tixianAccountMoneyToBranches(HttpServletRequest request, JSONObject jsonObject) throws Exception {
//        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            synchronized(String.valueOf(jsonObject.getLongValue("onlyId")).intern()) {
                DataMessage result = wtBranchesAccountService.tixianAccountMoneyToBranches(jsonObject.toJSONString());
                return result;
        }
//        }catch (Exception e){
//            e.printStackTrace();
//            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), e.getMessage());
//        }
    }

    @Override
    public DataMessage tixianAccountMoneyToWeiXin(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            synchronized(String.valueOf(jsonObject.getLongValue("onlyId")).intern()) {
                DataMessage dataMessage = wtBranchesAccountService.tixianAccountMoneyToWeiXin(jsonObject);
                return dataMessage;
            }
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    /**
     * 提现明细
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage tixianAccountDetailed(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            DataMessage dataMessage = wtBranchesAccountService.tixianAccountDetailed(jsonObject);
            return dataMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }
}
