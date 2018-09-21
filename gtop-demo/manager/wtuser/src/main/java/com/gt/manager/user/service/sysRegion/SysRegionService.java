package com.gt.manager.user.service.sysRegion;

import com.gt.manager.DataMessage;
import com.gt.manager.entity.sysRegion.SysRegion;

import java.util.List;
import java.util.Map;

public interface SysRegionService {

    /**
     * 构造省市区控件数据
     * @return
     */
    public Map<String, Object> queryAll();

    public List<SysRegion> queryProvince();

    public List<SysRegion> queryCity(String regionCode);

    public List<SysRegion> queryDistrict(String regionCode);

    public List<SysRegion> queryList(SysRegion sysRegion);

}
