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
 * @description 模型类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "模型类")
@Table(name = "cj_qc_model")
public class Model extends BasicPojo{

  @ApiModelProperty("模型ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '模型ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty("模型分类ID")
  @Column(name = "model_type_id",columnDefinition="bigint(20) COMMENT '模型分类ID'")
  private Long modelTypeId;

  @ApiModelProperty("模型名称")
  @Column(name = "model_name",columnDefinition="varchar(255) COMMENT '模型名称'")
  private String modelName;

  @ApiModelProperty("运算符：and|or")
  @Column(name = "operator",columnDefinition="varchar(255) COMMENT '运算符：and|or'")
  private String operator;

  @ApiModelProperty("组合关系")
  @Column(name = "model_comb",columnDefinition="text COMMENT '组合关系'")
  private String modelComb;

  @ApiModelProperty("包含规则（英文逗号分隔）")
  @Column(name = "model_rules",columnDefinition="text COMMENT '包含规则（英文逗号分隔）'")
  private String modelRules;

  @ApiModelProperty("检测逻辑")
  @Column(name = "model_logic",columnDefinition="text COMMENT '检测逻辑'")
  private String modelLogic;

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
