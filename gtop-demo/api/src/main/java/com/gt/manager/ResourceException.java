package com.gt.manager;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: rest接口层异常 </p>
 * <p>Author:jmzhang/张际明, 16/09/20</p>
 */
public class ResourceException extends Exception {
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}

	public void code(int code) {
		this.code = code;
	}

	public ResourceException(int errorCode, String message) {
		super(message);
		code(errorCode);
	}

	public ResourceException(String message) {
		super(message);
	}

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
