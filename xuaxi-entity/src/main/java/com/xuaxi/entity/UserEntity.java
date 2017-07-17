package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

public class UserEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 782816351974603520L;

	/**
	 * 用户编号(6位数字)
	 */
	@TableConfig(colName = "t.userNo",operators=QueryOperatorEnum.eq)
	private String userNo;

	/**
	 * 登陆名
	 */
	@TableConfig(colName = "t.loginName",operators=QueryOperatorEnum.eq)
	private String loginName;
	
	@TableConfig(colName = "t.userCode",operators=QueryOperatorEnum.eq)
	private String userCode;

	/**
	 * 登陆密码
	 */
	@TableConfig(colName = "t.password")
	private String password;

	/**
	 * 手机号码
	 */
	@TableConfig(colName = "t.telphone",operators=QueryOperatorEnum.eq)
	private String telphone;

	/**
	 * 姓名
	 */
	@TableConfig(colName = "t.name")
	private String name;

	/**
	 * 性别（0男 1女）
	 */
	@TableConfig(colName = "t.sex")
	private String sex;

	/**
	 * 邮箱
	 */
	@TableConfig(colName = "t.email")
	private String email;

	/**
	 * 用户类型（0个人 1企业 2第三方）
	 */
	@TableConfig(colName = "t.userType")
	private String userType;

	/**
	 * 证件类型(1身份证 2护照 3军官证 4其他)
	 */
	@TableConfig(colName = "t.certType")
	private String certType;

	/**
	 * 证件号码
	 */
	@TableConfig(colName = "t.certNo")
	private String certNo;

	/**
	 * 国籍
	 */
	@TableConfig(colName = "t.nationality")
	private String nationality;

	/**
	 * 企业ID
	 */
	@TableConfig(colName = "t.enterpriseId",operators={QueryOperatorEnum.eq,QueryOperatorEnum.eq})
	private Long enterpriseId;

	/**
	 * 证件证照
	 */
	@TableConfig(colName = "t.certPic")
	private String certPic;

	/**
	 * 已关注数
	 */
	@TableConfig(colName = "t.followed")
	private Integer followed;

	/**
	 * 可关注数
	 */
	@TableConfig(colName = "t.followedNum")
	private Integer followedNum;


	public String getCertPic() {
		return certPic;
	}

	public void setCertPic(String certPic) {
		this.certPic = certPic;
	}

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
