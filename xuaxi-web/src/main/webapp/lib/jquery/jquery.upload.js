(function($) {
	$._uploadTempFn=function(){}
	$.Upload = {
		imgUpload:function(param){
			$._uploadTempFn=param.callBack;
			if($("#fileUploadDiv_").length==0){
				var html="<div id='fileUploadDiv_' style='display:none'><form method='post' action='"+param.url+"' enctype='multipart/form-data' target='fileUploadIframe_'>"+
				"<input type='file' name='file' onchange='$.Upload.startUpload()'/>"
				+"</form><iframe id='fileUploadIframe_' name='fileUploadIframe_' style='height: 0px;width: 0px;'></iframe></div>";
				$(document.body).append(html);
			}
			$("#fileUploadDiv_ input[type='file']").trigger("click");
		},
		startUpload:function(){
			var $loading = AMUI.dialog.loading({
				title : "正在上传"
			});
			
			$("#fileUploadIframe_").on("load",function(){
				$loading.modal('close');
				try{
					var result=JSON.parse($(document.getElementById('fileUploadIframe_').contentWindow.document.body).find("pre").html());
					if (result.status == true) {
						$._uploadTempFn(result.result);
					}else{
						AMUI.dialog.error({
							title : '上传失败',
							content : result.error.message
						});
					}
				}catch(e){
					$._uploadTempFn();
					AMUI.dialog.error({
						title : '上传失败',
						content : "上传失败，请稍后再试"
					});
				}
				$("#fileUploadDiv_").remove();
			});
			
			$("#fileUploadDiv_ form").submit();
		}
	};
})(jQuery);