$(function() {
	initEvent();
	initValidate();
});

function initEvent() {
	$("#btnSubmit").click(submitForm);
	$("#btnBack").click(function() {
		history.go(-1);
	});
	
	$.post("../../admin/section/getSectionList", function(data) {
		$('#sectionId').select2({
			data : data
		});
	});
}

var validatorForm;

function initValidate() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	validatorForm = $("#productForm").validate({
		rules : {
			name : {
				required : true,
				minlength : 2,
				maxlength : 32
			},
			name : {
				required : true,
				minlength : 2,
				maxlength : 32
			}
		},
		messages : {
			name : icon + "请输入楼宇名称",
			code : icon + "请输入楼宇编号",
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
	$.ajaxJson("../building/save", formData, function(data) {
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
}