package com.gt.manager.order.service.wtSend;


//import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;

/**
 * 派单
 * @author why
 */
public interface WtSendService {



   /**
	* 查询所有派单
	*/
   List<Map<String,Object>> querySend(JSONObject json);
    /**
     * 派单详情
     */
   List<Map<String,Object>>  querySendMes(JSONObject json);

    /**
     * 添加添加派单 与 派单详情
     */
    Map insert(JSONObject json) throws Exception;
    /**
     * 插入一键催单
     */
    Map insertUrge(JSONObject json) throws Exception;

    Map tuisong(JSONObject json) throws Exception;

    /**
     * 取消派单
     */
    Map cancelSend(JSONObject json);
}