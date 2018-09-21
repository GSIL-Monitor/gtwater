package com.gt.manager.function.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.wtFunction.WtFunction;
import com.gt.manager.function.service.IFunctionService;
import com.gt.manager.rpc.function.IFunctionRpcService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Package com.gt.manager.function.rpc
 * @ClassName FunctionRpcService
 * @Description:
 * @Author towards
 * @Date 2018/8/13 20:05
 */
@Service
public class FunctionRpcServiceImpl implements IFunctionRpcService {
    private static final Logger log = Logger.getLogger(FunctionRpcServiceImpl.class);
    @Autowired
    IFunctionService functionService;
    /**
     * 更新发票状态
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage updateFunctionStata(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long id=jsonObject.getLong("id");
        if (id==null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.debug("id:"+id);
        Integer state= jsonObject.getInteger("state");
        if (state==null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("state:"+state);

        Long loginId= jsonObject.getLong("loginId");
        if (loginId==null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.debug("loginId:"+loginId);
        functionService.updateFunctionStata(id,state,loginId);

        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 查询所有配置
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getFunctionlist(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        List<WtFunction> wtFunctions=functionService.getFunctionlist();
        log.info("function.size:"+wtFunctions.size());
        return new DataMessage(DataMessage.RESULT_SUCESS, wtFunctions, "请求成功");
    }
}
