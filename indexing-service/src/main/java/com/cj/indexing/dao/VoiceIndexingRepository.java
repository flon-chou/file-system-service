package com.cj.indexing.dao;

import com.cj.indexing.entity.VoiceIndexing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceIndexing Dao
 * @date Date : 2019/03/25 17:00
 */
@Repository
public interface VoiceIndexingRepository extends JpaRepository<VoiceIndexing,Long > {
}
