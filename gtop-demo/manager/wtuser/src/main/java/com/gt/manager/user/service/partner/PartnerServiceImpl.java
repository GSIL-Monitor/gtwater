package com.gt.manager.user.service.partner;

import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.entity.wtBranchesAccount.WtBranchesAccount;
import com.gt.manager.entity.wtBranchesAccountStatement.WtBranchesAccountStatement;
import com.gt.manager.entity.wtExtensioncode.WtExtensioncode;
import com.gt.manager.entity.wtPartner.WtPartner;
import com.gt.manager.entity.wtUserExtensioncode.WtUserExtensioncode;
import com.gt.manager.user.repository.thirdUser.ThirdUserDAO;
import com.gt.manager.user.repository.wtBranchesAccount.WtBranchesAccountDAO;
import com.gt.manager.user.repository.wtBranchesAccountStatement.WtBranchesAccountStatementDAO;
import com.gt.manager.user.repository.wtExtensioncode.WtExtensioncodeDAO;
import com.gt.manager.user.repository.wtPartner.WtPartnerDAO;
import com.gt.manager.user.repository.wtUserExtensioncode.WtUserExtensioncodeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.util.HashMap;
import java.util.List;

import static org.bouncycastle.asn1.ua.DSTU4145NamedCurves.params;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private WtPartnerDAO wtPartnerDAO;
    @Autowired
    private WtUserExtensioncodeDAO wtUserExtensioncodeDAO;
    @Autowired
    private WtExtensioncodeDAO wtExtensioncodeDAO;
    @Autowired
    private ThirdUserDAO thirdUserDAO;
    @Autowired
    private WtBranchesAccountDAO wtBranchesAccountDAO;
    @Autowired
    private WtBranchesAccountStatementDAO wtBranchesAccountStatementDAO;

    /**
     * 添加合伙人
     * @param wtPartner
     * @return
     */
    @Override
    public Long addPartner(WtPartner wtPartner) {
        ThirdUser query = new ThirdUser();
        query.setUserId(wtPartner.getUserId());
        List<ThirdUser> thirdUsers = this.thirdUserDAO.selectList(query);
        if(null != thirdUsers && !thirdUsers.isEmpty()){
            wtPartner.setOpenId(thirdUsers.get(0).getOpenId());
            wtPartner.setPortrait(thirdUsers.get(0).getIcon());
            wtPartner.setNickname(thirdUsers.get(0).getNickname());
        }
        WtExtensioncode query2 = new WtExtensioncode();
        query2.setMessages(wtPartner.getQrCode());
        List<WtExtensioncode> wtExtensioncodes = this.wtExtensioncodeDAO.selectList(query2);
        if(null != wtExtensioncodes && !wtExtensioncodes.isEmpty()){
            wtPartner.setQrCodeId(wtExtensioncodes.get(0).getId());//二维码id
        }
        wtPartner.setDelState(1);
        wtPartner.setCreateTime(System.currentTimeMillis());

        WtPartner wt_query = new WtPartner();
        wt_query.setOpenId(wtPartner.getOpenId());
        List<WtPartner> wtPartners = this.wtPartnerDAO.selectList(wt_query);
        if(null == wtPartners || wtPartners.isEmpty()){
            //无数据，添加
            this.wtPartnerDAO.insert(wtPartner);
            //修改推广码为已使用
            WtExtensioncode edit = new WtExtensioncode();
            edit.setId(wtExtensioncodes.get(0).getId());
            edit.setPartnerId(wtPartner.getId());//合伙人id
            edit.setRegisterTime(System.currentTimeMillis());//注册时间
            this.wtExtensioncodeDAO.update(edit);
            return wtPartner.getId();
        }else{
            return wtPartners.get(0).getId();
        }
    }

    /**
     * 根据userId 查询合伙人信息
     * @param userId
     * @return
     */
    @Override
    public WtPartner queryParentByUserId(Long userId) {
        WtPartner query = new WtPartner();
        query.setUserId(userId);
        query.setDelState(1);
        List<WtPartner> wtPartners = this.wtPartnerDAO.selectList(query);
        if(null != wtPartners && !wtPartners.isEmpty()){
            return wtPartners.get(0);
        }else{
            return null;
        }
    }

    /**
     * 合伙人信息修改
     * @param wtPartner
     */
    @Override
    public void update(WtPartner wtPartner) {
        wtPartner.setUpdateTime(System.currentTimeMillis());
        this.wtPartnerDAO.update(wtPartner);
    }

    /**
     * 根据条件查询合伙人
     * @param wtPartner
     * @return
     */
    @Override
    public List<WtPartner> queryPartnerList(WtPartner wtPartner) {
        return this.wtPartnerDAO.selectList(wtPartner);
    }

    @Override
    public WtPartner queryByParentId(Long id) {
        return this.wtPartnerDAO.selectById(id);
    }

    /**
     * 添加扫码记录
     * @param wtUserExtensioncode
     * @return
     */
    @Override
    public Long addWtUserExtensioncode(WtUserExtensioncode wtUserExtensioncode) {
        wtUserExtensioncode.setCreateTime(System.currentTimeMillis());
        wtUserExtensioncode.setRegeditTime(System.currentTimeMillis());
        wtUserExtensioncode.setDelState(1);
        this.wtUserExtensioncodeDAO.insert(wtUserExtensioncode);
        return wtUserExtensioncode.getId();
    }

    /**
     * 根据 userId 查询扫码记录
     * @param userId
     * @return
     */
    @Override
    public WtUserExtensioncode queryScanRecordByUserId(Long userId) {
        WtUserExtensioncode query = new WtUserExtensioncode();
        query.setUserId(userId);
        query.setDelState(1);
        List<WtUserExtensioncode> wtUserExtensioncodes = this.wtUserExtensioncodeDAO.selectList(query);
        if(null != wtUserExtensioncodes && !wtUserExtensioncodes.isEmpty()){
            return wtUserExtensioncodes.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据条件查询扫码记录
     * @param wtUserExtensioncode
     * @return
     */
    @Override
    public List<WtUserExtensioncode> queryScanRecordList(WtUserExtensioncode wtUserExtensioncode) {
        return this.wtUserExtensioncodeDAO.selectList(wtUserExtensioncode);
    }

    /**
     * 修改扫码记录
     * @param wtUserExtensioncode
     */
    @Override
    public void updateScanRecord(WtUserExtensioncode wtUserExtensioncode) {
        this.wtUserExtensioncodeDAO.update(wtUserExtensioncode);
    }

    /**
     * 根据二维码参数查询二维码
     * @param messages
     * @return
     */
    @Override
    public WtExtensioncode queryQrByMessages(String messages) {
        WtExtensioncode query = new WtExtensioncode();
        query.setMessages(messages);
        query.setDelState(1);
        List<WtExtensioncode> wtExtensioncodes = this.wtExtensioncodeDAO.selectList(query);
        if(null != wtExtensioncodes && !wtExtensioncodes.isEmpty()){
            return wtExtensioncodes.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据合伙人id查询二维码信息
     * @param partnerId
     * @return
     */
    @Override
    public WtExtensioncode queryQrByPartnerId(Long partnerId) {
        WtExtensioncode query = new WtExtensioncode();
        query.setPartnerId(partnerId);
        query.setDelState(1);
        List<WtExtensioncode> wtExtensioncodes = this.wtExtensioncodeDAO.selectList(query);
        if(null != wtExtensioncodes && !wtExtensioncodes.isEmpty()){
            return wtExtensioncodes.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据条件查询二维码信息
     * @param wtExtensioncode
     * @return
     */
    @Override
    public List<WtExtensioncode> queryQrList(WtExtensioncode wtExtensioncode) {
        return this.wtExtensioncodeDAO.selectList(wtExtensioncode);
    }

    @Override
    public void updateQr(WtExtensioncode wtExtensioncode) {
        this.wtExtensioncodeDAO.update(wtExtensioncode);
    }

    /**
     * 根据用户id查询合伙人的钱包信息
     * @param userId
     * @return
     */
    @Override
    public WtBranchesAccount queryAccountByUserId(Long userId) {
        WtBranchesAccount query = new WtBranchesAccount();
        query.setUserId(userId);//合伙人id
        List<WtBranchesAccount> wtBranchesAccounts = this.wtBranchesAccountDAO.selectList(query);
        return (wtBranchesAccounts == null || wtBranchesAccounts.isEmpty()) ? null : wtBranchesAccounts.get(0);
    }

    /**
     * 根据用户id查询
     * @param
     * @return
     */
    @Override
    public List<WtBranchesAccountStatement> queryAccountStatement(Long userId, Long startTime, Long endTime) {
        HashMap params = new HashMap();
        params.put("userId", userId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return this.wtBranchesAccountStatementDAO.selectByCondition(params);
    }

    @Override
    public List<WtBranchesAccountStatement> queryWtBranchesAccountStatementList(WtBranchesAccountStatement wtBranchesAccountStatement) {
        return this.wtBranchesAccountStatementDAO.selectList(wtBranchesAccountStatement);
    }

    @Override
    public List<WtBranchesAccountStatement> queryToday(Long userId, Long startTime, Long endTime) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return this.wtBranchesAccountStatementDAO.selectToday(params);
    }
}
