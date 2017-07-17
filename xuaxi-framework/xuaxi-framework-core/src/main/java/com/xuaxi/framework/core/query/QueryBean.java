package com.xuaxi.framework.core.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QueryBean implements Serializable{

	private static final long serialVersionUID = -8471845984292248072L;

	private List<QueryColumn<Object>> queryColumn;
	
	private List<SortColumn> sortColumn;
	
	private Long startNum=0L;
	
	private int pageSize=10;
	
	private int pageNo=1;
	
	private boolean page=true;
	
	private Map<String,Object> otherParams;
	
	public Map<String, Object> getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(Map<String, Object> otherParams) {
		this.otherParams = otherParams;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public List<QueryColumn<Object>> getQueryColumn() {
		return queryColumn;
	}

	public void setQueryColumn(List<QueryColumn<Object>> queryColumn) {
		this.queryColumn = queryColumn;
	}

	public List<SortColumn> getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(List<SortColumn> sortColumn) {
		this.sortColumn = sortColumn;
	}
}
