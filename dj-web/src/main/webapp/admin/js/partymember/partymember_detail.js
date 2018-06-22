$(function() {
	initData();
	initEvent();
	initValidate();
});

function initData() {
	var eduData = parent.dictMap["education"];
	var postData = parent.dictMap["party_post"];
	var eduSelData = parent.getSelectDataByDictType("education");;

	var postSelData = parent.getSelectDataByDictType("party_post");
	$("#partyPostCode").select2({
		data : postSelData,
		language : "zh-CN"
	});

	$("#educationCode").select2({
		data : eduSelData,
		language : "zh-CN"
	});

	$("#partyPostCode").select2("val", memberPost);
	$("#educationCode").select2("val", memberEdu);
	
	//加载党支部
	$.post("../../admin/getSectionAndDeptTree", function(data) {
		$('#deptId').select2({
			data : data,
			language : "zh-CN"
		});
		$("#deptId").val(memberDeptId).trigger('change');
	});
	
}

function deptList(sectionId) {
	var $select = $('#deptId');
	var url = '../../api/v1/component/getDeptNameList?sectionId=' + sectionId;
	$.post(url, function(data) {
		instance = $select.data('select2');
		if (instance) {
			$select.select2('destroy').empty();
		}
		$select.select2({
			data : data,
			language : "zh-CN"
		});
	});
}

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
			name : {
				required : true,
				minlength : 2
			}
		},
		messages : {
			roleName : icon + "请输入名称"
		}
	});
}

function submitForm() {
	// 验证参数
	if (!validatorForm.form()) {
		return;
	}
	var id = $("#id").val();
	var formData = $("#productForm").formSerialize();
	console.log(formData);
	$.ajaxPost("../section/updateSection/" + id, formData, function(data) {
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