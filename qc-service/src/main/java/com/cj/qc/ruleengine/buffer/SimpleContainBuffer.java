package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 用于单一运算符（and、or）的结果集
 * @date 2019/3/21 16:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "用于单一运算符（and、or）的结果集")
public class SimpleContainBuffer {
  @ApiModelProperty("运算符：and、or")
  private String operator;
  @ApiModelProperty("匹配表达式")
  private String matchStr;
  @ApiModelProperty("匹配后的位置等信息")
  private List<ContainBuffer> containBuffers;
  @ApiModelProperty("匹配后的boolean信息")
  private Boolean boolRes;
}
