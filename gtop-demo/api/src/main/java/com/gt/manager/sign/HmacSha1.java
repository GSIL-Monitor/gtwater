package com.gt.manager.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author conmind
 */
public class HmacSha1 {
    public static String getSignature(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    //二行制转字符串
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    public static void main(String[] args){
        //  String  signatureHmaSha = HmacSha1.getSignature("regionId=111101".getBytes(), "661ee319dff061d4fb6532787f7b6wgl".getBytes());
    String  signatureHmaSha = HmacSha1.getSignature("address=西三环北路72号世纪经贸大厦&branchesName=指尖快递世纪经贸服务站&cityId=111101&cityName=北京市&districtId=11110101&districtName=海淀区&linkMail=10307560@qq.com&linkName=lion&linkPhone=18610101010&provinceId=1111&provinceName=北京市&selfLifeAddress=北京市海淀区西三环北路72号世纪经贸大厦&selfLifePhone=18610101010,18610101011&selfLifeState=1&selfLifeTime=09:00-17:59".getBytes(), "661ee319dff061d4fb6532787f7b6wgl".getBytes());
    System.out.print(signatureHmaSha);
    }

}