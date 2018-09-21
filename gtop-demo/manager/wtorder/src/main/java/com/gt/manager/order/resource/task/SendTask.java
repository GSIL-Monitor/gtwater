package com.gt.manager.order.resource.task;

import com.gt.manager.entity.wtSend.WtSend;
import com.gt.manager.entity.wtSendMes.WtSendMes;
import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.order.service.send.SendService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
@Order(1)
public class SendTask implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(SendTask.class);
    @Autowired
    private SendService sendService;
    private final Long cancelTime = 5L*60L*60L*1000L;//取消5个小时未接单的派送单
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        while (true){
            try {
                WtSend query = new WtSend();
                query.setStatus(0);
                query.setDelStatus(1);
                //查询未接单未删除的派送单
                List<WtSend> wtSends = this.sendService.queryWtSendList(query);
                this.sendService.cancelSend(wtSends, cancelTime);
                Thread.sleep(60*60*1000);//休息一小时
            }catch (Exception ex){
                ex.printStackTrace();
                logger.error("定时取消派送单异常=", ex);
            }
        }
    }
}
