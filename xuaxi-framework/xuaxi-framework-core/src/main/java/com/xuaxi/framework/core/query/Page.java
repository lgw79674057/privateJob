package com.xuaxi.framework.core.query;

import java.io.Serializable;
import java.util.List;

public class Page<Domain> implements Serializable{

	private static final long serialVersionUID = -2058060021955231260L;

	private List<Domain> datas;
	
	private int pageSize;
	
	private Long totalSize;
	
	private int pageNo;

	public List<Domain> getDatas() {
		return datas;
	}

	public void setDatas(List<Domain> datas) {
		this.datas = datas;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
