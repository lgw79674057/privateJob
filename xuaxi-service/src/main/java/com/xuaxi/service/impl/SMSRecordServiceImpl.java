package com.xuaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.ISMSRecordDao;
import com.xuaxi.domain.SMSRecordDomain;
import com.xuaxi.entity.SMSRecordEntity;
import com.xuaxi.service.ISMSRecordService;

@Service("sMSRecordServiceImpl")
public class SMSRecordServiceImpl extends AbstractBaseService<SMSRecordDomain, SMSRecordEntity, Long> implements ISMSRecordService{

	@Autowired
	public SMSRecordServiceImpl(ISMSRecordDao dao) {
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
