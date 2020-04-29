package com.cj.qc.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @author chenzhaowen
 * @description json工具类
 * @date 2019/4/22 17:45
 */
public class JsonTool {

  /**
   * List<T> 转 json 保存到数据库
   */
  public static <T> String listToJson(List<T> ts) {
    String jsons = JSON.toJSONString(ts);
    return jsons;
  }

  /**
   * json 转 List<T>
   */
  public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
    @SuppressWarnings("unchecked")
    List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
    return ts;
  }
}
