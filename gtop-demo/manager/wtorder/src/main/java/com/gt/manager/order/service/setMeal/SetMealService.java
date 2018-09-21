package com.gt.manager.order.service.setMeal;


import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;

import java.util.HashMap;
import java.util.List;

/**
 * 套餐
 * @author why
 */
public interface SetMealService {


    /**
     * 查询水站套餐
     */
    List<HashMap<String,Object>> selectWaterSetMeal(HashMap <String, Object> map);

    /**
     * 查询套餐详情
     */
    List<WtOrgSetmeal> selectSetMeal(WtOrgSetmeal wt);

}