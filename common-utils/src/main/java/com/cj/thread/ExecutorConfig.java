package com.cj.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 线程池配置
 * @date Date : 2019/03/15 10:49
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {
	/**
	 *
	 *  获取活跃的 cpu数量
	 */
	private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
	private final static BlockingQueue<Runnable> workQueue;
	private static ThreadFactory threadFactory;

	static {
		workQueue = new LinkedBlockingQueue<Runnable>();
		//默认的工厂方法将新创建的线程命名为：pool-[虚拟机中线程池编号]-thread-[线程编号]
		//mThreadFactory= Executors.defaultThreadFactory();
		threadFactory = new DefThreadFactory("DefFactory");
	}

	@Bean
	public Executor simpleExecutorAsync() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(NUMBER_OF_CORES/2);
		executor.setMaxPoolSize(NUMBER_OF_CORES+1);
		executor.setBeanName("SimpleAsync");
		//用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
		executor.setWaitForTasksToCompleteOnShutdown(true);
		//该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
		executor.setAwaitTerminationSeconds(60);
		executor.initialize();
		return executor;
	}

	@Bean
	public Executor rejectedExecutorAsync() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(NUMBER_OF_CORES/2);
		executor.setMaxPoolSize(NUMBER_OF_CORES+1);
		executor.setThreadFactory(threadFactory);
		executor.setBeanName("RejectedAsync");
		//用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
		executor.setWaitForTasksToCompleteOnShutdown(true);
		//该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
		executor.setAwaitTerminationSeconds(60*15);
		// rejection-policy：当pool已经达到max size的时候，执行任务（这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功） 如果执行器已关闭,则丢弃.
		// CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
}
