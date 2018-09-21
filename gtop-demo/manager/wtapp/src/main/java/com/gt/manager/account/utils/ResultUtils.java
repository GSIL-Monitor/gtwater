package com.gt.manager.account.utils;

import com.alibaba.fastjson.JSONObject;

public class ResultUtils {

    public static JSONObject getErrorJson(String message, String remark) {
        JSONObject jobj = new JSONObject();
        jobj.put("result", "false");
        jobj.put("message", message);
        jobj.put("remark", remark);
        return jobj;
    }

    public static JSONObject getSuccessJson(String message, String remark) {
        JSONObject jobj = new JSONObject();
        jobj.put("result", "true");
        jobj.put("message", message);
        jobj.put("remark", remark);
        return jobj;
    }

    public static JSONObject getSuccessObj(String message, Object data){
        JSONObject jobj = new JSONObject();
        jobj.put("result", "true");
        jobj.put("message", message);
        jobj.put("data", data);
        return jobj;
    }
}
