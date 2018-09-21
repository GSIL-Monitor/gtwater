package com.gt.manager.order.resource.send;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.Constant;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.common.ReqData;
import com.gt.manager.entity.common.RequestCodeEnum;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import com.gt.manager.order.entity.send.Goods;
import com.gt.manager.order.entity.send.MyTicket;
import com.gt.manager.order.service.receiveAddress.ReceiveAddressService;
import com.gt.manager.order.service.send.SendService;
import com.gt.manager.util.numberCreate.NumberCreateUtil;
import com.sun.jdi.PathSearchingVirtualMachine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一键送水
 */
@Path("/send")
@Api(value = "/send", description = "一键送水")
public class SendController {
    private static Logger logger = LoggerFactory.getLogger(SendController.class);
    @Autowired
    private SendService sendService;
    @Autowired
    private ReceiveAddressService receiveAddressService;

    @Path("/sendBusiess")
    @POST
    @ApiOperation(value = "/sendBusiess", notes = "一键送水")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public DataMessage sendBusiess(ReqData r){
        DataMessage dm = new DataMessage();
        try {
            logger.info("一键送水接收参数={}", JSONObject.toJSONString(dm));
            String platform = r.getPlatform();
            String requestCode = r.getRequestCode();
            String params = r.getParams();
            if(StringUtils.isBlank(platform) || StringUtils.isBlank(requestCode) || StringUtils.isBlank(params)){
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("参数错误");
            }else if(RequestCodeEnum.一键送水获取水票.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long userId = Long.parseLong(jsonObject.getString("userId"));
                Long waterstoreId = jsonObject.getLong("waterstoreId");
                List<MyTicket> myTicketList = this.sendService.queryMyTicket(userId, waterstoreId);
                dm.setResult(DataMessage.RESULT_SUCESS);
                dm.setData(myTicketList);
                dm.setMessage("获取成功");
            }else if(RequestCodeEnum.水票使用.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                Long userId = Long.parseLong(jsonObject.getString("userId"));
                Long skuId = jsonObject.getLong("skuId");
                String skuCode = jsonObject.getString("skuCode");
                Long waterstoreId = jsonObject.getLong("waterstoreId");
                //WtOrder w = this.sendService.queryOne(skuCode, userId);
                //查询用户默认地址，用于购买商品
                ReceiveAddress query = new ReceiveAddress();
                query.setUserid(userId);
                query.setIsDefault(1);
                query.setIsDelete(2);
                List<ReceiveAddress> queryList = this.receiveAddressService.queryList(query);
                if(null == queryList || queryList.isEmpty()){
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("请设置默认地址后在使用一键送水");
                }else{
                    Goods g = this.sendService.queryGoods(skuId);
                    WtWaterstore wtWaterstore = this.sendService.queryWaterstoreById(waterstoreId);
                    String start = wtWaterstore.getOperateStartTime();
                    String end = wtWaterstore.getOperateEndTime();
                    int s = Integer.parseInt(start.split(":")[0]);
                    int e = Integer.parseInt(end.split(":")[0]);
                    List<Integer> l = new ArrayList<>();
                    for(int i=s;i<=e;i++){
                        l.add(i);
                    }
                    String[] week = wtWaterstore.getOperateWeek().split(",");
                    List<String> week_l = new ArrayList<>();
                    for(int i=0;i< week.length;i++){
                        week_l.add(week[i]);
                    }
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("order", addressToOrder(queryList.get(0)));
                    result.put("g", g);
                    List<MyTicket> mt = this.sendService.queryMyTicket(userId, waterstoreId);
                    int ticketNum = 0;
                    if(mt != null && !mt.isEmpty()){
                        for(MyTicket m : mt){
                            if(m.getSkuCode().equals(skuCode)){
                                ticketNum = m.getSurplusNum();
                                break;
                            }
                        }
                    }
                    result.put("ticketNum", ticketNum);
                    result.put("businessTime",l);//营业时间
                    result.put("operateWeek",week_l);//运营周期
                    dm.setResult(DataMessage.RESULT_SUCESS);
                    dm.setData(result);
                    dm.setMessage("获取成功");
                }
            }else if(RequestCodeEnum.立即配送.req_code.equals(requestCode)){
                JSONObject jsonObject = JSONObject.parseObject(params);
                //水站信息
                Long waterstoreId = jsonObject.getLong("waterstoreId");//水站id
                //基本信息
                String provinceId  = jsonObject.getString("provinceId");
                String province = jsonObject.getString("province");
                String cityId = jsonObject.getString("cityId");
                String city = jsonObject.getString("city");
                String areaId = jsonObject.getString("areaId");
                String area = jsonObject.getString("area");
                String address = jsonObject.getString("address");
                Long userId = Long.parseLong(jsonObject.getString("userId"));
                String contacts = jsonObject.getString("contacts");
                String phone = jsonObject.getString("phone");
                String appointmentTime = jsonObject.getString("appointmentTime");//格式(yyyy-MM-dd HH:mm:ss)
                String remark = jsonObject.getString("remark");
                //商品信息
                Long skuId = jsonObject.getLong("skuId");
                Goods g = this.sendService.queryGoods(skuId);
                //水票信息
                int useNum = jsonObject.getInteger("userNum");//使用水票数量
                //用户持有的该种类水票信息，根据时间升序排列
//                List<WtUserTicket> wtUserTickets = this.sendService.queryTicket(userId, g.getSkuCode(), "asc");
                WtWaterstore wtWaterstore = this.sendService.queryWaterstoreById(waterstoreId);
                List<WtUserTicket> wtUserTickets = this.sendService.queryTicketAsc(userId, g.getSkuCode(), wtWaterstore.getCityBranchesId());

                //开始下单
                /**
                 * 1.验证水票是否充足
                 * 2.验证商品是否下架
                 * 3.下派送单
                 * 4.下派送单详情
                 * 5.扣除水票
                 * 6.添加水票消耗记录
                 * 7.下单完成
                 */
                //1
                boolean flag = true;
                flag = chkUserTicketNum(wtUserTickets, useNum);
                if(!flag){
                    dm.setResult(DataMessage.RESULT_FAILED);
                    dm.setData(null);
                    dm.setMessage("水票不足");
                }
                if(flag){
                    //2
                    WtWaterstoreSku query = new WtWaterstoreSku();
                    query.setSkuId(skuId);
                    query.setStatus(1);//销售中
                    List<WtWaterstoreSku> wtWaterstoreSkus = this.sendService.queryWaterSku(query);
                    if(null == wtWaterstoreSkus || wtWaterstoreSkus.isEmpty()){
                        flag = false;
                        dm.setResult(DataMessage.RESULT_FAILED);
                        dm.setData(null);
                        dm.setMessage("商品已下架");
                    }else{
                        //3
                        Long money = calculatePrice(useNum, wtUserTickets);//派送单价格
                        WtSend add_send = addSend(waterstoreId, provinceId, province, cityId, city, areaId, area, address, userId, contacts, phone, appointmentTime, remark, money, wtWaterstore.getTel());

                        //4
                        WtSendMes add_send_mes = addSendMes(g, useNum, add_send);

                        //5,6
                        synchronized(String.valueOf(userId).intern()){
                            boolean user_ticket_falg = this.sendService.useTicket(wtUserTickets, useNum, add_send, add_send_mes);
                            if(user_ticket_falg){
                                //添加派送单
                                Long sendId = this.sendService.addSend(add_send);
                                //添加订单
                                Long sendMesId = this.sendService.addSendMes(add_send_mes);
                                //返回派送单id
                                dm.setResult(DataMessage.RESULT_SUCESS);
                                dm.setData(sendId);
                                dm.setMessage("下单成功");
                            }else{
                                dm.setResult(DataMessage.RESULT_FAILED);
                                dm.setData(null);
                                dm.setMessage("水票不足");
                            }
                        }
                    }
                }
            }else{
                dm.setResult(DataMessage.RESULT_FAILED);
                dm.setData(null);
                dm.setMessage("请求码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("一键送水异常",e);
            dm.setResult(DataMessage.RESULT_FAILED);
            dm.setData(null);
            dm.setMessage("一键送水异常");
            return dm;
        }
        logger.info("一键送水={}", JSONObject.toJSONString(dm));
        return dm;
    }

    /**
     * 计算派单价格
     * @param num
     * @param wtUserTickets
     * @return
     */
    public static Long calculatePrice(int num, List<WtUserTicket> wtUserTickets){
        logger.info("计算派单价格使用水票数量{}", num);
        logger.info("计算派单价格用户水票list{}", JSONObject.toJSONString(wtUserTickets));
        Long price = 0L;
        if(null != wtUserTickets && !wtUserTickets.isEmpty()){
            for (WtUserTicket w : wtUserTickets){
                if(w.getSurplusNum()>=num){
                    price += w.getTicketPrice()*num;
                }else{
                    price += w.getTicketPrice()*w.getSurplusNum();
                    num -= w.getSurplusNum();
                }
            }
        }
        return price;
    }

    /**
     * 检验用户水票是否充足
     * @param wtUserTickets     用户持有的水票数据list
     * @param curUseNum         本次使用水票张数
     * @return                  true:水票充足  false:水票不够
     */
    public static boolean chkUserTicketNum(List<WtUserTicket> wtUserTickets, int curUseNum){
        logger.info("检验用户水票是否充足水票list{}", wtUserTickets);
        logger.info("使用数量{}", curUseNum);
        boolean flag = true;
        if(null != wtUserTickets && !wtUserTickets.isEmpty()){
            int allNum = 0;
            for(WtUserTicket w : wtUserTickets){
                allNum += w.getSurplusNum();//水票余量
            }
            if(allNum < curUseNum){
                flag = false;
            }
        }else{
            flag = false;
        }
        return flag;
    }

    /**
     * 构造派送单数据
     * @param waterstoreId      水站id
     * @param provinceId        省id
     * @param province          省名称
     * @param cityId            城市id
     * @param city              城市
     * @param areaId            区域id
     * @param area              区域
     * @param address           详细地址
     * @param userId            用户id
     * @param contacts          联系人
     * @param phone             联系方式
     * @param appointmentTime   预约时间
     * @param remark
     * @param money
     * @return
     */
    public static WtSend addSend(Long waterstoreId, String provinceId, String province, String cityId, String city, String areaId, String area,
                                 String address, Long userId, String contacts,String phone, String appointmentTime, String remark, Long money, String tel){
        WtSend w = new WtSend();
        w.setSendNo(NumberCreateUtil.makeOrderNum(3, null));
        w.setWaterstoreId(waterstoreId);
        w.setProvinceId(provinceId);
        w.setProvince(province);
        w.setCityId(cityId);
        w.setCity(city);
        w.setAreaId(areaId);
        w.setArea(area);
        w.setAddress(address);
        w.setUserId(userId);
        w.setContacts(contacts);
        w.setPhone(phone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            w.setAppointmentTime(sdf.parse(appointmentTime+":00").getTime());
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("派送单数据，预约时间异常="+appointmentTime, ex);
            w.setAppointmentTime(System.currentTimeMillis());
        }
        w.setStatus(0);//状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】
        w.setRemarks(remark);
        w.setDelStatus(1);
        w.setMoney(money);
        w.setCreateId(userId);
        w.setWaterstoreTel(tel);//站点联系电话
        return w;
    }

    /**
     * 构造配送单详情数据
     * @param g
     * @param num
     * @param wtSend
     * @return
     */
    public static WtSendMes addSendMes(Goods g, int num, WtSend wtSend){
        WtSendMes w = new WtSendMes();
        w.setSendCode(wtSend.getSendNo());
        w.setSendMesCode(NumberCreateUtil.makeOrderNum(4, wtSend.getSendNo()));
        w.setSkuCode(g.getSkuCode());
        w.setSkuName(g.getSkuName());
        w.setNum(num);
        w.setDeliveryNum(num);//实际送达数量和配送单数量默认相同
        w.setType(1);//1水票|| 2现金||3混合支付
        w.setSequence(JSONObject.toJSONString(g));//商品镜像
        w.setDelState(1);
        w.setGoodsSpec(g.getGoodsSpec());
        w.setBrandName(g.getBrandName());//品牌名称
        w.setCreateId(wtSend.getUserId());
        return w;
    }

    public static WtOrder addressToOrder(ReceiveAddress r){
        WtOrder w = new WtOrder();
        w.setProvinceId(r.getProvinceId()+"");
        w.setProvince(r.getProvinceName());
        w.setCityId(r.getCityId()+"");
        w.setCity(r.getCityName());
        w.setAreaId(r.getDistrictId()+"");
        w.setArea(r.getDistrictName());
        w.setAddress(r.getAddress()+r.getHouseNumber());
        w.setContacts(r.getName());
        w.setPhone(r.getPhone());
        return w;
    }


    public static void main(String[] args) {
        String url="http%3A%2F%2Fwater.gyexpress.cn%2Findex.html%3Fcode%3D061hfWpq1pHknp0vnnpq1KiOpq1hfWpB%26state%3Dstate%23%2Fstatic%2Faddress%2Fadd%3Fcode%3D061hfWpq1pHknp0vnnpq1KiOpq1hfWpB%26state%3Dstate%23%2Fstatic%2Faddress%2Flist";
        url = "http%3A%2F%2Fwater.gyexpress.cn%2Findex.html%23%2Fstatic%2Faddress%2Fadd%3FaddressId%3D12";
        try {
            System.out.println(URLDecoder.decode(url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
