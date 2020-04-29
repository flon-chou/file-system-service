package com.cj.qc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author chenzhaowen
 * @description 机器质检结果类详情信息类
 * @date 2019/3/26 10:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "机器质检结果类详情信息类")
@Table(name = "cj_qc_machine_check_detail_info")
public class MachineCheckDetailInfo {

  @ApiModelProperty("机器质检结果类详情信息ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '机器质检结果类详情信息ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty("机器质检结果详情ID")
  @Column(name = "machine_check_detail_id",columnDefinition="bigint(20) COMMENT '机器质检结果详情ID'")
  private Long machineCheckDetailId;

  @ApiModelProperty("当前计算内容")
  @Column(name = "current_content",columnDefinition="text COMMENT '当前计算内容'")
  private String currentContent;

  /**
   * 用于 bf、ne
   */
  //左
  @ApiModelProperty("左匹配内容")
  @Column(name = "left_content",columnDefinition="varchar(255) COMMENT '左匹配内容'")
  private String leftContent;

  @ApiModelProperty("左匹配内容左位置（开）")
  @Column(name = "left_open_location",columnDefinition="int(11) COMMENT '左匹配内容左位置（开）'")
  private Integer leftOpenLocation;

  @ApiModelProperty("左匹配内容右位置（开）")
  @Column(name = "left_close_location",columnDefinition="int(11) COMMENT '左匹配内容右位置（开）'")
  private Integer leftCloseLocation;

  @ApiModelProperty("左匹配内容左位置（开）时间")
  @Column(name = "left_open_time",columnDefinition="varchar(255) COMMENT '左匹配内容左位置（开）时间'")
  private String leftOpenTime;

  @ApiModelProperty("左匹配内容右位置（开）时间")
  @Column(name = "left_close_time",columnDefinition="varchar(255) COMMENT '左匹配内容右位置（开）时间'")
  private String leftCloseTime;

  @ApiModelProperty("左匹配内容角色：A->坐席，B->客户")
  @Column(name = "left_role",columnDefinition="varchar(255) COMMENT '左匹配内容角色：A->坐席，B->客户'")
  private String leftRole;

  //右
  @ApiModelProperty("右匹配内容")
  @Column(name = "right_content",columnDefinition="varchar(255) COMMENT '右匹配内容'")
  private String rightContent;

  @ApiModelProperty("右匹配内容左位置（开）")
  @Column(name = "right_open_location",columnDefinition="int(11) COMMENT '右匹配内容左位置（开）'")
  private Integer rightOpenLocation;

  @ApiModelProperty("右匹配内容右位置（开）")
  @Column(name = "right_close_location",columnDefinition="int(11) COMMENT '右匹配内容右位置（开）'")
  private Integer rightCloseLocation;

  @ApiModelProperty("右匹配内容左位置（开）时间")
  @Column(name = "right_open_time",columnDefinition="varchar(255) COMMENT '右匹配内容左位置（开）时间'")
  private String rightOpenTime;

  @ApiModelProperty("右匹配内容右位置（开）时间")
  @Column(name = "right_close_time",columnDefinition="varchar(255) COMMENT '右匹配内容右位置（开）时间'")
  private String rightCloseTime;

  @ApiModelProperty("右匹配内容角色：A->坐席，B->客户")
  @Column(name = "right_role",columnDefinition="varchar(255) COMMENT '右匹配内容角色：A->坐席，B->客户'")
  private String rightRole;

  //全部
  @ApiModelProperty("左右全部匹配表达式")
  @Column(name = "full_expression",columnDefinition="varchar(255) COMMENT '左右全部匹配表达式'")
  private String fullExpression;

  @ApiModelProperty("左右全部匹配内容")
  @Column(name = "full_content",columnDefinition="varchar(255) COMMENT '左右全部匹配内容'")
  private String fullContent;

  @ApiModelProperty("左右全部匹配内容左位置（开）")
  @Column(name = "full_open_location",columnDefinition="int(11) COMMENT '左右全部匹配内容左位置（开）'")
  private Integer fullOpenLocation;

  @ApiModelProperty("左右全部匹配内容右位置（开）")
  @Column(name = "full_close_location",columnDefinition="int(11) COMMENT '左右全部匹配内容右位置（开）'")
  private Integer fullCloseLocation;

  @ApiModelProperty("左右全部匹配内容左位置（开）时间")
  @Column(name = "full_open_time",columnDefinition="varchar(255) COMMENT '左右全部匹配内容左位置（开）时间'")
  private String fullOpenTime;

  @ApiModelProperty("左右全部匹配内容右位置（开）时间")
  @Column(name = "full_close_time",columnDefinition="varchar(255) COMMENT '左右全部匹配内容右位置（开）时间'")
  private String fullCloseTime;


  /**
   * 用于 and、or、ex、no、{w1,w2,w3,...}、{w1/w2/w3,...}
   */
  @ApiModelProperty("基础匹配内容")
  @Column(name = "base_content",columnDefinition="varchar(255) COMMENT '基础匹配内容'")
  private String baseContent;

  @ApiModelProperty("基础匹配内容左位置（开）")
  @Column(name = "base_open_location",columnDefinition="int(11) COMMENT '基础匹配内容左位置（开）'")
  private Integer baseOpenLocation;

  @ApiModelProperty("基础匹配内容右位置（开）")
  @Column(name = "base_close_location",columnDefinition="int(11) COMMENT '基础匹配内容右位置（开）'")
  private Integer baseCloseLocation;

  @ApiModelProperty("基础匹配内容左位置（开）时间")
  @Column(name = "base_open_time",columnDefinition="varchar(255) COMMENT '基础匹配内容左位置（开）时间'")
  private String baseOpenTime;

  @ApiModelProperty("基础匹配内容右位置（开）时间")
  @Column(name = "base_close_time",columnDefinition="varchar(255) COMMENT '基础匹配内容右位置（开）时间'")
  private String baseCloseTime;

  @ApiModelProperty("基础匹配内容角色：A->坐席，B->客户")
  @Column(name = "base_role",columnDefinition="varchar(255) COMMENT '基础匹配内容角色：A->坐席，B->客户'")
  private String baseRole;


  //{w1,w2,w3}
  @ApiModelProperty("标识是否是{w1,w2,w3}此类保存结果，0->不是，1->是")
  @Column(name = "series_type",columnDefinition="int(11) COMMENT '标识是否是{w1,w2,w3}此类保存结果，0->不是，1->是'")
  private int seriesType;

  @ApiModelProperty("顺序串联内容")
  @Column(name = "series_content",columnDefinition="varchar(255) COMMENT '顺序串联内容'")
  private String seriesContent;

  @ApiModelProperty("顺序串联内容第一层，表示一条{w1,w2,w3}的完整结果个数顺序")
  @Column(name = "series_sort_first",columnDefinition="int(11) COMMENT '顺序串联内容第一层，表示一条{w1,w2,w3}的完整结果个数顺序'")
  private int seriesSortFirst;

  @ApiModelProperty("顺序串联内容第二层，表示一条{w1,w2,w3}的完整结果的子内容如w1、w2、w3出现顺序")
  @Column(name = "series_sort_second",columnDefinition="int(11) COMMENT '顺序串联内容第二层，表示一条{w1,w2,w3}的完整结果的子内容如w1、w2、w3出现顺序'")
  private int seriesSortSecond;

}
