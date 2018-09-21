package com.gt.manager.order.service.userTicket;


import com.gt.manager.entity.wtUserTicket.WtUserTicket;

import java.util.List;

/**
 * 水票
 * @author why
 */
public interface UserTicketService {



   /**
	* 查询用户水票
	*/
   List<WtUserTicket> queryUserTicket(WtUserTicket ut);

}