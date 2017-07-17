package com.xuaxi.framework.core.server;

import java.util.List;
import java.util.Map;

import com.xuaxi.framework.core.domain.AbstractDomain;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;
import com.xuaxi.framework.core.query.Condition;
import com.xuaxi.framework.core.query.Page;

public interface IBaseService <Domain extends AbstractDomain<PK>,Entity extends AbstractEntity<PK>,PK>{

	int create(Domain domain);
	
	int create(List<Domain> domains);
	
	int merge(Domain domain);
	
	int remove(PK pk);
	
	int remove(List<PK> param);
	
	Domain findByPk(PK pk);
	
	List<Domain> findAll();
	
	List<Domain> findByBeanProp(Domain domain);
	
	List<Domain> findByBeanProp(Domain domain,Map<String, Object> otherParams);
	
	<V> List<Domain> findByBeanPropName(String prop,V value);
	
	<V> List<Domain> findByBeanPropName(String prop,V value,QueryOperatorEnum op);
	
	<V> List<Domain> findByBeanPropName(String prop,V value,QueryOperatorEnum op,Map<String, Object> otherParams);
	
	Page<Domain> findPage(Condition query);
	
	Page<Domain> findPage(Condition query,Map<String, Object> otherParams);
}
