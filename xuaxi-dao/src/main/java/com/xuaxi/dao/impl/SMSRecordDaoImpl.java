package com.xuaxi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.ISMSRecordDao;
import com.xuaxi.entity.SMSRecordEntity;

@Repository
public class SMSRecordDaoImpl extends AbstractBaseDao<SMSRecordEntity, Long> implements ISMSRecordDao{

	public SMSRecordDaoImpl() {
		super(SMSRecordDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	@Override
	public int findFiveMinSMSCount(String phone) {
		return sqlSession.selectOne(this.packageName+".findFiveMinSMSCount",phone);
	}

	@Override
	public int findDaySMSCount(String phone) {
		return sqlSession.selectOne(this.packageName+".findDaySMSCount",phone);
	}

}
