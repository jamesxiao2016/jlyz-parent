package cn.dlbdata.dj.constant;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;

public class DlbConstant extends CoreConst {

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
