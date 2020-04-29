package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 用于规则解析的源文件缓存类
 * @date 2019/3/26 10:06
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "用于规则解析的源文件缓存类")
public class SourceBuffer {
  @ApiModelProperty("源文件字符串")
  private String source;
  @ApiModelProperty("源文件字符串每个字映射后的集合")
  private List<String> sourceList;
  @ApiModelProperty("源文件字符串每个字所对应时间映射后的集合")
  private List<String> timeList;
  @ApiModelProperty("源文件字符串每个字所对应角色映射后的集合")
  private List<String> roleList;
}
