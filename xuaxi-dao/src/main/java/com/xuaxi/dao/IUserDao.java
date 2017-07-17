package com.xuaxi.dao;

import com.xuaxi.framework.core.dao.IBaseDao;
import com.xuaxi.entity.UserEntity;

public interface IUserDao extends IBaseDao<UserEntity, Long>{

    void removeFollowEd(Long userId);

}
