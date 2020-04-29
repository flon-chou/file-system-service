package com.cj.asr.dao;

import com.cj.asr.entity.VoiceBlankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceBlankInfo Repository
 * @date Date : 2019/03/27 10:57
 */
@Repository
public interface VoiceBlankInfoRepository extends JpaRepository<VoiceBlankInfo,Long> {
}
