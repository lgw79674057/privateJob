package com.xuaxi.dao;

import com.xuaxi.entity.UserEnterpriseEntity;
import com.xuaxi.framework.core.dao.IBaseDao;

import java.util.List;
import java.util.Map;

import com.xuaxi.entity.EnterpriseEntity;

public interface IEnterpriseDao extends IBaseDao<EnterpriseEntity, Long>{

	List<EnterpriseEntity> search(Map<String,Object> map);
	
	Long search_count(Map<String,Object> map);

	List<UserEnterpriseEntity> findEnterprises(Map<String,Object> map);
	Long findEnterprisesCount(Map<String,Object> map);
}
