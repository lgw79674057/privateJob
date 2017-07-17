package com.xuaxi.service;

import com.xuaxi.domain.UserEnterpriseDomain;
import com.xuaxi.framework.core.query.Page;
import com.xuaxi.framework.core.server.IBaseService;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.entity.EnterpriseEntity;

public interface IEnterpriseService extends IBaseService<EnterpriseDomain, EnterpriseEntity, Long>{

	Page<EnterpriseDomain> search(String key,int pageSize,int pageNo);
	
	int superMerge(EnterpriseDomain domain);

	Page<UserEnterpriseDomain> findEnterprises(String name,int pageSize, int pageNo);


}
