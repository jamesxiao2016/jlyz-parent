package cn.dlbdata.dj.common.core.db.annotation;

public @interface MyDataSource {
	String name() default MyDataSource.master;
	public static String master = "master";
	public static String slave = "slave";
	public static String slave1 = "slave1";
	public static String slave2 = "slave2";
}
