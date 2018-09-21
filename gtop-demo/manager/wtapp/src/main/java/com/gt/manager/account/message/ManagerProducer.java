package com.gt.manager.account.message;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component("managerProducer")
public class ManagerProducer {

    private static final Logger log = LoggerFactory.getLogger(ManagerProducer.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(String channl,String message){
        log.info("activeMQ发送消息," + channl + ":"+message);
        Destination destination = new ActiveMQQueue(channl);
        jmsMessagingTemplate.convertAndSend(destination,message);
    }


}
