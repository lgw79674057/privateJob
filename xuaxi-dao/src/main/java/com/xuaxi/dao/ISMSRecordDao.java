package com.xuaxi.dao;

import com.xuaxi.framework.core.dao.IBaseDao;
import com.xuaxi.entity.SMSRecordEntity;

public interface ISMSRecordDao extends IBaseDao<SMSRecordEntity, Long>{

	int findFiveMinSMSCount(String phone);
	
	int findDaySMSCount(String phone);
}
