package com.xuaxi.framework.core.responses;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable{

	private static final long serialVersionUID = 1662593076380888508L;

	private boolean status;
	
	private T result;
	
	private ErrorResponse error;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}
}
