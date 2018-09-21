package com.gt.manager.user.service.sysRegion;

import com.gt.manager.DataMessage;
import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.user.entity.area.Area;
import com.gt.manager.user.entity.area.City;
import com.gt.manager.user.entity.area.Province;
import com.gt.manager.user.repository.sysRegion.SysRegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sysRegionService")
public class SysRegionServiceImpl implements SysRegionService {

    @Autowired
    private SysRegionDAO sysRegionDAO;

    /**
     * 构造省市区选择控件数据
     * @return
     */
    @Override
    public Map<String, Object> queryAll() {
        Map<String, Object> result_map = new HashMap<String, Object>();
        List<SysRegion> all = this.sysRegionDAO.selectList(new SysRegion());
        //存放所有省
        List<Province> p = new ArrayList<Province>();
        //存放所有市
        Map<String, List<City>> city_map = new LinkedHashMap<String, List<City>>();
        //存放所有区
        Map<String, List<Area>> area_map = new LinkedHashMap<String, List<Area>>();
        if(null != all && !all.isEmpty()) {
            Province province = null;
            City city = null;
            Area area = null;
            //获取省
            for (SysRegion item : all) {
                //id长度4 省
                if (item.getRegionId().length() == 4) {
                    province = new Province(item.getRegionName(), item.getRegionId());
                    p.add(province);
                }
            }
            //获取省市关联
            for (SysRegion item : all) {
                if (null != p && !p.isEmpty()) {
                    //id长度6市
                    if (item.getRegionId().length() == 6) {
                        for (Province item_p : p) {
                            if (item_p.getId().equals(item.getRegionId().substring(0, 4))) {
                                city = new City(item.getRegionName(), item.getRegionId(), item_p.getName());
                                List<City> c = new ArrayList<City>();
                                if (city_map.containsKey(item_p.getId())) {
                                    c = city_map.get(item_p.getId());
                                    c.add(city);
                                } else {
                                    c.add(city);
                                }
                                city_map.put(item_p.getId(), c);
                            }
                        }
                    }

                }
            }
            //获取市区关联
            for (SysRegion item : all) {
                //id长度8区
                if (item.getRegionId().length() == 8) {
                    if (!city_map.isEmpty()) {
                        for (Map.Entry<String, List<City>> ent : city_map.entrySet()) {
                            List<City> city_list = ent.getValue();
                            if (null != city_list && !city_list.isEmpty()) {
                                for (City item_c : city_list) {
                                    if (item_c.getId().equals(item.getRegionId().substring(0, 6))) {
                                        area = new Area(item.getRegionName(), item.getRegionId(), item_c.getName());
                                        List<Area> a = new ArrayList<Area>();
                                        if (area_map.containsKey(item_c.getId())) {
                                            a = area_map.get(item_c.getId());
                                            a.add(area);
                                        } else {
                                            a.add(area);
                                        }
                                        area_map.put(item_c.getId(), a);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            result_map.put("provinces", p);
            result_map.put("citys", city_map);
            result_map.put("areas", area_map);
        }
        return result_map;
    }

    @Override
    public List<SysRegion> queryProvince() {
        return this.sysRegionDAO.selectProvince();
    }

    @Override
    public List<SysRegion> queryCity(String regionCode) {
        SysRegion query = new SysRegion();
        query.setRegionCode(regionCode);
        return this.sysRegionDAO.selectList(query);
    }

    @Override
    public List<SysRegion> queryDistrict(String regionCode) {
        SysRegion query = new SysRegion();
        query.setRegionCode(regionCode);
        return this.sysRegionDAO.selectList(query);
    }

    @Override
    public List<SysRegion> queryList(SysRegion sysRegion) {
        return this.sysRegionDAO.selectList(sysRegion);
    }
}
