package com.xuaxi.service;

import com.xuaxi.framework.core.server.IBaseService;
import com.xuaxi.domain.EnterpriseRegDomain;
import com.xuaxi.domain.UserDomain;
import com.xuaxi.entity.UserEntity;

public interface IUserService extends IBaseService<UserDomain, UserEntity, Long>{

	void regEnterprise(EnterpriseRegDomain domain);

	void removeFollowed(Long userId);
}
