package com.gt.manager.user.service.profit;

import com.github.pagehelper.PageHelper;
import com.gt.manager.entity.wtProfit.WtProfit;
import com.gt.manager.user.entity.profit.ProfitDetail;
import com.gt.manager.user.repository.wtProfit.WtProfitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("profitService")
public class ProfitServiceImpl implements ProfitService {

    @Autowired
    private WtProfitDAO wtProfitDAO;

    @Override
    public List<ProfitDetail> queryList(String goodsName, Long startTime, Long endTime, int pageNo, int pageSize, Long partnerId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("goodsName", goodsName);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("partnerId", partnerId);
        pageNo = pageNo<1?1:++pageNo;
        PageHelper.startPage(pageNo, pageSize);
        List<Map> profitDetails = this.wtProfitDAO.selectByCondition(params);
        List<ProfitDetail> list = new ArrayList<>();
        if(null != profitDetails && !profitDetails.isEmpty()){
            profitDetails.forEach(item ->{
                list.add(mapToObject(item));
            });
        }
        return list;
    }

    public static ProfitDetail mapToObject(Map m){
        ProfitDetail p = new ProfitDetail();
        p.setSendNo(m.get("sendNo")+"");
        p.setGoodsName(m.get("goodsName")+"");
        p.setArriveTime(Long.parseLong(m.get("arriveTime")+""));
        p.setArriveNum(Integer.parseInt(m.get("arriveNum")+""));
        p.setMoney(Long.parseLong(m.get("money")+""));
        p.setProportion(Integer.parseInt(m.get("proportion")+""));
        p.setSendMoney(Long.parseLong(m.get("sendMoney")+""));
        return p;
    }

}
