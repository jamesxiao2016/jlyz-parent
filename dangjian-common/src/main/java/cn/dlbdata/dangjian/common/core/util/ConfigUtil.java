package cn.dlbdata.dangjian.common.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件工具类
 * 
 * @author caspar.chen
 * @version 1.0
 */
public class ConfigUtil {
	// 日志器
	protected static final Logger logger = Logger.getLogger(ConfigUtil.class);

	private static Properties props = new Properties();

	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

	public static void setProps(Properties p) {
		props = p;
	}
}
