package com.tonga.thread.exam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ��дһ�����򣬿���3���̣߳���3���̵߳�ID�ֱ�ΪA��B��C��ÿ���߳̽��Լ���ID����Ļ�ϣ�Ҫ�����������밴ABC��˳����ʾ���磺ABCABC��.���ε��ơ�
 * 
 * <p>Title: SemaphoreExam.java</p>  
 * @author tangjia
 * @date 2018-3-19 ����11:16:23 
 * @version 1.0
 */
public class SemaphoreExam {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(1);
		
		Thread thread1 = new PrintThread(semaphore, "A");
		Thread thread2 = new PrintThread(semaphore, "B");
		Thread thread3 = new PrintThread(semaphore, "C");
		
		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.submit(thread1);
		exec.submit(thread2);
		exec.submit(thread3);
		
		exec.shutdown();

	}

}

class PrintThread extends Thread{
	private Semaphore semaphore;
	private String content;
	
	public PrintThread(Semaphore semaphore, String content){
		this.semaphore = semaphore;
		this.content = content;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				semaphore.acquire();
				System.out.println(Thread.currentThread().getName() + "--->" + content);
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}


