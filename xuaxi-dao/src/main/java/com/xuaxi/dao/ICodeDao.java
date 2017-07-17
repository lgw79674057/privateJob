package com.xuaxi.dao;

import com.xuaxi.framework.core.dao.IBaseDao;

import java.util.List;

import com.xuaxi.entity.CodeEntity;

public interface ICodeDao extends IBaseDao<CodeEntity, Long>{

	List<CodeEntity> rand(String type,String nationality,int count);
}
