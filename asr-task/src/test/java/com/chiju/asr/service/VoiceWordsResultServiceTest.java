package com.chiju.asr.service;

import com.cj.asr.AsrTaskApplication;
import com.cj.asr.service.VoiceTextAbResultService;
import com.cj.asr.service.VoiceWordsResultService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AsrTaskApplication.class)
public class VoiceWordsResultServiceTest {

	@Autowired
	private VoiceWordsResultService voiceWordsResultService;

	@Test
	public void saveContentsOfTxt() {
		voiceWordsResultService.saveContentsOfTxt();
	}
}