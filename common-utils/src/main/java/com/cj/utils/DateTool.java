package com.cj.utils;

import java.time.LocalDate;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 日期工具类
 * @date Date : 2019/04/29 14:46
 */
public class DateTool {

	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String  nowDate(){
		LocalDate date = LocalDate.now();
		return date.toString();
	}

	public static void main(String[] args) {
		System.out.println(nowDate());
	}
}
