package com.gt.manager;

import java.math.BigDecimal;
import java.util.Random;

/**
* <p>ClassName:RandomUtil</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 11:16</p>
**/
public class RandomUtil {
    /**
     *<p>Description:随机数</p>
     *<p>param min:最小值</p>
     *<p>param max:最大值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/2 13:46</p>
     */
    public static int nextInt(final int min, final int max) throws Exception {

        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        int randNum = new Random().nextInt(max-min+1) + min;
        return randNum;
    }
    /**
     *<p>Description:随机数</p>
     *<p>param min:最小值</p>
     *<p>param max:最大值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/2 13:46</p>
     */
    public static BigDecimal nextBigDecimal(final BigDecimal min, final BigDecimal max)throws Exception {
        int minNum=min.multiply(new BigDecimal(100)).intValue();
        int maxNum=max.multiply(new BigDecimal(100)).intValue();
        int randNum=nextInt(minNum,maxNum);
        BigDecimal d1=new BigDecimal(randNum);
        BigDecimal d2=new BigDecimal(100);
        BigDecimal d3=(d1.divide(d2)).setScale(2);
        return d3;
    }
    public static void main(String[] args) throws Exception
    {
        BigDecimal d1=new BigDecimal(0.11);
        BigDecimal d2=new BigDecimal(0.17);
        for(int i=0;i<50;i++){
            BigDecimal d3=nextBigDecimal(d1,d2);
            System.out.println(String.valueOf(d3));
        }

    }
}
