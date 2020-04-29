package com.cj.transcoding.convert;

import com.cj.ffmpeg.AsyncConvertTask;
import com.cj.ffmpeg.AudioConvert;
import com.cj.transcoding.TranscodingServiceApplication;
import com.cj.utils.FileTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TranscodingServiceApplication.class)
public class AudioConvertTest {

	private AsyncConvertTask asyncConvertTask;

	@Test
	public void changeToMp3() {
		AudioConvert audioConvert = new AudioConvert();
//		List<File> list = FileTool.scanFiles("C:\\Users\\flonc\\Desktop\\temp\\收集呼叫中心录音文件\\中美大都会电销录音");
		long start = System.currentTimeMillis();
//		for (File file : list) {
//			audioConvert.changeToMp3(file.getAbsolutePath(),"C:\\Users\\flonc\\Desktop\\temp\\收集呼叫中心录音文件\\转码文件\\"+file.getName().split("\\.")[0]+".mp3");
//		}
		audioConvert.batchToMp3("C:\\Users\\flonc\\Desktop\\temp\\收集呼叫中心录音文件\\中美大都会电销录音","C:\\Users\\flonc\\Desktop\\temp\\收集呼叫中心录音文件\\转码文件");
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)+"ms");
	}

	@Test
	public void covertToMp3() throws ExecutionException, InterruptedException {
		AudioConvert audioConvert = new AudioConvert();
		if(asyncConvertTask==null){
			asyncConvertTask = new AsyncConvertTask();
		}
		long start = System.currentTimeMillis();
		List<File> list = FileTool.scanFiles("C:\\Users\\flonc\\Desktop\\temp\\新建文件夹");
		for (File file : list) {
			File targetFile = asyncConvertTask.createFile("C:\\Users\\flonc\\Desktop\\temp\\新建文件夹\\"+file.getName().split("\\.")[0]+".mp3").get();
			asyncConvertTask.convertToMp3(file,targetFile);
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)+"ms");
	}
}