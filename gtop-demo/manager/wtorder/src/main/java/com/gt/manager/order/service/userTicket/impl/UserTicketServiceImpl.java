package com.gt.manager.order.service.userTicket.impl;


import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import com.gt.manager.order.repository.wtUserTicket.WtUserTicketDAO;
import com.gt.manager.order.service.userTicket.UserTicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    private static Logger logger = Logger.getLogger(UserTicketServiceImpl.class);

    @Autowired
    private WtUserTicketDAO dao;

    @Override
    public List<WtUserTicket> queryUserTicket(WtUserTicket ut) {
        List<WtUserTicket> userTicket = dao.selectList(ut);
        return userTicket;
    }
}
