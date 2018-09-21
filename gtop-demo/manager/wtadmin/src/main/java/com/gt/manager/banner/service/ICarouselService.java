package com.gt.manager.banner.service;

import com.gt.manager.entity.wtCarousel.WtCarousel;

import java.util.List;

/**
 * @Package com.gt.manager.banner.service
 * @ClassName ICarouselService
 * @Description:
 * @Author towards
 * @Date 2018/8/13 17:55
 */
public interface ICarouselService {

    /**
     * 获取所有轮播信息
     * @return
     * @throws Exception
     */
    public List<WtCarousel> getCarouselList()throws Exception;

    /**
     * 更新轮播图
     * @param wtCarousels
     * @throws Exception
     */
    public void updateCarousel(List<WtCarousel> wtCarousels,Long loginId)throws  Exception;
}
