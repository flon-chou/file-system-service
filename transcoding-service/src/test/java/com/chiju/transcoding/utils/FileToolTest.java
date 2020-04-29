package com.cj.transcoding.utils;

import com.cj.utils.FileTool;
import org.junit.Test;

public class FileToolTest {

	@Test
	public void scanFiles() {
		FileTool.scanFiles("C:\\Users\\flonc\\Desktop\\temp");
	}

	@Test
	public void deleteFile(){
		System.out.println(FileTool.deleteFile("C:\\Users\\flonc\\Desktop\\temp"));
	}
}