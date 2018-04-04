package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。
 * 之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。如果需要重置计数，请考虑使用 CyclicBarrier。
 * 
 * 通过CountDownLatch来实现主线程和子线程通信，主线程发送命令启动子线程，子线程全部完成后通知主线程。
 * @Title: CountDownLatchTest
 * @author tangjia
 * @date 2018-3-4 下午9:51:03
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
                        
                        System.out.println(Thread.currentThread().getName()+"正在等待命令");
                        cdOrder.await();//子线程等待
                        System.out.println(Thread.currentThread().getName()+"接受命令");
                        Thread.sleep(new Random().nextInt(10000));
                        System.out.println(Thread.currentThread().getName()+"发出应答");
                        cdAnswer.countDown();//当cdAnswer为0时，主线程收到回复
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(runnable);
        }
        
        /*
         * 主线程
         */
        try {
            Thread.sleep(new Random().nextInt(10000));
            System.out.println(Thread.currentThread().getName()+"即将发出命令");
            System.out.println(Thread.currentThread().getName()+"发出命令");
            cdOrder.countDown();//启动子线程
            cdAnswer.await();//等待子线程回复
            System.out.println(Thread.currentThread().getName()+"收到应答");
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        threadPool.shutdown();
    }
}