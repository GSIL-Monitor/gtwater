package com.gt.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>ClassName: OrderNumberUtil.java</p>
 * <p>Description:订单编号生成规则 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年11月25日 下午5:28:36</p>
 */
public class OrderNumberUtil {
    // 订单编号生成规则,用户微信订单号--利润提成
    public static String getWeiXinNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        int num = (int) ((Math.random() * 9 + 1) * 10000);
        String orderId = "FH" + sdf.format(new Date()) + String.valueOf(num);
        return orderId;
    }

    // 订单编号生成规则,用户微信订单号--退款单号
    public static String getRefundNo() {
        String machineNum = "01";// 机器号(暂时写死)
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        int num = (int) ((Math.random() * 9 + 1) * 10000);
        String orderId = "R" + sdf.format(new Date()) + machineNum + String.valueOf(num);
        return orderId;
    }

    // 订单编号生成规则,根据不同的地区生成订单号
    public static String getcustomerNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        int num = (int) ((Math.random() * 9 + 1) * 1000);
        String orderId = sdf.format(new Date()) + String.valueOf(num);
        return "B" + orderId;
    }

    // 订单编号生产规则:yyMMddHHmmss+2位机器号+4位随机数
    public synchronized static String getOrderNo() {
        String machineNum = "01";// 机器号(暂时写死)
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        int num = (int) ((Math.random() * 9 + 1) * 1000);
        String orderId = sdf.format(new Date()) + machineNum + String.valueOf(num);
        return orderId;
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, Integer> m = new HashMap<String, Integer>();
        String orderNo = null;
        for (int i = 1; i < 100000; i++) {
            orderNo = getOrderNo();
            m.put(orderNo, i);

            System.out.println(orderNo);
        }
        System.out.print("");
    }
}
