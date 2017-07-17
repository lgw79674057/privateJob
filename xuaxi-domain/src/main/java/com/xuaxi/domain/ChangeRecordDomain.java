package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;
import java.sql.Timestamp;
import java.util.Date;

public class ChangeRecordDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 868829524673628672L;

	/**
	 * 变更类型(0:企业基础信息变更  1：企业发票信息变更)
	 */
	
	public ChangeRecordDomain(){}
	
	public ChangeRecordDomain(String changeType,Long enterpriseId,String attributeName,String attributeText,String afterValue,String beforeValue){
		this.changeType=changeType;
		this.enterpriseId=enterpriseId;
		this.attributeName=attributeName;
		this.attributeText=attributeText;
		this.afterValue=afterValue;
		this.beforeValue=beforeValue;
		this.changeTime=new Timestamp(new Date().getTime());
	}
	
	private String changeType;

	/**
	 * 企业Id
	 */
	private Long enterpriseId;

	/**
	 * 变更属性
	 */
	private String attributeName;

	/**
	 * 变更属性名称
	 */
	private String attributeText;

	/**
	 * 变更后内容
	 */
	private String afterValue;

	/**
	 * 变更前内容
	 */
	private String beforeValue;

	/**
	 * 变更时间
	 */
	private Timestamp changeTime;

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeText() {
		return attributeText;
	}

	public void setAttributeText(String attributeText) {
		this.attributeText = attributeText;
	}

	public String getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}

	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	public Timestamp getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
	}

}
