package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 包含缓存类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "包含缓存类：指定字符串在源字符串中成功匹配的位置信息，原则：左开右闭，如如abc，匹配内容为bc，则开始位置为1，结束位置为3")
public class ContainBuffer {

  //用于标识缓存类型
  private final String type="contain";

  @ApiModelProperty("匹配内容")
  private String matchingContent;

  @ApiModelProperty("匹配结果是否存在")
  private boolean isValid;

  @ApiModelProperty("位置缓存类数组：位置为左开右闭，如abc，匹配内容为bc，则开始位置为1，结束位置为3")
  private List<LocationBuffer> locationBuffers;

  @ApiModelProperty("匹配次数")
  private int count;

  /**
   * 根据该类的isValid属性转换成ArrayList<Boolean>
   * @param containBuffers
   * @return
   */
  public static ArrayList<Boolean> toBoolArray(List<ContainBuffer> containBuffers){
    ArrayList<Boolean> booleans=new ArrayList<>();
    containBuffers.forEach(c->{
      booleans.add(c.isValid());
    });
    return booleans;
  }

  /**
   * 根据该类的isValid属性转换成ArrayList<Boolean>
   * @param containBuffers
   * @return
   */
  public static ArrayList<ContainBuffer> removeFalse(List<ContainBuffer> containBuffers){
    ArrayList<ContainBuffer> trueBuffers=new ArrayList<>();
    containBuffers.forEach(c->{
      if(c.isValid){
        trueBuffers.add(c);
      }
    });
    return trueBuffers;
  }
}
