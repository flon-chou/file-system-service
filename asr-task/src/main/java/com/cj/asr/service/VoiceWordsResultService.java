package com.cj.asr.service;

import com.cj.asr.dao.VoiceWordsResultRepository;
import com.cj.asr.entity.VoiceTextAbResult;
import com.cj.asr.entity.VoiceWordsResult;
import com.cj.asr.resolver.VoiceWordsResultResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextAbResult Service
 * @date Date : 2019/03/27 11:03
 */
@Slf4j
@Service
public class VoiceWordsResultService extends BaseService<VoiceWordsResult> {

	@Autowired
	private VoiceWordsResultRepository voiceWordsResultRepository;
	@Autowired
	private VoiceWordsResultResolver voiceWordsResultResolver;

	@Override
	public List<VoiceWordsResult> saveContentsOfTxt() {
		List<VoiceWordsResult> list = voiceWordsResultResolver.resolver();
		if (list == null || list.isEmpty()) {
			return null;
		}

		return voiceWordsResultRepository.saveAll(list);
	}
}
