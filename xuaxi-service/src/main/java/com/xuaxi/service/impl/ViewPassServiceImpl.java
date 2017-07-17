package com.xuaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.IViewPassDao;
import com.xuaxi.domain.ViewPassDomain;
import com.xuaxi.entity.ViewPassEntity;
import com.xuaxi.service.IViewPassService;

@Service("viewPassServiceImpl")
public class ViewPassServiceImpl extends AbstractBaseService<ViewPassDomain, ViewPassEntity, Long> implements IViewPassService{

	@Autowired
	private IViewPassDao viewPassDao;
	
	@Autowired
	public ViewPassServiceImpl(IViewPassDao dao) {
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
	public void removeByEnterprise(Long enterpriseId) {
		viewPassDao.removeByEnterprise(enterpriseId);
	}
}
