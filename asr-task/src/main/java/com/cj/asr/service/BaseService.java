package com.cj.asr.service;

import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: service  base
 * @date Date : 2019/03/29 11:35
 */
public abstract class BaseService<T> {


	/**
	 * 识别结果TXT文件 -->解析入库
	 * @return 	返回对象 LIst 集合
	 */
	public abstract List<T> saveContentsOfTxt();

}
