package com.cj.qc.base;

/**
 * @author songzhiheng
 * @date 2018/9/14
 */
public enum ResultCode {
    /**
     *成功
     */
    HTTP_CODE_SUCCESS(HttpCode.HTTP_CODE_SUCCESS,"成功"),
    /**
     *失败
     */
    HTTP_CODE_ERROR(HttpCode.HTTP_CODE_ERROR,"失败"),
    /**
     *无数据
     */
    HTTP_CODE_NO_DATA(HttpCode.HTTP_CODE_NO_DATA,"无数据"),
    /**
     *参数错误
     */
    HTTP_PARAMS_ERROR(HttpCode.HTTP_CODE_PARAMS_ERROR,"参数错误"),
    /**
     * 404
     */
    HTTP_404_CODE(HttpCode.HTTP_CODE_NOT_EXIST,"404找不到"),

    /**
     * 拒绝访问
     */
    HTTP_CODE_FORBIDDEN(HttpCode.HTTP_CODE_FORBIDDEN,"拒绝访问"),

    /**
     * 未经授权
     */
    HTTP_CODE_UNAUTHORIZED(HttpCode.HTTP_CODE_NOT_EXIST,"未经授权"),

    /**
     * 数据已存在
     */
    HTTP_CODE_DATA_EXIST(HttpCode.HTTP_CODE_DATA_EXIST,"数据已存在，勿重复添加"),
    /**
     * 上传失败
     */
    HTTP_CODE_UPLOAD_FAIL(HttpCode.HTTP_CODE_UPLOAD_FAIL,"上传失败"),

    /**
     * 数据过期
     */
    HTTP_CODE_OUT_DATA(HttpCode.HTTP_CODE_OUT_DATA,"数据过期");

    private String code;

    private String message;
    ResultCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

