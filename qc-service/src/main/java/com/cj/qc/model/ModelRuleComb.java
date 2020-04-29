package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author chenzhaowen
 * @description 模型与规则关系类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "模型与规则关系类")
@Table(name = "cj_qc_model_rule")
public class ModelRuleComb {

  @ApiModelProperty("模型与规则关系ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '模型与规则关系ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty("模型ID")
  @Column(name = "model_id",columnDefinition="bigint(20) COMMENT '模型ID'")
  private Long modelId;

  @ApiModelProperty("规则ID")
  @Column(name = "rule_id",columnDefinition="bigint(20) COMMENT '规则ID'")
  private Long ruleId;

  @ApiModelProperty("角色：A->坐席;B->客户")
  @Column(name = "role",columnDefinition="varchar(1) COMMENT '角色：A->坐席;B->客户'")
  private String role;

  @ApiModelProperty("次数")
  @Column(name = "number",columnDefinition="int(11) COMMENT '次数'")
  private Integer number;

  @ApiModelProperty("间距")
  @Column(name = "range",columnDefinition="int(11) COMMENT '间距'")
  private Integer range;

  @ApiModelProperty("分数")
  @Column(name = "score",columnDefinition="int(11) COMMENT '分数'")
  private Integer score;

}
