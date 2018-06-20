var dictMap = new Map();

$(function() {
	initDict();
});

function initDict() {

	$.get("../admin/getAllDictList", function(data) {
		if(data.code == 1000) {
			data.data.forEach(function(item, idx) {
				var tMap = dictMap[item.text];
				if(!tMap) {
					tMap = new Map();
				}
				tMap[item.id] = item.name;
				dictMap[item.text] = tMap;
			});
			console.log(dictMap);
		}
	});
}

function getDictName(dictType, dictCode) {
	var dictName = dictMap[dictType][dictCode];
	if(!dictName) {
		dictName = "";
	}
	return dictName;
}

function getDictListByDictType(dictType) {
	var dMap = new Map();
	$.get("../admin/getDictListByDictType?dictType=" + dictType, function(
		data) {
		if(data.code == 1000) {
			data.data.forEach(function(item, idx) {
				dMap[item.id] = item.text;
			});
		}
	});

	return dMap;
}