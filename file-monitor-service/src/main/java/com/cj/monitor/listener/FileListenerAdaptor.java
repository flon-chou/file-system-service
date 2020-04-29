package com.cj.monitor.listener;

import com.cj.mq.MessageEntity;
import com.cj.utils.DateTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 文件和目录监听适配器
 * @date Date : 2019/04/08 16:27
 */
@Slf4j
@Component
public class FileListenerAdaptor  extends FileAlterationListenerAdaptor {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void onStart(FileAlterationObserver arg0) {
		log.info("begin listening!");
	}

	@Override
	public void onStop(FileAlterationObserver arg0) {
		log.info("end listening!");
	}

	@Override
	public void onDirectoryCreate(File fold) {
		log.info("fold: "+fold.getAbsolutePath()+" is created.");
	}

	@Override
	public void onDirectoryChange(File fold) {
		log.info("fold: "+fold.getAbsolutePath()+" is changed.");
	}

	@Override
	public void onDirectoryDelete(File fold) {
		log.info("fold: "+fold.getAbsolutePath()+" is deleted.");
	}

	@Override
	public void onFileCreate(File file) {
		log.info("file: "+file.getAbsolutePath()+" is created.");
		MessageEntity messageEntity =  new MessageEntity("The index file already appears.",true, DateTool.nowDate());
		rabbitTemplate.convertAndSend(messageEntity);
	}

	@Override
	public void onFileChange(File file) {
		log.info("file: "+file.getAbsolutePath()+" is changed.");
	}

	@Override
	public void onFileDelete(File file) {
		log.info("file: "+file.getAbsolutePath()+" is deleted");
	}
}
