package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 规则解析结果转换时间类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "规则解析结果转换时间类")
public class RuleResTimeBuffer {

  @ApiModelProperty("开始时间")
  private String beginTime;

  @ApiModelProperty("结束时间")
  private String endTime;
}
