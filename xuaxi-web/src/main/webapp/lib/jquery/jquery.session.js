(function($) {
	$.session = {
		setCurrentUser : function(user){
			localStorage.setItem("_CURRENTUSER",JSON.stringify(user));
		},
		getCurrentUser : function(){
			var u = localStorage.getItem("_CURRENTUSER");
			if(u){
				try{
					return JSON.parse(u);
				}catch(e){
					return null;
				}
			}
			return null;
		},

		getAuthToken : function(){
			var user = this.getCurrentUser();
			if(user == null){
				return null;
			}else{
				return user.Authorization;
			}
		},
		
		login:function(param,fn,errfn){
			var $loading = AMUI.dialog.loading({
				title : "正在登录"
			});
			$.RESTFUL.POST({
				url : $.CONTEXT_PATH + "api/v1/user/login",
				data :param,
				success : function(d) {
					if (d.status == true) {
						$.session.setCurrentUser(d.result);
						AMUI.dialog.success({
							title : '系统提示',
							content : '登录成功',
							onConfirm : function() {
								if(typeof(fn)=='function'){
									fn();
								}else{
									var h=sessionStorage.getItem("_dsendRedirect_");
									window.location.href=h?h:'main.html';
								}
							}
						});
						return;
					}
					if(typeof(errfn)=='function'){
						errfn(d);
					}else{
						AMUI.dialog.error({
							title : '系统提示',
							content : d.error.message
						});
					}
				},
				error : function(response) {
					if(typeof(errfn)=='function'){
						errfn($.responseToJson(response));
					}else{
						AMUI.dialog.error({
							title : '系统开小差了',
							content : $.responseToJson(response).error.message
						});
					}
				},
				complete : function() {
					$loading.modal('close');
				}
			});
		},
		
		logout:function(fn){
			$.RESTFUL.GET({
				url:$.CONTEXT_PATH + "api/v1/user/logout",
				success:function(data){
					localStorage.removeItem("_CURRENTUSER");
					sessionStorage.clear();
					if(typeof(fn)=='function'){
						fn();
					}else{
						window.location.href=$.CONTEXT_PATH+"login.html?noAuto=true";
					}
				}
			});
		},
		checkSession:function(fn){
			$.RESTFUL.GET({
				url : $.CONTEXT_PATH + "api/v1/user/checkToken",
				success : function(d) {
					if (d.status == true&&d.result == true) {
						if(typeof(fn)=='function'){
							fn(true);
						}else{
							AMUI.dialog.confirm({
								title : '系统提示',
								content : '您当前已经登录了账户 '+$.session.getCurrentUser().loginName+' 是否进入首页？',
								onConfirm : function() {
									window.location.href='main.html';
								}
							});
						}
					}else{
						if(typeof(fn)=='function'){
							fn(false);
						}
					}
				}
			});
		}
	};
})(jQuery);