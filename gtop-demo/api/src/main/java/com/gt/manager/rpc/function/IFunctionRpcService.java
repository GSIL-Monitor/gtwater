package com.gt.manager.rpc.function;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.function
 * @ClassName IFunctionRpcService
 * @Description:
 * @Author towards
 * @Date 2018/8/13 20:02
 */
public interface IFunctionRpcService {
    /**
     * 更新发票状态
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage updateFunctionStata(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 查询所有配置
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public DataMessage getFunctionlist(HttpServletRequest request, JSONObject jsonObject) throws Exception;

}
