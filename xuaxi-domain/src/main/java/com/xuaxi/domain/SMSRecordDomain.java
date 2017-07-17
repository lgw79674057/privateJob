package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;
import java.sql.Timestamp;

public class SMSRecordDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 840788174777004416L;

	/**
	 * 接收号码
	 */
	private String phone;

	/**
	 * 短信内容
	 */
	private String smsContect;

	/**
	 * 发送时间
	 */
	private Timestamp sendTime;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsContect() {
		return smsContect;
	}

	public void setSmsContect(String smsContect) {
		this.smsContect = smsContect;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

}
