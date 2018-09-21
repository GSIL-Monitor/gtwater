package com.gt.manager.order.repository.setMeal;


import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 套餐
 * @author why
 */
@Mapper
public interface SetMeal2DAO {

   /**
	* 查询水站套餐
	*/
   List<HashMap<String,Object>> selectWaterSetMeal(HashMap <String, Object> map);

    /**
     * 查询套餐详情
     */
    List<WtOrgSetmeal> selectSetMeal(WtOrgSetmeal wt);


}