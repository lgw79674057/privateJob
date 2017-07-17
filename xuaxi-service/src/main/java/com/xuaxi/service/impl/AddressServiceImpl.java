package com.xuaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.IAddressDao;
import com.xuaxi.domain.AddressDomain;
import com.xuaxi.entity.AddressEntity;
import com.xuaxi.service.IAddressService;

@Service("addressServiceImpl")
public class AddressServiceImpl extends AbstractBaseService<AddressDomain, AddressEntity, Long> implements IAddressService{

	@Autowired
	public AddressServiceImpl(IAddressDao dao) {
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
