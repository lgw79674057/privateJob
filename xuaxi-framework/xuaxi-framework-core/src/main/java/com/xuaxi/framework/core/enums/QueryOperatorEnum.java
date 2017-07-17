package com.xuaxi.framework.core.enums;

import org.springframework.util.StringUtils;

/**
 * @author xiazhijian
 * @date 2016年10月10日 上午11:04:14
 * @Description 查询操作枚举
 * @FileName QueryOperatorEnum.java
 */
public enum QueryOperatorEnum {
	none("none", "none"), all("all", "all"), lt("lt", "<"), lte("lte", "<="), gt("gt", ">"), gte("gte", ">="), eq("eq",
			"="), ne("ne", "!="), in("in", "in"), like("like", "like"), llike("llike", "like"), rlike("rlike", "like");
	private String op;
	private String dbOp;

	private QueryOperatorEnum(String op, String dbOp) {
		this.op = op;
		this.dbOp = dbOp;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getDbOp() {
		return dbOp;
	}

	public void setDbOp(String dbOp) {
		this.dbOp = dbOp;
	}

	public static QueryOperatorEnum getByOpName(String op) {
		if (StringUtils.isEmpty(op) || "all".equals(op) || "none".equals(op)) {
			return null;
		}
		for (QueryOperatorEnum ope : QueryOperatorEnum.values()) {
			if (op.equals(ope.op)) {
				return ope;
			}
		}
		return null;
	}
}
