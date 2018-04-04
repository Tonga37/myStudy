package com.tonga.thread.exam.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ������������ͨ���е��������ڣ��������ǿյ�ʱ���Ӷ����л�ȡԪ�صĲ������ᱻ���������ߵ���������ʱ�������������Ԫ�صĲ����ᱻ������
 * ��ͼ�ӿյ����������л�ȡԪ�ص��߳̽��ᱻ������ֱ���������߳����յĶ��в����µ�Ԫ�ء�
 * ͬ������ͼ�����������������������Ԫ�ص��߳�ͬ��Ҳ�ᱻ������ֱ���������߳�ʹ�������±�ÿ�����������Ӷ������Ƴ�һ�����߶��Ԫ�أ�������ȫ��ն���.
 * 
 * synchronized �� wait/notify ʵ��
 * 
 * <p>Title: MyQueueTest.java</p>  
 * @author tangjia
 * @date 2018-3-12 ����5:23:28 
 * @version 1.0
 */
public class MyBlockingQueue<E> {
	
	private int limit;
	private List<E> list;
	
	public MyBlockingQueue(int limit){
		this.limit = limit;
		this.list = new LinkedList<E>();
	}
	
	public static void main(String[] args) {
		final MyBlockingQueue<Integer> myBlockingQueue = new MyBlockingQueue(10); 
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
			exec.execute(new test(myBlockingQueue));
		}
		exec.shutdown();
	}
	
	/**
	 * ��ͼ�ӿյ����������л�ȡԪ�ص��߳̽��ᱻ����
	 */
	public synchronized E take(){
		while(list.size() == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("list:" + list.toString());
		E remove = (E)list.remove(0);
		System.out.println("take:" + remove);
		notifyAll();
		return remove;
	}
	
	/**
	 * ��ͼ�����������������������Ԫ�ص��߳�ͬ��Ҳ�ᱻ����
	 */
	public synchronized void put(E e){
		while(list.size() == limit){
			try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println("list:" + list.toString());
		System.out.println("put:" + e);
		list.add(e);
		notifyAll();
	}
	
	static class test implements Runnable{
		
        private final MyBlockingQueue<Integer> myBlockingQueue;

        public test(MyBlockingQueue<Integer> myBlockingQueue) {
            this.myBlockingQueue = myBlockingQueue;
        }

		@Override
		public void run() {
            Random random = new Random();
            int r = random.nextInt(100);
            //���������,����һ�����ʶ�ȡ���߷��룬���Ը��ģ�����
            if (r < 30){
            	myBlockingQueue.put(r);
            } else {
            	myBlockingQueue.take();
            }
		}
		
	}
	
}
