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
			},
			userName : {
				required : true,
				minlength : 2
			},
			idcard : {
				required : true,
				minlength : 18
			}
		},
		messages : {
			name : icon + "请输入党员姓名",
			userName : icon + "请输入登录账号",
			idcard : icon + "请输入身份证号"
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
	$.ajaxJson("../partymember/updatePartyMember/" + id, formData, function(data) {
		if (data.code == 1000) {
			history.go(-1);
		} else {
			if (data.msg) {
				layer.msg(data.msg);
			} else {
				layer.msg("保存失败");
			}
		}
	});
	// $.submitForm();
	// $("#productForm").submit();
}