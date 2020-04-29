package com.cj.asr.service;

import com.cj.asr.dao.VoiceTextAbResultRepository;
import com.cj.asr.entity.VoiceTextAbResult;
import com.cj.asr.resolver.VoiceTextAbResultResolver;
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
public class VoiceTextAbResultService extends BaseService<VoiceTextAbResult> {

	@Autowired
	private VoiceTextAbResultRepository textAbResultRepository;
	@Autowired
	private VoiceTextAbResultResolver textAbResultResolver;

	@Override
	public List<VoiceTextAbResult> saveContentsOfTxt() {
		List<VoiceTextAbResult> list = textAbResultResolver.resolver();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return textAbResultRepository.saveAll(list);
	}
}
