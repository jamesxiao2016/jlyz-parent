var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "com.meig.link.db.mapper.MgQueryMapper.queryRole";
$(function() {
	init();
	initEvent();
});

function initEvent() {
	$("#btnQuery").click(query);

	$("#btnAdd").click(function() {
		location.href = "role_add.html";
	});
}

function query() {
	var qryParam = {
		"name" : getLikeVal($("#name").val()),
		"start" : $("#start").val(),
		"end" : $("#end").val(),
		"orderBy" : 't.create_time desc'
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
		"orderBy" : 't.create_time desc'
	};
	jQuery(grid_selector).jqGrid({
		url : '../../compCtl/queryJqData',
		datatype : "json",
		rownumbers : true,
		colModel : [ {
			label : 'ID',
			name : 'id',
			index : 'id',
			hidden : true
		}, {
			label : '角色名称',
			name : 'roleName',
			index : 'role_name',
		}, {
			label : '说明',
			name : 'remark',
			index : 'remark',
			align : "center",
		}, {
			label : '创建时间',
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
		sortname : 't.create_time',
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
	var btnEdit = "<a href='role_add.html?id=" + rowObject.id + "'>编辑</a>";
	var btnDel = "<a href='javascript:delRecord(" + rowObject.id + ")'>删除</a>";
	var btnConfig = "<a href='javascript:configRecord(" + rowObject.id
			+ ")'>权限配置</a>";
	if (rowObject.id <= 10) {
		return btnEdit + "&nbsp;" + btnConfig;
	}
	return btnEdit + "&nbsp;" + btnDel + "&nbsp;" + btnConfig;
}

function delRecord(id) {
	layer.confirm('确定要删除吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajaxPost("../../v1/system/deleteRoleById", {
			id : id
		}, function(data) {
			if (data.result == 200) {
				layer.msg("删除成功");
				query();
			} else {
				layer.msg("删除失败");
			}
		})
	});
}

function configRecord(id) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	// 加载已经配置的菜单
	$.ajaxPost("../../v1/system/getRoleMenuList", {
		roleId : id
	}, function(data) {
		var map = new Map();
		if (data && data.data) {
			$.each(data.data, function(idx, item) {
				map.set(item, item);
			});

			var node = treeObj.getNodes();
			var nodes = treeObj.transformToArray(node);
			for (var i = 0, l = nodes.length; i < l; i++) {
				var isExist = map.get(nodes[i].id);
				if (isExist != null && isExist != undefined) {
					treeObj.checkNode(nodes[i], true, false);
				}
			}
		}
	});

	// 打开窗口
	layer.open({
		type : 1,
		title : "配置菜单",
		skin : 'layui-layer-rim', // 加上边框
		area : [ '350px', '450px' ], // 宽高
		content : $("#treeDiv"),
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			var nodeIds = getCheckedNodes();
			$.ajaxPost("../../v1/system/saveRoleMenuList", {
				roleId : id,
				menus : nodeIds
			}, function(data) {
				if (data.result == 200) {
					layer.msg("保存成功");
					treeObj.checkAllNodes();
					layer.close(index);
				} else {
					if (data.reason) {
						layer.msg(data.reason);
					} else {
						layer.msg("保存失败");
					}
				}
			});
		},
		btn2 : function(index, layero) {
			layer.close(index);
		}
	});
}