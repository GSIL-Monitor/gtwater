package com.gt.manager.order.repository.wtSend;


import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 派单
 */
@Mapper
public interface WtSend2DAO {


    /**
     * 派单详情
     */
    List<Map<String, Object>> selectList(HashMap <String, Object> map);

    /**
     * 添加派单
     */
    Long insert(WtSend wt);

    /**
     * 添加派单详情
     */
    Long insertMes(WtSendMes wts);

    /**
     * 根据订单编号查询订单信息
     */
    List<HashMap<String, Object>> selectOrderMes(HashMap <String, Object> map);

    /**
     * 插入一键催单
     */
    Long insertUrge(HashMap <String, Object> map);

    /**
     * 通过用户ID查找派单
     */
    List<WtSend> selectBySendUserId(Map<String,Object> map);

    /**
     * 通过派单编号查询派单
     */
    WtSend  cancelSend(String sendNo);

    /**
     * 修改派状态
     */
    Long updateSendStatus(WtSend send);


}