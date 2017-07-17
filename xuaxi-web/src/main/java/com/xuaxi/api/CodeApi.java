package com.xuaxi.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.corba.se.impl.encoding.CDROutputObject;
import com.xuaxi.domain.CodeDomain;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.query.Condition;
import com.xuaxi.framework.utils.request.RequestBuild;
import io.swagger.jaxrs.PATCH;
import org.springframework.beans.factory.annotation.Autowired;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.ICodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/v1/code")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "位码表 REST API", description = "位码表 REST API")
public class CodeApi {

	@Autowired
	private ICodeService codeService;
	
	@GET
	@Path("/enterprise/gen/{nationality}")
	@ApiOperation(value = "随机一个位码",  httpMethod = "GET", notes="随机一个位码")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findById(@PathParam("nationality") String nationality) {
		return ResponseBuilder.buildSuccessResponse(codeService.rand("2", nationality,3));
	}

	@GET
	@Path("/")
	@ApiOperation(value = "获取所有位号",  httpMethod = "GET", notes="获取所有位号")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response getAllCode(@Context HttpServletRequest request) {
		Condition condition = RequestBuild.build(request);
//		condition.getQueryParam().put("no", "%"+request.getParameter("no")+"%");
//		Map sortMap = new HashMap<>();
//		sortMap.put("createTime","desc");
//		condition.setSortColumn(sortMap);
		return ResponseBuilder.buildSuccessResponse(codeService.findPage(condition));
	}

	@PATCH
	@Path("/changeNoUse")
	@ApiOperation(value = "更改位号用途",  httpMethod = "GET", notes="更改位号用途")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response changeNoUse(@Context HttpServletRequest request) {
		CodeDomain codeDomain = codeService.findByPk(Long.valueOf(request.getParameter("codeId")));
		codeDomain.setNoUse(request.getParameter("noUseCode"));
		return ResponseBuilder.buildSuccessResponse(codeService.merge(codeDomain));
	}

	@PUT
	@Path("/add")
	@ApiOperation(value = "增加位号",  httpMethod = "PUT", notes="增加位号")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response add(@Context HttpServletRequest request) {
		String nationality =request.getParameter("nationality");
		String enterpriseNo =request.getParameter("enterpriseNo");
		String noType =request.getParameter("noType");
		String noUse =request.getParameter("noUse");
		List<CodeDomain> list=codeService.findByBeanPropName("no",nationality+" "+enterpriseNo);
		if(list!=null && list.size()>0){
			throw new ApiException(0,"位号已存在,请重新输入");
		}else{
			CodeDomain codeDomain = new CodeDomain();
			codeDomain.setNationality(nationality);
			codeDomain.setNo(nationality+" "+enterpriseNo);
			codeDomain.setState(false);
			codeDomain.setNoType(noType);
			codeDomain.setNoUse(noUse);
			codeService.create(codeDomain);
		}
		return ResponseBuilder.buildSuccessResponse();
	}
}
