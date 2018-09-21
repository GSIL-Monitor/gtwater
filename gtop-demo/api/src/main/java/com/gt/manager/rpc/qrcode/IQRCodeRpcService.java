package com.gt.manager.rpc.qrcode;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Package com.gt.manager.rpc.qrcode
 * @ClassName QRCodeRpcService
 * @Description:
 * @Author towards
 * @Date 2018/8/1 13:32
 */
public interface IQRCodeRpcService {

    /**
     * 展示二维码列表数据
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage displayDataToQrcode(HttpServletRequest request, JSONObject jsonObject) throws Exception;


    /**
     * 生成二维码
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage qrCodeGenerate(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject) throws Exception;


    /**
     * 批量导出二维码生成二维码
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage piLiangqrCodeGenerate(HttpServletRequest request, JSONObject jsonObject) throws Exception;



    /**
     * 批量删除
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage deleteCodeById(HttpServletRequest request, JSONObject jsonObject) throws Exception;


}
