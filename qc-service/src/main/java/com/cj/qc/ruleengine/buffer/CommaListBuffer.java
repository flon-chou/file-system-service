package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 边界缓存类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "逗号缓存类<{w1,w2,w3}>")
public class CommaListBuffer {

  @ApiModelProperty("匹配内容")
  private List<String> matchingContent;

  @ApiModelProperty("匹配结果是否存在")
  private boolean isValid;

  @ApiModelProperty("一个完整匹配结果的逗号位置缓存类")
  private List<MultiCommaLocationBuffer> multiCommaLocationBuffers;

  @ApiModelProperty("匹配次数")
  private int count;

  public CommaListBuffer(List<String> matchingContent, boolean isValid) {
    this.matchingContent = matchingContent;
    this.isValid = isValid;
  }
}
