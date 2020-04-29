package com.cj.ffmpeg;

import com.cj.utils.FileTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 异步转码任务
 * @date Date : 2019/03/15 10:04
 */
@Slf4j
public class AsyncConvertTask {

	@Async("rejectedExecutorAsync")
	public Future<String> convertToMp3(File sourceFile, File targetFile) throws ExecutionException, InterruptedException {
		AudioConvert audioConvert = new AudioConvert();
		long start = System.currentTimeMillis();
		audioConvert.covertToMp3(sourceFile,targetFile);
		long end = System.currentTimeMillis();
		log.info("The audio is converted to MP3 format, time elapsed: {} ms.",end-start);
		return new AsyncResult<>("The audio is converted to MP3 format！");
	}

	@Async("rejectedExecutorAsync")
	public Future<String> convertToWav(File sourceFile, File targetFile)  {
		AudioConvert audioConvert = new AudioConvert();
		long start = System.currentTimeMillis();
		audioConvert.covertToWav(sourceFile,targetFile);
		long end = System.currentTimeMillis();
		log.info("The audio is converted to WAV format, time elapsed: {} ms.",end-start);
		return new AsyncResult<>("The audio is converted to WAV format！");
	}

	@Async("rejectedExecutorAsync")
	public Future<File> createFile(String path){
		File targetFile = FileTool.create(path);
		log.info("A file was successfully created.");
		return new AsyncResult<>(targetFile);
	}
}
