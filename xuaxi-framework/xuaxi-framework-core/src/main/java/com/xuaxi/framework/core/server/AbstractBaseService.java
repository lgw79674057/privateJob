package com.xuaxi.framework.core.server;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.xuaxi.framework.core.annotation.AnnotationUtils;
import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.dao.IBaseDao;
import com.xuaxi.framework.core.domain.AbstractDomain;
import com.xuaxi.framework.core.entity.AbstractEntity;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.query.Condition;
import com.xuaxi.framework.core.query.Page;
import com.xuaxi.framework.core.query.QueryBean;
import com.xuaxi.framework.core.query.QueryBeanBuild;
import com.xuaxi.framework.core.query.QueryColumn;
import com.xuaxi.framework.core.query.QueryOperatorUtils;
import com.xuaxi.framework.core.query.QuerySystemField;

public abstract class AbstractBaseService<Domain extends AbstractDomain<PK>, Entity extends AbstractEntity<PK>, PK>
		implements IBaseService<Domain, Entity, PK> {

	private Class<?> domainClass = null;

	private Class<?> entityClass = null;

	private IBaseDao<Entity, PK> businessDao = null;

	protected abstract PK getPrimaryKey();

	protected abstract PK getLoginUserId();
	
	protected abstract String getLoginUserName();

	public AbstractBaseService(IBaseDao<Entity, PK> businessDao) {
		this.businessDao = businessDao;
	}

	public Class<?> getDomainClass() {
		return getGenericType(0);
	}

	public Class<?> getEntityClass() {
		return getGenericType(1);
	}

	public int create(Domain domain) {
		List<Domain> list = new ArrayList<Domain>();
		list.add(domain);
		return create(list);
	}

	public int create(List<Domain> domains) {
		for (Domain domain : domains) {
			if (domain.getId() == null) {
				domain.setId(getPrimaryKey());
			}
			domain.setCreateBy(getLoginUserId());
			domain.setModifyBy(getLoginUserId());
			domain.setCreateByName(getLoginUserName());
			domain.setModifyByName(getLoginUserName());
		}
		return businessDao.create(copyFromDomain(domains));
	}

	public int merge(Domain domain) {
		domain.setModifyBy(getLoginUserId());
		domain.setModifyByName(getLoginUserName());
		return businessDao.merge(copyFromDomain(domain));
	}

	public int remove(PK pk) {
		List<PK> list = new ArrayList<PK>();
		list.add(pk);
		return remove(list);
	}

	public int remove(List<PK> param) {
		return businessDao.remove(param);
	}

	public Domain findByPk(PK pk) {
		TableConfig config = AnnotationUtils.getTableConfigByPropName("id", getEntityClass());
		if (!QueryOperatorUtils.checkSupportOperator(QueryOperatorEnum.eq, config)) {
			throw new ApiException(1000);
		}
		QueryBean bean = new QueryBean();
		bean.setPage(false);
		List<QueryColumn<Object>> list = new ArrayList<QueryColumn<Object>>();
		list.add(new QueryColumn<Object>("id", config != null ? config.colName() : "id", QueryOperatorEnum.eq.getDbOp(),
				pk));
		bean.setQueryColumn(list);
		List<Entity> entitylist = businessDao.find(bean);
		if (entitylist != null && entitylist.size() > 0) {
			return copyFromEntity(entitylist.get(0));
		}
		return null;
	}

	public List<Domain> findAll() {
		return copyFromEntity(businessDao.find(null));
	}

	public List<Domain> findByBeanProp(Domain domain) {
		return findByBeanProp(domain, null);
	}
	
	public List<Domain> findByBeanProp(Domain domain, Map<String, Object> otherParams) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JSON.toJSON(copyFromDomain(domain));
		QueryBean bean = new QueryBean();
		bean.setPage(false);
		List<QueryColumn<Object>> queryCloumns = new ArrayList<QueryColumn<Object>>();
		for (String key : map.keySet()) {
			Object val = map.get(key);
			if (val != null) {
				TableConfig config = AnnotationUtils.getTableConfigByPropName(key, getEntityClass());
				if (!QueryOperatorUtils.checkSupportOperator(QueryOperatorEnum.eq, config)) {
					throw new ApiException(1000);
				}
				queryCloumns.add(new QueryColumn<Object>(key, config != null ? config.colName() : key,
						QueryOperatorEnum.eq.getDbOp(), val));
			}
		}
		bean.setQueryColumn(queryCloumns);
		bean.setOtherParams(otherParams);
		return copyFromEntity(businessDao.find(bean));
	}

	public Page<Domain> findPage(Condition query, Map<String, Object> otherParams) {
		Page<Domain> page = new Page<Domain>();
		QueryBean bean = QueryBeanBuild.build(query, getEntityClass());
		bean.setOtherParams(otherParams);
		if (bean.isPage() == false) {
			page.setDatas(copyFromEntity(businessDao.find(bean)));
			return page;
		}
		page.setTotalSize(businessDao.count(bean));
		if (page.getTotalSize() > bean.getStartNum()) {
			page.setDatas(copyFromEntity(businessDao.find(bean)));
		}
		page.setPageNo(bean.getPageNo());
		page.setPageSize(bean.getPageSize());
		return page;
	}

	public <V> List<Domain> findByBeanPropName(String prop, V value) {
		return findByBeanPropName(prop, value, QueryOperatorEnum.eq);
	}

	public <V> List<Domain> findByBeanPropName(String prop, V value, QueryOperatorEnum op,Map<String,Object> otherParam) {
		if (value == null) {
			throw new ApiException(1001);
		}
		TableConfig config = AnnotationUtils.getTableConfigByPropName(prop, getEntityClass());
		if (!QueryOperatorUtils.checkSupportOperator(op, config)) {
			throw new ApiException(1000);
		}
		QueryBean bean = new QueryBean();
		bean.setPage(false);
		List<QueryColumn<Object>> queryCloumns = new ArrayList<QueryColumn<Object>>();
		Object v = value;
		if (op.getOp().equals("like")) {
			v = "%" + value + "%";
		} else if (op.getOp().equals("llike")) {
			v = "%" + value;
		} else if (op.getOp().equals("rlike")) {
			v = value + "%";
		} else if ("in".equals(op.getOp())) {
			if(value instanceof ArrayList){
				v=value;
			}else{
				List<String> valueList = new ArrayList<String>();
				String values[] = ((String) value).split(QuerySystemField.getValueSplit());
				for (String vl : values) {
					valueList.add(vl);
				}
				v = valueList;
			}
		}
		queryCloumns.add(new QueryColumn<Object>(prop, config != null ? config.colName() : prop, op.getDbOp(), v));
		bean.setQueryColumn(queryCloumns);
		bean.setOtherParams(otherParam);
		return copyFromEntity(businessDao.find(bean));
	}
	
	public <V> List<Domain> findByBeanPropName(String prop, V value, QueryOperatorEnum op) {
		return findByBeanPropName(prop, value, op, null);
	}

	public Page<Domain> findPage(Condition query) {
		return findPage(query, null);
	}
	
	public List<Domain> findByCondition(Condition condition){
		QueryBean bean = QueryBeanBuild.build(condition, getEntityClass(),true);
		return copyFromEntity(businessDao.find(bean));
	}

	public List<Domain> copyFromEntity(List<Entity> list) {
		List<Domain> resultList = new ArrayList<Domain>();
		if (list != null && list.size() > 0) {
			for (Entity entity : list) {
				resultList.add(copyFromEntity(entity));
			}
		}
		return resultList;
	}

	public List<Entity> copyFromDomain(List<Domain> list) {
		List<Entity> resultList = new ArrayList<Entity>();
		if (list != null && list.size() > 0) {
			for (Domain domain : list) {
				resultList.add(copyFromDomain(domain));
			}
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	protected Entity copyFromDomain(Domain domain) {
		if (domain == null) {
			return null;
		}
		Class<?> targetClass = getGenericType(1);
		Object target = null;
		try {
			target = targetClass.newInstance();
			BeanUtils.copyProperties(domain, target);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return (Entity) target;
	}

	@SuppressWarnings("unchecked")
	protected Domain copyFromEntity(Entity entity) {
		if (entity == null) {
			return null;
		}
		Class<?> targetClass = getGenericType(0);
		Object target = null;

		try {
			target = targetClass.newInstance();
			BeanUtils.copyProperties(entity, target);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return (Domain) target;
	}

	protected <E extends Entity, D extends Domain> List<E> copyFromDomain(List<D> domain, Class<E> clazz) {
		if (domain == null) {
			return null;
		}
		List<E> list = new ArrayList<E>();
		for (D d : domain) {
			list.add(copyFromDomain(d, clazz));
		}
		return list;
	}

	protected <E extends Entity, D extends Domain> List<D> copyFromEntity(List<E> entity, Class<D> clazz) {
		if (entity == null) {
			return null;
		}
		List<D> list = new ArrayList<D>();
		for (E e : entity) {
			list.add(copyFromEntity(e, clazz));
		}
		return list;
	}

	protected <E extends Entity, D extends Domain> E copyFromDomain(D domain, Class<E> clazz) {
		if (domain == null) {
			return null;
		}
		try {
			E entity = clazz.newInstance();
			BeanUtils.copyProperties(domain, entity);
			return entity;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <E extends Entity, D extends Domain> D copyFromEntity(E entity, Class<D> clazz) {
		if (entity == null) {
			return null;
		}
		try {
			D domain = clazz.newInstance();
			BeanUtils.copyProperties(entity, domain);
			return domain;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Class<?> getGenericType(int index) {
		if (index == 1) {
			if (entityClass == null) {
				synchronized (this) {
					if (entityClass == null) {
						entityClass = getClass(index);
					}
				}
			}
			return entityClass;
		} else if (index == 0) {
			if (domainClass == null) {
				synchronized (this) {
					if (domainClass == null) {
						domainClass = getClass(index);
					}
				}
			}
			return domainClass;
		}
		return Object.class;

	}

	private Class<?> getClass(int index) {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}
}
