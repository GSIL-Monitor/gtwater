package com.gt.manager.function.service.impl;

import com.gt.manager.entity.wtFunction.WtFunction;
import com.gt.manager.function.repository.WtFunctionDAO;
import com.gt.manager.function.service.IFunctionService;
import com.gt.util.exception.GtopException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.gt.manager.function.service.impl
 * @ClassName FunctionServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/13 20:20
 */
@Service

public class FunctionServiceImpl implements IFunctionService {
    private static final Logger log = Logger.getLogger(FunctionServiceImpl.class);


    @Autowired
    WtFunctionDAO wtFunctionDAO;

    /**
     * 更新发票状态
     *
     * @param id
     * @param state
     * @param loginId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFunctionStata(Long id, Integer state, Long loginId) throws Exception {


        if (id == null) {
            log.info("id is null");
            throw new GtopException("缺少参数");
        }
        if (state == null) {
            log.info("state is null");
            throw new GtopException("缺少参数");
        }
        if (loginId == null) {
            log.info("loginId is null");
            throw new GtopException("缺少参数");
        }
        Long updateTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("state", state);
        map.put("updateId", loginId);
        map.put("updateTime", updateTime);
        log.info("map:" + map.toString());
        wtFunctionDAO.updateFunctionStata(map);

    }

    /**
     * 查询所有function信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<WtFunction> getFunctionlist() throws Exception {
        try {
            return wtFunctionDAO.getFunctionlist();

        } catch (Exception e) {
            log.error("没有查询到开关信息", e);
            throw new GtopException("没有查询到信息");
        }
    }
}
