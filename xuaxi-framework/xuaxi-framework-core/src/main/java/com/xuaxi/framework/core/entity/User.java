package com.xuaxi.framework.core.entity;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 702052535642083883L;

	private Long id;
	
	private String userNo;
	
	private String userCode;

	/**
	 * 登陆名
	 */
	private String loginName;

	/**
	 * 登陆密码
	 */
	private String password;

	/**
	 * 手机号码
	 */
	private String telphone;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别（0男 1女）
	 */
	private String sex;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户类型（0个人 1企业 2第三方）
	 */
	private String userType;

	/**
	 * 证件类型(1身份证 2护照 3军官证 4其他)
	 */
	private String certType;

	/**
	 * 证件号码
	 */
	private String certNo;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 企业ID
	 */
	private Long enterpriseId;
	
	private Collection<GrantedAuthority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return loginName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
