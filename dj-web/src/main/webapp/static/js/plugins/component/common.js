String.prototype.trim = function(tag) {
	if(!tag) {
		tag = '\\s';
	} else {
		if(tag == '\\') {
			tag = '\\\\';
		} else if(tag == ',' || tag == '|' || tag == ';') {
			tag = '\\' + tag;
		} else {
			tag = '\\s';
		}
	}
	eval('var reg=/(^' + tag + '+)|(' + tag + '+$)/g;');
	return this.replace(reg, '');
};

// 字符串截取后面加入...
String.prototype.interceptString = function(len) {
	if(this.length > len) {
		return this.substring(0, len) + "...";
	} else {
		return this;
	}
}
// 将一个字符串用给定的字符变成数组
String.prototype.toArray = function(tag) {
	if(this.indexOf(tag) != -1) {
		return this.split(tag);
	} else {
		if(this != '') {
			return [this.toString()];
		} else {
			return [];
		}
	}
}
// 只留下数字(0123456789)
String.prototype.toNumber = function() {
	return this.replace(/\D/g, "");
}
// 保留中文
String.prototype.toCN = function() {
	var regEx = /[^\u4e00-\u9fa5\uf900-\ufa2d]/g;
	return this.replace(regEx, '');
}
// 转成int
String.prototype.toInt = function() {
	var temp = this.replace(/\D/g, "");
	return isNaN(parseInt(temp)) ? this.toString() : parseInt(temp);
}
// 是否是以XX开头
String.prototype.startsWith = function(tag) {
	return this.substring(0, tag.length) == tag;
}
// 是否已XX结尾
String.prototype.endWith = function(tag) {
	return this.substring(this.length - tag.length) == tag;
}
// StringBuffer
var StringBuffer = function() {
	this._strs = new Array;
};
StringBuffer.prototype.append = function(str) {
	this._strs.push(str);
};
StringBuffer.prototype.toString = function() {
	return this._strs.join("");
};
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+": this.getMonth() + 1, // 月份
		"d+": this.getDate(), // 日
		"h+": this.getHours(), // 小时
		"m+": this.getMinutes(), // 分
		"s+": this.getSeconds(), // 秒
		"q+": Math.floor((this.getMonth() + 3) / 3), // 季度
		"S": this.getMilliseconds()
		// 毫秒
	};
	if(/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
Array.prototype.serializeObject = function(lName) {
	var o = {};
	$t = this;

	for(var i = 0; i < $t.length; i++) {
		for(var item in $t[i]) {
			o[lName + '[' + i + '].' + item.toString()] = $t[i][item].toString();
		}
	}
	return o;
};

function datetimeFormatter(value, row, index) {
	if(value == undefined || value == "") {
		return "";
	}

	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
}

function dateFormatter(value, row, index) {
	if(value == undefined || value == "") {
		return "";
	}

	return new Date(value).Format("yyyy-MM-dd");
}

function S4() {
	return(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}
// 生成32位的guid
function guid() {
	return(S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4());
}

function replaceAll(str, oldStr, newStr) {
	if(str) {
		return str.toString().replace(new RegExp(oldStr, "gm"), newStr);
	}
	return str;
}

function gotoUrl(url) {
	// if (url.indexOf("?") == -1) {
	// url += "?" + token;
	// }
	// else {
	// url += "&" + token;
	// }
	location.href = url;
}

function getHeight() {
	var height = $(window).height() - 180;
	return height;
}

function isFunction(fn) {
	return Object.prototype.toString.call(fn) === '[object Function]';
}

function htmlEscape(s) {
	return(s + '').replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/'/g, '&#039;').replace(
		/"/g, '&quot;').replace(/\n/g, '<br />');
}

function getLikeVal(val) {
	if(val != "") {
		return '%' + val + '%';
	}

	return val;
}

function getUrlParam(name) {
	// 构造一个含有目标参数的正则表达式对象
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	// 匹配目标参数
	var r = window.location.search.substr(1).match(reg);
	// 返回参数值
	if(r != null)
		return unescape(r[2]);
	return null;
}

function getLikeVal(val) {
	if(val != "") {
		return '%' + val + '%';
	}

	return val;
}

function storeItem(key, value) {
	if(isLocalStorageSupported()) {
		window.localStorage.removeItem(key);
		window.localStorage.setItem(key, value);
	} else {
		$.cookie(key, value);
	}
}

function getStoreItem(key) {
	if(isLocalStorageSupported()) {
		return window.localStorage.getItem(key);
	} else {
		return $.cookie(key);
	}
}

function isLocalStorageSupported() {
    var testKey = 't';
    var storage = window.localStorage;
    try {
        storage.setItem(testKey, 't');
        storage.removeItem(testKey);
        return true;
    } catch (error) {
        return false;
    }
} 

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};