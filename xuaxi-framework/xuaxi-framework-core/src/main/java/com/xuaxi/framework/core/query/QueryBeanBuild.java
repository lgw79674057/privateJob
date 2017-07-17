package com.xuaxi.framework.core.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.xuaxi.framework.core.annotation.AnnotationUtils;
import com.xuaxi.framework.core.annotation.QueryConfig;
import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;
import com.xuaxi.framework.core.exceptions.ApiException;

public class QueryBeanBuild {

	private static final Logger logger = LoggerFactory.getLogger(QueryBeanBuild.class);

	public static QueryBean build(Condition condition, Class<?> clazz) {
		return build(condition, clazz,false);
	}
	
	public static QueryBean build(Condition condition, Class<?> clazz,boolean noPage) {
		QueryBean queryBean = new QueryBean();
		if(noPage){
			queryBean.setPage(false);
		}
		if (condition != null && condition.getQueryParam() != null && condition.getQueryParam().size() > 0) {
			List<QueryColumn<Object>> queryList = new ArrayList<QueryColumn<Object>>();
			for (String paramName : condition.getQueryParam().keySet()) {
				String value = condition.getQueryParam().get(paramName);
				if (StringUtils.isEmpty(value)) {
					logger.debug("忽略值为空的查询参数：" + paramName);
					continue;
				}
				if (QuerySystemField.getPage().equals(paramName)) {
					if(!noPage){
						if ("1".equals(value)) {
							QueryConfig qc = clazz.getAnnotation(QueryConfig.class);
							if(qc==null||qc.noPaging()==false){
								throw new ApiException(1010);
							}
							queryBean.setPage(false);
						}
					}
				} else if (QuerySystemField.getPageSize().equals(paramName)) {
					queryBean.setPageSize(Integer.parseInt(value));
				} else if (QuerySystemField.getPageNo().equals(paramName)) {
					queryBean.setPageNo(Integer.parseInt(value));
				} else {
					Map<String, TableConfig> tableConfig = AnnotationUtils.getTableConfig(clazz);
					if (QuerySystemField.getSort().equals(paramName)) {
						if (queryBean.getSortColumn() != null && queryBean.getSortColumn().size() > 0) {
							logger.debug("忽略重复的排序参数：" + value);
							throw new ApiException(1009);
						}
						String sorts[] = value.split(QuerySystemField.getValueSplit());
						List<SortColumn> sortColumns = new ArrayList<SortColumn>();
						for (String sort : sorts) {
							String operator = "DESC";
							String property = sort;
							if (sort.startsWith("+")) {
								operator = "ASC";
								property = sort.substring(1);
							} else if (sort.startsWith("-")) {
								operator = "DESC";
								property = sort.substring(1);
							}
							if (!tableConfig.containsKey(property)) {
								logger.debug("忽略不存在的排序字段：" + property + " " + operator);
								throw new ApiException(1008);
							}
							TableConfig config = tableConfig.get(property);
							if (config==null||!config.sort()) {
								logger.debug("忽略不支持的排序字段：" + property + " " + operator);
								throw new ApiException(1007);
							}
							sortColumns.add(new SortColumn(property,
									StringUtils.isEmpty(config.colName()) ? property : config.colName(), operator));
						}
						queryBean.setSortColumn(sortColumns);
					} else {
						String key = paramName;
						QueryOperatorEnum operator = QueryOperatorEnum.eq;
						if (paramName.indexOf(QuerySystemField.getConditonSplit()) != -1) {
							String k[] = paramName.split(QuerySystemField.getConditonSplit());
							if (k.length != 2) {
								logger.debug("忽略查询参数名称错误字段：" + key);
								throw new ApiException(1006);
							}
							key = k[0];
							operator = QueryOperatorEnum.getByOpName(k[1]);
						}
						if (operator == null) {
							logger.debug("忽略不支持的查询操作符：" + key);
							throw new ApiException(1000);
						}
						if (!tableConfig.containsKey(key)) {
							logger.debug("忽略不存在的查询字段：" + key + " " + operator.getOp());
							throw new ApiException(1004);
						}
						TableConfig config = tableConfig.get(key);
						if (!QueryOperatorUtils.checkSupportOperator(operator, config)) {
							logger.debug("忽略不支持的查询操作符：" + key + " " + operator.getOp());
							throw new ApiException(1005);
						}
						Object dbparam = value;
						if (operator.getOp().equals("like")) {
							dbparam = "%" + value + "%";
						} else if (operator.getOp().equals("llike")) {
							dbparam = "%" + value;
						} else if (operator.getOp().equals("rlike")) {
							dbparam = value + "%";
						} else if (operator.getOp().equals("in")) {
							List<String> valueList = new ArrayList<String>();
							String values[] = value.split(QuerySystemField.getValueSplit());
							for (String v : values) {
								valueList.add(v);
							}
							dbparam = valueList;
						}
						queryList.add(
								new QueryColumn<Object>(key,
										StringUtils.isEmpty(config.colName()) ? key : config.colName(), operator.getDbOp(), dbparam));
					}
				}
			}
			queryBean.setQueryColumn(queryList);
		}
		if (queryBean.isPage()) {
			queryBean.setStartNum((long) (queryBean.getPageNo() - 1) * (long) queryBean.getPageSize());
		}
		return queryBean;
	}
}
