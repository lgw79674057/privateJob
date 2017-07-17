package com.xuaxi.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xuaxi.domain.LoginAccountsDomain;
import com.xuaxi.domain.UserDomain;
import com.xuaxi.framework.core.entity.User;



public interface IAuthServer {

	Map<String,String> login(Map<String,String> map,HttpServletRequest request);
	
	User getUserByToken(String token);
	
	boolean check(String token);
	
	void logout(String token);
	
	LoginAccountsDomain accountsLogin(List<UserDomain> users,String phone);
	
	boolean checkKey(String phone,String key);
}
