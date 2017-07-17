package com.xuaxi.framework.core.redis;

import java.io.UnsupportedEncodingException;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJSONRedisSerializer<T> implements RedisSerializer<T> {

	public byte[] serialize(T t) throws SerializationException {
		try {
			return JSON.toJSONString(t,SerializerFeature.WriteClassName,SerializerFeature.DisableCircularReferenceDetect).getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null){
			return null;
		}
		String data;
		try {
			data = new String(bytes,"utf-8");
			Object obj = JSON.parse(data);
			return (T) obj;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
