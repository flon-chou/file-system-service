package com.cj.asr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Flon
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019/04/10 9:31
 */
@Service
public class ASRService {

	@Autowired
	private VoiceBlankInfoService blankInfoService;
	@Autowired
	private VoiceEmotionResultDetailService emotionResultDetailService;
	@Autowired
	private VoiceEmotionResultTopService emotionResultTopService;
	@Autowired
	private VoiceTextAbResultService textAbResultService;
	@Autowired
	private VoiceTextRoleResultEmoService textRoleResultEmoService;
	@Autowired
	private VoiceTextRoleSpeedResultService textRoleSpeedResultService;
	@Autowired
	private VoiceWordsResultService voiceWordsResultService;

	@Transactional(rollbackFor = Exception.class)
	public void saveAsrResult(){
		blankInfoService.saveContentsOfTxt();
		emotionResultDetailService.saveContentsOfTxt();
		emotionResultTopService.saveContentsOfTxt();
		textAbResultService.saveContentsOfTxt();
		textRoleResultEmoService.saveContentsOfTxt();
		textRoleSpeedResultService.saveContentsOfTxt();
		voiceWordsResultService.saveContentsOfTxt();
	}

}
