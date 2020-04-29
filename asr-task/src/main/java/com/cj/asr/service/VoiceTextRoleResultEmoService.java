package com.cj.asr.service;

import com.cj.asr.dao.VoiceTextRoleResultEmoRepository;
import com.cj.asr.entity.VoiceTextRoleResultEmo;
import com.cj.asr.resolver.VoiceTextRoleResultEmoResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextRoleResultEmo Service
 * @date Date : 2019/03/27 11:02
 */
@Slf4j
@Service
public class VoiceTextRoleResultEmoService extends BaseService<VoiceTextRoleResultEmo> {

	@Autowired
	private VoiceTextRoleResultEmoRepository textRoleResultEmoRepository;
	@Autowired
	private VoiceTextRoleResultEmoResolver textRoleResultEmoResolver;

	@Override
	public List<VoiceTextRoleResultEmo> saveContentsOfTxt() {
		List<VoiceTextRoleResultEmo> list = textRoleResultEmoResolver.resolver();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return textRoleResultEmoRepository.saveAll(list);
	}
}
