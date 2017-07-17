package com.xuaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.IChangeRecordDao;
import com.xuaxi.domain.ChangeRecordDomain;
import com.xuaxi.entity.ChangeRecordEntity;
import com.xuaxi.service.IChangeRecordService;

@Service("changeRecordServiceImpl")
public class ChangeRecordServiceImpl extends AbstractBaseService<ChangeRecordDomain, ChangeRecordEntity, Long> implements IChangeRecordService{

	@Autowired
	public ChangeRecordServiceImpl(IChangeRecordDao dao) {
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
}
