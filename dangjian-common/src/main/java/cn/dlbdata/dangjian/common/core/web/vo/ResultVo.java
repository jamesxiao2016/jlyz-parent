package cn.dlbdata.dangjian.common.core.web.vo;

import java.io.Serializable;

public class ResultVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4468686331988970001L;
	protected int result = 200;
	protected String reason;
	protected String method;

	public ResultVo() {

	}

	public ResultVo(int result, String reason) {
		this.reason = reason;
		this.result = result;
	}

	public ResultVo(String reason) {
		this.reason = reason;
	}

	public ResultVo(int result) {
		this.result = result;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
