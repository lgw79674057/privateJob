package com.xuaxi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xuaxi.domain.InvoiceDomain;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.IInvoiceService;
import com.xuaxi.service.utils.SecurityContextHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import io.swagger.annotations.ApiResponse;

@Path("/v1/invoice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "企业发票信息表 REST API", description = "企业发票信息表 REST API")
public class InvoiceApi {

	@Autowired
	private IInvoiceService invoiceService;
	
	@GET
	@Path("/byUser")
	@ApiOperation(value = "根据登录用户查询企业发票信息表",  httpMethod = "GET", notes="根据登录用户查询企业发票信息表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findById() {
		return ResponseBuilder.buildSuccessResponse(invoiceService.findByBeanPropName("enterpriseId", SecurityContextHelper.getCurrentUser().getEnterpriseId()).get(0));
	}
	
	@PATCH
	@Path("/")
	@ApiOperation(value = "根据登录用户修改企业发票信息表",  httpMethod = "PATCH", notes="根据登录用户修改企业发票信息表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response modify(String json) {
		return ResponseBuilder.buildSuccessResponse(invoiceService.merge(JSON.parseObject(json,InvoiceDomain.class)));
	}
}
