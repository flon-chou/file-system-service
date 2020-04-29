package com.cj.qc.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: Restful API 接口返回响应类
 * @author : Flon
 * @date Date : 2018/08/28 13:40:00
 * @version V1.0
 */
@ApiModel(description = "返回响应类，带数据")
public class ApiDataResult<T> extends ApiResult{

    @ApiModelProperty(value = "返回数据")
    private T   data;

    public ApiDataResult(){}

    public ApiDataResult(String code, String message) {
        super(code,message);
    }

    public ApiDataResult(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /*@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(this.getCode());
        sb.append(",\"message\":\"")
                .append(this.getMessage()).append('\"');
        sb.append(",\"data\":{");
        Map<String,Object> map = ReflectUtil.reflectObject(data);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            sb.append("\""+entry.getKey()+"\":");
            if(entry.getValue() instanceof  Timestamp){
                Timestamp timestamp = (Timestamp) entry;
                sb.append(entry.getValue() == null ? null : "\"" + timestamp.getTime() + '\"');
            }else {
                sb.append(entry.getValue() == null ? null : "\"" + entry.getValue().toString() + '\"');
            }
            if (it.hasNext()){
                sb.append(",");
            }
        }
        sb.append('}');
        sb.append('}');
        return sb.toString();
    }*/
}
