package com.gt.manager.order.service.wtSend.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.entity.base.DataMessage;
import com.gt.gtop.service.dubbo.gtpush.IPushMessageDubboAPIService;
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
import com.gt.manager.order.service.wtSend.WtSendService;
import com.gt.manager.util.numberCreate.NumberCreateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import net.sf.json.JSONObject;


@Service
public class WtSendServiceImpl implements WtSendService {

    private static Logger log = Logger.getLogger(WtSendServiceImpl.class);

    @Autowired
    private WtSend2DAO dao; //派单
    @Autowired
    private WtOrder2DAO orderDao;//订单

    @Autowired
    private WtUserTicketDAO ticketDao;//水票

    @Autowired
    private WtTicketLogDAO tickLogDao;//水票日志表

    @Autowired
    private WtWaterstoreDAO waterDao;//水站信息表

    @Reference
    IPushMessageDubboAPIService pushMessageDubboAPIService;

    @Override
    public List <Map <String, Object>> querySend(JSONObject json) {

        return null;
    }

    @Override
    public List <Map <String, Object>> querySendMes(JSONObject json) {

        return null;
    }

    /**
     * 添加添加派单 与 派单详情
     */
    @Override
    @Transactional
    public Map insert(JSONObject json) throws Exception {
        String sendNo = NumberCreateUtil.makeOrderNum(3,null);  //派单编号
        HashMap<String,Object> hashMap = new HashMap <String,Object>();
        hashMap.put("orderNo",json.getString("orderNo"));
        hashMap.put("orderState",3);
        Long updateOrder = orderDao.updateOrder(hashMap);
        if ( updateOrder<1) {
            log.error("订单状态修改有误");
            throw new RuntimeException("订单状态修改有误");
        }
        List<HashMap<String,Object>> OrderMes = dao.selectOrderMes(hashMap); //
        for(int i = 0;i<OrderMes.size();i++){
            WtSend wt = new WtSend();
            wt.setSendNo(sendNo);//派送单编号
            wt.setWaterstoreId(Long.valueOf(String.valueOf(OrderMes.get(i).get("waterstoreId"))));//水站ID
            wt.setProvinceId(String.valueOf(OrderMes.get(i).get("provinceId")));//省ID
            wt.setProvince(String.valueOf(OrderMes.get(i).get("province")));//省
            wt.setCityId(String.valueOf(OrderMes.get(i).get("cityId")));//市ID
            wt.setCity(String.valueOf(OrderMes.get(i).get("city")));//市
            wt.setAreaId(String.valueOf(OrderMes.get(i).get("areaId")));//区ID
            wt.setArea(String.valueOf(OrderMes.get(i).get("area")));//区
            wt.setAddress(String.valueOf(OrderMes.get(i).get("address")));//区
            wt.setUserId(Long.valueOf(String.valueOf(OrderMes.get(i).get("userId"))));//用户（客户）ID
            wt.setContacts(String.valueOf(OrderMes.get(i).get("contacts")));//联系人
            wt.setPhone(String.valueOf(OrderMes.get(i).get("phone")));//联系电话
            wt.setAppointmentTime(json.getLong("appointmentTime"));//预约时间
            wt.setMoney(json.getLong("money"));//派单金额
            wt.setSendTime(null);//实际派送时间
            wt.setSendUser(null);//派送人
            wt.setSigenUser(null);//签收人
            wt.setStatus(0);//状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】
            wt.setChangeSendNo(null);//改派后单号
            wt.setRemarks(String.valueOf(OrderMes.get(i).get("remarks")));//备注
            wt.setCreateTime(System.currentTimeMillis());//创建时间
            wt.setCreateId(Long.valueOf(String.valueOf(OrderMes.get(i).get("userId"))));//创建人
            wt.setDelStatus(1);//删除状态(1:未删除 0：已删除)
            wt.setUpdateTime(System.currentTimeMillis());
            if(i==0){
                //添加派送单
                Long longwts = dao.insert(wt);
                log.info("派送单3333333333333333333333"+longwts);
                if ( longwts<1) {
                    log.error("派送订单有误");
                    throw new RuntimeException("派送订单有误");
                }
            }
            Map mapPro = new HashMap();
            mapPro.put("skuCode",OrderMes.get(i).get("skuCode"));
            mapPro.put("waterstoreId",OrderMes.get(i).get("waterstoreId"));
            Map  sequences = orderDao.selectProducts(mapPro);
            log.info("派送单4444444444444444444444444444"+sequences);
            Goods gods  = mapToGoods(sequences);
            String sendMesCode = NumberCreateUtil.makeOrderNum(4,sendNo);  //派单编号
            log.info("sendMesCode======================="+sendMesCode);
                WtSendMes wts = new WtSendMes();
                wts.setSendCode(sendNo);//派送单编号
                wts.setSendMesCode(sendMesCode);//派送单详情编号
                wts.setSkuCode(String.valueOf(OrderMes.get(i).get("skuCode")));//SKU编号
                wts.setSkuName(String.valueOf(OrderMes.get(i).get("skuName")));//SKU名称（商品名称+规格）
                wts.setNum(Integer.parseInt(String.valueOf(OrderMes.get(i).get("num"))));//数量
                wts.setDeliveryNum(Integer.parseInt(String.valueOf(OrderMes.get(i).get("num"))));//实际送达数量（默认和数量相同）
                //用SKU编号 和 订单编号查询 如果 sku==21 则为混合支付
                HashMap<String,Object> has = new HashMap <String,Object>();
                    has.put("orderNo",json.getString("orderNo"));
                    has.put("skuCode",OrderMes.get(i).get("skuCode"));
                log.info("派送单5555555555555555555555555555555555");
                Map<String,Object> skuCodeNum = orderDao.querySkuCodeNum(has);
                if( Integer.parseInt(skuCodeNum.get("skuCode").toString())==2){
                    wts.setType(3);//3混合支付
                }else {
                    wts.setType(Integer.parseInt(String.valueOf(OrderMes.get(i).get("type"))));
                }
                 log.info("派送单666666666666666666666666");
                wts.setOrderMesCode(String.valueOf(OrderMes.get(i).get("orderMesCode")));//订单详情CODE
                // 在水票消费表里添加派单详情code
                if( "1".equals(String.valueOf(skuCodeNum.get("pType")))){
                    HashMap<String,Object> ha = new HashMap <String,Object>();
                    ha.put("orderMesCode",String.valueOf(OrderMes.get(i).get("orderMesNo")));
                    ha.put("sendMesCode",sendMesCode);
                    Long ticketLogMes = orderDao.updateticketLogMes(ha);
                    if ( ticketLogMes<1) {
                        log.error("水票消费记录表派单CODE修改有误");
                        throw new RuntimeException("水票消费记录表派单CODE修改有误");
                    }
                }
                wts.setSequence(JSONObject.toJSONString(gods));//商品序列
                wts.setCreateTime(System.currentTimeMillis());//创建时间
                wts.setCreateId(Long.valueOf(String.valueOf(OrderMes.get(i).get("userId"))));//创建人
                wts.setDelState(1);//删除状态 1正常、0删除
                    //查询商品规格和商品名称  sku_code branches_id
                    HashMap<String,Object> h = new HashMap <String,Object>();
                    h.put("waterstoreId",Long.valueOf(String.valueOf(OrderMes.get(i).get("waterstoreId"))));
                    h.put("skuCode",String.valueOf(OrderMes.get(i).get("skuCode")));
                    log.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh==="+ h);
                     Map<String,Object>   nameSpece = orderDao.queryNameSpece(h);
                    log.info("nameSpece===================="+ nameSpece);
                wts.setGoodsSpec(String.valueOf(nameSpece.get("goodsSpec")));//商品规格
                wts.setBrandName(String.valueOf(nameSpece.get("brandName")));//品牌名称

            //添加派送单详情
            Long longwtss  = dao.insertMes(wts);
            if ( longwtss<1) {
                log.error("派送订单详情有误");
                throw new RuntimeException("派送订单详情有误");
            }
            log.info("添加派送单详情...............................");
        }
        //推送消息设置
        log.info("推送消息设置.................................");
        JSONObject json2 = new JSONObject();
        json2.put("receiveId",OrderMes.get(0).get("waterstoreId"));//接受的分支机构id
        json2.put("pushId",OrderMes.get(0).get("waterstoreId"));//推送的分支机构ID
        json.put("title","新的订单");//消息题目
        json.put("content","您有新的订单，请注意查收！");//内容
        Map longs =tuisong(json2);
        return longs;
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
     * 一键催单添加
     * @param json
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Map insertUrge(JSONObject json) throws Exception {
        HashMap<String,Object> map = new HashMap <String,Object>();
        map.put("userId",Long.valueOf(json.getString("userId")));
        map.put("userName",json.getString("userName"));
        map.put("sendId",json.getLong("sendId"));
        map.put("remarks",json.getString("remarks"));
        map.put("createTime",System.currentTimeMillis());
        map.put("createId",Long.valueOf(json.getString("userId")));
        map.put("delState",1);
        Long Urges = dao.insertUrge(map);
        if ( Urges<1) {
            log.error("一键催单添加有误");
            throw new RuntimeException("一键催单添加有误");
        }
        WtWaterstore waterInfo = waterDao.selectById(Long.valueOf(String.valueOf(json.get("waterstoreId"))));
        log.info("waterInfo=================="+waterInfo);
        if (waterInfo == null || "".equals(waterInfo)){
            log.info("waterInfo 没有水站信息==" +waterInfo);
            throw  new Exception("waterInfo 没有水站信息==" );
        }
        //推送消息设置
        JSONObject json2 = new JSONObject();
        json2.put("receiveId",waterInfo.getBranchesId());//接受的分支机构id
        json2.put("pushId",waterInfo.getBranchesId());//推送的分支机构ID
        json2.put("title","催单");//消息题目
        json2.put("content","快点呗，等的花儿都谢了！");//内容
        Map cuidanMap =tuisong(json2);
        return cuidanMap;
    }

    /**
     * 推送接口
     * @param json
     * @return
     * @throws Exception
     */
    @Override
    public Map tuisong(JSONObject json) throws Exception {
        json.put("typeCode","123");//编码
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

    /**
     * 取消派单
     */
    @Override
    public Map cancelSend(JSONObject json) {
        Map mapsResult = new HashMap();
        // 通过派单编号查询派单详情
        if(json == null || json.isEmpty()){
            mapsResult.put("result","0");
            mapsResult.put("message","取消失败");
            log.info("前台传入参数为空");
            return mapsResult;
        }
        WtSend querySend = dao.cancelSend(json.get("sendNo")+"" );
        if(querySend == null){
            mapsResult.put("result","0");
            mapsResult.put("message","取消失败");
            log.info("派单查询为空");
            return mapsResult;
        }
        if(querySend.getStatus() == -1){
            mapsResult.put("result","1");
            mapsResult.put("message","派单已取消");
            log.info("派单已取消");
            return mapsResult;
        }
        if(querySend.getStatus() == 5){
            mapsResult.put("result","0");
            mapsResult.put("message","派单已送达不能取消");
            log.info("派单已送达不能取消");
            return mapsResult;
        }
        if(querySend.getStatus() == 6){
            mapsResult.put("result","0");
            mapsResult.put("message","派单已改派不能取消");
            log.info("派单已改派不能取消");
            return mapsResult;
        }
        List<WtSendMes> querySendMes = querySend.getWtSendMes();
        if(querySendMes == null || querySendMes.isEmpty()){
            mapsResult.put("result","0");
            mapsResult.put("message","取消失败");
            log.info("派单详情查询为空");
            return mapsResult;
        }
       //修改派单状态
        WtSend wtSend= new WtSend();
        wtSend.setUpdateTime(System.currentTimeMillis());
        wtSend.setSendNo(json.get("sendNo")+"");
        wtSend.setStatus(-1);
        dao.updateSendStatus(wtSend);
        for (int i = 0; i <querySendMes.size() ; i++) {
            WtTicketLog wtTicketLog= new WtTicketLog();
            wtTicketLog.setSendMesCode(querySendMes.get(i).getSendMesCode());
            wtTicketLog.setOperation(-1);
            //通过派单编号查询水票日志表
          List<WtTicketLog> logs =  tickLogDao.selectOneXiaoFeiSend(wtTicketLog);
            if(logs == null || logs.isEmpty()){
                log.info("水票消耗记录为空"+i);
                continue;
            }
            //循环水票日志表
            for (int j = 0; j <logs.size() ; j++) {
                //添加水票日志
                logs.get(j).setOperation(1);
                logs.get(j).setId(null);
                logs.get(j).setCreateTime(System.currentTimeMillis());
                tickLogDao.insert(logs.get(j));
                //修改用户水票
                //修改回填水票数量
                WtUserTicket userTick = new WtUserTicket();
                userTick.setUpdateTime(System.currentTimeMillis());
                userTick.setId(logs.get(j).getTicketId());
                WtUserTicket ut = ticketDao.selectById(logs.get(j).getTicketId());
                log.error("通过水票ID 查询剩余水票数量");
                userTick.setSurplusNum(logs.get(j).getNum()+ut.getSurplusNum());
                ticketDao.updateUserTick(userTick);
                log.error("通过水票ID 修改回填水票");
            }
        }
        mapsResult.put("result","1");
        mapsResult.put("message","取消成功");
        log.info("取消成功");
        return mapsResult;
    }


       /**
         * 用户水票
         * @return
         */
        public WtUserTicket userTicket(Long userId, int num, int surplusNum, Long price, String skuCode, String orderNo, Long orderTime, Long createId){
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
            wtu.setCreateId(createId);//创建人
            wtu.setDelState(1);//删除状态 1正常、0删除
            return wtu;
        }


        /**
         * 水票日志
         * @return
         */
        public WtTicketLog ticketLog(Long TicketId,String skuName,String skuCode,int num,int type,Integer operation,Long userId,String userName,String address,String sendMesCode,String orderMesNo,Long longTime,Long createId){
            WtTicketLog wtlog = new WtTicketLog();
            wtlog.setTicketId(TicketId);//水票ID
            wtlog.setSkuName(skuName); //SKU名称（商品名称+规格）
            wtlog.setSkuCode(skuCode);   //SKU编号
            wtlog.setNum(num);   //数量
            wtlog.setType(type);   //订单/派送单
            wtlog.setOperation(operation);   //增/减 【1增、-1减】
            wtlog.setUserId(userId);   //用户(客户)ID
            wtlog.setUserName(userName);   //用户（客户）名称
            wtlog.setAddress(address);   //地址
            wtlog.setSendMesCode(sendMesCode);   //派送单明细code
            wtlog.setOrderMesCode(orderMesNo);   //订单明细code
            wtlog.setLogTime(longTime);   //消费时间
            wtlog.setCreateTime(System.currentTimeMillis());   //创建时间
            wtlog.setCreateId(createId);//创建人
            wtlog.setDelState(1); //删除状态 1正常、0删除'
            return wtlog;
        }
}
