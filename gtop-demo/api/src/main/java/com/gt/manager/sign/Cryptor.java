package com.gt.manager.sign;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class Cryptor {

    // sha1加密
    public static String encodeSHA1(String sourceString) {
        String resultString = null;
        try {
            resultString = sourceString;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
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

    public static String encodeMD5(String sourceString) {
        String resultString = null;
        try {
            resultString = sourceString;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return resultString;
    }

    public static String HashObject(Object valueToHash) {
        return HashObject(valueToHash, "SHA1");
    }

    public static String HashObject(Object valueToHash, String hashAlgorithm) {
        String s = hashAlgorithm.toUpperCase();
        if (s.equals("SHA1") || s.equals("MD5")) {
            MessageDigest md;
            try {
                md = MessageDigest
                        .getInstance(hashAlgorithm.equals("MD5") ? "MD5"
                                : "SHA-1");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            return toBase64String(md.digest(getBytes(valueToHash)));
        }
        throw new UnsupportedOperationException("加密算法不支持");
    }

    private static byte[] getBytes(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return (bos.toByteArray());
        } catch (IOException e) {
            return null;
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (Throwable e) {
                    //
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (Throwable e) {
                    //
                }
        }
    }

    public static String toBase64String(byte[] data) {
        return Base64.encode(data);
        // return new BASE64Encoder().encode(data);
    }

    public static byte[] fromBase64String(String base64Str) {

        // try {
        // return new BASE64Decoder().decodeBuffer(base64Str);
        // } catch (IOException e) {
        // throw new RuntimeException(e);
        // }

        return Base64.decode(base64Str);
    }

    public static String MD5Pwd(String pwd) {
        // return System.Web.Security.FormsAuthentication
        // .HashPasswordForStoringInConfigFile(pwd, "md5")
        // .ToLower();
        return encodeMD5(pwd).toLowerCase();
    }

    public static byte[] DESEncrypt(byte[] data, String key) {
        return null;// TODO:
    }

    public static byte[] DESDecrypt(byte[] data, String key) {
        return null;// TODO:
    }

    public static String DESEncrypt(String data, String key) {
        try {
            return toBase64String(DESEncrypt(data.getBytes("UTF-8"), key));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String DESDecrypt(String data, String key) {
        byte[] d = fromBase64String(data);
        try {
            return new String(DESEncrypt(d, key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GenerateSalt() {
        byte[] buffer = new byte[16];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) (1000 * Math.random());
        }
        return toBase64String(buffer);
    }

    public static String debug(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        System.out.println("L=" + bytes.length);
        for (byte b : bytes) {
            String str = b + "|";
            sb.append(str);
            // if((b<0)){
            // System.out.println(b+"|"+(b+256));
            // }
        }
        return sb.toString();
    }

    public static String HashString(String pass, String salt) {
        byte[] buffer1;
        buffer1 = getCSharpBytes(pass);// .getBytes("UTF-8");
        byte[] buffer2 = fromBase64String(salt);
        byte[] buffer3 = new byte[buffer2.length + buffer1.length];
        System.arraycopy(buffer2, 0, buffer3, 0, buffer2.length);
        System.arraycopy(buffer1, 0, buffer3, buffer2.length, buffer1.length);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = md.digest(buffer3);
        return toBase64String(digest);
    }

    @SuppressWarnings("unchecked")
    public static byte[] getCSharpBytes(String str) {
        LinkedList bs = new LinkedList();
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            try {
                byte[] bytes = s.getBytes("UTF-8");
                if (bytes.length == 1) {
                    bs.add(bytes[0]);
                    byte e = 0;
                    bs.add(e);
                } else {
                    for (int j = 0; j < bytes.length; j++)
                        bs.add(bytes[j]);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        byte[] re = new byte[bs.size()];
        int idx = 0;
        for (Object b1 : bs) {
            re[idx++] = (Byte) b1;
        }
        return re;
    }
//
   public static void main(String[] args) {
//
         System.out.println("******************\n\n");
         System.out.println("uZ5AMIYSxK+ehntORvAmgw==");
         System.out.println(HashString("123456", "47i9mau4Z6nUc3PL3qZsLg=="));
    //     byte b = 0;
//        System.out.println(b);
//        System.out.println(b + "|");
//    }
//
//    public static void main1(String[] args) {
//        String salt = GenerateSalt();
//        System.out.println(salt);
//        String pass = "123321";
//        System.out.println(HashString(pass, salt));
//        System.out.println("******************\n\n");
//        System.out.println("uZ5AMIYSxK+ehntORvAmgw==");
//        System.out.println(HashString("123456", "47i9mau4Z6nUc3PL3qZsLg=="));
//        System.out.println("******************\n\n");
//        System.out.println("aCQybnqiQOl1b/Zm9m681g==");
//        String salt1 = "VCsXYqiemIPhoxra1FgxgQ==";
//        byte[] bytes = fromBase64String(salt1);
//        // for(byte b:bytes ){
//        // System.out.println(b+"|"+(b<0));
//        // // if((b<0)){
//        // // System.out.println(b+"|"+(b+256));
//        // // }
//        // }
//        // System.out.println();
//        System.out.println(HashString("123456", salt1));
//        byte a = -93;
//        System.out.println(a);
//        a = (byte) (a + 256);
//        System.out.println(a);
//        a = (byte) 169;
//        System.out.println(toBase64String(new byte[]{a}));
//        System.out.println(a);
//        String s = "1111";
//
//        // b59c67bf196a4758191e42f76670ceba
//        // lWTCf81TLt57KZihkbeJmQ==
//        // TUQ1
//        System.out.println(MD5Pwd(s));
//        System.out.println(HashObject(s, "MD5"));
//        System.out.println(toBase64String("MD5".getBytes()));
//        System.out.println(fromBase64String("TUQ1"));
//
    }
}
