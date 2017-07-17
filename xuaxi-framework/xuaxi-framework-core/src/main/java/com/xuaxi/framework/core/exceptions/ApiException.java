package com.xuaxi.framework.core.exceptions;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 2854955381610324018L;

	private int code;

	private int responseCode;

	private String message;
	
	private String responseMessage;
	
	private Object errorTargetData;
	
	public ApiException(int code,String msg,Object errorTargetData) {
		ExceptionBean exception = ExceptionCore.getException(code);
		this.code = code;
		this.responseCode = exception.getResponseCode();
		this.message=exception.getDescribe().replace("{}", msg);
		this.responseMessage = exception.getMessage().get(ExceptionCore.getDefaultLanguage()).replace("{}", msg);
		this.errorTargetData=errorTargetData;
	}
	
	public ApiException(int code,Object errorTargetData) {
		ExceptionBean exception = ExceptionCore.getException(code);
		this.code = code;
		this.responseCode = exception.getResponseCode();
		this.message=exception.getDescribe().replace("{}", "");
		this.responseMessage = exception.getMessage().get(ExceptionCore.getDefaultLanguage()).replace("{}", "");
		this.errorTargetData=errorTargetData;
	}

	public ApiException(int code,String msg) {
		ExceptionBean exception = ExceptionCore.getException(code);
		this.code = code;
		this.responseCode = exception.getResponseCode();
		this.message=exception.getDescribe().replace("{}", msg);
		this.responseMessage = exception.getMessage().get(ExceptionCore.getDefaultLanguage()).replace("{}", msg);
	}
	
	public ApiException(int code){
		ExceptionBean exception = ExceptionCore.getException(code);
		this.code = code;
		this.responseCode = exception.getResponseCode();
		this.message=exception.getDescribe().replace("{}", "");
		this.responseMessage = exception.getMessage().get(ExceptionCore.getDefaultLanguage()).replace("{}", "");
	}

	
	public Object getErrorTargetData() {
		return errorTargetData;
	}

	public void setErrorTargetData(Object errorTargetData) {
		this.errorTargetData = errorTargetData;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getMessage() {
		return message;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
