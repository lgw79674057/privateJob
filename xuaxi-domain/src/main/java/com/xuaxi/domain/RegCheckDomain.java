package com.xuaxi.domain;

import java.io.Serializable;

public class RegCheckDomain implements Serializable{

	private static final long serialVersionUID = 7541773421309007096L;

	private String userNo;
	
	private String loginName;
	
	private String smsCode;
	
	private String telphone;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
}
