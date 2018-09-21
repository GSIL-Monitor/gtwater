package com.gt.gtop.entity.base;

import org.springframework.stereotype.Component;


import java.io.Serializable;

/**
 * 数据消息实体类
 * @author liwei
 */

@Component("dataMessage")
public class DataMessage implements Serializable {
	/**
	 * default SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 成功
	 */
	public static int RESULT_SUCESS = 0;
	/**
	 * 失败
	 */
	public static int RESULT_FAILED = -1;
	/**
	 * 参数错误
	 */
	public static int RESULT_PARAMS_ERR = -2;
	/**
	 * 非法请求
	 */
	public static int RESULT_ILLEGAL_REQUEST = -3;
	/**
	 * 系统异常
	 */
	public static int RESULT_SYSTEM_ERR = -4;
	/**
	 * 登录失效
	 */
	public static int RESULT_LOGIN_OUT = -5;
	/**
	 * 权限不足
	 */
	public static int RESULT_ROLE_NOT = -6;
	// Fields
	/**
	 * 操作结果[0-成功 | -1-失败 | -2-参数错误 | -3 非法请求 |-4-系统异常|-5-登陆失效]
	 */
	private int result;
	/**
	 * 数据对象
	 */
	private Object data;
	/**
	 * 消息内容
	 */
	private String message;

	// Empty Constructor
	public DataMessage() {
		super();
	}

	// Full Constructor
	public DataMessage(int result, Object data, String message) {
		super();
		this.result = result;
		this.data = data;
		this.message = message;
	}

	// Property accessors

	public int getResult() {
		return result;
	}

	public DataMessage setResult(int result) {
//		this.clearData();
		this.result = result;
		return this;
	}

	public Object getData() {
		return data;
	}

	public DataMessage setData(Object data) {
		this.data = data;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public DataMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 清空上一次数据结果
	 */
	public void clearData() {
		this.setMessage(null);
		this.setData(null);
	}

}