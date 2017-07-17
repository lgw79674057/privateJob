package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;
import java.lang.Boolean;

public class InvoiceDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 464707914110495488L;

	/**
	 * 企业Id
	 */
	private Long enterpriseId;

	/**
	 * 0：非一般纳税人；1：一般纳税人
	 */
	private String invoiceType;

	/**
	 * 企业名称
	 */
	private String enterpriseName;

	/**
	 * 查询码
	 */
	private String viewCode;

	/**
	 * 数据保护
	 */
	private Boolean dataLock;

	/**
	 * 纳税识别号
	 */
	private String invoiceCode;

	/**
	 * 企业地址
	 */
	private String address;

	/**
	 * 企业电话
	 */
	private String phone;

	/**
	 * 开户行
	 */
	private String bankName;

	/**
	 * 开户支行
	 */
	private String detilBack;

	/**
	 * 银行账号
	 */
	private String bancAccount;

	/**
	 * 发票附件
	 */
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
