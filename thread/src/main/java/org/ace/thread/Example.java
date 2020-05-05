package org.ace.thread;

import java.util.concurrent.*;

public class Example {

	public static void main(String[] args) throws InterruptedException {
		ThreadPool threadPool = new ThreadPool(2,3, 100);
		for(int i=0; i < 3; i++){
			ExampleTask t = new ExampleTask(i);
			threadPool.execute(t);
		}

		TimeUnit.SECONDS.sleep(30);
		for(int i=4; i < 7; i++){
			ExampleTask t = new ExampleTask(i);
			threadPool.execute(t);
		}
//		threadPool.shutdown();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
//		ConcurrentHashMap

		int corePoolSize = 2;// 核心线程数
		int maximumPoolsize = 3; // 最大线程数，非核心=最大线程数-核心线程数
		long keepAliveTime = 120; // 非核心空闲线程存活时间
		ExecutorService executor = new ThreadPoolExecutor(2, 3, 120, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(1000));

		for(int i=0; i < 3; i++){
			executor.execute(new ExampleTask(i));
		}
		executor.shutdownNow();
	}
}
