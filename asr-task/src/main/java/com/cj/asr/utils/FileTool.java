//package com.cj.asr.utils;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author : Flon
// * @version V1.0
// * @Description: 文件工具类
// * @date Date : 2019/03/11 11:15
// */
//@Slf4j
//public class FileTool {
//
//	/**
//	 * 读取某个文件夹下的所有文件
//	 * @param path 文件夹路径
//	 * @return
//	 */
//	public static List<File> scanFiles(String path) {
//		File[] files = readFiles(path);
//		if(files==null){
//			return null;
//		}
//		List<File> list = new ArrayList<>();
//		log.info("----------------------开始扫描文件目录-----------------------");
//		int sum = 0;
//		for (File file : files) {
//			if (!file.isDirectory()) {
//				list.add(file);
//				sum +=1;
//				log.info(file.getAbsolutePath());
//			}
//		}
//		log.info("-----------------结束扫描，一共加载【"+sum+"】个文件-----------------");
//		return list;
//	}
//
//	/**
//	 * 删除某个文件夹下的所有文件和文件夹
//	 * @param path 文件夹路径
//	 * @return
//	 */
//	public static boolean deleteFile(String path) {
//		File dir = new File(path);
//		if (!dir.isDirectory()) {
//			dir.delete();
//		} else if (dir.isDirectory()) {
//			for (File file : dir.listFiles()) {
//				if (!file.isDirectory()) {
//					file.delete();
//				} else {
//					deleteFile(file.getAbsolutePath());
//				}
//			}
//		}
//		return dir.delete();
//	}
//
//	/**
//	 * 获取文件夹下所有文件及文件目录
//	 * @param path
//	 * @return
//	 */
//	public static File[] readFiles(String path){
//		File[] files = null;
//		File dir = new File(path);
//		if (!dir.isDirectory()) {
//			log.error("不是一个文件目录，请检查路径是否正确。");
//		} else if (dir.isDirectory()) {
//			 files = dir.listFiles();
//		}
//		return files;
//	}
//
//	public static File create(String path){
//		File file = new File(path);
//		return file;
//	}
//
//	/**
//	 *  拷贝文件
//	 * @param oldFile
//	 * @param newFilePath
//	 */
//	public static void copyFile(File oldFile,String newFilePath) {
//		try {
//			int bytesum = 0;
//			int byteread = 0;
//			if (oldFile.exists()) {
//				InputStream in = new FileInputStream(oldFile);
//				FileOutputStream fos = new FileOutputStream(newFilePath);
//				byte[] buffer = new byte[1444];
//				int length;
//				while ((byteread = in.read(buffer)) != -1) {
//					bytesum += byteread;
//					fos.write(buffer, 0, byteread);
//				}
//				in.close();
//				log.info("A file was copied successfully.");
//			}
//		} catch (Exception e) {
//			log.error("拷贝单个文件操作出错");
//		}
//	}
//}
