package cn.dlbdata.dj.common.core.exception;

/**
 * 异常处理类
 * 
 * @author xiaowei
 *
 */
public class DlbException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMsg;

	public DlbException() {

	}

	public DlbException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public DlbException(int errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
