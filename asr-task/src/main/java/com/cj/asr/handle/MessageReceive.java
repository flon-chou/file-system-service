package com.cj.asr.handle;

import com.cj.asr.service.ASRService;
import com.cj.asr.utils.RemoteCmdUtil;
import com.cj.ffmpeg.AsyncConvertTask;
import com.cj.ffmpeg.TempFileGroup;
import com.cj.mq.MessageEntity;
import com.cj.utils.DateTool;
import com.cj.utils.FileTool;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 接收消息中间件的消息
 * @date Date : 2019/04/09 11:09
 */
@Slf4j
@Component
public class MessageReceive {

	@Value("${ffmpeg.source-dir-path}")
	private String sourceDirPath;
	@Value("${ffmpeg.target-dir-path}")
	private String targetDirPath;

	@Autowired
	private ASRService asrService;

	@RabbitListener(
			bindings = @QueueBinding(
					value = @Queue(value = "queue.asr", durable = "true"),
					exchange = @Exchange(value = "file-handle", durable = "true",type = "fanout"),
					key = "index.created"
			)
	)
	@RabbitHandler
	public void process(@Payload MessageEntity message, @Headers Map<String,Object> headers, Channel channel) throws IOException, ExecutionException, InterruptedException {
		AsyncConvertTask asyncConvertTask = new AsyncConvertTask();
		log.info("received handle:{}"+message.getMsg());
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag,false);
		if (message.isFlag()){
			sourceDirPath += File.separator+ message.getDirName();
			List<File> sources = FileTool.scanFiles(sourceDirPath);
			log.info("--------------------------开始执行音频转码任务（wav）----------------------------");
			List<TempFileGroup> tempFileGroups = new ArrayList<>();
			for (File file : sources) {
				if (file.getName().contains(".index")){
					continue;
				}
				String targetPath = targetDirPath+file.getName().split("\\.")[0]+".wav";
				File targetFile = asyncConvertTask.createFile(targetPath).get();
				if (file.getName().contains(".wav")){
					FileTool.copyFile(file,targetFile);
					continue;
				}
				tempFileGroups.add(new TempFileGroup(file,targetFile));
			}
			for (TempFileGroup tempFileGroup : tempFileGroups) {
				asyncConvertTask.convertToWav(tempFileGroup.getSource(),tempFileGroup.getTarget()).get();
			}
			log.info("--------------------------音频转码任务执行结束（wav）----------------------------");
			// TODO  上生产需要修改
			if(/*RemoteCmdUtil.execute()*/ 1==1){
				log.info("------------------------------------- 语音识别结果入库 (start)---------------------------------");
//				asrService.saveAsrResult();
				log.info("------------------------------------- 语音识别结果入库 (end)---------------------------------");
			}
		}
	}

}

