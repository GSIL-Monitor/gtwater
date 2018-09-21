package com.gt.manager;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * <p>ClassName: EncryptUtil.java</p>
 * <p>Description: sha加密算法</p>
 * <p>author: wanggongliang</p>
 * <p>2016年11月23日 上午10:09:21</p>
 */
public class EncryptUtil {
    //构造函数
    public EncryptUtil() {
    }

    //定义加密方式
    private final static String KEY_SHA = "SHA";
    //全局数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    //配置参数
    private final static String PASSSALT = "w1fsd2DurO0";

    /**
     * SHA 加密
     *
     * @param data 需要加密的字符串
     * @return 加密之后的字符串
     * @throws Exception
     */
    public static String encryptSHA(String data) throws Exception {
        // 验证传入的字符串
        if (null == data || 0 == data.length()) {
            return null;
        }
        // 创建具有指定算法名称的信息摘要
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        // 使用指定的字节数组对摘要进行最后更新
        sha.update(data.getBytes());
        // 完成摘要计算
        byte[] bytes = sha.digest();
        // 将得到的字节数组变成字符串返回
        return byteArrayToHexString(bytes);
    }

    /**
     * SHA 登录时候加密
     *
     * @param data 需要加密的字符串
     * @return 加密之后的字符串
     * @throws Exception
     */
    public static String encryptLoginSHA(String data) throws Exception {
        //字符串拼接
        String pass = data.concat(PASSSALT);
        // 创建具有指定算法名称的信息摘要
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        // 使用指定的字节数组对摘要进行最后更新
        sha.update(pass.getBytes());
        // 完成摘要计算
        byte[] bytes = sha.digest();
        // 将得到的字节数组变成字符串返回
        return byteArrayToHexString(bytes);
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteToHexString(byte b) {
        int ret = b;
        if (ret < 0) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }

    // base64加密
    public static String getBase64(String str) throws Exception {
        byte[] b = null;
        String data = null;
        b = str.getBytes("utf-8");
        if (b != null) {
            data = new String(Base64.encodeBase64(b), "utf-8");
        }
        return data;
    }

    // base64解密
    public static synchronized String getFromBase64(String str){
        byte[] b = null;
        try {
            String result = null;
            if (str != null) {
                b = Base64.decodeBase64(str);
                result = new String(b, "utf-8");
            }
            return result;
        } catch (Exception ex) {
            return str;
        }

    }
}
