package com.tonga.thread.exam.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞队列与普通队列的区别在于，当队列是空的时，从队列中获取元素的操作将会被阻塞；或者当队列是满时，往队列里添加元素的操作会被阻塞。
 * 试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程往空的队列插入新的元素。
 * 同样，试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其他的线程使队列重新变得空闲起来，如从队列中移除一个或者多个元素，或者完全清空队列.
 * 
 * synchronized 和 wait/notify 实现
 * 
 * <p>Title: MyQueueTest.java</p>  
 * @author tangjia
 * @date 2018-3-12 下午5:23:28 
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
	 * 试图从空的阻塞队列中获取元素的线程将会被阻塞
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
	 * 试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞
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
            //生成随机数,按照一定比率读取或者放入，可以更改！！！
            if (r < 30){
            	myBlockingQueue.put(r);
            } else {
            	myBlockingQueue.take();
            }
		}
		
	}
	
}
