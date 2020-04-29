package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 一个完整匹配结果的逗号位置缓存类
 * @date 2019/3/20 0:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "一个完整匹配结果的逗号位置缓存类")
public class MultiCommaLocationBuffer {

  @ApiModelProperty("全量匹配内容")
  private String matchingContent;
  @ApiModelProperty("开始位置")
  private Integer start;
  @ApiModelProperty("结束位置")
  private Integer end;
  @ApiModelProperty("结束位置")
  private List<CommaLocationBuffer> commaLocationBuffers;
}
