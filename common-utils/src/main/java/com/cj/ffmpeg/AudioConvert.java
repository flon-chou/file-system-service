package com.cj.ffmpeg;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.*;

import java.io.File;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 音视频转码
 * @date Date : 2019/03/11 16:57
 */
@Slf4j
@Data
public class AudioConvert {

	/**
	 * ffmpeg 转码命令,命令行后面需要空格 ( -y  覆盖写 )
	 */
//	@Value("${ffmpeg.cmd}")
	private String cmd="ffmpeg -y -i";
	/**
	 * ffmpeg安装路径的bin路径，路径后面必须跟随 '\' 或  '/'
	 */
//	@Value("${ffmpeg.path}")
	private String cmdPath="G:\\ffmpeg\\bin\\ffmpeg.exe";

	ProcessUtil instance = ProcessUtil.instance();

	/**
	 * 将文件音频文件转为mp3格式
	 * @param sourcePath
	 * @param targetPath
	 */
	@Deprecated
	public void changeToMp3(String sourcePath,String targetPath){
		convertFormat(sourcePath,targetPath);
	}

	/**
	 * 将文件音频文件转为mp3格式
	 * @param sourceFile
	 * @param targetFile
	 */
	public void covertToMp3(File sourceFile, File targetFile){
		AudioAttributes audio = new AudioAttributes();
		//设置编码器
		audio.setCodec("libmp3lame");
		//设置声音频道
		audio.setChannels(new Integer(1));
		// 设置采样频率
		audio.setSamplingRate(new Integer(8000));
		EncodingAttributes encodingAttributes = new EncodingAttributes();
		encodingAttributes.setFormat("mp3");
		encodingAttributes.setAudioAttributes(audio);
		FFMPEGLocator locator = new DefaultFFMPEGLocator();
		Encoder encoder = new Encoder();
		try {
			encoder.encode(new MultimediaObject(sourceFile,locator),targetFile,encodingAttributes);
		} catch (EncoderException e) {
			log.error("音频转换成MP3格式失败",e);
		}
	}

	/**
	 * 将文件音频文件转为mp3格式
	 * @param sourceFile
	 * @param targetFile
	 */
	public void covertToWav(File sourceFile, File targetFile){
		AudioAttributes audio = new AudioAttributes();
		//设置编码器
		audio.setCodec("pcm_s16le");
		//设置声音频道
		audio.setChannels(new Integer(2));
		// 设置采样频率
		audio.setSamplingRate(new Integer(8000));
		EncodingAttributes encodingAttributes = new EncodingAttributes();
		encodingAttributes.setFormat("wav");
		encodingAttributes.setAudioAttributes(audio);
		FFMPEGLocator locator = new DefaultFFMPEGLocator();
		Encoder encoder = new Encoder();
		try {
			encoder.encode(new MultimediaObject(sourceFile,locator),targetFile,encodingAttributes);
		} catch (EncoderException e) {
			log.error("音频转换成WAV格式失败",e);
		}
	}

	/**
	 * 将文件音频文件转为mp3格式
	 * @param sourcePath
	 * @param targetPath
	 */
	@Deprecated
	public void batchToMp3(String sourcePath,String targetPath){
		batchConvertFormat(sourcePath,targetPath,"mp3");
	}

	/**
	 * 将音频文件转为wav格式
	 * @param sourcePath
	 * @param targetPath
	 */
	@Deprecated
	public void changeToWav(String sourcePath,String targetPath){
		convertFormat(sourcePath,targetPath);
	}

	/**
	 * 转化音频文件格式
	 * @param sourcePath
	 * @param targetPath
	 */
	@Deprecated
	private void convertFormat(String sourcePath,String targetPath){
		Runtime run = null;
		try {
			run = Runtime.getRuntime();
			instance.process(cmdPath+cmd+"  \""+sourcePath+"\" \""+targetPath+"\"");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//run调用lame解码器最后释放内存
			run.freeMemory();
		}
	}

	/**
	 * 转化音频文件格式
	 * @param sourcePath
	 * @param targetPath
	 */
	@Deprecated
	private void batchConvertFormat(String sourcePath,String targetPath,String format){
		Runtime run = null;
		try {
			run = Runtime.getRuntime();
			instance.process(cmdPath+" for /r . %a in (\""+sourcePath+"\\*\") do ("+cmd+"  %a \""+targetPath+"\\%~na."+format+"\")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//run调用lame解码器最后释放内存
			run.freeMemory();
		}
	}

}
