package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

public class FollowDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 303678760151143040L;

	/**
	 * 企业Id
	 */
	private Long enterpriseId;

	/**
	 * 用户ID
	 */
	private Long userId;
	
	private String enterpriseName;
	
	private Boolean dataLock;
	
	private Boolean unLock;
	
	private Boolean change;
	
	private Boolean newChange;
	private Boolean audit;
	private Boolean disable;

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Boolean getNewChange() {
		return newChange;
	}

	public void setNewChange(Boolean newChange) {
		this.newChange = newChange;
	}

	public Boolean getChange() {
		return change;
	}

	public void setChange(Boolean change) {
		this.change = change;
	}

	public Boolean getUnLock() {
		return unLock;
	}

	public void setUnLock(Boolean unLock) {
		this.unLock = unLock;
	}
	
	public Boolean getDataLock() {
		return dataLock;
	}

	public void setDataLock(Boolean dataLock) {
		this.dataLock = dataLock;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
