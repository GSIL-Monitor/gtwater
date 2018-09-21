package com.gt.manager.qrcode.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.qrcode.service.QrCodeService;
import com.gt.manager.rpc.qrcode.IQRCodeRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Service

public class IQRCodeRpcServiceImpl implements IQRCodeRpcService {

    @Autowired
    private QrCodeService qrCodeService;

    @Override
    public DataMessage displayDataToQrcode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try {
            DataMessage result = this.qrCodeService.displayDataToQrcode(jsonObject);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    @Override
    public DataMessage qrCodeGenerate(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            DataMessage result = qrCodeService.qrCodeGenerate(jsonObject);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    @Override
    public DataMessage piLiangqrCodeGenerate(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            DataMessage result = qrCodeService.piLiangqrCodeGenerate(jsonObject);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }

    @Override
    public DataMessage deleteCodeById(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try{
            if(jsonObject == null){
                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
            }
            DataMessage result = qrCodeService.deleteCodeById(jsonObject);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "系统异常");
        }
    }


}
