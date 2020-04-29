package com.cj.qc.ruleengine.buffer;

import com.cj.qc.ruleengine.config.RuleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 角色缓存类
 * @date 2019/3/26 16:02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "角色缓存类：如 A-w1 B-w1")
public class RoleBuffer {
  @ApiModelProperty("角色枚举：RuleEnum.A 或 RuleEnum.B")
  private RuleEnum ruleEnum;
  @ApiModelProperty("源匹配字符串：如 A-w1 或 w1")
  private String sourceRegex;
  @ApiModelProperty("子匹配字符串：如w1")
  private String childRegex;
}
