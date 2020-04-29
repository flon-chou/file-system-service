package com.cj.asr.service;

import com.cj.asr.dao.VoiceTextRoleSpeedAverageRepository;
import com.cj.asr.dao.VoiceTextRoleSpeedResultRepository;
import com.cj.asr.entity.VoiceTextRoleSpeedAverage;
import com.cj.asr.entity.VoiceTextRoleSpeedResult;
import com.cj.asr.resolver.SpecialRowData;
import com.cj.asr.resolver.VoiceTextRoleSpeedResultResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceTextRoleSpeedResult Service
 * @date Date : 2019/03/27 11:02
 */
@Slf4j
@Service
public class VoiceTextRoleSpeedResultService extends BaseService<VoiceTextRoleSpeedResult> {

	@Autowired
	private VoiceTextRoleSpeedResultRepository textRoleSpeedResultRepository;
	@Autowired
	private VoiceTextRoleSpeedAverageRepository textRoleSpeedAverageRepository;
	@Autowired
	private VoiceTextRoleSpeedResultResolver textRoleSpeedResultResolver;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<VoiceTextRoleSpeedResult> saveContentsOfTxt() {
		List<VoiceTextRoleSpeedResult>  list = textRoleSpeedResultResolver.resolver();
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<SpecialRowData> specialRowDataList = textRoleSpeedResultResolver.specialRowDataList;
		List<VoiceTextRoleSpeedAverage> tempTextRoleSpeedAverages = null;
		if (specialRowDataList!=null ){
			tempTextRoleSpeedAverages = convert(specialRowDataList);
		}
		if (tempTextRoleSpeedAverages!=null && ! tempTextRoleSpeedAverages.isEmpty()){
			textRoleSpeedAverageRepository.saveAll(tempTextRoleSpeedAverages);
		}
		return textRoleSpeedResultRepository.saveAll(list);
	}

	private List<VoiceTextRoleSpeedAverage> convert(List<SpecialRowData> specialRowDataList) {
		List<VoiceTextRoleSpeedAverage> tempTextRoleSpeedAverages = null;
		try {
			List<VoiceTextRoleSpeedAverage> textRoleSpeedAverages = new ArrayList<>();
			tempTextRoleSpeedAverages = new ArrayList<>();
			for (int i = 0,len = specialRowDataList.size();i<len; i+=3){
				VoiceTextRoleSpeedAverage textRoleSpeedAverage = new VoiceTextRoleSpeedAverage();
				textRoleSpeedAverage.setAudioName(specialRowDataList.get(i).getAudioName());
				textRoleSpeedAverage.setSpeedService(Double.valueOf(specialRowDataList.get(i).getRowData().split(specialRowDataList.get(i).getDelimiter())[1]));
				textRoleSpeedAverage.setSpeedClient(Double.valueOf(specialRowDataList.get(i+1).getRowData().split(specialRowDataList.get(i+1).getDelimiter())[1]));
				textRoleSpeedAverage.setSpeedMix(Double.valueOf(specialRowDataList.get(i+2).getRowData().split(specialRowDataList.get(i+2).getDelimiter())[1]));
				tempTextRoleSpeedAverages.add(textRoleSpeedAverage);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempTextRoleSpeedAverages;
	}
}
