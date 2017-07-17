package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

public class ViewPassEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 740818429496360832L;

	/**
	 * 企业Id
	 */
	@TableConfig(colName = "t.enterpriseId",operators=QueryOperatorEnum.eq)
	private Long enterpriseId;

	/**
	 * 用户ID
	 */
	@TableConfig(colName = "t.userId",operators=QueryOperatorEnum.eq)
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
