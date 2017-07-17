package com.xuaxi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.IViewPassDao;
import com.xuaxi.entity.ViewPassEntity;

@Repository
public class ViewPassDaoImpl extends AbstractBaseDao<ViewPassEntity, Long> implements IViewPassDao{

	public ViewPassDaoImpl() {
		super(ViewPassDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	@Override
	public void removeByEnterprise(Long enterpriseId) {
		sqlSession.update(this.packageName+".removeByEnterprise", enterpriseId);
	}

}
