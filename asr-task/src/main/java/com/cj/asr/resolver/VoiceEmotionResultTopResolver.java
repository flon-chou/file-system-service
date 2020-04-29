package com.cj.asr.resolver;

import com.cj.asr.entity.VoiceEmotionResultTop;
import com.cj.asr.resolver.exception.ResolverException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceEmotionResultTop  Resolver
 * @date Date : 2019/03/29 11:13
 */
@Slf4j
@Component
public class VoiceEmotionResultTopResolver extends AbstractBaseResolver<VoiceEmotionResultTop> {

	private String filename = "emotion_result_top.txt";
	private int len = 3;

	@Override
	protected DataFormat formatData() {
		return new DataFormat(len,SAME_LINE,filename);
	}

	@Override
	protected boolean hasSpecialRowData() {
		return false;
	}

	@Override
	protected List<VoiceEmotionResultTop> convert(String[] arr) throws ResolverException {
		List<VoiceEmotionResultTop> list = null;
		try {
			VoiceEmotionResultTop emotionResultTop = new VoiceEmotionResultTop();
			emotionResultTop.setAudioName(parseAudioName(arr[0]));
			emotionResultTop.setResult(arr[1]);
			emotionResultTop.setCredible(arr[2]);
			list = new ArrayList<>();
			list.add(emotionResultTop);
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return list;
	}
}
