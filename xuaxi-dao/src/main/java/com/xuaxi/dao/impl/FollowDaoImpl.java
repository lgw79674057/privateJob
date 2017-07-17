package com.xuaxi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.IFollowDao;
import com.xuaxi.entity.FollowEntity;

@Repository
public class FollowDaoImpl extends AbstractBaseDao<FollowEntity, Long> implements IFollowDao{

	public FollowDaoImpl() {
		super(FollowDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	@Override
	public void updateNewChange(Long enterpriseId) {
		sqlSession.update(this.packageName+".updateNewChange", enterpriseId);
	}

}
