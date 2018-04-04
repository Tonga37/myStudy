package com.tonga.thread.exam.pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock、Condition方式实现生产者-消费者
 * <p>Title: LockCondition.java</p>  
 * @author tangjia
 * @date 2018-3-14 下午2:25:48 
 * @version 1.0
 */
public class LockCondition {
	
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Condition producerCondition = lock.newCondition();
		Condition customerCondition = lock.newCondition();
		final Resource1 resource1 = new Resource1(10, lock, producerCondition, customerCondition);
		
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			exec.execute(new Runnable() {
				public void run() {
					new Producer1(resource1).start();
				}
			});
			exec.execute(new Runnable() {
				public void run() {
					new Customer1(resource1).start();
				}
			});
		}
		
		exec.shutdown();
		
	}
	
	
}

class Resource1{
	private int num;
	private int size;
	private Lock lock;
	private Condition producerCondition;
	private Condition customerCondition;
	
	public Resource1(int size, Lock lock, Condition producercCondition, Condition customerCondition){
		this.size = size;
		this.lock = lock;
		this.producerCondition = producercCondition;
		this.customerCondition = customerCondition;
	}
	
	public void add(){
		lock.lock();
		try {
			if(num < size){
				num++;
				System.out.println(Thread.currentThread().getName()+"-[producer]生产资源成功，总共资源：" + num);
				customerCondition.signalAll();
			}else {
				System.out.println(Thread.currentThread().getName()+"-[producer]资源已满，等待...");
				producerCondition.await();	//此处千万不要调用wait()方法，会出现IllegalMonitorStateException异常
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void remove(){
		lock.lock();
		try {
			if(num>0){
				num--;
				System.out.println(Thread.currentThread().getName()+"-[consumer]消费资源成功，剩余资源：" + num);
				producerCondition.signalAll();
			}else{
				System.out.println(Thread.currentThread().getName()+"-[consumer]暂时无资源，等待...");
				customerCondition.await();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
}

class Customer1 extends Thread{
	Resource1 resource1;
	public Customer1(Resource1 resource1){
		this.resource1 = resource1;
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
			resource1.remove();
		}
	}
}

class Producer1 extends Thread{
	Resource1 resource1;
	public Producer1(Resource1 resource1){
		this.resource1 = resource1;
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
			resource1.add();
		}
	}
}

