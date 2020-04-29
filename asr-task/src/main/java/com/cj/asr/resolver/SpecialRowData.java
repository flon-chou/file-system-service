package com.cj.asr.resolver;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 特殊行数据
 * @date Date : 2019/03/29 23:48
 */
@Data
@AllArgsConstructor
public class SpecialRowData {

	/**
	 * 行数据字符串
	 */
	private String rowData;
	/**
	 * 行数据分隔符
	 */
	private String delimiter;
	/**
	 * 音频文件名
	 */
	private String audioName;

}
