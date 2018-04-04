package com.tonga.thread.exam;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这3个线程的ID分别为A、B、C，每个线程将自己的ID在屏幕上打印10遍，
 * 要求输出结果必须按ABC的顺序显示；如：ABCABC….依次递推。
 * 
 * <p>Title: ThreadExam1.java</p>  
 * @author tangjia
 * @date 2018-3-16 上午9:36:32 
 * @version 1.0
 */
public class ThreadExam1 {
	
	public static volatile String flag = "A";	//控制线程按ABC顺序执行
	
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		
		Thread work1 = new WorkThread(lock, condition1, condition2, "A");
		Thread work2 = new WorkThread(lock, condition2, condition3, "B");
		Thread work3 = new WorkThread(lock, condition3, condition1, "C");
		
		try {
			work1.start();
//			Thread.sleep(1000);	//确保按三个线程按ABC顺序执行
			work2.start();
//			Thread.sleep(1000);
			work3.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class WorkThread extends Thread{
	private Lock lock;
	private Condition currentCondition;
	private Condition nextCondition;
	private String workContent;
	public WorkThread(Lock lock, Condition currentCondition, Condition nextCondition, String workContent){
		this.lock = lock;
		this.currentCondition = currentCondition;
		this.nextCondition = nextCondition;
		this.workContent = workContent;
	}
	
	@Override
	public void run() {
		int i = 0;
		while(i<10){
			if(ThreadExam1.flag.equals(workContent)){
				lock.lock();
				try {
					i++;
					System.out.print(workContent);
					nextCondition.signal();
					
					if("A".equals(ThreadExam1.flag)){
						ThreadExam1.flag = "B";
					}else if ("B".equals(ThreadExam1.flag)) {
						ThreadExam1.flag = "C";
					}else if ("C".equals(ThreadExam1.flag)) {
						ThreadExam1.flag = "A";
					}
					
					if(i<10){	//最后一次无需等待，让线程退出
						currentCondition.await();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
		}
	}
	
}


