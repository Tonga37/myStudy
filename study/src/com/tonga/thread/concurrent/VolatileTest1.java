package com.tonga.thread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileTest1 {
	
	public static volatile String para = "a";
	
	public static void main(String[] args) {
		
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				while(true){
					VolatileTest1.change("b");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				while(true){
					VolatileTest1.change("c");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread thread3 = new Thread(new Runnable() {
			public void run() {
				while(true){
					System.out.println(VolatileTest1.para);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.submit(thread1);
		exec.submit(thread2);
		exec.submit(thread3);
		exec.shutdown();

	}
	
	public static void change(String para){
		VolatileTest1.para += para;
	}
	
}
