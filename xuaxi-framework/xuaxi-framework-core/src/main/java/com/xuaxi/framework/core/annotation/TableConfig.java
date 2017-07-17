package com.xuaxi.framework.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xuaxi.framework.core.enums.QueryOperatorEnum;

/**
 * @author xiazhijian
 * @date 2016年10月10日 上午10:51:56
 * @Description domain查询配置
 * @FileName DomainConfig.java
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableConfig {

	/**
	 * 支持的操作符，默认支持所有
	 * @return
	 */
	public QueryOperatorEnum[] operators() default {QueryOperatorEnum.none};
	
	/**
	 * 数据库字段名，默认为属性名
	 * @return
	 */
	public String colName() default ""; 
	
	/**
	 * 是否支持排序，默认不支持
	 * @return
	 */
	public boolean sort() default false;
	
	/**
	 * 查询默认值，默认为空
	 * @return
	 */
	public String defalutValue() default "";
}
