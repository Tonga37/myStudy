package com.tonga.thread.concurrent.lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * <p>Title: ReadWriteLockTest.java</p>  
 * @author tangjia
 * @date 2018-3-12 下午4:43:15 
 * @version 1.0
 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final MyDate date = new MyDate();
		for (int i = 0; i < 3; i++) {	
			new Thread(new Runnable() {
				public void run() {
					while(true){
						date.put();
					}
				}
			}).start();
		
			new Thread(new Runnable() {
				public void run() {
					while(true){
						date.get();
					}
				}
			}).start();
		}
	}
	
}

class MyDate{
	ReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	Integer date;
	
	/**
	 * 读取数据方法
	 */
	public void get(){
		rwLock.readLock().lock();
		try {
//			Thread.sleep(5000);
			System.out.println(Thread.currentThread().getName() + "--->获取数据:" + date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			rwLock.readLock().unlock();
		}
	}
	
	/**
	 * 写入数据方法
	 */
	public void put(){
		rwLock.writeLock().lock();
		
		try {
			Thread.sleep(1000);
			date = new Random().nextInt(5000);
			System.out.println(Thread.currentThread().getName()+"------>写入数据:"+date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			rwLock.writeLock().unlock();
		}
	}
	
}
