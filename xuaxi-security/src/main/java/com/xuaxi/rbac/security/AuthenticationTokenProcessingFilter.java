package com.xuaxi.rbac.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.xuaxi.framework.core.entity.User;
import com.xuaxi.framework.core.spring.SpringContextUtil;
import com.xuaxi.service.IAuthServer;
import com.xuaxi.service.utils.TokenUtils;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token = TokenUtils.getTokenFromRequest(request);
		if (!StringUtils.isEmpty(token) && !("null").equals(token)){
			if (logger.isDebugEnabled()) {
				logger.debug("Token 值 :{},请求资源:{}", token, ((HttpServletRequest) request).getRequestURI());
			}
			User user = ((IAuthServer) SpringContextUtil.getBean("authServer")).getUserByToken(token);
			if (user == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Token {} 无效", token);
				}
			} else {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
				authentication.setDetails(user);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				if (logger.isDebugEnabled()) {
					logger.debug("Token {} 认证通过，用户名:{}", token,user.getUsername());
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("请求没有传入Token,请求资源:{}", ((HttpServletRequest) request).getRequestURI());
			}
		}
		chain.doFilter(request, response);
	}
}
