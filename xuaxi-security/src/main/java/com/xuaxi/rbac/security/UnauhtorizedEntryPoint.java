package com.xuaxi.rbac.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.alibaba.fastjson.JSON;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.utils.SecurityContextHelper;

public class UnauhtorizedEntryPoint implements AuthenticationEntryPoint{

	public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		arg1.setContentType("application/json;charset=UTF-8");
		arg1.setCharacterEncoding("UTF-8");
		ApiException e = new ApiException(SecurityContextHelper.isLogin()? 403:401);
		arg1.setStatus(e.getResponseCode());
		PrintWriter out = arg1.getWriter();
		out.println(JSON.toJSON(ResponseBuilder.buildFaildResponse(e,arg0.getHeader("Referer")).getEntity()));
		out.flush();
	}

}
