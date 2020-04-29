//package com.cj.asr.utils;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author : Flon
// * @version V1.0
// * @Description: 文件工具类
// * @date Date : 2019/03/11 11:15
// */
//@Slf4j
//public class ReadTXT {
//
//	/**
//	 *  读取文件行信息，每一行代表数据库一行数据
//	 * @param file
//	 * @return	将行数据插入ArrayList集合
//	 */
//	public static List<String> readFile(File file){
//		List<String> context = null;
//		try (FileReader reader = new FileReader(file);
//			 BufferedReader br = new BufferedReader(reader))
//		{
//			String lineData ;
//			context = new ArrayList<>();
//			while ((lineData = br.readLine()) != null) {
//				context.add(lineData);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return context;
//	}
//
////	public static String getFileEncode(String filePath) {
////		String charsetName = null;
////		try {
////			File file = new File(filePath);
////			CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
////			detector.add(new ParsingDetector(false));
////			detector.add(JChardetFacade.getInstance());
////			detector.add(ASCIIDetector.getInstance());
////			detector.add(UnicodeDetector.getInstance());
////			java.nio.charset.Charset charset = null;
////			charset = detector.detectCodepage(file.toURI().toURL());
////			if (charset != null) {
////				charsetName = charset.name();
////			} else {
////				charsetName = "UTF-8";
////			}
////		} catch (Exception ex) {
////			ex.printStackTrace();
////			return null;
////		}
////		return charsetName;
////	}
//}
