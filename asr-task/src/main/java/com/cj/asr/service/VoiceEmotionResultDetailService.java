package com.cj.asr.service;

import com.cj.asr.dao.VoiceEmotionResultDetailRepository;
import com.cj.asr.entity.VoiceEmotionResultDetail;
import com.cj.asr.resolver.VoiceEmotionResultDetailResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceEmotionResultDetail Service
 * @date Date : 2019/03/27 11:03
 */
@Slf4j
@Service
public class VoiceEmotionResultDetailService extends BaseService<VoiceEmotionResultDetail>{

	@Autowired
	private VoiceEmotionResultDetailRepository emotionResultDetailRepository;
	@Autowired
	private VoiceEmotionResultDetailResolver emotionResultDetailResolver;

	@Override
	public List<VoiceEmotionResultDetail> saveContentsOfTxt(){
		List<VoiceEmotionResultDetail> list = emotionResultDetailResolver.resolver();
		if (list==null || list.isEmpty()){
			return null;
		}
		return emotionResultDetailRepository.saveAll(list);
	}
}
