package com.gt.manager.order.repository.wtUserTicket;


import com.gt.manager.entity.wtTicketLog.WtTicketLog;
import com.gt.manager.entity.wtUserTicket.WtUserTicket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 水票
 * @author why
 */
@Mapper
public interface UserTicket2DAO {

   /**
	* 查询用户水票
	*/
   List<WtUserTicket> selectList(WtUserTicket ut);

    /**
     * 插入用户水票表
     */
    Long insert(WtUserTicket wt);

    /**
     * 插入用户水票表
     */
    Long insertLog(WtTicketLog wt);
}