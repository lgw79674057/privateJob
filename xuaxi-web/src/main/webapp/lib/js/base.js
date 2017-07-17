 var loginKey;
 var selectUserId;
(function($) {
	
	$.CONTEXT_PATH="/";  // contectPaht
	
	$.LOCAL_CONTEXT_PATH="/";
	
	$.requestParam=function(paramName){ // 从url取参数
		paramValue = "", isFound = !1;  
	    if (window.location.search.indexOf("?") == 0 && window.location.search.indexOf("=") > 1) {  
	        arrSource = unescape(window.location.search).substring(1, window.location.search.length).split("&"), i = 0;  
	        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++  
	    }  
	    return paramValue == "" && (paramValue = null), paramValue  
	}
	
	$.responseToJson=function(response){ // 格式化responseText
		try{
			return JSON.parse(response.responseText);
		}catch(e){
			return {status:false,error:{code:500,message:"系统异常请稍后再试",responseCode:500}};
		}
	}
	
	var template=
		'<div><div class="swiper-container" id="_view_"> '+
	'<div class="swiper-wrapper" style="width: 100%;">'+
	'<div class="swiper-slide">'+
	'	<!--<fieldset>'+
	'		<legend>单位开票信息</legend>-->'+
	'		<div class="am-input-group zx-input-group">'+
	'			<span class="am-input-group-label" style="width:1px;">纳</span><span class="am-input-group-label" style="width:1px;">税</span><span class="am-input-group-label" style="width:1px;">人:</span><div'+
	'				class="zx-form-field">{{invoice.invoiceType}}</div>'+
	'		</div>'+
	'		<div class="am-input-group zx-input-group">'+
	'			<span class="am-input-group-label" style="width:63px;width: 75px;text-align:left;">名</span><span class="am-input-group-label" style="text-align: left;width: 1px;">称:</span><div'+
	'				class="zx-form-field">{{invoice.enterpriseName}}</div>'+
	'		</div>'+
	'		<div class="am-input-group zx-input-group">'+
	'			<span class="am-input-group-label" style="width:126px">纳税人识别号:</span><div'+
	'				class="zx-form-field" style="padding-left:10px;">{{invoice.invoiceCode}}</div>'+
	'		</div>'+
	'		<div class="am-input-group zx-input-group" ns>'+
	'			<span class="am-input-group-label" style="width:126px">地址、电话:</span><div'+
	'				class="zx-form-field">{{invoice.address}}</div>'+
	'		</div>'+
	// '		<div class="am-input-group zx-input-group" ns>'+
	// '			<span class="am-input-group-label">联系电话</span><div'+
	// '				class="zx-form-field">{{invoice.phone}}</div>'+
	// '		</div>'+
	'		<div class="am-input-group zx-input-group" ns>'+
	'			<span class="am-input-group-label" style="width:126px">开户行及账号:</span><div'+
	'				class="zx-form-field">{{invoice.bankName}}</div>'+
	'		</div>'+
	// '<!--		<div class="am-input-group zx-input-group" ns>'+
	// '			<span class="am-input-group-label">支行</span><div'+
	// '				class="zx-form-field">{{invoice.detilBack}}</div>'+
	// '		</div>-->'+
	// '		<div class="am-input-group zx-input-group" ns>'+
	// '			<span class="am-input-group-label">银行账户</span><div'+
	// '				class="zx-form-field">{{invoice.bancAccount}}</div>'+
	// '		</div>'+
	'<!--		{{#if invoice.filePath}}'+
	'		<div class="am-input-group zx-input-group">'+
	'			<span class="am-input-group-label">发票附件</span>'+
	'			 <figure data-am-widget="figure" class="am am-figure am-figure-default"  data-am-figure="{pureview: \'true\'}">'+
	'				<img src="{{#contextPath}}{{/contextPath}}{{invoice.filePath}}" style="border-bottom: 1px solid #ddd;width: 100%;border-radius: 8px;"/>'+
	'			</figure>'+
	'		</div>'+
	'		{{/if}}-->'+
	'	<!--</fieldset>	-->'+
	'</div>'+
	'<!--<div class="swiper-slide">'+
		'<fieldset >'+
			'<legend style="margin-bottom: 0px;">单位基础信息</legend>'+
			'<div class="am-input-group zx-input-group">'+
				'<span class="am-input-group-label">位号</span><div'+
				'	class="zx-form-field">{{enterprise.enterpriseNo}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">单位名称</span><div'+
			'		class="zx-form-field">{{enterprise.name}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">英文名称</span><div'+
			'		class="zx-form-field">{{enterprise.enName}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">单位简称</span><div'+
			'		class="zx-form-field">{{enterprise.abbreviation}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">英文简称</span><div'+
			'		class="zx-form-field">{{enterprise.enAbbreviation}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">营业范围</span><div'+
			'		class="zx-form-field">{{enterprise.businessScope}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">地址</span><div'+
			'		class="zx-form-field">{{enterprise.fullAddress1}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">英文地址</span><div'+
			'		class="zx-form-field">{{enterprise.enAddress1}}</div>'+
			'</div>'+
			'{{#show enterprise.address2}}<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">地址</span><div'+
			'		class="zx-form-field">{{enterprise.fullAddress2}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">英文地址</span><div'+
			'		class="zx-form-field">{{enterprise.enAddress2}}</div>'+
			'</div>{{/show}}'+
			'{{#show enterprise.address3}}<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">地址</span><div'+
			'		class="zx-form-field">{{enterprise.fullAddress3}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">英文地址</span><div'+
			'		class="zx-form-field">{{enterprise.enAddress3}}</div>'+
			'</div>{{/show}}'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">单位电话</span><div'+
			'		class="zx-form-field">{{enterprise.phone}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">传真</span><div'+
			'		class="zx-form-field">{{enterprise.fax}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">联系人</span><div'+
			'		class="zx-form-field">{{enterprise.contacts}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">联系电话</span><div'+
			'		class="zx-form-field">{{enterprise.cPhone}}</div>'+
			'</div>'+
			'<div class="am-input-group zx-input-group">'+
			'	<span class="am-input-group-label">单位法人</span><div'+
			'		class="zx-form-field">{{enterprise.legalPerson}}</div>'+
			'</div>'+
		'</fieldset>'+
	'</div>-->'+
	'<!--<div class="swiper-slide">'+
	'	<fieldset>'+
	'		<legend style="margin-bottom: 10px;">单位证照信息</legend>'+
	'		{{#each enterpriseFiles}}'+
	'			<fieldset style="margin-bottom: 0px;margin-top: 0px;">'+
	'				<legend style="margin-bottom: 0px;">{{certType}}</legend>'+
	'				<div class="am-input-group zx-input-group">'+
	'					<span class="am-input-group-label">证照号码</span><div'+
	'						class="zx-form-field">{{certNo}}</div>'+
	'				</div>'+
	'				{{#if filePath}}'+
	'				<div class="am-input-group zx-input-group">'+
	'					<span class="am-input-group-label">证照照片</span>'+
	'					 <figure data-am-widget="figure" class="am am-figure am-figure-default"  data-am-figure="{pureview: \'true\'}">'+
	'						<img src="{{#contextPath}}{{/contextPath}}{{filePath}}" style="border-bottom: 1px solid #ddd;width: 100%;border-radius: 8px;"/>'+
	'					</figure>'+
	'				</div>'+
	'				{{/if}}'+
	'			</fieldset>'+
	'		{{/each}}'+
	'	</fieldset>'+
	'</div>-->'+
'</div>'+
'<!--<div class="swiper-pagination"></div><div class="swiper-button-next" style="color:#848c90;top: 200px;"></div><div class="swiper-button-prev" style="color:#848c90;top: 200px;"></div>--></div>'+
'<div  style="text-align: center;"><button class="am-btn am-btn-sm" style="border-radius: 4px;" onclick="$(this).parent().parent().parent().parent().find(\'.am-close\').trigger(\'click\')">返回</button></div></div>';
	
	$.view=function(id,code,flg){
		if(code!=0&&code!='0'&&code.length!=6){
			AMUI.dialog.error({
				title : '系统提示',
				content : "查询码输错误"
			});
			return;
		}
		var $loading = AMUI.dialog.loading({
			title : "正在查询"
		});
		$.RESTFUL.GET({
			url : $.CONTEXT_PATH + "api/v1/enterprise/info/"+id+"/"+(code?code:0),
			success : function(d) {
				$loading.modal('close');
				if (d.status == true) {
					Handlebars.registerHelper("contextPath", function(options) {
						return $.CONTEXT_PATH;
					});
					if(true==flg){
						template = template.replace(/\<!--/g, '').replace(/\-->/g, '');
					}
					Handlebars.registerHelper('show', function(obj, options){
				          if(obj.length>0&&obj!=' '){
				        	  return options.fn(this);
				          }else{
				        	  return options.inverse(this);
				          }
				     });
					AMUI.dialog.popup({
						title:"单位开票信息",
						content:Handlebars.compile(template)(d.result),
					});
					if(true==flg){
						$('#_view_').swiper({pagination: '#_view_ .swiper-pagination',
						    effect: 'cube',
						    grabCursor: true,
						    nextButton: '#_view_ .swiper-button-next',
						    prevButton: '#_view_ .swiper-button-prev',
						    cube: {
						      shadow: true,
						      slideShadows: true,
						      shadowOffset: 20,
						      shadowScale: 0.94
						    }
						});
					}
					$.AMUI.figure.init();
					if(d.result.invoice.invoiceType=='非一般纳税人'){
						$("#_view_ div[ns]").hide();
					}
					
					return;
				}
				AMUI.dialog.error({
					title : '系统提示',
					content : d.error.message
				});
			},
			error : function(response) {
				$loading.modal('close');
				AMUI.dialog.error({
					title : '系统开小差了',
					content : $.responseToJson(response).error.message
				});
			}
		});
	};
	
	$.modifyPassword=function(){
		var telphone=$.session.getCurrentUser().telphone;
		telphone=telphone.substring(0,3)+"****"+telphone.substring(7);
		 var html = [];
		  html.push('<div class="am-modal am-modal-prompt" tabindex="-1">');
		  html.push('<div class="am-modal-dialog">');
		  html.push('<div class="am-modal-hd">密码修改</div>');
		  html.push('<div class="am-modal-bd">');
		  html.push('<input type="text" placeholder="手机号码" readonly class="am-modal-prompt-input" value="'+telphone+'">');
		  html.push('<input type="text" placeholder="请输入短信验证码(6位数字)" id="_sms_modify_pass_code_" class="am-modal-prompt-input">');
		  html.push('<button type="button" class="am-btn am-topbar-btn am-btn-sm" style="position: absolute;width:30%;margin-top:-35px;right:auto;margin-left:8.5%;border-bottom: 4px solid #dedede;" id="_sms_modify_pass_">获取</button>');
		  html.push('<input type="password" id="_sms_modify_pass_newpass" placeholder="请输入新密码(6-12位数字字母符号组合)" class="am-modal-prompt-input">');
		  html.push('<input type="password" id="_sms_modify_pass_newpass1" placeholder="请再次输入新密码" class="am-modal-prompt-input">');
		  html.push('</div><div class="am-modal-footer">');
		  html.push('<span class="am-modal-btn" data-am-modal-cancel>取消</span>');
		  html.push('<span class="am-modal-btn1" id="_btn_modify_pass_">确定</span>');
		  html.push('</div>');
		  html.push('</div>');
		  html.push('</div>');
		  var htmlObj=$(html.join('')).appendTo('body')
		  htmlObj.modal().on('closed.modal.amui', function(e) {
			  $(this).remove();
		  });
		  
		  $("#_btn_modify_pass_").click(function(){
			  var code=$("#_sms_modify_pass_code_").val();
			  var password=$("#_sms_modify_pass_newpass").val();
			  var password1=$("#_sms_modify_pass_newpass1").val();
			  if(code.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "短信验证码长度不正确"
				  });
				  return;
			  }
			  if(password.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "密码长度不正确"
				  });
				  return;
			  }
			  if(password!=password1){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "两次输入密码不一致"
				  });
				  return;
			  }
			  
			  
			  var $loading = AMUI.dialog.loading({
					title : "正在提交"
				});
			  $.RESTFUL.PATCH({
					url : $.CONTEXT_PATH + "api/v1/user/modifyPassword",
					data:'{"smsCode":"'+code+'","password":"'+password+'"}',
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '修改成功',
								onConfirm:function(){
									 htmlObj.modal("close");
								}
							});
							return;
						}
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					},
					error : function(response) {
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
		  
		  $("#_sms_modify_pass_").click(function(){
			  var time;
			  var timeSend;
			  var $loading = AMUI.dialog.loading({
					title : "正在发送"
				});
			  $.RESTFUL.GET({
					url : $.CONTEXT_PATH + "api/v1/user/sms",
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '发送成功'
							});
							timeSend = 60;
							var btn = $("#_sms_modify_pass_");
							btn.attr("disabled", "disabled")
							time = setInterval(function() {
								if (timeSend <= 0) {
									btn.removeAttr("disabled");
									clearInterval(time);
									btn.text("获取");
								} else {
									btn.text(timeSend);
									timeSend--;
								}
							}, 1000);
							return;
						}
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					},
					error : function(response) {
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
	},
	
	
	$.modifyPhone=function(){
		var telphone=$.session.getCurrentUser().telphone;
		telphone=telphone.substring(0,3)+"****"+telphone.substring(7);
		 var html = [];
		  html.push('<div class="am-modal am-modal-prompt" tabindex="-1">');
		  html.push('<div class="am-modal-dialog">');
		  html.push('<div class="am-modal-hd">绑定手机更换</div>');
		  html.push('<div class="am-modal-bd">');
		  html.push('<input type="text" placeholder="手机号码" readonly class="am-modal-prompt-input" value="'+telphone+'">');
		  html.push('<input type="text" placeholder="请输入短信验证码(6位数字)" id="_sms_modify_phone_code_" class="am-modal-prompt-input">');
		  html.push('<button type="button" class="am-btn am-topbar-btn am-btn-sm" style="position: absolute;width:30%;margin-top:-35px;right:auto;margin-left:8.5%;border-bottom: 4px solid #dedede;" id="_sms_modify_phone_">获取</button>');
		  html.push('<input type="text" id="_sms_modify_phone_newPhone" placeholder="请输入新手机号码" class="am-modal-prompt-input">');
		  html.push('<input type="text" placeholder="请输入新手机收到的短信验证码(6位数字)" id="_sms_modify_phone_code_1" class="am-modal-prompt-input">');
		  html.push('<button type="button" class="am-btn am-topbar-btn am-btn-sm" style="position: absolute;width:30%;margin-top:-35px;right:auto;margin-left:8.5%;border-bottom: 4px solid #dedede;" id="_sms_modify_phone_1">获取</button>');
		  html.push('</div><div class="am-modal-footer">');
		  html.push('<span class="am-modal-btn" data-am-modal-cancel>取消</span>');
		  html.push('<span class="am-modal-btn1" id="_btn_modify_pass_">确定</span>');
		  html.push('</div>');
		  html.push('</div>');
		  html.push('</div>');
		  var htmlObj=$(html.join('')).appendTo('body')
		  htmlObj.modal().on('closed.modal.amui', function(e) {
			  $(this).remove();
		  });
		  
		  $("#_btn_modify_pass_").click(function(){
			  var code=$("#_sms_modify_phone_code_").val();
			  var code1=$("#_sms_modify_phone_code_1").val();
			  var phone=$("#_sms_modify_phone_newPhone").val();
			  if(code.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "短信验证码长度不正确"
				  });
				  return;
			  }
			  if(code1.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "短信验证码长度不正确"
				  });
				  return;
			  }
			  if(phone.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "手机号码不正确"
				  });
				  return;
			  }
			  
			  
			  var $loading = AMUI.dialog.loading({
					title : "正在提交"
				});
			  $.RESTFUL.PATCH({
					url : $.CONTEXT_PATH + "api/v1/user/modifyPhone",
					data:'{"smsCode":"'+code+'","phone":"'+phone+'","code1":"'+code1+'"}',
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '修改成功',
								onConfirm:function(){
									 htmlObj.modal("close");
								}
							});
							return;
						}
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					},
					error : function(response) {
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
		  
		  
		  
		  $("#_sms_modify_phone_").click(function(){
			  var time;
			  var timeSend;
			  var $loading = AMUI.dialog.loading({
					title : "正在发送"
				});
			  $.RESTFUL.GET({
					url : $.CONTEXT_PATH + "api/v1/user/sms",
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '发送成功'
							});
							timeSend = 60;
							var btn = $("#_sms_modify_phone_");
							btn.attr("disabled", "disabled")
							time = setInterval(function() {
								if (timeSend <= 0) {
									btn.removeAttr("disabled");
									clearInterval(time);
									btn.text("获取");
								} else {
									btn.text(timeSend);
									timeSend--;
								}
							}, 1000);
							return;
						}
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					},
					error : function(response) {
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
		  
		  
		  $("#_sms_modify_phone_1").click(function(){
			  var phone=$("#_sms_modify_phone_newPhone").val();
			  if(phone.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "手机号码不正确"
				  });
				  return;
			  }
			  var time;
			  var timeSend;
			  var $loading = AMUI.dialog.loading({
					title : "正在发送"
				});
			  $.RESTFUL.GET({
					url : $.CONTEXT_PATH + "api/v1/user/smsByPhone/"+phone,
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '发送成功'
							});
							timeSend = 60;
							var btn = $("#_sms_modify_phone_1");
							btn.attr("disabled", "disabled")
							time = setInterval(function() {
								if (timeSend <= 0) {
									btn.removeAttr("disabled");
									clearInterval(time);
									btn.text("获取");
								} else {
									btn.text(timeSend);
									timeSend--;
								}
							}, 1000);
							return;
						}
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					},
					error : function(response) {
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
	},
	$.getPassword=function(){
		 var html = [];
		  html.push('<div class="am-modal am-modal-prompt" tabindex="-1">');
		  html.push('<div class="am-modal-dialog">');
		  html.push('<div class="am-modal-hd">密码修改</div>');
		  html.push('<div class="am-modal-bd">');
		  html.push('<input type="text" placeholder="请输入手机号码" id="youPhone_" class="am-modal-prompt-input">');
		  html.push('<input type="text" placeholder="请输入短信验证码(6位数字)" id="_sms_modify_pass_code_" class="am-modal-prompt-input">');
		  html.push('<button type="button" class="am-btn am-topbar-btn am-btn-sm" style="position: absolute;width:30%;margin-top:-35px;right:auto;margin-left:8.5%;border-bottom: 4px solid #dedede;" id="_sms_modify_pass_">获取</button>');
		  html.push('<input type="password" id="_sms_modify_pass_newpass" placeholder="请输入新密码(6-12位数字字母符号组合)" class="am-modal-prompt-input">');
		  html.push('<input type="password" id="_sms_modify_pass_newpass1" placeholder="请再次输入新密码" class="am-modal-prompt-input">');
		  html.push('</div><div class="am-modal-footer">');
		  html.push('<span class="am-modal-btn" data-am-modal-cancel>取消</span>');
		  html.push('<span class="am-modal-btn1" id="_btn_modify_pass_">确定</span>');
		  html.push('</div>');
		  html.push('</div>');
		  html.push('</div>');
		  var htmlObj=$(html.join('')).appendTo('body')
		  htmlObj.modal().on('closed.modal.amui', function(e) {
			  $(this).remove();
		  });
		  var password;
		  var phone;
		  $("#_btn_modify_pass_").click(function(){
			  var code=$("#_sms_modify_pass_code_").val();
			  password=$("#_sms_modify_pass_newpass").val();
			  var password1=$("#_sms_modify_pass_newpass1").val();
			  if(code.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "短信验证码长度不正确"
				  });
				  return;
			  }
			  if(password.length<6){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "密码长度不正确"
				  });
				  return;
			  }
			  if(password!=password1){
				  AMUI.dialog.error({
						title : "系统提示",
						content : "两次输入密码不一致"
				  });
				  return;
			  }
			  
			  phone=$("#youPhone_").val();
			  var $loading = AMUI.dialog.loading({
					title : "正在提交"
				});
			  $.RESTFUL.PATCH({
					url : $.CONTEXT_PATH + "api/v1/user/getPassword",
					data:'{"smsCode":"'+code+'","password":"'+password+'","phone":"'+phone+'"}',
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '修改成功',
								onConfirm:function(){
									 htmlObj.modal("close");
								}
							});
							return;
						}
						accountSelect(d);
					},
					error : function(response) {
						accountSelect($.responseToJson(response))
						
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
		 
		  function accountSelect(err){
			  if(err.error.code==2100){
				  htmlObj.modal("close");
				  AMUI.dialog.alert({
						title:"请选择您要修改的账户",
						content:'<table class="am-table am-table-hover am-table-striped" id="accountSelectTable"><thead><tr><th></th><th style="min-width: 70px;">位号</th><th style="min-width: 70px;">账号</th><th style="min-width: 70px;">用户名</th></tr></thead></table>',
						onConfirm:function(){
							var $loading = AMUI.dialog.loading({
								title : "正在提交"
							});
						  $.RESTFUL.PATCH({
								url : $.CONTEXT_PATH + "api/v1/user/getPassword/"+selectUserId,
								data:'{"password":"'+password+'","phone":"'+phone+'","key":"'+loginKey+'"}',
								success : function(d) {
									if (d.status == true) {
										AMUI.dialog.success({
											title : '系统提示',
											content : '修改成功',
											onConfirm:function(){
												 //htmlObj.modal("close");
											}
										});
										return;
									}
									 AMUI.dialog.error({
										title : '系统开小差了',
										content : d.error.message
									});
								},
								error : function(response) {
									 AMUI.dialog.error({
										title : '系统开小差了',
										content : $.responseToJson(response).error.message
									});
								},
								complete : function() {
									$loading.modal('close');
								}
							});
						}
					});
					var users=err.error.errorTargetData.users;
					var html=[];
					for(var i=0;i<users.length;i++){
						html.push('<tr onclick="$(this).find(\'input\').uCheck(\'check\');selectUserId=\''+users[i].id+'\';"><td><label class="am-radio am-warning"><input '+(i==0?"checked":"")+' type="radio" name="radio_"></label></td><td>'+users[i].userCode.substring(0,16)+'</td><td>'+users[i].userNo+'</td><td>'+users[i].loginName+'</td></tr>')
					}
					selectUserId=users[0].id;
					$("#accountSelectTable").append(html.join(''));
					loginKey=err.error.errorTargetData.key;
				  return;
			  }
			  AMUI.dialog.error({
					title : '系统开小差了',
					content : err.error.message
				});
		  }
		  
		  $("#_sms_modify_pass_").click(function(){
			  var phone=$("#youPhone_").val();
			  if(phone.length<6){
				  AMUI.dialog.error({
						title : '系统提示',
						content : "手机号码不正确"
					});
				  return;
			  }
			  var time;
			  var timeSend;
			  var $loading = AMUI.dialog.loading({
					title : "正在发送"
				});
			  $.RESTFUL.GET({
					url : $.CONTEXT_PATH + "api/v1/user/smsByPhoneNoLogin/"+phone,
					success : function(d) {
						if (d.status == true) {
							AMUI.dialog.success({
								title : '系统提示',
								content : '发送成功'
							});
							timeSend = 60;
							var btn = $("#_sms_modify_pass_");
							btn.attr("disabled", "disabled")
							time = setInterval(function() {
								if (timeSend <= 0) {
									btn.removeAttr("disabled");
									clearInterval(time);
									btn.text("获取");
								} else {
									btn.text(timeSend);
									timeSend--;
								}
							}, 1000);
							return;
						}
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					},
					error : function(response) {
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					},
					complete : function() {
						$loading.modal('close');
					}
				});
		  });
	}
	
	$.address=function(o){
		var html = [];
		html.push('<div class="am-modal am-modal-prompt" tabindex="-1">');
		html.push('<div class="am-modal-dialog">');
		html.push('<div class="am-modal-hd">行政区划</div>');
		html.push('<div class="am-modal-bd">');
		html.push('省<select class="am-modal-prompt-input" id="address_level1"></select>');
		html.push('市<select class="am-modal-prompt-input" id="address_level2"></select>');
		html.push('县/区<select class="am-modal-prompt-input" id="address_level3"></select>');
		html.push('街道<select class="am-modal-prompt-input" id="address_level4"></select>');
		html.push('社区<select class="am-modal-prompt-input" id="address_level5"></select>');
		html.push('</div><div class="am-modal-footer">');
		html.push('<span class="am-modal-btn" data-am-modal-cancel>取消</span>');
		html.push('<span class="am-modal-btn1" id="address-data-am-modal-confirm">确定</span>');
		html.push('</div>');
		html.push('</div>');
		html.push('</div>');
		var modal_=$(html.join('')).appendTo('body');
		modal_.modal().on('closed.modal.amui', function(e) {
			$(this).remove();
		});
		var $target=$(o.target);
		var $address=$(o.address);
		$("#address-data-am-modal-confirm").click(function(){
			if(!$("#address_level5").val()){
				AMUI.dialog.error({
					title : '系统提示',
					content : "请选择地址"
				});
				return;
			}
			$target.val($("#address_level5").val());
			$address.val($("#address_level1 option:selected").text()+$("#address_level2 option:selected").text()+$("#address_level3 option:selected").text()+$("#address_level4 option:selected").text()+$("#address_level5 option:selected").text())
			modal_.modal("close");
		});
		
		var $loading = AMUI.dialog.loading({
			title : "正在获取"
		});
		$.RESTFUL.GET({
			url:$.CONTEXT_PATH + "api/v1/address/"+($target.val()?$target.val():"-1"),
			success : function(d) {
				if (d.status == true) {
					ShowAddress(d.result);
					return;
				}
				AMUI.dialog.error({
					title : '系统提示',
					content : d.error.message
				});
			},
			error : function(response) {
				AMUI.dialog.error({
					title : '系统开小差了',
					content : $.responseToJson(response).error.message
				});
			},
			complete : function() {
				$loading.modal('close');
			}
		});
		
		
		function ShowAddress(data){
			$("#address_level1").html(getOptionHtml(data.level1));
			$("#address_level2").html(getOptionHtml(data.level2));
			$("#address_level3").html(getOptionHtml(data.level3));
			$("#address_level4").html(getOptionHtml(data.level4));
			$("#address_level5").html(getOptionHtml(data.level5));
			linsenEvent();
		}
		
		function getOptionHtml(arr){
			var html=[];
			for(var i=0;i<arr.length;i++){
				html.push("<option value='"+arr[i].id+"' "+(arr[i].selected==true?"selected":"")+">"+arr[i].name+"</option>")
			}
			return html.join('');
		}
		
		function loadByParentId(pid,fn){
			var $loading = AMUI.dialog.loading({
				title : "正在获取"
			});
			$.RESTFUL.GET({
				url:$.CONTEXT_PATH + "api/v1/address/"+pid+"/byParent",
				success : function(d) {
					if (d.status == true) {
						fn(d.result);
						return;
					}
					AMUI.dialog.error({
						title : '系统提示',
						content : d.error.message
					});
				},
				error : function(response) {
					AMUI.dialog.error({
						title : '系统开小差了',
						content : $.responseToJson(response).error.message
					});
				},
				complete : function() {
					$loading.modal('close');
				}
			});
			
		}
		
		function linsenEvent(){
			$("#address_level1").change(function(){
				loadByParentId($("#address_level1").val(),function(arr){
					$("#address_level2").html("<option value=''>--请选择--</option>"+getOptionHtml(arr));
					$("#address_level3").html('');
					$("#address_level4").html('');
					$("#address_level5").html('');
				})
			});
			$("#address_level2").change(function(){
				loadByParentId($("#address_level2").val(),function(arr){
					$("#address_level3").html("<option value=''>--请选择--</option>"+getOptionHtml(arr));
					$("#address_level4").html('');
					$("#address_level5").html('');
				})
			});
			$("#address_level3").change(function(){
				loadByParentId($("#address_level3").val(),function(arr){
					$("#address_level4").html("<option value=''>--请选择--</option>"+getOptionHtml(arr));
					$("#address_level5").html('');
				})
			});
			$("#address_level4").change(function(){
				loadByParentId($("#address_level4").val(),function(arr){
					$("#address_level5").html("<option value=''>--请选择--</option>"+getOptionHtml(arr));
				})
			});
		}
	}
	
	
})(jQuery);