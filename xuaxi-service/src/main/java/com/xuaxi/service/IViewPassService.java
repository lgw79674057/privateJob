package com.xuaxi.service;

import com.xuaxi.framework.core.server.IBaseService;
import com.xuaxi.domain.ViewPassDomain;
import com.xuaxi.entity.ViewPassEntity;

public interface IViewPassService extends IBaseService<ViewPassDomain, ViewPassEntity, Long>{

	void removeByEnterprise(Long enterpriseId);
}
