package com.xuaxi.rbac.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xuaxi.domain.UserDomain;
import com.xuaxi.framework.core.entity.GrantedAuthority;
import com.xuaxi.framework.core.entity.User;
import com.xuaxi.service.IUserService;

public class TraderDetailService implements UserDetailsService {

	@Autowired
	private IUserService userService;

	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		
		UserDomain udmain=new UserDomain();
		String uNames[]=arg0.split(",");
		udmain.setLoginName(uNames[0]);
		udmain.setUserCode(uNames[1]);
		List<UserDomain> users = userService.findByBeanProp(udmain);
		
		if (users == null || users.size() == 0) {
			return null;
		}
		User user = new User();
		BeanUtils.copyProperties(users.get(0), user);
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(new GrantedAuthority("ROLE_USER"));
		user.setAuthorities(auths);
		user.setId(users.get(0).getId());
		return user;
	}

}
