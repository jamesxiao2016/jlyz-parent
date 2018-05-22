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
	 * 返回值状态
	 * 
	 * @author SW
	 *
	 */
	public static enum ResultCode {
		OK(200, "OK"), BadRequest(400, "Bad Request"), Unauthorized(401, "Unauthorized"), ParameterError(402,
				"Parameter Error"), Forbidden(403, "Forbidden"), NotFound(404, "Not Found"), MethodNotAllowed(405,
						"Method Not Allowed"), InternalServerError(500,
								"500 Internal Server Error"), ServiceUnavailable(503, "Service Unavailable");
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
