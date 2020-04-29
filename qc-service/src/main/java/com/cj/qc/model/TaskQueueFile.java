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
 * @description 质检任务队列文件类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "质检任务队列文件类")
@Table ( name ="cj_qc_task_queue_file" )
public class TaskQueueFile {

	@ApiModelProperty("质检任务队列文件ID")
	@Id
	@Column(name = "id",columnDefinition="bigint(20) COMMENT '质检任务队列文件ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("质检任务ID")
	@Column(name = "task_id", columnDefinition = "bigint(20) COMMENT '质检任务ID'" )
	private Long taskId;

	@ApiModelProperty("音频名称")
	@Column(name = "file_name",columnDefinition="varchar(255) COMMENT '音频名称'")
	private String fileName;

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
