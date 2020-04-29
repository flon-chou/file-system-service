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
 * @description 任务类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "任务类")
@Table ( name ="cj_qc_task" )
public class Task extends BasicPojo{

	@ApiModelProperty("任务ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '任务ID'")
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

	@ApiModelProperty("质检任务范围ID")
	@Column(name = "qc_task_scope_id", columnDefinition = "bigint(20) COMMENT '质检任务范围ID'" )
	private Long qcTaskScopeId;

	@ApiModelProperty("语音质检任务规则ID")
	@Column(name = "qc_task_voice_id", columnDefinition = "bigint(20) COMMENT '语音质检任务规则ID'" )
	private Long qcTaskVoiceId;

	@ApiModelProperty("任务名称")
	@Column(name = "task_name", columnDefinition = "varchar(255) COMMENT '任务名称'" )
	private String taskName;

	@ApiModelProperty("状态：自动（未执行->0;已执行->2）;手动（未执行->0;执行中->1;已执行->2）")
	@Column(name = "status", columnDefinition = "int(1) COMMENT '状态：自动（未执行->0;已执行->2）;手动（未执行->0;执行中->1;已执行->2）'" )
	private Integer status;

	@ApiModelProperty("任务检测逻辑")
	@Column(name = "task_logic", columnDefinition = "text COMMENT '任务检测逻辑'" )
	private String taskLogic;

	@ApiModelProperty("任务类型（1：自动，2：手动）")
	@Column(name = "task_type", columnDefinition = "int(1) COMMENT '任务类型（1：自动，2：手动）'" )
	private Integer taskType;

	@ApiModelProperty(value = "自动任务开始日期")
	@Column(name = "start_date",columnDefinition="date COMMENT '自动任务开始日期'")
	private Timestamp startDate;

	@ApiModelProperty(value = "自动任务结束日期")
	@Column(name = "end_date",columnDefinition="date COMMENT '自动任务结束日期'")
	private Timestamp endDate;
}
