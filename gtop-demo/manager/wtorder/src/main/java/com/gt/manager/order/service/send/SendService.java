package com.gt.manager.order.service.send;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.wtOrder.WtOrder;
import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUser.WtUser;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.entity.wtWaterstore.WtWaterstore;
import com.gt.manager.entity.wtWaterstoreSku.WtWaterstoreSku;
import com.gt.manager.order.entity.send.Goods;
import com.gt.manager.order.entity.send.MyTicket;

import java.util.List;

public interface SendService {
    public List<MyTicket> queryMyTicket(Long usreId, Long waterstoreId);

    public WtOrder queryOne(String skuCode, Long userId);

    public Goods queryGoods(Long skuIds);

    public List<WtUserTicket> queryTicketAsc(Long userId, String skuCode, Long branchesId);

    public List<WtUserTicket> queryTicketDesc(Long userId, String skuCode, Long branchesId);

    public Long addSend(WtSend wtSend);

    public Long addSendMes(WtSendMes wtSendMes);

    public List<WtWaterstoreSku> queryWaterSku(WtWaterstoreSku wtWaterstoreSku);

    public List<WtTicketLog> queryTicketLong(Long ticketId);

    public boolean useTicket(List<WtUserTicket> wtUserTickets, int usrNum, WtSend wtSend, WtSendMes wtSendMes);

    public List<WtSend> queryWtSendList(WtSend wtSend);

    public List<WtSendMes> queryWtSendMesList(WtSendMes wtSendMes);

    public void updateWtSend(WtSend wtSend);

    public void updateWtSendMes(WtSendMes wtSendMes);

    public List<WtTicketLog> queryWtTicketLogList(WtTicketLog wtTicketLog);

    public List<WtUserTicket> queryWtUserTicketList(WtUserTicket wtUserTicket);

    public void updateWtUserTicket(WtUserTicket wtUserTicket);

    public Long addWtTicketLog(WtTicketLog wtTicketLog);

    //定时取消派送单
    public void cancelSend(List<WtSend> wtSends, Long cancelTime);


    public Long tuisong(JSONObject json) throws Exception;

    public WtWaterstore queryWaterstoreById(Long id);

    //水票加减

    public void updateAddTicket(WtUserTicket wtUserTicket);

    public void updateMinusTicket(WtUserTicket wtUserTicket);


}
