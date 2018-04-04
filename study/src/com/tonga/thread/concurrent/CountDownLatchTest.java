package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * һ��ͬ�������࣬�����һ�����������߳���ִ�еĲ���֮ǰ��������һ�������߳�һֱ�ȴ���
 * �ø����ļ��� ��ʼ�� CountDownLatch�����ڵ����� countDown() �����������ڵ�ǰ����������֮ǰ��await ������һֱ��������
 * ֮�󣬻��ͷ����еȴ����̣߳�await �����к������ö����������ء���������ֻ����һ�Ρ��������޷������á������Ҫ���ü������뿼��ʹ�� CyclicBarrier��
 * 
 * ͨ��CountDownLatch��ʵ�����̺߳����߳�ͨ�ţ����̷߳��������������̣߳����߳�ȫ����ɺ�֪ͨ���̡߳�
 * @Title: CountDownLatchTest
 * @author tangjia
 * @date 2018-3-4 ����9:51:03
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        
                        System.out.println(Thread.currentThread().getName()+"���ڵȴ�����");
                        cdOrder.await();//���̵߳ȴ�
                        System.out.println(Thread.currentThread().getName()+"��������");
                        Thread.sleep(new Random().nextInt(10000));
                        System.out.println(Thread.currentThread().getName()+"����Ӧ��");
                        cdAnswer.countDown();//��cdAnswerΪ0ʱ�����߳��յ��ظ�
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(runnable);
        }
        
        /*
         * ���߳�
         */
        try {
            Thread.sleep(new Random().nextInt(10000));
            System.out.println(Thread.currentThread().getName()+"������������");
            System.out.println(Thread.currentThread().getName()+"��������");
            cdOrder.countDown();//�������߳�
            cdAnswer.await();//�ȴ����̻߳ظ�
            System.out.println(Thread.currentThread().getName()+"�յ�Ӧ��");
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        threadPool.shutdown();
    }
}