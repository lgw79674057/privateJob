package com.xuaxi.framework.core.resource;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

public class LoaderService {

	@Autowired
	private ResourceLoader resourceLoader;

	public Resource[] getResource(String path) throws IOException {
		return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(path);
	}
}
