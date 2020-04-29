package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author chenzhaowen
 * @description 规则类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "规则类")
@Table(name = "cj_qc_rule")
public class Rule extends BasicPojo{

  @ApiModelProperty("规则ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '规则ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty("规则分类标识")
  @Column(name = "rule_type_id",columnDefinition="bigint(20) COMMENT '规则分类ID'")
  private Long ruleTypeId;

  @ApiModelProperty("规则名称")
  @Column(name = "rule_name",columnDefinition="varchar(255) COMMENT '规则名称'")
  private String ruleName;

  @ApiModelProperty("检测逻辑")
  @Column(name = "rule_logic",columnDefinition="text COMMENT '检测逻辑'")
  private String ruleLogic;

  @ApiModelProperty("状态（0->停用；1->启用）")
  @Column(name = "status",columnDefinition="tinyint(1) COMMENT '状态（0->停用；1->启用）'")
  private Boolean status;

  @ApiModelProperty("停用时间")
  @Column(name = "stop_time",columnDefinition="datetime COMMENT '停用时间'")
  private Timestamp stopTime;

  @ApiModelProperty("备注")
  @Column(name = "remark",columnDefinition="text COMMENT '备注'")
  private String remark;
}
