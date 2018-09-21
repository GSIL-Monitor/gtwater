package com.gt.manager.product.repository;


import com.gt.manager.entity.wtadmin.WtBranchesProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.product.repository
 * @ClassName WtBranchesProductDAO
 * @Description:
 * @Author towards
 * @Date 2018/8/1 20:43
 */
@Mapper
public interface WtBranchesProductDAO {
    /**
     * 根据条件查询水管家商品信息
     *
     * @return
     */
    public List<WtBranchesProductEntity> getWtBranchesProdeuctCountNumList(Map<String,Object> map);





}
