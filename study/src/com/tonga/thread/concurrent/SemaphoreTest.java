package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ����
 *	�ź���(Semaphore)����ʱ����Ϊ�źŵƣ����ڶ��̻߳�����ʹ�õ�һ����ʩ, ������Э�������߳�, �Ա�֤�����ܹ���ȷ�������ʹ�ù�����Դ��
 * ����
 *	Semaphore��Ϊ��ֵ�Ͷ�ֵ���֣�ǰ��ֻ�ܱ�һ���̻߳�ã����߿��Ա����ɸ��̻߳�á�
 *	Semaphore��ǰ�ڶ��̻߳����±�����ʹ�ã�����ϵͳ���ź����Ǹ�����Ҫ�ĸ���ڽ��̿��Ʒ��涼��Ӧ�á�
 *  Java������Semaphore ���Ժ���������ź������ƣ�Semaphore���Կ���ĳ����Դ�ɱ�ͬʱ���ʵĸ�����ͨ�� acquire() ��ȡһ����ɣ�
 *  ���û�о͵ȴ����� release() �ͷ�һ����ɡ�������Windows�¿������ù����ļ������ͻ��˷��ʸ�����
 *	�����ź�����Semaphore�������ʵ�ֻ������Ĺ��ܣ����ҿ�������һ���̻߳���ˡ�������������һ���߳��ͷš����������Ӧ���������ָ���һЩ���ϡ�
 * ʵ��
 * 	������һ�������źŵƣ�����10���̷ֱ߳��ȡ�źŵƣ����źŵƱ�ռ��ʱ�������߳�ֻ�ܵȴ������źŵƱ��ͷ���ȴ��̻߳�ȡ�źŵơ�
 * @Title: SemaphoreTest
 * @author tangjia
 * @date 2018-3-4 ����3:45:00
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ExecutorService pool =  Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3,true);
        
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();//��ȡ�źŵ����
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Thread "+Thread.currentThread().getName()+" ����" +"��ǰϵͳ�Ĳ������ǣ�"+(3-semaphore.availablePermits()));
                    try {
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Thread "+Thread.currentThread().getName()+" �����뿪");
                    semaphore.release();//�ͷ��źŵ�
                    System.out.println("Thread "+Thread.currentThread().getName()+" �Ѿ��뿪����ǰϵͳ�Ĳ������ǣ�"+(3-semaphore.availablePermits()));
                }
            };
            pool.execute(runnable);
        
        }
    }
}