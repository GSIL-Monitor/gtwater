package com.gt.manager.function.service;

import com.gt.manager.entity.wtFunction.WtFunction;

import java.util.List;

/**
 * @Package com.gt.manager.function.service
 * @ClassName IFunctionService
 * @Description:
 * @Author towards
 * @Date 2018/8/13 20:09
 */
public interface IFunctionService {
    /**
     * 更新发票状态
     * @param
     * @param stata
     * @param loginId
     * @throws Exception
     */
    public void updateFunctionStata(Long id,Integer state,Long loginId)throws Exception;

    /**
     * 查询所有function信息
     * @return
     * @throws Exception
     */
    public List<WtFunction> getFunctionlist()throws Exception;

}
