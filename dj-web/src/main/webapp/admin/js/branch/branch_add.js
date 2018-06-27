$(function() {
	initData();
	initEvent();
	initValidate();
});

function initData() {
	var eduData = parent.dictMap["education"];
	var postData = parent.dictMap["party_post"];
	var eduSelData = parent.getSelectDataByDictType("education");
	if(deptId1) {
        $.get(
            "../../admin/section/getPartyMembersByDeptId?deptId="
            + deptId1, function(data) {
                $('#principalId').select2({
                    data : data.results
                });
                if(deptPrincipalId) {
                		$('#principalId').val(deptPrincipalId).trigger("change");
                }
            });
        $("#title").text("修改党支部");
	}

	$.post("../../admin/getSectionAndBuildingTree", function(data) {
		$('#buildingId').select2({
			data : data,
			language : "zh-CN"
		});
        if (deptBuilingId) {
            $("#buildingId").val(deptBuilingId).trigger("change");
        }
	});

    //加载父级党支部
    $.post("../../admin/getSectionAndDeptTree", function(data) {
        $('#parentId').select2({
            data : data,
            language : "zh-CN"
        });
		if(!deptParentDeptId) {
            deptParentDeptId = "0";
		}
        $("#parentId").val(deptParentDeptId).trigger("change");
    });
}

function initEvent() {
	$("#btnSubmit").click(submitForm);
	$("#btnBack").click(function() {
		history.go(-1);
	});
}

function loadPrincipal(deptId) {
    var $select = $('#principalId');
    var url = '../../admin/section/getPartyMembersByDeptId?deptId=' + deptId;
    $.post(url, function(data) {

        instance = $select.data('select2');
        if (instance) {
            $select.select2('destroy').empty();
        }
        $select.select2({
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
    var reqUrl = "../branch/addBranch";
    if(id && id != "0") {
        reqUrl = "../branch/updateBranch/" + id;
    }
	var formData = $("#productForm").formSerialize();
	console.log(formData);
	$.ajaxJson(reqUrl, formData, function(data) {
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
}