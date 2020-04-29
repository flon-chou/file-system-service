package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 除外关系缓存类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "除外关系缓存类")
public class ExceptBuffer {

  //用于标识缓存类型
  private final String type="except";

  @ApiModelProperty("匹配内容")
  private String matchingContent;

  @ApiModelProperty("匹配结果是否存在")
  private boolean isValid;

  @ApiModelProperty("左匹配内容结果")
  private ContainBuffer leftContainBuffer;

  @ApiModelProperty("右匹配内容结果")
  private ContainBuffer rightContainBuffer;

  @ApiModelProperty("匹配次数")
  private int count;
}
