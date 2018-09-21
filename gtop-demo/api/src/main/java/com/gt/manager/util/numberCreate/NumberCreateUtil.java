package com.gt.manager.util.numberCreate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单编号，订单明细编号，派送单编号，派送单明细编号
 * 使用方法：
 * 1.生成订单编号          NumberCreateUtil.makeOrderNum(1,null);
 * 2.生成订单明细编号       NumberCreateUtil.makeOrderNum(2,"订单号");
 * 3.生成派送单编号        NumberCreateUtil.makeOrderNum(3,null);
 * 4.生成派送单明细编号    NumberCreateUtil.makeOrderNum(4,"派送单号");
 */
public class NumberCreateUtil {
    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize=1000;
    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     * @param type 1:订单编号  2：订单明细编号   3.配送单编号  4：派送单明细编号
     * @param parentNo  type 为1和3时，不传，type为2时传订单编号，为4传派送单编号
     */
    public static String makeOrderNum(int type, String parentNo) {
        try {
            // 最终生成的订单号
            String finOrderNum = "";
            synchronized (lockObj) {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
                if (orderNumCount >= maxPerMSECSize) {
                    orderNumCount = 0L;
                }
                //组装订单号
                String countStr=maxPerMSECSize +orderNumCount+"";
                if(type == 1){
                    finOrderNum="D"+nowLong+countStr.substring(1);
                }else if(type == 2){
                    finOrderNum=parentNo+countStr.substring(1);
                }else if(type == 3){
                    finOrderNum="P"+nowLong+countStr.substring(1);
                }else if(type == 4){
                    finOrderNum=parentNo+countStr.substring(1);
                }
                orderNumCount++;
                return finOrderNum;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // 测试多线程调用订单号生成工具
        String orderNo = NumberCreateUtil.makeOrderNum(1,null);
        System.out.println(orderNo);
        for(int i=0;i<5;i++){
            String mx = NumberCreateUtil.makeOrderNum(2,orderNo);
            System.out.println(mx);
        }

        String pNo = NumberCreateUtil.makeOrderNum(3,null);
        System.out.println(pNo);
        for(int i=0;i<5;i++){
            String mx = NumberCreateUtil.makeOrderNum(4,pNo);
            System.out.println(mx);
        }
    }
}
