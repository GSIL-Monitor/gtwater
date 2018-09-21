package com.gt.manager.account.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.manager.account.repository.WtBranchesAccountDAO;
import com.gt.manager.account.repository.WtBranchesAccountStatementDAO;
import com.gt.manager.entity.wtBranchesAccount.WtBranchesAccount;
import com.gt.manager.entity.wtBranchesAccountStatement.WtBranchesAccountStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ManagerConsumer {

    private static final Logger log = LoggerFactory.getLogger(ManagerConsumer.class);

    @Autowired
    private WtBranchesAccountStatementDAO wtBranchesAccountStatementDAO;

    @Autowired
    private WtBranchesAccountDAO wtBranchesAccountDAO;

    @Transactional
    @JmsListener(destination = "gtop_branches_to_user_account_back")
    public void toSendSms(String text){
       WtBranchesAccountStatement accountStatement = new WtBranchesAccountStatement();
       WtBranchesAccount account = new WtBranchesAccount();
        log.info("activeMQ提现反馈,"+text);
        if(StringUtils.isEmpty(text)){
            return;
        }
        JSONObject object = JSONObject.parseObject(text);
        try{
            //提现失败
            if(object.containsKey("result") && !"0".equals(object.getString("result"))){
                if(object.containsKey("data") && !StringUtils.isEmpty(object.get("data"))){
                    String data = object.getString("data");
                    JSONObject dataObject = JSONObject.parseObject(data);
                    String extend = dataObject.getString("extend");
                    //查询据路标失败的金额
                    accountStatement = new WtBranchesAccountStatement();
                    accountStatement.setDelState(1);
                    accountStatement.setPayStatus(1);
                    accountStatement.setId(Long.valueOf(extend));
                    List<WtBranchesAccountStatement> statementsDao = wtBranchesAccountStatementDAO.selectList(accountStatement);

                    if(statementsDao != null && !statementsDao.isEmpty()){
                        //得到提现失败的金额和分支机构
                        WtBranchesAccountStatement branchesAccountStatement = statementsDao.get(0);
                        Long money = branchesAccountStatement.getMoney();
                        Long branchesId = branchesAccountStatement.getBranchesId();
                        //更新记录表当前数据为失败
                        accountStatement.setPayStatus(-1);
                        accountStatement.setFailMes(object.getString("message"));
                        accountStatement.setUpdateTime(System.currentTimeMillis());
                        wtBranchesAccountStatementDAO.update(accountStatement);

                        //查询钱包的数据
                        account = new WtBranchesAccount();
                        account.setBranchesId(branchesId);
                        account.setStatus(1);
                        account.setDelState(1);
                        List<WtBranchesAccount> accounts = wtBranchesAccountDAO.selectList(account);
                        if(accounts != null && !accounts.isEmpty()){
                            //回滚钱包金额
                            WtBranchesAccount wtBranchesAccount = accounts.get(0);
                            String moneyNew = new BigDecimal(wtBranchesAccount.getAccount()).add(new BigDecimal(money)).toString();
                            wtBranchesAccount.setAccount(Long.valueOf(moneyNew));
                            wtBranchesAccount.setUpdateTime(System.currentTimeMillis());
                            wtBranchesAccountDAO.update(wtBranchesAccount);
                        }
                    }
                }
            }else{
                //提现到个人账户成功后，更新到账/打款时间
                accountStatement = new WtBranchesAccountStatement();
                accountStatement.setPayTime(System.currentTimeMillis());
                if(object.containsKey("data") && !StringUtils.isEmpty(object.get("data"))) {
                    String data = object.getString("data");
                    JSONObject dataObject = JSONObject.parseObject(data);
                    String extend = dataObject.getString("extend");
                    accountStatement.setId(Long.valueOf(extend));
                    wtBranchesAccountStatementDAO.update(accountStatement);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("activeMQ监听提现反馈接口异常"+e.getCause().getMessage());
        }

    }


   /* @JmsListener(destination = "gtop_express_sms_back")
    public void getSmsBack(String text){
        log.info("获取短信回推信息。。。。");
        log.info(text);
    }*/

}
