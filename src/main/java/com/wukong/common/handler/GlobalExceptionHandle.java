package com.wukong.common.handler;

import com.wukong.common.utils.ResultWrapper;
import com.wukong.common.utils.TokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

/**
 * Created By WuKong on 2022/8/2 21:37
 **/
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ArithmeticException.class)
    public ResultWrapper customException() {
        return ResultWrapper.builder().code(301).msg("统一异常").build();
    }

    /**
     * 自定义token异常
     * @param e
     * @return
     */
    @ExceptionHandler(TokenException.class)
    public ResultWrapper loginException(Exception e) {
        return ResultWrapper.getErrorBuilder(501,e.getMessage());
    }
}
