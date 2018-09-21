package com.gt.manager.account.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.gt.gtop.entity.sys.branches.OpenBranchesAccountStatement;
import com.gt.gtop.service.dubbo.user.IUserAccountDoubleAPIService;
import com.gt.gtpay.entity.account.Account;
import com.gt.gtpay.entity.payout.Payout;
import com.gt.gtpay.entity.payout.PayoutOrder;
import com.gt.gtpay.service.GtPayService;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.manager.account.message.ManagerProducer;

import com.gt.manager.account.repository.ThirdUserDAO;
import com.gt.manager.account.repository.WtBranchesAccountDAO;
import com.gt.manager.account.repository.WtBranchesAccountStatementDAO;
import com.gt.manager.account.service.WtBranchesAccountService;
import com.gt.manager.account.utils.ResultUtils;
import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.entity.wtBranchesAccount.WtBranchesAccount;
import com.gt.manager.entity.wtBranchesAccountStatement.WtBranchesAccountStatement;
import com.gt.util.exception.GtopException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service("wtBranchesAccountService")
@Component
public class WtBranchesAccountServiceImpl  implements WtBranchesAccountService {

    @Autowired
    private WtBranchesAccountDAO wtBranchesAccountDAO;

    @Autowired
    private WtBranchesAccountStatementDAO wtBranchesAccountStatementDAO;

    @Reference
    private GtPayService gtPayService;

    @Autowired
    private ManagerProducer managerProducer;

    @Autowired
    private ThirdUserDAO thirdUserDAO;

    @Reference
    private IUserAccountDoubleAPIService userAccountDoubleAPIService;

    @Value("${webchat.tixian.account}")
    private String tixianAccount;

    @Value("${webchat.tixian.Password}")
    private String tixianPassword;

    /**
     * 增加or修改账户钱包的操作(分佣之后的数据)  ------ 相应的增加or修改账户操作记录
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public String addWtBranchesAccountData(String params) {
        WtBranchesAccount account = new WtBranchesAccount();
        WtBranchesAccountStatement accountStatement = new WtBranchesAccountStatement();
        List<WtBranchesAccount> wtBranchesAccountsList = new ArrayList<>();
        try{
            if(StringUtils.isNotBlank(params)){
                JSONArray array = JSONArray.parseArray(params);
                    for(int i = 0; i < array.size(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        //先查询是否存在这个数据
                        //userType 用户类型[1平台、2城市机构、3水站、4合伙人]
                        if(jsonObject.containsKey("userType") && !StringUtils.isEmpty(jsonObject.getString("userType")) && jsonObject.containsKey("onlyId") && !StringUtils.isEmpty(jsonObject.getString("onlyId"))){
                            accountStatement = new WtBranchesAccountStatement();
                            account = new WtBranchesAccount();
                            account.setDelState(1);
                            if("1".equals(jsonObject.getString("userType"))
                                    || "2".equals(jsonObject.getString("userType"))
                                    || "3".equals(jsonObject.getString("userType"))){
                                account.setBranchesId(jsonObject.getLong("onlyId"));
                                accountStatement.setBranchesId(jsonObject.getLong("onlyId"));
                                wtBranchesAccountsList = wtBranchesAccountDAO.selectList(account);
                            }else if("4".equals(jsonObject.getString("userType"))){
                                account.setUserId(jsonObject.getLong("onlyId"));
                                accountStatement.setUserId(jsonObject.getLong("onlyId"));
                                wtBranchesAccountsList = wtBranchesAccountDAO.selectList(account);
                            }
                            String money;
                            if(!jsonObject.containsKey("userId") || StringUtils.isEmpty(jsonObject.getString("userId"))){
                                return ResultUtils.getErrorJson("参数为空,userId操作用户为空","参数为空,userId操作用户为空").toJSONString();
                            }
                            if(!jsonObject.containsKey("money") || StringUtils.isEmpty(jsonObject.getString("money"))){
                                return ResultUtils.getErrorJson("参数为空,money账户金额为空","参数为空,money账户金额为空").toJSONString();
                            }
                            if(wtBranchesAccountsList != null && !wtBranchesAccountsList.isEmpty()) {
                                WtBranchesAccount lodWtBranchesAccount = wtBranchesAccountsList.get(0);
                                money = new BigDecimal(lodWtBranchesAccount.getAccount()).add(new BigDecimal(jsonObject.getLongValue("money"))).toString();
                                lodWtBranchesAccount.setUpdateId(jsonObject.getLongValue("userId"));
                                lodWtBranchesAccount.setUpdateTime(System.currentTimeMillis());
                                lodWtBranchesAccount.setAccount(Long.valueOf(money));
                                wtBranchesAccountDAO.update(lodWtBranchesAccount);
                                accountStatement.setBranchesAccountId(lodWtBranchesAccount.getId());
                            }else{
                                //添加
                                account.setUserType(jsonObject.getInteger("userType"));
                                account.setAccount(jsonObject.getLongValue("money"));
                                account.setStatus(1);
                                account.setCreateId(jsonObject.getLongValue("userId"));
                                account.setCreateTime(System.currentTimeMillis());
                                account.setDelState(1);
                                wtBranchesAccountDAO.insertSelective(account);
                                accountStatement.setBranchesAccountId(account.getId());
                            }
                            //添加记录表
                            if(jsonObject.containsKey("type") && !StringUtils.isEmpty(jsonObject.getString("type"))){
                                accountStatement.setType(jsonObject.getIntValue("type"));
                            }else{
                                return ResultUtils.getErrorJson("参数为空,type用户操作类型为空","参数为空,type用户操作类型为空").toJSONString();
                            }
                            if(jsonObject.containsKey("transactionNumber") && !StringUtils.isEmpty(jsonObject.getString("transactionNumber"))){
                                accountStatement.setTransactionNumber(jsonObject.getString("transactionNumber"));
                            }
                            if(jsonObject.containsKey("failMes") && !StringUtils.isEmpty(jsonObject.getString("failMes"))){
                                accountStatement.setFailMes(jsonObject.getString("failMes"));
                            }
                            accountStatement.setPayStatus(1);
                            accountStatement.setPayTime(System.currentTimeMillis());
                            accountStatement.setUserType(jsonObject.getIntValue("userType"));
                            accountStatement.setMoney(jsonObject.getLongValue("money"));
                            accountStatement.setCreateId(jsonObject.getLongValue("userId"));
                            accountStatement.setCreateTime(System.currentTimeMillis());
                            accountStatement.setDelState(1);
                            wtBranchesAccountStatementDAO.insertSelective(accountStatement);
                        }else{
                            return ResultUtils.getErrorJson("参数为空,userType或onlyId为空","参数为空,userType或onlyId为空").toJSONString();
                        }
                    }
                    return ResultUtils.getSuccessJson("账户更新成功", "账户,账户操作记录更新成功").toJSONString();
                }else{
                    return ResultUtils.getErrorJson("参数为空,请检查参数","参数为空,请检查参数").toJSONString();
                }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.getErrorJson("账户信息添加异常","账户信息添异常，" + e.getCause().getMessage()).toJSONString();
        }
    }


    @Transactional
    public DataMessage tixianAccountMoneyToData(String params) throws Exception {
        WtBranchesAccount account = new WtBranchesAccount();
        WtBranchesAccountStatement accountStatement = new WtBranchesAccountStatement();
        List<WtBranchesAccount> accountsList = new ArrayList<>();
//        try {
            if (!StringUtils.isEmpty(params)) {
                JSONObject object = JSONObject.parseObject(params);
                if (!object.containsKey("userId") || StringUtils.isEmpty(object.getString("userId"))) {
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,userId为空");
                }
                if(!object.containsKey("password") || StringUtils.isEmpty(object.getString("password"))){
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,password为空");
                }
                //验证密码
                Boolean checkResult = userAccountDoubleAPIService.checkUserAccountPassword(object.getLongValue("userId"), object.getString("password"));
                if(!checkResult){
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "验证码错误");
                }
                //userType 用户类型[1平台、2城市机构、3水站、4合伙人]
                if (object.containsKey("userType") && !StringUtils.isEmpty(object.getString("userType")) && object.containsKey("onlyId") && !StringUtils.isEmpty(object.getString("onlyId"))) {
                    if (!object.containsKey("money") || StringUtils.isEmpty(object.getString("money"))) {
                        return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,提现金额money为空");
                    }
                    if ("1".equals(object.getString("userType")) || "2".equals(object.getString("userType")) || "3".equals(object.getString("userType"))) {
                        //查询当前业务站点的账户信息
                        account.setBranchesId(object.getLongValue("onlyId"));
                        account.setStatus(1);
                        account.setDelState(1);
                        account.setUserType(object.getIntValue("userType"));
                        accountsList = wtBranchesAccountDAO.selectList(account);

                        if (accountsList != null && accountsList.size() > 0) {
                            //判断当前账户余额是否支持提现到个人帐户
                            WtBranchesAccount wtBranchesAccount = accountsList.get(0);
                            if (wtBranchesAccount.getAccount() < object.getLongValue("money")) {
                                return new DataMessage(DataMessage.RESULT_FAILED, null, "提现金额大于当前账户余额");
                            }

                            //更新钱包信息
                            String moneyNew = new BigDecimal(wtBranchesAccount.getAccount()).subtract(new BigDecimal(object.getString("money"))).toString();
                            wtBranchesAccount.setAccount(Long.valueOf(moneyNew));
                            wtBranchesAccount.setUpdateTime(System.currentTimeMillis());
                            wtBranchesAccount.setUpdateId(object.getLongValue("userId"));
                            wtBranchesAccountDAO.update(wtBranchesAccount);

                            //添加记录
                            if (object.containsKey("type") && !StringUtils.isEmpty(object.getString("type"))) {
                                accountStatement.setType(object.getIntValue("type"));
                            } else {
                                return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,操作类型type为空");
                            }
                            //水站到个人没有交易订单号
                           /* if(object.containsKey("transactionNumber") && !StringUtils.isEmpty(object.getString("transactionNumber"))){
                                accountStatement.setTransactionNumber(object.getString("transactionNumber"));
                            }*/
                            accountStatement.setBranchesAccountId(wtBranchesAccount.getId());
                            accountStatement.setUserType(object.getIntValue("userType"));
                            accountStatement.setPayStatus(1);
                            accountStatement.setDelState(1);
                            accountStatement.setCreateId(object.getLongValue("userId"));
                            accountStatement.setCreateTime(System.currentTimeMillis());
                            accountStatement.setUpdateId(object.getLongValue("userId"));
                            accountStatement.setMoney(object.getLongValue("money"));
                            accountStatement.setBranchesId(object.getLongValue("onlyId"));
                            wtBranchesAccountStatementDAO.insertSelective(accountStatement);
                            return new DataMessage(DataMessage.RESULT_SUCESS, accountStatement.getId(), "成功申请提现到个人帐户");
                        } else {
                            return new DataMessage(DataMessage.RESULT_FAILED, null, "不存在该账户");
                        }
                    }
                }else{
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "用户类型或账户唯一识别为空");
                }
            }

        return null;
    }


    /**
     * 体现操作，对不同的账户进行体现的操作
     * @param params
     * @return
     */
    @Override
    public DataMessage tixianAccountMoneyToBranches(String params) throws Exception {
        DataMessage dataMessage = null;
//        try {
            dataMessage = tixianAccountMoneyToData(params);
            JSONObject object = null;
            if (!StringUtil.isEmpty(params)) {
                object = JSONObject.parseObject(params);
            }
            if(dataMessage != null && !StringUtils.isEmpty(dataMessage.toString()) && !StringUtils.isEmpty(dataMessage.getResult()+"")){
                if("0".equals(String.valueOf(dataMessage.getResult()))){
                    /**
                     * 水站提现到个人钱包的调用操作
                     */
                    JSONObject messageObject = new JSONObject();
                    messageObject.put("branchesId", object.getLongValue("onlyId"));
                    messageObject.put("extend", dataMessage.getData());
                    messageObject.put("money", object.getLongValue("money"));
                    managerProducer.send("gtop_branches_to_user_account_send", messageObject.toJSONString());
                    return new DataMessage(DataMessage.RESULT_SUCESS, null, "成功申请提现到个人帐户");
                }else{
                    return dataMessage;
                }
            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return new DataMessage(DataMessage.RESULT_FAILED, e.toString(), e.toString().substring(e.toString().indexOf(":")+1));
//        }
        return null;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public DataMessage tixianAccountMoneyToWeiXin(JSONObject jsonObject) throws Exception {
        WtBranchesAccount account = new WtBranchesAccount();
        WtBranchesAccountStatement accountStatement = new WtBranchesAccountStatement();
        if(jsonObject == null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空");
        }
       // try{
            if (jsonObject.containsKey("userType") && !StringUtils.isEmpty(jsonObject.getString("userType")) && jsonObject.containsKey("onlyId") && !StringUtils.isEmpty(jsonObject.getString("onlyId"))) {
                if (!jsonObject.containsKey("userId") || StringUtils.isEmpty(jsonObject.getString("userId"))) {
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,userId为空");
                }
                if (!jsonObject.containsKey("money") || StringUtils.isEmpty(jsonObject.getString("money"))) {
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,提现金额money为空");
                }else{
                    if (jsonObject.getLongValue("money") < 100L) {
                        return new DataMessage(DataMessage.RESULT_FAILED, null, "转账系统不支持小于一元转账");
                    }
                }
                if (!jsonObject.containsKey("type") || StringUtils.isEmpty(jsonObject.getString("type"))) {
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,操作类型type为空");
                }
                if (!jsonObject.containsKey("transactionNumber") || StringUtils.isEmpty(jsonObject.getString("transactionNumber"))) {
                    return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,交易订单号为空");
                }
                if("4".equals(jsonObject.getString("userType"))){
                    //先减去账户的金额
                    //
                    account = new WtBranchesAccount();
                    account.setUserId(jsonObject.getLongValue("onlyId"));
                    account.setDelState(1);
                    account.setUserType(jsonObject.getIntValue("userType"));
                    account.setStatus(1);
                    List<WtBranchesAccount> accountList = wtBranchesAccountDAO.selectList(account);
                    if(accountList != null && !accountList.isEmpty()){
                        WtBranchesAccount wtBranchesAccount = accountList.get(0);
                        Long money = wtBranchesAccount.getAccount();
                        if(jsonObject.getLongValue("money") > money){
                            return new DataMessage(DataMessage.RESULT_FAILED, null, "提现金额大于您现在账户的现有金额,请重新操作");
                        }
                        String moneyNew = new BigDecimal(money).subtract(new BigDecimal(jsonObject.getString("money"))).toString();
                        wtBranchesAccount.setAccount(Long.valueOf(moneyNew));
                        wtBranchesAccount.setUpdateId(jsonObject.getLongValue("userId"));
                        wtBranchesAccount.setUpdateTime(System.currentTimeMillis());

                        //合伙人提现到微信
                        Account accountGtop = new Account();
                        accountGtop.setAccount(tixianAccount);
                        accountGtop.setSecret(tixianPassword);

                        Payout payoutGtop = new Payout();
                        payoutGtop.setSource(OpenBranchesAccountStatement.Source.WX.value());
                        payoutGtop.setPurposeType(1);// '1:转出 2:退款',
                        payoutGtop.setMoney(jsonObject.getLongValue("money"));

                        PayoutOrder payoutOrderGtop = new PayoutOrder();
                        payoutOrderGtop.setPayOutType(1);// 1:零钱/余额 2:银行卡
                        payoutOrderGtop.setPurposeType(1);// '1:转出 2:退款',
                        payoutOrderGtop.setMoney(jsonObject.getLongValue("money"));
                        //查询用户绑定的微信OpenId
                        ThirdUser thirdUser = new ThirdUser();
                        thirdUser.setThirdType(1);
                        thirdUser.setAppCode("2");
                        thirdUser.setUserId(jsonObject.getLongValue("userId"));
                        List<ThirdUser> thirdUsers = thirdUserDAO.selectList(thirdUser);
                        if(thirdUsers != null && !thirdUsers.isEmpty()){
                            payoutOrderGtop.setOpenId(thirdUsers.get(0).getOpenId());
                        }else{
                            return new DataMessage(DataMessage.RESULT_FAILED, null, "未绑定微信无法操作打款");
                        }
                        payoutOrderGtop.setDescription("个人账户提现");
                        // 转账系统不支持小于一元转账
                        com.gt.gtpay.entity.base.DataMessage dm = gtPayService.payout(accountGtop, payoutGtop, payoutOrderGtop);

                        //立即返回结果
                        System.out.println("返回结果："+ dm.getResult()+" "+dm.getMessage()+"------------------");
                        if("0".equals(String.valueOf(dm.getResult()))){
                            wtBranchesAccountDAO.update(wtBranchesAccount);
                            //成功后添加操作成功的记录
                            //失败回滚，并添加失败记录
                            accountStatement = new WtBranchesAccountStatement();
                            accountStatement.setUserType(jsonObject.getIntValue("userType"));
                            accountStatement.setUserId(jsonObject.getLongValue("onlyId"));
                            accountStatement.setMoney(jsonObject.getLongValue("money"));
                            accountStatement.setPayStatus(1);
                            accountStatement.setPayTime(System.currentTimeMillis());
                            accountStatement.setCreateId(jsonObject.getLongValue("userId"));
                            accountStatement.setCreateTime(System.currentTimeMillis());
                            accountStatement.setUpdateTime(System.currentTimeMillis());
                            accountStatement.setDelState(1);
                            accountStatement.setType(jsonObject.getIntValue("type"));
                            accountStatement.setTransactionNumber(jsonObject.getString("transactionNumber"));
                            wtBranchesAccountStatementDAO.insertSelective(accountStatement);
                            return new DataMessage(DataMessage.RESULT_SUCESS, null, "个人账户提现提现成功");

                        }else{
                            //失败回滚，并添加失败记录
//                            wtBranchesAccount.setAccount(Long.valueOf(money));
//                            wtBranchesAccount.setUpdateId(jsonObject.getLongValue("userId"));
//                            wtBranchesAccount.setUpdateTime(System.currentTimeMillis());
//                            wtBranchesAccountDAO.update(wtBranchesAccount);

                            //添加失败的记录
                            accountStatement = new WtBranchesAccountStatement();
                            accountStatement.setUserType(jsonObject.getIntValue("userType"));
                            accountStatement.setUserId(jsonObject.getLongValue("onlyId"));
                            accountStatement.setMoney(jsonObject.getLongValue("money"));
                            accountStatement.setTransactionNumber(jsonObject.getString("transactionNumber"));
                            accountStatement.setPayStatus(-1);
                            accountStatement.setFailMes(dm.getMessage());
                            accountStatement.setCreateId(jsonObject.getLongValue("userId"));
                            accountStatement.setCreateTime(System.currentTimeMillis());
                            accountStatement.setUpdateId(jsonObject.getLongValue("userId"));
                            accountStatement.setUpdateTime(System.currentTimeMillis());
                            accountStatement.setDelState(1);
                            accountStatement.setType(jsonObject.getIntValue("type"));
                            wtBranchesAccountStatementDAO.insertSelective(accountStatement);
                            return new DataMessage(DataMessage.RESULT_FAILED, null, dm.getMessage());
                        }
                    }
                }
            }else{
                return new DataMessage(DataMessage.RESULT_FAILED, null, "用户类型或账户唯一识别为空");
            }
//        }catch (Exception e){
//            return new DataMessage(DataMessage.RESULT_FAILED, e.getCause().getMessage(), "系统异常");
//        }
        return null;
    }

    /**
     * 提现明细
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage tixianAccountDetailed(JSONObject jsonObject) throws Exception {
        WtBranchesAccountStatement accountStatement = null;
        if(jsonObject == null){
            return new DataMessage(DataMessage.RESULT_FAILED, null, "参数为空,请检查参数");
        }
        if (jsonObject.containsKey("userType") && !StringUtils.isEmpty(jsonObject.getString("userType")) && jsonObject.containsKey("onlyId") && !StringUtils.isEmpty(jsonObject.getString("onlyId"))) {

            PageHelper.startPage(jsonObject.getIntValue("pageNo"), jsonObject.getIntValue("pageSize"));

            if ("1".equals(jsonObject.getString("userType")) || "2".equals(jsonObject.getString("userType")) || "3".equals(jsonObject.getString("userType"))) {
                accountStatement = new WtBranchesAccountStatement();
                accountStatement.setUserType(jsonObject.getIntValue("userType"));
                accountStatement.setBranchesId(jsonObject.getLongValue("onlyId"));
            }else if ("4".equals(jsonObject.getString("userType"))){
                accountStatement = new WtBranchesAccountStatement();
                accountStatement.setUserType(jsonObject.getIntValue("userType"));
                accountStatement.setUserId(jsonObject.getLongValue("onlyId"));
            }
            accountStatement.setDelState(1);
            accountStatement.setType(2);
            accountStatement.setPayStatus(1);
            if(jsonObject.containsKey("startTime") && !StringUtils.isEmpty(jsonObject.getString("startTime"))){
                accountStatement.setStartTime(jsonObject.getLongValue("startTime"));
            }
            if(jsonObject.containsKey("endTime") && !StringUtils.isEmpty(jsonObject.getString("endTime"))){
                accountStatement.setEndTime(jsonObject.getLongValue("endTime"));
            }
            List<WtBranchesAccountStatement> accountStatementsList = wtBranchesAccountStatementDAO.selectList(accountStatement);
            PageInfo<WtBranchesAccountStatement> accountStatementPageInfo = new PageInfo<>(accountStatementsList);
            return new DataMessage(DataMessage.RESULT_SUCESS, accountStatementPageInfo, "查询用户提现记录成功");
        }else{
            return new DataMessage(DataMessage.RESULT_FAILED, null, "用户类型或账户唯一识别为空");
        }
    }

    public static void main(String[] args) {
        System.out.println("0".equals(0));//false
    }

}
