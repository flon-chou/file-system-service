package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 位置缓存类
 * @date 2019/3/20 0:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "指定字符串在源字符串中成功匹配的位置信息，原则：左开右闭，如如abc，匹配内容为bc，则开始位置为1，结束位置为3")
public class LocationBuffer {

  @ApiModelProperty("开始位置")
  private Integer start;
  @ApiModelProperty("结束位置")
  private Integer end;
}
