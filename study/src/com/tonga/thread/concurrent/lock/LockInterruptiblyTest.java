package com.tonga.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: LockInterruptiblyTest
 * @author tangjia
 * @date 2018-3-2 下午4:19:36
 */
public class LockInterruptiblyTest {
	
    private Lock lock = new ReentrantLock();   
    public static void main(String[] args)  {
    	LockInterruptiblyTest test = new LockInterruptiblyTest();
        MyThread thread1 = new MyThread(test);
        MyThread thread2 = new MyThread(test);
        thread1.start();
        thread2.start();
         
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
    }  
     
    public void insert(Thread thread) throws InterruptedException{
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {
            System.out.println(thread.getName()+"得到了锁");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finally {
            lock.unlock();
            System.out.println(thread.getName()+"释放了锁");
        }  
    }

}

class MyThread extends Thread {
    private LockInterruptiblyTest test = null;
    public MyThread(LockInterruptiblyTest test) {
        this.test = test;
    }
    @Override
    public void run() {
         
        try {
            test.insert(Thread.currentThread());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"被中断");
        }
    }
}
