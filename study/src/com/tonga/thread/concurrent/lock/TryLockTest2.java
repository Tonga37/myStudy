package com.tonga.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: TryLockTest
 * @author tangjia
 * @date 2018-3-2 下午3:49:34
 */
public class TryLockTest2 {
	
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
        Thread thread1=new SubThead(lock);
        Thread thread2=new SubThead(lock);
        
        thread1.start();
        thread2.start();
	}
}

class SubThead extends Thread{
	
	public Lock lock = new ReentrantLock();
	
	public SubThead(Lock lock) {
		this.lock = lock;
	}
	
	@Override
    public void run() {
        if(lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName()+"得到了锁");
    			try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                System.out.println(Thread.currentThread().getName()+"释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName()+"获取锁失败");
        }
    }
	
}




