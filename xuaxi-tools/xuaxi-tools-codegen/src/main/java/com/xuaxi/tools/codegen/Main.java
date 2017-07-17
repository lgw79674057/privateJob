package com.xuaxi.tools.codegen;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		String basePath = "D:\\workspace\\java-private\\xuaxi";
		String daoPath = "xuaxi-dao";
		String servicePath = "xuaxi-service";
		String entityPath = "xuaxi-entity";
		String domainPath = "xuaxi-domain";
		String apiPath = "xuaxi-web";
		String entityPackage = "com.xuaxi.entity";
		String daoPackage = "com.xuaxi.dao";
		String domainPackage = "com.xuaxi.domain";
		String servicePackage = "com.xuaxi.service";
		String apiPackage = "com.xuaxi.api";
		String template = "base";
		String db_name = "weicode";
		String tablename = "rm_address_cnarea";
		String classname = "Address";
		String restFullPath = "address";
		String tableDesc = "行政区划表";
		new GenUtils(basePath, daoPath, servicePath, entityPath, domainPath, apiPath, template).gen(db_name, tablename,
				restFullPath, classname, entityPackage, domainPackage, daoPackage, servicePackage, apiPackage,
				tableDesc);
	}
}
