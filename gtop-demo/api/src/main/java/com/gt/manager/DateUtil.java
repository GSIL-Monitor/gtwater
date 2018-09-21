package com.gt.manager;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>ClassName: DateUtil.java</p>
 * <p>Description:时间工具类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月19日 上午11:26:44</p>
 */
public class DateUtil {
    // 传入时间比较区间(开始时间>=服务器时间<=结束时间)
    public static synchronized boolean betweenDate(String startTime, String endTime) {
        // 服务器时间
        String today = LocalDate.now().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        Date nowDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
            nowDate = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nowDate.getTime() >= startDate.getTime() && nowDate.getTime() <= endDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // 验证是否是日期格式
    public static synchronized boolean validShortDate(String date) {
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean result = mat.matches();
        return result;
    }

    // yyyy-MM-dd HH:mm:ss
    public static synchronized boolean validateDateTime(String dateTime) {
        String rexp = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(dateTime);
        boolean result = mat.matches();
        return result;
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static synchronized String toDateTimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public static synchronized String toShortDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    public static synchronized String plusDays(Date date, int days) {
        String dateString = toShortDateString(date);
        String result = plusDays(dateString, days);
        return result;
    }

    public static synchronized String plusDays(String shorDate, int days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String result = "";
        Date d = toShortDate(shorDate);

        c.setTime(d);
        c.add(Calendar.DAY_OF_MONTH, 1);
        result = toShortDateString(c.getTime());

        return result;
    }
    /**
     * 返回当前日期的前几天或或后几天--正数后几天---负数表示前几天
     */
    public static synchronized String beforeDays(int days){
    	Date dNow = new Date();   //当前时间
    	Date dBefore = null;

    	Calendar calendar = Calendar.getInstance(); //得到日历
    	calendar.setTime(dNow);//把当前时间赋给日历
    	calendar.add(Calendar.DAY_OF_MONTH, days);  //设置为前一天
    	dBefore = calendar.getTime();   //得到前一天的时间


    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
    	String defaultStartDate = format.format(dBefore);    //格式化某一天
		return defaultStartDate;
    	

    }

    public static synchronized long beforeMonths (int month){

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -month);  //设置为前3月
        dBefore = calendar.getTime();  //得到前3月的时间
     //   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
      //  String defaultStartDate = sdf.format(dBefore);  //格式化前3月的时间
     // String defaultEndDate = sdf.format(dNow); //格式化当前时间

        return dBefore.getTime();
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static synchronized Date toShortDate(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            format.setLenient(false);
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static synchronized Date toShortDate(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            format.setLenient(false);
            result = format.parse(toShortDateString(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成日期
     *
     * @param str yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static synchronized Date toDateTime(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            format.setLenient(false);
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) throws Exception {
        //System.out.print(toDateTime("2017-11-11 15:30:35"));
        //System.out.print(toShortDate("2017-11-11"));
        //System.out.print(plusDays("2017-11-1",5));
    //	System.out.println(beforeDays(-7));
        long times = beforeMonths(-7);
        System.out.println(times);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultStartDate = sdf.format(times);  //格式化前3月的时间
        System.out.println(defaultStartDate);
    }

}
