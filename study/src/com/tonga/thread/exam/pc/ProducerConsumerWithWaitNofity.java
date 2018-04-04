package com.tonga.thread.exam.pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生产者-消费组，wait 和 notify实现
 * 生产者生产数据到缓冲区中，消费者从缓冲区中取数据。
 * 如果缓冲区已经满了，则生产者线程阻塞； 
 * 如果缓冲区为空，那么消费者线程阻塞。
 * 
 * synchronized、wait和notify实现
 * 
 * <p>Title: ProducerConsumer.java</p>  
 * @author tangjia
 * @date 2018-3-13 下午5:51:46 
 * @version 1.0
 */
public class ProducerConsumerWithWaitNofity {
    public static void main(String[] args) {
    	final Resource resource = new Resource(10);
    	ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			exec.execute(new Runnable() {
				public void run() {
					new Producer(resource).start();
				}
			});
	    	exec.execute(new Runnable() {
				public void run() {
					new Consumer(resource).start();
				}
			});
		}
    	
    	exec.shutdown();
    	
    }
}

/**
 * 公共资源类
 * <p>Title: ProducerConsumerWithWaitNofity.java</p>  
 * @author tangjia
 * @date 2018-3-14 上午9:48:45 
 * @version 1.0
 */
class Resource{
	int num;
	int size;
	
	public Resource(int size){
		this.size = size;
	}
	
	public synchronized void add() {
		try {
			if (num < size) {
				num++;
				System.out.println(Thread.currentThread().getName()+"-[producer]生产资源成功，总共资源：" + num);
				notifyAll();	//通知消费者消费资源
			}else{
				System.out.println(Thread.currentThread().getName()+"-[producer]资源已满，等待...");
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 消费组消费资源
	 */
	public synchronized void remove(){
		try {
			if(num > 0){
				num--;
				System.out.println(Thread.currentThread().getName()+"-[consumer]消费资源成功，剩余资源：" + num);
				notifyAll();	//通知生产者生产资源
			}else{
				System.out.println(Thread.currentThread().getName()+"-[consumer]暂时无资源，等待...");
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
 * 消费者线程
 * <p>Title: ProducerConsumerWithWaitNofity.java</p>  
 * @author tangjia
 * @date 2018-3-14 上午11:25:53 
 * @version 1.0
 */
class Consumer extends Thread {
	Resource resource;
	
	public Consumer(Resource resource){
		this.resource = resource;
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
			resource.remove();
		}
	}
}

class Producer extends Thread{
	Resource resource;
	
	public Producer(Resource resource){
		this.resource = resource;
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
			resource.add();
		}
	}
}
    
