package com.gt.manager.user.service.wxconfig;

import com.gt.manager.entity.wtWxconfig.WtWxconfig;
import com.gt.manager.user.repository.wtWxconfig.WtWxconfigDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxconfigService")
public class WxconfigServiceImpl implements WxconfigService {
    @Autowired
    private WtWxconfigDAO wtWxconfigDAO;
    @Override
    public void update(WtWxconfig wtWxconfig) {
        wtWxconfig.setUpdateTime(System.currentTimeMillis());
        this.wtWxconfigDAO.update(wtWxconfig);
    }

    @Override
    public WtWxconfig queryById(Long id) {
        return this.wtWxconfigDAO.selectById(id);
    }
}
