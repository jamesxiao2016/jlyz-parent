$(function() {
	initEvent();
});

function initEvent() {
	$("#username").focus();
	$("#btnLogin").click(login);
	$("#pwd").keypress(function(e) {
		var key = e.which;
		if (key == 13) {
			login();
		}
	});
}

function login() {
	var username = $("#username").val();
	var pwd = $("#pwd").val();

	if (username == "") {
		layer.msg('请输入用户名');
		$("#username").focus();
		return false;
	}

	if (pwd == "") {
		layer.msg('请输入密码');
		$("#pwd").focus();
		return false;
	}

	$.post("../api/v1/loginAdmin", {
		name : username,
		pwd : $.md5(pwd)
	}, function(data) {
		if (data.code == 1000) {
			storeItem("atoken", data.data.token);
			location.href = "index.html";
		} else {
			if (data.reason) {
				layer.msg(data.reason);
			} else {
				layer.msg("登录失败");
			}
		}
	});
}