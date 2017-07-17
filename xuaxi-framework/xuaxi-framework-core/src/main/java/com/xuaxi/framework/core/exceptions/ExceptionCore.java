package com.xuaxi.framework.core.exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xuaxi.framework.core.resource.LoaderService;
import com.xuaxi.framework.core.spring.PropertyConfigurer;
import com.xuaxi.framework.core.spring.SpringContextUtil;

public class ExceptionCore {

	private static String language;

	private static Map<Integer, ExceptionBean> exceptionMap;

	public static String getDefaultLanguage() {
		if (language == null) {
			language = SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("system.exception.language.default");
		}
		return language;
	}

	public static ExceptionBean getException(Integer id) {
		if (exceptionMap == null) {
			try {
				synchronized (ExceptionCore.class) {
					if (exceptionMap == null) {
						exceptionMap = new HashMap<Integer, ExceptionBean>();
						init();
					}
				}
			} catch (BeansException | IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return exceptionMap.get(id);
	}

	private synchronized static void init() throws BeansException, IOException {
		Resource[] res = SpringContextUtil.getBean(LoaderService.class).getResource(SpringContextUtil.getBean(PropertyConfigurer.class).getProperty("system.exception.config"));
		for (Resource resource : res) {
			InputStream ins = resource.getInputStream();
			try {
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ins);
				NodeList nodelist = document.getDocumentElement().getChildNodes();
				for (int i = 0; i < nodelist.getLength(); i++) {
					Node node = nodelist.item(i);
					if ("exception".equals(node.getNodeName())) {
						ExceptionBean bean = new ExceptionBean();
						NamedNodeMap attr = node.getAttributes();
						bean.setId(Integer.parseInt(attr.getNamedItem("id").getNodeValue()));
						bean.setResponseCode(Integer.parseInt(attr.getNamedItem("responseCode") == null ? "500" : (StringUtils.isEmpty(attr.getNamedItem("responseCode").getNodeValue()) ? "500" : attr.getNamedItem("responseCode").getNodeValue())));
						bean.setDescribe(attr.getNamedItem("describe") == null ? null : attr.getNamedItem("describe").getNodeValue());
						NodeList messageNodes = node.getChildNodes();
						Map<String, String> messageMap = new HashMap<String, String>();
						for (int y = 0; y < messageNodes.getLength(); y++) {
							Node messageNode = messageNodes.item(y);
							if ("message".equals(messageNode.getNodeName())) {
								if (messageNode.getAttributes().getNamedItem("language") != null && !StringUtils.isEmpty(messageNode.getAttributes().getNamedItem("language").getNodeValue())) {
									messageMap.put(messageNode.getAttributes().getNamedItem("language").getNodeValue(), messageNode.getAttributes().getNamedItem("value") != null ? messageNode.getAttributes().getNamedItem("value").getNodeValue() : null);
								}
							}
						}
						bean.setMessage(messageMap);
						exceptionMap.put(bean.getId(), bean);
					}
				}

			} catch (SAXException | ParserConfigurationException e) {
				e.printStackTrace();
			} finally {
				ins.close();
			}
		}
	}
}
