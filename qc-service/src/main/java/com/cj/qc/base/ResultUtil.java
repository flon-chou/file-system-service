package com.cj.qc.base;

import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 结果工具类
 * @date Date : 2018/09/10 16:08
 */
public class ResultUtil {

    /**
     * 结果
     * @param resultCode 返回状态码及信息
     * @return
     */
    public static ApiResult getResponse(ResultCode resultCode){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(resultCode.getCode());
        apiResult.setMessage(resultCode.getMessage());
        return apiResult;
    }

    /**
     * 结果
     * @param  errors 校验参数错误的对象
     * @return
     */
    public static ApiResult getResponse(BindingResult errors){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(ResultCode.HTTP_PARAMS_ERROR.getCode());
        apiResult.setMessage(errors.getFieldError().getDefaultMessage());
        return apiResult;
    }

    /**
     * 结果
     * @param code 返回状态码
     * @param message 自定义返回信息
     * @return
     */
    public static ApiResult getResponse(String code,String message){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

    /**
     * 带数据的结果
     * @param resultCode 返回状态码及信息
     * @param o    数据
     * @return
     */
    public static ApiDataResult getResponse(ResultCode resultCode, Object o){
        ApiDataResult apiDataResult = new ApiDataResult(resultCode.getCode(),resultCode.getMessage(),o);
        return apiDataResult;
    }

    /**
     * 自定义信息输入
     * @param resultCode 返回状态码及信息
     * @param message   自定义信息
     * @return
     */
    public static ApiDataResult getResponse(ResultCode resultCode, String message){
        ApiDataResult apiDataResult = new ApiDataResult(resultCode.getCode(),message);
        return apiDataResult;
    }

    /**
     * 带数据的结果
     * @param code 返回状态码
     * @param message 自定义返回信息
     * @param o    数据
     * @return
     */
    public static ApiDataResult getResponse(String code,String message,Object o){
        ApiDataResult apiDataResult = new ApiDataResult(code,message,o);
        return apiDataResult;
    }

    /**
     *  PrintWriter输出，不带数据
     * @param response
     */
    public static void printWriter(HttpServletResponse response, ResultCode resultCode) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(ResultUtil.getResponse(resultCode));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            pw.flush();
            pw.close();
        }
    }

    public static void printWriter(HttpServletResponse response, String code, String msg) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(ResultUtil.getResponse(code,msg));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            pw.flush();
            pw.close();
        }
    }

}
