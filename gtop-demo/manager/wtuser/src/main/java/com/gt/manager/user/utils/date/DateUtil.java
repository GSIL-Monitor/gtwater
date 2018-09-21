package com.gt.manager.user.utils.date;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 是否是今天
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean isToday(final Date date) throws ParseException {
        Date dayStart = dayBegin(new Date());
        Date dayEnd = dayEnd(new Date());
        if(date.getTime()>dayStart.getTime() && date.getTime()<dayEnd.getTime()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
}
