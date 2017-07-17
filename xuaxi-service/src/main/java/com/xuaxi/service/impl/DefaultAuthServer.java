package com.xuaxi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.domain.LoginAccountsDomain;
import com.xuaxi.domain.UserDomain;
import com.xuaxi.framework.core.entity.User;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.service.IAuthServer;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IUserService;
import com.xuaxi.service.utils.Constant;
import com.xuaxi.service.utils.SMSUtil;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.service.utils.TokenUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DefaultAuthServer implements IAuthServer {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${rbac.token.key}")
	private String tokenKey;
	
	@Value("${rbac.token.timeout}")
	private Integer tokenTimeout;
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IEnterpriseService enterpriseService;

	@Override
	public Map<String, String> login(Map<String, String> map, HttpServletRequest request) {
		if ("0".equals(map.get("loginType"))) {
			try {
				String uname_=StringUtils.isEmpty(map.get("username"))?"adminok1":map.get("username");
				String username=uname_+","+map.get("nationality")+" "+map.get("enterpriseNo")+(StringUtils.isEmpty(map.get("username"))||"adminok1".equals(map.get("username"))?"":(StringUtils.isEmpty(map.get("userNo"))?"999999":map.get("userNo")));
				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username,map.get("password"));
				Authentication a = authenticationManager.authenticate(upat);
				User user = (User) a.getPrincipal();
				EnterpriseDomain ed= null;
				if(user.getEnterpriseId()!=null){ed=enterpriseService.findByPk(user.getEnterpriseId());}
				if(!"adminok1".equals(uname_)){
					if(!(map.get("nationality")+" "+map.get("enterpriseNo")).equals(ed.getEnterpriseNo())){
						throw new ApiException(0, "位号，账号，用户名或密码错误");
					}
				}else{
					if(!(map.get("nationality")+" "+map.get("enterpriseNo")).equals(user.getUserCode())){
						throw new ApiException(0, "位号，账号，用户名或密码错误");
					}
				}
				if(!(StringUtils.isEmpty(map.get("userNo"))?"999999":map.get("userNo")).equals(user.getUserNo())){
					throw new ApiException(0, "位号，账号，用户名或密码错误");
				}
				String token = TokenUtils.genToken();
				addSession(token, user);
				return buildResult(user, token,ed);
			} catch (AuthenticationException e) {
				throw new ApiException(0, "位号，账号，用户名或密码错误");
			}
		}else if("1".equals(map.get("loginType"))){
			if(StringUtils.isEmpty(map.get("telPhone"))){
				throw new ApiException(0, "请输入手机号码");
			}
			if(SMSUtil.checkCode(map.get("telPhone"), map.get("smsCode"),true)){
				List<UserDomain> users = userService.findByBeanPropName("telphone", map.get("telPhone"));
				if(users==null||users.size()==0){
					throw new ApiException(0, "手机号"+map.get("telPhone")+"不存在");
				}
				if(users.size()>1){
					throw new ApiException(2100,accountsLogin(users,map.get("telPhone")));
				}
				String token = TokenUtils.genToken();
				User u=new User();
				BeanUtils.copyProperties(users.get(0), u);
				u.setId(users.get(0).getId());
				EnterpriseDomain ed= null;
				if(u.getEnterpriseId()!=null){ed=enterpriseService.findByPk(u.getEnterpriseId());}
				addSession(token, u);
				return buildResult(u, token,ed);
			}else{
				throw new ApiException(0, "手机验证码不正确");
			}
		}else if("2".equals(map.get("loginType"))){
			if(checkKey(map.get("telPhone"), map.get("key"))){
				UserDomain user= userService.findByPk(Long.parseLong(map.get("id")));
				if(user==null){
					throw new ApiException(0, "非法登陆请求");
				}
				String token = TokenUtils.genToken();
				User u=new User();
				BeanUtils.copyProperties(user, u);
				u.setId(user.getId());
				EnterpriseDomain ed= null;
				if(u.getEnterpriseId()!=null){ed=enterpriseService.findByPk(u.getEnterpriseId());}
				addSession(token, u);
				return buildResult(u, token,ed);
			}else{
				throw new ApiException(0, "非法登陆请求");
			}
		}else{
			throw new ApiException(0, "无法登陆");
		}
	}
	
	public boolean checkKey(String phone,String key){
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(Constant.SESSION_DB_INDEX);
			String rediskey=jedis.get(Constant.ACCOUNTS_KEY + phone);
			if(key.equals(rediskey)){
				jedis.del(Constant.ACCOUNTS_KEY + phone);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(1012);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return false;
	}
	
	public LoginAccountsDomain accountsLogin(List<UserDomain> users,String phone){
		LoginAccountsDomain lad=new LoginAccountsDomain();
		for(int i=0;i<users.size();i++){
			users.get(i).setPassword(null);
		}
		lad.setUsers(users);
		lad.setKey(TokenUtils.genToken());
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(Constant.SESSION_DB_INDEX);
			jedis.set(Constant.ACCOUNTS_KEY + phone,lad.getKey());
			jedis.expire(Constant.ACCOUNTS_KEY + phone, 300);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(1012);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return lad;
	}
	
	private Map<String,String> buildResult(User user,String token,EnterpriseDomain ed){
		Map<String,String> map=new HashMap<>();
		map.put("loginName", user.getLoginName());
		map.put("userNo", user.getUserNo());
		map.put("id", user.getId().toString());
		map.put("telphone", user.getTelphone());
		map.put("name", user.getName());
		map.put("sex", "1".equals(user.getSex())?"女":"男");
		map.put("email", user.getEmail());
		map.put("userType", user.getUserType());
		map.put("nationality", user.getNationality());
		map.put(tokenKey, token);
		if(user.getEnterpriseId()!=null){
			map.put("enterpriseId",user.getEnterpriseId().toString());
			map.put("enterpriseNo", ed.getEnterpriseNo());
			map.put("enterpriseName", ed.getName());
			map.put("audit", String.valueOf(ed.getAudit()));
			map.put("auditRemark", ed.getAuditRemark());
			map.put("disable", String.valueOf(ed.getDisable()));
		}
		return map;
	}
	
	public void addSession(String token,User user){
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(Constant.SESSION_DB_INDEX);
			jedis.set(Constant.TOKEN_KEY + token, user.getId().toString()+"."+token);
			jedis.expire(Constant.TOKEN_KEY + token, tokenTimeout);
			jedis.set(Constant.USERINFO_KEY + user.getId().toString()+"."+token, JSON.toJSONString(user, SerializerFeature.DisableCircularReferenceDetect));
			jedis.expire(Constant.USERINFO_KEY + user.getId().toString()+"."+token, tokenTimeout);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(1011);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public User getUserByToken(String token) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(Constant.SESSION_DB_INDEX);
			String id = jedis.get(Constant.TOKEN_KEY + token);
			if (!StringUtils.isEmpty(id)) {
				jedis.expire(Constant.TOKEN_KEY + token, tokenTimeout);
				jedis.expire(Constant.USERINFO_KEY + id, tokenTimeout);
				return JSON.parseObject(jedis.get(Constant.USERINFO_KEY + id), User.class);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(1012);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public boolean check(String token) {
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		User u = getUserByToken(token);
		if (u != null) {
			return true;
		}
		return false;
	}

	@Override
	public void logout(String token) {
		User user = SecurityContextHelper.getCurrentUser();
		if (user != null) {
			Jedis jedis = jedisPool.getResource();
			try {
				jedis.select(Constant.SESSION_DB_INDEX);
				jedis.del(Constant.TOKEN_KEY + token);
				jedis.del(Constant.USERINFO_KEY + user.getId()+"."+token); 
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApiException(1011);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}
	}
}
