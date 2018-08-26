package com.liucan.taskmanager.common.advice;

import com.liucan.taskmanager.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author liucan
 * @date 2018/8/26
 * @brief
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e) {
        log.error("[异常捕获]捕获到异常", e);
        return CommonResponse.error("系统错误");
    }
}
