
var baseUrl = "/dj-web";

/**
 * 字符串替换 使用示例： var text = "a{0}b{0}c{1}d\nqq{0}"; var text2 = $.format(text, 1,
 * 2); alert(text2);
 */
$.format = function(source, params) {
	if (arguments.length == 1)
		return function() {
			var args = $.makeArray(arguments);
			args.unshift(source);
			return $.format.apply(this, args);
		};
	if (arguments.length > 2) {
		params = $.makeArray(arguments).slice(1);
	}
	if (params.constructor != Array) {
		params = [ params ];
	}
	$.each(params, function(i, n) {
		source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
	});
	return source;
};

function getUrlParam(name) {
	// 构造一个含有目标参数的正则表达式对象
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	// 匹配目标参数
	var r = window.location.search.substr(1).match(reg);
	// 返回参数值
	if (r != null)
		return unescape(r[2]);
	return "";
}
$.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return "";
}

$.submitForm = function(options) {
	var defaults = {
		url : "",
		param : [],
		loading : "正在提交数据...",
		success : null,
		close : true
	};
	var options = $.extend(defaults, options);
	// $.loading(true, options.loading);
	window.setTimeout(function() {
		if ($('[name=__RequestVerificationToken]').length > 0) {
			options.param["__RequestVerificationToken"] = $(
					'[name=__RequestVerificationToken]').val();
		}
		$.ajax({
			url : options.url,
			data : options.param,
			type : "post",
			// contentType : "application/json",
			dataType : "json",
			success : function(data) {
				if (data.code == "1000") {
					if (options.close == true) {
						layer.closeAll('page');
					}
					options.success(data);
				} else {
					// $.modalAlert(data.message, "warning");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// $.loading(false);
				// $.modalMsg(errorThrown, "error");
			},
			beforeSend : function() {
				// $.loading(true, options.loading);
			},
			complete : function() {
				// $.loading(false);
			}
		});
	}, 500);
}

$.submitForm2 = function(options) {
	var defaults = {
		url : "",
		param : [],
		loading : "正在提交数据...",
		success : null,
		close : true
	};
	var options = $.extend(defaults, options);
	// $.loading(true, options.loading);
	window.setTimeout(function() {
		if ($('[name=__RequestVerificationToken]').length > 0) {
			options.param["__RequestVerificationToken"] = $(
					'[name=__RequestVerificationToken]').val();
		}
		$.ajax({
			url : options.url,
			data : options.param,
			type : "post",
			// contentType : "application/json",
			dataType : "json",
			success : function(data) {
				if (data.code == "1000") {
					if (options.close == true) {
						layer.closeAll('page');
					}
					options.success(data);
				} else {
					// $.modalAlert(data.message, "warning");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// $.loading(false);
				// $.modalMsg(errorThrown, "error");
			},
			beforeSend : function() {
				// $.loading(true, options.loading);
			},
			complete : function() {
				// $.loading(false);
			}
		});
	}, 500);
}

$.fn.formValid = function() {
	return $(this)
			.valid(
					{
						errorPlacement : function(error, element) {
							element.parents('.formValue').addClass('has-error');
							element.parents('.has-error').find('i.error')
									.remove();
							element
									.parents('.has-error')
									.append(
											'<i class="form-control-feedback fa fa-exclamation-circle error" data-placement="left" data-toggle="tooltip" title="'
													+ error + '"></i>');
							$("[data-toggle='tooltip']").tooltip();
							if (element.parents('.input-group').hasClass(
									'input-group')) {
								element.parents('.has-error').find('i.error')
										.css('right', '33px')
							}
						},
						success : function(element) {
							element.parents('.has-error').find('i.error')
									.remove();
							element.parent().removeClass('has-error');
						}
					});
}
$.fn.formSerialize = function(formdate) {
	var element = $(this);
	// if (!!formdate) {
	for ( var key in formdate) {
		var $id = element.find('#' + key);
		var value = $.trim(formdate[key]).replace(/&nbsp;/g, '');
		var type = $id.attr('type');
		if ($id.hasClass("select2-hidden-accessible")) {
			type = "select";
		}
		switch (type) {
		case "checkbox":
			if (value == "true") {
				$id.attr("checked", 'checked');
			} else {
				$id.removeAttr("checked");
			}
			break;
		case "select":
			$id.val(value).trigger("change");
			break;
		default:
			$id.val(value);
			break;
		}
	}
	;
	// return false;
	// }
	var postdata = {};
	element.find('input,select,textarea').each(function(r) {
		var $this = $(this);
		var id = $this.attr('id');
		var type = $this.attr('type');
		switch (type) {
		case "checkbox":
			// id = $this.attr('name');
			if (id != undefined) {
				postdata[id] = $this.val();
			}
			break;
		default:
			var value = $this.val();
			if (!$.request("keyValue")) {
				if ($.isString(value)) {
					value = value.replace(/&nbsp;/g, '');
				}
			}
			if (id != undefined) {
				postdata[id] = value;
			}
			break;
		}
	});
	if ($('[name=__RequestVerificationToken]').length > 0) {
		postdata["__RequestVerificationToken"] = $(
				'[name=__RequestVerificationToken]').val();
	}
	return postdata;
};

$.isString = function(obj) { // 判断对象是否是字符串
	return Object.prototype.toString.call(obj) === "[object String]";
}
$.request = function(name) {
	var search = location.search.slice(1);
	var arr = search.split("&");
	for (var i = 0; i < arr.length; i++) {
		var ar = arr[i].split("=");
		if (ar[0] == name) {
			if (unescape(ar[1]) == 'undefined') {
				return "";
			} else {
				return unescape(ar[1]);
			}
		}
	}
	return "";
}
$.setDataToForm = function(data) {
	if (data == undefined || data == null) {
		return;
	}
	for ( var key in data) {
		var controlObj = $("#" + key);
		if (controlObj[0] != undefined && controlObj[0] != null) {
			var type = controlObj[0].type;
			switch (type) {
			case "hidden":
			case "text":
			case "number":
			case "password":
			case "textarea":
				controlObj.val(data[key]);
				break;
			case "select":
				// controlObj.val(data[key]);
				// $("#" + key).bootstrapSelect('setValue', data[key]);
				controlObj.val(data[key]);
				break;
			case "select-one": // 下拉框回选
				// controlObj.val(data[key]).trigger("change");
				// $("#" + key + "_div").bootstrapSelect('setValue', data[key]);
				controlObj.val(data[key]).trigger("change");
				break;
			case "checkbox":
				var selectData = data[key];
				if (controlObj.hasClass("js-switch")) {
					// setSwitchery(test_switch_val, true);
					var checkedBool = (selectData == "1") ? true : false;
					$("#test_switch_val_div").bootstrapSwitch("setValue",
							checkedBool);
				} else {
					$('#' + key + '_div').bootstrapiCheckbox("setValue",
							selectData);
				}

				break;
			case "radio":
				$('#' + key + '_div').bootstrapiCheck("setValue", data[key]);
				break;
			default:
				controlObj.val(data[key]);
				break;
			}
		}
	}
}

$.ajaxPost = function(url, param, callback, completeCallback, errorCallback) {
	var token = getStoreItem("atoken");
	if (jQuery.isFunction(param)) {
		callback = param;
		param = null;
	} else {
		if (!token && param) {
			token = param["token"];
		}
	}
	if (!token) {
		// top.location.href = getLoginUrl();
	}
	return $.ajax({
		type : "POST",
		traditional : true,
		dataType : "json",
		url : url,
		data : param,
		beforeSend : function(reqObj, settings) {
			reqObj.setRequestHeader('token', token);
		},
		success : function(data) {
			if (data.code == 1010) {
				top.location.href = baseUrl + '/admin/login.html';
			} else {
				if (callback) {
					callback(data);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			layer.closeAll('loading');
			if (textStatus == 'parsererror') {
				console.log("url:" + url)
				top.location.href = baseUrl + '/admin/login.html';
			}
			if (errorCallback && jQuery.isFunction(errorCallback)) {
				errorCallback(XMLHttpRequest, textStatus);
			} else {
				layer.msg("服务器加载失败，请联系管理员 " + textStatus);
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			layer.closeAll('loading');
			if (completeCallback && jQuery.isFunction(completeCallback)) {
				completeCallback(XMLHttpRequest, textStatus);
			}
		}
	});
}

$.ajaxJson = function(url, param, callback, completeCallback, errorCallback) {
	var token = getStoreItem("atoken");
	if (jQuery.isFunction(param)) {
		callback = param;
		param = null;
	} else {
		if (!token && param) {
			token = param["token"];
		}
	}
	if (!token) {
		// top.location.href = getLoginUrl();
	}
	return $.ajax({
		type : "POST",
		traditional : true,
		contentType : "application/json",
		dataType : "json",
		url : url,
		data : JSON.stringify(param),
		beforeSend : function(reqObj, settings) {
			reqObj.setRequestHeader('token', token);
		},
		success : function(data) {
			if (data.code == 1010) {
				top.location.href = baseUrl + '/admin/login.html';
			} else {
				if (callback) {
					callback(data);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			layer.closeAll('loading');
			if (textStatus == 'parsererror') {
				console.log("url:" + url)
				top.location.href = baseUrl + '/admin/login.html';
			}
			if (errorCallback && jQuery.isFunction(errorCallback)) {
				errorCallback(XMLHttpRequest, textStatus);
			} else {
				layer.msg("服务器加载失败，请联系管理员 " + textStatus);
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			layer.closeAll('loading');
			if (completeCallback && jQuery.isFunction(completeCallback)) {
				completeCallback(XMLHttpRequest, textStatus);
			}
		}
	});
}

var token = localStorage.getItem("atoken");

$.ajaxSetup({
	dataType : "json",
	cache : false,
	headers : {
		"atoken" : token
	},
	xhrFields : {
		withCredentials : true
	},
	complete : function(xhr) {
		// token过期，则跳转到登录页面
		if (xhr.responseJSON.code == 1010) {
			parent.location.href = baseUrl + '/admin/login.html';
		}
	}
});
