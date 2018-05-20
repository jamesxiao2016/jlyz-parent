package cn.dlbdata.dj.common.core.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理类
 * 
 * @author xiaowei
 *
 */
public class CacheManager {
	private static CacheManager instance = new CacheManager();
	static Map<String, Object> cacheMap = new ConcurrentHashMap<>();

	private CacheManager() {

	}

	public static CacheManager getInstance() {
		return instance;
	}

	public boolean put(String key, Object value) {
		if (key == null || key.trim().length() == 0) {
			return false;
		}
		Object v = cacheMap.put(key, value);
		return v == null ? false : true;
	}

	public Object get(String key) {
		if (key == null || key.trim().length() == 0) {
			return null;
		}
		return cacheMap.get(key);
	}

	public boolean remove(String key) {
		if (key == null || key.trim().length() == 0) {
			return false;
		}
		Object value = cacheMap.remove(key);

		return value == null ? false : true;
	}
}
