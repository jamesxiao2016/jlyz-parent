package cn.dlbdata.dj.common.core.util;

import java.util.Collection;

public class ArrayUtil {
	public static boolean isEmpty(Collection<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}

		return false;
	}
	
	public static boolean isNotEmpty(Collection<?> list) {
		return !isEmpty(list);
	}
}
