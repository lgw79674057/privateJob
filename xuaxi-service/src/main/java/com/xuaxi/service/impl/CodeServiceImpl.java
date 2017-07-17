package com.xuaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.ICodeDao;
import com.xuaxi.domain.CodeDomain;
import com.xuaxi.entity.CodeEntity;
import com.xuaxi.service.ICodeService;

@Service("codeServiceImpl")
public class CodeServiceImpl extends AbstractBaseService<CodeDomain, CodeEntity, Long> implements ICodeService{

	@Autowired
	private ICodeDao dao;
	
	@Autowired
	public CodeServiceImpl(ICodeDao dao) {
		super(dao);
	}

	@Override
	protected Long getPrimaryKey() {
		return PrimaryKeyGenerator.SEQUENCE.next();
	}

	@Override
	protected Long getLoginUserId() {
		return SecurityContextHelper.getCurrentUserId();
	}

	@Override
	protected String getLoginUserName() {
		return SecurityContextHelper.getCurrentUserName();
	}

	@Override
	public List<CodeDomain> rand(String type, String nationality,int count) {
		return super.copyFromEntity(dao.rand(type, nationality,count));
	}
}
