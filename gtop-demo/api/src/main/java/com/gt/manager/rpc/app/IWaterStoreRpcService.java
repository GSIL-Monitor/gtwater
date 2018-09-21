package com.gt.manager.rpc.app;


import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
//import com.gt.manager.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.app
 * @ClassName IWaterStoreRpcService
 * @Description:  水管家店铺添加接口
 * @Author fengyueli
 * @Date 2018/8/8 18:19
 */
public interface IWaterStoreRpcService {
    /**
     * 根据商家平台机构编号branchesId，维护新增水站app自己的店铺基本信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage addWaterstore(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 获取水站信息
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */

    DataMessage getWaterstore(HttpServletRequest request, JSONObject jsonObject) throws Exception;
}
