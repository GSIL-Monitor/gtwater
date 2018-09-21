package com.gt.manager.sign;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
public class SignUtils {

    /**
     * 为了兼容旧的接口，使用的加密方法
     *
     * @param decript
     * @return
     */
    public static String SHA1(String decript) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            return Cryptor.toBase64String(messageDigest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSha1(String str) {
        String result = null;
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String decript) {
        String resultString = null;
        try {
            resultString = decript;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return resultString;
    }

    private static String byte2hexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            if (((int) aByte & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) aByte & 0xff, 16));
        }
        return buf.toString();
    }

    public static StringBuffer paramsSort(SortedMap<String, Object> paramsMap) {
        StringBuffer sb = new StringBuffer();
        if (paramsMap != null) {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> param : paramsMap.entrySet()) {
                String key = param.getKey();
                Object value = param.getValue();
                if (value != null) {
                    String val = value.toString().trim();
                    sb.append(key + "=" + value + "&");
                    paramList.add(new BasicNameValuePair(key, val));
                }
            }
            if (sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
        }
        return sb;
    }
}
