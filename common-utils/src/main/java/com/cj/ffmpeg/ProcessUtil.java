package com.cj.ffmpeg;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Flon
 * @version V1.0
 * @Description:
 * 	任务一般分为：CPU密集型、IO密集型、混合型，对于不同类型的任务需要分配不同大小的线程池
 *
 * 1、CPU密集型
 *
 * 尽量使用较小的线程池，一般Cpu核心数+1
 *
 * 因为CPU密集型任务CPU的使用率很高，若开过多的线程，只能增加线程上下文的切换次数，带来额外的开销
 *
 * 2、IO密集型
 *
 * 方法一：可以使用较大的线程池，一般CPU核心数 * 2
 *
 * IO密集型CPU使用率不高，可以让CPU等待IO的时候处理别的任务，充分利用cpu时间
 *
 * 方法二：线程等待时间所占比例越高，需要越多线程。线程CPU时间所占比例越高，需要越少线程。
 * 下面举个例子：
 * 比如平均每个线程CPU运行时间为0.5s，而线程等待时间（非CPU运行时间，比如IO）为1.5s，CPU核心数为8，那么根据上面这个公式估算得到：((0.5+1.5)/0.5)*8=32。这个公式进一步转化为：
 * 最佳线程数目 = （线程等待时间与线程CPU时间之比 + 1）* CPU数目
 * 3、混合型
 * 可以将任务分为CPU密集型和IO密集型，然后分别使用不同的线程池去处理，按情况而定
 * @date Date : 2019/03/11 17:55
 */
@Slf4j
public class ProcessUtil {

	/**
	 * 进程输入流的缓冲区大小，用于读取进程的输出
	 */
	public static final int BUFFER_SIZE = 65536;

	public static final int EXEC_TIME_OUT = 2;

	private ExecutorService exec;

	private ProcessUtil() {
		exec = new ThreadPoolExecutor(6,
				12,
				1,
				TimeUnit.MINUTES,
				new LinkedBlockingQueue<>(10),
				new CustomThreadFactory("cmd-process"),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * 初始化ProcessUtil
	 * @return
	 */
	public static ProcessUtil instance() {
		return InputStreamConsumer.instance;
	}


	/**
	 * 简单的封装， 执行cmd命令
	 *
	 *
	 * @param cmd 待执行的操作命令
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public boolean process(String cmd) throws Exception {
		Process process = Runtime.getRuntime().exec(cmd);
		waitForProcess(process);
		return true;
	}

	/**
	 * 执行流程输入/输出，等待流程终止。
	 */
	private int waitForProcess(final Process pProcess)
			throws IOException, InterruptedException, TimeoutException, ExecutionException {
		// 并行处理stdout和子进程的stderr
		//如果有很多stderr输出(例如，通过convert从ghostscript调用)，这可以防止在Windows下死锁
		FutureTask<Object> outTask = new FutureTask<Object>(() -> {
			processOutput(pProcess.getInputStream(), InputStreamConsumer.DEFAULT_CONSUMER);
			return null;
		});
		exec.submit(outTask);

		FutureTask<Object> errTask = new FutureTask<Object>(() -> {
			processError(pProcess.getErrorStream(), InputStreamConsumer.DEFAULT_CONSUMER);
			return null;
		});
		exec.submit(errTask);

		// 等待并检查IO异常(FutureTask.get()块)
		try {
			outTask.get();
			errTask.get();
		} catch (ExecutionException e) {
			Throwable t = e.getCause();
			if (t instanceof IOException) {
				throw (IOException) t;
			} else if (t instanceof RuntimeException) {
				throw (RuntimeException) t;
			} else {
				throw new IllegalStateException(e);
			}
		}

		FutureTask<Integer> processTask = new FutureTask<Integer>(() -> {
			pProcess.waitFor();
			return pProcess.exitValue();
		});
		exec.submit(processTask);


		// 设置超时时间，防止死等
		int rc = processTask.get(EXEC_TIME_OUT, TimeUnit.SECONDS);


		// 为了安全起见
		try {
			pProcess.getInputStream().close();
			pProcess.getOutputStream().close();
			pProcess.getErrorStream().close();
		} catch (Exception e) {
			log.error("close stream error! e: {}", e);
		}

		return rc;
	}


	/**
	 * 让OutputConsumer处理命令的输出
	 * <p>
	 * 方便后续对输出流的扩展
	 */
	private void processOutput(InputStream pInputStream,
							   InputStreamConsumer pConsumer) throws IOException {
		pConsumer.consume(pInputStream);
	}

	/**
	 * 让ErrorConsumer处理stderr流。
	 * <p>
	 * 方便对后续异常流的处理
	 */
	private void processError(InputStream pInputStream,
							  InputStreamConsumer pConsumer) throws IOException {
		pConsumer.consume(pInputStream);
	}


	private static class InputStreamConsumer {
		static com.cj.ffmpeg.ProcessUtil instance = new com.cj.ffmpeg.ProcessUtil();
		static InputStreamConsumer DEFAULT_CONSUMER = new InputStreamConsumer();

		void consume(InputStream stream) throws IOException {
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream), BUFFER_SIZE);
			String temp;
			while ((temp = reader.readLine()) != null) {
				builder.append(temp);
			}
			if (log.isDebugEnabled()) {
				log.debug("cmd process input stream: {}", builder.toString());
			}
			reader.close();
		}
	}

	private static class CustomThreadFactory implements ThreadFactory {
		private String name;
		private AtomicInteger count = new AtomicInteger(0);

		public 	CustomThreadFactory(String name) {
			this.name = name;
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, name + "-" + count.addAndGet(1));
		}
	}

}
