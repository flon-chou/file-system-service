package com.cj.asr.resolver;

import com.cj.asr.entity.VoiceTextRoleResultEmo;
import com.cj.asr.resolver.exception.ResolverException;
import com.cj.asr.resolver.utils.GenderEnum;
import com.cj.asr.resolver.utils.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextRoleResultEmo Resolver
 * @date Date : 2019/03/29 14:53
 */
@Slf4j
@Component
public class VoiceTextRoleResultEmoResolver extends AbstractBaseResolver<VoiceTextRoleResultEmo> {

	private String filename = "text_role_result_emo.txt";
	private int len = 9;

	@Override
	protected DataFormat formatData() {
		return new DataFormat(len,DIFFERENT_LINE,filename);
	}

	@Override
	protected boolean hasSpecialRowData() {
		return false;
	}

	@Override
	protected List<VoiceTextRoleResultEmo> convert(String[] arr) throws ResolverException {
		if(arr[3].length()!=5){
			throw new ResolverException("文件内容格式不正确！--------  "+arr[3]);
		}
		List<VoiceTextRoleResultEmo> list = null;
		try {
			VoiceTextRoleResultEmo textRoleResultEmo = new VoiceTextRoleResultEmo();
			textRoleResultEmo.setAudioName(getAudioName());
			textRoleResultEmo.setBeginTime(arr[0]);
			textRoleResultEmo.setEndTime(arr[1]);
			textRoleResultEmo.setContent(arr[2]);
			textRoleResultEmo.setRole(RoleEnum.match(arr[3].substring(0,2)));
			textRoleResultEmo.setGender(GenderEnum.match(arr[3].substring(3,4)));
			textRoleResultEmo.setEmotion(arr[4].substring(1,arr[4].length()-1));
			textRoleResultEmo.setCredible(arr[5]);
			textRoleResultEmo.setToneValue(arr[6]);
			textRoleResultEmo.setToneBeginTime(arr[7]);
			textRoleResultEmo.setToneEndTime(arr[8]);
			list = new ArrayList<>();
			list.add(textRoleResultEmo);
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return list;
	}
}
