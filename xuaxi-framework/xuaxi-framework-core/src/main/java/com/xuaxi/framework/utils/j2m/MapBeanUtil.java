package com.xuaxi.framework.utils.j2m;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * bean map互转工具类
 * 
 * @author xiazhijian
 *
 */
public class MapBeanUtil {

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> transBean2Map(T object){
		if(object==null){return null;}
		return (Map<String, Object>) JSON.toJSON(object);
	}
	
	public static <PK> Map<String, Object> transPK2Map(PK object){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", object);
		return map;
	}
}
