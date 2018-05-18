package cn.dlbdata.dj.common.core.db;

public class DataSourceHolder {
	// 线程本地环境
	private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

	// 设置数据源
	public static void setDataSource(String dataSourceName) {
		dataSources.set(dataSourceName);
	}

	// 获取数据源
	public static String getDataSource() {
		return (String) dataSources.get();
	}

	// 清除数据源
	public static void clearDataSource() {
		dataSources.remove();
	}
}
