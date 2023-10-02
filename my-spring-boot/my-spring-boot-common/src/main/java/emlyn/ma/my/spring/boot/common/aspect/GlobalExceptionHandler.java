package emlyn.ma.my.spring.boot.common.aspect;

import emlyn.ma.my.spring.boot.common.CommonException;
import emlyn.ma.my.spring.boot.common.CommonResponse;
import emlyn.ma.my.spring.boot.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public CommonResponse<?> handleException(CommonException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.failure(exception.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.failure(ErrorCode.UNKNOWN_ERROR);
    }

}
