package com.xuaxi.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.IInvoiceDao;
import com.xuaxi.entity.InvoiceEntity;

@Repository
public class InvoiceDaoImpl extends AbstractBaseDao<InvoiceEntity, Long> implements IInvoiceDao{

	public InvoiceDaoImpl() {
		super(InvoiceDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

}
