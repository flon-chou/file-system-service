package com.cj.asr.dao;

import com.cj.asr.entity.VoiceTextRoleSpeedResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextRoleSpeedResult Repository
 * @date Date : 2019/03/27 11:00
 */
@Repository
public interface VoiceTextRoleSpeedResultRepository extends JpaRepository<VoiceTextRoleSpeedResult,Long> {
}
