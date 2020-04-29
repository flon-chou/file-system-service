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
 * @description 质检任务范围类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "质检任务范围类")
@Table ( name ="cj_qc_task_scope" )
public class TaskScope {

	@ApiModelProperty("质检任务范围ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '质检任务范围ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "录音开始时间")
	@Column(name = "record_start_time",columnDefinition="datetime COMMENT '录音开始时间'")
	private Timestamp recordStartTime;

	@ApiModelProperty(value = "录音结束时间")
	@Column(name = "record_end_time",columnDefinition="datetime COMMENT '录音结束时间'")
	private Timestamp recordEndTime;

	@ApiModelProperty("机构领域（数据字典值）")
	@Column(name = "org_relation", columnDefinition = "varchar(100) COMMENT '机构领域（数据字典值）'" )
	private String orgRelation;

	@ApiModelProperty("业务类型（数据字典值）")
	@Column(name = "type_of_service", columnDefinition = "varchar(100) COMMENT '业务类型（数据字典值）'" )
	private String typeOfService;

	@ApiModelProperty("（投保）单号（多个以','隔开）")
	@Column(name = "policy_no", columnDefinition = "text COMMENT '（投保）单号'" )
	private String policyNo;

	@ApiModelProperty("通话类型（数据字典值）")
	@Column(name = "call_type", columnDefinition = "varchar(100) COMMENT '通话类型（数据字典值）'" )
	private String callType;

	@ApiModelProperty("通话最少时长（单位：秒）")
	@Column(name = "call_duration_min", columnDefinition = "int(11) COMMENT '通话最少时长（单位：秒）'" )
	private Integer callDurationMin;

	@ApiModelProperty("通话最多时长（单位：秒）")
	@Column(name = "call_duration_max", columnDefinition = "int(11) COMMENT '通话最多时长（单位：秒）'" )
	private Integer callDurationMax;

	@ApiModelProperty("通话号码（多个以','隔开）")
	@Column(name = "call_numbers", columnDefinition = "text COMMENT '通话号码（多个以','隔开）'" )
	private String callNumbers;

	@ApiModelProperty("话务员组别（多个以','隔开）")
	@Column(name = "tsr_groups", columnDefinition = "text COMMENT '话务员组别（多个以','隔开）'" )
	private String tsrGroups;

	@ApiModelProperty("话务员工号（多个以','隔开）")
	@Column(name = "tsr_no", columnDefinition = "text COMMENT '话务员工号（多个以','隔开）'" )
	private String tsrNo;

	@ApiModelProperty("录音文件名（多个以','隔开）,不加格式后缀名")
	@Column(name = "audio_names", columnDefinition = "text COMMENT '录音文件名（多个以','隔开）'" )
	private String audioNames;

	@ApiModelProperty("数据类型（数据字典值）")
	@Column(name = "data_type", columnDefinition = "varchar(100) COMMENT '数据类型（数据字典值）'" )
	private String dataType;

	@ApiModelProperty("关键词（多个以','隔开）")
	@Column(name = "key_words", columnDefinition = "text COMMENT '关键词（多个以','隔开）'" )
	private String keyWords;

}
