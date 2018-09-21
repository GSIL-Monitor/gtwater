package com.gt.manager.merchant.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.gtop.service.dubbo.branches.IBranchesDubboService;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.merchant.repository.WtWaterstoreDAO;
import com.gt.manager.merchant.service.WaterStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fengyueli
 * @date 2018/8/8 19:03
 */
@Service
public class WaterStoreServiceImpl implements WaterStoreService {
    @Autowired
    private WtWaterstoreDAO wtWaterstoreDAO;
    @Reference
    private IBranchesDubboService iBranchesDubboService;
    /**
     * 添加店铺信息
     *
     * @param wtWaterstore
     * @return
     */
    @Override
    public  void addWaterStore(WtWaterstore wtWaterstore) throws Exception{
        Long cityId = null;
        //根据水站编码得到水站所属城市的branchesId
        OpenBranches filialeByBranches = iBranchesDubboService.getFilialeByBranches(wtWaterstore.getOrgId(),wtWaterstore.getBranchesId());
        cityId = filialeByBranches.getId();
        wtWaterstore.setCityBranchesId(cityId);

        WtWaterstore wtWaterstore1 = wtWaterstoreDAO.selectByBranchesId(wtWaterstore.getBranchesId());
        if(wtWaterstore1==null ){
            wtWaterstore.setCreateTime(new Date().getTime());
            wtWaterstore.setWaterstoreNo(String.valueOf(new Date().getTime()));//暂未使用，使用时间戳作啥一标识
            wtWaterstoreDAO.insert(wtWaterstore);
        }else{
            wtWaterstore.setId(wtWaterstore1.getId());
            wtWaterstore.setUpdateTime(new Date().getTime());
            wtWaterstoreDAO.update(wtWaterstore);
        }

    }

    /**
     * 获取水站信息
     * @param branchesId
     * @return
     */
    @Override
    public WtWaterstore getWaterStore(Long branchesId) {
        WtWaterstore wtWaterstore = wtWaterstoreDAO.selectByBranchesId(branchesId);
        return wtWaterstore;
    }


}
