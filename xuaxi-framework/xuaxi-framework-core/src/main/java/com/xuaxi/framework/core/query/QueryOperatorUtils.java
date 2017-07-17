package com.xuaxi.framework.core.query;

import org.springframework.util.StringUtils;

import com.xuaxi.framework.core.annotation.TableConfig;
import com.xuaxi.framework.core.enums.QueryOperatorEnum;

public class QueryOperatorUtils {

	public static boolean checkSupportOperator(String dbop,TableConfig config){
		if(config==null||config.operators()==null||config.operators().length==0||StringUtils.isEmpty(dbop)){
			return false;
		}
		for (QueryOperatorEnum operator : config.operators()) {
			if("all".equals(operator.name())){
				return true;
			}
			if("none".equals(operator.name())){
				continue;
			}
			if(dbop.equals(operator.getDbOp())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkSupportOperator(QueryOperatorEnum operator,TableConfig config){
		if(config==null||config.operators()==null||config.operators().length==0||operator==null){
			return false;
		}
		for (QueryOperatorEnum o : config.operators()) {
			if("all".equals(o.name())){
				return true;
			}
			if("none".equals(o.name())){
				continue;
			}
			if(operator.name().equals(o.name())){
				return true;
			}
		}
		return false;
	}
}
