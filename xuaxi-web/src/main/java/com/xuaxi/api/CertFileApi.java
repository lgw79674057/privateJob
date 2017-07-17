package com.xuaxi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xuaxi.domain.CertFileDomain;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.ICertFileService;
import com.xuaxi.service.utils.SecurityContextHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import io.swagger.annotations.ApiResponse;

@Path("/v1/certfile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "企业证照表 REST API", description = "企业证照表 REST API")
public class CertFileApi {

	@Autowired
	private ICertFileService certFileService;
	
	@GET
	@Path("/byUser")
	@ApiOperation(value = "根据enterpriseId查询企业证照表",  httpMethod = "GET", notes="根据enterpriseId查询企业证照表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findById() {
		return ResponseBuilder.buildSuccessResponse(certFileService.findByBeanPropName("enterpriseId", SecurityContextHelper.getCurrentUser().getEnterpriseId()));
	}
	
	
	@PATCH
	@Path("/")
	@ApiOperation(value = "修改附件按登陆用户",  httpMethod = "GET", notes="修改附件按登陆用户")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response modifiy(String json) {
		certFileService.modifys(JSON.parseArray(json, CertFileDomain.class));
		return ResponseBuilder.buildSuccessResponse();
	}
}
