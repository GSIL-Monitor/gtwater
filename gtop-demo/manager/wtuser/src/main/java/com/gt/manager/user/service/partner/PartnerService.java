package com.gt.manager.user.service.partner;

import com.gt.manager.entity.wtBranchesAccount.WtBranchesAccount;
import com.gt.manager.entity.wtBranchesAccountStatement.WtBranchesAccountStatement;
import com.gt.manager.entity.wtExtensioncode.WtExtensioncode;
import com.gt.manager.entity.wtPartner.WtPartner;
import com.gt.manager.entity.wtUserExtensioncode.WtUserExtensioncode;

import java.util.List;

public interface PartnerService {
    //合伙人信息

    public Long addPartner(WtPartner wtPartner);

    public WtPartner queryParentByUserId(Long userId);

    public void update(WtPartner wtPartner);

    public List<WtPartner> queryPartnerList(WtPartner wtPartner);

    public WtPartner queryByParentId(Long id);

    //扫码记录

    public Long addWtUserExtensioncode(WtUserExtensioncode wtUserExtensioncode);

    public WtUserExtensioncode queryScanRecordByUserId(Long userId);

    public List<WtUserExtensioncode> queryScanRecordList(WtUserExtensioncode wtUserExtensioncode);

    public void updateScanRecord(WtUserExtensioncode wtUserExtensioncode);

    //二维码信息

    public WtExtensioncode queryQrByMessages(String messages);

    public WtExtensioncode queryQrByPartnerId(Long partnerId);

    public List<WtExtensioncode> queryQrList(WtExtensioncode wtExtensioncode);

    public void updateQr(WtExtensioncode wtExtensioncode);

    //钱包信息
    public WtBranchesAccount queryAccountByUserId(Long userId);

    public List<WtBranchesAccountStatement> queryAccountStatement(Long userId, Long startTime, Long endTime);

    public List<WtBranchesAccountStatement> queryWtBranchesAccountStatementList(WtBranchesAccountStatement wtBranchesAccountStatement);

    public List<WtBranchesAccountStatement> queryToday(Long userId, Long startTime, Long endTime);

}
