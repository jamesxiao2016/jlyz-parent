var id = "";
var pid = "";
var pname = "";
$(function() {
	id = $.getUrlParam("id");
	pid = $.getUrlParam("pid");
	pname = decodeURI(decodeURI($.getUrlParam("pname")));
	if (pid != "") {
		$("#parentId").val(pid);
		$("#parentName").val(pname);
	}
	getRecordInfo(id);
	initEvent();
	initValidate();

	loadMenuTree(id);
});

function initEvent() {
	$("#btnSubmit").click(submitForm);
	$("#btnBack").click(function() {
		history.go(-1);
	});

	$("#parentSelect").select2({
		language : "zh-CN", // 设置 提示语言
		width : "100%", // 设置下拉框的宽度
		placeholder : "请选择",
	});

	$('.i-checks').on('ifChecked', function(event) { // ifCreated
														// 事件应该在插件初始化之前绑定
		// changeMenuType($(event.target).val());
	});

	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green',
	});
}

var validatorForm;

function initValidate() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	validatorForm = $("#productForm").validate({
		rules : {
			menuName : {
				required : true,
				minlength : 2
			}
		},
		messages : {
			menuName : icon + "请输入菜单名称"
		}
	});
}

function getRecordInfo(id) {
	if (id != undefined && id != "") {
		$("#id").val(id);
		$("#nametip").hide();
		$("#title").text("编辑菜单");

		$.ajaxPost("../../v1/system/getMenuInfoById", {
			id : id
		}, function(data) {
			if (data.result == 200) {
				$.setDataToForm(data.data);
			} else {
				layer.msg("获取产品信息失败");
			}
		})
	}
}

function submitForm() {
	// 验证参数
	if (!validatorForm.form()) {
		return;
	}
	// 提交
	var formData = $("#productForm").formSerialize();
	console.log(formData);
	$.ajaxPost("../../v1/system/saveMenu", formData, function(data) {
		if (data.result == 200) {
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

var setting = {
	view : {
		// addHoverDom: addHoverDom,
		// removeHoverDom: removeHoverDom,
		selectedMulti : false
	},
	check : {
		enable : false
	},
	data : {
		simpleData : {
			enable : true,
		}
	},
	edit : {
		enable : false
	},
	callback : {
		beforeClick : beforeClick,
	// onClick: onClick
	}
};

function loadMenuTree(id) {
	var param = [];
	if (id) {
		param.push(id);
	}
	jQuery.ajaxSettings.traditional = true;
	$.ajaxPost("../../v1/system/getMenuTreeList", {
		"filterIds" : param
	}, function(data) {
		$.fn.zTree.init($("#treeDemo"), setting, data.data);
	});
}

function beforeClick(treeId, treeNode) {
	var check = (treeNode);
	return check;
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), v = "", vids = "";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vids += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (vids.length > 0)
		vids = vids.substring(0, vids.length - 1);
	$("#parentId").val(vids);
	$("#parentName").val(v);
}

function showMenu() {
	layer.open({
		type : 1,
		title : "选择上级菜单",
		skin : 'layui-layer-rim', // 加上边框
		area : [ '300px', '450px' ], // 宽高
		content : $("#menuContent"),
		btn : [ '确定', '关闭' ],
		btn1 : function(index, layero) {
			onClick();
		},
		btn2 : function(index, layero) {
			layer.close(index);
		}
	});

	// var cityObj = $("#parentName");
	// var cityOffset = $("#parentName").offset();
	// $("#menuContent").css({
	// left: cityOffset.left + "px",
	// top: cityOffset.top + cityObj.outerHeight() + "px"
	// }).slideDown("fast");
	//
	// $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}