package com.xuaxi.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuaxi.domain.ChangeRecordDomain;
import com.xuaxi.domain.FollowDomain;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.service.IChangeRecordService;
import com.xuaxi.service.IFollowService;
import com.xuaxi.service.utils.SecurityContextHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/v1/changerecord")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "变更记录表 REST API", description = "变更记录表 REST API")
public class ChangeRecordApi {

	@Autowired
	private IChangeRecordService changeRecordService;
	
	@Autowired
	private IFollowService followServiceImpl;

	@Autowired
	private IEnterpriseService enterpriseService;
	
	@GET
	@Path("/{enterpriseId}/byUser")
	@ApiOperation(value = "根据enterpriseId查询变更记录表",  httpMethod = "GET", notes="根据enterpriseId查询变更记录表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findByIdByUser(@PathParam("enterpriseId") Long enterpriseId) {
//		//先查看是否审核过,如果审核中不允许查看
//		EnterpriseDomain enterpriseDomain=enterpriseService.findByPk(enterpriseId);
//		if(!enterpriseDomain.getAudit()){
//			return ResponseBuilder.buildFaildResponse(new ApiException(0,"审核中,稍后查看"));
//		}
		ChangeRecordDomain domain=new ChangeRecordDomain();
		domain.setEnterpriseId(enterpriseId);
//		domain.setChangeType("1");
		
		FollowDomain fd=new FollowDomain();
		fd.setEnterpriseId(enterpriseId);
		fd.setUserId(SecurityContextHelper.getCurrentUserId());
		List<FollowDomain> fs= followServiceImpl.findByBeanProp(fd);
		if(fs.size()>0){
			fd=new FollowDomain();
			fd.setNewChange(false);
			fd.setId(fs.get(0).getId());
			followServiceImpl.merge(fd);
		}
		return ResponseBuilder.buildSuccessResponse(changeRecordService.findByBeanProp(domain));
	}
	
	@GET
	@Path("/{enterpriseId}")
	@ApiOperation(value = "根据enterpriseId查询变更记录表",  httpMethod = "GET", notes="根据enterpriseId查询变更记录表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findById(@PathParam("enterpriseId") Long enterpriseId) {
		ChangeRecordDomain domain=new ChangeRecordDomain();
		domain.setEnterpriseId(enterpriseId);
		domain.setChangeType("1");
		return ResponseBuilder.buildSuccessResponse(changeRecordService.findByBeanProp(domain));
	}
}
