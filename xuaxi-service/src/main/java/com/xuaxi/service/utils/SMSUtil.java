package com.xuaxi.service.utils;

import java.sql.Timestamp;
import java.util.Date;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.xuaxi.dao.ISMSRecordDao;
import com.xuaxi.domain.SMSRecordDomain;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;
import com.xuaxi.service.ISMSRecordService;
import com.xuaxi.service.impl.RedisServerImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SMSUtil {

	private static IClientProfile profile;
	
	private static IAcsClient client;
	
	private static String signature;
	
	private static String templateCode;
	
	private static String smsCodeName;
	
	private static ISMSRecordDao smsRecordDao;
	
	private static ISMSRecordService smsRecordService;
	
	private static RedisServerImpl redisServerImpl;
	
	private static String []str=new String[]{"000000","00000","0000","000","00","00","0",""};
	
	private static void init() throws ClientException{
		PropertyConfigurer propertyConfigurer=SpringContextUtil.getBean(PropertyConfigurer.class);
		profile=DefaultProfile.getProfile("cn-hangzhou", propertyConfigurer.getProperty("sms.aliyun.accessKey"),propertyConfigurer.getProperty("sms.aliyun.accessSecret"));
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
		client = new DefaultAcsClient(profile);
		signature=propertyConfigurer.getProperty("sms.aliyun.signature");
		templateCode=propertyConfigurer.getProperty("sms.aliyun.templateCode");
		smsCodeName=propertyConfigurer.getProperty("sms.aliyun.smsCodeName");
		smsRecordDao=SpringContextUtil.getBean(ISMSRecordDao.class);
		smsRecordService=SpringContextUtil.getBean(ISMSRecordService.class);
		redisServerImpl=SpringContextUtil.getBean(RedisServerImpl.class);
	}
	
	public static void sendSMS(String phone){
		try {
			if(profile==null||client==null||redisServerImpl==null){
				init();
			}
			sendCheck(phone);
			String code=getSMSCode();
			redisServerImpl.setValue(Constant.SMS_DB_INDEX, Constant.SMS_KEY+phone, code, 1800);
			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName(signature);
			request.setTemplateCode(templateCode);
			request.setParamString("{\""+smsCodeName+"\":\""+code+"\"}");
			request.setRecNum(phone);
			client.getAcsResponse(request);
			saveSMS(phone, code);
		} catch (ServerException e) {
			e.printStackTrace();
			throw new ApiException(2001, "发送失败");
		} catch (ClientException e) {
			e.printStackTrace();
			throw new ApiException(2001, "发送失败");
		}
	}
	
	private static String getSMSCode(){
		String sms= String.valueOf(((Double)(Math.random()*1000000)).intValue());
		sms+=str[sms.length()];
		if(sms.length()>6){
			return sms.substring(0, 6);
		}
		return sms;
	}
	
	private static void saveSMS(String phone,String code){
		SMSRecordDomain smsRe=new SMSRecordDomain();
		smsRe.setPhone(phone);
		smsRe.setSmsContect(code);
		smsRe.setSendTime(new Timestamp(new Date().getTime()));
		smsRecordService.create(smsRe);
	}
	
	private static void sendCheck(String phone){
		if(smsRecordDao.findFiveMinSMSCount(phone)>5){
			throw new ApiException(2001, "5分钟内发送短信超过限制");
		}
		if(smsRecordDao.findDaySMSCount(phone)>20){
			throw new ApiException(2001, "当天发送短信超过限制");
		}
	}
	
	
	
	public static boolean checkCode(String phone,String code,boolean destroy){
		try {
			if(profile==null||client==null||redisServerImpl==null){
				init();
			}
			
			Jedis jedis = SpringContextUtil.getBean(JedisPool.class).getResource();
			try {
				jedis.select(Constant.SMS_DB_INDEX);
				if(code.equals(jedis.get(Constant.SMS_KEY+phone))){
					if(destroy){
						jedis.del(Constant.SMS_KEY+phone);
					}
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApiException(1012);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return false;
	}
}
