(function($) {
	$.RESTFUL = {
		POST : function(options) {
			options.type = "POST";
			this.ajax(options);
		},
		PUT : function(options) {
			options.type = "PUT";
			this.ajax(options);
		},
		PATCH : function(options) {
			options.type = "PATCH";
			this.ajax(options);
		},
		GET : function(options) {
			options.type = "GET";
			this.ajax(options);
		},
		DELETE : function(options) {
			options.type = "DELETE";
			this.ajax(options);
		},
		ajax : function(options) {
			options.contentType = "application/json; charset=utf-8";
			options.dataType = "JSON";
			$.ajax(options);
		}
	};
})(jQuery);