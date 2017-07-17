package com.xuaxi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.utils.DictionariesUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/dict")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "字典数据 REST API", description = "字典数据REST API")
public class DictApi {

	@GET
	@Path("/nationalitys")
	@ApiOperation(value = "国籍",  httpMethod = "GET", notes="国籍")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response nationalitys() {
		return ResponseBuilder.buildSuccessResponse(DictionariesUtil.getNationality());
	}
	
	@GET
	@Path("/cardType")
	@ApiOperation(value = "证件类型",  httpMethod = "GET", notes="证件类型")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response cardType() {
		return ResponseBuilder.buildSuccessResponse(DictionariesUtil.getCardType());
	}
	
	@GET
	@Path("/certType")
	@ApiOperation(value = "企业证照类型",  httpMethod = "GET", notes="企业证照类型")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response certType() {
		return ResponseBuilder.buildSuccessResponse(DictionariesUtil.getCertType());
	}
}
