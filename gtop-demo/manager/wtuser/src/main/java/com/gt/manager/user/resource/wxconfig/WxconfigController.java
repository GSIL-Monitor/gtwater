package com.gt.manager.user.resource.wxconfig;

import com.gt.manager.entity.wtWxconfig.WtWxconfig;
import com.gt.manager.user.entity.wx.AccessToken;
import com.gt.manager.user.service.wxconfig.WxconfigService;
import com.gt.manager.user.utils.wx.UpdateOldToken;
import com.gt.manager.user.utils.wx.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时获取token,jsTicket
 */
@Component
@Order(1)
public class WxconfigController implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(WxconfigController.class);
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    @Autowired
    private WxconfigService wxconfigService;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        while(true){
            try {
                AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
                if(null != at){
                    String jsTicket = WeixinUtil.getJsapiTicket(at.getToken());
                    WtWxconfig edit = new WtWxconfig();
                    edit.setId(1L);
                    edit.setAccessToken(at.getToken());
                    edit.setApiTicket(jsTicket);
                    edit.setUpdateTime(System.currentTimeMillis());
                    this.wxconfigService.update(edit);
                    logger.info("appId{}", appId);
                    logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"获取token={},jsTicket={}",at.getToken(),jsTicket);
                    //需改旧系统token应为新旧系统并行，会出现token失效，所以停止旧系统获取token的定时任务只开新系统的定时任务
                    //只有 appid = wx5fe899e6a4ffb49d的时候修改，其余不修改
                    if("wx5fe899e6a4ffb49d".equals(appId)){
                        UpdateOldToken.update(at.getToken());
                    }
                    Thread.sleep((at.getExpiresIn()-200)*1000);
                }else{
                    Thread.sleep(60*1000);
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
                logger.error("获取token异常",ex);
                try {
                    Thread.sleep(60*1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
