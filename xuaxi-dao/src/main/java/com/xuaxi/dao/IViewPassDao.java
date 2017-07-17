package com.xuaxi.dao;

import com.xuaxi.framework.core.dao.IBaseDao;
import com.xuaxi.entity.ViewPassEntity;

public interface IViewPassDao extends IBaseDao<ViewPassEntity, Long>{

	void removeByEnterprise(Long enterpriseId);
}
