package com.tonga.thread.exam.pc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockingQueue方式实现生产者-消费者
 * 
 * <p>Title: BlockingQueueConsumerProducer.java</p>  
 * @author tangjia
 * @date 2018-3-14 下午3:57:08 
 * @version 1.0
 */
public class BlockingQueueConsumerProducer {
	public static void main(String[] args) {
		final Resource2 resource2 = new Resource2();
		
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			exec.execute(new Runnable() {
				public void run() {
					new Producer2(resource2).start();
				}
			});
			exec.execute(new Runnable() {
				public void run() {
					new Customer2(resource2).start();
				}
			});
		}
		
		exec.shutdown();
	}
}

class Resource2{
	BlockingQueue resourceQueue = new LinkedBlockingQueue<>(10);
	
	public void add() {
		try {
			resourceQueue.put(1);
			System.out.println(Thread.currentThread().getName()+"-[producer]生产资源成功，总共资源：" + resourceQueue.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remove() {
		try {
			resourceQueue.take();
			System.out.println(Thread.currentThread().getName()+"-[consumer]消费资源成功，剩余资源：" + resourceQueue.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Customer2 extends Thread{
	Resource2 resource2;
	public Customer2(Resource2 resource2){
		this.resource2 = resource2;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resource2.remove();
		}
	}
}

class Producer2 extends Thread{
	Resource2 resource2;
	public Producer2(Resource2 resource2){
		this.resource2 = resource2;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resource2.add();
		}
	}
}



