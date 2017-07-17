package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

public class AddressEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 798188452295046400L;

	/**
	 * 区域代码
	 */
	@TableConfig(colName = "t.area_code",operators=QueryOperatorEnum.eq)
	private String area_code;

	/**
	 * 层级，从0开始
	 */
	@TableConfig(colName = "t.level")
	private Integer level;

	/**
	 * 父级区域代码
	 */
	@TableConfig(colName = "t.father",operators=QueryOperatorEnum.eq)
	private String father;

	/**
	 * 区域名称
	 */
	@TableConfig(colName = "t.name")
	private String name;

	/**
	 * 简称
	 */
	@TableConfig(colName = "t.short_name")
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
