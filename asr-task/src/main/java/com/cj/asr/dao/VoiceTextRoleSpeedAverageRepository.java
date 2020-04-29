package com.cj.asr.dao;

import com.cj.asr.entity.VoiceTextRoleSpeedAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextRoleSpeedAverage Repository
 * @date Date : 2019/03/30 0:42
 */
@Repository
public interface VoiceTextRoleSpeedAverageRepository extends JpaRepository<VoiceTextRoleSpeedAverage,Long> {
}
