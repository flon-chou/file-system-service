package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author chenzhaowen
 * @description 语音质检任务规则类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "语音质检任务规则类")
@Table ( name ="cj_qc_task_voice_rule" )
public class TaskVoiceRule {

	@ApiModelProperty("语音质检任务规则ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '语音质检任务规则ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("长时静音大于（单位：秒）")
	@Column(name = "long_silent", columnDefinition = "int(11) COMMENT '长时静音大于（单位：秒）'" )
	private Integer longSilent;

	@ApiModelProperty("超长通话大于（单位：秒）")
	@Column(name = "long_talk", columnDefinition = "int(11) COMMENT '超长通话大于（单位：秒）'" )
	private Integer longTalk;

	@ApiModelProperty("语速异常大于（单位：字/分钟）")
	@Column(name = "abnormal_speed", columnDefinition = "int(11) COMMENT '语速异常大于（单位：字/分钟）'" )
	private Integer abnormalSpeed;

	@ApiModelProperty("抢插话（叠音）大于（单位：秒）")
	@Column(name = "summation_tone", columnDefinition = "int(11) COMMENT '抢插话（叠音）大于（单位：秒）'" )
	private Integer summationTone;

	@ApiModelProperty("坐席情绪")
	@Column(name = "seat_emtion", columnDefinition = "char(10) COMMENT '坐席情绪'" )
	private String seatEmtion;

	@ApiModelProperty("客户情绪")
	@Column(name = "customer_emtion", columnDefinition = "char(10) COMMENT '客户情绪'" )
	private String customerEmtion;
}
