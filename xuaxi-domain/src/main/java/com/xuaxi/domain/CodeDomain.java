package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;
import java.lang.Boolean;
import java.math.BigDecimal;

public class CodeDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 679977741764507776L;

	/**
	 * 企业Id
	 */
	private Long enterpriseId;

	/**
	 * 位码
	 */
	private String no;

	/**
	 * 位码类型(1：个人、2：企业、3：第三方)
	 */
	private String noType;

	/**
	 * 0：未启用  1：已启用
	 */
	private Boolean state;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 价格
	 */
	private BigDecimal price;
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
