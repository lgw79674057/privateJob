package com.xuaxi.framework.core.query;

import java.io.Serializable;

/**
 * 查詢操作类
 * 
 * @author xiazhijian
 *
 */
public class QueryColumn<T> implements Serializable {

	private static final long serialVersionUID = -8471845984292248072L;

	private String column;

	private String property;

	private String operator;

	private T value;
	
	public QueryColumn(){}
	
	public QueryColumn(String property,String column,String operator,T value){
		this.column=column;this.property=property;this.operator=operator;this.value=value;
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
