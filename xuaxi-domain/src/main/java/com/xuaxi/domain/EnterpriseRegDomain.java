package com.xuaxi.domain;

import java.io.Serializable;
import java.util.List;

public class EnterpriseRegDomain implements Serializable {

	private static final long serialVersionUID = 2348298856695331977L;

	private UserDomain userInfo;
	
	private EnterpriseDomain enterpriseInfo;
	
	private InvoiceDomain invoiceInfo;
	
	private List<CertFileDomain> filesInfo;

	public UserDomain getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserDomain userInfo) {
		this.userInfo = userInfo;
	}

	public EnterpriseDomain getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseDomain enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public InvoiceDomain getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(InvoiceDomain invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public List<CertFileDomain> getFilesInfo() {
		return filesInfo;
	}

	public void setFilesInfo(List<CertFileDomain> filesInfo) {
		this.filesInfo = filesInfo;
	}
}
