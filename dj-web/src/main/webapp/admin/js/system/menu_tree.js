var id = "";
var setting = {
	view: {
		//addHoverDom: addHoverDom,
		//removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true,
		}
	},
	edit: {
		enable: false
	}
};

$(function() {
	id = $.getUrlParam("id");
	var param = [];
	if(id) {
		param.push(id);
	}

	jQuery.ajaxSettings.traditional = true;
	$.ajaxPost("../../v1/system/getMenuTreeList", {
		"filterIds": param
	}, function(data) {
		$.fn.zTree.init($("#treeDemo"), setting, data.data);
	});
});

function getCheckedNodes() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	var ids = "";
	for(var i = 0; i < nodes.length; i++) {
		ids += nodes[i].id + ",";
	}
	return ids;
}