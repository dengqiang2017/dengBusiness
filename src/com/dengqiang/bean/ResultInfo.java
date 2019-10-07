package com.dengqiang.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 请求结果封装对象
 * 
 * @author dengqiang
 * 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResultInfo {
	private boolean success = false;
	private String msg;
	private Integer error_code;

	public ResultInfo(boolean success) {
		super();
		this.success = success;
	}

	public ResultInfo() {}

	public ResultInfo(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public ResultInfo(boolean success, String msg, Integer error_code) {
		super();
		this.success = success;
		this.msg = msg;
		this.error_code = error_code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getError_code() {
		return error_code;
	}

	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}

}
