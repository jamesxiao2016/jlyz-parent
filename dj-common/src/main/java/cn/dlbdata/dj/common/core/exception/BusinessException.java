package cn.dlbdata.dj.common.core.exception;

public class BusinessException extends RuntimeException{
    private Integer errorCode;

    public BusinessException(){
        super();
    }
    public BusinessException(String message,Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}