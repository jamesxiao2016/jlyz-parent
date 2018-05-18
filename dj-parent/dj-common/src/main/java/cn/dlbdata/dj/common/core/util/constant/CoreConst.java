package cn.dlbdata.dj.common.core.util.constant;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

public class CoreConst {
	public static final String SUCCESS = "1";
	public static final String FAIL = "0";
	public static final String ERROR = "-1";
	public static final String MSG_FAIL_PARAM = "参数错误";
	public static final String MSG_FAIL = "操作失败";
	public static final String SESSION_USER = "CURR_USER";
	public static final String SESSION_ADMIN_USER = "ADMIN_USER";
	public static final String SESSION_VAL_CODE = "VAL_CODE";
	
	
	/**
	 * 项目物理路径
	 */
	public static String WEB_PROJECT_PATH = "";

	/**
	 * 工程物理路径
	 */
	public static String WEB_PHYSICAL_PATH = "";

	/**
	 * servlet上下文
	 */
	public static ServletContext servletContext = null;

	/**
	 * spring上下文
	 */
	public static ApplicationContext springContext = null;

}
