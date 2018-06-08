$(function() {
	checkLogin();

	initEvent();
});

function initEvent() {
	$("#applyModule").click(dialogModule);
}

function checkLogin() {
	$
			.ajaxPost(
					"v1/getLoginUser",
					function(data) {
						if (data.result != 200) {
							location.href = "login.html";
						} else {
							loadMenu();
							if (data.data) {
								if (data.data.userType == "2") {
									$("#applyModule").show();
									$("#mainframe").attr("src",
											"user/main.html");
								}
								// $("#uName").text(data.data.userName);
								$("#roleName").text(data.data.userName);
								$("#userName").val(data.data.userName);
								$("#loginName").val(data.data.loginName);
								$("#email").val(data.data.email);
								$("#telphone").val(data.data.telphone);
								storeItem("uid", data.data.id);
								storeItem("cid", data.data.companyId);
								storeItem("uname", data.data.userName);
								$("#mainframe").data("uid", data.data.id);
								$("#mainframe")
										.data("cid", data.data.companyId);
							}
						}
					})
}

function loginout() {
	$.post("v1/logout", {}, function(data) {
		if (data && data.success) {
			storeItem("uid", "");
			storeItem("cid", "");
			storeItem("uname", "");
		}
	});
	location.href = "login.html";
}

function getUId() {
	return $("#mainframe").data("uid");
}
var arrowStr = '<span class="fa arrow"></span>';
var target = 'target="mainframe"';
var firstStr = '<li><a href="{0}" {1}><i class="fa fa-building"></i><span class="nav-label">{2}</span>{3}</a>{4}</li>';
var subMenuStr = '<ul class="nav nav-second-level">'
		+ '<li><a class="J_menuItem" href="view/product/product_list.html">产品管理</a></li></ul>';

function loadMenu() {
	var token = getStoreItem("token");
	$
			.ajaxPost(
					"v1/system/getUserMenus",
					{
						userId : 1,
						token : token
					},
					function(data) {
						if (data.result == 200 && data.data) {
							handleUserModule(data.method);
							var menuStr = '';
							$
									.each(
											data.data,
											function(idx, item) {
												if (item.children
														&& item.children.length > 0) {
													var sub = loadSubMenu(item.children);
													sub = '<ul class="nav nav-second-level" aria-expanded="true">'
															+ sub + '</ul>';
													menuStr += $.format(
															firstStr, "#", "",
															item.name,
															arrowStr, sub);
												} else {
													menuStr += $
															.format(
																	firstStr,
																	item.url == "" ? "#"
																			: item.url,
																	item.url == "" ? ""
																			: target,
																	item.name,
																	"", "");
												}
											});

							$("#side-menu").html(menuStr);
							$('#side-menu').metisMenu();
							$("#side-menu ul").eq(0).addClass("in");
							$('#side-menu>li').click(function() {
								if ($('body').hasClass('mini-navbar')) {
									NavToggle();
								}
							});
							$('#side-menu>li li a').click(function() {
								if ($(window).width() < 769) {
									NavToggle();
								}
							});
						}
					});
}

function handleUserModule(modules) {
	if (modules) {
		if (modules.indexOf("4,") > -1) {
			$("#moduleIds1").iCheck('check');
			$("#moduleIds1").attr("disabled", "disabled");
		} else {
			$("#moduleIds1").iCheck('uncheck');
			$("#moduleIds1").removeAttr("disabled");
		}
		if (modules.indexOf("3,") > -1) {
			$("#moduleIds2").iCheck('check');
			$("#moduleIds2").attr("disabled", "disabled");
		} else {
			$("#moduleIds2").iCheck('uncheck');
			$("#moduleIds2").removeAttr("disabled");
		}
	}
}

function loadSubMenu(menus) {
	var str = '';
	if (menus && menus.length > 0) {
		$.each(menus, function(idx, item) {
			if (item.children && item.children.length > 0) {
				var sub = loadSubMenu(item.children);
				sub = '<ul class="nav nav-third-level" aria-expanded="true">'
						+ sub + '</ul>';
				str += $.format(firstStr, "#", "", item.name, arrowStr, sub);
			} else {
				str += $.format(firstStr, item.url == "" ? "#" : item.url,
						item.url == "" ? "" : target, item.name, "", "");
			}
		});
	}
	return str;
}

function modifyPwd() {
	layer.open({
		type : 1,
		title : "修改密码",
		skin : 'layui-layer-rim', // 加上边框
		area : [ '500px', '320px' ], // 宽高
		content : $("#modifyPwdDiv"),
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			// 验证参数
			if (!pwdValidator.form()) {
				return;
			}
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			$.ajaxPost("v1/system/modifyPwd", {
				oldPwd : $.md5(oldPwd),
				newPwd : $.md5(newPwd)
			}, function(data) {
				if (data && data.result == 200) {
					layer.msg("修改密码成功");
					layer.close(index);
				} else {
					if (data.reason) {
						layer.msg(data.reason);
					} else {
						layer.msg("修改密码失败");
					}
				}
			});
		},
		btn2 : function(index, layero) {
			layer.close(index);
		}
	});
}
var pwdValidator = $("#pwdForm").validate({
	rules : {
		"oldPwd" : "required",
		"newPwd" : {
			required : true,
			regexPassword : true,
			minlength : 8,
		},
		"confirmPwd" : {
			required : true,
			minlength : 8,
			equalTo : "#newPwd"
		}
	},
	messages : {
		oldPwd : "请输入原密码",
		newPwd : {
			required : "请输入您的新密码",
			minlength : "密码必须8个字符以上",
			regexPassword : '密码至少包一个大写字母、一个小写字母及一个符号，长度至少8位',
		},
		confirmPwd : {
			required : "请再次输入密码",
			minlength : "密码必须8个字符以上",
			equalTo : "两次输入的密码不一致"
		}
	}
});

var infoValidator = $("#userForm").validate({
	rules : {
		"userName" : "required",
		"email" : {
			required : true,
			email : true,
		}
	},
	messages : {
		userName : "请输入您的姓名",
		email : {
			required : "请输入您的邮箱",
			email : "请输入有效的电子邮件地址"
		}
	}
});

function modifyUser() {
	layer.open({
		type : 1,
		title : "用户信息",
		skin : 'layui-layer-rim', // 加上边框
		area : [ '500px', '350px' ], // 宽高
		content : $("#userInfoDiv"),
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			// 验证参数
			if (!infoValidator.form()) {
				return;
			}
			$.ajaxPost("v1/system/modifyUser", {
				userName : $("#userName").val(),
				email : $("#email").val(),
				telphone : $("#telphone").val()
			}, function(data) {
				if (data && data.result == 200) {
					layer.msg("修改成功");
					layer.close(index);
				} else {
					if (data.reason) {
						layer.msg(data.reason);
					} else {
						layer.msg("修改失败");
					}
				}
			});
		},
		btn2 : function(index, layero) {
			layer.close(index);
		}
	});
}

function loadSrc() {
	var src = $("#mainframe").attr("src");
	if (src.indexOf("login.html") != -1) {
		top.location.href = src;
	}
}

// 密码验证正则表达式
jQuery.validator
		.addMethod(
				"regexPassword",
				function(value, element) {
					return this.optional(element)
							|| /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
									.test(value);
				}, "一个大写，一个小写，一个符号");

function dialogModule() {
	layer.open({
		type : 1,
		title : "申请开通服务",
		skin : 'layui-layer-rim', // 加上边框
		area : [ '450px', '200px' ], // 宽高
		content : $("#applyModuleDiv"),
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			var ids = "";
			$("input[name='moduleIds']:checkbox").each(function() {
				if (true == $(this).is(':checked')) {
					ids += $(this).val() + ",";
				}
			});

			if (ids.length == 0) {
				layer.msg("请选择需要开通的服务");
				return;
			}

			$.ajaxPost("ct/user/applyModule", {
				modules : ids,
			}, function(data) {
				if (data && data.result == 200) {
					layer.msg("申请成功");
					layer.close(index);
				} else {
					if (data.reason) {
						layer.msg(data.reason);
					} else {
						layer.msg("申请失败");
					}
				}
			});
		},
		btn2 : function(index, layero) {
			layer.close(index);
		}
	});
}