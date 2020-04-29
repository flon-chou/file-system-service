package com.cj.asr.dao;

import com.cj.asr.entity.VoiceEmotionResultTop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceEmotionResultTop Repository
 * @date Date : 2019/03/27 10:58
 */
@Repository
public interface VoiceEmotionResultTopRepository extends JpaRepository<VoiceEmotionResultTop,Long> {
}
