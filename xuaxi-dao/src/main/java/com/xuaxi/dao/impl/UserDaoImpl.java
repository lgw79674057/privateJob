package com.xuaxi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.IUserDao;
import com.xuaxi.entity.UserEntity;

@Repository
public class UserDaoImpl extends AbstractBaseDao<UserEntity, Long> implements IUserDao{

	public UserDaoImpl() {
		super(UserDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	@Override
	public void removeFollowEd(Long userId) {
		sqlSession.update(this.packageName+".removeFollowEd",userId);
	}
}
