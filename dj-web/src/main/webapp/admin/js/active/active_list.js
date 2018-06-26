var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryActiveList";
$(function() {
	init();
	initEvent();
});

function initEvent() {
	$("#btnQuery").click(query);

	$("#btnAdd").click(function() {
		location.href = "add.html";
	});

	$.post("../../admin/getSectionAndDeptTree", function(data) {
		$('#seldept').select2({
			data : data,
			language : "zh-CN"
		});
	});

	$("#seldept").on("change", function() {
		sectionId = $(this).val();
		query();
	});
}


function deptList(sectionId) {
	var $select = $('#seldept');
	var url = '../../api/v1/component/getDeptNameList?sectionId=' + sectionId;
	$.post(url, function(data) {
		
		instance = $select.data('select2');  
        if(instance){  
          $select.select2('destroy').empty();  
        }
        $select.select2({
			data : data
		});
        query();
	});
}

function query() {
	var qryParam = {
		"name" : getLikeVal($("#name").val()),
		"seldept" : $("#seldept").val(),
		"status" : $("#status").val(),
		"start" : $("#start").val(),
		"end" : $("#end").val(),
		"orderBy" : 't.start_time desc'
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
		"seldept" : "0",
		"orderBy" : 't.start_time desc'
	};
	jQuery(grid_selector).jqGrid({
		url : '../../api/v1/component/query',
		datatype : "json",
		rownumbers : true,
		colModel : [ {
			label : '活动ID',
			name : 'id',
			index : 'id',
			hidden : true
		}, {
			label : '活动名称',
			name : 'activeName',
			index : 'name',
		}, {
			label : '活动类型',
			name : 'activeTypeName',
			index : 'sub_type_name',
			width: 90,
		}, {
			label : '负责人',
			name : 'principalName',
			index : 'principal_name',
			width: 50,
		}, {
			label : '发起人',
			name : 'createUser',
			index : 'user_name',
			width: 50,
		}, 
		{
			label : '活动状态',
			name : 'status',
			index : 'status',
			formatter : statusFormatter,
			width: 50,
		}, {
			label : '开始时间',
			name : 'startTime',
			index : 'start_time',
			formatter : datetimeFormatterMinute,
		}, {
			label : '结束时间',
			name : 'endTime',
			index : 'end_time',
			formatter : datetimeFormatterMinute,
		}, {
			label : '操作',
			name : '',
			formatter : actionFormatter,
			fixed : true,
			sortable : false,
			resize : false,
			align : "center",
			width : 150,
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
		sortname : 't.start_time',
		sortorder : "desc",
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

function actionFormatter(cellvalue, options, rowObject) {
	var btnEdit = "<a href='../active/detail.html?id=" + rowObject.id + "&roleId=6"+"'>活动详情&nbsp;&nbsp;&nbsp;</a>";
	var btnDel = "<a href='javascript:delRecord(" + rowObject.id + ")'>删除</a>";

	return btnEdit + "&nbsp;" + btnDel;
}

function statusFormatter(cellvalue, options, rowObject) {
	if (cellvalue == "1") {
		return "已发布";
	} else if (cellvalue == "0") {
		return "待审核";
	} else if (cellvalue == "-1") {
		return "已取消";
	}

	return "";
}

function delRecord(id) {
	layer.confirm('确定要删除吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajaxPost("../../admin/active/deleteById", {
			id : id
		}, function(data) {
			if (data.code == 1000) {
				layer.msg("删除成功");
				query();
			} else {
				layer.msg("删除失败");
			}
		})
	});
}
