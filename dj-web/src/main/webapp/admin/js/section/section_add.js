var id = "";
$(function() {
	id = $.getUrlParam("id");
	//getProdInfo(id);
	initEvent();
	initValidate();
});

function initEvent() {
	$("#btnSubmit").click(submitForm);
	$("#btnBack").click(function() {
		history.go(-1);
	});
}

var validatorForm;

function initValidate() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	validatorForm = $("#productForm").validate({
		rules : {
			roleName : {
				required : true,
				minlength : 2
			}
		},
		messages : {
			roleName : icon + "请输入角色名称"
		}
	});
}

function submitForm() {
	// 验证参数
	if (!validatorForm.form()) {
		return;
	}
	var formData = $("#productForm").formSerialize();
	console.log(formData);
	$.ajaxJson("../section/addSection", formData, function(data) {
		if (data.code == 1000) {
			history.go(-1);
		} else {
			if (data.reason) {
				layer.msg(data.reason);
			} else {
				layer.msg("保存失败");
			}
		}
	});
	// $.submitForm();
	// $("#productForm").submit();
}