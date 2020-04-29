package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 规则解析结果转换类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "规则解析结果转换类")
public class RuleResBuffer {

  @ApiModelProperty("匹配内容")
  private String content;

  @ApiModelProperty("时间集合")
  private List<RuleResTimeBuffer> times;
}
