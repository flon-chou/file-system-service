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
 * @description 业务索引类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "业务索引类")
@Table(name = "cj_voice_indexing")
public class VoiceIndexing {

  @ApiModelProperty("业务索引ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '业务索引ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value = "创建时间")
  @Column(name = "creation_time",columnDefinition="datetime COMMENT '创建时间'")
  private Timestamp creationTime;

  @ApiModelProperty("音频文件名称")
  @Column(name = "file_name",columnDefinition="varchar(100) COMMENT '音频文件名称'")
  private String fileName;

  @ApiModelProperty("录音号")
  @Column(name = "number_of_record",columnDefinition="varchar(100) COMMENT '录音号'")
  private String numberOfRecord;

  @ApiModelProperty("通话开始时间")
  @Column(name = "start_time",columnDefinition="datetime COMMENT '通话开始时间'")
  private Timestamp startTime;

  @ApiModelProperty("通话结束时间")
  @Column(name = "end_time",columnDefinition="datetime COMMENT '通话结束时间'")
  private Timestamp endTime;

  @ApiModelProperty("通话时长，单位：秒")
  @Column(name = "duration",columnDefinition="int(4) COMMENT '通话时长，单位：秒'")
  private Integer duration;

  @ApiModelProperty("通话号码")
  @Column(name = "call_number",columnDefinition="varchar(100) COMMENT '通话号码'")
  private String callNumber;

  @ApiModelProperty("通话类型")
  @Column(name = "call_type",columnDefinition="varchar(100) COMMENT '通话类型'")
  private String callType;

  @ApiModelProperty("业务类型")
  @Column(name = "type_of_service",columnDefinition="varchar(100) COMMENT '业务类型'")
  private String typeOfService;

  @ApiModelProperty("话务员工号")
  @Column(name = "tsr_no",columnDefinition="varchar(100) COMMENT '话务员工号'")
  private String tsrNo;

  @ApiModelProperty("话务员组别")
  @Column(name = "tsr_group",columnDefinition="varchar(100) COMMENT '话务员组别'")
  private String tsrGroup;

  @ApiModelProperty("话务员姓名")
  @Column(name = "agent_no",columnDefinition="varchar(100) COMMENT '话务员姓名'")
  private String agentNo;

  @ApiModelProperty("话务员团队")
  @Column(name = "tsr_team",columnDefinition="varchar(100) COMMENT '话务员团队'")
  private String tsrTeam;

  @ApiModelProperty("场地")
  @Column(name = "region",columnDefinition="varchar(100) COMMENT '场地'")
  private String region;

  @ApiModelProperty("项目队列")
  @Column(name = "project_list",columnDefinition="varchar(100) COMMENT '项目队列'")
  private String projectList;

  @ApiModelProperty("客户号")
  @Column(name = "account_no",columnDefinition="varchar(100) COMMENT '客户号'")
  private String accountNo;

  @ApiModelProperty("客户证件号")
  @Column(name = "customer_id",columnDefinition="varchar(100) COMMENT '客户证件号'")
  private String customerId;

  @ApiModelProperty("客户姓名")
  @Column(name = "customer_name",columnDefinition="varchar(100) COMMENT '客户姓名'")
  private String customerName;

  @ApiModelProperty("质检员工号")
  @Column(name = "qa_no",columnDefinition="varchar(100) COMMENT '质检员工号'")
  private String qaNo;

  @ApiModelProperty("话后小结组别")
  @Column(name = "summarize_group",columnDefinition="varchar(100) COMMENT '话后小结组别'")
  private String summarizeGroup;

  @ApiModelProperty("小结分类")
  @Column(name = "summarize_class",columnDefinition="varchar(100) COMMENT '小结分类'")
  private String summarizeClass;

  @ApiModelProperty("小结项目")
  @Column(name = "summarize_project",columnDefinition="varchar(100) COMMENT '小结项目'")
  private String summarizeProject;

  @ApiModelProperty("小结内容")
  @Column(name = "summarize_content",columnDefinition="varchar(100) COMMENT '小结内容'")
  private String summarizeContent;

  @ApiModelProperty("是否质检")
  @Column(name = "is_qa",columnDefinition="varchar(100) COMMENT '是否质检'")
  private String isQa;

  @ApiModelProperty("是否提交案例库")
  @Column(name = "to_warehouse",columnDefinition="varchar(100) COMMENT '是否提交案例库'")
  private String toWarehouse;

  @ApiModelProperty("是否提交学习跟进")
  @Column(name = "to_improvement",columnDefinition="varchar(100) COMMENT '是否提交学习跟进'")
  private String toImprovement;

  @ApiModelProperty("是否异常录音")
  @Column(name = "is_normality",columnDefinition="varchar(100) COMMENT '是否异常录音'")
  private String isNormality;

  @ApiModelProperty("数据状态一级原因")
  @Column(name = "data_status",columnDefinition="varchar(100) COMMENT '数据状态一级原因'")
  private String dataStatus;

  @ApiModelProperty("数据状态二级原因")
  @Column(name = "data_status_2",columnDefinition="varchar(100) COMMENT '数据状态二级原因'")
  private String dataStatus2;

  @ApiModelProperty("数据状态三级原因")
  @Column(name = "data_status_3",columnDefinition="varchar(100) COMMENT '数据状态三级原因'")
  private String dataStatus3;

  @ApiModelProperty("所属组长")
  @Column(name = "group_leader",columnDefinition="varchar(100) COMMENT '所属组长'")
  private String groupLeader;

  @ApiModelProperty("保单号")
  @Column(name = "policy_no",columnDefinition="varchar(100) COMMENT '保单号'")
  private String policyNo;

  @ApiModelProperty("产品类型")
  @Column(name = "product_type",columnDefinition="varchar(100) COMMENT '产品类型'")
  private String productType;

  @ApiModelProperty("险种代码")
  @Column(name = "plan_code",columnDefinition="varchar(100) COMMENT '险种代码'")
  private String planCode;

  @ApiModelProperty("险种类型")
  @Column(name = "plan_type",columnDefinition="varchar(100) COMMENT '险种类型'")
  private String planType;

  @ApiModelProperty("被保险人姓名")
  @Column(name = "ins_name",columnDefinition="varchar(100) COMMENT '被保险人姓名'")
  private String insName;

  @ApiModelProperty("销售区域")
  @Column(name = "sales_area",columnDefinition="varchar(100) COMMENT '销售区域'")
  private String salesArea;

  @ApiModelProperty("其他规则类型")
  @Column(name = "bu_type",columnDefinition="varchar(100) COMMENT '其他规则类型'")
  private String buType;

  @ApiModelProperty("规则提示信息")
  @Column(name = "rule_prompt",columnDefinition="varchar(100) COMMENT '规则提示信息'")
  private String rulePrompt;
}
