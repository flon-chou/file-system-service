package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author chenzhaowen
 * @description 机器质检结果类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "机器质检结果类")
@Table(name = "cj_qc_machine_result")
public class MachineResult extends BasicPojo {

	@ApiModelProperty("机器质检结果ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '机器质检结果ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("删除标识，0：不启用；1：启用")
	@Column(name = "enable", columnDefinition = "int(1) COMMENT '删除标识，0：不启用；1：启用'" )
	private Integer enable;

	@ApiModelProperty("数据备注")
	@Column(name = "remark", columnDefinition = "varchar(255) COMMENT '数据备注'" )
	private String remark;

	@ApiModelProperty("质检模型ID")
	@Column(name = "qc_model_id", columnDefinition = "bigint(20) COMMENT '质检模型ID'" )
	private Long qcModelId;

	@ApiModelProperty("投保单号")
	@Column(name = "policy_no", columnDefinition = "varchar(100) COMMENT '投保单号'" )
	private String policyNo;

	@ApiModelProperty("音频文件名")
	@Column(name = "audio_name", columnDefinition = "varchar(100) COMMENT '音频文件名'" )
	private String audioName;

	@ApiModelProperty("任务ID")
	@Column(name = "task_id", columnDefinition = "bigint(20) COMMENT '任务ID'" )
	private Long taskId;

	@ApiModelProperty("分数")
	@Column(name = "score", columnDefinition = "int(3) COMMENT '分数'" )
	private Integer score;

	@ApiModelProperty("是否质检通过（0：不通过/否/错；1：通过/是/对）")
	@Column(name = "is_pass", columnDefinition = "int(1) COMMENT '是否质检通过（0：不通过/否/错；1：通过/是/对）'" )
	private Integer isPass;

	@ApiModelProperty("质检结果")
	@Column(name = "result", columnDefinition = "text COMMENT '质检结果'" )
	private String result;
}
