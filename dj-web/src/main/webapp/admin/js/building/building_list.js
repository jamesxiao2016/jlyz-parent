var grid_selector = "#grid-table";
var pager_selector = "#grid-pager";
var sId = "cn.dlbdata.dj.db.mapper.DjQueryMapper.queryBuildingList";
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
        "name" : getLikeVal($("#name").val()),
        "start" : $("#start").val(),
        "end" : $("#end").val()
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
    };
    jQuery(grid_selector).jqGrid({
        sortable: true,
        url : '../../api/v1/component/query',
        datatype : "json",
        rownumbers : true,
        colModel : [ {
            label : 'ID',
            name : 'id',
            index : 'id',
            hidden : true
        }, {
            label : '大楼名称',
            name : 'name',
            index : 'name',
            width :150
        }, {
            label : '大楼编号',
            name : 'code',
            index : 'code',
            width :70
        }, {
            label : '所属片区',
            name : 'sectionName',
            index : 'sectionName',
            width :150
        }, {
            label : '总层数',
            name : 'floorNum',
            index : 'floorNum',
            align : "center",
            width :70
        }, {
            label : '总人数',
            name : 'peopleNum',
            index : 'peopleNum',
            align : "center",
            width :70
        }, {
            label : '党员数',
            name : 'partyNum',
            index : 'partyNum',
        }, {
            label : '地址',
            name : 'address',
            index : 'address',
            align : "center",
            width :150
        }, {
            label : '状态',
            name : 'status',
            index : 'status',
            align : "center",
            width : 70
        }, {
            label : '创建时间',
            name : 'createTime',
            index : 'createTime',
            align : "center",
            formatter : datetimeFormatter,
            width :70
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
        rowList : [ 10, 20, 30 ],
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
