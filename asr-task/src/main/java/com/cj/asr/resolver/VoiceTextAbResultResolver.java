package com.cj.asr.resolver;

import com.cj.asr.entity.VoiceTextAbResult;
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
public class VoiceTextAbResultResolver extends AbstractBaseResolver<VoiceTextAbResult> {

	private String filename = "text_AB_result.txt";
	private int len = 4;

	@Override
	protected DataFormat formatData() {
		return new DataFormat(len,DIFFERENT_LINE,filename);
	}

	@Override
	protected boolean hasSpecialRowData() {
		return false;
	}

	@Override
	protected List<VoiceTextAbResult> convert(String[] arr) throws ResolverException {
		List<VoiceTextAbResult> list = null;
		try {
			VoiceTextAbResult textAbResult = new VoiceTextAbResult();
			textAbResult.setAudioName(getAudioName());
			textAbResult.setBeginTime(arr[0]);
			textAbResult.setEndTime(arr[1]);
			textAbResult.setContent(arr[2]);
			textAbResult.setRole(RoleEnum.match(arr[3]));
			list = new ArrayList<>();
			list.add(textAbResult);
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return list;
	}
}
