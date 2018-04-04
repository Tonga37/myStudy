package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        /**
         * Exchanger实现的是一种数据分片的思想，这在大数据情况下将数据分成一定的片段并且多线程执行的情况下有一定的使用价值。
         */
        final Exchanger<String> exchanger = new Exchanger<String>();
        
        threadPool.execute(new Runnable() {
            
            @Override
            public void run() {
                String date1 = "love";
                System.out.println("Thread "+Thread.currentThread().getName()+"正在把数据"+date1+"放入！");
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String date2 = null;
                try {
                    date2 = (String) exchanger.exchange(date1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Thread "+Thread.currentThread().getName()+"得到数据"+date2);
            }
        });
        threadPool.execute(new Runnable() {
            
            @Override
            public void run() {
                String date1 = "hate";
                System.out.println("Thread "+Thread.currentThread().getName()+"正在把数据"+date1+"放入！");
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String date2 = null;
                try {
                    date2 = (String) exchanger.exchange(date1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Thread "+Thread.currentThread().getName()+"得到数据"+date2);
            }
        });
        
        threadPool.shutdown();
    }
}