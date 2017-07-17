package com.xuaxi.framework.core.query;

import java.io.Serializable;

/**
 * 查詢操作类
 * 
 * @author xiazhijian
 *
 */
public class SortColumn implements Serializable {

	private static final long serialVersionUID = -8471845984292248072L;

	private String column;

	private String property;

	private String operator;

	public SortColumn(){}
	
	public SortColumn(String ...arg){
		property=arg[0];column=arg[1];operator=arg[2];
	}
	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
