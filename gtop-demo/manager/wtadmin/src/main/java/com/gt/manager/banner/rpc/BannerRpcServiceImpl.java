package com.gt.manager.banner.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.banner.service.ICarouselService;
import com.gt.manager.entity.wtCarousel.WtCarousel;
import com.gt.manager.rpc.banner.IBannerRpcService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Package com.gt.manager.banner.rpc
 * @ClassName BannerRpcServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/13 17:28
 */
@Service
public class BannerRpcServiceImpl implements IBannerRpcService {
    private static final Logger log = Logger.getLogger(BannerRpcServiceImpl.class);

    @Autowired
    ICarouselService carouselService;
    /**
     * 查询所有轮播图
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getCarouselList(HttpServletRequest request, JSONObject jsonObject) throws Exception {

        List<WtCarousel> carouseList = carouselService.getCarouselList();
        log.info("list.size:"+carouseList.size());
        return new DataMessage(DataMessage.RESULT_SUCESS, carouseList, "请求成功");

    }

    /**
     * 更新轮播表
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     * private String accessUrl;// 访问地址
     * 	private String carouselPic
     * 	delState 0--删除；1-- 正常
     */
    @Override
    public DataMessage updateCarousel(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long loginId= jsonObject.getLong("loginId");
        if (loginId==null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        JSONArray json = jsonObject.getJSONArray("wtCarouselList");
        log.info("carouseList:"+json.toJSONString());
        if (json==null||json.size()==0){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<WtCarousel> wtCarouselList = JSONObject.parseArray(json.toJSONString(), WtCarousel.class);
        if (CollectionUtils.isEmpty(wtCarouselList)){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        carouselService.updateCarousel(wtCarouselList,loginId);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }
}
