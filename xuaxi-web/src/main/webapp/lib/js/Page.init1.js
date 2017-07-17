$(function() {
	_pageLoad();
	function _pageLoad() {
		$.ajax({
			url : $.LOCAL_CONTEXT_PATH + "top1.html",
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
			if(user.userType!="A"){
				window.location.href=$.LOCAL_CONTEXT_PATH+"index.html";
			}
			$("#_zx_fullName").html(user.loginName);
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