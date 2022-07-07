package com.ability_plus.system;

import com.ability_plus.system.entity.CheckException;
import com.ability_plus.utils.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author Yuzhe Ma
 * @date 2018/11/12
 */
@ControllerAdvice
public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理 Exception 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RestResponse exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        logger.error("服务错误:", e);
        return RestResponse.error(400L,e.getMessage());
    }

    /**
     * 处理 BusinessException 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CheckException.class)
    public RestResponse businessExceptionHandler(HttpServletRequest httpServletRequest, CheckException e) {
//        logger.info("业务异常。code:" + e.getCode() + "msg:" + e.getMsg());
        return RestResponse.error(e.getCode(), e.getMessage());
    }
}