package cn.dlbdata.dangjian.common.core.web.vo;

import java.io.Serializable;

public class MsgVo<T> implements Serializable {
	private static final long serialVersionUID = 8865849235876987173L;
	private String result;
	private String msg;
	private String value;
	private T data;

	public MsgVo(String result) {
		this.result = result;
	}

	public MsgVo(String result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public MsgVo(String result, String msg, String value) {
		this.result = result;
		this.msg = msg;
		this.value = value;
	}

	public MsgVo(String result, String msg, String value, T data) {
		this.result = result;
		this.msg = msg;
		this.value = value;
		this.data = data;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
