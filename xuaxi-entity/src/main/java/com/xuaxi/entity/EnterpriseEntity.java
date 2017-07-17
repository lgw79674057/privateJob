package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

public class EnterpriseEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 61594227116043568L;

	/**
	 * 位号
	 */
	@TableConfig(colName = "t.enterpriseNo",operators={QueryOperatorEnum.eq,QueryOperatorEnum.eq})
	private String enterpriseNo;

	/**
	 * 企业名称
	 */
	@TableConfig(colName = "t.name",operators={QueryOperatorEnum.eq,QueryOperatorEnum.like})
	private String name;

	/**
	 * 企业简称
	 */
	@TableConfig(colName = "t.abbreviation")
	private String abbreviation;

	/**
	 * 英文全称
	 */
	@TableConfig(colName = "t.enName")
	private String enName;

	/**
	 * 英文简称
	 */
	@TableConfig(colName = "t.enAbbreviation")
	private String enAbbreviation;

	/**
	 * 通信地址1
	 */
	@TableConfig(colName = "t.address1")
	private String address1;

	/**
	 * 英文通信地址1
	 */
	@TableConfig(colName = "t.enAddress1")
	private String enAddress1;

	/**
	 * 英文通信地址2
	 */
	@TableConfig(colName = "t.enAddress2")
	private String enAddress2;

	/**
	 * 通信地址2
	 */
	@TableConfig(colName = "t.address2")
	private String address2;

	/**
	 * 英文通信地址3
	 */
	@TableConfig(colName = "t.enAddress3")
	private String enAddress3;

	/**
	 * 通信地址3
	 */
	@TableConfig(colName = "t.address3")
	private String address3;

	/**
	 * 企业电话
	 */
	@TableConfig(colName = "t.phone")
	private String phone;

	/**
	 * 联系人
	 */
	@TableConfig(colName = "t.contacts")
	private String contacts;

	/**
	 * 联系电话
	 */
	@TableConfig(colName = "t.cPhone")
	private String cPhone;

	/**
	 * 传真
	 */
	@TableConfig(colName = "t.fax")
	private String fax;

	/**
	 * 营业范围
	 */
	@TableConfig(colName = "t.businessScope")
	private String businessScope;

	/**
	 * 企业法人
	 */
	@TableConfig(colName = "t.legalPerson")
	private String legalPerson;

	/**
	 * 查询码
	 */
	@TableConfig(colName = "t.viewCode")
	private String viewCode;

	/**
	 * 数据保护(0不保护 1保护)
	 */
	@TableConfig(colName = "t.dataLock")
	private Boolean dataLock;

	/**
	 * 禁用（0启用1禁用）
	 */
	@TableConfig(colName = "t.disable")
	private Boolean disable;
	
	@TableConfig(colName = "t.auditRemark")
	private String auditRemark;
	
	@TableConfig(colName = "t.fullAddress1")
	private String fullAddress1;
	
	@TableConfig(colName = "t.fullAddress2")
	private String fullAddress2;
	
	@TableConfig(colName = "t.fullAddress3")
	private String fullAddress3;

	@TableConfig(colName = "t.address1Id")
	private Long address1Id;
	
	@TableConfig(colName = "t.address2Id")
	private Long address2Id;
	
	@TableConfig(colName = "t.address3Id")
	private Long address3Id;

	@TableConfig(colName = "t.enterprisePic")
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

	/**
	 * 审核（0是1否）
	 */
	@TableConfig(colName = "t.audit",operators=QueryOperatorEnum.eq)
	private Boolean audit;
	
	private Boolean unLock;

	public Boolean getUnLock() {
		return unLock;
	}

	public void setUnLock(Boolean unLock) {
		this.unLock = unLock;
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
