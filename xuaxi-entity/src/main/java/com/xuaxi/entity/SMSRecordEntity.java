package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import java.sql.Timestamp;

public class SMSRecordEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 535427456889108736L;

	/**
	 * 接收号码
	 */
	@TableConfig(colName = "t.phone")
	private String phone;

	/**
	 * 短信内容
	 */
	@TableConfig(colName = "t.smsContect")
	private String smsContect;

	/**
	 * 发送时间
	 */
	@TableConfig(colName = "t.sendTime")
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
