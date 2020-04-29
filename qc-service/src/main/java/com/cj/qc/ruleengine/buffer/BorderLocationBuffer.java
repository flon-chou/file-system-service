package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 边界位置缓存类
 * @date 2019/3/20 0:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "边界位置缓存类")
public class BorderLocationBuffer {

  @ApiModelProperty("公式左边内容匹配位置")
  private LocationBuffer leftBorder;

  @ApiModelProperty("公式右边内容匹配位置")
  private LocationBuffer rightBorder;

  @ApiModelProperty("公式两边内容匹配位置")
  private LocationBuffer bothBorder;
}
