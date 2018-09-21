package com.gt.manager.order.service.setMeal.impl;


import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.order.repository.setMeal.SetMeal2DAO;
import com.gt.manager.order.service.setMeal.SetMealService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {

    private static Logger logger = Logger.getLogger(SetMealServiceImpl.class);

    @Autowired
    private SetMeal2DAO dao;


    @Override
    public List <HashMap <String, Object>> selectWaterSetMeal(HashMap <String, Object> map) {
        return null;
    }

    @Override
    public List <WtOrgSetmeal> selectSetMeal(WtOrgSetmeal wt) {
        List<WtOrgSetmeal> wtOrgSetmeal = dao.selectSetMeal(wt);
        return wtOrgSetmeal;
    }

}
