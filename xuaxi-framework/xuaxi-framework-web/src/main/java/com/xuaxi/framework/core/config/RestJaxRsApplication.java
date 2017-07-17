package com.xuaxi.framework.core.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.validation.ValidationFeature;

import com.xuaxi.framework.core.config.filters.CORSResponseFilter;
import com.xuaxi.framework.core.config.providers.ObjectMapperProvider;
import com.xuaxi.framework.core.exceptions.ServiceExceptionMapper;

public class RestJaxRsApplication extends ResourceConfig{
	public RestJaxRsApplication(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE,true);
        register(ValidationFeature.class);
        register(RequestContextFilter.class);
        register(CORSResponseFilter.class);
        register(HttpMethodOverrideFilter.class); 
        register(ObjectMapperProvider.class);
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        register(ServiceExceptionMapper.class);
    }

}
