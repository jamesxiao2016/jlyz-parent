// 添加默认值
//$.extend(true, $.fn.jqGrid, {
//	datatype: "json",
//	mtype: "POST"
//});

$.jgrid.defaults.styleUI = 'Bootstrap';

function styleCheckbox(table) {
	/**
	 * $(table).find('input:checkbox').addClass('ace') .wrap('<label />') .after('<span class="lbl align-top" />')
	 * 
	 * 
	 * $('.ui-jqgrid-labels th[id*="_cb"]:first-child') .find('input.cbox[type=checkbox]').addClass('ace') .wrap('<label
	 * />').after('<span class="lbl align-top" />');
	 */
}

function updateActionIcons(table) {
	/**
	 * var replacement = { 'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue', 'ui-ace-icon fa fa-trash-o' :
	 * 'ace-icon fa fa-trash-o red', 'ui-icon-disk' : 'ace-icon fa fa-check green', 'ui-icon-cancel' : 'ace-icon fa
	 * fa-times red' }; $(table).find('.ui-pg-div span.ui-icon').each(function(){ var icon = $(this); var $class =
	 * $.trim(icon.attr('class').replace('ui-icon', '')); if($class in replacement) icon.attr('class', 'ui-icon
	 * '+replacement[$class]); })
	 */
}

function updatePagerIcons(table) {
	var replacement = {
		'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function() {
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

		if($class in replacement)
			icon.attr('class', 'ui-icon ' + replacement[$class]);
	})
}

function enableTooltips(table) {
	// $('.navtable .ui-pg-button').tooltip({
	// container : 'body'
	// });
	// $(table).find('.ui-pg-div').tooltip({
	// container : 'body'
	// });
}
var timezone = -new Date().getTimezoneOffset() / 60;
function dateFormatter(cellvalue, options, rowObject) {
	if(cellvalue == undefined || cellvalue == "") {
		return "";
	}
	var date = new Date(cellvalue);
	date = date.setHours(date.getHours() + timezone);
	return new Date(date).Format("yyyy-MM-dd");
}

function datetimeFormatter(cellvalue, options, rowObject) {
	if(cellvalue == undefined || cellvalue == "") {
		return "";
	}
	var date = new Date(cellvalue);
	date = date.setHours(date.getHours() + timezone);
	return new Date(date).Format("yyyy-MM-dd hh:mm:ss");
}

function numberFormatter(cellvalue, options, rowObject) {
	if(cellvalue == undefined || cellvalue == "") {
		return "0";
	}

	return cellvalue;
}