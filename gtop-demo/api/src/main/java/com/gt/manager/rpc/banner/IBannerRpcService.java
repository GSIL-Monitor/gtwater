package com.gt.manager.rpc.banner;

import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.gt.manager.rpc.banner
 * @ClassName IBannerRpcService
 * @Description:
 * @Author towards
 * @Date 2018/8/13 17:04
 */
public interface IBannerRpcService {
    /**
     * 查询所有轮播图
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage getCarouselList(HttpServletRequest request, JSONObject jsonObject) throws Exception;

    /**
     * 更新轮播表
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    DataMessage updateCarousel(HttpServletRequest request, JSONObject jsonObject) throws Exception;


}
