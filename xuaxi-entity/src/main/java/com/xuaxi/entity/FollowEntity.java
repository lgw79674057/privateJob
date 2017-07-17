package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.QueryConfig;
import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

@QueryConfig(noPaging=true)
public class FollowEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 741410370066141824L;

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
	
	@TableConfig(colName = "t.newChange",operators=QueryOperatorEnum.eq)
	private Boolean newChange;
	
	private String enterpriseName;
	
	private Boolean dataLock;
	
	private Boolean unLock;
	
	private Boolean change;
	private Boolean audit;
	private Boolean disable;

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

	public Boolean getChange() {
		return change;
	}

	public Boolean getNewChange() {
		return newChange;
	}

	public void setNewChange(Boolean newChange) {
		this.newChange = newChange;
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
