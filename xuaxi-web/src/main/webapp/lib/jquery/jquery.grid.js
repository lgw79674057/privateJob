/**
 * 表格组件
 */
(function($) {

	var obj = new Object();

	function put(table, key, value) {
		obj[table + key] = value;
	}

	function get(table, key) {
		return obj[table + key];
	}

	function show(data, table) {
		var isPage = get(table, "isPage");
		var template = Handlebars.compile($(get(table, "template")).html());
		var t = $(table);
		if(t.is("table")){
			t.find("tbody").html(template(data.datas));
		}else{
			t.html(template(data.datas));
		}
		if (typeof (data.datas) == 'undefined'
				|| typeof (data.datas.length) == 'undefined'
				|| data.datas.length == 0) {
			if(get(table,"noDataInfo")){
				if(t.is("table")){
					t.find("tbody").html(get(table,"noDataInfo"));
				}else{
					t.html(get(table,"noDataInfo"));
				}
			}else{
				if(t.is("table")){
					t.find("tbody").html(
					"<tr><td colspan='999' align='center'><i class='am-icon-frown-o am-icon-sm' style='color:#dd514c'>&nbsp;&nbsp;</i>暂无数据</td></tr>");
				}else{
					t.html("<div align='center'><i class='am-icon-frown-o am-icon-sm' style='color:#dd514c'>&nbsp;&nbsp;</i>暂无数据</div>");
				}
			}
		}
		page(data, table);
		if (typeof (get(table, "callBack")) == 'function') {
			get(table, "callBack")(data.datas);
		}
	}

	function page(data, table) {
		if (get(table, "isPage") != true) {
			return;
		}
		var pageNo = get(table, "pageNo");
		var pageSize = get(table, "pageSize");
		var totalCount = data.totalSize;
		var pageCount = parseInt(totalCount % pageSize == 0 ? totalCount
				/ pageSize : (totalCount / pageSize) + 1);
		var pagertype=get(table, "pager");
		if(typeof(pagertype)=='function'){
			pagertype({data:data,pageNo:pageNo,pageSize:pageSize,totalCount:totalCount,pageCount:pageCount,table:table});
			return;
		}
		if(typeof(pagertype)!='string'){
			return;
		}
		var p = $(pagertype);
		var html = "<ul class=\"am-pagination am-pagination-right\">"
				+ "<li title='首页' "
				+ (pageNo == 1 ? "class=\"am-disabled\""
						: "onclick=\"$.Grid.load('" + table + "',1)\"")
				+ "><a>&laquo;</a></li>"
				+ "<li title='上一页' "
				+ (pageNo == 1 ? "class=\"am-disabled\""
						: "onclick=\"$.Grid.prev('" + table + "')\"")
				+ "><a>&lsaquo;</a></li>";
		if (pageCount <= 10) {
			for (var i = 1; i <= pageCount; i++) {
				html += "<li "
						+ (pageNo == i ? "class='am-active'"
								: "onclick=\"$.Grid.load('" + table + "'," + i
										+ ")\"") + "><a>" + i + "</a></li>";
			}
		} else {
			html += "<li "
					+ (pageNo == 1 ? "class='am-active'"
							: "onclick=\"$.Grid.load('" + table + "'," + 1
									+ ")\"") + "><a>1</a></li>";

			var start = pageNo - 2;

			if (start > 2) {
				html += "<li><a>..</a></li>";
			}
			if (start < 2) {
				start = 2;
			}
			if (start + 5 > pageCount) {
				start = pageCount - 5;
			}
			for (var i = start; i < start + 5; i++) {
				html += "<li "
						+ (pageNo == i ? "class='am-active'"
								: "onclick=\"$.Grid.load('" + table + "'," + i
										+ ")\"") + "><a>" + i + "</a></li>";
			}

			if (pageNo + 3 < pageCount) {
				html += "<li><a>..</a></li>";
			}
			html += "<li "
					+ (pageNo == pageCount ? "class='am-active'"
							: "onclick=\"$.Grid.load('" + table + "',"
									+ pageCount + ")\"") + "><a>" + pageCount
					+ "</a></li>";
		}
		if (pageCount == 0) {
			html += "<li class='am-active'><a>1</a></li>";
		}
		html += "<li title='下一页' "
				+ (pageNo == pageCount || pageCount == 0 ? "class=\"am-disabled\""
						: "onclick=\"$.Grid.next('" + table + "')\"")
				+ "><a>&rsaquo;</a></li>"
				+ "<li title='末页' "
				+ (pageNo == pageCount || pageCount == 0 ? "class=\"am-disabled\""
						: "onclick=\"$.Grid.load('" + table + "'," + pageCount
								+ ")\"")
				+ "><a>&raquo;</a></li></ul>";
		p.html(html);
	}

	$.Grid = {
		init : function(param) {
			var table = $(param.table);
			if(table.is("table")){
				if (table.find("tbody").length == 0) {
					table.append("<tbody></tbody>");
				}
			}
			put(param.table, "table", param.table);
			put(param.table, "url", param.url);
			var isPage = typeof (param.isPage) != 'undefined' ? param.isPage
					: true;
			put(param.table, "isPage", isPage);
			put(param.table, "pager", param.pager);
			put(param.table, "conditionForm", param.conditionForm);
			put(param.table, "template", param.template);
			var method = typeof (param.method) != 'undefined' ? param.method
					: "GET";
			put(param.table, "method", method);
			var pageSize = typeof (param.pageSize) != 'undefined' ? param.pageSize
					: 10;
			put(param.table, "pageSize", pageSize);
			var pageNo = typeof (param.pageNo) != 'undefined' ? param.pageNo
					: 1;
			put(param.table, "pageNo", pageNo);
			put(param.table, "dataFormart", param.dataFormart);
			put(param.table, "callBack", param.callBack);
			put(param.table, "loadInfo", param.loadInfo);
			put(param.table, "noDataInfo", param.noDataInfo);
			$.Grid.load(param.table, pageNo);
		},
		load : function(table_, pageNo) {
			var pageNo = typeof (pageNo) != 'undefined' ? pageNo : 1;
			var table = $(table_);
			put(table_, "pageNo", pageNo);
			var conditionForm = get(table_, "conditionForm");
			var param = "pageSize=" + get(table_, "pageSize") + "&pageNo="
					+ pageNo+"&isPage="+(get(table_,"isPage")?"0":"1");
			if (typeof (conditionForm) != 'undefined') {
				param += "&" + $(conditionForm).serialize();
			}
			if(get(table_,"loadInfo")){
				if(table.is("table")){
					table.find("tbody").html(get(table_,"loadInfo"));
				}else{
					table.html(get(table_,"loadInfo"));
				}
			}else{
				if(table.is("table")){
					table.find("tbody").html(
					"<tr><td colspan='999' align='center'><i class='am-icon-spinner am-icon-spin'></i></td></tr>");
				}else{
					table.html("<div align='center'><i class='am-icon-spinner am-icon-spin'></i></div>");
				}
			}
			$.RESTFUL.ajax({
				type : get(table_, "method"),
				url : get(table_, "url"),
				data : param,
				success : function(d) {
					if (d.status == true) {
						if (typeof (get(table_, "dataFormart")) == 'function') {
							d = get(table_, "dataFormart")(d.result);
						}
						show(d.result, table_);
					}else{
						AMUI.dialog.error({
							title : '系统开小差了',
							content : d.error.message
						});
					}
				},error : function(response) {
					AMUI.dialog.error({
						title : '系统开小差了',
						content : $.responseToJson(response).error.message
					});
				}
			});
		},
		next : function(table_) {
			$.Grid.load(table_, get(table_, "pageNo") + 1);
		},
		prev : function(table_) {
			$.Grid.load(table_, get(table_, "pageNo") - 1);
		},
		search : function(table_) {
			$.Grid.load(table_, 1);
		}
	}
})(jQuery);