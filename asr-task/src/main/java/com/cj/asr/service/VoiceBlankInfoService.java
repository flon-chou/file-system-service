package com.cj.asr.service;

import com.cj.asr.dao.VoiceBlankInfoRepository;
import com.cj.asr.entity.VoiceBlankInfo;
import com.cj.asr.resolver.VoiceBlankInfoResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceBlankInfo Service
 * @date Date : 2019/03/27 11:03
 */
@Slf4j
@Service
public class VoiceBlankInfoService extends BaseService<VoiceBlankInfo>{

	@Autowired
	private VoiceBlankInfoRepository blankInfoRepository;
	@Autowired
	private VoiceBlankInfoResolver blankInfoResolver;

	@Override
	public List<VoiceBlankInfo> saveContentsOfTxt(){
		List<VoiceBlankInfo> list = blankInfoResolver.resolver();
		if (list==null || list.isEmpty()){
			return null;
		}
		return blankInfoRepository.saveAll(list);
	}
}
