(function($) {
	var ajax = $.ajax;
	$.ajax = function(param) {
		
		var success = param.success;
		
		var error = param.error;
		
		if(!success){
			success=function(){}
		}
		
		if(!error){
			error=function(){}
		}
		
		param.success = function(arg, arg1, arg2) {
			success(arg, arg1, arg2);
		}

		param.error = function(arg, arg1, arg2) {
			if(arg.status==401||arg.status==403){
				try{
					var response=JSON.parse(arg.responseText);
					if(response.error.code==401){
						AMUI.dialog.error({
							title : '没有权限',
							content : response.error.message,
							onConfirm:function(){
								if(response.error.referer&&sessionStorage){
									sessionStorage.setItem("_dsendRedirect_", response.error.referer);
								}
								window.location.href=$.CONTEXT_PATH+"login.html";
							}
						});
						return;
					}
					if(response.error.code==403){
						AMUI.dialog.error({
							title : '没有权限',
							content : response.error.message
						});
						return;
					}
				}catch(e){
					error(arg, arg1, arg2);
				}
			}
			error(arg, arg1, arg2);
		}
		
		if (!param.headers) {
			param.headers = {};
		}
		
		param.headers.Authorization = $.session.getAuthToken();// 注入token
		
		ajax(param);
	}
})(jQuery);