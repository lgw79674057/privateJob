package com.xuaxi.framework.core.annotation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {

	public static <C> TableConfig getTableConfigByPropName(String propName,Class<C> c){
		Class<?> clazz=c;
		while(clazz!=null){
			Field[] fieldArray = clazz.getDeclaredFields();
			for (Field field : fieldArray) {
				if(propName.equals(field.getName())){
					return field.getAnnotation(TableConfig.class);
				}
			}
			clazz=clazz.getSuperclass();
		}
		return null;
	}
	
	public static <C> Map<String,TableConfig> getTableConfig(Class<C> c){
		Map<String,TableConfig> map=new HashMap<String, TableConfig>();
		Class<?> clazz=c;
		while(clazz!=null){
			Field[] fieldArray = clazz.getDeclaredFields();
			for (Field field : fieldArray) {
				if(!map.containsKey(field.getName())){
					map.put(field.getName(), field.getAnnotation(TableConfig.class));
				}
			}
			clazz=clazz.getSuperclass();
		}
		return map;
	}
}
