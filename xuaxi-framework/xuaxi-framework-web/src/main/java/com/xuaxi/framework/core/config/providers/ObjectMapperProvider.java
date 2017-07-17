package com.xuaxi.framework.core.config.providers;


import java.sql.Timestamp;
import java.util.Date;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.xuaxi.framework.utils.serializer.Date2StringSerializer;
import com.xuaxi.framework.utils.serializer.Long2StringSerializer;
import com.xuaxi.framework.utils.serializer.Timestamp2StringSerializer;

/**
 * Created by tonymac on 14/12/26.
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
    final ObjectMapper defaultObjectMapper;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public ObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper();
    }

    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }


    private static ObjectMapper createDefaultMapper() {

    	 final ObjectMapper result = new ObjectMapper();
         //序列化JSON 字符串时忽略属性为NULL 的字段 
    	 result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
         //反序列化成JAVA 对象时 忽略不存在的属性或字段
    	 result.getDeserializationConfig().without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
         result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
         SimpleModule long2StringModule = new SimpleModule("long2StringModule", new Version(2, 5, 4, null,
        		 "com.fasterxml.jackson.core","jackson-databind"));
         long2StringModule.addSerializer(Long.class, new Long2StringSerializer());
         result.registerModule(long2StringModule);
         
         SimpleModule dateFormatModule = new SimpleModule("dateFormatModule", new Version(2, 5, 4, null,
        		 "com.fasterxml.jackson.core","jackson-databind"));
         long2StringModule.addSerializer(Date.class, new Date2StringSerializer());
         result.registerModule(dateFormatModule);
         
         
         SimpleModule timestampFormatModule = new SimpleModule("timestampFormatModule", new Version(2, 5, 4, null,
        		 "com.fasterxml.jackson.core","jackson-databind"));
         long2StringModule.addSerializer(Timestamp.class, new Timestamp2StringSerializer());
         result.registerModule(timestampFormatModule);
         AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
         AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
         AnnotationIntrospector introspector =  AnnotationIntrospector.pair(primary,secondary);
         result.getDeserializationConfig().with(introspector);
         result.getSerializationConfig().with(introspector);
         return result;
    
    }
}
