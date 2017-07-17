package com.xuaxi.entity;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

public class CertFileEntity extends AbstractEntity<Long> {

	private static final long serialVersionUID = 486224170592196608L;

	/**
	 * 企业Id
	 */
	@TableConfig(colName = "t.enterpriseId",operators=QueryOperatorEnum.eq)
	private Long enterpriseId;

	/**
	 * 证照类型(1：统一社会信用代码  2：营业执照号码 3：税务登记证号码 4：组织机构代码)
	 */
	@TableConfig(colName = "t.certType")
	private String certType;

	/**
	 * 证照号码
	 */
	@TableConfig(colName = "t.certNo")
	private String certNo;

	/**
	 * 附件目录
	 */
	@TableConfig(colName = "t.filePath")
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
