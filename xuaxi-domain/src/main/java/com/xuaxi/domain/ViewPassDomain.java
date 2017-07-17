package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

public class ViewPassDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 37432105440186488L;

	/**
	 * 企业Id
	 */
	private Long enterpriseId;

	/**
	 * 用户ID
	 */
	private Long userId;

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
