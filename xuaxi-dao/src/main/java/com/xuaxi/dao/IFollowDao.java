package com.xuaxi.dao;

import com.xuaxi.framework.core.dao.IBaseDao;
import com.xuaxi.entity.FollowEntity;

public interface IFollowDao extends IBaseDao<FollowEntity, Long>{

	void updateNewChange(Long enterpriseId);
}
