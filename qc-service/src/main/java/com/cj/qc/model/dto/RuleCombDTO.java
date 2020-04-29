package com.cj.qc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author chenzhaowen
 * @description 规则组合类：规则与模型中规则设置参数组合
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "规则组合类：规则与模型中规则设置参数组合")
public class RuleCombDTO {

  @ApiModelProperty("规则ID")
  private Long id;

  @ApiModelProperty("规则分类标识")
  private Long ruleTypeId;

  @ApiModelProperty("规则名称")
  private String ruleName;

  @ApiModelProperty("检测逻辑")
  private String ruleLogic;

  @ApiModelProperty("状态（0->停用；1->启用）")
  private Boolean status;

  @ApiModelProperty("停用时间")
  private Timestamp stopTime;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("模型ID")
  private Long modelId;

  @ApiModelProperty("角色：A->坐席;B->客户")
  private String role;

  @ApiModelProperty("次数")
  private Integer number;

  @ApiModelProperty("间距")
  private Integer range;

  @ApiModelProperty("分数")
  private Integer score;
}
