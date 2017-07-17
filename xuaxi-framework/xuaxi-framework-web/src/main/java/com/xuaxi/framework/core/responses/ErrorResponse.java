package com.xuaxi.framework.core.responses;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 3187929309697480497L;

	private int code;

	private String message;
	
	private int responseCode;
	
	private Object errorTargetData;
	
	private String referer;

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public ErrorResponse(int code, String message,int responseCode,Object errorTargetData) {
		this.code = code;
		this.message = message;
		this.responseCode=responseCode;
		this.errorTargetData=errorTargetData;
	}

	public int getCode() {
		return code;
	}

	public Object getErrorTargetData() {
		return errorTargetData;
	}

	public void setErrorTargetData(Object errorTargetData) {
		this.errorTargetData = errorTargetData;
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
}
