package com.cj.asr.resolver;

import com.cj.asr.entity.VoiceWordsResult;
import com.cj.asr.resolver.exception.ResolverException;
import com.cj.asr.resolver.utils.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextAbResult Resolver
 * @date Date : 2019/03/29 14:42
 */
@Slf4j
@Component
public class VoiceWordsResultResolver extends AbstractBaseResolver<VoiceWordsResult> {

	private String filename = "words_result1.txt";
	private int len = 5;

	@Override
	protected DataFormat formatData() {
		return new DataFormat(len,DIFFERENT_LINE,filename);
	}

	@Override
	protected boolean hasSpecialRowData() {
		return false;
	}

	@Override
	protected List<VoiceWordsResult> convert(String[] arr) throws ResolverException {
		List<VoiceWordsResult> list = null;
		try {
			VoiceWordsResult voiceWordsResult = new VoiceWordsResult();
			voiceWordsResult.setFileName(getAudioName().substring(0,getAudioName().lastIndexOf(".")));
			voiceWordsResult.setBeginTime(arr[0]);
			voiceWordsResult.setEndTime(arr[1]);
			voiceWordsResult.setContent(arr[2]);
			voiceWordsResult.setRole(arr[4]);
			list = new ArrayList<>();
			list.add(voiceWordsResult);
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return list;
	}
}
