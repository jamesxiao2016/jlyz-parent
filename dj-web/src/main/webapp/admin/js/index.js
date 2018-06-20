var dictMap = new Map();

$(function() {
	initDict();
});

/**
 * 初始化，读取所有的字典表信息，放到map中
 * 
 * @returns
 */
function initDict() {
	$.get("../admin/getAllDictList", function(data) {
		if (data.code == 1000) {
			data.data.forEach(function(item, idx) {
				var tMap = dictMap[item.text];
				if (!tMap) {
					tMap = new Map();
				}
				tMap[item.id] = item.name;
				dictMap[item.text] = tMap;
			});
		}
	});
}

/**
 * 根据dictType和dictCode从所有的字典表中读取dictName
 * 
 * @param dictType
 * @param dictCode
 * @returns
 */
function getDictName(dictType, dictCode) {
	var dictName = dictMap[dictType][dictCode];
	if (!dictName) {
		dictName = "";
	}
	return dictName;
}

/**
 * 根据字典表类型获取所有的字典列表
 * 
 * @param dictType
 * @returns
 */
function getDictListByDictType(dictType) {
	var dMap = new Map();
	var url = "../admin/getDictListByDictType?dictType=" + dictType;
	$.get(url, function(data) {
		if (data.code == 1000) {
			data.data.forEach(function(item, idx) {
				dMap[item.id] = item.text;
			});
		}
	});

	return dMap;
}