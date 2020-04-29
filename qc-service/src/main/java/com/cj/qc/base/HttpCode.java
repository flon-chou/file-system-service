package com.cj.qc.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义接口返回码
 * @author flonc
 */
@ApiModel(value = "返回码")
public class HttpCode {
    /**
     * 成功
     */
    @ApiModelProperty(value = "成功")
    public final static String HTTP_CODE_SUCCESS = "1";
    /**
     *返回结果错误代码
     */
    @ApiModelProperty(value = "错误")
    public final static String HTTP_CODE_ERROR = "-1";
    /**
     *未经授权代码
     */
    @ApiModelProperty(value = "未经授权")
    public final static String HTTP_CODE_UNAUTHORIZED = "401";
    /**
     *参数错误代码
     */
    @ApiModelProperty(value = "参数错误")
    public final static String HTTP_CODE_PARAMS_ERROR = "2";
    /**
     *拒绝访问代码
     */
    @ApiModelProperty(value = "拒绝访问")
    public final static String HTTP_CODE_FORBIDDEN = "403";
    /**
     * 无数据代码
     */
    @ApiModelProperty(value = "无数据")
    public final static String HTTP_CODE_NO_DATA = "0";
    /**
     * 请求不存在
     */
    @ApiModelProperty(value = "请求不存在")
    public final static String HTTP_CODE_NOT_EXIST = "404";
    /**
     * 数据已存在
     */
    @ApiModelProperty(value = "数据已存在")
    public final static String HTTP_CODE_DATA_EXIST = "-2";
    /**
    * 数据过期
    */
    @ApiModelProperty(value = "数据过期")
    public final static String HTTP_CODE_OUT_DATA = "-3";
    /**
     * 上传失败
     */
    @ApiModelProperty(value = "上传失败")
    public final static String HTTP_CODE_UPLOAD_FAIL = "-4";
    /**
     * Token失效
     */
    @ApiModelProperty(value = "Token失效")
    public final static String HTTP_TOKEN_EXPIRED = "5001";
    /**
     * Token签名密钥不正确
     */
    @ApiModelProperty(value = "Token签名密钥不正确")
    public final static String HTTP_TOKEN_SIGN = "5002";
    /**
     * Token格式错误
     */
    @ApiModelProperty(value = "Token格式错误")
    public final static String HTTP_TOKEN_MALFORMED = "5003";
    /**
     * 不支持该Token
     */
    @ApiModelProperty(value = "不支持该Token")
    public final static String HTTP_TOKEN_UNSUPPORTED = "5004";
}
