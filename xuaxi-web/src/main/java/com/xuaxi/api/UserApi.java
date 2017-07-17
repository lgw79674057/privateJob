package com.xuaxi.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.aopalliance.reflect.Code;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xuaxi.domain.CertFileDomain;
import com.xuaxi.domain.CodeDomain;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.domain.EnterpriseRegDomain;
import com.xuaxi.domain.RegCheckDomain;
import com.xuaxi.domain.UserDomain;
import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.responses.ResponseBuilder;
import com.xuaxi.framework.core.spring.SpringContextUtil;
import com.xuaxi.service.IAuthServer;
import com.xuaxi.service.ICodeService;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IUserService;
import com.xuaxi.service.utils.DictionariesUtil;
import com.xuaxi.service.utils.PasswordTools;
import com.xuaxi.service.utils.SMSUtil;
import com.xuaxi.service.utils.SecurityContextHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;

@Path("/v1/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "用户管理 REST API", description = "用户管理 REST API")
public class UserApi {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICodeService codeService;
	
	@Autowired
	private IEnterpriseService enterpriseService;
	
	@POST
	@Path("/check")
	@ApiOperation(value = "校验userNo是否可用",  httpMethod = "POST", notes="校验userNo是否可用")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response findByUserNo(RegCheckDomain checkDomain) {
//		List<UserDomain> userNoList= userService.findByBeanPropName("userNo", checkDomain.getUserNo());
//		if(userNoList!=null&&userNoList.size()>0){
//			return ResponseBuilder.buildSuccessResponse(1);
//		}
//		List<UserDomain> loginNameList= userService.findByBeanPropName("loginName", checkDomain.getLoginName());
//		if(loginNameList!=null&&loginNameList.size()>0){
//			return ResponseBuilder.buildSuccessResponse(2);
//		}
		if(!SMSUtil.checkCode(checkDomain.getTelphone(), checkDomain.getSmsCode(),false)){
			return ResponseBuilder.buildSuccessResponse(3);
		}
		return ResponseBuilder.buildSuccessResponse(0);
	}
	
	
	@POST
	@Path("/sms/{type}")
	@ApiOperation(value = "发送短信",  httpMethod = "POST", notes="发送短信")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response sendSMS(RegCheckDomain checkDomain,@PathParam("type") String type) {
		try{
			List<UserDomain> users = userService.findByBeanPropName("telphone", checkDomain.getTelphone());
			if("1".equals(type)){
//				if(users!=null&&users.size()>0){
//					return ResponseBuilder.buildFaildResponse(new ApiException(0, "手机号"+checkDomain.getTelphone()+"已被注册"));
//				} 此处不做校验，允许一个手机对应多个账号
			}else{
				if(users==null||users.size()==0){
					return ResponseBuilder.buildFaildResponse(new ApiException(0, "手机号"+checkDomain.getTelphone()+"不存在"));
				}
//				if(users.size()!=1){
//					return ResponseBuilder.buildFaildResponse(new ApiException(0, "手机号"+checkDomain.getTelphone()+"对应多个账号，请使用用户名密码登录"));
//				}此处不做校验允许
			}
			SMSUtil.sendSMS(checkDomain.getTelphone());
		}catch (ApiException e) {
			return ResponseBuilder.buildFaildResponse(e);
		}
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@POST
	@Path("/enterprisereg")
	@ApiOperation(value = "企业注册",  httpMethod = "POST", notes="企业注册")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response reg(String json) {
		EnterpriseRegDomain domain=JSON.parseObject(json,EnterpriseRegDomain.class);
		if(domain==null||domain.getUserInfo()==null||domain.getEnterpriseInfo()==null||domain.getInvoiceInfo()==null){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "数据错误"));
		}
//		if(domain.getUserInfo().getUserNo()==null||domain.getUserInfo().getUserNo().length()!=6||!StringUtils.isNumeric(domain.getUserInfo().getUserNo())){
//			return ResponseBuilder.buildFaildResponse(new ApiException(0, "用户编号输入不正确"));
//		}
		if(StringUtils.isEmpty(domain.getUserInfo().getUserNo())){
			domain.getUserInfo().setUserNo("999999");
		}
		if(domain.getUserInfo().getCertType()!=null&&domain.getUserInfo().getCertType().length()>0){
			if(DictionariesUtil.getCardType().get(domain.getUserInfo().getCertType())==null){
				return ResponseBuilder.buildFaildResponse(new ApiException(0, "证件类型无法识别"));
			}
		}
		if(domain.getUserInfo().getLoginName()==null||domain.getUserInfo().getLoginName().length()<2){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "用户名输入不正确"));
		}
		if(domain.getUserInfo().getPassword()==null||domain.getUserInfo().getPassword().length()<6){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "密码输入不正确"));
		}
		
		if(domain.getUserInfo().getTelphone()==null||domain.getUserInfo().getTelphone().length()<2){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "手机号码不正确"));
		}
		
//		List<UserDomain> users = userService.findByBeanPropName("telphone", domain.getUserInfo().getTelphone());
//		if(users!=null&&users.size()>0){
//			return ResponseBuilder.buildFaildResponse(new ApiException(0, "手机号"+domain.getUserInfo().getTelphone()+"已被注册"));
//		}
		
		if(domain.getUserInfo().getNationality()==null||domain.getUserInfo().getNationality().length()<3){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "国籍输入不正确"));
		}
		
		if(!"1".equals(domain.getUserInfo().getSex())){
			domain.getUserInfo().setSex("0");
		}
		
		if(domain.getEnterpriseInfo().getEnterpriseNo()==null||!domain.getEnterpriseInfo().getEnterpriseNo().startsWith(domain.getUserInfo().getNationality())){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "位号输入不正确"));
		}
		List<CodeDomain> codeList= codeService.findByBeanPropName("no", domain.getEnterpriseInfo().getEnterpriseNo());
		if(codeList==null||codeList.size()==0||codeList.get(0).getPrice().intValue()!=0||codeList.get(0).getState().booleanValue()==true||!"2".equals(codeList.get(0).getNoType())){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "位号输入不正确"));
		}
		if(domain.getEnterpriseInfo().getName()==null||domain.getEnterpriseInfo().getName().length()<2){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "企业名称输入不正确"));
		}
		List<EnterpriseDomain> entList = enterpriseService.findByBeanPropName("name", domain.getEnterpriseInfo().getName());
		if(entList!=null&&entList.size()>0){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, domain.getEnterpriseInfo().getName()+" 已被注册"));
		}
		if(domain.getFilesInfo()!=null&&domain.getFilesInfo().size()>0){
			for(CertFileDomain cfd:domain.getFilesInfo()){
//				if(DictionariesUtil.getCertType().get(cfd.getCertType())==null){
//					return ResponseBuilder.buildFaildResponse(new ApiException(0, "证照类型无法识别"));
//				}
				if(cfd.getCertNo()==null||cfd.getCertNo().length()<2){
					return ResponseBuilder.buildFaildResponse(new ApiException(0, "证照号码输入不正确"));
				}
			}
		}
		
		if(!"0".equals(domain.getInvoiceInfo().getInvoiceType())){
			domain.getInvoiceInfo().setInvoiceType("1");
		}
		
		if(domain.getInvoiceInfo().getEnterpriseName()==null||domain.getInvoiceInfo().getEnterpriseName().length()<2){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "企业名称输入不正确"));
		}
		
		if("1".equals(domain.getInvoiceInfo().getInvoiceType())){
			if(domain.getInvoiceInfo().getInvoiceCode()==null||domain.getInvoiceInfo().getInvoiceCode().length()<2){
				return ResponseBuilder.buildFaildResponse(new ApiException(0, "纳税识别号输入不正确"));
			}
			
			if(domain.getInvoiceInfo().getBankName()==null||domain.getInvoiceInfo().getBankName().length()<2){
				return ResponseBuilder.buildFaildResponse(new ApiException(0, "开户行输入不正确"));
			}
			
//			if(domain.getInvoiceInfo().getBancAccount()==null||domain.getInvoiceInfo().getBancAccount().length()<2){
//				return ResponseBuilder.buildFaildResponse(new ApiException(0, "银行账户输入不正确"));
//			}
		}
		if(!SMSUtil.checkCode(domain.getUserInfo().getTelphone(), domain.getUserInfo().getSmsCode(),true)){
			return ResponseBuilder.buildFaildResponse(new ApiException(0, "短信验证码不正确"));
		}
		domain.getUserInfo().setUserType("2");
		domain.getUserInfo().setUserCode(domain.getEnterpriseInfo().getEnterpriseNo()+"999999");
		userService.regEnterprise(domain);
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@POST
	@Path("/login")
	@ApiOperation(value = "用户登陆",  httpMethod = "POST", notes="用户登陆")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response login(Map<String,String> domain,@Context HttpServletRequest request) {
		return ResponseBuilder.buildSuccessResponse(((IAuthServer) SpringContextUtil.getBean("authServer")).login(domain, request));
	}
	
	@GET
	@Path("/checkToken")
	@ApiOperation(value = "校验用户是否登陆",  httpMethod = "GET", notes="校验用户是否登陆")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response checkToken(@HeaderParam("Authorization") String token) {
		return ResponseBuilder.buildSuccessResponse(((IAuthServer) SpringContextUtil.getBean("authServer")).check(token));
	}
	
	@GET
	@Path("/logout")
	@ApiOperation(value = "登出",  httpMethod = "GET", notes="登出")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response logout(@HeaderParam("Authorization") String token) {
		((IAuthServer) SpringContextUtil.getBean("authServer")).logout(token);
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@GET
	@Path("/sms")
	@ApiOperation(value = "给登陆用户发送短信",  httpMethod = "GET", notes="给登陆用户发送短信")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response sms() {
		SMSUtil.sendSMS(SecurityContextHelper.getCurrentUser().getTelphone());
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@GET
	@Path("/smsByPhone/{phone}")
	@ApiOperation(value = "给用户发送短信",  httpMethod = "GET", notes="给用户发送短信")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response smsByPhone(@PathParam("phone")String phone) {
		if(SecurityContextHelper.getCurrentUser().getTelphone().equals(phone)){
			throw new ApiException(0,"手机号码未发生变化");
		}
		SMSUtil.sendSMS(phone);
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@GET
	@Path("/smsByPhoneNoLogin/{phone}")
	@ApiOperation(value = "给用户发送短信",  httpMethod = "GET", notes="给用户发送短信")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response smsByPhoneNoLogin(@PathParam("phone")String phone) {
		List<UserDomain> users = userService.findByBeanPropName("telphone", phone);
		if(users==null||users.size()==0){
			throw new ApiException(0,"该手机号码未注册");
		}
		SMSUtil.sendSMS(phone);
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@PATCH
	@Path("/modifyPassword")
	@ApiOperation(value = "修改密码",  httpMethod = "PATCH", notes="修改密码")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response modifyPassword(Map<String,String> domain) {
		if(StringUtils.isEmpty(domain.get("password"))||domain.get("password").length()<6){
			throw new ApiException(0, "密码输入不正确");
		}
		if(!SMSUtil.checkCode(SecurityContextHelper.getCurrentUser().getTelphone(), domain.get("smsCode"), true)){
			throw new ApiException(0, "短信验证码不正确");
		}
		UserDomain user=new UserDomain();
		user.setId(SecurityContextHelper.getCurrentUserId());
		user.setPassword(SpringContextUtil.getBean(PasswordTools.class).encode(domain.get("password")));
		userService.merge(user);
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@PATCH
	@Path("/getPassword")
	@ApiOperation(value = "修改密码",  httpMethod = "PATCH", notes="修改密码")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response getPassword(Map<String,String> domain) {
		String phone=domain.get("phone");
		if(StringUtils.isEmpty(domain.get("password"))||domain.get("password").length()<6){
			throw new ApiException(0, "密码输入不正确");
		}
		if(!SMSUtil.checkCode(phone, domain.get("smsCode"), true)){
			throw new ApiException(0, "短信验证码不正确");
		}
		List<UserDomain> users = userService.findByBeanPropName("telphone", phone);
		if(users==null||users.size()==0){
			throw new ApiException(0, "该手机未注册");
		}
		//DefaultAuthServer das = SpringContextUtil.getBean(DefaultAuthServer.class);
		if(users.size()>1){
			throw new ApiException(2100,((IAuthServer) SpringContextUtil.getBean("authServer")).accountsLogin(users,domain.get("phone")));
		}
		UserDomain user=new UserDomain();
		user.setId(users.get(0).getId());
		user.setPassword(SpringContextUtil.getBean(PasswordTools.class).encode(domain.get("password")));
		userService.merge(user);
		return ResponseBuilder.buildSuccessResponse();
	}
	
	@PATCH
	@Path("/getPassword/{userId}")
	@ApiOperation(value = "修改密码",  httpMethod = "PATCH", notes="修改密码")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response getPassword_(@PathParam("userId")Long id, Map<String,String> domain) {
		if(StringUtils.isEmpty(domain.get("password"))||domain.get("password").length()<6){
			throw new ApiException(0, "密码输入不正确");
		}
		
		UserDomain userdomain = userService.findByPk(id);
		if(userdomain==null){
			throw new ApiException(0, "该手机未注册");
		}
		//DefaultAuthServer das =SpringContextUtil.getBean(DefaultAuthServer.class);
		((IAuthServer) SpringContextUtil.getBean("authServer")).checkKey(domain.get("phone"), domain.get("key"));
		UserDomain user=new UserDomain();
		user.setId(userdomain.getId());
		user.setPassword(SpringContextUtil.getBean(PasswordTools.class).encode(domain.get("password")));
		userService.merge(user);
		
		return ResponseBuilder.buildSuccessResponse();
	}
	
	
	@PATCH
	@Path("/modifyPhone")
	@ApiOperation(value = "修改手机",  httpMethod = "PATCH", notes="修改手机")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response modifyPhone(Map<String,String> domain) {
		String newPhone=domain.get("phone");
		String smsCode=domain.get("smsCode");
		String code1=domain.get("code1");
		if(SecurityContextHelper.getCurrentUser().getTelphone().equals(newPhone)){
			throw new ApiException(0,"手机号码未发生变化");
		}
		if(StringUtils.isEmpty(newPhone)||newPhone.length()<6){
			throw new ApiException(0, "新手机号码不正确");
		}
		if(!SMSUtil.checkCode(SecurityContextHelper.getCurrentUser().getTelphone(), smsCode, true)){
			throw new ApiException(0, "短信验证码不正确");
		}
		if(!SMSUtil.checkCode(newPhone, code1, true)){
			throw new ApiException(0, "短信验证码不正确");
		}
		UserDomain user=new UserDomain();
		user.setId(SecurityContextHelper.getCurrentUserId());
		user.setTelphone(newPhone);
		userService.merge(user);
		return ResponseBuilder.buildSuccessResponse();
	}

	@GET
	@Path("/getFollowed")
	@ApiOperation(value = "获取已关注数和可关注数",  httpMethod = "GET", notes="获取已关注数和可关注数")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response getFollowed(){
		//根据用户id查询出对应已关注数和可关注数
		UserDomain userDomain= userService.findByPk(SecurityContextHelper.getCurrentUserId());
		return ResponseBuilder.buildSuccessResponse(userDomain);
	}

	@DELETE
	@Path("/deleteUser")
	@ApiOperation(value = "删除用户",  httpMethod = "DELETE", notes="删除用户")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response deleteUser(@Context HttpServletRequest request){
		//根据userId将用户状态改成删除同时对应的企业也改成删除状态
		UserDomain userDomain =new UserDomain();
		userDomain.setId(Long.parseLong(request.getParameter("userId")));
		userDomain.setDeleteTag(true);
		userService.merge(userDomain);

		EnterpriseDomain enterpriseDomain = new EnterpriseDomain();
		enterpriseDomain.setId(Long.parseLong(request.getParameter("enterpriseId")));
		enterpriseDomain.setDeleteTag(true);
		enterpriseService.superMerge(enterpriseDomain);
		return ResponseBuilder.buildSuccessResponse();
	}

	@PATCH
	@Path("/pauseUser")
	@ApiOperation(value = "暂停、启用用户",  httpMethod = "PATCH", notes="暂停、启用用户")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response pauseUser(@Context HttpServletRequest request){
		EnterpriseDomain enterpriseDomain = new EnterpriseDomain();
		enterpriseDomain.setId(Long.parseLong(request.getParameter("enterpriseId")));
		enterpriseDomain.setDisable(request.getParameter("disable").equals("1")?true:false);
		enterpriseService.superMerge(enterpriseDomain);
		return ResponseBuilder.buildSuccessResponse();
	}

	@PATCH
	@Path("/replaceCode")
	@ApiOperation(value = "替换账号",  httpMethod = "PATCH", notes="替换账号")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
	public Response replaceCode(@Context HttpServletRequest request){
		//去code表里查询对应是否有值并且是否使用过,然后之前位号释放,对应修改user和enterprise表中字段
		List<CodeDomain> codeDomainList=codeService.findByBeanPropName("no",request.getParameter("replaceNo"));
		if(codeDomainList!=null && codeDomainList.size()>0){
			CodeDomain cd=codeDomainList.get(0);
			if(cd.getState()){
				throw new ApiException(0,"该位号已经被使用,请重新输入");
			}else{
				List<CodeDomain> codeDomains=codeService.findByBeanPropName("no",request.getParameter("enterpriseNo"));
				CodeDomain codeDomain=codeDomains.get(0);
				cd.setEnterpriseId(codeDomain.getEnterpriseId());
				codeDomain.setEnterpriseId(0L);
				codeDomain.setState(false);

				cd.setState(true);
				codeService.merge(codeDomain);
				codeService.merge(cd);

				UserDomain userDomain=userService.findByPk(Long.valueOf(request.getParameter("userId")));
				userDomain.setUserCode(userDomain.getUserCode().replace(codeDomain.getNo(),cd.getNo()));
				EnterpriseDomain enterpriseDomain=enterpriseService.findByPk(Long.valueOf(request.getParameter("enterpriseId")));
				enterpriseDomain.setEnterpriseNo(cd.getNo());

				userService.merge(userDomain);
				enterpriseService.superMerge(enterpriseDomain);
			}
		}else{
			throw new ApiException(0,"该位号不存在,请重新输入");
		}
		return ResponseBuilder.buildSuccessResponse();
	}
}
