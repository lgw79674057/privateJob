package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

public class CertFileDomain extends AbstractDomain<Long> {

	private static final long serialVersionUID = 611688065519443328L;

	/**
	 * 企业Id
	 */
	private Long enterpriseId;

	/**
	 * 证照类型(1：统一社会信用代码  2：营业执照号码 3：税务登记证号码 4：组织机构代码)
	 */
	private String certType;

	/**
	 * 证照号码
	 */
	private String certNo;

	/**
	 * 附件目录
	 */
	private String filePath;

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
