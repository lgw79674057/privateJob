package com.xuaxi.dao.impl;

import java.util.List;
import java.util.Map;

import com.xuaxi.entity.UserEnterpriseEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.IEnterpriseDao;
import com.xuaxi.entity.EnterpriseEntity;

@Repository
public class EnterpriseDaoImpl extends AbstractBaseDao<EnterpriseEntity, Long> implements IEnterpriseDao{

	public EnterpriseDaoImpl() {
		super(EnterpriseDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	@Override
	public List<EnterpriseEntity> search(Map<String, Object> map) {
		return sqlSession.selectList(this.packageName+".search", map);
	}

	@Override
	public Long search_count(Map<String, Object> map) {
		return sqlSession.selectOne(this.packageName+".search_count", map);
	}

	@Override
	public List<UserEnterpriseEntity> findEnterprises(Map<String, Object> map) {
		return sqlSession.selectList(this.packageName+".findEnterprises",map);
	}

	@Override
	public Long findEnterprisesCount(Map<String, Object> map) {
		return sqlSession.selectOne(this.packageName+".findEnterprisesCount",map);
	}

}
