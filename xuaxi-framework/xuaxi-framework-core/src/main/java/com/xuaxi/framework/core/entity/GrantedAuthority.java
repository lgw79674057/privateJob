package com.xuaxi.framework.core.entity;

public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

	private static final long serialVersionUID = 8166877377397288512L;

	public GrantedAuthority(){}
	
	private String authority;
	
	public GrantedAuthority(String authority){
		this.authority=authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
