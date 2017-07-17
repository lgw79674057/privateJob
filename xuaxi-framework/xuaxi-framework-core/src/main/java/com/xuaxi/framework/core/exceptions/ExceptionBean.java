package com.xuaxi.framework.core.exceptions;

import java.io.Serializable;
import java.util.Map;

public class ExceptionBean implements Serializable{

	private static final long serialVersionUID = -6921459530988154892L;

	private Integer id;
	
	private Integer responseCode;
	
	private String describe;
	
	private Map<String,String> message;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Map<String,String> getMessage() {
		return message;
	}

	public void setMessage(Map<String,String> message) {
		this.message = message;
	}
}
