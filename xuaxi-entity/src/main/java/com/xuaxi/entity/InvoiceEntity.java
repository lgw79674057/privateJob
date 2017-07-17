package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

import java.lang.Boolean;

public class InvoiceEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 44422827128294592L;

	/**
	 * 企业Id
	 */
	@TableConfig(colName = "t.enterpriseId",operators=QueryOperatorEnum.eq)
	private Long enterpriseId;

	/**
	 * 0：非一般纳税人；1：一般纳税人
	 */
	@TableConfig(colName = "t.invoiceType")
	private String invoiceType;

	/**
	 * 企业名称
	 */
	@TableConfig(colName = "t.enterpriseName")
	private String enterpriseName;

	/**
	 * 查询码
	 */
	@TableConfig(colName = "t.viewCode")
	private String viewCode;

	/**
	 * 数据保护
	 */
	@TableConfig(colName = "t.dataLock")
	private Boolean dataLock;

	/**
	 * 纳税识别号
	 */
	@TableConfig(colName = "t.invoiceCode")
	private String invoiceCode;

	/**
	 * 企业地址
	 */
	@TableConfig(colName = "t.address")
	private String address;

	/**
	 * 企业电话
	 */
	@TableConfig(colName = "t.phone")
	private String phone;

	/**
	 * 开户行
	 */
	@TableConfig(colName = "t.bankName")
	private String bankName;

	/**
	 * 开户支行
	 */
	@TableConfig(colName = "t.detilBack")
	private String detilBack;

	/**
	 * 银行账号
	 */
	@TableConfig(colName = "t.bancAccount")
	private String bancAccount;

	/**
	 * 发票附件
	 */
	@TableConfig(colName = "t.filePath")
	private String filePath;

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public Boolean getDataLock() {
		return dataLock;
	}

	public void setDataLock(Boolean dataLock) {
		this.dataLock = dataLock;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDetilBack() {
		return detilBack;
	}

	public void setDetilBack(String detilBack) {
		this.detilBack = detilBack;
	}

	public String getBancAccount() {
		return bancAccount;
	}

	public void setBancAccount(String bancAccount) {
		this.bancAccount = bancAccount;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
