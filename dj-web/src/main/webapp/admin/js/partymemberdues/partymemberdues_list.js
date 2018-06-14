var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryPartymemberDuesList";
$(function() {
	init();
	initEvent();
});

function initEvent() {
	$("#btnQuery").click(query);

	$("#btnAdd").click(function() {
		location.href = "add.html";
	});
}

function query() {
	var qryParam = {
		"userName" :  $("#userName").val(),
		"start" : $("#start").val(),
		"end" : $("#end").val(),
		"orderBy" : 'dpd.payment_time desc'
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
		"orderBy" : 'dpd.payment_time desc'
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
		}, {
			label : '党员姓名',
			name : 'userName',
			index : 'user_name',
		}, 
		{
			label : '待缴纳年份',
			name : 'duesYear',
			index : 'dues_year',
		}, 
		{
			label : '待缴纳月份',
			name : 'duesMonth',
			index : 'dues_month',
		}, 
		{
			label : '待缴纳金额',
			name : 'duesMoney',
			index : 'dues_money',
		}, 
		{
			label : '缴费状态',
			name : 'status',
			index : 'status',
			formatter :  statusFormatter,
		},
		{
			label : '缴费时间',
			name : 'paymentTime',
			index : 'payment_time',
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
		sortname : 'dpd.payment_time',
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
	var btnEdit = "<a href='../partymemberdues/add.html?id=" + rowObject.id + "'>编辑&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>";
	var btnDel = "<a href='javascript:delRecord(" + rowObject.id + ")'>删除</a>";

	return btnEdit + "&nbsp;" + btnDel;
}

function statusFormatter(cellvalue, options, rowObject) {
	if(cellvalue == "0"){
		return "未缴纳";
	}else if(cellvalue == "1"){
		return "已缴纳";
	}
	return "";
}

function delRecord(id) {
	layer.confirm('确定要删除吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajaxPost("../../admin/partymemberdues/deleteById", {
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
