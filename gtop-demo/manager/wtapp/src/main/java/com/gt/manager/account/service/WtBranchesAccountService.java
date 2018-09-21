package com.gt.manager.account.service;


import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;

public interface WtBranchesAccountService {

    public  String addWtBranchesAccountData(String params);

    public DataMessage tixianAccountMoneyToBranches(String params) throws Exception;

    public DataMessage tixianAccountMoneyToWeiXin(JSONObject jsonObject) throws Exception;

    public DataMessage tixianAccountDetailed(JSONObject jsonObject) throws Exception;
}
