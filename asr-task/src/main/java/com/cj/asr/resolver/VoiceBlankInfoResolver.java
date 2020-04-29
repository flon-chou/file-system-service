package com.cj.asr.resolver;

import com.alibaba.fastjson.JSON;
import com.cj.asr.entity.VoiceBlankInfo;
import com.cj.asr.resolver.utils.BlankInfoNoFileName;
import com.cj.asr.resolver.exception.ResolverException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: VoiceBlankInfo Resolver
 * @date Date : 2019/03/27 11:05
 */
@Slf4j
@Component
public class VoiceBlankInfoResolver  extends AbstractBaseResolver<VoiceBlankInfo> {

	private String filename = "blankinfo.txt";
	private int len = 2;

	@Override
	protected DataFormat formatData() {
		return new DataFormat(len,SAME_LINE,filename);
	}

	@Override
	protected boolean hasSpecialRowData() {
		return false;
	}

	/**
	 * 1、	静音结果lineDataArr[1] JSON数组转List对象，如果对象为null则数据保存为null，否则循环遍历List集合获取单个静音结果：开始出现静音的时间、静音时长。
	 * 2、	第1步循环遍历list后获取的数据如果不包含“startBlankPos”和“blankLen”字段则抛出异常日志，并终止解析，否则执行下一步
	 * 3、	将解析后的数据插入数据库，再回到第1步
	 * @param arr 行数据字符串以{@code delimiter}分割后的字符串数组
	 * @return 返回 List
	 */
	@Override
	protected List<VoiceBlankInfo> convert(String[] arr) throws ResolverException{
		setAudioName(arr[0]);
		List<VoiceBlankInfo> blankInfos = null;
		try {
			List<BlankInfoNoFileName> blankInfoNoFileNames = JSON.parseArray(arr[1],BlankInfoNoFileName.class);
			blankInfos = new ArrayList<>(arr.length);
			if (blankInfoNoFileNames==null || blankInfoNoFileNames.isEmpty()){
				VoiceBlankInfo blankInfo = new VoiceBlankInfo();
				blankInfo.setAudioName(getAudioName());
				blankInfos.add(blankInfo);
				return blankInfos;
			}
			for (BlankInfoNoFileName blankInfoNoFileName : blankInfoNoFileNames){
				VoiceBlankInfo blankInfo = new VoiceBlankInfo();
				blankInfo.setAudioName(getAudioName());
				blankInfo.setBlankLen(blankInfoNoFileName.getBlankLen());
				blankInfo.setStartBlankPos(blankInfoNoFileName.getStartBlankPos());
				blankInfos.add(blankInfo);
			}
		} catch (Exception e) {
			throw new ResolverException("文件内容格式不正确！");
		}
		return blankInfos;
	}
}
