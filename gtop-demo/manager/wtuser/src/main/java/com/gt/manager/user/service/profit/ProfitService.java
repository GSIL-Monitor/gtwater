package com.gt.manager.user.service.profit;

import com.gt.manager.entity.wtProfit.WtProfit;
import com.gt.manager.entity.wtProfitPartner.WtProfitPartner;
import com.gt.manager.user.entity.profit.ProfitDetail;

import java.util.List;

public interface ProfitService {
    public List<ProfitDetail> queryList(String goodsName, Long startTime, Long endTime, int pageNo, int pageSize, Long partnerId);
}
