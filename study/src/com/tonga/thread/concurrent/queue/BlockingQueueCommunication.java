package com.tonga.thread.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue实现了线程同步，不可在方法中再次加入同步限制，否则会出现死锁。
 * @Title: BlockingQueueCommunication
 * @author tangjia
 * @date 2018-3-4 下午11:17:29
 */
public class BlockingQueueCommunication {
    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                for (int i = 0; i < 2; i++) {
                    try {
                        business.sub(i);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        for (int i = 0; i < 2; i++) {
            try {
                business.main(i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    static class Business{
        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
        {
            try {
                queue2.put(1);//保证queue2阻塞
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        public void main(int i) throws InterruptedException{
            queue1.put(1);//阻塞queue1
            for (int j = 0; j < 3; j++) {
                System.out.println("main thread is looping of "+j +" in " + i);
            }
            queue2.take();//唤醒queue2
        }
        public void sub(int i) throws InterruptedException{
            queue2.put(1);//阻塞queue2
            for (int j = 0; j < 2; j++) {
                System.out.println("sub thread is looping of "+j +" in " + i);
            }
            queue1.take();//唤醒queue1
        }
    }
}