package com.gt.manager.sign.response;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
public enum SignResult {
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    //9000-9999	系统(Token、Session等)
    BAD_PARAMETERS(9000, "错误的参数"),
    MISSING_APIKEY(9001, "缺少请求参数appKey"),
    MISSING_SIGNATURE(9002, "缺少请求参数signature"),
    BAD_APIKEY(9003, "错误的appKey"),
    BAD_SIGNATURE(9004, "错误的signature"),
    MISSING_SESSION(9005, "缺少请求参数session"),
    BAD_SESSION(9006, "错误的或者过期的session"),
    MISSING_DEVICEID(9007, "缺少请求参数deviceId"),
    MISSING_USERID(9008, "缺少请求参数userId"),
    MISSING_USERNAME(9009, "缺少请求参数userName"),
    MISSING_TOKEN(9010, "缺少请求参数token"),
    BAD_TOKEN(9011, "错误的参数token"),
    NO_AUTHO(9012, "没有权限访问"),
    NOT_ALLOWED(9013, "不允许访问"),

    SERVER_CONNECTED_ERROR(9995, "网关处理异常"),
    ILLEGAL_CHARACTERS(9996, "参数{0}中含有非法字符"),
    PARAMS_TYPE_WRONG(9997, "参数{0}的类型不正确"),
    PARAMS_NOT_NULL(9998, "参数{0}不能为空"),
    PARAMS_ILLEGAL_CHARACTERS(9999, "参数{0}中含有非法字符");

    private final int code;
    private final String message;

    SignResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static SignResult valueOf(int code) {
        for (SignResult r : values()) {
            if (r.code == code) {
                return r;
            }
        }
        throw new RuntimeException("Undefined Result " + code);
    }

}
