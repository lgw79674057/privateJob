package com.xuaxi.framework.core.query;

import java.io.Serializable;

import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;

public class QuerySystemField implements Serializable {

	private static final long serialVersionUID = 7732856133058697424L;

	private static String conditonSplit;

	private static String page;

	private static String pageNo;

	private static String pageSize;

	private static String sort;

	private static String valueSplit;

	public static String getConditonSplit() {
		if (conditonSplit == null) {
			conditonSplit = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("query.conditonSplit");
		}
		return conditonSplit;
	}

	public static String getPage() {
		if (page == null) {
			page = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("query.page");
		}
		return page;
	}

	public static String getPageNo() {
		if (pageNo == null) {
			pageNo = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("query.pageNo");
		}
		return pageNo;
	}

	public static String getPageSize() {
		if (pageSize == null) {
			pageSize = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("query.pageSize");
		}
		return pageSize;
	}

	public static String getSort() {
		if (sort == null) {
			sort = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("query.sort");
		}
		return sort;
	}

	public static String getValueSplit() {
		if (valueSplit == null) {
			valueSplit = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("query.valueSplit");
		}
		return valueSplit;
	}
}
