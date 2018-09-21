package com.gt.manager.user.resource.sysRegion;

import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.user.constants.NameSpaceConstant;
import com.gt.manager.user.service.sysRegion.SysRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SysRegionInit implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(SysRegionInit.class);
    @Autowired
    private SysRegionService sysRegionService;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        NameSpaceConstant.regionList = this.sysRegionService.queryList(new SysRegion());
        logger.info("初始化省市区信息成功{}",NameSpaceConstant.regionList.size());
    }
}
