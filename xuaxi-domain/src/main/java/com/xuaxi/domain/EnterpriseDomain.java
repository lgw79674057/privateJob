package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

public class EnterpriseDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 109717852239333840L;

	/**
	 * 位号
	 */
	private String enterpriseNo;

	/**
	 * 企业名称
	 */
	private String name;

	/**
	 * 企业简称
	 */
	private String abbreviation;

	/**
	 * 英文全称
	 */
	private String enName;

	/**
	 * 英文简称
	 */
	private String enAbbreviation;

	/**
	 * 通信地址1
	 */
	private String address1;

	/**
	 * 英文通信地址1
	 */
	private String enAddress1;

	/**
	 * 英文通信地址2
	 */
	private String enAddress2;

	/**
	 * 通信地址2
	 */
	private String address2;

	/**
	 * 英文通信地址3
	 */
	private String enAddress3;

	/**
	 * 通信地址3
	 */
	private String address3;

	/**
	 * 企业电话
	 */
	private String phone;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 联系电话
	 */
	private String cPhone;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 营业范围
	 */
	private String businessScope;

	/**
	 * 企业法人
	 */
	private String legalPerson;

	/**
	 * 查询码
	 */
	private String viewCode;

	/**
	 * 数据保护(0不保护 1保护)
	 */
	private Boolean dataLock;

	/**
	 * 禁用（0启用1禁用）
	 */
	private Boolean disable;

	/**
	 * 审核（0是1否）
	 */
	private Boolean audit;
	
	private String sync;
	
	private Boolean unLock;
	
	private String auditRemark;
	
	private String fullAddress1;
	
	private String fullAddress2;
	
	private String fullAddress3;

	private Long address1Id;
	
	private Long address2Id;
	
	private Long address3Id;

	private String enterprisePic;

	public String getEnterprisePic() {
		return enterprisePic;
	}

	public void setEnterprisePic(String enterprisePic) {
		this.enterprisePic = enterprisePic;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getFullAddress1() {
		return fullAddress1;
	}

	public void setFullAddress1(String fullAddress1) {
		this.fullAddress1 = fullAddress1;
	}

	public String getFullAddress2() {
		return fullAddress2;
	}

	public void setFullAddress2(String fullAddress2) {
		this.fullAddress2 = fullAddress2;
	}

	public String getFullAddress3() {
		return fullAddress3;
	}

	public void setFullAddress3(String fullAddress3) {
		this.fullAddress3 = fullAddress3;
	}

	public Long getAddress1Id() {
		return address1Id;
	}

	public void setAddress1Id(Long address1Id) {
		this.address1Id = address1Id;
	}

	public Long getAddress2Id() {
		return address2Id;
	}

	public void setAddress2Id(Long address2Id) {
		this.address2Id = address2Id;
	}

	public Long getAddress3Id() {
		return address3Id;
	}

	public void setAddress3Id(Long address3Id) {
		this.address3Id = address3Id;
	}

	public Boolean getUnLock() {
		return unLock;
	}

	public void setUnLock(Boolean unLock) {
		this.unLock = unLock;
	}

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public String getEnterpriseNo() {
		return enterpriseNo;
	}

	public void setEnterpriseNo(String enterpriseNo) {
		this.enterpriseNo = enterpriseNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnAbbreviation() {
		return enAbbreviation;
	}

	public void setEnAbbreviation(String enAbbreviation) {
		this.enAbbreviation = enAbbreviation;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getEnAddress1() {
		return enAddress1;
	}

	public void setEnAddress1(String enAddress1) {
		this.enAddress1 = enAddress1;
	}

	public String getEnAddress2() {
		return enAddress2;
	}

	public void setEnAddress2(String enAddress2) {
		this.enAddress2 = enAddress2;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getEnAddress3() {
		return enAddress3;
	}

	public void setEnAddress3(String enAddress3) {
		this.enAddress3 = enAddress3;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getcPhone() {
		return cPhone;
	}

	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
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

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

}
