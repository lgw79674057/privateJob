(function($) {
	$.Dict = {
		nationalitys:function(obj){
			$.RESTFUL.GET({
				url : $.CONTEXT_PATH + "api/v1/dict/nationalitys",
				success : function(d) {
					if (d.status == true) {
						for(var str in d.result){
							if(str=="CHN"){
								obj.prepend("<option value='"+str+"'>"+str+"-"+d.result[str]+"</option>")
							}else{
								obj.append("<option value='"+str+"'>"+str+"-"+d.result[str]+"</option>")
							}
						}
					}
					obj[0].selectedIndex=0;
				}
			});
		},
		nationalitysCallBack:function(fn){
			$.RESTFUL.GET({
				url : $.CONTEXT_PATH + "api/v1/dict/nationalitys",
				success : function(d) {
					if (d.status == true) {
						var string="";
						var firest="";
						for(var str in d.result){
							if(str=="CHN"){
								firest="<option value='"+str+"'>"+str+"-"+d.result[str]+"</option>"
							}else{
								string+="<option value='"+str+"'>"+str+"-"+d.result[str]+"</option>";
							}
						}
						string=firest+string;
					}
					fn(string)
				}
			});
		},
		cardType:function(obj){
			$.RESTFUL.GET({
				url : $.CONTEXT_PATH + "api/v1/dict/cardType",
				success : function(d) {
					if (d.status == true) {
						for(var str in d.result){
							if(str=="1"){
								obj.prepend("<option value='"+str+"'>"+d.result[str]+"</option>")
							}else{
								obj.append("<option value='"+str+"'>"+d.result[str]+"</option>")
							}
						}
						obj.prepend("<option value=''>--请选择--</option>")
					}
					obj[0].selectedIndex=0;
				}
			});
		},
		
		certType:function(obj){
			$.RESTFUL.GET({
				url : $.CONTEXT_PATH + "api/v1/dict/certType",
				success : function(d) {
					if (d.status == true) {
						for(var str in d.result){
							if(str=="1"){
								//obj.prepend("<option value='"+str+"'>"+d.result[str]+"</option>")
							}else{
								if(str=="2"){
									obj.prepend("<option value='"+str+"'>"+d.result[str]+"</option>")
								}else{
									obj.append("<option value='"+str+"'>"+d.result[str]+"</option>")
								}
							}
						}
					}
				}
			});
		},
		
		certTypeCallBack:function(fn){
			$.RESTFUL.GET({
				url : $.CONTEXT_PATH + "api/v1/dict/certType",
				success : function(d) {
					if (d.status == true) {
						var ss="";
						for(var str in d.result){
							if(str!="1"){
								if(str=="2"){
									ss="<option value='"+str+"'>"+d.result[str]+"</option>"+ss;
								}else{
									ss+="<option value='"+str+"'>"+d.result[str]+"</option>";
								}
							}
						}
						fn(ss);
					}
				}
			});
		}
	}
})(jQuery);