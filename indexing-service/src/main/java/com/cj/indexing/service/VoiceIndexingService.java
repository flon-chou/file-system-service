package com.cj.indexing.service;

import com.cj.indexing.dao.VoiceIndexingRepository;
import com.cj.indexing.entity.VoiceIndexing;
import com.cj.indexing.utils.TxtToVoiceIndexing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: voiceIndexing Service
 * @date Date : 2019/03/25 17:01
 */
@Slf4j
@Service
public class VoiceIndexingService {

	@Autowired
	private VoiceIndexingRepository voiceIndexingRepository;


	/**
	 *  保存TXT文件内容
	 * @param path   文件地址
	 * @return
	 */
	public List<VoiceIndexing> saveTXT(String path){
		List<VoiceIndexing> indexingList = TxtToVoiceIndexing.covert(path);
		if (indexingList==null || indexingList.isEmpty()){
			log.error("保存数据失败，传入参数为空！");
			return null;
		}
		return voiceIndexingRepository.saveAll(indexingList);
	}

}
