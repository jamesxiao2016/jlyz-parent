package cn.dlbdata.dj.common.core.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 缓存管理类
 * 
 * @author xiaowei
 *
 */
public class CacheManager {
	private static CacheManager instance = new CacheManager();
	static Map<String, Object> cacheMap = new ConcurrentHashMap<>();
	static HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	static String CACHE_NAME = "CACHE_MASTER";
	
	private CacheManager() {

	}

	public static CacheManager getInstance() {
		return instance;
	}

	public boolean put(String key, Object value) {
		if (key == null || key.trim().length() == 0) {
			return false;
		}
		//Object v = cacheMap.put(key, value);
		Object v = hazelcastInstance.getMap(CACHE_NAME).put(key, value);
		return v == null ? false : true;
	}

	public Object get(String key) {
		if (key == null || key.trim().length() == 0) {
			return null;
		}
		//cacheMap.get(key);
		Object value = hazelcastInstance.getMap(CACHE_NAME).get(key);
		return value;
	}

	public boolean remove(String key) {
		if (key == null || key.trim().length() == 0) {
			return false;
		}
		//Object value = cacheMap.remove(key);
		Object value = hazelcastInstance.getMap(CACHE_NAME).remove(key);
		return value == null ? false : true;
	}
}
