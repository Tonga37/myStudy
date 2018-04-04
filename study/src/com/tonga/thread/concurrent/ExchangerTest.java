package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        /**
         * Exchangerʵ�ֵ���һ�����ݷ�Ƭ��˼�룬���ڴ���������½����ݷֳ�һ����Ƭ�β��Ҷ��߳�ִ�е��������һ����ʹ�ü�ֵ��
         */
        final Exchanger<String> exchanger = new Exchanger<String>();
        
        threadPool.execute(new Runnable() {
            
            @Override
            public void run() {
                String date1 = "love";
                System.out.println("Thread "+Thread.currentThread().getName()+"���ڰ�����"+date1+"���룡");
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
                System.out.println("Thread "+Thread.currentThread().getName()+"�õ�����"+date2);
            }
        });
        threadPool.execute(new Runnable() {
            
            @Override
            public void run() {
                String date1 = "hate";
                System.out.println("Thread "+Thread.currentThread().getName()+"���ڰ�����"+date1+"���룡");
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
                System.out.println("Thread "+Thread.currentThread().getName()+"�õ�����"+date2);
            }
        });
        
        threadPool.shutdown();
    }
}