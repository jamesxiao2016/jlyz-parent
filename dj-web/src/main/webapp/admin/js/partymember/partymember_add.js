$(function() {
	//getProdInfo(id);
	initEvent();
	initValidate();
});


function initEvent() {
    $("#btnSubmit").click(submitForm);
    $("#btnBack").click(function() {
        history.go(-1);
    });

    // 性别
    $.post("../../api/v1/component/getDictList?dictType=sex", function(data) {
        $('.selsex').select2({
            data : data
        });
    });
    $.post("../../admin/section/getSectionList", function(data) {
        $('#selsection').select2({
            data : data
        });
        sectionId = $("#selsection").select2("val");
        deptList(sectionId);
    });

    $("#selsection").on("change", function() {
        sectionId = $(this).val();
        console.log(sectionId);
        deptList(sectionId);
    });
    // 职位
    // $.post("../../api/v1/component/getDictList?dictType=party_post",
    // function(
    // data) {
    // $('.selpost').select2({
    // data : data
    // });
    // })
    function deptList(sectionId) {
        var $select = $('#deptId');
        var url = '../../api/v1/component/getDeptNameList?sectionId=' + sectionId;
        $.post(url, function(data) {

            instance = $select.data('select2');
            if(instance){
                $select.select2('destroy').empty();
            }
            $select.select2({
                data : data
            });
        });
    }

    $('.selpost').select2({
        ajax : {
            url : '../../api/v1/component/getDynamicDictList?dictType=party_post',
            dataType : 'json',
            delay : 150
        }
    });

    $('.seledu').select2({
        ajax : {
            url : '../../api/v1/component/getDynamicDictList?dictType=education',
            dataType : 'json',
            delay : 150
        }
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

function getProdInfo(id) {
	if (id != undefined && id != "") {
		$("#id").val(id);
		$("#nametip").hide();
		$("#title").text("编辑角色");

		$.ajaxPost("../../v1/system/getRoleInfoById", {
			id : id
		}, function(data) {
			if (data.result == 200) {
				$.setDataToForm(data.data);
			} else {
				layer.msg("获取角色信息失败");
			}
		})
	}
}

function submitForm() {
	// 验证参数
	if (!validatorForm.form()) {
		return;
	}
	var formData = $("#productForm").formSerialize();
	console.log(formData);
	$.ajaxPost("../partymember/addPartyMember", formData, function(data) {
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