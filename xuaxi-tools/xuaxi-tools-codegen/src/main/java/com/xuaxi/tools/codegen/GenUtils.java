package com.xuaxi.tools.codegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class GenUtils {

	private String basePath;

	private String daoPath;

	private String servicePath;

	private String entityPath;

	private String domainPath;

	private String apiPath;

	private String templateName;

	private Map<String, Boolean> baseColumns;

	private Map<String, Class<?>> dbType2JavaType;

	private List<Class<?>> noImportTag;

	public GenUtils(String basePath, String daoPath, String servicePath, String entityPath, String domainPath,
			String apiPath, String template) {
		this.basePath = basePath;
		this.daoPath = daoPath;
		this.servicePath = servicePath;
		this.entityPath = entityPath;
		this.domainPath = domainPath;
		this.apiPath = apiPath;
		this.templateName = template;
		baseColumns = new HashMap<String, Boolean>();
		baseColumns.put("ID", false);
		baseColumns.put("DELETETAG", false);
		baseColumns.put("CREATEBY", false);
		baseColumns.put("CREATETIME", false);
		baseColumns.put("MODIFYBY", false);
		baseColumns.put("MODIFYTIME", false);
		baseColumns.put("MODIFYBYNAME", false);
		baseColumns.put("CREATEBYNAME", false);
		dbType2JavaType = new HashMap<String, Class<?>>();
		dbType2JavaType.put("varchar", String.class);
		dbType2JavaType.put("tinyint", Integer.class);
		dbType2JavaType.put("timestamp", Timestamp.class);
		dbType2JavaType.put("text", String.class);
		dbType2JavaType.put("longtext", String.class);
		dbType2JavaType.put("int", Integer.class);
		dbType2JavaType.put("double", BigDecimal.class);
		dbType2JavaType.put("decimal", BigDecimal.class);
		dbType2JavaType.put("datetime", Date.class);
		dbType2JavaType.put("date", Date.class);
		dbType2JavaType.put("char", String.class);
		dbType2JavaType.put("bigint", Long.class);
		dbType2JavaType.put("smallint", Integer.class);
		dbType2JavaType.put("mediumint", Long.class);
		noImportTag = new ArrayList<Class<?>>();
		noImportTag.add(int.class);
		noImportTag.add(byte.class);
		noImportTag.add(float.class);
		noImportTag.add(double.class);
		noImportTag.add(short.class);
		noImportTag.add(long.class);
		noImportTag.add(char.class);
		noImportTag.add(boolean.class);
		noImportTag.add(String.class);
		noImportTag.add(Integer.class);
		noImportTag.add(Long.class);
	}

	public void gen(String db_name, String tablename, String restFullPath, String className, String entityPackage,
			String domainPackage, String daoPackage, String servicePackage, String apiPackage, String tableDesc)
			throws FileNotFoundException, UnsupportedEncodingException {
		@SuppressWarnings("resource")
		SqlSession sqlsession = (SqlSession) new ClassPathXmlApplicationContext("classpath:spring-context.xml")
				.getBean("information_schemaSqlsession");
		Map<String, String> param = new HashMap<String, String>();
		param.put("table_name", tablename);
		param.put("db_name", db_name);
		List<Map<?, ?>> data = sqlsession.selectList("com.xuaxi.codegen.getColumnInfo", param);

		Context velocityParam = new VelocityContext();

		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
		List<String> importList = new ArrayList<String>();
		for (Map<?, ?> map : data) {
			String column_name = (String) map.get("column_name");
			if ("ID".equals(column_name.toUpperCase())) {
				if (!"PRI".equals(map.get("column_key"))) {
					throw new RuntimeException("ID字段必须为主键");
				}
			}
			String data_type = (String) map.get("data_type");
			if (baseColumns.containsKey(column_name.toUpperCase())) {
				baseColumns.put(column_name.toUpperCase(), true);
			} else {
				Map<String, String> columnMap = new HashMap<String, String>();
				columnMap.put("columnName", column_name);
				columnMap.put("dbType", data_type);
				Class<?> c = dbType2JavaType.get(data_type);
				if("TINYINT(1)".equals(map.get("column_type").toString().toUpperCase())){
					c=Boolean.class;
				}
				if (c == null) {
					throw new RuntimeException("找不到数据库类型" + data_type + "的对应java类型");
				}
				if (!noImportTag.contains(c)) {
					importList.add(c.getTypeName());
				}
				columnMap.put("javaType", c.getTypeName());
				columnMap.put("javaTypeName", c.getSimpleName());
				columnMap.put("javaName", column_name);
				if (!StringUtils.isEmpty(map.get("column_comment"))) {
					columnMap.put("comment", (String) map.get("column_comment"));
				}
				columnMap.put("getSetName", getGetSetName(column_name));
				columns.add(columnMap);
			}
		}
		for (String string : baseColumns.keySet()) {
			if (!baseColumns.get(string)) {
				throw new RuntimeException("缺少必须字段" + string);
			}
		}
		velocityParam.put("columns", columns);
		velocityParam.put("attName", Character.toLowerCase(className.charAt(0))+className.substring(1));
		velocityParam.put("imports", importList);
		velocityParam.put("restFullPath", restFullPath);
		velocityParam.put("tableDesc", tableDesc);
		velocityParam.put("className", className);
		velocityParam.put("tableDesc", tableDesc);
		velocityParam.put("entityPackage", entityPackage);
		velocityParam.put("domainPackage", domainPackage);
		velocityParam.put("daoPackage", daoPackage);
		velocityParam.put("servicePackage", servicePackage);
		velocityParam.put("apiPackage", apiPackage);
		velocityParam.put("serialVersionUIDEntity", (long) (Math.random() * 1000000000000000000L));
		velocityParam.put("serialVersionUIDDomain", (long) (Math.random() * 1000000000000000000L));
		velocityParam.put("tableName", tablename);
		genEntity(velocityParam);
		genDao(velocityParam);
		genDomain(velocityParam);
		genService(velocityParam);
		genApi(velocityParam);
	}

	private String getGetSetName(String cloum) {
		if (cloum.length() > 1) {
			if (!Character.isUpperCase(cloum.charAt(1))) {
				return Character.toUpperCase(cloum.charAt(0)) + cloum.substring(1);
			} else {
				return cloum;
			}
		}
		return cloum.toUpperCase();
	}

	/**
	 * 生成entity
	 * 
	 * @param velocityParam
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private void genEntity(Context velocityParam) throws FileNotFoundException, UnsupportedEncodingException {
		Velocity.setProperty("input.encoding", "utf-8");
		Velocity.setProperty("output.encoding", "utf-8");
		Velocity.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init();
		Template template = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "Entity.vm");
		File entityFile = new File(basePath + File.separator + entityPath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("entityPackage")).replaceAll("\\.", "/") + File.separator
				+ velocityParam.get("className") + "Entity.java");
		if (!entityFile.getParentFile().exists()) {
			entityFile.getParentFile().mkdirs();
		}
		PrintWriter writer = new PrintWriter(entityFile, "utf-8");
		template.merge(velocityParam, writer);
		System.out.println("输出Entity代码到：" + entityFile.getPath());
		writer.flush();
		writer.close();
	}

	/**
	 * 生成dao
	 * 
	 * @param velocityParam
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private void genDao(Context velocityParam) throws FileNotFoundException, UnsupportedEncodingException {
		Velocity.setProperty("input.encoding", "utf-8");
		Velocity.setProperty("output.encoding", "utf-8");
		Velocity.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init();
		Template template = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "IDao.vm");
		File daoFile = new File(basePath + File.separator + daoPath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("daoPackage")).replaceAll("\\.", "/") + File.separator + "I"
				+ velocityParam.get("className") + "Dao.java");
		if (!daoFile.getParentFile().exists()) {
			daoFile.getParentFile().mkdirs();
		}
		PrintWriter writer = new PrintWriter(daoFile, "utf-8");
		template.merge(velocityParam, writer);
		System.out.println("输出Dao代码到：" + daoFile.getPath());
		writer.flush();
		writer.close();
		
		
		Template templateImpl = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "DaoImpl.vm");
		File daoImplFile = new File(basePath + File.separator + daoPath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("daoPackage")).replaceAll("\\.", "/") + File.separator + "impl"+File.separator
				+ velocityParam.get("className") + "DaoImpl.java");
		if (!daoImplFile.getParentFile().exists()) {
			daoImplFile.getParentFile().mkdirs();
		}
		PrintWriter implWriter = new PrintWriter(daoImplFile, "utf-8");
		templateImpl.merge(velocityParam, implWriter);
		System.out.println("输出DaoImpl代码到：" + daoImplFile.getPath());
		implWriter.flush();
		implWriter.close();
		
		
		Template templateXml = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "map.vm");
		File xmlFile = new File(basePath + File.separator + daoPath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("daoPackage")).replaceAll("\\.", "/") + File.separator + "impl"+File.separator
				+ velocityParam.get("className") + ".map.xml");
		if (!xmlFile.getParentFile().exists()) {
			xmlFile.getParentFile().mkdirs();
		}
		PrintWriter xmlWriter = new PrintWriter(xmlFile, "utf-8");
		templateXml.merge(velocityParam, xmlWriter);
		System.out.println("输出map代码到：" + xmlFile.getPath());
		xmlWriter.flush();
		xmlWriter.close();
	}
	
	/**
	 * 生成Domain
	 * 
	 * @param velocityParam
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private void genDomain(Context velocityParam) throws FileNotFoundException, UnsupportedEncodingException {
		Velocity.setProperty("input.encoding", "utf-8");
		Velocity.setProperty("output.encoding", "utf-8");
		Velocity.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init();
		Template template = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "Domain.vm");
		File domainFile = new File(basePath + File.separator + domainPath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("domainPackage")).replaceAll("\\.", "/") + File.separator 
				+ velocityParam.get("className") + "Domain.java");
		if (!domainFile.getParentFile().exists()) {
			domainFile.getParentFile().mkdirs();
		}
		PrintWriter writer = new PrintWriter(domainFile, "utf-8");
		template.merge(velocityParam, writer);
		System.out.println("输出Domain代码到：" + domainFile.getPath());
		writer.flush();
		writer.close();
	}

	/**
	 * 生成Service
	 * 
	 * @param velocityParam
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private void genService(Context velocityParam) throws FileNotFoundException, UnsupportedEncodingException {
		Velocity.setProperty("input.encoding", "utf-8");
		Velocity.setProperty("output.encoding", "utf-8");
		Velocity.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init();
		Template template = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "IService.vm");
		File serviceFile = new File(basePath + File.separator + servicePath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("servicePackage")).replaceAll("\\.", "/") + File.separator 
				+ "I"+velocityParam.get("className") + "Service.java");
		if (!serviceFile.getParentFile().exists()) {
			serviceFile.getParentFile().mkdirs();
		}
		PrintWriter writer = new PrintWriter(serviceFile, "utf-8");
		template.merge(velocityParam, writer);
		System.out.println("输出Service代码到：" + serviceFile.getPath());
		writer.flush();
		writer.close();
		
		Template templateimpl= Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "ServiceImpl.vm");
		File serviceImplFile = new File(basePath + File.separator + servicePath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("servicePackage")).replaceAll("\\.", "/") + File.separator 
				+"impl"+File.separator+ velocityParam.get("className") + "ServiceImpl.java");
		if (!serviceImplFile.getParentFile().exists()) {
			serviceImplFile.getParentFile().mkdirs();
		}
		PrintWriter writerImpl = new PrintWriter(serviceImplFile, "utf-8");
		templateimpl.merge(velocityParam, writerImpl);
		System.out.println("输出ServiceImpl代码到：" + serviceImplFile.getPath());
		writerImpl.flush();
		writerImpl.close();
	}
	
	/**
	 * 生成Api
	 * 
	 * @param velocityParam
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private void genApi(Context velocityParam) throws FileNotFoundException, UnsupportedEncodingException {
		Velocity.setProperty("input.encoding", "utf-8");
		Velocity.setProperty("output.encoding", "utf-8");
		Velocity.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init();
		Template template = Velocity
				.getTemplate("template" + File.separator + templateName + File.separator + "api.vm");
		File apiFile = new File(basePath + File.separator + apiPath + File.separator + "src" + File.separator
				+ "main" + File.separator + "java" + File.separator
				+ ((String) velocityParam.get("apiPackage")).replaceAll("\\.", "/") + File.separator 
				+ velocityParam.get("className") + "Api.java");
		if (!apiFile.getParentFile().exists()) {
			apiFile.getParentFile().mkdirs();
		}
		PrintWriter writer = new PrintWriter(apiFile, "utf-8");
		template.merge(velocityParam, writer);
		System.out.println("输出Api代码到：" + apiFile.getPath());
		writer.flush();
		writer.close();
	}
}
