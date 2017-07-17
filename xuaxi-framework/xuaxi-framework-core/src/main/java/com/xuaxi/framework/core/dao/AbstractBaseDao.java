package com.xuaxi.framework.core.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.query.QueryBean;

/**
 * @author xiazhijian
 * @date 2016年10月10日 下午2:34:04
 * @Description dao抽象实现
 * @FileName AbstractBaseDao.java
 * @param <Entity>
 * @param <PK>
 */
public abstract class AbstractBaseDao<Entity extends AbstractEntity<PK>, PK>
		implements IBaseDao<Entity, PK> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	protected abstract SqlSessionTemplate getSqlSession();

	protected String packageName;

	public AbstractBaseDao(String packageName) {
		this.packageName = packageName;
	}

	public int create(List<Entity> entiey) {
		logger.debug("执行Sql：" + packageName + ".create");
		return getSqlSession().insert(packageName + ".create", entiey);
	}

	public int merge(Entity entity) {
		if (entity.getId() == null) {
			throw new ApiException(1003);
		}
		logger.debug("执行Sql：" + packageName + ".merge");
		return getSqlSession().update(packageName + ".merge", entity);
	}

	public int remove(List<PK> param) {
		logger.debug("执行Sql：" + packageName + ".remove");
		return getSqlSession().update(packageName + ".remove", param);
	}

	public List<Entity> find(QueryBean query) {
		logger.debug("执行Sql：" + packageName + ".find");
		return getSqlSession().selectList(packageName + ".find", query);
	}

	public Long count(QueryBean query) {
		logger.debug("执行Sql：" + packageName + ".count");
		return getSqlSession().selectOne(packageName + ".count", query);
	}
}
