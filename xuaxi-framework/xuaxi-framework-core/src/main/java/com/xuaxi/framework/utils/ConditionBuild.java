package com.xuaxi.framework.utils;

import java.util.HashMap;
import java.util.Map;

import com.xuaxi.framework.core.entity.KeyValue;
import com.xuaxi.framework.core.query.Condition;

public class ConditionBuild {

	@SuppressWarnings("unchecked")
	public static Condition build(KeyValue<String, String> ...params){
		Condition condition=new Condition();
		Map<String, String> map=new HashMap<>();
		if(params!=null&&params.length>0){
			for (KeyValue<String, String> keyValue : params) {
				map.put(keyValue.getKey(),keyValue.getValue());
			}
		}
		condition.setQueryParam(map);
		return condition;
	}
}
