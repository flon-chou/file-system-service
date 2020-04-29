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
 * @description 机器质检结果类
 * @date 2019/3/26 10:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "机器质检结果类")
@Table(name = "cj_qc_machine_check")
public class MachineCheck {

  @ApiModelProperty("机器质检结果ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '机器质检结果ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty("音频ID（多个音频可组成工单，此ID并非工单ID）")
  @Column(name = "voice_id",columnDefinition="bigint(20) COMMENT '音频ID（多个音频可组成工单，此ID并非工单ID）'")
  private Long voiceId;

  @ApiModelProperty("模型ID")
  @Column(name = "model_id",columnDefinition="bigint(20) COMMENT '模型ID'")
  private Long modelId;

  @ApiModelProperty("匹配布尔结果")
  @Column(name = "bool_res",columnDefinition="tinyint(1) COMMENT '匹配布尔结果（0->false；1->true）'")
  private Boolean boolRes;

  @ApiModelProperty("机器质检结果详情集合")
  @Transient
  private List<MachineCheckDetail> details;

  public MachineCheck(Boolean boolRes, List<MachineCheckDetail> details) {
    this.boolRes = boolRes;
    this.details = details;
  }
}
