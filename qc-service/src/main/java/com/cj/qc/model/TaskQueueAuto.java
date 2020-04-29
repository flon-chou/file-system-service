package com.cj.qc.model;

import com.cj.qc.tool.DateTool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author chenzhaowen
 * @description 自动质检任务队列类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "自动质检任务队列类")
@Table ( name ="cj_qc_task_queue_auto" )
public class TaskQueueAuto {

	@ApiModelProperty("自动质检任务队列ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '自动质检任务队列ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("自动质检任务ID(任务类型为自动的任务ID)")
	@Column(name = "task_auto_id", columnDefinition = "bigint(20) COMMENT '自动质检任务ID(任务类型为自动的任务ID)'" )
	private Long taskAutoId;

	@ApiModelProperty(value = "执行日期")
	@Column(name = "day",columnDefinition="date COMMENT '执行日期'")
	private Timestamp day;

	@ApiModelProperty("执行状态（0->未执行；1->已执行）")
	@Column(name = "status", columnDefinition = "int(1) COMMENT '执行状态（0->未执行；1->已执行）'" )
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "creation_time",columnDefinition="datetime COMMENT '创建时间'")
	private Timestamp creationTime;

	@ApiModelProperty(value = "最后修改时间")
	@Column(name = "last_modified",columnDefinition="datetime COMMENT '最后修改时间'")
	private Timestamp lastModified;

	public void createOperation(){
		this.creationTime= DateTool.nowTime();
		this.lastModified= this.creationTime;
	}
	public void updateOperation(){
		this.lastModified= DateTool.nowTime();
	}
}
