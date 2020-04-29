package com.cj.asr.resolver;

import com.cj.asr.entity.VoiceEmotionResultDetail;
import com.cj.asr.resolver.exception.ResolverException;
import com.cj.asr.resolver.utils.GenderEnum;
import com.cj.asr.resolver.utils.RoleEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceEmotionResultDetail  Resolver
 * @date Date : 2019/03/28 11:41
 */
@Component
public class VoiceEmotionResultDetailResolver extends AbstractBaseResolver<VoiceEmotionResultDetail> {

	private String filename = "emotion_result_detail.txt";
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
	protected List<VoiceEmotionResultDetail> convert(String[] arr) throws ResolverException {
		if(arr[4].length()!=5){
			throw new ResolverException("文件内容格式不正确！--------  "+arr[4]);
		}
		List<VoiceEmotionResultDetail> list = null;
		try {
			VoiceEmotionResultDetail emotionResultDetail = new VoiceEmotionResultDetail();
			emotionResultDetail.setAudioName(getAudioName());
			emotionResultDetail.setResult(arr[0]);
			emotionResultDetail.setBeginTime(arr[1]);
			emotionResultDetail.setEndTime(arr[2]);
			emotionResultDetail.setCredible(arr[3]);
			emotionResultDetail.setRole(RoleEnum.match(arr[4].substring(0,2)));
			emotionResultDetail.setGender(GenderEnum.match(arr[4].substring(3,4)));
			list = new ArrayList<>();
			list.add(emotionResultDetail);
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return list;
	}
}
