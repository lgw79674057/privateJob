package com.xuaxi.service;

import com.xuaxi.framework.core.server.IBaseService;

import java.util.List;

import com.xuaxi.domain.CertFileDomain;
import com.xuaxi.entity.CertFileEntity;

public interface ICertFileService extends IBaseService<CertFileDomain, CertFileEntity, Long>{

	void modifys(List<CertFileDomain> domains);
}
