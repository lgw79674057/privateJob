package com.xuaxi.service;

import com.xuaxi.framework.core.server.IBaseService;
import com.xuaxi.domain.FollowDomain;
import com.xuaxi.entity.FollowEntity;

public interface IFollowService extends IBaseService<FollowDomain, FollowEntity, Long>{

	void updateNewChange(Long enterpriseId);
}
