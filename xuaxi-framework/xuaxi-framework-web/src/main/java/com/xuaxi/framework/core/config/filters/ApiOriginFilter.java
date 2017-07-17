package com.xuaxi.framework.core.config.filters;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiOriginFilter implements javax.servlet.Filter {

//	private List<String> list = new ArrayList<String>();

//	private boolean isAll = false;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Credentials", "true");
//		if (isAll) {
			res.addHeader("Access-Control-Allow-Origin", "*");
//		} else {
//			String referer= ((HttpServletRequest) request).getHeader("Referer");
//			res.addHeader("Access-Control-Allow-Origin", getOrigin(referer));
//		}
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
		res.addHeader("Access-Control-Allow-Headers",
				"X-Requested-With,X-HTTP-Method-Override,origin, content-type, accept, authorization,authName,secretKey,applicationCode, SourceUrl, src_url_base, X_Requested_With");
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
	
//	private String getOrigin(String referer){
//		for (String string : list) {
//			if(referer.startsWith(string)){
//				return string;
//			}
//		}
//		return null;
//	}
//
	public void init(FilterConfig filterConfig) throws ServletException {
//		String origin = filterConfig.getInitParameter("crossDomainList");
//		if ("*".equals(origin)) {
//			isAll = true;
//		} else {
//			String domains[] = origin.split(",");
//			for (String string : domains) {
//				list.add(string);
//			}
//		}
	}
}