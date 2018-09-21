package com.gt.manager.order.service.wtOrder.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.gtop.service.dubbo.gtpush.IPushMessageDubboAPIService;
import com.gt.gtpay.entity.account.Account;
import com.gt.gtpay.entity.income.IncomeOrder;
import com.gt.gtpay.service.GtPayService;
import com.gt.manager.entity.wtInvoice.WtInvoice;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtOrderMes.WtOrderMes;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.order.entity.send.Goods;
import com.gt.manager.order.repository.wtOrder.WtOrder2DAO;
import com.gt.manager.order.repository.wtSend.WtSend2DAO;
import com.gt.manager.order.repository.wtTicketLog.WtTicketLogDAO;
import com.gt.manager.order.repository.wtUserTicket.WtUserTicketDAO;
import com.gt.manager.order.repository.wtWaterstore.WtWaterstoreDAO;
import com.gt.manager.order.service.wtOrder.WtOrderService;
import com.gt.manager.util.numberCreate.NumberCreateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Service
public class WtOrderServiceImpl implements WtOrderService {

    private static Logger log = Logger.getLogger(WtOrderServiceImpl.class);

    @Value("${myYml.CallbackUrl}")
    private String callbackUrl;

    @Value("${myYml.accounts}")
    private String accounts;

    @Value("${myYml.password}")
    private String password;


    @Autowired
    private WtOrder2DAO dao;//订单

    @Autowired
    private WtUserTicketDAO dao2;//水票

    @Autowired
    private WtSend2DAO daoSend;//派单

    @Autowired
    private WtWaterstoreDAO waterDao;//水站信息表

    @Autowired
    private WtTicketLogDAO tickLogDao;//水票消费记录表


    @Reference
    IPushMessageDubboAPIService pushMessageDubboAPIService;
    @Reference
    GtPayService gtPayService;

    /**
     *  查询订单
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String,Object>>  queryList(HashMap<String,Object> map) {
        List<Map<String, Object>> Lists= dao.queryAllOrder(map);
        return Lists;
    }

    /**
     *  查询订单详情
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String,Object>>  queryOrderDetails(HashMap<String,Object> map) {
        List<Map<String, Object>> Lists= dao.queryOrderMes(map);
        return Lists;
    }
    /**
     *  添加订单 ，添加订单详情
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public HashMap<String,Object> insert(JSONObject json) throws   Exception {
        log.info("前台 传的添加订单的 数据      "+json);
        List<WtSend> sendList = new ArrayList();
        HashMap<String,Object> map = new HashMap <String,Object>();
        if (json == null ||  json.isEmpty()) {
            log.error("商品购买列表不能为空");
            map.put("error","商品购买列表不能为空");
            return map;
        }
        String orderNo = NumberCreateUtil.makeOrderNum(1,null);  //订单编号
        if(json.getInteger("isInvoice")==1){
            WtInvoice wtInvoice = new WtInvoice();
            wtInvoice.setOrderCode(orderNo);//订单ID
            wtInvoice.setUserId(Long.parseLong(json.getString("userId")));//用户（客户）ID
            wtInvoice.setMoney(json.getLong("money"));//发票金额
            wtInvoice.setWaterstoreId(json.getLong("waterstoreId"));//水站ID
            wtInvoice.setState(1);//发票状态【1未送达、2已送达】
            wtInvoice.setTitle(json.getString("title"));//发票台头
            wtInvoice.setDutyNo(json.getString("dutyNo"));//税号
            wtInvoice.setCreateTime(System.currentTimeMillis());//创建时间
            wtInvoice.setCreateId(json.getLong("userId"));//创建人
            wtInvoice.setDelState(1);//删除状态
            dao.insertWtInvoice(wtInvoice);
        }
        //通过用户ID查询用户推广码ID
            HashMap<String ,Object> hashMaps = new HashMap <String,Object>();
                 hashMaps.put("userId",json.getString("userId"));
             Map<String ,Object>  extensioncodeId = dao.queryCodeId(hashMaps);
             Long extensioncodeIds = Long.valueOf("0");
                if(extensioncodeId !=null && !extensioncodeId.isEmpty())
                {
                        extensioncodeIds = Long.valueOf(String.valueOf(extensioncodeId.get("codeId")));
                }
         String appointmentTime = String.valueOf(json.get("appointmentTime"));
          Long timse = Long.parseLong("0");
          if(!"null".equals(appointmentTime) && appointmentTime != null && !"".equals(appointmentTime) ){
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              timse = sdf.parse(appointmentTime+":00").getTime();
              if(System.currentTimeMillis()>timse){
                  timse = System.currentTimeMillis()+600*1000;
              }
          }else{
              timse = System.currentTimeMillis()+600*1000;
          }
        //添加订单
        WtOrder wt = new WtOrder();
        wt.setOrderNo(orderNo);//订单编号
        wt.setWaterstoreId(json.getLong("waterstoreId"));//水站ID
        wt.setUserId( Long.parseLong(json.getString("userId")));//用户（客户）ID
        wt.setContacts(json.getString("contacts"));//用户名
        wt.setPhone(json.getString("phone"));//电话
        wt.setProvinceId(json.getString("provinceId"));//省ID
        wt.setProvince(json.getString("province"));//省
        wt.setCityId( json.getString("cityId"));//市ID
        wt.setCity(json.getString("city"));///市
        wt.setAreaId(json.getString("areaId"));//区ID
        wt.setArea(json.getString("area"));//区
        wt.setAddress( json.getString("address"));//详细地址
        wt.setMoney(json.getLong("money"));//订单金额
        wt.setRemarks( json.getString("remarks"));//买家留言
        wt.setIsInvoice(json.getInteger("isInvoice"));//是否发票
        wt.setIsSetmeal(json.getInteger("isSetmeal"));//是否有套餐
        wt.setIsTicket(json.getInteger("isTicket"));//是否水票抵扣
        wt.setTicketMoney(json.getLong("ticketMoney"));//水票抵扣金额
        wt.setPaymentMoney(json.getLong("paymentMoney"));//付款金额
        wt.setPaymentType(1);//付款方式【1微信】
        wt.setOrderState(1);//订单状态【1订单确认（代付款）、3完成、-1取消】
        wt.setOrderTime(System.currentTimeMillis());//下单时间
        if(json.getLong("paymentMoney")==0){
            wt.setPaymentTime(System.currentTimeMillis());//付款时间
        }else{
            wt.setPaymentTime(null);//付款时间
        }
        wt.setExtensioncodeId(extensioncodeIds);//推广码ID
        wt.setCreateTime(System.currentTimeMillis());//创建时间
        wt.setCreateId(json.getLong("userId"));//创建人
        wt.setDelState(1);//删除状态 1正常、0删除
        wt.setUpdateTime(System.currentTimeMillis());//修改时间
        wt.setAppointmentTime(timse);//预约派送时间
        //添加订单
         Long wtOrder = dao.insert(wt);
        /*if (wtOrder<=0) {
            log.error("订单添加失败");
            map.put("error","订单添加失败");
            return map;
        }*/
        JSONArray jsons = json.getJSONArray("ordermes");
        if(jsons == null || jsons.isEmpty()){
            log.error("订单详情缺失");
            map.put("error","订单详情缺失");
            return map;
        }
        //验证金额
        /*   int moneys = 0 ;
        for(int i = 0;i < jsons.size();i++){
            JSONObject jsono =  JSONObject.parseObject(String.valueOf(jsons.get(i)));
            moneys +=  jsono.getLong("price")*jsono.getInteger("num");
        }
         if (json.getLong("money") != moneys) {
            log.error("订单金额有问题");
            map.put("error","订单金额有问题");
            return map;
        }*/
        map.put("resultType",1);
        String sendNo = NumberCreateUtil.makeOrderNum(3,null);  //派单编号
        log.info("moneyss=================="+json.getLong("paymentMoney"));
        WtWaterstore waterInfo = waterDao.selectById(json.getLong("waterstoreId"));
        log.info("waterInfo=================="+waterInfo);
        if (waterInfo == null || "".equals(waterInfo)){
            log.info("waterInfo 没有水站信息==" +waterInfo);
            throw  new RuntimeException("waterInfo 没有水站信息==" );
        }
        if(json.getLong("paymentMoney")==0){
            WtSend send = new WtSend();
            send.setSendNo(sendNo);//派送单编号
            send.setWaterstoreId(json.getLong("waterstoreId"));//水站ID
            send.setProvinceId(json.getString("provinceId"));//省ID
            send.setProvince(json.getString("province"));//省
            send.setCityId(json.getString("cityId"));//市ID
            send.setCity(json.getString("city"));///市
            send.setAreaId(json.getString("areaId"));//区ID
            send.setArea(json.getString("area"));//区
            send.setAddress( json.getString("address"));//详细地址
            send.setUserId(Long.parseLong(json.getString("userId")));//用户（客户）ID
            send.setContacts(json.getString("contacts"));//用户名
            send.setPhone(json.getString("phone"));//电话
            send.setWaterstoreTel(waterInfo.getTel());
            send.setAppointmentTime(timse);//预约派送时间
            send.setSendTime(null);//实际派送时间
            send.setSendUser(null);// 派送人
            send.setSigenUser(null);// 签收人
            send.setStatus(0);// 状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】
            send.setChangeSendNo(null);// 改派后单号
            send.setRemarks(json.getString("remarks"));//留言
            send.setCreateTime(System.currentTimeMillis());//创建时间
            send.setCreateId( Long.parseLong(json.getString("userId")));//创建人
            send.setDelStatus(1);//删除状态 1正常、0删除
            send.setMoney(json.getLong("money"));//派单金额
            send.setUpdateTime(System.currentTimeMillis());
            send.setUpdateId(Long.parseLong(json.getString("userId")));
            sendList.add(send);
            map.put("resultType",2);
            Map wtMap = new HashMap();
            wtMap.put("orderNo",orderNo);
            wtMap.put("orderState",3);
            wtMap.put("updateTime",System.currentTimeMillis());
            log.info("修改订单状态");
            dao.updateOrders(wtMap);
        }

        //循环添加订单详情、水票消耗和水票消耗记录
        int payMoney = 0;
        for(int i = 0;i < jsons.size();i++){
            JSONObject jsono =  JSONObject.parseObject(String.valueOf(jsons.get(i)));
            //详情编号
            String orderMesNo = NumberCreateUtil.makeOrderNum(2,orderNo);
            System.out.println("jsono================="+jsono);
            Map mapPro = new HashMap();
            mapPro.put("skuCode",jsono.getString("skuCode"));
            mapPro.put("waterstoreId",json.getLong("waterstoreId"));
            JSONObject seqJson = new JSONObject();
            Map setmealJson = new HashMap();
            Map productJson = new HashMap();
            Map giftJson = new HashMap();
            Map invoiceJson = new HashMap();
            invoiceJson.put("title",json.getString("title"));
            invoiceJson.put("dutyNo",json.getString("dutyNo"));
            seqJson.put("invoiceJson",invoiceJson);
            String onshelfState ="";
            if(jsono.getInteger("pType") == 1){
                Map quMap = new HashMap();
                quMap.put("waterstoreId",json.getLong("waterstoreId"));
                quMap.put("skuCode",jsono.getString("skuCode"));
                Map querySetmealJson = dao.querySetmealJson(quMap);
                if(  querySetmealJson == null  || querySetmealJson.isEmpty() ){
                    log.error("没有套餐详情");
                    throw new RuntimeException("没有套餐详情");
                }else{
                    log.info("添加套餐详情序列");
                    setmealJson.put("branchesId",querySetmealJson.get("branchesId"));
                    setmealJson.put("brandId",querySetmealJson.get("brandId"));
                    setmealJson.put("skuCode",querySetmealJson.get("skuCode"));
                    setmealJson.put("setmealCode",querySetmealJson.get("setmealCode"));
                    setmealJson.put("seriesSkuCode",querySetmealJson.get("seriesSkuCode"));
                    setmealJson.put("name",querySetmealJson.get("name"));
                    setmealJson.put("typeCode",querySetmealJson.get("typeCode"));
                    setmealJson.put("seriesName",querySetmealJson.get("seriesName"));
                    setmealJson.put("setmealImg",querySetmealJson.get("setmealImg"));
                    setmealJson.put("shopcartImg",querySetmealJson.get("shopcartImg"));
                    //setmealJson.put("goodsProfile",querySetmealJson.get("goodsProfile"));
                    setmealJson.put("num",querySetmealJson.get("num"));
                    setmealJson.put("introduce",querySetmealJson.get("introduce"));
                    setmealJson.put("price",querySetmealJson.get("price"));
                    setmealJson.put("isGift",querySetmealJson.get("isGift"));
                    setmealJson.put("remark",querySetmealJson.get("remark"));
                    setmealJson.put("onshelfState",querySetmealJson.get("onshelfState"));
                    onshelfState =String.valueOf(querySetmealJson.get("onshelfState"));
                    setmealJson.put("goodsSpec",querySetmealJson.get("goodsSpec"));
                    seqJson.put("setmealJson",setmealJson);
                    Map quMap2 = new HashMap();
                    quMap2.put("branchesId",querySetmealJson.get("branchesId"));
                    quMap2.put("skuCode",querySetmealJson.get("skuCode"));
                    Map querySetmealProJson = dao.querySetmealProJson(quMap2);
                    if(  querySetmealProJson == null  || querySetmealProJson.isEmpty() ){
                        log.error("没有主商品");
                        throw new RuntimeException("没有主商品");
                    }else{
                        log.info("添加套餐主商品序列");
                        productJson.put("pId",querySetmealProJson.get("pId"));
                        productJson.put("goodsCode",querySetmealProJson.get("goodsCode"));
                        productJson.put("brandId",querySetmealProJson.get("brandId"));
                        productJson.put("skuName",querySetmealProJson.get("skuName"));
                        productJson.put("skuCode",querySetmealProJson.get("skuCode"));
                        productJson.put("goodsBar",querySetmealProJson.get("goodsBar"));
                        productJson.put("typeCode",querySetmealProJson.get("typeCode"));
                        productJson.put("goodsPic",querySetmealProJson.get("goodsPic"));
                        productJson.put("goodsSpec",querySetmealProJson.get("goodsSpec"));
                        productJson.put("goodsWeight",querySetmealProJson.get("goodsWeight"));
                        productJson.put("goodsUtil",querySetmealProJson.get("goodsUtil"));
                        productJson.put("goodsSize",querySetmealProJson.get("goodsSize"));
                        productJson.put("goodsColor",querySetmealProJson.get("goodsColor"));
                        productJson.put("status",querySetmealProJson.get("status"));
                        productJson.put("costPrice",querySetmealProJson.get("costPrice"));
                        productJson.put("price",querySetmealProJson.get("price"));
                        productJson.put("onSales",querySetmealProJson.get("onSales"));
                        productJson.put("sellPrice",querySetmealProJson.get("sellPrice"));
                        seqJson.put("productJson",productJson);
                    }
                    if("0".equals(String.valueOf(querySetmealJson.get("isGift")))){
                        seqJson.put("giftJson",giftJson);
                    }else{
                        Map mapGift = new HashMap <>();
                        mapGift.put("branchesId",querySetmealJson.get("branchesId"));
                        mapGift.put("seriesSkuCode",querySetmealJson.get("seriesSkuCode"));
                        List<Map<String,Object>> queryGiftProJson = dao.queryGiftProJson(mapGift);
                        if( queryGiftProJson.size()<=0 ){
                            log.info("没有赠品");
                        }else{
                            log.info("添加赠品序列");
                            List listpr = new ArrayList();
                            for(int p = 0;p<queryGiftProJson.size();p++){
                                Map mapsp = new HashMap();
                                mapsp.put("pick",queryGiftProJson.get(p).get("pick"));
                                mapsp.put("num",queryGiftProJson.get(p).get("num"));
                                mapsp.put("mes",queryGiftProJson.get(p).get("mes"));
                                mapsp.put("type",queryGiftProJson.get(p).get("type"));
                                mapsp.put("skuName",queryGiftProJson.get(p).get("skuName"));
                                mapsp.put("skuCode",queryGiftProJson.get(p).get("skuCode"));
                                mapsp.put("goodsBar",queryGiftProJson.get(p).get("goodsBar"));
                                mapsp.put("typeCode",queryGiftProJson.get(p).get("typeCode"));
                                mapsp.put("goodsPic",queryGiftProJson.get(p).get("goodsPic"));
                                mapsp.put("goodsSpec",queryGiftProJson.get(p).get("goodsSpec"));
                                mapsp.put("sellPrice",queryGiftProJson.get(p).get("sellPrice"));
                                mapsp.put("costPrice",queryGiftProJson.get(p).get("costPrice"));
                                mapsp.put("goodsWeight",queryGiftProJson.get(p).get("goodsWeight"));
                                mapsp.put("goodsUtil",queryGiftProJson.get(p).get("goodsUtil"));
                                mapsp.put("goodsSize",queryGiftProJson.get(p).get("goodsSize"));
                                listpr.add(mapsp);
                            }
                            seqJson.put("giftJson",listpr);
                        }
                    }
                }
            }else {
                log.info("添加商品序列");
                Map  sequences = dao.selectProducts(mapPro);
                Goods gods  = mapToGoods(sequences);
                seqJson.put("productJson",JSONObject.toJSON(gods));
                seqJson.put("setmealJson",setmealJson);
                seqJson.put("giftJson",giftJson);
            }
            //添加订单详情
            log.info("添加订单详情");
            WtOrderMes wtMes = new WtOrderMes();
            wtMes.setOrderNo(orderNo); //订单编号
            wtMes.setOrderMesNo(orderMesNo);//订单详情编号
            wtMes.setSkuCode( jsono.getString("skuCode"));//SKU编号
            wtMes.setSkuName(jsono.getString("skuName"));//SKU名称（商品名称+规格）
            wtMes.setType(jsono.getInteger("type"));//支付方式1水票|| 2现金
            wtMes.setpType(jsono.getInteger("pType"));//商品类型（1水票|| 2桶水）
            wtMes.setPrice(jsono.getLong("price"));//售价
            wtMes.setNum(jsono.getInteger("num")); //数量
            wtMes.setTotalPrice(jsono.getLong("totalPrice"));
            wtMes.setSequence(String.valueOf(seqJson));//商品序列
            wtMes.setCreateTime(System.currentTimeMillis());//创建时间
            wtMes.setCreateId(jsono.getLong("userId"));//创建人
            wtMes.setDelState(1);//删除状态 1正常、0删除
            if(jsono.getInteger("pType") == 1){
                //验证套餐是否已下架
                if ( "0".equals(onshelfState)) {
                    log.error("该套餐以下架");
                    throw new RuntimeException("该套餐以下架");
                }
            }else if(jsono.getInteger("pType") == 2){
                //验证商品是否已下架
                HashMap<String,Object> ha =  new HashMap<String,Object>();
                ha.put("waterstoreId",json.getLong("waterstoreId"));
                ha.put("skuCode",jsono.getString("skuCode"));
                Map list1 = dao.queryProductStatus(ha);
                if ("0".equals(list1.get("status"))) {
                    log.error("该商品以下架");
                    throw new RuntimeException("该商品以下架");
                }
            }
            //查询用户水票余量消耗水票生成水票消耗记录
            log.info("查询用户水票余量消耗水票生成水票消耗记录");
            String sendMesCode = NumberCreateUtil.makeOrderNum(4,sendNo);  //派单明细编号

            if(jsono.getInteger("type") == 1){
                //通过 skucode和 用户id 查找水票余量
                log.info("通过 skucode和 用户id 查找水票余量");
                HashMap mapUT = new HashMap();
                mapUT.put("userId",json.getString("userId"));
                mapUT.put("skuCode",jsono.getString("skuCode"));
                mapUT.put("waterstoreId",json.getLong("waterstoreId"));
                List<WtUserTicket>  queryBySkuCode = dao2.selectByUseridAndSkuCodeDesc(mapUT); //通过 skucode和 用户id 查找水票余量
                //验证   surplus_num
                int numss = 0;

                if(queryBySkuCode != null && !queryBySkuCode.isEmpty()){
                    int needNums = jsono.getInteger("num"); //需支付数量
                    for (int j = 0; j < queryBySkuCode.size(); j++) {
                        numss += queryBySkuCode.get(j).getSurplusNum();
                    }
                    if(needNums > numss){
                        throw  new RuntimeException("水票余额不足");
                    }
                    log.info("需支付数量的水票数"+jsono.getInteger("num"));
                    for (int j = 0; j <queryBySkuCode.size() ; j++) {
                      int ticNums =  queryBySkuCode.get(j).getSurplusNum();//水票数量
                        log.info("添加订单里的水票余量====="+ticNums);
                        //支付数量跟 水票数量相同个 或大于水票数量
                        WtTicketLog wtLog = new WtTicketLog();
                        if(needNums-ticNums>0){
                            WtUserTicket wts = new WtUserTicket();
                            wts.setUpdateTime(System.currentTimeMillis());
                            wts.setSurplusNum(0);
                            needNums = needNums-ticNums;
                            //通过水票ID修改 水票余量
                            wts.setId(queryBySkuCode.get(j).getId());
                            dao2.updateUserTick(wts);
                            wtLog.setNum(ticNums);
                        }else if(needNums-ticNums<0){
                            WtUserTicket wts = new WtUserTicket();
                            wts.setUpdateTime(System.currentTimeMillis());
                            wts.setSurplusNum(ticNums-needNums);
                            //通过水票ID修改 水票余量
                            wts.setId(queryBySkuCode.get(j).getId());
                            dao2.updateUserTick(wts);
                            wtLog.setNum(needNums);
                            wtLog.setCreateTime(System.currentTimeMillis());
                            wtLog.setTicketId(queryBySkuCode.get(j).getId());
                            wtLog.setSkuCode(jsono.getString("skuCode"));
                            wtLog.setSkuName(jsono.getString("skuName"));
                            wtLog.setTicketPrice(queryBySkuCode.get(j).getTicketPrice());
                            payMoney += queryBySkuCode.get(j).getTicketPrice();
                            if(json.getLong("paymentMoney") == 0){
                                wtLog.setSendMesCode(sendMesCode);
                            }
                            wtLog.setOperation(-1);
                            wtLog.setType(1);
                            wtLog.setUserId(queryBySkuCode.get(j).getUserId());
                            wtLog.setUserName(json.getString("contacts"));
                            wtLog.setAddress(json.getString("address"));
                            wtLog.setOrderMesCode(wtMes.getOrderMesNo());
                            wtLog.setLogTime(System.currentTimeMillis());
                            wtLog.setCreateTime(System.currentTimeMillis());
                            wtLog.setCreateId(queryBySkuCode.get(j).getUserId());
                            wtLog.setDelState(1);
                            tickLogDao.insert(wtLog);
                            break;
                        }else if(needNums-ticNums==0){
                            WtUserTicket wts = new WtUserTicket();
                            wts.setUpdateTime(System.currentTimeMillis());
                            wts.setSurplusNum(0);
                            wts.setId(queryBySkuCode.get(j).getId());
                            dao2.updateUserTick(wts);
                            wtLog.setNum(needNums);
                            wtLog.setCreateTime(System.currentTimeMillis());
                            wtLog.setTicketId(queryBySkuCode.get(j).getId());
                            wtLog.setSkuCode(jsono.getString("skuCode"));
                            wtLog.setSkuName(jsono.getString("skuName"));
                            wtLog.setTicketPrice(queryBySkuCode.get(j).getTicketPrice());
                            payMoney += queryBySkuCode.get(j).getTicketPrice();
                            if(json.getLong("paymentMoney") == 0){
                                wtLog.setSendMesCode(sendMesCode);
                            }
                            wtLog.setOperation(-1);
                            wtLog.setType(1);
                            wtLog.setUserId(queryBySkuCode.get(j).getUserId());
                            wtLog.setUserName(json.getString("contacts"));
                            wtLog.setAddress(json.getString("address"));
                            wtLog.setOrderMesCode(wtMes.getOrderMesNo());
                            wtLog.setLogTime(System.currentTimeMillis());
                            wtLog.setCreateTime(System.currentTimeMillis());
                            wtLog.setCreateId(queryBySkuCode.get(j).getUserId());
                            wtLog.setDelState(1);
                            tickLogDao.insert(wtLog);
                            break;
                        }
                        log.info("添加水票消费日志");
                        wtLog.setCreateTime(System.currentTimeMillis());
                        wtLog.setTicketId(queryBySkuCode.get(j).getId());
                        wtLog.setSkuCode(jsono.getString("skuCode"));
                        wtLog.setSkuName(jsono.getString("skuName"));
                        wtLog.setTicketPrice(queryBySkuCode.get(j).getTicketPrice());
                        payMoney += queryBySkuCode.get(j).getTicketPrice();
                        if(json.getLong("paymentMoney") == 0){
                            wtLog.setSendMesCode(sendMesCode);
                        }
                        wtLog.setOperation(-1);
                        wtLog.setType(1);
                        wtLog.setUserId(queryBySkuCode.get(j).getUserId());
                        wtLog.setUserName(json.getString("contacts"));
                        wtLog.setAddress(json.getString("address"));
                        wtLog.setOrderMesCode(wtMes.getOrderMesNo());
                        wtLog.setLogTime(System.currentTimeMillis());
                        wtLog.setCreateTime(System.currentTimeMillis());
                        wtLog.setCreateId(queryBySkuCode.get(j).getUserId());
                        wtLog.setDelState(1);
                        tickLogDao.insert(wtLog);
                    }
                }else {
                    throw new RuntimeException("水票余额不足");
                }
            }
             if(sendList != null && !sendList.isEmpty()){
                 log.info("派单不等于空 添加派单明细");
                List sendMesList = new ArrayList();
                Map setmealMap = new HashMap();
                 setmealMap.put("skuCode",jsono.getString("skuCode"));
                 setmealMap.put("waterstoreId",json.getString("waterstoreId"));
                 Map  sequences = dao.selectProducts(setmealMap);
                 Goods gods  = mapToGoods(sequences);
                 WtSendMes sendMes  = new WtSendMes();
                 sendMes.setSendCode(sendNo);
                 sendMes.setSendMesCode(sendMesCode);
                 sendMes.setSkuCode(jsono.getString("skuCode"));
                 sendMes.setSkuName(jsono.getString("skuName"));
                 sendMes.setNum(jsono.getInteger("num"));
                 sendMes.setDeliveryNum(jsono.getInteger("num"));
                 sendMes.setSequence( JSONObject.toJSONString(gods));
                 sendMes.setOrderMesCode(orderMesNo);
                 sendMes.setCreateId(jsono.getLong("userId"));
                 sendMes.setType(1);
                 sendMes.setCreateTime(System.currentTimeMillis());
                 sendMes.setDelState(1);
                 sendMes.setGoodsSpec(gods.getGoodsSpec());
                 sendMes.setBrandName(gods.getBrandName());
                 sendMesList.add(sendMes);
                 dao.insertSendMesList(sendMesList);
             }
                Long longmes = dao.insertMes(wtMes);
            System.out.println("longmes============="+longmes);
            if ( longmes<1) {
                log.error("订单详情添加有误");
                throw new RuntimeException("订单详情添加有误");
            }
        }
        if(json.getInteger("isInvoice")==1)
        {
            HashMap hh = new HashMap();
            hh.put("userId",json.getString("userId"));
            //根据用户ID查看用户发票
            List<HashMap<String,Object>>  listMap = dao.queryUserDetailed(hh);
            map.put("listMap",listMap);
        }
        if(json.getLong("paymentMoney")==0){
            if( sendList != null || !sendList.isEmpty()){
                sendList.get(0).setMoney(Long.parseLong(payMoney+""));
                dao.insertSendList(sendList);
            }
            log.info("推送消息设置.................................");
            try{
            JSONObject json22 = new JSONObject();
                json22.put("receiveId",waterInfo.getBranchesId());//接受的分支机构id
                json22.put("pushId",waterInfo.getBranchesId());//推送的分支机构ID
                json22.put("title","新的订单");//消息题目
                json22.put("content","您有新的订单，请注意查收！");//内容
            tuisong(json22);
            }catch (Exception e){
                log.error("推送消息异常 ",e);
                e.printStackTrace();
            }
        }
        log.info("返给前台的状态  resultType=========="+map.get("resultType").toString());
        map.put("data",json);
        map.put("orderNo",orderNo);
        return map;
    }

    /**
     * 水管家开关
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Object> queryFunction() throws Exception{
        return dao.queryFunction();
    }

    /**
     * 查询订单和派送单
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public Map <String, Object> queryOrders(JSONObject json ) throws Exception {
        //获取状态 1全部  2待付款  3待收货  4已完成  5已取消
            Long state = json.getLong("state");
            Map map = new HashMap();
            HashMap maps = new HashMap();
            maps.put("userId",json.getString("userId"));
         if(state==1){
             //map.put("data1",dao.queryOrders(maps));
             List<WtOrder>  list = dao.selectByOrderUserId(maps);
             map.put("data1",list);
             //map.put("data2",dao.queryDistributeLeaflets(maps));
             List<WtSend>  list2 =  daoSend.selectBySendUserId(maps);
             map.put("data2",list2);
         }else if(state==2){
            maps.put("orderState",1);
             List<WtOrder>  list = dao.selectByOrderUserId(maps);
             map.put("data1",list);
        }else if(state==3){
             maps.put("status",1);
             maps.put("state",3);
             List<WtSend>  list2 =  daoSend.selectBySendUserId(maps);
             map.put("data2",list2);
        }else if(state==4){
             maps.put("orderState",3);
             maps.put("status",5);
             List<WtOrder>  list = dao.selectByOrderUserId(maps);
             map.put("data1",list);
             List<WtSend>  list2 =  daoSend.selectBySendUserId(maps);
             map.put("data2",list2);
         }else if(state==5){
             maps.put("orderState",-1);
             maps.put("status",-1);
             List<WtOrder>  list = dao.selectByOrderUserId(maps);
             map.put("data1",list);
             List<WtSend>  list2 =  daoSend.selectBySendUserId(maps);
             map.put("data2",list2);
         }
         if(map.containsKey("data1")){
             List<WtOrder> listData1 = (List)map.get("data1");
             if(listData1 !=null && !listData1.isEmpty()){
                 for(int i=0 ;i<listData1.size();i++){
                     List<WtOrderMes> listD2 =  listData1.get(i).getWtOrderMes();
                     if(listD2 !=null && !listD2.isEmpty()){
                         for(int j=0 ;j<listD2.size();j++){
                             listD2.get(j).setSequence(listD2.get(j).getSequence().replaceAll("\\\\",""));
                         }
                         List<WtOrderMes>   countList = new ArrayList<WtOrderMes>();// 用于存放最后的结果
                         for (int p = 0; p < listD2.size(); p++) {
                             String gsId = listD2.get(p).getSkuCode();
                             log.info("gsId===="+gsId);
                             int flag = 0;// 0为新增数据，1为增加count
                             for (int s = 0; s < countList.size(); s++) {
                                 String gsId_ = countList.get(s).getSkuCode();
                                 if (gsId.equals(gsId_)) {
                                     int sum = listD2.get(p).getNum() + countList.get(s).getNum();
                                     Long price = listD2.get(p).getPrice() + countList.get(s).getPrice();
                                     countList.get(s).setNum(sum);
                                     countList.get(s).setPrice(price);
                                     flag = 1;
                                     continue;
                                 }
                             }
                             if (flag == 0) {
                                 countList.add(listD2.get(p));
                             }
                         }
                         listData1.get(i).setWtOrderMes(countList);
                         log.info("listData1.get(i).setWtOrderMes(countList)======="+listData1.get(i).getWtOrderMes());
                     }

                 }
             }
         }
        return map;
    }
    /**
     * 查询状态数量
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public Map <String, Object> queryOrdersNums(JSONObject json) throws Exception {
           //获取状态 1全部  2待付款  3待收货  4已完成  5已取消
            int s1 = 0;
            int s2 = 0;
            int s3 = 0;
            int s4 = 0;
            int s5 = 0;
            Map maps = new HashMap();
            log.info("查询数量的  userId" +json.getString("userId"));
            maps.put("userId",json.getString("userId"));
            List<Map<String,Object>> list1 = dao.queryOrdersNums(maps);//所有订单
            List<Map<String,Object>> list2 = dao.queryDistributeLeafletsNums(maps);//所有派送单
            if (list1 != null && !list1.isEmpty()){
                for (int i =0; i<list1.size();i++){
                    int numss = Integer.parseInt(String.valueOf(list1.get(i).get("nums")));
                    if( "1".equals(String.valueOf(list1.get(i).get("stauts"))) ){
                        s2 += numss;
                    }else if( "3".equals(String.valueOf(list1.get(i).get("stauts")))){
                        s4 += numss;
                    }else if( "-1".equals(String.valueOf(list1.get(i).get("stauts"))) ){
                        s5 += numss;
                    }
                }
            }
        if (list2 != null && !list2.isEmpty()){
                for (int i =0; i<list2.size();i++){
                    int numss2 = Integer.parseInt(String.valueOf(list2.get(i).get("nums")));
                    if( "1".equals(String.valueOf(list2.get(i).get("stauts"))) ||"0".equals(String.valueOf(list2.get(i).get("stauts")))){
                        s3 += numss2;
                    }else if( "5".equals(String.valueOf(list2.get(i).get("stauts")))){
                        s4 += numss2;
                    }else if( "-1".equals(String.valueOf(list2.get(i).get("stauts"))) ){
                        s5 += numss2;
                    }
                }
            }
        s1 = s2 + s3 + s4 + s5;
        Map map = new HashMap();
        map.put("nums1",s1);
        map.put("nums2",s2);
        map.put("nums3",s3);
        map.put("nums4",s4);
        map.put("nums5",s5);
        log.info("前端订单数量展示======"+map);
        return map;
    }
    /**
     * 通过用户ID查询水票剩余量
     */
    @Override
    public List <Map> queryUserTickets(Long userId,String waterstoreId) {
        Map map = new HashMap();
        map.put("userId",userId);
        map.put("waterstoreId",waterstoreId);
        return dao.queryUserTickets(map);
    }
    /**
     * 查找店铺营业时间
     */
    @Override
    public Map <String, Object> selsectOpenTimes(String waterstoreId) {
        Map <String, Object> timesMap = dao.selsectOpenTimes(waterstoreId);
        String operateWeek = String.valueOf(timesMap.get("operateWeek"));
        String[]  operateWeeks = operateWeek.split(",");
        timesMap.put("operateWeek",operateWeeks);
        return timesMap;
    }
    /**
     *   回调 添加水票 生成派送单
     */
    @Override
    @Transactional
    public Map <String, Object> querySend(String payCode) throws Exception {
        //通过payCode 查询
        Map mapsResult =new HashMap();//返回
        Map  setmealMap  = new HashMap();  //查询所需要的参数
        List<WtUserTicket> tickList    = new ArrayList();//用户水票表
        List<WtTicketLog> tickLogLost = new ArrayList();//水票日志表
        List<WtSend> sendList    = new ArrayList();//派单表
        List<WtSendMes> sendMesList = new ArrayList();//派单详情表
        Map mesMap = new HashMap();
        log.info("payCode ==============="+payCode);
        //判断是否支付
        WtOrder wtOrders = dao.weChatPayment(payCode); //订单和订单详情order_state
        //判断是否为空
        if(wtOrders==null){
            mapsResult.put("result","0");
            mapsResult.put("message","没有订单");
            log.info("wtOrders 没有订单==" +wtOrders);
            return mapsResult;
        }
        if(wtOrders.getOrderState() == 3){
            mapsResult.put("result","0");
            mapsResult.put("message","已支付");
            return mapsResult;
        }else{
            setmealMap.put("orderState",3);
            setmealMap.put("paymentTime",System.currentTimeMillis());
            setmealMap.put("orderNo",wtOrders.getOrderNo());
            dao.updateOrders(setmealMap);
         WtWaterstore waterInfo = waterDao.selectById(wtOrders.getWaterstoreId());
            log.info("yyyyyyyyyyyyyy22222222222222");
         if (waterInfo == null || "".equals(waterInfo)){
             log.info("waterInfo 没有水站信息==" +waterInfo);
             throw  new RuntimeException("waterInfo 没有水站信息==" );
         }
        String sendNo = NumberCreateUtil.makeOrderNum(3,null);  //派单编号
        long zNum = 0;
        List<WtOrderMes> listOrderMes = wtOrders.getWtOrderMes();//订单详情
            //判断是否为空
            if(listOrderMes==null || listOrderMes.isEmpty()){
                mapsResult.put("result","0");
                mapsResult.put("message","没有明细");
                log.info("listOrderMes 没有明细==" +listOrderMes);
                return mapsResult;
            }
            log.info("listOrderMes==============="+listOrderMes);
        for (int i = 0; i<listOrderMes.size(); i++){
            //通过订单明细编号查询 水票消耗日志
            WtTicketLog tLogs = new WtTicketLog();
            tLogs.setOrderMesCode(listOrderMes.get(i).getOrderMesNo());
            tLogs.setOperation(-1);
           List<WtTicketLog>  tLogss = tickLogDao.selectOneXiaoFeiSend(tLogs);
           log.info("循环日志  计算每个详情消耗水票的价格"+tLogss);
           //循环日志  计算每个详情消耗水票的价格
            if(tLogss != null && !tLogss.isEmpty()){
                for (int j = 0; j < tLogss.size(); j++) {
                    zNum += tLogss.get(j).getNum()*tLogss.get(j).getTicketPrice();
                }
            }
            String sendMesCode = NumberCreateUtil.makeOrderNum(4,sendNo);  //派单明细编号

            log.info("yyyyyyyyyyyyyy");
            setmealMap.put("orderCode",wtOrders.getOrderNo());
            //判断是否为套餐
            if(1 == listOrderMes.get(i).getpType()){
                log.info("套餐");
                //是套餐
                // 通过skuCode（套系编号） 水站ID  套系的主商品
                setmealMap.put("seriesSkuCode",listOrderMes.get(i).getSkuCode());
                setmealMap.put("waterstoreId",wtOrders.getWaterstoreId());
                Map qSteamMes =  dao.queryetmealsSMes(setmealMap);//套系的主商品
                log.info("qSteamMes==============="+qSteamMes);
                if(qSteamMes == null || qSteamMes.isEmpty()){
                    mapsResult.put("result","0");
                    mapsResult.put("message","套系的主商品为空");
                    log.info("套系的主商品为空==" +qSteamMes);
                    throw new RuntimeException("套系的主商品为空");
                }
                if("1".equals(String.valueOf(qSteamMes.get("isGift")))){
                    log.info("赠品");
                    //有赠品
                    setmealMap.put("branchesId",qSteamMes.get("branchesId"));
                    List<Map<String,Object>> queryGiftPro = dao.queryGiftProJson(setmealMap);
                    if(queryGiftPro==null || queryGiftPro.isEmpty()){
                        mapsResult.put("result","0");
                        mapsResult.put("message","设置了赠品 没查到");
                        log.info("queryGiftPro 设置了赠品 没查到==");
                        throw new RuntimeException("没有赠品");
                    }
                    for(int j = 0; j<queryGiftPro.size(); j++){
                        if("1".equals(String.valueOf(queryGiftPro.get(j).get("type")))){
                            log.info("赠品水票");
                            //通过 skucode 和  订单编号 查询用户数水票ID 和 水票数量
                            setmealMap.put("skuCode",qSteamMes.get("skuCode"));
                            setmealMap.put("orderCode",wtOrders.getOrderNo());
                           // WtUserTicket uTicket = dao.selsectTicketByCode(setmealMap);
                            Long utId ;
                            // if(uTicket == null){
                                 //没有数据新建用户水票
                                 WtUserTicket wtUserTicket = new WtUserTicket();
                                 wtUserTicket.setUserId(wtOrders.getUserId());//用户ID
                                 wtUserTicket.setNum(Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());//水票数量
                                 wtUserTicket.setSurplusNum(Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());//水票余额数量
                                 wtUserTicket.setTicketPrice(Long.valueOf(0));//水票单价
                                 wtUserTicket.setSkuCode(String.valueOf(queryGiftPro.get(j).get("skuCode")));//SkuID
                                 wtUserTicket.setOrderCode(wtOrders.getOrderNo());//订单编号
                                 wtUserTicket.setOrderTime( wtOrders.getOrderTime());//订单时间
                                 wtUserTicket.setUpdateTime(System.currentTimeMillis());//修改时间
                                 wtUserTicket.setCreateTime(System.currentTimeMillis());//创建时间
                                 wtUserTicket.setCreateId(wtOrders.getUserId());//创建人
                                 wtUserTicket.setDelState(1);//删除状态 1正常、0删除
                                 wtUserTicket.setBranchesId(Long.parseLong(String.valueOf(qSteamMes.get("branchesId"))));//机构id
                                 //新增用户水票  用skucode 和  订单编号
                                 log.info("wtUserTicket 赠品水票===="+wtUserTicket.toString());
                                 dao.insertTickList(wtUserTicket);
                                 utId = wtUserTicket.getId();
                            /* }else{
                                 //有记录 修改用户水票表里的谁票数
                                 Map nmMap = new HashMap();
                                 nmMap.put("num",uTicket.getNum()+Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());
                                 nmMap.put("surplusNum",uTicket.getSurplusNum()+Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());
                                 nmMap.put("updateTime",System.currentTimeMillis());
                                 nmMap.put("id",uTicket.getId());
                                 utId = uTicket.getId();
                                 //通过用户水票ID 修改水票数量
                                 dao.updateUTickList(nmMap);
                             }*/
                            //添加水漂日志
                            WtTicketLog wtTicketLogs = new WtTicketLog();
                            wtTicketLogs.setTicketId(utId);//水票ID
                            wtTicketLogs.setSkuName(String.valueOf(queryGiftPro.get(j).get("skuName"))); //SKU名称（商品名称+规格）
                            wtTicketLogs.setSkuCode(String.valueOf(queryGiftPro.get(j).get("skuCode")));   //SKU编号
                            wtTicketLogs.setNum(Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());   //数量
                            wtTicketLogs.setTicketPrice(Long.parseLong("0"));//价格
                            wtTicketLogs.setType(1);   //订单/派送单
                            wtTicketLogs.setOperation(1);   //增/减 【1增、-1减】
                            wtTicketLogs.setUserId(wtOrders.getUserId());   //用户(客户)ID
                            wtTicketLogs.setUserName(wtOrders.getContacts());   //用户（客户）名称
                            wtTicketLogs.setAddress(wtOrders.getAddress());   //地址
                            wtTicketLogs.setTicketPrice(Long.parseLong("0"));
                            //wtTicketLogs.setSendMesCode(sendMesCode);   //派送单明细code
                            wtTicketLogs.setOrderMesCode(listOrderMes.get(i).getOrderMesNo());   //订单明细code
                            wtTicketLogs.setLogTime(null);   //消费时间
                            wtTicketLogs.setCreateTime(System.currentTimeMillis());   //创建时间
                            wtTicketLogs.setCreateId(wtOrders.getUserId());//创建人
                            wtTicketLogs.setDelState(1); //删除状态 1正常、0删除'
                            tickLogLost.add(wtTicketLogs);
                        }else if("2".equals(String.valueOf(queryGiftPro.get(j).get("type")))){
                            log.info("赠品水");
                            //zNum += Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("pick"))) * Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")));
                            //生成派送单详情
                            setmealMap.put("skuCode",queryGiftPro.get(j).get("skuCode"));
                            Map  sequences = dao.selectProducts(setmealMap);
                            Goods gods  = mapToGoods(sequences);
                            WtSendMes sendMes  = new WtSendMes();
                            sendMes.setSendCode(sendNo);
                            sendMes.setSendMesCode(sendMesCode);
                            sendMes.setSkuCode( String.valueOf(queryGiftPro.get(j).get("skuCode")));
                            sendMes.setSkuName( String.valueOf(queryGiftPro.get(j).get("skuName")));
                            sendMes.setNum(Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());
                            sendMes.setDeliveryNum(Integer.parseInt(String.valueOf(queryGiftPro.get(j).get("num")))*listOrderMes.get(i).getNum());
                            sendMes.setOrderMesCode( listOrderMes.get(i).getOrderMesNo());
                            sendMes.setSequence( JSONObject.toJSONString(gods));
                            sendMes.setCreateId(wtOrders.getUserId());
                            sendMes.setType(4);
                            sendMes.setCreateTime(System.currentTimeMillis());
                            sendMes.setDelState(1);
                            sendMes.setGoodsSpec(gods.getGoodsSpec());
                            sendMes.setBrandName(gods.getBrandName());
                            //sendMes.setSendMesMoney(listOrderMes.get(i).getPrice());
                            sendMesList.add(sendMes);
                            }
                    }
                }
                //主商品
                //添加水票 添加用户水票
                setmealMap.put("skuCode",qSteamMes.get("skuCode"));
                setmealMap.put("orderCode",wtOrders.getOrderNo());
              //  WtUserTicket uTicket = dao.selsectTicketByCode(setmealMap);
                Long utId2 ;
               // if(uTicket == null){
                    //没有数据新建用户水票
                    WtUserTicket wtUserTicket = new WtUserTicket();
                    wtUserTicket.setUserId(wtOrders.getUserId());//用户ID
                    wtUserTicket.setNum(Integer.parseInt(String.valueOf(qSteamMes.get("num")))*listOrderMes.get(i).getNum());//水票数量
                    wtUserTicket.setSurplusNum(Integer.parseInt(String.valueOf(qSteamMes.get("num")))*listOrderMes.get(i).getNum());//水票余额数量
                    wtUserTicket.setTicketPrice(Math.round(Math.floor(Integer.parseInt(String.valueOf(qSteamMes.get("price")))/Integer.parseInt(String.valueOf(qSteamMes.get("num"))))));//水票单价
                    wtUserTicket.setSkuCode(String.valueOf(qSteamMes.get("skuCode")));//SkuID
                    wtUserTicket.setOrderCode(wtOrders.getOrderNo());//订单编号
                    wtUserTicket.setOrderTime( wtOrders.getOrderTime());//订单时间
                    wtUserTicket.setUpdateTime(System.currentTimeMillis());//修改时间
                    wtUserTicket.setCreateTime(System.currentTimeMillis());//创建时间
                    wtUserTicket.setCreateId(wtOrders.getUserId());//创建人
                    wtUserTicket.setDelState(1);//删除状态 1正常、0删除
                    wtUserTicket.setBranchesId(Long.parseLong(String.valueOf(qSteamMes.get("branchesId"))));//机构id
                    //新增用户水票  用skucode 和  订单编号
                    log.info("wtUserTicket 用户水票表 添加 ==============" +wtUserTicket.toString());
                    dao.insertTickList(wtUserTicket);
                    utId2 = wtUserTicket.getId();
              /*  }else{
                    //有记录 修改用户水票表里的谁票数
                    Map nmMap = new HashMap();
                    nmMap.put("num",uTicket.getNum()+Integer.parseInt(String.valueOf(qSteamMes.get("num")))*listOrderMes.get(i).getNum());
                    nmMap.put("surplusNum",uTicket.getSurplusNum()+Integer.parseInt(String.valueOf(qSteamMes.get("num")))*listOrderMes.get(i).getNum());
                    nmMap.put("updateTime",System.currentTimeMillis());
                    nmMap.put("id",uTicket.getId());
                    utId2 = uTicket.getId();
                    //通过用户水票ID 修改水票数量
                    dao.updateUTickList(nmMap);
                }*/
                //添加水漂日志
                WtTicketLog wtTicketLogs = new WtTicketLog();
                wtTicketLogs.setTicketId(utId2);//水票ID
                wtTicketLogs.setSkuName(String.valueOf(qSteamMes.get("skuName"))); //SKU名称（商品名称+规格）
                wtTicketLogs.setSkuCode(String.valueOf(qSteamMes.get("skuCode")));   //SKU编号
                wtTicketLogs.setNum(Integer.parseInt(String.valueOf(qSteamMes.get("num")))*listOrderMes.get(i).getNum());   //数量
                wtTicketLogs.setTicketPrice(Math.round(Math.floor(Integer.parseInt(String.valueOf(qSteamMes.get("price")))/Integer.parseInt(String.valueOf(qSteamMes.get("num"))))));//价格
                wtTicketLogs.setType(1);   //订单/派送单
                wtTicketLogs.setOperation(1);   //增/减 【1增、-1减】
                wtTicketLogs.setUserId(wtOrders.getUserId());   //用户(客户)ID
                wtTicketLogs.setUserName(wtOrders.getContacts());   //用户（客户）名称
                wtTicketLogs.setAddress(wtOrders.getAddress());   //地址
               // wtTicketLogs.setSendMesCode(sendMesCode);   //派送单明细code
                wtTicketLogs.setOrderMesCode(listOrderMes.get(i).getOrderMesNo());   //订单明细code
                wtTicketLogs.setLogTime(null);   //消费时间
                wtTicketLogs.setCreateTime(System.currentTimeMillis());   //创建时间
                wtTicketLogs.setCreateId(wtOrders.getUserId());//创建人
                wtTicketLogs.setDelState(1); //删除状态 1正常、0删除'
                tickLogLost.add(wtTicketLogs);
                log.info("套餐完");
            }else{
                log.info("商品");
                //商品生成派送单

                //生成派送单详情
                setmealMap.put("skuCode",listOrderMes.get(i).getSkuCode());
                setmealMap.put("waterstoreId",wtOrders.getWaterstoreId());
                Map  sequences = dao.selectProducts(setmealMap);
                Goods gods  = mapToGoods(sequences);
                WtSendMes sendMes  = new WtSendMes();
                sendMes.setSendCode(sendNo);
                sendMes.setSendMesCode(sendMesCode);
                sendMes.setSkuCode(listOrderMes.get(i).getSkuCode());
                sendMes.setSkuName(listOrderMes.get(i).getSkuName());
                sendMes.setNum(listOrderMes.get(i).getNum());
                sendMes.setDeliveryNum(sendMes.getNum());
                sendMes.setOrderMesCode(listOrderMes.get(i).getOrderMesNo());
                if(listOrderMes.get(i).getType()==2){
                zNum += listOrderMes.get(i).getPrice()*listOrderMes.get(i).getNum();
                }
                sendMes.setSequence( JSONObject.toJSONString(gods));
                sendMes.setCreateId(wtOrders.getUserId());
                sendMes.setType(listOrderMes.get(i).getType());//
                sendMes.setCreateTime(System.currentTimeMillis());
                sendMes.setDelState(1);
                sendMes.setGoodsSpec(gods.getGoodsSpec());
                sendMes.setBrandName(gods.getBrandName());
                //sendMes.setSendMesMoney(listOrderMes.get(i).getPrice());
                if(listOrderMes.get(i).getType()==1){
                    WtTicketLog wt = new WtTicketLog();
                    wt.setOrderMesCode(listOrderMes.get(i).getOrderMesNo());
                    wt.setOperation(-1);
                    wt.setSendMesCode(sendMesCode);
                    tickLogDao.updateTickLog(wt);
                }
                int qq = 0;
                // 如果订单详情相等 该派单详情为混合支付
                for (int q = 0;q<sendMesList.size();q++){
                    if(sendMesList.get(q).getSkuCode().equals(sendMes.getSkuCode()) && sendMesList.get(q).getType() != 4){
                        qq=1;
                        sendMesList.get(q).setType(3);
                        sendMesList.get(q).setNum(sendMesList.get(q).getNum()+sendMes.getNum());
                        sendMesList.get(q).setDeliveryNum( sendMesList.get(q).getNum());
                        //根据订单编号 和 SkuCode 查找用水票的订单明细
                        WtOrderMes odMes = new WtOrderMes();
                        odMes.setSkuCode(sendMesList.get(q).getSkuCode());
                        odMes.setOrderNo(wtOrders.getOrderNo());
                        WtOrderMes orMes = dao.queryOrderMesNoTick(odMes);
                        if(orMes != null){
                            WtTicketLog wt = new WtTicketLog();
                            wt.setOrderMesCode(orMes.getOrderMesNo());
                            wt.setOperation(-1);
                            wt.setSendMesCode(sendMesList.get(q).getSendMesCode());
                            //wt.setSendMesCode(sendMes.getSendMesCode());
                            tickLogDao.updateTickLog(wt);
                        }
                    }
                }

                if(qq<1){
                    sendMesList.add(sendMes);
                }
                log.info("商品完");
            }
            log.info("订单详情执行了" + i +"次");
        }
        log.info("生成派单");
            WtSend send = new WtSend();
            send.setSendNo(sendNo);//派送单编号
            send.setWaterstoreId(wtOrders.getWaterstoreId());//水站ID
            send.setProvinceId( wtOrders.getProvinceId());//省ID
            send.setProvince(wtOrders.getProvince());//省
            send.setCityId( wtOrders.getCityId());//市ID
            send.setCity(wtOrders.getCity());///市
            send.setAreaId(wtOrders.getAreaId());//区ID
            send.setArea(wtOrders.getArea());//区
            send.setAddress( wtOrders.getAddress());//详细地址
            send.setUserId( wtOrders.getUserId());//用户（客户）ID
            send.setContacts(wtOrders.getContacts());//用户名
            send.setPhone(wtOrders.getPhone());//电话
            send.setAppointmentTime(wtOrders.getAppointmentTime());//预约派送时间
            send.setSendTime(null);//实际派送时间
            send.setSendUser(null);// 派送人
            send.setSigenUser(null);// 签收人
            send.setStatus(0);// 状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】
            send.setChangeSendNo(null);// 改派后单号
            send.setRemarks(wtOrders.getRemarks());//留言
            send.setCreateTime(System.currentTimeMillis());//创建时间
            send.setCreateId( wtOrders.getUserId());//创建人
            send.setDelStatus(1);//删除状态 1正常、0删除
            send.setMoney(zNum);//派单金额
            send.setUpdateTime(System.currentTimeMillis());
            send.setUpdateId( wtOrders.getUserId());
            send.setWaterstoreTel(waterInfo.getTel());
            sendList.add(send);
            //用户水票表
            if(wtOrders.getIsSetmeal()==1){//1有套餐 0 没有
                log.info("添加水票日志表  tickLogLost====" +   JSONObject.toJSONString(tickLogLost));
                dao.insertTickLogLost(tickLogLost);//水票日志表
            }
            if(sendMesList.size()>0){
            log.info("派单表   sendList========"+JSONObject.toJSONString(sendList) );
             dao.insertSendList(sendList);   //派单表
            log.info("派单详情表  sendMesList======="+ JSONObject.toJSONString(sendMesList));
             dao.insertSendMesList(sendMesList);//派单详情表
            }
        //推送消息设置
        log.info("推送消息设置.................................");
            try {
                JSONObject json2 = new JSONObject();
                json2.put("receiveId", waterInfo.getBranchesId());//接受的分支机构id
                json2.put("pushId", waterInfo.getBranchesId());//推送的分支机构ID
                json2.put("title", "新的订单");//消息题目
                json2.put("content", "您有新的订单，请注意查收！");//内容
                tuisong(json2);
            }catch (Exception e){
                log.error("推送消息异常 ",e);
                e.printStackTrace();
            }
            mapsResult.put("result","1");
            mapsResult.put("message","成功");
            return mapsResult;
        }
    }

    /**
     * 推送接口
     * @param json
     * @return
     * @throws Exception
     */
    public Map tuisong(JSONObject json) throws Exception {
        json.put("typeCode","utf-8");//编码
        json.put("thenType","1");//0.为定时发送 1.立即发送
        json.put("type","1");//1:通知消息 2:系统消息
        json.put("sourceName","开放平台");//	开放平台
        json.put("batchId", UUID.randomUUID().toString());//	批次号(batchId = UUID.randomUUID().toString())
        json.put("userIds", "");//	接受分支机构你的下的所有用户ID
        DataMessage abcd = pushMessageDubboAPIService.buildPushObjectWithAlias(json.toJSONString());
        log.info("abcd==============="+abcd.getData());
        log.info("abcd==============="+abcd.getMessage());
        log.info("abcd==============="+abcd.getResult());
        Map map = new HashMap();
        map.put("data",abcd.getData());
        map.put("Message",abcd.getMessage());
        map.put("Result",abcd.getResult());
        return map;
    }

    public static Goods mapToGoods(Map map){
        Goods g = new Goods();
        g.setId(Long.parseLong(map.get("id")+""));
        g.setBranchesId(Long.parseLong(map.get("branches_id")+""));
        g.setPId(Long.parseLong(map.get("p_id")+""));
        g.setGoodsCode(map.get("goods_code")+"");
        g.setBrandId(Long.parseLong(map.get("brand_id")+""));
        g.setSkuName(map.get("sku_name")+"");
        g.setSkuCode(map.get("sku_code")+"");
        g.setGoodsBar(map.get("goods_bar")+"");
        g.setTypeCode(map.get("type_code")+"");
        g.setGoodsSpec(map.get("goods_spec")+"");
//        g.setGoodsWeight(new BigDecimal(map.get("goods_weight")+""));
//        g.setGoodsUtil(Integer.parseInt(map.get("goods_util")+""));
        g.setGoodsSize(map.get("goods_size")+"");
        g.setGoodsColor(map.get("goods_color")+"");
        g.setStatus(Integer.parseInt(map.get("status")+""));
        g.setCostPrice(Long.parseLong(map.get("cost_price")+""));
        g.setPrice(Long.parseLong(map.get("price")+""));
//        g.setOnSales(Integer.parseInt(map.get("on_sales")+""));
        g.setSellPrice(Long.parseLong(map.get("sell_price")+""));
        g.setShelfOnTime(Long.parseLong(map.get("shelf_on_time")+""));
        g.setShellOffReason(map.get("shell_off_reason")+"");
//        g.setCreateTime(Long.parseLong(map.get("create_time")+""));
//        g.setUpdateTime(Long.parseLong(map.get("update_time")+""));
        g.setDelState(Integer.parseInt(map.get("del_state")+""));
//        g.setVersion(Long.parseLong(map.get("version")+""));
        g.setGoodsName(map.get("goods_name")+"");
        g.setGoodsPic(map.get("goods_pic")+"");
        g.setBrandName(map.get("name")+"");
        return g;
    }

    /**
     * 支付 钱
     * @param
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Map<String,Object> getPayment(JSONObject json) throws Exception {
        Map map = new HashMap();
        Map<String,Object> payMoney = dao.queryPrices(json.getString("orderNo"));
        if(Long.parseLong(payMoney.get("orderState")+"") == 3){
            log.info("订单已付款");
            throw new RuntimeException("订单已付款");
        }else {
            Account account = new Account();
            account.setAccount(accounts);//账号
            account.setSecret(password);//密匙
            IncomeOrder incomeOrder = new IncomeOrder();
            incomeOrder.setCallbackUrl(callbackUrl);//回调地址 我的
            log.info("回调地址==============="+callbackUrl);
            //http://192.168.101.219:10000/wtorder/api/order/onLinePayCallBack
            incomeOrder.setPurpose("订单结算 订单id=" + json.getString("orderNo"));//用途
            incomeOrder.setRemark(" 订单id=" + json.getString("orderNo"));//说明
            incomeOrder.setSource(1);//1:微信 2:支付宝 3银行卡
            incomeOrder.setTradeType("JSAPI");//交易方式 公众号支付
            incomeOrder.setPayMoney(Long.parseLong(String.valueOf(payMoney.get("paymentMoney"))));// 支付金额 分
            incomeOrder.setSpbillCreateIp("127.0.0.1");//
            incomeOrder.setOpenid(json.getString("openId"));
            incomeOrder.setAccountOrderCode(json.getString("orderNo"));//账户的订单号1
            try {
                System.out.println(gtPayService);
                com.gt.gtpay.entity.base.DataMessage dm = this.gtPayService.income(account,incomeOrder);
                JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(dm.getData()));
                String orderCode = jo.getString("orderCode");//gtpay生成的订单号
                log.info("jo==="+jo.toString());
                JSONObject wechatPayJson=jo.getJSONObject("datamessage");
                if(wechatPayJson!=null&&wechatPayJson.getInteger("result")==0)
                {
                    JSONObject payInfoJson=wechatPayJson.getJSONObject("data");
                    System.out.println("payInfoJson======================"+payInfoJson);
                    map.put("appId",payInfoJson.getString("appId"));
                    map.put("needPay",payInfoJson.getString("needPay"));
                    map.put("nonceStr",payInfoJson.getString("nonceStr"));
                    map.put("package",payInfoJson.getString("package"));
                    map.put("sign",payInfoJson.getString("sign"));
                    map.put("signType",payInfoJson.getString("signType"));
                    map.put("timeStamp",payInfoJson.getString("timeStamp"));
                }
                map.put("message",wechatPayJson.getString("message"));
                Map newMap = new HashMap();
                newMap.put("orderNo",json.get("orderNo"));
                newMap.put("updateTime",System.currentTimeMillis());
                newMap.put("paymentCode",orderCode);
                dao.updateOrders(newMap);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                log.error("调用支付中心接口错误");
                throw new RuntimeException("调用支付中心接口错误");
            }
            //String paymentOrderCode = weChatPayment();
            return map;
        }
    }

    /**
     *  支付消耗水票
     * @return
     * @throws Exception
     */
    @Transactional
    public Long payWaterU(JSONObject json){
        Map mapOrder = new HashMap();
        mapOrder.put("orderNo",json.get("orderNo"));
        WtOrder listOrder = dao.selectByOrders(mapOrder);
        //获取订单详情
        int qq = 0;
        List<WtOrderMes>  listOrderMes= listOrder.getWtOrderMes();
        for (int i = 0; i < listOrderMes.size(); i++) {
            if (listOrderMes.get(i).getType() == 2) {
                qq = 1;
            }
            if (listOrderMes.get(i).getpType() == 2 && listOrderMes.get(i).getpType() == 1) {
                HashMap <String, Object> hash = new HashMap <String, Object>();
                hash.put("skuCode", listOrderMes.get(i).getSkuCode());
                hash.put("userId", listOrder.getUserId());
                List <Map <String, Object>> listMap = dao.queryWaterStatus(hash);
                int values = 0;
                int nums = listOrderMes.get(i).getNum();
                for (int e = 0; e < listMap.size(); e++) {
                    int surplusNum = Integer.parseInt(listMap.get(e).get("surplusNum").toString());
                    values = nums - surplusNum;
                    HashMap <String, Object> has = new HashMap <String, Object>();
                    has.put("userId", listMap.get(e).get("userId"));
                    has.put("skuCode", listMap.get(e).get("skuCode"));
                    has.put("orderCode", listMap.get(e).get("orderCode"));
                    has.put("createTime", listMap.get(e).get("createTime"));
                    has.put("updateTime", System.currentTimeMillis());
                    WtTicketLog wtlog = ticketLog(
                            Long.valueOf(String.valueOf(listMap.get(e).get("id"))),//水票ID
                            listOrderMes.get(i).getSkuName(),//SKU名称（商品名称+规格）
                            listOrderMes.get(i).getSkuCode(),//SKU编号
                            values, //数量
                            null,
                            1,//订单/派送单
                            -1,  //增/减 【1增、-1减】
                            json.getLong("userId"),//用户(客户)ID
                            json.getString("contacts"),//用户（客户）名称
                            json.getString("address"),//地址
                            null,//派送单明细code
                            listOrderMes.get(i).getOrderMesNo(),//订单明细code
                            System.currentTimeMillis()//消费时间
                    );
                    if (values < 0) {
                        has.put("surplusNum", 0);
                        Long longTicket = dao.updateUserTicket(has);
                        if (longTicket < 1) {
                            log.error("用户水票修改有误");
                            throw new RuntimeException("用户水票修改有误");
                        }
                        Long longTick = dao2.insertLog(wtlog);
                        if (longTick < 1) {
                            log.error("水票消费记录表修修改有误");
                            throw new RuntimeException("水票消费记录表修改有误");
                        }
                        nums = Math.abs(values);
                    } else if (values > 0 || values == 0) {
                        has.put("surplusNum", values);
                        Long longTicket = dao.updateUserTicket(has);
                        if (longTicket < 1) {
                            log.error("用户水票修改有误");
                            throw new RuntimeException("用户水票修改有误");
                        }
                        Long longTick = dao2.insertLog(wtlog);
                        if (longTick < 1) {
                            log.error("水票消费记录表添加有误");
                            throw new RuntimeException("水票消费记录表添加有误");
                        }
                        break;
                    }
                }
            }
        }
        HashMap <String, Object> mapo = new HashMap <String, Object>();
        mapo.put("orderNo", json.getString("orderNo"));
        mapo.put("orderState", 3);
        Long longOrder = dao.updateOrder(mapo);
        if (longOrder < 1) {
            log.error("订单状态修改有误");
            throw new RuntimeException("订单状态修改有误");
        }
        return  longOrder;
    }


    /**
     *  添加用户水票和 水票消费记录表
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public String insertUserTicket(JSONObject json) throws Exception {
        HashMap<String,Object> hashMaps = new HashMap<String ,Object>();
        hashMaps.put("orderState",3);
        hashMaps.put("paymentTime",System.currentTimeMillis());
        hashMaps.put("orderNo",json.getString("orderNo"));
        dao.updateOrder(hashMaps);

        WtOrder wOrder = dao.selectOrderList(hashMaps);
        List<Map<String, Object>> listMap = dao.queryOrderMes(hashMaps);

        for(int i = 0;i < listMap.size();i++){
            HashMap<String,Object> hashMaps1 = new HashMap<String ,Object>();
            HashMap<String,Object> hashMaps2 = new HashMap<String ,Object>();
            HashMap<String,Object> hashMaps4 = new HashMap<String ,Object>();
            if("1".equals(String.valueOf(listMap.get(i).get("pType")))){
                hashMaps4.put("seriesSkuCode",listMap.get(i).get("skuCode"));
                hashMaps4.put("waterstoreId",wOrder.getWaterstoreId());
                //查询套系数量和赠品数量
                Map<String, Object>  setmealMap = dao.querySetmealNum(hashMaps4);
                hashMaps1.put("orderCode",wOrder.getOrderNo());
                hashMaps1.put("skuCode",listMap.get(i).get("skuCode"));
                HashMap<String,Object> hashMaps3 = dao.queryOneUserId(hashMaps1);
                int numss = Integer.parseInt(String.valueOf(setmealMap.get("taoxinum")));//主商品的数量
                if("1".equals(String.valueOf(setmealMap.get("type"))) ){
                    // 赠品的 skucode 和 商品的skuCODE 是否相等  相等  水票表里加一条记录
                    if(  setmealMap.get("skuCode")==listMap.get(i).get("skuCode")){//
                        numss += Integer.parseInt(String.valueOf(setmealMap.get("num")))*Integer.parseInt(String.valueOf(listMap.get(i).get("num")));
                    }else{
                        HashMap<String,Object> hashMaps5 = new HashMap<String ,Object>();
                        hashMaps5.put("userId", wOrder.getUserId());
                        hashMaps5.put("num",numss*Integer.parseInt(String.valueOf(listMap.get(i).get("num"))));
                        hashMaps5.put("surplusNum",numss*Integer.parseInt(String.valueOf(listMap.get(i).get("num"))));
                        hashMaps5.put("ticketPrice", setmealMap.get("price"));
                        hashMaps5.put("skuCode",setmealMap.get("skuCode"));
                        hashMaps5.put("orderCode",wOrder.getOrderNo());
                        hashMaps5.put("orderTime",wOrder.getOrderTime());
                        hashMaps5.put("updateTime",System.currentTimeMillis());
                        hashMaps5.put("createTime",System.currentTimeMillis());
                        hashMaps5.put("createId",listMap.get(i).get("createId"));
                        hashMaps5.put("delState",1);
                        Long log1 = dao.insertUserTicket(hashMaps5);
                        if ( log1<1) {
                            log.error("用户水票添加有误");
                            throw new RuntimeException("用户水票添加有误");
                        }
                        HashMap<String,Object> hashMaps6 = dao.queryOneUserId(hashMaps1);
                        hashMaps6.put("ticketId",hashMaps3.get("id"));
                        hashMaps6.put("skuName",setmealMap.get("skuName"));
                        hashMaps6.put("skuCode",setmealMap.get("skuCode"));
                        hashMaps6.put("num",numss*Integer.parseInt(String.valueOf(listMap.get(i).get("num"))));
                        hashMaps6.put("type",1);
                        hashMaps6.put("ticketPrice", setmealMap.get("price"));
                        hashMaps6.put("address",wOrder.getAddress());
                        hashMaps6.put("sendMesCode",null);
                        hashMaps6.put("orderMesCode",listMap.get(i).get("orderMesCode"));
                        hashMaps6.put("logTime",null);
                        hashMaps6.put("createTime",System.currentTimeMillis());
                        hashMaps6.put("createTd",listMap.get(i).get("createId"));
                        hashMaps6.put("delState",1);
                        Long log2 = dao.insertTicketLog(hashMaps6);
                        if ( log2<1) {
                            log.error("水票消费记录表");
                            throw new RuntimeException("水票消费记录表");
                        }
                    }
                }
                numss = numss*Integer.parseInt(String.valueOf(listMap.get(i).get("num")));
                //用户水票表
                hashMaps1.put("userId", wOrder.getUserId());
                hashMaps1.put("num",numss);
                hashMaps1.put("surplusNum",numss);
                hashMaps1.put("ticketPrice",listMap.get(i).get("price"));
                hashMaps1.put("skuCode",listMap.get(i).get("skuCode"));
                hashMaps1.put("orderCode",wOrder.getOrderNo());
                hashMaps1.put("orderTime",wOrder.getOrderTime());
                hashMaps1.put("updateTime",System.currentTimeMillis());
                hashMaps1.put("createTime",System.currentTimeMillis());
                hashMaps1.put("createId",listMap.get(i).get("createId"));
                hashMaps1.put("delState",1);
                Long log1 = dao.insertUserTicket(hashMaps1);
                if ( log1<1) {
                    log.error("用户水票添加有误");
                    throw new RuntimeException("用户水票添加有误");
                }
                //水票消费记录
                hashMaps2.put("ticketId",hashMaps3.get("id"));
                hashMaps2.put("skuName",listMap.get(i).get("skuName"));
                hashMaps2.put("skuCode",listMap.get(i).get("skuCode"));
                hashMaps2.put("num",numss);
                hashMaps2.put("type",1);
                hashMaps2.put("ticketPrice",listMap.get(i).get("price"));
                hashMaps2.put("address",wOrder.getAddress());
                hashMaps2.put("sendMesCode",null);
                hashMaps2.put("orderMesCode",listMap.get(i).get("orderMesCode"));
                hashMaps2.put("logTime",null);
                hashMaps2.put("createTime",System.currentTimeMillis());
                hashMaps2.put("createTd",listMap.get(i).get("createId"));
                hashMaps2.put("delState",1);
                Long log2 = dao.insertTicketLog(hashMaps2);
                if ( log2<1) {
                    log.error("水票消费记录表");
                    throw new RuntimeException("水票消费记录表");
                }
            }

        }
        return json.getString("orderNo");
    }
    /**
     *  修改订单状态 归还水票
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Map <String, Object> cancellationOrder(String orderNo) throws Exception {
        Map<String,Object> map = new  HashMap<String,Object>();
       if("".equals(orderNo)){
           map.put("SUCCESS","订单已取消");
           return map;
       }
        HashMap wts = new HashMap();
        wts.put("orderNo",orderNo);
        WtOrder orders= dao.selectOrderList(wts);
        if(orders.getOrderState() == -1){
            map.put("erroy","订单编号为空");
            return map;
        }
        Map wt = new HashMap();
        wt.put("orderNo",orderNo);
        wt.put("orderState",-1);
        wt.put("updateTime",System.currentTimeMillis());
        dao.updateOrders(wt);
        log.error("修改超时订单");
        HashMap map2 = new HashMap();
        map2.put("orderNo",orderNo);
        List<Map<String,Object>>  orderMes = dao.queryOrderMes(map2);
        if(orderMes != null || !orderMes.isEmpty()){
            for (int i = 0; i < orderMes.size(); i++) {
                WtTicketLog tk = new WtTicketLog();
                tk.setOrderMesCode(orderMes.get(i).get("orderMesNo")+"");
                tk.setOperation(-1);
                //通过订单详情编号查询水票消费日志
                List<WtTicketLog> tickLog = tickLogDao.selectOneXiaoFei(tk);
                if(tickLog.isEmpty()){
                    //空跳过这个循环
                    continue;
                }
                log.error("通过订单详情编号和状态 -1 查询水票消费记录");
                for(int j=0;j<tickLog.size();j++){
                    //插入水票日志
                    tickLog.get(j).setId(null);
                    tickLog.get(j).setOperation(1);
                    tickLog.get(j).setCreateTime(System.currentTimeMillis());
                    tickLogDao.insert(tickLog.get(j));
                    log.error("添加状态为1的税票销号记录 ");
                    //修改回填水票数量
                    WtUserTicket userTick = new WtUserTicket();
                    userTick.setUpdateTime(System.currentTimeMillis());
                    userTick.setId(tickLog.get(j).getTicketId());
                    WtUserTicket ut = dao2.selectById(tickLog.get(j).getTicketId());
                    log.error("通过水票ID 查询剩余水票数量");
                    userTick.setSurplusNum(tickLog.get(j).getNum()+ut.getSurplusNum());
                    dao2.updateUserTick(userTick);
                    log.error("通过水票ID 修改回填水票");
                }

            }
        }
        map.put("SUCCESS","取消成功");
        return map;
    }

    /**
     * 定时任务 5分钟执行一次 >30分钟 未付款就退水票
     */
    @Scheduled(cron = "0 0/5 * * * *")
    @Transactional
    public void scheduled1() {
        List<Map<String,Object>>  timeOut = dao.queryTimeOut();
        if(!timeOut.isEmpty()){
            for (int i = 0; i< timeOut.size();i++){
                Long times = Long.valueOf(String.valueOf(timeOut.get(i).get("orderTime")));
                if(System.currentTimeMillis() - times  >= 1800*1000){
                    log.error("有超时订单");
                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("orderMesCode",timeOut.get(i).get("orderMesNo"));
                    Map wt = new HashMap();
                    wt.put("orderNo",timeOut.get(i).get("orderNo"));
                    wt.put("orderState",-1);
                    wt.put("updateTime",System.currentTimeMillis());
                    dao.updateOrders(wt);
                    log.error("修改超时订单");
                    //添加水票消费记录
                    //通过详情编号查询水票消耗日志的消耗记录 再通过水票ID修改回去
                    WtTicketLog tk = new WtTicketLog();
                    tk.setOrderMesCode(timeOut.get(i).get("orderMesNo")+"");
                    tk.setOperation(-1);
                    List<WtTicketLog> tickLog = tickLogDao.selectOneXiaoFei(tk);
                    if(tickLog.isEmpty()){
                        //空跳过这个循环
                        continue;
                    }
                    log.error("通过订单详情编号和状态 -1 查询水票消费记录");
                    for(int j=0;j<tickLog.size();j++){
                        //插入水票日志
                        tickLog.get(j).setId(null);
                        tickLog.get(j).setOperation(1);
                        tickLog.get(j).setCreateTime(System.currentTimeMillis());
                        tickLogDao.insert(tickLog.get(j));
                        log.error("添加状态为1的税票销号记录");
                        //修改回填水票数量
                        WtUserTicket userTick = new WtUserTicket();
                        userTick.setUpdateTime(System.currentTimeMillis());
                        userTick.setId(tickLog.get(j).getTicketId());
                        WtUserTicket ut = dao2.selectById(tickLog.get(j).getTicketId());
                        log.error("通过水票ID 查询剩余水票数量");
                        userTick.setSurplusNum(tickLog.get(j).getNum()+ut.getSurplusNum());
                        dao2.updateUserTick(userTick);
                        log.error("通过水票ID 修改回填水票");
                    }
                }
            }
        }
    }


    /**
     * 用户水票
     * @return
     */
    public WtUserTicket userTicket(Long userId,int num,int surplusNum,Long price,String skuCode,String orderNo,Long orderTime,Long branchesId)
    {
        WtUserTicket wtu = new WtUserTicket();
        wtu.setUserId(userId);//用户ID
        wtu.setNum(num);//水票数量
        wtu.setSurplusNum(surplusNum);//水票余额数量
        wtu.setTicketPrice(price);//水票单价
        wtu.setSkuCode(skuCode);//SkuID
        wtu.setOrderCode(orderNo);//订单编号
        wtu.setOrderTime(orderTime);//订单时间
        wtu.setUpdateTime(System.currentTimeMillis());//修改时间
        wtu.setCreateTime(System.currentTimeMillis());//创建时间
        wtu.setCreateId(userId);//创建人
        wtu.setDelState(1);//删除状态 1正常、0删除
        wtu.setBranchesId(branchesId);//机构id
    return wtu;
    }


    /**
     * 水票日志
     * @return
     */
    public WtTicketLog ticketLog(Long TicketId,String skuName,String skuCode,int num,Long ticketPrice,int type,Integer operation,Long userId,String userName,String address,String sendMesCode,String orderMesNo,Long longTime){
        WtTicketLog wtlog = new WtTicketLog();
        wtlog.setTicketId(TicketId);//水票ID
        wtlog.setSkuName(skuName); //SKU名称（商品名称+规格）
        wtlog.setSkuCode(skuCode);   //SKU编号
        wtlog.setNum(num);   //数量
        wtlog.setTicketPrice(ticketPrice);//价格
        wtlog.setType(type);   //订单/派送单
        wtlog.setOperation(operation);   //增/减 【1增、-1减】
        wtlog.setUserId(userId);   //用户(客户)ID
        wtlog.setUserName(userName);   //用户（客户）名称
        wtlog.setAddress(address);   //地址
        wtlog.setSendMesCode(sendMesCode);   //派送单明细code
        wtlog.setOrderMesCode(orderMesNo);   //订单明细code
        wtlog.setLogTime(longTime);   //消费时间
        wtlog.setCreateTime(System.currentTimeMillis());   //创建时间
        wtlog.setCreateId(userId);//创建人
        wtlog.setDelState(1); //删除状态 1正常、0删除'
        return wtlog;
    }
    /**
     * 订单详情
     * @return
     */
    public WtOrderMes rderMes(String orderno,String orderMesNo,String skuCode,String skuName,int type,int pType,Long price,int num,Long totalPrice,String sequence, Long createId){
        WtOrderMes wtMes = new WtOrderMes();
        wtMes.setOrderNo(orderno); //订单编号
        wtMes.setOrderMesNo(orderMesNo);//订单详情编号
        wtMes.setSkuCode( skuCode);//SKU编号
        wtMes.setSkuName(skuName);//SKU名称（商品名称+规格）
        wtMes.setType(type);//支付方式1水票|| 2现金
        wtMes.setpType(pType);//商品类型（1水票|| 2桶水）
        wtMes.setPrice(price);//售价
        wtMes.setNum(num); //数量
        wtMes.setTotalPrice(totalPrice);
        wtMes.setSequence(sequence);//商品序列
        wtMes.setCreateTime(System.currentTimeMillis());//创建时间
        wtMes.setCreateId(createId);//创建人
        wtMes.setDelState(1);//删除状态 1正常、0删除
        return wtMes;
    }
    /**
     * 订单
     * @return
     */
    public WtOrder order(String orderNo,Long waterstoreId,Long userId,String contacts,String phone,String provinceId,String province,String cityId,String city,String areaId, String area, String address, Long money, String remarks, int isInvoice, int isSetmeal, int isTicket, Long ticketMoney, Long paymentMoney, int paymentType, int orderState, Long paymentTime, Long extensioncodeId, Long createId, Long updateTime, Long appointmentTime){
        WtOrder wt = new WtOrder();
        wt.setOrderNo(orderNo);//订单编号
        wt.setWaterstoreId(waterstoreId);//水站ID
        wt.setUserId(userId);//用户（客户）ID
        wt.setContacts(contacts);//用户名
        wt.setPhone(phone);//电话
        wt.setProvinceId(provinceId);//省ID
        wt.setProvince(province);//省
        wt.setCityId(cityId);//市ID
        wt.setCity(city);///市
        wt.setAreaId(areaId);//区ID
        wt.setArea(area);//区
        wt.setAddress(address);//详细地址
        wt.setMoney(money);//订单金额
        wt.setRemarks(remarks);//买家留言
        wt.setIsInvoice(isInvoice);//是否发票
        wt.setIsSetmeal(isSetmeal);//是否有套餐
        wt.setIsTicket(isTicket);//是否水票抵扣
        wt.setTicketMoney(ticketMoney);//水票抵扣金额
        wt.setPaymentMoney(paymentMoney);//付款金额
        wt.setPaymentType(paymentType);//付款方式【1微信】
        wt.setOrderState(orderState);//订单状态【1订单确认（代付款）、3完成、-1取消】
        wt.setOrderTime(System.currentTimeMillis());//下单时间
        wt.setPaymentTime(paymentTime);//付款时间
        wt.setExtensioncodeId(extensioncodeId);//推广码ID
        wt.setCreateTime(System.currentTimeMillis());//创建时间
        wt.setCreateId(createId);//创建人
        wt.setDelState(1);//删除状态 1正常、0删除
        wt.setUpdateTime(updateTime);//修改时间
        wt.setAppointmentTime(appointmentTime);//预约派送时间
        return wt;
    }
        /**
         * 派单
         * @return
         */
        public WtSend send(String sendNo,Long waterstoreId,String provinceId,String province,String cityId,String city,String areaId, String area, String address, Long userId,String contacts,String phone,Long appointmentTime, Long sendTime,String sendUser,String sigenUser, int status,String changeSendNo, String remarks, Long money,Long updateId){
            WtSend wt = new WtSend();
            wt.setSendNo(sendNo);//订单编号
            wt.setWaterstoreId(waterstoreId);//水站ID
            wt.setProvinceId(provinceId);//省ID
            wt.setProvince(province);//省
            wt.setCityId(cityId);//市ID
            wt.setCity(city);///市
            wt.setAreaId(areaId);//区ID
            wt.setArea(area);//区
            wt.setAddress(address);//详细地址
            wt.setUserId(userId);//用户（客户）ID
            wt.setContacts(contacts);//用户名
            wt.setPhone(phone);//电话
            wt.setAppointmentTime(appointmentTime);//预约派送时间
            wt.setSendTime(sendTime);//实际派送时间
            wt.setSendUser(sendUser);// 派送人
            wt.setSigenUser(sigenUser);// 签收人
            wt.setStatus(status);// 状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】
            wt.setChangeSendNo(changeSendNo);// 改派后单号
            wt.setRemarks(remarks);//留言
            wt.setCreateTime(System.currentTimeMillis());//创建时间
            wt.setCreateId(userId);//创建人
            wt.setDelStatus(1);//删除状态 1正常、0删除
            wt.setMoney(money);//订单金额
            wt.setUpdateTime(System.currentTimeMillis());
            wt.setUpdateId(updateId);
            return wt;
        }
    /**
     * 派单详情
     * @return
     */
    public WtSendMes sendMes(String sendCode,String sendMesCode,String skuCode,String skuName,int num,int deliveryNum,String orderMesCode,int type,String sequence,Long userId,String goodsSpec,String brandName){
        WtSendMes wt = new WtSendMes();
        wt.setSendCode(sendCode);// 派送单编号
        wt.setSendMesCode(sendMesCode);// 派送单详情编号
        wt.setSendCode(skuCode);// SKU编号
        wt.setSkuName(skuName);// SKU名称
        wt.setNum(num);// 数量
        wt.setDeliveryNum(deliveryNum);//实际送达数量
        wt.setOrderMesCode(orderMesCode);// 订单明细编号
        wt.setType(type);// 1水票|| 2桶水
        wt.setSequence(sequence);// 商品序列
        wt.setCreateTime(System.currentTimeMillis());//创建时间
        wt.setCreateId(userId);//创建人
        wt.setDelState(1);// 删除状态 1正常、0删除
        wt.setGoodsSpec(goodsSpec);//商品规格
        wt.setBrandName(brandName);//商品品牌
        return wt;
    }

    /**
     *  通过订单编号查询订单
     * @return
     * @throws Exception
     */
    @Override
    public WtOrder  queryOrderNos(String orderNo) {
        Map map = new HashMap();
        map.put("orderNo",orderNo);
        WtOrder wtOrder= dao.selectByOrders(map);
        if(wtOrder !=null ){
            Map oId = dao.selsectOpenId(String.valueOf(wtOrder.getUserId()));
            if(oId !=null && !oId.isEmpty()){
                wtOrder.setOpenId(oId.get("openId")+"");
            }
            List<WtOrderMes> listD2 =  wtOrder.getWtOrderMes();
            if(listD2 !=null && !listD2.isEmpty()){
                for(int j=0 ;j<listD2.size();j++){
                    listD2.get(j).setSequence(listD2.get(j).getSequence().replaceAll("\\\\",""));
                }
                List<WtOrderMes>   countList = new ArrayList<WtOrderMes>();// 用于存放最后的结果
                for (int i = 0; i < listD2.size(); i++) {
                    String gsId = listD2.get(i).getSkuCode();
                    int flag = 0;// 0为新增数据，1为增加count
                    for (int j = 0; j < countList.size(); j++) {
                        String gsId_ = countList.get(j).getSkuCode();
                        if (gsId.equals(gsId_)) {
                            int sum = listD2.get(i).getNum() + countList.get(j).getNum();
                            Long price = listD2.get(i).getPrice() + countList.get(j).getPrice();
                            countList.get(j).setPrice(price);
                            countList.get(j).setNum(sum);
                            flag = 1;
                            continue;
                        }
                    }
                    if (flag == 0) {
                        countList.add(listD2.get(i));
                    }
                }
                 wtOrder.setWtOrderMes(countList);
                log.info("wtOrder=============="+wtOrder);
             }
        }

        return wtOrder;
    }
 }
