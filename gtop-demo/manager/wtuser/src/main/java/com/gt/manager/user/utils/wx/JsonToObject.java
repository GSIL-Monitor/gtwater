package com.gt.manager.user.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.thirdUser.ThirdUser;

public class JsonToObject {
    public static DataMessage json2ThirdUser(JSONObject json){
        DataMessage dm = new DataMessage();
        if(json.containsKey("errcode")){
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage(json.getString("errmsg"));
        }else{

            ThirdUser t = new ThirdUser();


        }
        return dm;
    }
}
