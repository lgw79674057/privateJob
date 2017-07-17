package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

import java.sql.Timestamp;

public class ChangeRecordEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 542049970562500608L;

	/**
	 * 变更类型(0:企业基础信息变更  1：企业发票信息变更)
	 */
	@TableConfig(colName = "t.changeType",operators=QueryOperatorEnum.eq)
	private String changeType;

	/**
	 * 企业Id
	 */
	@TableConfig(colName = "t.enterpriseId",operators=QueryOperatorEnum.eq)
	private Long enterpriseId;

	/**
	 * 变更属性
	 */
	@TableConfig(colName = "t.attributeName")
	private String attributeName;

	/**
	 * 变更属性名称
	 */
	@TableConfig(colName = "t.attributeText")
	private String attributeText;

	/**
	 * 变更后内容
	 */
	@TableConfig(colName = "t.afterValue")
	private String afterValue;

	/**
	 * 变更前内容
	 */
	@TableConfig(colName = "t.beforeValue")
	private String beforeValue;

	/**
	 * 变更时间
	 */
	@TableConfig(colName = "t.changeTime")
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
