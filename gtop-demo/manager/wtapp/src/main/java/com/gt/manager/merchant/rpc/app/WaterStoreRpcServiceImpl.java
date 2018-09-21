package com.gt.manager.merchant.rpc.app;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.gt.manager.DataMessage;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.merchant.service.WaterStoreService;
import com.gt.manager.rpc.app.IWaterStoreRpcService;
import com.gt.util.exception.GtopException;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * 水管家店铺信息设置
 * @author fengyueli
 * @date 2018/8/8 18:58
 */
@Service
public class WaterStoreRpcServiceImpl implements IWaterStoreRpcService {
    @Autowired
    private WaterStoreService waterStoreService;

    /**
     * 根据商家平台机构编号branchesId，维护新增水站app自己的店铺基本信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage addWaterstore(HttpServletRequest request, JSONObject jsonObject) throws Exception {

        Long branchesId = jsonObject.getLong("branchesId");
        Long orgId = jsonObject.getLong("orgId");
        //如果是修改的话传此值
        Long id = jsonObject.getLong("id");
        if(branchesId==null){
            throw new GtopException("城市机构编码id不能为空");
        }
        if(orgId==null){
            throw new GtopException("组织id不能为空");
        }

        String showName = jsonObject.getString("showName");
        String waterName = jsonObject.getString("waterName");
        //更新运营时间周 【0,0,0,0,0,0,0】7位字符串，从周一至周日每一位代表一天，1代表上班，0代表休息，以英文逗号分隔
        String operateWeek = jsonObject.getString("operateWeek");

        String operateStartTime = jsonObject.getString("operateStartTime");
        String operateEndTime=jsonObject.getString("operateEndTime");
        String sendTime=jsonObject.getString("sendTime");

        String tel=jsonObject.getString("tel");
        String linkPhone=jsonObject.getString("linkPhone");


        WtWaterstore wtWaterstore=new WtWaterstore();
        wtWaterstore.setId(id);
        wtWaterstore.setBranchesId(branchesId);
        wtWaterstore.setOrgId(orgId);

        wtWaterstore.setShowName(showName);
        wtWaterstore.setWaterName(waterName);
        wtWaterstore.setOperateWeek(operateWeek);
        wtWaterstore.setOperateStartTime(operateStartTime);
        wtWaterstore.setOperateEndTime(operateEndTime);
        wtWaterstore.setSendTime(sendTime);
        wtWaterstore.setTel(tel);
        wtWaterstore.setLinkPhone(linkPhone);

        synchronized(String.valueOf(branchesId).intern()) {
            waterStoreService.addWaterStore(wtWaterstore);
        }
        return new DataMessage(DataMessage.RESULT_SUCESS, "操作成功", "操作成功");

    }

    public static void main(String[] args) {
        String time="8:10:00";
        Time time1 = Time.valueOf(time);
        System.out.println(time1);


    }


    /**
     * 根据商家平台机构编号branchesId，获得店铺基本信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWaterstore(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        try{
            Long branchesId = jsonObject.getLong("branchesId");
            //Long orgId = jsonObject.getLong("orgId");


            WtWaterstore waterStore = waterStoreService.getWaterStore(branchesId);
            return new DataMessage(DataMessage.RESULT_SUCESS, waterStore, "查询成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new DataMessage(DataMessage.RESULT_FAILED, e.getMessage(), "查询失败");
        }
    }


}
