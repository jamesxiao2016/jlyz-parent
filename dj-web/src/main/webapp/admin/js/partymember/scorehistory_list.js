var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryScoreHistoryList";
$(function() {
	init();
	initEvent();
});

function initEvent() {
	$("#btnQuery").click(query);

	$("#btnAdd").click(function() {
		location.href = "add.html";
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
		"orderBy" : 'pm.party_post_code asc'
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
			"orderBy" : 'pm.party_post_code asc'
	};
	jQuery(grid_selector).jqGrid({
        sortable: true,
		url : '../../api/v1/component/query',
		datatype : "json",
		rownumbers : true,
		colModel : [  {
			label : '党员姓名',
			name : 'partyMemberName',
			index : 'partyMemberName'
		}, {
			label : '积分',
			name : 'score',
			index : 'score'
		}, {
            label : '积分描述',
            name : 'scoreDesc',
            index : 'scoreDesc',
            align : "center",
            width : 300
        }, {
			label : '加分时间',
			name : 'addTime',
			index : 'addTime',
			align : "center",
            formatter : datetimeFormatter
		}, {
            label : '申请人',
            name : 'applicant',
            index : 'applicant'
        }, {
            label : '审批人',
            name : 'approver',
            index : 'approver',
            align : "center"
        }],
		postData : {
			"sId" : sId,
			params : JSON.stringify(qryParam)
		},
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : pager_selector,
		viewrecords : true,
		height : '100%',
		sortname : 'pm.party_post_code',
		sortorder : "asc",
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
	var btnEdit = "<a href='../section/add.html?id=" + rowObject.id + "'>编辑</a>";
	var btnDel = "<a href='javascript:delRecord(" + rowObject.id + ")'>删除</a>";

	return btnEdit + "&nbsp;" + btnDel;
}

// function delRecord(id) {
// 	layer.confirm('确定要删除吗？', {
// 		btn : [ '确定', '取消' ]
// 	// 按钮
// 	}, function() {
// 		$.ajaxPost("../../admin/section/deleteById", {
// 			id : id
// 		}, function(data) {
// 			if (data.code == 1000) {
// 				layer.msg("删除成功");
// 				query();
// 			} else {
// 				layer.msg("删除失败");
// 			}
// 		})
// 	});
// }
