package com.cj.asr.service;

import com.cj.asr.AsrTaskApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AsrTaskApplication.class)
public class VoiceTextAbResultServiceTest {

	@Autowired
	private VoiceTextAbResultService textAbResultService;

	@Test
	public void saveContentsOfTxt() {
		textAbResultService.saveContentsOfTxt();
	}
}