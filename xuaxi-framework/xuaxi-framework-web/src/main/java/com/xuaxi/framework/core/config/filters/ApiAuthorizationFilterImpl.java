package com.xuaxi.framework.core.config.filters;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;

import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;

@SuppressWarnings("deprecation")
public class ApiAuthorizationFilterImpl implements SwaggerSpecFilter {

	public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params,
			Map<String, String> cookies, Map<String, List<String>> headers) {
		return checkKey(params, headers);
	}

	
	public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api,
			Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
			return true;
	}

	public boolean isPropertyAllowed(Model model, Property property, String propertyName,
			Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
	 
		return true;
	}

	public boolean checkKey(Map<String, List<String>> params, Map<String, List<String>> headers) {
		String key=SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("swagger.key");
		if(StringUtils.isEmpty(key)){
			return true;
		}
		if(params==null||!params.containsKey("api_key")||params.get("api_key").size()==0){
			return false;
		}
		if(key.equals(params.get("api_key").get(0))){
			return true;
		}
		return false;
	}
}