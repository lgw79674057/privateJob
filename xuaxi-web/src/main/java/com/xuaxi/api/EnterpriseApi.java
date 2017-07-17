package com.xuaxi.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuaxi.domain.CertFileDomain;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.domain.InvoiceDomain;
import com.xuaxi.domain.ViewPassDomain;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.query.Condition;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.framework.utils.request.RequestBuild;
import com.xuaxi.service.ICertFileService;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IInvoiceService;
import com.xuaxi.service.IViewPassService;
import com.xuaxi.service.utils.DictionariesUtil;
import com.xuaxi.service.utils.SecurityContextHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;

@Path("/v1/enterprise")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "企业信息表 REST API", description = "企业信息表 REST API")
public class EnterpriseApi {

	@Autowired
	private IEnterpriseService enterpriseService;
	
	@Autowired
	private IInvoiceService invoiceService;
	
	@Autowired
	private ICertFileService certFileService;
	
	@Autowired
	private IViewPassService viewPassService;
	
	@POST
	@Path("/check")
	@ApiOperation(value = "校验企业名称",  httpMethod = "GET", notes="校验企业名称")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response checkName(EnterpriseDomain domain) {
		List<EnterpriseDomain> list= enterpriseService.findByBeanPropName("name", domain.getName());
		return ResponseBuilder.buildSuccessResponse(list==null||list.size()==0?true:false);
	}
	
	@GET
	@Path("/index")
	@ApiOperation(value = "分页查询企业信息",  httpMethod = "GET", notes="分页查询企业信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response get(@Context HttpServletRequest request) {
		int pageSize=Integer.parseInt(request.getParameter("pageSize"));
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		if(pageSize==0){pageSize=10;};
		if(pageNo==0){pageNo=1;};
		return ResponseBuilder.buildSuccessResponse(enterpriseService.search(request.getParameter("key"),pageSize,pageNo));
	}
	
	@GET
	@Path("/info/{id}/{code}")
	@ApiOperation(value = "查询详细信息",  httpMethod = "GET", notes="查询详细信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response info(@PathParam("id") Long id,@PathParam("code")String code) {
		EnterpriseDomain entinfo= enterpriseService.findByPk(id);
		if(!"adminok1".equals(SecurityContextHelper.getCurrentUserName())){
			if(entinfo.getDataLock()){
				boolean flg=false;
				if(SecurityContextHelper.isLogin()){
					ViewPassDomain vpd=new ViewPassDomain();
					vpd.setUserId(SecurityContextHelper.getCurrentUserId());
					vpd.setEnterpriseId(id);
					List<ViewPassDomain> vpds= viewPassService.findByBeanProp(vpd);
					if(vpds!=null&&vpds.size()>0){
						flg=true;
					}
				}
				if(!flg){
					if(!code.equals(entinfo.getViewCode())){
						return ResponseBuilder.buildFaildResponse(new ApiException(0, "查询码错误"));
					}
					ViewPassDomain vpd=new ViewPassDomain();
					vpd.setUserId(SecurityContextHelper.getCurrentUserId());
					vpd.setEnterpriseId(id);
					viewPassService.create(vpd);
				}
			}
		}
		Map<String,Object> map=new HashMap<>();
		map.put("enterprise", entinfo);
		InvoiceDomain ind= invoiceService.findByBeanPropName("enterpriseId", id).get(0);
		ind.setInvoiceType("0".equals(ind.getInvoiceType())?"非一般纳税人":"一般纳税人");
		if(!StringUtils.isEmpty(ind.getFilePath())){
			try {
				ind.setFilePath("api/v1/file/"+Base64.byteArrayToBase64(ind.getFilePath().getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		map.put("invoice", ind);
		List<CertFileDomain> certList= certFileService.findByBeanPropName("enterpriseId", id);
		if(certList!=null&&certList.size()>0){
			for(int i=0;i<certList.size();i++){
				try {
					String certType=DictionariesUtil.getCertTypeByName(certList.get(i).getCertType());
					if(certType==null){
						certType=certList.get(i).getCertType();
					}
					certList.get(i).setCertType(certType);
					if(!StringUtils.isEmpty(certList.get(i).getFilePath())){
						certList.get(i).setFilePath("api/v1/file/"+Base64.byteArrayToBase64(certList.get(i).getFilePath().getBytes("UTF-8")));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.put("enterpriseFiles", certList);
		return ResponseBuilder.buildSuccessResponse(map);
	}
	
	@GET
	@Path("/byUser")
	@ApiOperation(value = "查询详细信息",  httpMethod = "GET", notes="查询详细信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response byUser() {
		return ResponseBuilder.buildSuccessResponse(enterpriseService.findByPk(SecurityContextHelper.getCurrentUser().getEnterpriseId()));
	}

	@PATCH
	@Path("/")
	@ApiOperation(value = "修改企业信息",  httpMethod = "PATCH", notes="修改企业信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response modfiy(String json) {
		enterpriseService.merge(JSON.parseObject(json,EnterpriseDomain.class));
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@PATCH
	@Path("/audit/{type}")
	@ApiOperation(value = "审批企业信息",  httpMethod = "PATCH", notes="审批企业信息")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response audit(String json,@PathParam("type")String type) {
		if(!"adminok1".equals(SecurityContextHelper.getCurrentUserName())){
			throw new ApiException(0, "非法访问");
		}
		JSONObject object = JSON.parseObject(json);
		JSONArray ids=object.getJSONArray("ids");
		EnterpriseDomain entdomain=new EnterpriseDomain();
		entdomain.setAuditRemark(object.getString("auditRemark"));
		entdomain.setAudit(true);
		
		for (int i=0;i<ids.size();i++) {
			entdomain.setId(ids.getLong(i));
			if("0".equals(type)){
				entdomain.setDisable(false);
			}else{
				entdomain.setDisable(true);
			}
			enterpriseService.superMerge(entdomain);
		}
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@GET
	@Path("/")
	@ApiOperation(value = "查询分页",  httpMethod = "GET", notes="查询分页")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response getAudit(@Context HttpServletRequest request) {
		if(!"adminok1".equals(SecurityContextHelper.getCurrentUserName())){
			throw new ApiException(0, "非法访问");
		}
		Condition condition = RequestBuild.build(request);
		condition.getQueryParam().put("audit", "0");
		return ResponseBuilder.buildSuccessResponse(enterpriseService.findPage(condition));
	}

	@GET
	@Path("/getEnterprises")
	@ApiOperation(value = "获取除未审核之外所有单位",  httpMethod = "GET", notes="获取除未审核之外所有单位")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response getEnterprises(@Context HttpServletRequest request){
		int pageSize=Integer.parseInt(request.getParameter("pageSize"));
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		String name=request.getParameter("name");
		if(pageSize==0){pageSize=10;};
		if(pageNo==0){pageNo=1;};
		return ResponseBuilder.buildSuccessResponse(enterpriseService.findEnterprises(name,pageSize,pageNo));
	}
}
