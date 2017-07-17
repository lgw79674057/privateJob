package com.xuaxi.dao;

import com.xuaxi.framework.core.dao.IBaseDao;
import com.xuaxi.entity.CertFileEntity;

public interface ICertFileDao extends IBaseDao<CertFileEntity, Long>{

	void removeByEnterprise(Long enterpriseId);
}
