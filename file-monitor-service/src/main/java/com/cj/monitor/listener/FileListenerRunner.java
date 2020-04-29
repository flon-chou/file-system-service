package com.cj.monitor.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 服务启动执行任务
 * @date Date : 2019/04/08 17:02
 */
@Slf4j
@Component
public class FileListenerRunner implements CommandLineRunner {

	@Autowired
	private FileListenerFactory fileListenerFactory;

	@Override
	public void run(String... args) throws Exception {
		// 创建监听者
		FileAlterationMonitor fileAlterationMonitor = fileListenerFactory. initMonitor();
		try {
			fileAlterationMonitor.start();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
