package com.cj.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 自定义实现ThreadFactory
 * @date Date : 2019/03/15 9:41
 */
public class DefThreadFactory implements ThreadFactory {
	private String name;
	private AtomicInteger count = new AtomicInteger(0);

	public DefThreadFactory(String name) {
		this.name = name;
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, name + "-" + count.addAndGet(1));
	}
}
