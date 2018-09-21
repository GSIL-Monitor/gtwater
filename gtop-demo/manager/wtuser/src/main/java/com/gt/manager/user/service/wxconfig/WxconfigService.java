package com.gt.manager.user.service.wxconfig;

import com.gt.manager.entity.wtWxconfig.WtWxconfig;

public interface WxconfigService {

    public void update(WtWxconfig wtWxconfig);

    public WtWxconfig queryById(Long id);
}
