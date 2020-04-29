package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 规则解析结果转换json类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "规则解析结果转换json类")
public class RuleResJsonBuffer {

  @ApiModelProperty("是否通过")
  private Integer isPass;

  @ApiModelProperty("匹配结果（json字符串）")
  private String result;

}
