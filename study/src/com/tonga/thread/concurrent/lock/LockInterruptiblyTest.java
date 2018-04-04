package com.tonga.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: LockInterruptiblyTest
 * @author tangjia
 * @date 2018-3-2 ����4:19:36
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
        lock.lockInterruptibly();   //ע�⣬�����Ҫ��ȷ�жϵȴ������̣߳����뽫��ȡ���������棬Ȼ��InterruptedException�׳�
        try {
            System.out.println(thread.getName()+"�õ�����");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finally {
            lock.unlock();
            System.out.println(thread.getName()+"�ͷ�����");
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
            System.out.println(Thread.currentThread().getName()+"���ж�");
        }
    }
}
