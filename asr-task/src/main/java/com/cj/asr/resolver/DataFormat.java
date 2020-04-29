package com.cj.asr.resolver;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Flon
 * @version V1.0
 * @Description: data format
 * @date Date : 2019/03/28 11:10
 */
@Data
@AllArgsConstructor
@Slf4j
public class DataFormat {

	/**
	 * 行数据分割后的字符串数组规定长度
	 */
	private int lineDataArrLen;
	/**
	 * 数据格式类型
	 */
	private int formatType;
	/**
	 * 被解析的目标文件名
	 */
	private String filename;

	/**
	 *  校验参数是否赋值
	 * @return 只要有一个属性没赋值则返回 false，否则返回 true
	 */
	public boolean hasValue(){
		if (lineDataArrLen<=0){
			log.error("'lineDataArrLen' has to be greater than zero.");
			return false;
		}
		if (formatType<=0){
			log.error("The 'formatType' setting is incorrect.");
			return false;
		}
		if (StringUtils.isEmpty(filename)){
			log.error("The 'filename' can`t be empty.");
			return false;
		}
		return true;
	}
	
}
