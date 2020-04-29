package com.cj.asr.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author chenzhaowen
 * @description 词识别结果类
 * @date 2019/3/21 14:05
 */
@Data
@Entity
@Table(name = "cj_voice_words_result")
@EntityListeners(AuditingEntityListener.class)
public class VoiceWordsResult {

  @Id
  @Column(name = "id",columnDefinition="bigint(20) COMMENT '词识别结果ID'")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(name = "creation_time",columnDefinition="datetime COMMENT '创建时间'")
  private Timestamp creationTime;

  @Column(name = "audio_id",columnDefinition="bigint(20) COMMENT '音频id'")
  private Long audioId;

  @Column(name = "file_name",columnDefinition="varchar(255) COMMENT '音频名称'")
  private String fileName;

  @Column(name = "begin_time",columnDefinition="varchar(10) COMMENT '开始时间'")
  private String beginTime;

  @Column(name = "end_time",columnDefinition="varchar(10) COMMENT '结束时间'")
  private String endTime;

  @Column(name = "content",columnDefinition="text COMMENT '词内容'")
  private String content;

  @Column(name = "role",columnDefinition="varchar(1) COMMENT '角色（A：坐席，B：客户）'")
  private String role;
}
