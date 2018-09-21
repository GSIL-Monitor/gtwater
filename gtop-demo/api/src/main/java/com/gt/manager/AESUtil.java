package com.gt.manager;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.Security;
import java.util.Map;


/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description:  </p>
 * <p>Author:xiaochangdong, 2017/3/17</p>
 */
public class AESUtil {
    /** 密钥算法 */
    //private static final String KEY_ALGORITHM = "AES";
    //private static final int KEY_SIZE = 128;
    /**
     * 加密/解密算法/工作模式/填充方法
     */
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    /* 密钥 */
    private static final String KEY = "Immerexabcdefghi";

    /* IV */
    private static final String IV = "0000000000000000";

    public static String initKey() throws Exception{
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的16进制编码字符串
        return bytesToHexString(skey.getEncoded());
    }




    /**
     * <p>Description: 加密</p>
     * <p> * @param file
     *
     * @param destFile</p> <p>author xiaochangdong </p>
     *                     <p>date 2017/3/17 18:58 </p>
     *                     <p>return </p>
     */
    public static void encrypt(String file, String destFile, String tsKey) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key key = new SecretKeySpec(tsKey.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        //cipher.init(Cipher.ENCRYPT_MODE, key);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    public static void encrypt2(String file, String destFile, byte [] keyByte) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key key = new SecretKeySpec(keyByte, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        //cipher.init(Cipher.ENCRYPT_MODE, key);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    /**
     * <p>Description: 这里用一句话描述这个方法的作用</p>
     * <p> * @param file
     *
     * @param dest</p> <p>author xiaochangdong </p>
     *                 <p>date 2017/3/17 19:35 </p>
     *                 <p>return </p>
     */
    public static void decrypt(String file, String dest, String tsKey) throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key key = new SecretKeySpec(tsKey.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            System.out.println();
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }
//    public static void traverseFolderEncrypt(String sourceFilePath, String destFilePath, HashMap<String, String> tsMap) throws Exception {
//        File file = new File(sourceFilePath);
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            if (files.length == 0) {
//                System.out.println("文件夹是空的!");
//                return;
//            } else {
//                for (File file2 : files) {
//                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
//                        traverseFolderEncrypt(file2.getAbsolutePath());
//                    } else {
//                        //System.out.println("文件:" + file2.getAbsolutePath());
//                        //encrypt(file2.getAbsolutePath(), file2.getAbsolutePath() + "encrypt");
//                        decrypt(file2.getAbsolutePath(), file2.getAbsolutePath() + "decrypt");
//                    }
//                }
//            }
//        } else {
//            System.out.println("文件不存在!");
//        }
//    }

    /**
     * <p>Description: 对一个目录下的文件加密</p>
     * <p> * @param path</p>
     * <p>author xiaochangdong </p>
     * <p>date 2017/3/17 19:39 </p>
     * <p>return </p>
     */
    public static void traverseFolderEncrypt(String sourceFilePath, String destFilePath, Map<String, String> tsMap) {
        File file = new File(sourceFilePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println(sourceFilePath + "文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    String tsKey = tsMap.get(file2.getName());
                    if (tsKey == null) {
                        //过滤掉缩略图
                        continue;
                    }
                    try {
                        encrypt(file2.getAbsolutePath(), destFilePath + File.separator + file2.getName(), tsKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public static byte[] hexStringToByte(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
        bb.flip ();
        CharBuffer cb = cs.decode (bb);

        return cb.array();
    }

    /**
    * <p>Description: 16进制字符串转字符串</p>
    * <p>author xiaochangdong </p>
    * <p>date 2017/6/1 9:40 </p>
    * <p>return </p>
    */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "ISO8859-1");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String string2HexString(String strPart) {
        String hexString = "";
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString = hexString + strHex;
        }
        return hexString;
    }

    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }


    public static void main(String[] args) throws Exception {
//        AESUtil td = new AESUtil();
        //td.traverseFolderEncrypt("E:\\testAES");
        //AESUtil.encrypt("C:\\Users\\Administrator\\Desktop\\test1\\test.mp4", "C:\\Users\\Administrator\\Desktop\\test1\\encry.mp4", ); //加密
        //td.decrypt("D:/test/cam17jimi.mp4", "D:/test/cam17jiemi.mp4"); //解密
        //生成秘钥
//        String password="testkey";
//        try {
//            KeyGenerator kg = KeyGenerator.getInstance("AES");
//            kg.init(128);
//            //要生成多少位，只需要修改这里即可128, 192或256
//            int loop = 100;
//            while(loop-- > 0){
//                SecretKey sk = kg.generateKey();
//                byte[] key = sk.getEncoded();
//                String str = Base64.encodeBase64String(key);
//                System.out.println(str);
//                System.out.println("十六进制密钥长度为" + str.length());
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            System.out.println("没有此算法。");
//        }
//        String key1 = "yaIA5gz+HqWWNYfH";
//        byte[] b = key1.getBytes(); //如果是这样的话，可以。
//        String result = "";
//        for(int i = 0; i < b.length; i++){
//            result += (char)b[i];
//        }
//        System.out.println(b.length);
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        byte [] key = skey.getEncoded();
        //String str = Base64.encodeBase64String(key);
//        String result = "";
//        for(int i = 0; i < key.length; i++){
//            result += (char)key[i];
//        }
//        System.out.println(result);
//        System.out.println(result.length());
//
        String s = bytesToHexString(key);
        System.out.println(s);
        System.out.println("十六进制密钥长度为"+s.length());
        System.out.println("数组长度为"+key.length);
        //String keyStr = new String(key, "UTF-8");
        //String keySTr = Encoding.ASCII.GetString()
        //System.out.println("key=" + keyStr);
        //System.out.println("length = " + keyStr.length());
        String key8 = hexStringToString(s);
        //String key16 = string2HexString(key8);
        System.out.println("8位字符串=" + key8);
        System.out.println("8位字符串length=" + key8.length());
        //System.out.println("16字符串=" + key16);
        //System.out.println("16字符串length=" + key16.length());
        byte [] key2 = hexStringToBytes(s);
        FileInputStream writer;
        try {
            //writer = new FileInputStream("E:/backup/token.txt", "GB2312");
            File f = new File("E:/backup/aes128.key");
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter osw = null;
            osw = new OutputStreamWriter(fos, "ISO8859-1");
            BufferedWriter bw = null;
            bw = new BufferedWriter(osw);
            bw.write(key8);
            bw.close();
            fos.close();
            //FileInputStream fis=new FileInputStream("E:/backup/token.txt");
            //OutputStreamWriter osw=new OutputStreamWriter(fis);
           // writer.write(key8);
            //writer.flush();
            //writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        byte [] key1 = hexStringToByte(s);
//        String result1 = "";
//        for(int i = 0; i < key1.length; i++){
//            result1 += (char)key1[i];
//        }
//        System.out.println(result1);
//        System.out.println(result1.length());
        AESUtil.encrypt2("C:\\Users\\ZDWX\\Desktop\\test1\\testencrypt\\output10.ts", "C:\\Users\\ZDWX\\Desktop\\test1\\testencrypt\\output10en.ts", key2); //加密
        AESUtil.encrypt2("C:\\Users\\ZDWX\\Desktop\\test1\\testencrypt\\output11.ts", "C:\\Users\\ZDWX\\Desktop\\test1\\testencrypt\\output11en.ts", key2);
        //AESUtil.decrypt("C:\\Users\\ZDWX\\Desktop\\test1\\encry.mp4", "C:\\Users\\ZDWX\\Desktop\\test1\\decry.mp4", key8);
        //创建密钥生成器
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        //初始化密钥
//        String strKey = "wlkjefklfdklmsljdlfk";
//        keyGenerator.init(new SecureRandom(strKey.getBytes()));
//        //生成密钥
//        SecretKey getKey = keyGenerator.generateKey();
//        System.out.println("生成密钥:"+byteToHexString(getKey.getEncoded ())+"----"+byteToHexString(getKey.getEncoded ()).length());
    }


}
