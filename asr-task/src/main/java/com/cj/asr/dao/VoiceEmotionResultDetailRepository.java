package com.cj.asr.dao;

import com.cj.asr.entity.VoiceEmotionResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceEmotionResultDetail Repository
 * @date Date : 2019/03/27 10:58
 */
@Repository
public interface VoiceEmotionResultDetailRepository extends JpaRepository<VoiceEmotionResultDetail,Long> {
}
