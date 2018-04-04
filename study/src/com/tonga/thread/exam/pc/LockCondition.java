package com.tonga.thread.exam.pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock��Condition��ʽʵ��������-������
 * <p>Title: LockCondition.java</p>  
 * @author tangjia
 * @date 2018-3-14 ����2:25:48 
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
				System.out.println(Thread.currentThread().getName()+"-[producer]������Դ�ɹ����ܹ���Դ��" + num);
				customerCondition.signalAll();
			}else {
				System.out.println(Thread.currentThread().getName()+"-[producer]��Դ�������ȴ�...");
				producerCondition.await();	//�˴�ǧ��Ҫ����wait()�����������IllegalMonitorStateException�쳣
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
				System.out.println(Thread.currentThread().getName()+"-[consumer]������Դ�ɹ���ʣ����Դ��" + num);
				producerCondition.signalAll();
			}else{
				System.out.println(Thread.currentThread().getName()+"-[consumer]��ʱ����Դ���ȴ�...");
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

