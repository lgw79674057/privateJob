package com.xuaxi.framework.core.dao;

import java.util.List;

import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.query.QueryBean;

public interface IBaseDao<Entity extends AbstractEntity<PK>, PK> {

	int create(List<Entity> entiey);
	
	int merge(Entity entity);
	
	int remove(List<PK> param);
	
	List<Entity> find(QueryBean query);
	
	Long count(QueryBean query);
}
