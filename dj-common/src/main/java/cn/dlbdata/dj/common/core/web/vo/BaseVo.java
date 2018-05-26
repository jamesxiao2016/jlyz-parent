package cn.dlbdata.dj.common.core.web.vo;

import java.io.Serializable;

public class BaseVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 错误码
	protected int code = 200;
	// 错误消息
	protected String msg;

	public BaseVo() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
