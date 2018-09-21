package com.gt.manager.sign.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * <p>ClassName: SignResponse</p>
 * <p>Description: 签名控制用SignResponse</p>
 * <p>author zhouhe</p>
 * <p>date 2016/12/16 13:44</p>
 */
@XmlRootElement
public class SignResponse<T> implements Serializable {

    private static final long serialVersionUID = 2781574908412412425L;

    /**
     * 成功标志
     */
    public static final int OK = 0;
    /**
     * 失败标志
     */
    public static final int ERROR = 1;

    private int code;
    private String message;
    private T value;

    public SignResponse() {
    }

    public SignResponse(T value) {
        this.value = value;
    }

    public SignResponse(int code, String message, T value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public SignResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SignResponse(int code, T value) {
        this.code = code;
        this.value = value;
    }

    public SignResponse(int code) {
        this.code = code;
    }

    public SignResponse(SignResult signResult) {
        this.code = signResult.getCode();
        this.message = signResult.getMessage();
    }

    public SignResponse(SignResult signResult, T value) {
        this.code = signResult.getCode();
        this.message = signResult.getMessage();
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
