package com.cj.qc.ruleengine.buffer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 边界角色缓存类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "边界角色缓存类<bf、ne>")
public class BorderRoleBuffer {

  @ApiModelProperty("左匹配内容")
  private RoleBuffer leftRoleBuffer;

  @ApiModelProperty("右匹配内容")
  private RoleBuffer rightRoleBuffer;

}
