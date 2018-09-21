package com.gt.manager.merchant.service;

import com.github.pagehelper.PageInfo;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.merchant.entity.response.WtSendResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.merchant.service
 * @ClassName WtSendService
 *  商家派件管理服务
 * @author fengyueli
 * @date 2018/8/3 13:54
 */
public interface WtSendService {
    /**
     * 分页查询，商家所有派单（branchesId状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】）
     * @param pageNo
     * @param pageSize
     * @param branchesId    商家branchesId,status
     * @return
     */
    PageInfo<WtSendResponse> getSendPage(Integer pageNo, Integer pageSize, Long branchesId, Integer status);

    /**
     * 派送单详情
     * @param sendCode    派单编号send_code
     * @return
     */
    WtSendResponse getSendDetailByBranchesId(String sendCode);

    /**
     * 接单
     * @param sendCode    派单编号send_code
     * @return
     */
    void orderReceiving(String sendCode);

    /**
     * 确认收货
     * @param sendCode    派单编号send_code,各个详情的编号及对应的实际送达数量
     * @return
     */




    void confirmDelivery(Long orgId, Long branchesId, String sendCode, Long loginId, Map<String, Object> realMes) throws Exception;

    /**
     * 返回派单列表的各状态的数量
     * @return
     */
    Map<String,Integer> sendStatusNum(Long branchesId);
}
