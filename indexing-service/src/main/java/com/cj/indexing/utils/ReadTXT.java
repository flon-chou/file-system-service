package com.cj.indexing.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 文件工具类
 * @date Date : 2019/03/11 11:15
 */
@Slf4j
public class ReadTXT {

	/**
	 * 读取索引文件
	 * @param path 文件路径
	 * @return	返回LinkedList数据集合
	 */
	public static List<String> readIndexFile(String path) {
		File file = new File(path);
		List<String> context = null;
		if (file.isFile()){
			context =readFile(file);
		}
		return context;
	}

	/**
	 *  读取文件行信息，每一行代表数据库一行数据
	 * @param file
	 * @return	将行数据插入ArrayList集合
	 */
	public static List<String> readFile(File file){
		List<String> context = null;
		try (FileReader reader = new FileReader(file);
			 // 建立一个对象，它把文件内容转成计算机能读懂的语言
			 BufferedReader br = new BufferedReader(reader))
		{
			String line ;
			context = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				context.add(line);
			}
			log.info(context.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return context;
	}

}
