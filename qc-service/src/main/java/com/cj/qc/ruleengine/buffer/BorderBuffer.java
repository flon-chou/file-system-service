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
@ApiModel(description = "边界缓存类<bf、ne>")
public class BorderBuffer {

  //用于标识缓存类型
  private final String type="border";

  @ApiModelProperty("匹配内容")
  private String matchingContent;

  @ApiModelProperty("匹配结果是否存在")
  private boolean isValid;

  @ApiModelProperty("边界位置缓存类数组")
  private List<BorderLocationBuffer> borderLocationBuffers;

  @ApiModelProperty("匹配次数")
  private int count;

}
