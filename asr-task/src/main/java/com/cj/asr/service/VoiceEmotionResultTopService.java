package com.cj.asr.service;

import com.cj.asr.dao.VoiceEmotionResultTopRepository;
import com.cj.asr.entity.VoiceEmotionResultTop;
import com.cj.asr.resolver.VoiceEmotionResultTopResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceEmotionResultTop Service
 * @date Date : 2019/03/27 11:03
 */
@Slf4j
@Service
public class VoiceEmotionResultTopService extends BaseService<VoiceEmotionResultTop>{

	@Autowired
	private VoiceEmotionResultTopRepository emotionResultTopRepository;
	@Autowired
	private VoiceEmotionResultTopResolver emotionResultTopResolver;

	@Override
	public List<VoiceEmotionResultTop> saveContentsOfTxt(){
		List<VoiceEmotionResultTop> emotionResultTops = emotionResultTopResolver.resolver();
		if (emotionResultTops == null || emotionResultTops.isEmpty()) {
			return null;
		}
		return emotionResultTopRepository.saveAll(emotionResultTops);
	}
}
