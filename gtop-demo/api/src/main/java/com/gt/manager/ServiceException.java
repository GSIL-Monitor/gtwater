package com.gt.manager;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: service层异常 </p>
 * <p>Author:jmzhang/张际明, 16/09/20</p>
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Result result) {
        super(result.message);
        this.code = result.code;
        this.message = result.message;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
