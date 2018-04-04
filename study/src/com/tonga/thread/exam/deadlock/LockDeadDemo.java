package com.tonga.thread.exam.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * <p>Title: LockDeadDemo.java</p>  
 * @author tangjia
 * @date 2018-3-14 ����6:02:33 
 * @version 1.0
 */
public class LockDeadDemo {

    public static void main(String[] args){
        final DeadLockBean deadLockBean = new DeadLockBean();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    deadLockBean.productDeadLock();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        },"threadA");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(310);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    deadLockBean.productDeadLock();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        },"threadB");
        threadA.start();
        threadB.start();
        try {
            System.out.println("main�̼߳�����join");
            threadA.join();
            threadB.join();
            System.out.println("main�̴߳�join�лָ�");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class DeadLockBean{
        private Lock lock = new ReentrantLock();
        public void productDeadLock() throws Throwable {
            System.out.println(Thread.currentThread().getName() + "   �����˷�����");
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName() + "   �Ѿ�ִ���ˣ�");
                throw new Throwable("��Ϊ�׳��쳣Throwable");//�ؼ�������1��
                //throw new Exception("��Ϊ�׳��쳣Exception");//�ؼ�������2����������������catch(Exception e�б�����)��Ƕ��lock.unlock()���ͷ�
            }catch(Exception e){
                System.out.println(Thread.currentThread().getName()+"   �����쳣catch��");
                //lock.unlock();//�ؼ�������3���������������ͷţ����緢�����ؼ�������1�����������
            }finally{
                System.out.println(Thread.currentThread().getName()+"   �����쳣finally��");
                lock.unlock();//�ؼ�������4�����۷��������쳣�������ͷ�����
            }
            //lock.unlock();//�ؼ�������5�����緢�����ܲ����쳣�������������壬��ִ�д˴�
            System.out.println(Thread.currentThread().getName() + "   tryCatch���ͷ�����");
        }
    }
}
