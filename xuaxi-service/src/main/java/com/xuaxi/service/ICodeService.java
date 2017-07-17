package com.xuaxi.service;

import com.xuaxi.framework.core.server.IBaseService;

import java.util.List;

import com.xuaxi.domain.CodeDomain;
import com.xuaxi.entity.CodeEntity;

public interface ICodeService extends IBaseService<CodeDomain, CodeEntity, Long>{

	List<CodeDomain> rand(String type,String nationality,int count);
}
