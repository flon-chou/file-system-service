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
 * @description 词识别结果类
 * @date 2019/3/21 14:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "词识别结果类")
@Table(name = "cj_voice_words_result")
public class WordsResult {

  @ApiModelProperty("词识别结果ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '词识别结果ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value = "创建时间")
  @Column(name = "creation_time",columnDefinition="datetime COMMENT '创建时间'")
  private Timestamp creationTime;

  @ApiModelProperty("音频id")
  @Column(name = "audio_id",columnDefinition="bigint(20) COMMENT '音频id'")
  private Long audioId;

  @ApiModelProperty("音频名称")
  @Column(name = "file_name",columnDefinition="varchar(255) COMMENT '音频名称'")
  private String fileName;

  @ApiModelProperty("开始时间")
  @Column(name = "begin_time",columnDefinition="varchar(10) COMMENT '开始时间'")
  private String beginTime;

  @ApiModelProperty("结束时间")
  @Column(name = "end_time",columnDefinition="varchar(10) COMMENT '结束时间'")
  private String endTime;

  @ApiModelProperty("词内容")
  @Column(name = "content",columnDefinition="text COMMENT '词内容'")
  private String content;

  @ApiModelProperty("角色（A：坐席，B：客户）")
  @Column(name = "role",columnDefinition="varchar COMMENT '角色（A：坐席，B：客户）'")
  private String role;

  public void createOperation(){
    this.creationTime= DateTool.nowTime();
  }
}
