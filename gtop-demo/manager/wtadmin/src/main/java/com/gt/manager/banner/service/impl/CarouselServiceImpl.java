package com.gt.manager.banner.service.impl;

import com.gt.manager.banner.repository.WtCarouselDAO;
import com.gt.manager.banner.service.ICarouselService;
import com.gt.manager.entity.wtCarousel.WtCarousel;
import com.gt.util.exception.GtopException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.gt.manager.banner.service.impl
 * @ClassName CarouselServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/13 17:55
 */
@Service
@Transactional
public class CarouselServiceImpl implements ICarouselService {
    private static final Logger log = Logger.getLogger(CarouselServiceImpl.class);

    @Autowired
    WtCarouselDAO wtCarouselDAO;

    /**
     * 获取所有轮播信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<WtCarousel> getCarouselList() throws Exception {
        List<WtCarousel> wtCarousels;
        try {
            wtCarousels =wtCarouselDAO.selectAllList();
            return wtCarousels;
        }catch (Exception e){
            log.error("没有数据",e);
            throw new GtopException("没有轮播图数据");
        }
    }

    /**
     * 更新轮播图
     *
     * @param wtCarousels
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCarousel(List<WtCarousel> wtCarousels,Long loginId) throws Exception {
        Long time=System.currentTimeMillis();
        if (CollectionUtils.isEmpty(wtCarousels)){
            throw new GtopException("缺失相关数据");
        }
        if (loginId==null){
            throw new GtopException("缺失用户数据");
        }
        for (int i = 0; i <wtCarousels.size() ; i++) {
            if (wtCarousels.get(i).getId()==null){
                wtCarousels.get(i).setCreateId(loginId);
                wtCarousels.get(i).setCreateTime(time);
                wtCarouselDAO.insert(wtCarousels.get(i));
            }else {
                wtCarousels.get(i).setUpdateBy(loginId);
                wtCarousels.get(i).setUpdateTime(time);
                wtCarouselDAO.update(wtCarousels.get(i));
            }
        }

    }
}
