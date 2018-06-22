var dictMap = new Map();

$(function() {
	getUserInfo();
	initDict();
});

function getUserInfo() {
	$.ajaxPost("../admin/getAdminUserInfo",function(data) {
		if(data.code === 1000) {
			if(data && data.data) {
				$("#uName").text(data.data.userName);
				$("#userName").val(data.data.userName);
				$("#loginName").val(data.data.name);
				$("#email").val(data.data.email);
				$("#telphone").val(data.data.telphone);
			}
		}
	});
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
			$.ajaxPost("../admin/modifyPwd", {
				oldPwd : $.md5(oldPwd),
				newPwd : $.md5(newPwd)
			}, function(data) {
				if (data && data.code == 1000) {
					layer.msg("修改密码成功");
					layer.close(index);
					$("#oldPwd").val("");
					$("#newPwd").val("");
					$("#confirmPwd").val("");
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
			regexPassword : false,
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
			$.ajaxPost("../admin/modifyUser", {
				userName : $("#userName").val(),
				email : $("#email").val(),
				telphone : $("#telphone").val()
			}, function(data) {
				if (data && data.code == 1000) {
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

//密码验证正则表达式
jQuery.validator
		.addMethod(
				"regexPassword",
				function(value, element) {
					return this.optional(element)
							|| /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/
									.test(value);
				}, "一个大写，一个小写，一个符号");

function logoutSys() {
	storeItem("atoken", "");
	location.href = "login.html";
}

/**
 * 初始化，读取所有的字典表信息，放到map中
 * 
 * @returns
 */
function initDict() {
	$.get("../admin/getAllDictList", function(data) {
		if (data.code == 1000) {
			data.data.forEach(function(item, idx) {
				var tMap = dictMap[item.text];
				if (!tMap) {
					tMap = new Map();
				}
				tMap[item.id] = item.name;
				dictMap[item.text] = tMap;
			});
		}
	});
}

/**
 * 根据dictType和dictCode从所有的字典表中读取dictName
 * 
 * @param dictType
 * @param dictCode
 * @returns
 */
function getDictName(dictType, dictCode) {
	var dictName = dictMap[dictType][dictCode];
	if (!dictName) {
		dictName = "";
	}
	return dictName;
}

//从字典表中读取字典
function getSelectDataByDictType(dictType) {
	var dictData = dictMap[dictType];
	var selectData = [];
	$.each(dictData, function(index, item) {
		selectData.push({
			id : index,
			text : item
		});
	});
	return selectData;
}

/**
 * 根据字典表类型获取所有的字典列表
 * 
 * @param dictType
 * @returns
 */
function getDictListByDictType(dictType) {
	var dMap = new Map();
	var url = "../admin/getDictListByDictType?dictType=" + dictType;
	$.get(url, function(data) {
		if (data.code == 1000) {
			data.data.forEach(function(item, idx) {
				dMap[item.id] = item.text;
			});
		}
	});

	return dMap;
}