package com.cj.qc.config.exception;


import com.cj.qc.base.ApiResult;
import com.cj.qc.base.ResultCode;
import com.cj.qc.base.ResultUtil;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 统一异常处理类
 * @date Date : 2018/09/10 15:57
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 全局异常处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult jsonErrorHandler(HttpServletRequest req, Exception e) {
        //log.error("【系统异常】{}",e.fillInStackTrace());
        log.error("【系统异常】{}", Throwables.getStackTraceAsString(e));
        return ResultUtil.getResponse(ResultCode.HTTP_CODE_ERROR.getCode(),"系统异常，请联系管理员！");
    }

    /**
     * pojo实体属性校验异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ApiResult jsonErrorHandler(HttpServletRequest req, ConstraintViolationException e) {
        log.warn("ConstraintViolationException [pojo实体属性校验异常！]"+e.getConstraintViolations().toString());
        ConstraintViolation cv = e.getConstraintViolations().iterator().next();
        return ResultUtil.getResponse(ResultCode.HTTP_PARAMS_ERROR.getCode(),cv.getMessage());
    }

    /**
     * 参数错误异常处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ApiResult jsonErrorHandler(HttpServletRequest req, IllegalArgumentException e) {
        log.warn("IllegalArgumentException [参数错误异常！]");
        return ResultUtil.getResponse(ResultCode.HTTP_PARAMS_ERROR.getCode(),"参数错误异常！");
    }

    /**
     * 自定义系统运行异常
     * @param e
     * @return
     */
    @ExceptionHandler(value=CJRunningTimeException.class)
    @ResponseBody
    public ApiResult traingExceptionHandle(CJRunningTimeException e) {
        log.warn("CJRunningTimeException [系统运行异常！]",e.fillInStackTrace());
        return ResultUtil.getResponse(ResultCode.HTTP_CODE_ERROR.getCode(),e.getMessage());
    }

    /**
      * 自定义绑定参数校验异常<p>需要配合Spring validation验证框架使用，controller层校验接口需要加上BindingResult参数 </p>
     * @param e
     * @return
     */
    @ExceptionHandler(value=CJBindingResultError.class)
    @ResponseBody
    public ApiResult traingExceptionHandle(CJBindingResultError e) {
        BindingResult result = e.getResult();
		result.getAllErrors().stream().forEach(
                error -> {
                    FieldError fieldError = (FieldError) error;
                    log.info("错误字段：{} -> 错误信息：{}",fieldError.getField(),fieldError.getDefaultMessage());
                });
        return  ResultUtil.getResponse(result);
    }



}
