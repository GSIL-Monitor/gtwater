package com.gt.manager;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: 常量类 </p>
 * <p>Author:jmzhang/张际明, 16/08/25</p>
 */
public class Constant {

    public final static String APPLICATION_JSON_UTF8 = "application/json;charset=utf-8";
    public final static String DATE_PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public final static String SYSTEM_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    public final static String ENCODING = "utf-8";
    public final static String APPLICATION_TEXT = "text/plain";

    //http头信息,api-gateway验证用
    public final static String HEADER_PUBLIC_KEY = "publickey";
    public final static String HEADER_SIGNATURE_KEY = "signature";
    public final static String HEADER_TIMESTAMP_KEY = "timestamp";


    //http头信息,api-gateway签名，权限控制用
    public final static String HEADER_APPKEY_KEY = "appKey";
    public final static String HEADER_TOKEN_KEY = "token";
    public final static String HEADER_SESSION_KEY = "session";

    /**
     * 四大分类
     **/
    public static final Integer CONTENT_APP_TYPE = 1;
    public static final Integer CONTENT_IMG_TYPE = 2;
    public static final Integer CONTENT_SDK_TYPE = 3;
    public static final Integer CONTENT_VIDEO_TYPE = 4;


    public static final String LANGUAGE_JUYUWANG_CN = "局域网";
    public static final String LANGUAGE_JUYUWANG = "juyuwang";
    public static final String LANGUAGE_LOCALHOST_CN = "本机地址";
    public static final String LANGUAGE_LOCALHOST = "benjidizhi";
    public static final String LANGUAGE_ZHONGGUO = "zhongguo";
    public static final String LANGUAGE_RIBEN = "riben";
    public static final String LANGUAGE_HANGUO = "hanguo";
    public static final String LANGUAGE_MEIGUO = "meiguo";

    public static final String BASE_PATH = "/api/*";

    //设置网站域名
    public static final String REQUEST_NETWORK_URL="http://test.jinjixiadan.com";
}
