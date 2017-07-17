package com.xuaxi.framework.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiazhijian
 * @date 2016年10月10日 上午10:51:56
 * @Description domain查询配置
 * @FileName QueryConfig.java
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QueryConfig {

	/**
	 * 是否允许不分页查询
	 * @return
	 */
	public boolean noPaging() default false;
	
}
