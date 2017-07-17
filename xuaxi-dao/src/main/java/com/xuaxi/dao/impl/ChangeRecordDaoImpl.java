package com.xuaxi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.IChangeRecordDao;
import com.xuaxi.entity.ChangeRecordEntity;

@Repository
public class ChangeRecordDaoImpl extends AbstractBaseDao<ChangeRecordEntity, Long> implements IChangeRecordDao{

	public ChangeRecordDaoImpl() {
		super(ChangeRecordDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

}
