package com.cj.asr.dao;

import com.cj.asr.entity.VoiceTextAbResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextAbResult Repository
 * @date Date : 2019/03/27 10:59
 */
@Repository
public interface VoiceTextAbResultRepository extends JpaRepository<VoiceTextAbResult,Long> {
}
