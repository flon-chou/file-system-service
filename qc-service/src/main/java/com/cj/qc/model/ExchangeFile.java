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
 * @description 转换音频文件类（用于规则解析）类
 * @date 2019/3/26 10:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "转换音频文件类（用于规则解析）")
@Table(name = "cj_voice_exchange_file")
public class ExchangeFile {

  @ApiModelProperty("转换音频文件ID")
  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '转换音频文件ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value = "创建时间")
  @Column(name = "creation_time",columnDefinition="datetime COMMENT '创建时间'")
  private Timestamp creationTime;

  @ApiModelProperty("音频文件名")
  @Column(name = "file_name",columnDefinition="varchar(255) COMMENT '音频文件名'")
  private String fileName;

  @ApiModelProperty("源文件全文")
  @Column(name = "source",columnDefinition="text COMMENT '源文件全文'")
  private String source;

  @ApiModelProperty("源文件数组")
  @Column(name = "source_arr",columnDefinition="text COMMENT '源文件数组'")
  private String sourceArr;

  @ApiModelProperty("时间文件数组")
  @Column(name = "time_arr",columnDefinition="text COMMENT '时间文件数组'")
  private String timeArr;

  @ApiModelProperty("角色文件数组")
  @Column(name = "role_arr",columnDefinition="text COMMENT '角色文件数组'")
  private String roleArr;

  public void createOperation(){
    this.creationTime= DateTool.nowTime();
  }
}
