package com.tonga.thread.concurrent.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: TryLockTest
 * @author tangjia
 * @date 2018-3-2 ����3:49:34
 */
public class TryLockTest implements Runnable {
	
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();    //ע������ط�
	
	public static void main(String[] args) {
        final TryLockTest tlt = new TryLockTest();
        
        Thread thread1=new Thread(tlt);
        Thread thread2=new Thread(tlt);
        
        thread1.start();
        thread2.start();
		
	}
	
	@Override
    public void run() {
        if(lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName()+"�õ�����");
                for(int i=0;i<5;i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                System.out.println(Thread.currentThread().getName()+"�ͷ�����");
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName()+"��ȡ��ʧ��");
        }
    }

}
