package com.cj.qc.config.exception;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 自定义运行异常
 * @date Date : 2018/11/05 18:48
 */
public class CJRunningTimeException extends RuntimeException{

    private static final long serialVersionUID = 149844600658522289L;

    public CJRunningTimeException(String message) {
        super(message);
    }
}
