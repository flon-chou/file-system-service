package com.cj.qc.ruleengine.buffer;

import com.cj.qc.model.MachineCheckDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 表达式缓存类
 * @date 2019/3/25 15:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "存储操作数栈与操作符计算后缓存")
public class CommonExpressionBuffer {
  @ApiModelProperty("表达式缓存类:用于分离表达式后的数据信息保存")
  private ExpressionBuffer expressionBuffer;
  @ApiModelProperty("规则解析结果详情类")
  private MachineCheckDetail machineCheckDetail;
}
