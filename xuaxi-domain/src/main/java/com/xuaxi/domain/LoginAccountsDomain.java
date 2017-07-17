package com.xuaxi.domain;

import java.io.Serializable;
import java.util.List;

public class LoginAccountsDomain implements Serializable{

	private static final long serialVersionUID = -4005621898511136228L;

	private String key;
	
	private List<UserDomain> users;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<UserDomain> getUsers() {
		return users;
	}

	public void setUsers(List<UserDomain> users) {
		this.users = users;
	}
}
