$(function() {
	initEvent();
});

function initEvent() {
	$("#username").focus();
	$("#btnLogin").click(login);
	$("#code").keypress(function(e) {
		var key = e.which;
		if(key == 13) {
			login();
		}
	});
}

function login() {
	var username = $("#username").val();
	var code = $("#code").val();

	if(username == "") {
		layer.msg('请输入公司邮箱');
		$("#username").focus();
		return false;
	}

	if(code == "") {
		layer.msg('请输入验证码');
		$("#code").focus();
		return false;
	}

	$.post("v1/forgotPwd", {
		email: username,
		code: code
	}, function(data) {
		if(data.result == "200") {
			layer.confirm('新的密码已经发送到您的邮箱，请登录您的邮箱进行查看。', {
				btn: ['确定'] //按钮
			}, function() {
				location.href = "login.html";
			});
		} else {
			if(data.reason) {
				layer.msg(data.reason);
			} else {
				layer.msg("找回密码失败");
			}
		}
	});
}

function changeCode() {
	$("#valImg").attr("src", "ct/valcode?"+Math.random());
}
