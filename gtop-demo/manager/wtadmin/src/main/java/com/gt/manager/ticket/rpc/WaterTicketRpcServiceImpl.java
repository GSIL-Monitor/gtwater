package com.gt.manager.ticket.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.gtop.entity.sys.branches.OpenBranches;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.entity.wtOrgSetmeal.WtOrgSetmeal;
import com.gt.manager.entity.wtProduct.WtProduct;
import com.gt.manager.entity.wtSku.WtSku;
import com.gt.manager.entity.wtadmin.UserSimpleEntity;
import com.gt.manager.entity.wtadmin.WtTicketSimple;
import com.gt.manager.product.service.IWtProductService;
import com.gt.manager.rpc.product.IWaterTicketRpcService;
import com.gt.manager.ticket.service.IWtTicketService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Package com.gt.manager.ticket.rpc
 * @ClassName WaterTicketRpcServiceImpl
 * @Description:
 * @Author towards
 * @Date 2018/8/9 19:15
 */
@Service
public class WaterTicketRpcServiceImpl implements IWaterTicketRpcService {
    private static final Logger log = Logger.getLogger(WaterTicketRpcServiceImpl.class);


    @Autowired
    IWtProductService wtProductService;
    @Autowired
    IWtTicketService wtTicketService;

    /**
     * 根据条件查询水票记录（分页）
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception String userName, String userPhone,
     *                   Long branchesId, String qrCode,
     *                   Integer minTicketNum, Integer maxTicketNum,
     *                   Long lastRechargeTime
     */
    @Override
    public DataMessage getTicketRecord(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Integer pageNo = jsonObject.getInteger("pageNo");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String userName = jsonObject.getString("userName");
        String userPhone = jsonObject.getString("userPhone");
        String qrCode = jsonObject.getString("qrCode");
        Integer minTicketNum = jsonObject.getInteger("minTicketNum");
        Integer maxTicketNum = jsonObject.getInteger("maxTicketNum");
        Long fromRechargeTime = jsonObject.getLong("fromRechargeTime");
        Long toRechargeTime = jsonObject.getLong("toRechargeTime");
        String cityId = jsonObject.getString("cityId");
        if (minTicketNum != null && maxTicketNum != null && minTicketNum > maxTicketNum) {
            return new DataMessage(DataMessage.RESULT_PARAMS_ERR, null, "请求参数错误！");
        }
        if (fromRechargeTime != null && toRechargeTime != null && fromRechargeTime > toRechargeTime) {
            return new DataMessage(DataMessage.RESULT_PARAMS_ERR, null, "请求参数错误！");
        }

        JSONObject json = wtTicketService.getTicketRecordList(pageNo, pageSize, userName, userPhone, cityId, qrCode, minTicketNum, maxTicketNum, fromRechargeTime, toRechargeTime);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }

    /**
     * 根据条件导出水票记录
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage exportTicketRecord(HttpServletRequest request, JSONObject jsonObject) throws Exception {

        String userName = jsonObject.getString("userName");
        String userPhone = jsonObject.getString("userPhone");
        String qrCode = jsonObject.getString("qrCode");
        Integer minTicketNum = jsonObject.getInteger("minTicketNum");
        Integer maxTicketNum = jsonObject.getInteger("maxTicketNum");
        Long fromRechargeTime = jsonObject.getLong("fromRechargeTime");
        Long toRechargeTime = jsonObject.getLong("toRechargeTime");
        String cityId = jsonObject.getString("cityId");
        if (minTicketNum != null && maxTicketNum != null && minTicketNum > maxTicketNum) {
            return new DataMessage(DataMessage.RESULT_PARAMS_ERR, null, "请求参数错误！");
        }
        if (fromRechargeTime != null && toRechargeTime != null && fromRechargeTime > toRechargeTime) {
            return new DataMessage(DataMessage.RESULT_PARAMS_ERR, null, "请求参数错误！");
        }

        JSONObject json = wtTicketService.exportTicketRecordList(userName, userPhone, cityId, qrCode, minTicketNum, maxTicketNum, fromRechargeTime, toRechargeTime);
        return new DataMessage(DataMessage.RESULT_SUCESS, json, "请求成功");
    }

    /**
     * 增加水票
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override

    public DataMessage addTicket(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //用户收货地址ID
        Long receiveAddrssId = jsonObject.getLong("receiveAddrssId");
        if (receiveAddrssId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }

        //分支机构ID
        Long branchesId = jsonObject.getLong("branchesId");
        if (branchesId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //商品skuCode
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("skuCode:" + skuCode);
        skuCode = skuCode.trim();

        //水票数量
        Integer num = jsonObject.getInteger("num");
        if (num == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        //操作者ID
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        wtTicketService.addTicket(receiveAddrssId, branchesId, skuCode, num, loginId);
        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 减少水票
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage deleteTicket(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        //商品skuCode
        String skuCode = jsonObject.getString("skuCode");
        if (StringUtils.isEmpty(skuCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("skuCode:" + skuCode);
        skuCode = skuCode.trim();
        //用户ID
        Long userId = jsonObject.getLong("userId");
        if (userId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        //操作者ID
        Long loginId = jsonObject.getLong("loginId");
        if (loginId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        //水票变动数量
        Integer num = jsonObject.getInteger("num");
        if (num == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        wtTicketService.deleteTicket(userId, skuCode, num, loginId);


        return new DataMessage(DataMessage.RESULT_SUCESS, null, "请求成功");
    }

    /**
     * 根据用户ID获取所拥有水票商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getProductByUserId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long userId = jsonObject.getLong("userId");
        if (userId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        List<WtProduct> wtProducts = wtProductService.getProductListByUserId(userId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtProducts, "请求成功");
    }

    /**
     * 根据用户ID和商品ID获取sku信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getWtSkuByUserIdProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long userId = jsonObject.getLong("userId");
        if (userId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        Long productId = jsonObject.getLong("productId");
        if (productId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");

        }
        List<WtSku> wtSkuList = wtProductService.getWtSkuListByUserIdProductId(userId, productId);

        return new DataMessage(DataMessage.RESULT_SUCESS, wtSkuList, "请求成功");
    }


    /**
     * 根据客户id查询水票简单信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getTicketSimple(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long userId = jsonObject.getLong("userId");
        if (userId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<WtTicketSimple> wtTicketSimples = wtTicketService.getWtTicketSimpleByUserId(userId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtTicketSimples, "请求成功");
    }

    /**
     * 根据电话获取用户简单信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getUserSimple(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(phone)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("用户电话：" + phone);
        phone = phone.trim();
        UserSimpleEntity userSimpleEntity = wtTicketService.getUserSimpleByPhone(phone);
        return new DataMessage(DataMessage.RESULT_SUCESS, userSimpleEntity, "请求成功");
    }


    /**
     * 根据电话获取用户收货信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getUserAddressList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(phone)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("用户电话：" + phone);
        phone = phone.trim();
        List<ReceiveAddress> receiveAddresses = wtTicketService.getUserAddressByPhone(phone);
        log.info("地址ID："+receiveAddresses.get(0).getId());
        log.info("地址ID----addId:"+receiveAddresses.get(0).getAddId());

        return new DataMessage(DataMessage.RESULT_SUCESS, receiveAddresses, "请求成功");
    }

    /**
     * 获取所有城市级别组织机构id
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getAllBranchesList(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long orgId = jsonObject.getLong("orgId");
        if (orgId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<OpenBranches> openBranches = wtTicketService.getAllBranchesList(orgId);
        return new DataMessage(DataMessage.RESULT_SUCESS, openBranches, "请求成功");
    }

    /**
     * 根据组织ID获取所属商品信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getProductByBranchesId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long branchesId = jsonObject.getLong("branchesId");
        if (branchesId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<WtProduct> wtProducts = wtProductService.getProductListByBranchesId(branchesId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtProducts, "请求成功");
    }

    /**
     * 根据商品ID和组织机构ID获取sku信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getProductSkuByBranchesIdProductId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        Long productId = jsonObject.getLong("productId");
        if (productId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        Long branchesId = jsonObject.getLong("branchesId");
        if (branchesId == null) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        List<WtSku> wtSkuList = wtProductService.getProductSkuByBranchesIdProductId(branchesId, productId);
        return new DataMessage(DataMessage.RESULT_SUCESS, wtSkuList, "请求成功");
    }

    /**
     * 根据订单编号获取套系信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSetmealListByOrderCode(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String orderCode = jsonObject.getString("orderCode");
        if (StringUtils.isEmpty(orderCode)) {
            return new DataMessage(DataMessage.RESULT_FAILED, null, "请求参数不足！");
        }
        log.info("订单编号：" + orderCode);
        orderCode = orderCode.trim();
        List<WtOrgSetmeal> wtOrgSetmeals = wtTicketService.getSetmealListByOrderCode(orderCode);

        return new DataMessage(DataMessage.RESULT_SUCESS, wtOrgSetmeals, "请求成功");
    }

    /**
     * 根据上级区域ID查询所属区域信息
     *
     * @param request
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public DataMessage getSysRegionByParentId(HttpServletRequest request, JSONObject jsonObject) throws Exception {
        String parentId = jsonObject.getString("parentId");
        if (StringUtils.isEmpty(parentId)){
            parentId=null;
        }else {
            parentId=parentId.trim();
            log.info("上级区域ID："+parentId);
        }

        List<SysRegion> sysRegions = wtTicketService.getSysRegionByParentId(parentId);
        return new DataMessage(DataMessage.RESULT_SUCESS, sysRegions, "请求成功");

    }

    @Override
    public DataMessage proxyCreateOrdreForUser(HttpServletRequest httpServletRequest, JSONObject jsonObject) throws Exception {
        return null;
    }

    @Override
    public DataMessage addAddressForUser(HttpServletRequest httpServletRequest, JSONObject jsonObject) throws Exception {
        return null;
    }
}
