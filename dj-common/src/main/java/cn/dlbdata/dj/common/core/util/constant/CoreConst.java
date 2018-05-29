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

	/**
	 * 返回值状态枚举
	 * 
	 * @author xw
	 *
	 */
	public static enum ResultCode {
		OK(200, "OK"), // 成功统一返回
		BadRequest(400, "Bad Request"), // 默认返回此错误码
		Unauthorized(401, "Unauthorized"), // 请求要求身份验证，未登录返回此错误码
		ParameterError(402, "Parameter Error"), // 参数错误统一返回此错误码
		Forbidden(403, "Forbidden"), // 资源不能访问
		NotFound(404, "Not Found"), // 资源不存在
		MethodNotAllowed(405, "Method Not Allowed"), // 方法不允许访问
		NotAcceptable(406, "Not Acceptable"), //
		ProxyAuthenticationRequired(407, "Proxy Authentication Required"), //
		RequestTimeout(408, "Request Timeout"), //
		InternalServerError(500, "500 Internal Server Error"), //
		ServiceUnavailable(503, "Service Unavailable");
		
		ResultCode(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		int code;
		String desc;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	}
}
