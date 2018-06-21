var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryPartyMemberList";
$(function() {
	init();
	initEvent();
});

function initEvent() {
	$("#btnQuery").click(query);

	$("#btnAdd").click(function() {
		location.href = "partymember_add.html";
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
        query();
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

	$('.selpost').select2({
        ajax : {
            url : '../../api/v1/component/getDynamicDictList?dictType=party_post',
            dataType : 'json',
            delay : 150
        }
    });
}

function query() {
	var qryParam = {
		"name" : getLikeVal($("#name").val()),
        "sex" : $("#selsex").val(),
        "position" : $("#selpost").val(),
        "dept" : $("#seldept").val(),
        "section" : $("#selsection").val()
	};
	jQuery(grid_selector).setGridParam({
		postData : {
			"sId" : sId,
			params : JSON.stringify(qryParam)
		}
	}).trigger("reloadGrid");
}

function init() {
	var qryParam = {};
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
			width : 70
		}, {
            label : '账号',
            name : 'account',
            index : 'account',
            align : "center"
        }, {
            label : '职位',
            name : 'partyPostCode',
            index : 'partyPostCode',
            align : "center",
            width : 70,
            formatter : positionFormatter
        }, {
			label : '性别',
			name : 'sex',
			index : 'sex',
			width : 70,
            formatter : sexFormatter
		}, {
			label : '年龄',
			name : 'age',
			index : 'age',
			align : "center",
			width : 70
		}, {
			label : '总积分',
			name : 'totalScore',
			index : 'totalScore',
			align : "center",
			width : 70
		}, {
			label : 'deptId',
			name : 'deptId',
			index : 'deptId',
			hidden : true
		}, {
			label : '出生日期',
			name : 'birthDate',
			index : 'birthDate',
			align : "center",
			width : 70
		}, {
			label : '所属支部',
			name : 'deptName',
			index : 'deptName',
			align : "center",
			width : 300
		}, {
			label : '身份证号',
			name : 'idCard',
			index : 'idCard',
			align : "center",
			hidden :true
		}, {
			label : '操作',
			name : '',
			formatter : actionFormatter,
			fixed : true,
			sortable : false,
			resize : false,
			align : "center",
			width : 150
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

function actionFormatter(cellvalue, options, rowObject) {
	var btnEdit = "<a href='../partymember/add.html?id=" + rowObject.id
			+ "'>编辑</a>";
	var btnDel = "<a href='javascript:delRecord(" + rowObject.id + ")'>删除</a>";

	return btnEdit + "&nbsp;" + btnDel;
}

function sexFormatter(cellvalue, options, rowObject) {
	var name = parent.getDictName("sex",cellvalue);
	console.log(name);
	return name;
}

function positionFormatter(cellvalue, options, rowObject) {
    var name = parent.getDictName("party_post",cellvalue);
    console.log(name);
    return name;

}

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
