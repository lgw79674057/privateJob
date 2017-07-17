package com.xuaxi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xuaxi.framework.core.exceptions.ApiException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("redisServerImpl")
public class RedisServerImpl {

	@Autowired
	private JedisPool jedisPool;
	
	@Value("${rbac.token.timeout}")
	private Integer tokenTimeout;

	@Value("${rbac.token.key}")
	private String tokenKey;
	
	public String getValue(int dbIndex, String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(1012);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	public void setValue(int dbIndex, String key,String value,int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			jedis.set(key,value);
			jedis.expire(key, expire);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(1011);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
