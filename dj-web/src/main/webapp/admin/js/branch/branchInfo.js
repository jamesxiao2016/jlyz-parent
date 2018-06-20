var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryPartyMemberList";
var sectionId = 0;
$(function() {
	initEvent();
	init();
});

function initEvent() {

	$("#btnSubmit").click(saveDeptInfo);

	$.post("../../admin/section/getSectionList", function(data) {
		$('#selsection').select2({
			data : data
		});

		sectionId = $("#selsection").select2("val");

		initTree(sectionId);
		loadBuilding(sectionId);
	});

	$("#selsection").on("change", function() {
		sectionId = $(this).val();
		console.log(sectionId);
		initTree(sectionId);
		loadBuilding(sectionId);
	});
}

function saveDeptInfo() {
	// 验证参数
	if (!validatorForm.form()) {
		return;
	}
	var id = $("#id").val();
	var formData = $("#deptForm").formSerialize();
	console.log(formData);
	$.ajaxPost("../../admin/updateBranch/"+id, formData, function(data) {
		if (data.code == 1000) {
			layer.msg("保存成功");
		} else {
			if (data.reason) {
				layer.msg(data.reason);
			} else {
				layer.msg("保存失败");
			}
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

function loadBuilding(sectionId) {
	var $select = $('#buildingId');
	var url = '../../admin/building/getBuildingList?sectionId=' + sectionId;
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

function query(deptId) {
	var qryParam = {
		"deptId" : deptId
	};
	jQuery(grid_selector).setGridParam({
		postData : {
			"sId" : sId,
			params : JSON.stringify(qryParam)
		}
	}).trigger("reloadGrid");
}

function init() {
	var qryParam = {
		"deptId" : 0
	};
	jQuery(grid_selector).jqGrid({
		sortable : true,
		url : '../../api/v1/component/query',
		datatype : "json",
		rownumbers : true,
		colModel : [ {
			label : 'ID',
			name : 'id',
			index : 'id',
			hidden : true
		}, {
			label : '姓名',
			name : 'name',
			index : 'name',
			width : 30
		}, {
			label : '性别',
			name : 'sex',
			index : 'sex',
			width : 30,
			formatter : sexFormatter,
		}, {
			label : '总积分',
			name : 'totalScore',
			index : 'totalScore',
			align : "center",
			width : 30
		}, {
			label : '职级',
			name : 'roleId',
			index : 'roleId',
			align : "center",
			width : 30
		} ],
		postData : {
			"sId" : sId,
			params : JSON.stringify(qryParam)
		},
		rowNum : 10,
		rowList : [ 10, 30, 50 ],
		pager : pager_selector,
		viewrecords : true,
		height : '100%',

		loadComplete : function() {
			var table = this;
			setTimeout(function() {
				styleCheckbox(table);
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},
	});

	jQuery(grid_selector).jqGrid('navGrid', pager_selector, {
		edit : false,
		add : false,
		del : false,
		search : false
	});

	var parent_column = $(grid_selector).closest('[class*="col-"]');
	// resize to fit page size
	$(window).on('resize.jqGrid', function() {
		$(grid_selector).jqGrid('setGridWidth', parent_column.width());
	})
	$(window).triggerHandler('resize.jqGrid');
}

function sexFormatter(cellvalue, options, rowObject) {
	var sexStr = "保密";
	if (cellvalue == "0") {
		sexStr = "女";
	} else if (cellvalue == "1") {
		sexStr = "男"
	}
	return sexStr;
}

var setting = {
	callback : {
		onClick : zTreeOnClick
	}
};

function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	var deptId = treeNode["deptId"];
	query(deptId);

	getDeptInfo(deptId);
}

function getDeptInfo(deptId) {
	$.get("../../admin/branch/detail/" + deptId, function(data) {
		console.log(data);
		$.setDataToForm(data.data);
		$("#buildingId").val(data.data.buildingId).trigger("change");
	});
}

var zTreeObj;
function initTree(sectionId) {
	$.get("../../api/v1/dept/deptTree?sectionId=" + sectionId, function(data) {
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
	});
}

// -->

// function delRecord(id) {
// layer.confirm('确定要删除吗？', {
// btn : [ '确定', '取消' ]
// // 按钮
// }, function() {
// $.ajaxPost("../../admin/section/deleteById", {
// id : id
// }, function(data) {
// if (data.code == 1000) {
// layer.msg("删除成功");
// query();
// } else {
// layer.msg("删除失败");
// }
// })
// });
// }
