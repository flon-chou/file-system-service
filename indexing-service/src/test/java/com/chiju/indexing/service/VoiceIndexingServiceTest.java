package com.cj.indexing.service;

import com.cj.indexing.IndexingServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IndexingServiceApplication.class)
public class VoiceIndexingServiceTest {

	@Autowired
	private VoiceIndexingService voiceIndexingService;

	@Test
	public void saveTXT() {
		voiceIndexingService.saveTXT("C:\\Users\\flonc\\Desktop\\temp\\20190325.index");
	}
}