package cn.dlbdata.dj.common.core.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 缓存管理类
 * 
 * @author xiaowei
 *
 */
public class CacheManager {
	protected static Logger logger = LoggerFactory.getLogger(CacheManager.class);
	
	private static CacheManager instance = new CacheManager();
	static Map<String, Object> cacheMap = new ConcurrentHashMap<>();
	// 创建默认config对象
	static HazelcastInstance hazelcastInstance = null;
	static String CACHE_NAME = "CACHE_MASTER";
	
	static {
		Config config = new Config();
		// 获取network元素<network></network>
        NetworkConfig netConfig = config.getNetworkConfig();
        logger.info("Default port:" + netConfig.getPort());
        System.out.println("Default port:" + netConfig.getPort());
        
        // 设置组网起始监听端口
        netConfig.setPort(7701);
        logger.info("Customer port:" + netConfig.getPort());
        System.out.println("Customer port:" + netConfig.getPort());
        // 获取join元素<join></join>
        JoinConfig joinConfig = netConfig.getJoin();
        // 获取multicast元素<multicast></multicast>
        MulticastConfig multicastConfig = joinConfig.getMulticastConfig();
        // 输出组播协议端口
        logger.info("MulticastPort:"+multicastConfig.getMulticastPort());
        System.out.println("MulticastPort:"+multicastConfig.getMulticastPort());
        // 禁用multicast协议
        multicastConfig.setEnabled(false);
        
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	}
	
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
