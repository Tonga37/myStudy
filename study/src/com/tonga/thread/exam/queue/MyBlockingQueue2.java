package com.tonga.thread.exam.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * ͨ�� lock���Ķ�������condition����������ʵ��
 * 
 * <p>Title: MyBlockingQueue2.java</p>  
 * @author tangjia
 * @date 2018-3-13 ����5:39:26 
 * @version 1.0
 */
public class MyBlockingQueue2<E> {
	
	private int limit;
	private List<E> list;
	
	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();
	
	public MyBlockingQueue2(int limit){
		this.limit = limit;
		list = new LinkedList<>();
	}
	
	public static void main(String[] args) {
		MyBlockingQueue2<Integer> myBlockingQueue2 = new MyBlockingQueue2<>(10);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			exec.execute(new test(myBlockingQueue2));
		}
		
		exec.shutdown();
		
	}
	
	public void take(){
		lock.lock();
		try {
			while(list.size()==0){
				notEmpty.await();	//����Ϊ�գ���ȡ���ᱻ����
			}
			E a = list.get(0);
			System.out.println("take:" + a);
			list.remove(0);
			System.out.println("take->list:" + list.toString());
			notFull.signalAll();	//֪ͨput�߳�д������
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}

	public void put(E a){
		lock.lock();
		try {
			while(list.size()==limit){
				notFull.await();	//����������д���߳�����
			}
			System.out.println("put:"+a);
			list.add(a);
			System.out.println("put->list:" + list.toString());
			notEmpty.signalAll();	//֪ͨtake�̶߳�ȡ����
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			lock.unlock();
		}

	}
	
	static class test implements Runnable{
		private MyBlockingQueue2<Integer> myBlockingQueue2;
		
		public test(MyBlockingQueue2<Integer> myBlockingQueue2){
			this.myBlockingQueue2 = myBlockingQueue2;
		}

		@Override
		public void run() {
            Random random = new Random();
            int r = random.nextInt(100);
            //���������,����һ�����ʶ�ȡ���߷��룬���Ը��ģ�����
            if (r < 30){
            	myBlockingQueue2.put(r);
            } else {
            	myBlockingQueue2.take();
            }
		}
		
		
		
	}
}
