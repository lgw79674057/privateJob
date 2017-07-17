$(function() {
	_pageLoad();
	function _pageLoad() {
		$.ajax({
			url : $.LOCAL_CONTEXT_PATH + "top.html",
			type : "GET",
			dataType : "html",
			success : function(html) {
				html = html.replace(/\{LOCAL_CONTEXT_PATH\}/g, $.LOCAL_CONTEXT_PATH);
				$(document.body).prepend(html);
				_pageInit();
				var menu=$("li[menuid='"+$(document.body).attr("menu_id")+"']");
				menu.addClass("zx-active");
				if("2"==menu.attr("level")){
					menu.parent().collapse('open');
				}
				$("#userSetting").dropdown()
			}
		});
		$.ajax({
			url : $.LOCAL_CONTEXT_PATH + "footer.html",
			type : "GET",
			dataType : "html",
			success : function(html) {
				html = html.replace(/\{LOCAL_CONTEXT_PATH\}/g, $.LOCAL_CONTEXT_PATH);
				$("#_xuaxi_main_").append(html);
			}
		});
	}
	function _pageInit() {
		var body = $("#zx-body");
		var left = $("#zx-body-left");
		$("#zx-header-fullscreen").click(function() {
			$.AMUI.fullscreen.toggle();
		})
		$("#zx-header-menu").click(function() {
			if (left.position().left == 0) {
				left.css("left", "-240px")
				body.css("padding-left", "0px");
			} else {
				left.css("left", "0px");
				if ($(window).width() >= 1000) {
					body.css("padding-left", "240px");
				}
				left.css("top",$(document).scrollTop());
				s=$(document).scrollTop();
			}
		});
		$(window).resize(function() {
			if (left.attr("style")) {
				left.removeAttr("style");
				body.removeAttr("style");
			}
		});
		
		
		var user=$.session.getCurrentUser();
		if(user){
			if(user.userType=="A"){
				window.location.href=$.LOCAL_CONTEXT_PATH+"audit.html";
			}
			$("#_zx_fullName").html(user.loginName);
			$("#enterPrise_Name").html(user.enterpriseName);
			$("#enterPrise_No").html(user.enterpriseNo);
			$("#user_No").html(user.userNo);
			if(user.audit==true||user.audit=='true'){
				if(user.disable==true||user.disable=='true'){
					$("#enterPrise_Status").css("color","red");
					$("#enterPrise_Status").html("审核未通过");
				}else{
					$("#enterPrise_Status").css("color","green");
					$("#enterPrise_Status").html("审核通过");
				}
			}else{
				$("#enterPrise_Status").css("color","red");
				$("#enterPrise_Status").html("审核中");
			}
			if(user.auditRemark){
				$('#enterPrise_Status').popover({
				    content: user.auditRemark,
				    trigger:"click",
				    theme:"primary"
				})
			}
		}else{
			window.location.href=$.LOCAL_CONTEXT_PATH+"index.html";
		}
		var s=$(document).scrollTop();
		left.css("top",s);
		$(document).scroll(function() {
			if (left.position().left == 0) {
				if($(document).scrollTop()<s){
					left.css("top",$(document).scrollTop());
				}
				s=$(document).scrollTop();
			}
		});
	}
});