package com.xuaxi.framework.core.config.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.xuaxi.framework.core.web.enums.HttpMethodEnum;
import com.xuaxi.framework.core.wraper.XSSRequestWrapper;


/**
 * 
 * @ClassName: XSSFilter 
 * @Description: xss 攻击过滤器 
 *
 */
public class XSSFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<String> excludeAPIList = new ArrayList<String>();

	public void init(FilterConfig filterConfig) throws ServletException {
    	String trustAPIs = filterConfig.getInitParameter("exclude");
    	if(!StringUtils.isEmpty(trustAPIs)){
    		String[] array = trustAPIs.split(",");
    		for(String api : array){
    			excludeAPIList.add(api);
    			logger.info("add xss trust API {}" ,api);
    		}
    	}
    	logger.info("XSSFilter init success!");
    }
    
    public void destroy() {
    }
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	String requestAPI = httpRequest.getRequestURI();
    	String contextPath = httpRequest.getContextPath();
    	requestAPI =  requestAPI.replace(contextPath, "");
    	/**
    	 * 对于信任API，不做xss过滤
    	 */
    	for(String trustAPI : excludeAPIList){
    		if(requestAPI.startsWith(trustAPI)){
    			chain.doFilter(request, response);
    			return;
    		}
    	}
    	
    	if(!httpRequest.getMethod().equalsIgnoreCase(HttpMethodEnum.OPTIONS.name()) ){
        	logger.debug("filter request start!"); 
    	    chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    	    logger.debug("filter request end!");
    	} else{
    		chain.doFilter(request, response);
    	}
    } 

}
