package com.xuaxi.service.utils;

import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;

public class TokenUtils {

	private static String tokenKey;
	
	public static String genToken(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getTokenFromRequest(ServletRequest request){
		if(tokenKey==null){
			tokenKey=SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("rbac.token.key");
		}
		HttpServletRequest r=(HttpServletRequest) request;
		String authorization=r.getHeader(tokenKey);//先去Header取不到从request取
		if(StringUtils.isEmpty(authorization)){
			authorization=r.getParameter(tokenKey);
		}
		return authorization;
	}
}
