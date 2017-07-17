package com.xuaxi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.domain.UserDomain;
import com.xuaxi.framework.core.entity.User;
import com.xuaxi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.query.Condition;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.framework.utils.request.RequestBuild;
import com.xuaxi.domain.FollowDomain;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IFollowService;
import com.xuaxi.service.utils.SecurityContextHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/v1/follow")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "关注企业表 REST API", description = "关注企业表 REST API")
public class FollowApi {

	@Autowired
	private IFollowService followService;
	
	@Autowired
	private IEnterpriseService enterpriseService;

	@Autowired
	private IUserService iUserService;
	
	@POST
	@Path("/")
	@ApiOperation(value = "添加关注企业表",  httpMethod = "POST", notes="添加关注企业表")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response add(FollowDomain domain) {
		if(domain.getEnterpriseId()!=null&&!domain.getEnterpriseId().equals(0)&&enterpriseService.findByPk(domain.getEnterpriseId())!=null){
			domain.setUserId(SecurityContextHelper.getCurrentUserId());
			FollowDomain s=new FollowDomain();
			s.setEnterpriseId(domain.getEnterpriseId());
			s.setUserId(SecurityContextHelper.getCurrentUserId());
			List<FollowDomain> f= followService.findByBeanProp(s);
			if(f!=null&&f.size()>0){
				throw new ApiException(0, "您已关注该企业，请勿重复关注");
			}
			//如果关注数到限制,则不允许关注
			UserDomain userDomain=iUserService.findByPk(SecurityContextHelper.getCurrentUserId());
			if(userDomain.getFollowedNum()<=userDomain.getFollowed()+1){
				throw new ApiException(0, "关注数已满,请联系管理员");
			}else{
				userDomain.setFollowed(userDomain.getFollowed()+1);
				iUserService.merge(userDomain);
			}
			return ResponseBuilder.buildSuccessResponse(followService.create(domain));
		}
		throw new ApiException(0, "非法操作");
	}

	@GET
	@Path("/")
	@ApiOperation(value = "查询关注企业",  httpMethod = "GET", notes="查询关注企业")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response get(@Context HttpServletRequest request) {
		Map<String,Object> param=new HashMap<>();
		param.put("userId", SecurityContextHelper.getCurrentUserId());
		Condition cond= RequestBuild.build(request);
		cond.getQueryParam().put("userId", SecurityContextHelper.getCurrentUserId().toString());
		return ResponseBuilder.buildSuccessResponse(followService.findPage(cond,param));
	}
	
	@GET
	@Path("/byChange")
	@ApiOperation(value = "查询关注企业",  httpMethod = "GET", notes="查询关注企业")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response get1() {
		Map<String,Object> param=new HashMap<>();
		param.put("userId", SecurityContextHelper.getCurrentUserId());
		FollowDomain domain=new FollowDomain();
		domain.setUserId(SecurityContextHelper.getCurrentUserId());
		domain.setNewChange(true);
		List<FollowDomain>  followDomains=followService.findByBeanProp(domain,param);
		List<FollowDomain> fds=new ArrayList<>();
		for (FollowDomain f : followDomains) {
			EnterpriseDomain ed=enterpriseService.findByPk(f.getEnterpriseId());
			if(ed.getAudit()){
				fds.add(f);
			}
		}
		return ResponseBuilder.buildSuccessResponse(fds);
	}
	
	
	@DELETE
	@Path("/")
	@ApiOperation(value = "取消关注",  httpMethod = "DELETE", notes="取消关注")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response delete(FollowDomain domain) {
		if(domain.getEnterpriseId()!=null&&!domain.getEnterpriseId().equals(0)){
			domain.setUserId(SecurityContextHelper.getCurrentUserId());
			FollowDomain s=new FollowDomain();
			s.setEnterpriseId(domain.getEnterpriseId());
			s.setUserId(SecurityContextHelper.getCurrentUserId());
			List<FollowDomain> f= followService.findByBeanProp(s);
			if(f!=null&&f.size()>0){
				followService.remove(f.get(0).getId());
			}
			//取消关注,对应关注数-1
			UserDomain userDomain=iUserService.findByPk(SecurityContextHelper.getCurrentUserId());
			if(userDomain.getFollowed()!=null&&userDomain.getFollowed()!=0){
				userDomain.setFollowed((userDomain.getFollowed()-1)<=0?0:userDomain.getFollowed()-1);
			}else{
				userDomain.setFollowedNum(0);
			}
			iUserService.merge(userDomain);
			return ResponseBuilder.buildSuccessResponse();
		}
		throw new ApiException(0, "非法操作");
	}
}
