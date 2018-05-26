package cn.dlbdata.dj.common.core.web.vo;

/**
 * 返回结果对象
 * 
 * @author xiaowei
 *
 * @param <T>
 */
public class ResultVo<T> extends BaseVo {

	private static final long serialVersionUID = -4468686331988970001L;
	// 返回的数据（code == 200时有数据）
	protected T data;

	public ResultVo() {

	}

	public ResultVo(int code) {
		this.code = code;
	}

	public ResultVo(String msg) {
		this.msg = msg;
	}

	public ResultVo(int code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public ResultVo(int code, String msg, T data) {
		this.msg = msg;
		this.code = code;
		this.data = data;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
