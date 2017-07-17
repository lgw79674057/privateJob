package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

import java.lang.Boolean;
import java.math.BigDecimal;

public class CodeEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 360569938184207040L;

	/**
	 * 企业Id
	 */
	@TableConfig(colName = "t.enterpriseId")
	private Long enterpriseId;

	/**
	 * 位码
	 */
	@TableConfig(colName = "t.no",operators=QueryOperatorEnum.eq)
	private String no;

	/**
	 * 位码类型(1：个人、2：企业、3：第三方)
	 */
	@TableConfig(colName = "t.noType")
	private String noType;

	/**
	 * 0：未启用  1：已启用
	 */
	@TableConfig(colName = "t.state")
	private Boolean state;

	/**
	 * 国籍
	 */
	@TableConfig(colName = "t.nationality")
	private String nationality;

	/**
	 * 价格
	 */
	@TableConfig(colName = "t.price")
	private BigDecimal price;

	@TableConfig(colName = "t.noUse")
	private String noUse;

	public String getNoUse() {
		return noUse;
	}

	public void setNoUse(String noUse) {
		this.noUse = noUse;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
