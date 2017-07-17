package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

public class AddressDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 619856907905003648L;

	/**
	 * 区域代码
	 */
	private String area_code;

	/**
	 * 层级，从0开始
	 */
	private Integer level;

	/**
	 * 父级区域代码
	 */
	private String father;

	/**
	 * 区域名称
	 */
	private String name;
	
	private Boolean selected;

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * 简称
	 */
	private String short_name;

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

}
