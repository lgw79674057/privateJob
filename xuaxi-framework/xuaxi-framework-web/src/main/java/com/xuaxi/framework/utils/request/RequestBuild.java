package com.xuaxi.framework.utils.request;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xuaxi.framework.core.query.Condition;

public class RequestBuild {

	@SuppressWarnings("unchecked")
	public static Condition build(HttpServletRequest request){
		Condition condition=new Condition();
		condition.initQueryParam(request.getParameterMap());
		return condition;
	}
	
	public static Condition build(Map<String,String> requestParam){
		Condition condition=new Condition();
		condition.setQueryParam(requestParam);
		return condition;
	}
}
