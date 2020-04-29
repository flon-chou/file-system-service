package com.cj.asr.resolver.utils;

import lombok.Data;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 没有文件名的VoiceBlankInfo
 * @date Date : 2019/03/28 15:10
 */
@Data
public class BlankInfoNoFileName {

	/**
	 * 开始出现静音的时间
	 */
	private Integer startBlankPos;

	/**
	 * 静音时长
	 */
	private Integer blankLen;
}
