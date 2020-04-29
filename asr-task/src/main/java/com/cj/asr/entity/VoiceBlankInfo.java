package com.cj.asr.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;;

/**
 * generated by Generate MyPOJOs.groovy 
 * <p>Date: Thu Mar 28 16:53:06 CST 2019.</p>
 *
 * @author Flon
 */
@Data
@Entity
@Table ( name ="cj_voice_blankinfo" )
@EntityListeners(AuditingEntityListener.class)
public class VoiceBlankInfo  implements Serializable {


	private static final long serialVersionUID =  2204775456522542831L;


	/**
	 * id
	 */
   	@Column(name = "id", columnDefinition = "id" )	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreatedDate
   	@Column(name = "creation_time",updatable = false, columnDefinition = "null" )
	private Timestamp creationTime;

	/**
	 * 开始出现静音的时间
	 */
   	@Column(name = "start_blank_pos", columnDefinition = "开始出现静音的时间" )
	private Integer startBlankPos;

	/**
	 * 静音时长
	 */
   	@Column(name = "blank_len", columnDefinition = "静音时长" )
	private Integer blankLen;

	/**
	 * 音频标识
	 */
   	@Column(name = "audio_id", columnDefinition = "音频标识" )
	private Integer audioId;

	/**
	 * 音频文件名
	 */
   	@Column(name = "audio_name", columnDefinition = "音频文件名" )
	private String audioName;

}