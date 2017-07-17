package com.xuaxi.framework.core.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.xuaxi.framework.core.exceptions.ApiException;

public class Condition implements Serializable{

	private static final long serialVersionUID = -4071721088800790748L;

	private Map<String, String> queryParam;

	private Map<String, String> sortColumn;

	public Map<String, String> getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(Map<String, String> sortColumn) {
		this.sortColumn = sortColumn;
	}

	public Map<String, String> getQueryParam() {
		return queryParam;
	}
	
	public void setQueryParam(Map<String, String> param) {
		this.queryParam=param;
	}

	public void initQueryParam(Map<String, String[]> param) {
		this.queryParam=new HashMap<String, String>();
		if(param==null||param.size()==0){
			return;
		}
		for (String key : param.keySet()) {
			String []value=param.get(key);
			if(value.length>1){
				throw new ApiException(1002);
			}
			queryParam.put(key, value[0]);
		}
	}
}
