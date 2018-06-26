var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryOperationLogList";
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
	});
}


function query() {
	var qryParam = {
		"optStatus" : $("#optStatus").val(),
		"userName" : getLikeVal($("#userName").val()),
		"seldept" :  $("#seldept").val(),
		"start" : $("#start").val(),
		"end" : $("#end").val(),
		"orderBy" : 'dlo.create_time desc'
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
		"orderBy" : 'dlo.create_time desc'
	};
	jQuery(grid_selector).jqGrid({
		url : '../../api/v1/component/query',
		datatype : "json",
		rownumbers : true,
		colModel : [ {
			label : 'ID',
			name : 'id',
			index : 'id',
			hidden : true
		}, 
		{
			label : '支部名称',
			name : 'deptName',
			index : 'dept_name',
			width : 170,
		}, 
		{
			label : '操作人',
			name : 'userName',
			index : 'user_name',
			width : 70,
		}, 
		{
			label : '操作位置',
			name : 'optName',
			index : 'opt_name',
			width : 70,
		}, 
		{
			label : '状态',
			name : 'status',
			index : 'status',
			formatter : statusFormatter,
			width : 70,
		}, 
		{
			label : '操作信息',
			name : 'errorMsg',
			index : 'error_msg',
			width : 100,
		}, {
			label : '操作时间',
			name : 'createTime',
			index : 'create_time',
			formatter : datetimeFormatter,
			align : "center",
			width : 100
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
		sortname : 'dlo.create_time',
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
	var btnDel = "<a href='javascript:delRecord(" + rowObject.id + ")'>删除</a>";

	return btnDel;
}

function statusFormatter(cellvalue, options, rowObject) {
	if(cellvalue == "1000"){
		return "成功";
	}else{
		return "失败";
	}
	return "";
}

function delRecord(id) {
	layer.confirm('确定要删除吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajaxPost("../../admin/operationlog/deleteById", {
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
