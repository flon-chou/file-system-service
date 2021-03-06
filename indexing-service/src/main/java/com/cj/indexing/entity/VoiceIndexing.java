package com.cj.indexing.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;;

/**
 * generated by Generate MyPOJOs.groovy 
 * <p>Date: Mon Mar 25 19:50:48 CST 2019.</p>
 *
 * @author Flon
 */
@Data
@Entity
@Table ( name ="cj_voice_indexing" )
public class VoiceIndexing  implements Serializable {


	private static final long serialVersionUID =  4327424958965550572L;


	@Column(name = "id", columnDefinition = "null" )	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "creation_time", columnDefinition = "null" )
	@CreationTimestamp
	private java.sql.Timestamp creationTime;

	/**
	 * 文件名称
	 */
	@Column(name = "file_name", columnDefinition = "文件名称" )
	private String fileName;

	/**
	 * 录音号
	 */
	@Column(name = "number_of_record", columnDefinition = "录音号" )
	private String numberOfRecord;

	/**
	 * 通话开始时间
	 */
	@Column(name = "start_time", columnDefinition = "通话开始时间" )
	private java.sql.Timestamp startTime;

	/**
	 * 通话结束时间
	 */
	@Column(name = "end_time", columnDefinition = "通话结束时间" )
	private java.sql.Timestamp endTime;

	/**
	 * 通话时长，单位：秒
	 */
	@Column(name = "duration", columnDefinition = "通话时长，单位：秒" )
	private Integer duration;

	/**
	 * 通话号码
	 */
	@Column(name = "call_number", columnDefinition = "通话号码" )
	private String callNumber;

	/**
	 * 通话类型
	 */
	@Column(name = "call_type", columnDefinition = "通话类型" )
	private String callType;

	/**
	 * 业务类型
	 */
	@Column(name = "type_of_service", columnDefinition = "业务类型" )
	private String typeOfService;

	/**
	 * 话务员工号
	 */
	@Column(name = "tsr_no", columnDefinition = "话务员工号" )
	private String tsrNo;

	/**
	 * 话务员组别
	 */
	@Column(name = "tsr_group", columnDefinition = "话务员组别" )
	private String tsrGroup;

	/**
	 * 话务员姓名
	 */
	@Column(name = "agent_no", columnDefinition = "话务员姓名" )
	private String agentNo;

	/**
	 * 话务员团队
	 */
	@Column(name = "tsr_team", columnDefinition = "话务员团队" )
	private String tsrTeam;

	/**
	 * 场地
	 */
	@Column(name = "region", columnDefinition = "场地" )
	private String region;

	/**
	 * 项目队列
	 */
	@Column(name = "project_list", columnDefinition = "项目队列" )
	private String projectList;

	/**
	 * 客户号
	 */
	@Column(name = "account_no", columnDefinition = "客户号" )
	private String accountNo;

	/**
	 * 客户证件号
	 */
	@Column(name = "customer_id", columnDefinition = "客户证件号" )
	private String customerId;

	/**
	 * 客户姓名
	 */
	@Column(name = "customer_name", columnDefinition = "客户姓名" )
	private String customerName;

	/**
	 * 质检员工号
	 */
	@Column(name = "qa_no", columnDefinition = "质检员工号" )
	private String qaNo;

	/**
	 * 话后小结组别
	 */
	@Column(name = "summarize_group", columnDefinition = "话后小结组别" )
	private String summarizeGroup;

	/**
	 * 小结分类
	 */
	@Column(name = "summarize_class", columnDefinition = "小结分类" )
	private String summarizeClass;

	/**
	 * 小结项目
	 */
	@Column(name = "summarize_project", columnDefinition = "小结项目" )
	private String summarizeProject;

	/**
	 * 小结内容
	 */
	@Column(name = "summarize_content", columnDefinition = "小结内容" )
	private String summarizeContent;

	/**
	 * 是否质检
	 */
	@Column(name = "is_qa", columnDefinition = "是否质检" )
	private String isQa;

	/**
	 * 是否提交案例库
	 */
	@Column(name = "to_warehouse", columnDefinition = "是否提交案例库" )
	private String toWarehouse;

	/**
	 * 是否提交学习跟进
	 */
	@Column(name = "to_improvement", columnDefinition = "是否提交学习跟进" )
	private String toImprovement;

	/**
	 * 是否异常录音
	 */
	@Column(name = "is_normality", columnDefinition = "是否异常录音" )
	private String isNormality;

	/**
	 * 数据状态一级原因
	 */
	@Column(name = "data_status", columnDefinition = "数据状态一级原因" )
	private String dataStatus;

	/**
	 * 数据状态二级原因
	 */
	@Column(name = "data_status_2", columnDefinition = "数据状态二级原因" )
	private String dataStatus2;

	/**
	 * 数据状态三级原因
	 */
	@Column(name = "data_status_3", columnDefinition = "数据状态三级原因" )
	private String dataStatus3;

	/**
	 * 所属组长
	 */
	@Column(name = "group_leader", columnDefinition = "所属组长" )
	private String groupLeader;

	/**
	 * 保单号
	 */
	@Column(name = "policy_no", columnDefinition = "保单号" )
	private String policyNo;

	/**
	 * 产品类型
	 */
	@Column(name = "product_type", columnDefinition = "产品类型" )
	private String productType;

	/**
	 * 险种代码
	 */
	@Column(name = "plan_code", columnDefinition = "险种代码" )
	private String planCode;

	/**
	 * 险种类型
	 */
	@Column(name = "plan_type", columnDefinition = "险种类型" )
	private String planType;

	/**
	 * 被保险人姓名
	 */
	@Column(name = "ins_name", columnDefinition = "被保险人姓名" )
	private String insName;

	/**
	 * 销售区域
	 */
	@Column(name = "sales_area", columnDefinition = "销售区域" )
	private String salesArea;

	/**
	 * 其他规则类型
	 */
	@Column(name = "bu_type", columnDefinition = "其他规则类型" )
	private String buType;

	/**
	 * 规则提示信息
	 */
	@Column(name = "rule_prompt", columnDefinition = "规则提示信息" )
	private String rulePrompt;

}
