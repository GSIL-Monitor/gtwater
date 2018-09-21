package com.gt.manager.merchant.service;

import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;

import java.util.List;
import java.util.Map;

/**
 * @author fengyueli
 * @date 2018/8/8 18:59
 */
public interface WaterStoreService {
    /**
     * 添加店铺信息
     * @param wtWaterstore
     * @return
     */
    void addWaterStore(WtWaterstore wtWaterstore) throws Exception;
    /**
     * 获取店铺信息
     * @param branchesId
     * @return
     */
    WtWaterstore getWaterStore(Long branchesId);
}
