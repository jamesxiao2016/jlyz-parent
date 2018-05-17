package cn.dlbdata.dangjian.common.core.web.vo;

public class BaseVo {
	private String result;
	private String msg;

	public BaseVo() {

	}

	public BaseVo(String result) {
		this.result = result;
	}

	public BaseVo(String result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
