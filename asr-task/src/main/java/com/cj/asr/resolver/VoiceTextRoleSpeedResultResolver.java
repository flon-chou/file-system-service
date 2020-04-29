package com.cj.asr.resolver;

import com.cj.asr.entity.VoiceTextRoleSpeedResult;
import com.cj.asr.resolver.exception.ResolverException;
import com.cj.asr.resolver.utils.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextRoleSpeedResult resolver
 * @date Date : 2019/03/29 14:57
 */
@Slf4j
@Component
public class VoiceTextRoleSpeedResultResolver extends AbstractBaseResolver<VoiceTextRoleSpeedResult> {

	private String filename = "text_role_speed_result.txt";
	private int len = 5;
	private String specialRowDataDelimiter = "=";

	@Override
	protected DataFormat formatData() {
		return new DataFormat(len,DIFFERENT_LINE,filename);
	}

	@Override
	protected boolean hasSpecialRowData() {
		return true;
	}

	@Override
	protected List<VoiceTextRoleSpeedResult> convert(String[] arr) throws ResolverException {
		if (arr.length==1){
			addSpecialRowData(new SpecialRowData(arr[0],specialRowDataDelimiter,getAudioName()));
			return null;
		}
		String[] speed = arr[4].split("=");
		if (speed==null || speed.length!=2){
			throw new ResolverException("文件内容格式不正确！--------  "+arr[4]);
		}
		List<VoiceTextRoleSpeedResult> list = null;
		try {
			VoiceTextRoleSpeedResult textRoleSpeedResult = new VoiceTextRoleSpeedResult();
			textRoleSpeedResult.setAudioName(getAudioName());
			textRoleSpeedResult.setBeginTime(arr[0]);
			textRoleSpeedResult.setEndTime(arr[1]);
			textRoleSpeedResult.setContent(arr[2]);
			textRoleSpeedResult.setRole(RoleEnum.match(arr[3]));
			textRoleSpeedResult.setSpeed(speed[1]);
			list = new ArrayList<>();
			list.add(textRoleSpeedResult);
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return list;
	}
}
