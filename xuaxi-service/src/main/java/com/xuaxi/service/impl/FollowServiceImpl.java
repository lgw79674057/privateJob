package com.xuaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.IFollowDao;
import com.xuaxi.domain.FollowDomain;
import com.xuaxi.entity.FollowEntity;
import com.xuaxi.service.IFollowService;

@Service("followServiceImpl")
public class FollowServiceImpl extends AbstractBaseService<FollowDomain, FollowEntity, Long> implements IFollowService{

	@Autowired
	private IFollowDao followDao;
	
	@Autowired
	public FollowServiceImpl(IFollowDao dao) {
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
	public void updateNewChange(Long enterpriseId) {
		followDao.updateNewChange(enterpriseId);
	}
}
