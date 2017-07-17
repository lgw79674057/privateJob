package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

public class 	UserDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 822596781876978432L;

	/**
	 * 用户编号(6位数字)
	 */
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
	
	private String smsCode;

	/**
	 * 证照照片
	 * @return
     */
	private String certPic;

	/**
	 * 推荐人No
	 * @return
     */
	private String recommendNo;

	/**
	 * 已关注
	 */
	private Integer followed;
	/**
	 * 可关注
	 */
	private Integer followedNum;

	public Integer getFollowed() {
		return followed;
	}

	public void setFollowed(Integer followed) {
		this.followed = followed;
	}

	public Integer getFollowedNum() {
		return followedNum;
	}

	public void setFollowedNum(Integer followedNum) {
		this.followedNum = followedNum;
	}

	public String getCertPic() {
		return certPic;
	}

	public void setCertPic(String certPic) {
		this.certPic = certPic;
	}

	public String getRecommendNo() {
		return recommendNo;
	}

	public void setRecommendNo(String recommendNo) {
		this.recommendNo = recommendNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
