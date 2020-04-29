package com.cj.qc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author chenzhaowen
 * @description 质检任务范围组合类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "质检任务范围组合类：与字典表组合获取相应名称")
public class TaskScopeCombDTO {

	@ApiModelProperty("质检任务范围ID")
	private Long id;

	@ApiModelProperty(value = "录音开始时间")
	private Timestamp recordStartTime;

	@ApiModelProperty(value = "录音结束时间")
	private Timestamp recordEndTime;

	@ApiModelProperty("机构领域（数据字典值）")
	private String orgRelation;

	@ApiModelProperty("机构领域名称呢个（数据字典名称）")
	private String orgRelationName;

	@ApiModelProperty("业务类型（数据字典值）")
	private String typeOfService;

	@ApiModelProperty("业务类型名称（数据字典名称）")
	private String typeOfServiceName;

	@ApiModelProperty("（投保）单号（多个以','隔开）")
	private String policyNo;

	@ApiModelProperty("通话类型（数据字典值）")
	private String callType;

	@ApiModelProperty("通话类型名称（数据字典名称）")
	private String callTypeName;

	@ApiModelProperty("通话最少时长（单位：秒）")
	private Integer callDurationMin;

	@ApiModelProperty("通话最多时长（单位：秒）")
	private Integer callDurationMax;

	@ApiModelProperty("通话号码（多个以','隔开）")
	private String callNumbers;

	@ApiModelProperty("话务员组别（多个以','隔开）")
	private String tsrGroups;

	@ApiModelProperty("话务员工号（多个以','隔开）")
	private String tsrNo;

	@ApiModelProperty("录音文件名（多个以','隔开）")
	private String audioNames;

	@ApiModelProperty("数据类型（数据字典值）")
	private String dataType;

	@ApiModelProperty("数据类型名称（数据字典名称）")
	private String dataTypeName;

	@ApiModelProperty("关键词（多个以','隔开）")
	private String keyWords;

}
