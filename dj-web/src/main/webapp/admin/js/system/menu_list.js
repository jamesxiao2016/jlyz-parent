$(function() {
	loadSystemMenu();
	initEvent();
});

function initEvent() {
	$("#btnQuery").click(query);
	$("#btnAdd").click(function() {
		location.href = "menu_add.html";
	});

	$('.i-checks').on('ifChecked', function(event) { //ifCreated 事件应该在插件初始化之前绑定 
		changeMenuType($(event.target).val());
	});

	$('.i-checks').iCheck({
		checkboxClass: 'icheckbox_square-green',
		radioClass: 'iradio_square-green',
	});
}

function query() {
	loadSystemMenu("");
}

var menuList = new Array();
var subMenu = new Map();

function loadSystemMenu(name) {
	$.ajaxPost("../../v1/system/getAllMenuList", {
		name: name
	}, function(data) {
		menuList = new Array();
		subMenu = new Map();
		if(data && data.result == 200) {
			var str = "";
			$.each(data.data, function(idx, item) {
				if(item.parentId == "0") {
					menuList.push(item);
				} else {
					var s = subMenu[item.parentId];
					if(s == undefined || s == null || s.length == 0) {
						s = new Array();
					}
					s.push(item);
					subMenu[item.parentId] = s;
				}
			});

			$.each(menuList,
				function(idx, item) {
					var parentClass = "";
					if(item.parentId == "0") {
						str += $.format(rootStr, item.id, parentClass, item.menuName, item.menuUrl, item.sortIndex, 0, 1);
						str += eachMenu(item, subMenu[item.id]);
					}
				});
			$("#tdata").empty().html(str);
		}
		$('.tree').treegrid();
	});
}

function eachMenu(item, mlist) {
	var str = "";
	if(mlist && mlist.length > 0) {
		$.each(mlist, function(i, sitem) {
			parentClass = "treegrid-parent-" + item.id;
			str += $.format(menuStr, sitem.id, parentClass, sitem.menuName, sitem.menuUrl, sitem.sortIndex, item.id, 2);
			if(subMenu[sitem.id]) {
				str += eachMenu(sitem, subMenu[sitem.id]);
			}
		});
	}

	return str;
}
var rootStr = '<tr data-id={0} data-parent={5} data-level={6} class="treegrid-{0} {1}">' +
	'<td>{2}</td>' +
	'<td>{3}</td>' +
	'<td>{4}</td>' +
	'<td>' +
	'<button class="btn btn-primary btn-xs" type="button" onclick="addSubMenu({0},\'{2}\')">' +
	'<i class="fa fa-plus"></i>&nbsp;子菜单' +
	'</button>&nbsp;&nbsp;' +
	'<button class="btn btn-primary btn-xs" type="button" onclick="editMenu({0})">' +
	'<i class="fa fa-pencil"></i>&nbsp;编辑' +
	'</button>&nbsp;&nbsp;</td>' +
	'</tr>';

var menuStr = '<tr data-id={0} data-parent={5} data-level={6} class="treegrid-{0} {1}">' +
	'<td>{2}</td>' +
	'<td>{3}</td>' +
	'<td>{4}</td>' +
	'<td>' +
	'<button class="btn btn-primary btn-xs" type="button" onclick="addSubMenu({0},\'{2}\')">' +
	'<i class="fa fa-plus"></i>&nbsp;子菜单' +
	'</button>&nbsp;&nbsp;' +
	'<button class="btn btn-primary btn-xs" type="button" onclick="editMenu({0})">' +
	'<i class="fa fa-pencil"></i>&nbsp;编辑' +
	'</button>&nbsp;&nbsp;' +
	'<button class="btn btn-primary btn-xs" type="button" onclick="delMenu({0})">' +
	'<i class="fa fa-trash-o"></i>&nbsp;删除' + '</button>' + '</td>' +
	'</tr>';

var dialogIndex;

function addSubMenu(pid, pname) {
	location.href = "menu_add.html?pid=" + pid + "&pname=" + encodeURI(encodeURI(pname));
}

function addSubMenu2(pid, pname) {
	$("#formEditor")[0].reset();
	if(pid == "0") {
		pname = $("#appSelect").find("option:selected").text();
	}
	$("#pf_menu_id").val("");
	$("#pf_app_id").val($("#appSelect").val());
	$("#parent_id").val(pid);
	$("#parent_name").val(pname);
	$("#pDiv").show();
	dialogIndex = layer.open({
		type: 1,
		title: '添加菜单',
		shadeClose: true,
		shade: 0.3,
		area: ['600px', '460px'],
		content: $("#addForm")
	});
}

function editMenu(menuId) {
	location.href = "menu_add.html?id=" + menuId;
	//	$("#formEditor")[0].reset();
	//	$("#pf_menu_id").val(menuId);
	//	$("#pDiv").hide();
	//	$.getFormData("../../api/common/getInfoById", "#formEditor", {
	//		id: menuId,
	//		_table_name: "pf_app_menu"
	//	}, function(data) {
	//		dialogIndex = layer.open({
	//			type: 1,
	//			title: '修改菜单',
	//			shadeClose: true,
	//			shade: 0.3,
	//			area: ['600px', '405px'],
	//			content: $("#addForm")
	//		});
	//	});

}
//删除菜单
function delMenu(id) {
	layer.confirm('确定要删除吗？', {
		btn: ['确定', '取消'] //按钮
	}, function() {
		$.ajaxPost("../../v1/system/deleteMenuById", {
			id: id
		}, function(data) {
			if(data.result == 200) {
				layer.msg("删除成功");
				query();
			} else {
				if(data.reason) {
					layer.msg(data.reason);
				} else {
					layer.msg("删除失败");
				}

			}
		})
	});
}

function closeDialog() {
	layer.close(dialogIndex);
}

function changeMenuType(type) {
	$(".menuType").hide();
	$("#menuTypeDiv" + type).show();
}