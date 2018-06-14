package cn.dlbdata.dj.common.core.exception;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public final ResultVo handleBusinessException(BusinessException ex) {
        return new ResultVo(ex.getErrorCode(),ex.getMessage());
    }
}
