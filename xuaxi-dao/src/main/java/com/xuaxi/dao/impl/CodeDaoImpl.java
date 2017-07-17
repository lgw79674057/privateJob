package com.xuaxi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xuaxi.framework.core.dao.AbstractBaseDao;
import com.xuaxi.dao.ICodeDao;
import com.xuaxi.entity.CodeEntity;

@Repository
public class CodeDaoImpl extends AbstractBaseDao<CodeEntity, Long> implements ICodeDao{

	public CodeDaoImpl() {
		super(CodeDaoImpl.class.getName());
	}

	@Qualifier("sqlSession")
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	@Override
	protected SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	@Override
	public List<CodeEntity> rand(String type, String nationality,int count) {
		Map<String,String> map=new HashMap<>();
		map.put("noType", type);
		map.put("nationality", nationality);
		map.put("count", String.valueOf(count));
		return sqlSession.selectList(this.packageName+".rand",map);
	}

}
