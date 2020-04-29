package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author chenzhaowen
 * @description 机器质检规则结果类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "机器质检规则结果类")
@Table(name = "cj_qc_machine_result_rule")
public class MachineResultRule extends BasicPojo {

	@ApiModelProperty("机器质检规则结果ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '机器质检规则结果ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("删除标识，0：不启用；1：启用")
	@Column(name = "enable", columnDefinition = "int(1) COMMENT '删除标识，0：不启用；1：启用'" )
	private Integer enable;

	@ApiModelProperty("数据备注")
	@Column(name = "remark", columnDefinition = "varchar(255) COMMENT '数据备注'" )
	private String remark;

	@ApiModelProperty("机器质检结果ID")
	@Column(name = "qc_result_id", columnDefinition = "bigint(20) COMMENT '机器质检结果ID'" )
	private Long qcResultId;

	@ApiModelProperty("质检规则ID")
	@Column(name = "qc_rule_id", columnDefinition = "bigint(20) COMMENT '质检规则ID'" )
	private Long qcRuleId;

	@ApiModelProperty("分数")
	@Column(name = "score", columnDefinition = "int(3) COMMENT '分数'" )
	private Integer score;

	@ApiModelProperty("是否质检通过（0：不通过/否/错；1：通过/是/对）")
	@Column(name = "is_pass", columnDefinition = "int(1) COMMENT '是否质检通过（0：不通过/否/错；1：通过/是/对）'" )
	private Integer isPass;

	@ApiModelProperty("质检结果")
	@Column(name = "rule_result", columnDefinition = "text COMMENT '质检结果'" )
	private String ruleResult;
}
