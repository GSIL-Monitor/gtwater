package com.gt.manager.order.service.send;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.gt.gtop.service.dubbo.gtpush.IPushMessageDubboAPIService;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import com.gt.manager.order.entity.send.Goods;
import com.gt.manager.order.entity.send.MyTicket;
import com.gt.manager.order.repository.wtOrder.WtOrderDAO;
import com.gt.manager.order.repository.wtSend.WtSendDAO;
import com.gt.manager.order.repository.wtSendMes.WtSendMesDAO;
import com.gt.manager.order.repository.wtSku.WtSkuDAO;
import com.gt.manager.order.repository.wtTicketLog.WtTicketLogDAO;
import com.gt.manager.order.repository.wtUserTicket.WtUserTicketDAO;
import com.gt.manager.order.repository.wtWaterstore.WtWaterstoreDAO;
import com.gt.manager.order.repository.wtWaterstoreSku.WtWaterstoreSkuDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

@Service("sendService")
@Component
public class SendServiceImpl implements SendService {

    private static Logger logger = LoggerFactory.getLogger(SendServiceImpl.class);

    @Autowired
    private WtSendDAO wtSendDAO;
    @Autowired
    private WtSendMesDAO wtSendMesDAO;
    @Autowired
    private WtSkuDAO wtSkuDAO;
    @Autowired
    private WtWaterstoreSkuDAO wtWaterstoreSkuDAO;
    @Autowired
    private WtTicketLogDAO wtTicketLogDAO;
    @Autowired
    private WtUserTicketDAO wtUserTicketDAO;
    @Autowired
    private WtOrderDAO wtOrderDAO;
    @Autowired
    private WtWaterstoreDAO wtWaterstoreDAO;
    @Reference
    IPushMessageDubboAPIService pushMessageDubboAPIService;

    @Override
    public List<MyTicket> queryMyTicket(Long usreId, Long waterstoreId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", usreId);
        params.put("waterstoreId", waterstoreId);
        List<Map> res = wtUserTicketDAO.selectMyTicket(params);
        List<MyTicket> list = new ArrayList<MyTicket>();
        if(null != res && !res.isEmpty()){
            res.forEach(item ->{
                list.add(mapToObject(item));
            });
        }
        //同一个城市的水票可以共用,不同城市的不可以共用
        WtWaterstore wtWaterstore = this.wtWaterstoreDAO.selectById(waterstoreId);
        if(null != wtWaterstore){
            if(null != list && !list.isEmpty()){
                Iterator<MyTicket> it = list.iterator();
                while (it.hasNext()){
                    MyTicket item = it.next();
                    if(item.getBranchesId().longValue() != wtWaterstore.getCityBranchesId().longValue()){
                        it.remove();
                    }
                }
            }
        }
        return list;
    }

    public static MyTicket mapToObject(Map map){
        MyTicket m = new MyTicket();
        m.setId(Long.parseLong(map.get("id")+""));
        m.setUserId(Long.parseLong(map.get("user_id")+""));
        m.setNum(Integer.parseInt(map.get("num")+""));
        m.setSurplusNum(Integer.parseInt(map.get("surplus_num")+""));
        m.setTicketPrice(Long.parseLong(map.get("ticket_price")+""));
        m.setSkuCode(map.get("sku_code")+"");
        m.setOrderCode(map.get("order_code")+"");
        m.setOrderTime(Long.parseLong(map.get("order_time")+""));
//        m.setUpdateTime(Long.parseLong(map.get("update_time")+""));
        m.setCreateTime(Long.parseLong(map.get("create_time")+""));
        m.setDelState(Integer.parseInt(map.get("del_state")+""));
        m.setBranchesId(Long.parseLong(map.get("branches_id")+""));
        m.setGoodsName(map.get("sku_name")+"");
        m.setGoodsSpec(map.get("goods_spec")+"");
        m.setSkuId(Long.parseLong(map.get("sku_id")+""));
        return m;
    }

    @Override
    public WtOrder queryOne(String skuCode, Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("skuCode", skuCode);
        params.put("userId", userId);
        return this.wtOrderDAO.selectBySkuCode(params);
    }

    @Override
    public Goods queryGoods(Long skuId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        Map res = this.wtSkuDAO.selectGoods(params);
        Goods g = mapToGoods(res);
        return g;
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
//        g.setGoodsPic("http://img.goola.cn/"+map.get("goods_pic")+"");
        g.setGoodsPic(map.get("goods_pic")+"");
        g.setBrandName(map.get("name")+"");
        return g;
    }

    @Override
    public List<WtUserTicket> queryTicketAsc(Long userId, String skuCode, Long branchesId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("skuCode", skuCode);
        params.put("branchesId", branchesId);
        return this.wtUserTicketDAO.selectByUseridAndSkuCodeAsc(params);
    }

    @Override
    public List<WtUserTicket> queryTicketDesc(Long userId, String skuCode, Long branchesId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("skuCode", skuCode);
        params.put("branchesId", branchesId);
        return this.wtUserTicketDAO.selectByUseridAndSkuCodeDesc(params);
    }


    @Override
    public Long addSend(WtSend wtSend) {
        wtSend.setCreateTime(System.currentTimeMillis());
        JSONObject json2 = new JSONObject();
        WtWaterstore wtWaterstore = this.wtWaterstoreDAO.selectById(wtSend.getWaterstoreId());

        json2.put("receiveId",wtWaterstore.getBranchesId());//接受的分支机构id
        json2.put("pushId",wtWaterstore.getBranchesId());//推送的分支机构ID
        json2.put("title","新的订单");//消息题目
        json2.put("content","您有新的订单，请注意查收！");//内容
        try {
            tuisong(json2);
        } catch (Exception e) {
            logger.error("一键送水推送下派送单推送异常", e);
            e.printStackTrace();
        }
        this.wtSendDAO.insert(wtSend);
        return wtSend.getId();
    }

    @Override
    public Long addSendMes(WtSendMes wtSendMes) {
        wtSendMes.setCreateTime(System.currentTimeMillis());
        return this.wtSendMesDAO.insert(wtSendMes);
    }

    @Override
    public List<WtWaterstoreSku> queryWaterSku(WtWaterstoreSku wtWaterstoreSku) {
        return this.wtWaterstoreSkuDAO.selectList(wtWaterstoreSku);
    }

    @Override
    public List<WtTicketLog> queryTicketLong(Long ticketId) {
        return this.wtTicketLogDAO.selectByTicketId(ticketId);
    }

    @Override
    //@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public boolean useTicket(List<WtUserTicket> wtUserTickets, int useNum, WtSend wtSend, WtSendMes wtSendMes) {
        boolean flag = true;
        WtWaterstore wtWaterstore = this.wtWaterstoreDAO.selectById(wtSend.getWaterstoreId());
        List<WtUserTicket> cur_wtUserTickets = this.queryTicketAsc(wtSend.getUserId(), wtSendMes.getSkuCode(), wtWaterstore.getCityBranchesId());

        if(cur_wtUserTickets == null || cur_wtUserTickets.isEmpty()){
            //水票不足
            flag = false;
        }else{
            int temp_num = 0;
            for(WtUserTicket temp : cur_wtUserTickets){
                temp_num+= temp.getSurplusNum();
            }
            logger.info("用户id={}",wtSend.getUserId());
            logger.info("用户使用水票数量={}",useNum);
            logger.info("商品skucode={}",wtSendMes.getSkuCode());
            logger.info("用户剩余数量={}",temp_num);

            if(temp_num < useNum){
                //水票不足
                flag = false;
            }else{
                //消费水票前，在查询一下
                for(WtUserTicket wut : cur_wtUserTickets){
                    //修改水票余量，添加水票使用记录
                    if(wut.getSurplusNum() >= useNum){
                        //修改用户水票
                        WtUserTicket edit = new WtUserTicket();
                        edit.setId(wut.getId());
                        edit.setSurplusNum(useNum);
                        edit.setUpdateTime(System.currentTimeMillis());
                        this.wtUserTicketDAO.updateMinusById(edit);


                        //添加水票消耗记录
                        List<WtTicketLog> wtTicketLogs = this.wtTicketLogDAO.selectByTicketId(wut.getId());
                        WtTicketLog add = new WtTicketLog();
                        add.setTicketId(wut.getId());
                        add.setSkuName(wtTicketLogs.get(0).getSkuName());
                        add.setSkuCode(wtTicketLogs.get(0).getSkuCode());
                        add.setTicketPrice(wut.getTicketPrice());
                        add.setNum(useNum);
                        add.setOperation(-1);
                        add.setType(2);
                        add.setUserId(wut.getUserId());
                        add.setUserName(wtSend.getContacts());
                        add.setAddress(wtSend.getProvince()+wtSend.getCity()+wtSend.getArea()+wtSend.getAddress());
                        add.setSendMesCode(wtSendMes.getSendMesCode());
                        add.setOrderMesCode(wtTicketLogs.get(0).getOrderMesCode());
                        add.setLogTime(System.currentTimeMillis());
                        add.setCreateTime(System.currentTimeMillis());
                        add.setDelState(1);
                        this.wtTicketLogDAO.insert(add);
                        break;
                    }else{
                        //记录剩余未扣水票
                        useNum-=wut.getSurplusNum();
                        //修改用户水票(使用水票数量大于水票剩余数量，将该水票余量修改为0)
                        WtUserTicket edit = new WtUserTicket();
                        edit.setId(wut.getId());
                        edit.setSurplusNum(0);
                        edit.setUpdateTime(System.currentTimeMillis());
                        this.wtUserTicketDAO.update(edit);

                        //添加水票消耗记录
                        List<WtTicketLog> wtTicketLogs = this.wtTicketLogDAO.selectByTicketId(wut.getId());
                        WtTicketLog add = new WtTicketLog();
                        add.setTicketId(wut.getId());
                        add.setSkuName(wtTicketLogs.get(0).getSkuName());
                        add.setSkuCode(wtTicketLogs.get(0).getSkuCode());
                        add.setTicketPrice(wut.getTicketPrice());
                        add.setNum(wut.getSurplusNum());
                        add.setOperation(-1);
                        add.setType(2);
                        add.setUserId(wut.getUserId());
                        add.setUserName(wtSend.getContacts());
                        add.setAddress(wtSend.getProvince()+wtSend.getCity()+wtSend.getArea()+wtSend.getAddress());
                        add.setSendMesCode(wtSendMes.getSendMesCode());
                        add.setOrderMesCode(wtTicketLogs.get(0).getOrderMesCode());
                        add.setLogTime(System.currentTimeMillis());
                        add.setCreateTime(System.currentTimeMillis());
                        add.setDelState(1);
                        this.wtTicketLogDAO.insert(add);
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public List<WtSend> queryWtSendList(WtSend wtSend) {
        return this.wtSendDAO.selectList(wtSend);
    }

    @Override
    public List<WtSendMes> queryWtSendMesList(WtSendMes wtSendMes) {
        return this.wtSendMesDAO.selectList(wtSendMes);
    }

    @Override
    public void updateWtSend(WtSend wtSend) {
        this.wtSendDAO.update(wtSend);
    }

    @Override
    public void updateWtSendMes(WtSendMes wtSendMes) {
        this.wtSendMesDAO.update(wtSendMes);
    }

    @Override
    public List<WtTicketLog> queryWtTicketLogList(WtTicketLog wtTicketLog) {
        return this.wtTicketLogDAO.selectList(wtTicketLog);
    }

    @Override
    public List<WtUserTicket> queryWtUserTicketList(WtUserTicket wtUserTicket) {
        return this.wtUserTicketDAO.selectList(wtUserTicket);
    }

    @Override
    public void updateWtUserTicket(WtUserTicket wtUserTicket) {
        this.wtUserTicketDAO.update(wtUserTicket);
    }

    @Override
    public Long addWtTicketLog(WtTicketLog wtTicketLog) {
        this.wtTicketLogDAO.insert(wtTicketLog);
        return wtTicketLog.getId();
    }

    @Override
//    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void cancelSend(List<WtSend> wtSends, Long cancelTime) {
        if(null != wtSends && !wtSends.isEmpty()){
            Long curTime = System.currentTimeMillis();
            Predicate<WtSend> timeFilter  = (wtSend) -> (curTime - wtSend.getCreateTime() >= cancelTime);
            wtSends.stream().filter(timeFilter).forEach(wtSend -> {

                //查询水站信息
//                WtWaterstore wtWaterstore = this.wtWaterstoreDAO.selectById(wtSend.getWaterstoreId());
                //根据派送单号查询派送单明细信息
                WtSendMes query_mes = new WtSendMes();
                query_mes.setSendCode(wtSend.getSendNo());
                List<WtSendMes> wtSendMes = this.queryWtSendMesList(query_mes);

                if(null != wtSendMes && !wtSendMes.isEmpty()){

                    //如果不是水票支付不取消派送单
                    boolean edit_flag = true;
                    for(WtSendMes wtSendMes1 : wtSendMes) {
                        if(wtSendMes1.getType() != 1){
                            edit_flag = false;
                            logger.info("混合支付的派送单不定时取消,派送单信息{}", JSONObject.toJSONString(wtSend));
                            logger.info("混合支付的派送单不定时取消,派送单明细{}", JSONObject.toJSONString(wtSendMes));
                        }
                    }
                    //该派送单商品都是通过水票购买
                    if(edit_flag){
                        //设置派送单为取消状态,只取消全部使用水票购买的派送单
                        WtSend ws_edit = new WtSend();
                        ws_edit.setId(wtSend.getId());
                        ws_edit.setStatus(-1);
                        ws_edit.setUpdateTime(System.currentTimeMillis());
                        //取消派送单
                        this.wtSendDAO.update(ws_edit);


                        /**
                         * 1.根据派送单明细code退（一键送水产生的派送单）
                         * 2.根据订单明细code退(购物车下单生产派送单)
                         * 订单下单的时候，水票消耗记录插入数据，生成派送单的时候，把派送单
                         * 明细放入数据
                         */
                        //循环派单明细
                        for(WtSendMes wtSendMes1 : wtSendMes){
                            //派单明细——>水票记录(多个,根据明细标号关联)——>水票(多个,根据水票id关联)

                            //根据派送单明细编号，查询水票消耗记录
                            WtTicketLog query_log = new WtTicketLog();
                            query_log.setSendMesCode(wtSendMes1.getSendMesCode());
                            query_log.setOperation(-1);
                            List<WtTicketLog> wtTicketLogs = this.queryWtTicketLogList(query_log);

                            if(null != wtTicketLogs && !wtTicketLogs.isEmpty()){
                                wtTicketLogs.forEach(wtTicketLog -> {
                                    logger.info("定时取消派送单退还水票派送单{}", JSONObject.toJSONString(wtSend));
                                    logger.info("定时取消派送单退还水票派送单明细{}", JSONObject.toJSONString(wtSendMes));
                                    WtUserTicket cur_t = this.wtUserTicketDAO.selectById(wtTicketLog.getTicketId());

                                    //循环水票消耗记录，退还水票
//                                    WtUserTicket wut_edit = new WtUserTicket();
//                                    wut_edit.setId(wtTicketLog.getTicketId());
//                                    wut_edit.setSurplusNum(cur_t.getSurplusNum()+wtTicketLog.getNum());
//                                    wut_edit.setUpdateTime(System.currentTimeMillis());
//                                    this.wtUserTicketDAO.update(wut_edit);
                                    WtUserTicket wut_edit = new WtUserTicket();
                                    wut_edit.setId(wtTicketLog.getTicketId());
                                    wut_edit.setSurplusNum(wtTicketLog.getNum());
                                    wut_edit.setUpdateTime(System.currentTimeMillis());
                                    this.wtUserTicketDAO.updateAddById(wut_edit);
                                    //添加水票变动记录
                                    WtTicketLog add = new WtTicketLog();
                                    add.setTicketId(cur_t.getId());
                                    add.setSkuName(wtTicketLog.getSkuName());
                                    add.setSkuCode(wtTicketLog.getSkuCode());
                                    add.setNum(wtTicketLog.getNum());
                                    add.setTicketPrice(cur_t.getTicketPrice());
                                    add.setOperation(1);
                                    add.setType(2);
                                    add.setUserId(wtTicketLog.getUserId());
                                    add.setUserName(wtTicketLog.getUserName());
                                    add.setAddress(wtTicketLog.getAddress());
                                    add.setSendMesCode(wtTicketLog.getSendMesCode());
                                    add.setOrderMesCode(wtTicketLog.getOrderMesCode());
                                    add.setLogTime(wtSendMes1.getCreateTime());//消费时间（下单时间）
                                    add.setCreateTime(System.currentTimeMillis());
                                    add.setDelState(1);
                                    this.addWtTicketLog(add);
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    public Long tuisong(JSONObject json) throws Exception {
        json.put("typeCode","123");//编码
        json.put("thenType","1");//0.为定时发送 1.立即发送
        json.put("type","1");//1:通知消息 2:系统消息
        json.put("sourceName","开放平台");//	开放平台
        json.put("batchId", UUID.randomUUID().toString());//	批次号(batchId = UUID.randomUUID().toString())
        json.put("userIds", "");//	接受分支机构你的下的所有用户ID
        logger.info("一键送水推送消息={}", json);
        com.gt.gtop.entity.base.DataMessage abcd = pushMessageDubboAPIService.buildPushObjectWithAlias(json.toJSONString());
        if(abcd != null){
            logger.info("abcd==============={}",abcd.getData());
            logger.info("abcd==============={}",abcd.getMessage());
            logger.info("abcd==============={}",abcd.getResult());
        }else{
            logger.info("一键送水推送失败={}", abcd);
        }

        return 1L;
    }

    @Override
    public WtWaterstore queryWaterstoreById(Long id) {
        return this.wtWaterstoreDAO.selectById(id);
    }

    @Override
    public void updateAddTicket(WtUserTicket wtUserTicket) {
        this.wtUserTicketDAO.updateAddById(wtUserTicket);
    }

    @Override
    public void updateMinusTicket(WtUserTicket wtUserTicket) {
        this.wtUserTicketDAO.updateMinusById(wtUserTicket);
    }


}
