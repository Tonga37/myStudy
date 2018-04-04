package com.tonga.thread.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: LockTest
 * @author tangjia
 * @date 2018-3-2 上午11:41:10
 * 
 * 1）Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问；
 * 2）Lock和synchronized有一点非常大的不同，采用synchronized不需要用户去手动释放锁，
 *   当synchronized方法或者synchronized代码块执行完之后，系统会自动让线程释放对锁的占用；而Lock则必须要用户去手动释放锁，
 *   如果没有主动释放锁，就有可能导致出现死锁现象。
 */
public class LockTest implements Runnable{
    
	public static ReentrantLock lock=new ReentrantLock();
    public static int c = 0;
    
    public static void main(String[] args) {
        LockTest lt=new LockTest();
        Thread thread1=new Thread(lt);
        Thread thread2=new Thread(lt);
        thread1.start();
        thread2.start();
        System.out.println(c);
    }
    
    public void run() {
        for(int i=0;i<5;i++){
            lock.lock();//获取锁
            try {
                System.out.println(Thread.currentThread().getName()+":lock");
                System.out.println(Thread.currentThread().getName()+"====>"+c);
    			try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                c++;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                System.out.println(Thread.currentThread().getName()+":unlock");
                lock.unlock();//释放锁
            }
        }
    }
}