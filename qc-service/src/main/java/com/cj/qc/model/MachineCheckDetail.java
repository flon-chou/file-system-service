package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 机器质检结果类详情类
 * @date 2019/3/26 10:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "机器质检结果类详情类")
@Table(name = "cj_qc_machine_check_detail")
public class MachineCheckDetail {

  @ApiModelProperty("机器质检结果详情ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '机器质检结果详情ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty("机器质检结果ID")
  @Column(name = "machine_check_id",columnDefinition="bigint(20) COMMENT '机器质检结果ID'")
  private Long machineCheckId;

  @ApiModelProperty("源表达式")
  @Column(name = "source_expression",columnDefinition="text COMMENT '源表达式'")
  private String sourceExpression;

  @ApiModelProperty("源后缀表达式")
  @Column(name = "source_postfix_expression",columnDefinition="text COMMENT '源后缀表达式'")
  private String sourcePostfixExpression;

  @ApiModelProperty("当前计算的表达式")
  @Column(name = "current_expression",columnDefinition="text COMMENT '当前计算的表达式'")
  private String currentExpression;

  @ApiModelProperty("运算符")
  @Column(name = "operator",columnDefinition="varchar(255) COMMENT '运算符'")
  private String operator;

  @ApiModelProperty("计算顺序，越小越早计算")
  @Column(name = "sort",columnDefinition="int(11) COMMENT '计算顺序，越小越早计算'")
  private int sort;

  @ApiModelProperty("源表达式布尔结果")
  @Column(name = "bool_res",columnDefinition="tinyint(1) COMMENT '源表达式布尔结果（0->false；1->true）'")
  private Boolean boolRes;

  @ApiModelProperty("机器质检结果详情信息集合")
  @Transient
  private List<MachineCheckDetailInfo> infos;
}
