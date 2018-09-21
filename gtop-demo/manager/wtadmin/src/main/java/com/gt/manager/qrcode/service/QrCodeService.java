package com.gt.manager.qrcode.service;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

public interface QrCodeService {

    /**
     * 展示二维码列表
     * @return
     * @param jsonObject
     */
    DataMessage displayDataToQrcode(JSONObject jsonObject);

    /**
     * 生成二维码
     * @param jsonObject
     * @return
     */
    public DataMessage qrCodeGenerate(JSONObject jsonObject);

    /**
     * 批量生成二维码
     * @param jsonObject
     * @return
     */
    public DataMessage piLiangqrCodeGenerate(JSONObject jsonObject);

    /**
     * 批量删除二维码数据
     * @param jsonObject
     * @return
     */
    public DataMessage deleteCodeById(JSONObject jsonObject);
}
