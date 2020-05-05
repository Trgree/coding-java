package org.ace.thread;

import java.util.concurrent.*;

/**
 * 线程池
 * @author Liangsj
 *
 */
public class ThreadPool {

	private ExecutorService executor;
	
	/**
	 * 初始化线程池
	 * @param corePoolSize 核心线程数
	 * @param maximumPoolSize 最大线程数，非核心=最大线程数-核心线程数
	 * @param queueSize
	 */
	public ThreadPool(int corePoolSize,
					  int maximumPoolSize, int queueSize) {
		long keepAliveTime = 120L;// 非核心空闲线程存活时间
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(queueSize));

	}
	
	/**
	 * 任务加入执行队列
	 * @param task
	 */
	public void execute(Runnable task) {
		executor.execute(task);
	}

	/**
	 * 关门线程池，不接收新任务，已经添加的任务还会执行
	 */
	public void shutdown(){
		executor.shutdown();
	}

	/**
	 * 关门线程池，不接收新任务，不处理等待的任务，并停止进行运行的任务
	 */
	public void shutdownNow(){
		executor.shutdownNow();
	}
}
