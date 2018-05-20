package cn.dlbdata.dj.web.utils;

import java.util.HashMap;
import java.util.Map;

public class CacheUtil {
	static Map<String, Object> USER_MAP = new HashMap<>();

	public static void put(String key, Object value) {
		USER_MAP.put(key, value);
	}

	public static Object get(String key) {
		if (key == null) {
			return null;
		}
		return USER_MAP.get(key);
	}
}
