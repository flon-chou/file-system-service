package com.cj.asr.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * generated by Generate MyPOJOs.groovy 
 * <p>Date: Thu Mar 28 16:53:06 CST 2019.</p>
 *
 * @author Flon
 */
@Data
@Entity
@Table ( name ="cj_voice_emotion_result_top" )
@EntityListeners(AuditingEntityListener.class)
public class VoiceEmotionResultTop  implements Serializable {


	private static final long serialVersionUID =  4368793458392865606L;


	/**
	 * id
	 */
   	@Column(name = "id", columnDefinition = "id" )	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreatedDate
   	@Column(name = "creation_time",updatable = false, columnDefinition = "null" )
	private Timestamp creationTime;

	/**
	 * 音频id
	 */
   	@Column(name = "audio_id", columnDefinition = "音频id" )
	private Integer audioId;

	/**
	 * 音频文件名
	 */
   	@Column(name = "audio_name", columnDefinition = "音频文件名" )
	private String audioName;

	/**
	 * 情绪结果
	 */
   	@Column(name = "result", columnDefinition = "情绪结果" )
	private String result;

	/**
	 * 置信度
	 */
   	@Column(name = "credible", columnDefinition = "置信度" )
	private String credible;

}
