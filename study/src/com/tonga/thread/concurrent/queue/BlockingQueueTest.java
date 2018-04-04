package com.tonga.thread.concurrent.queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * @Title: BlockingQueueTest
 * @author tangjia
 * @date 2018-3-4 ����11:14:52
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Thread "+Thread.currentThread().getName()+"����׼����������");
                        try {
                            //ģ���̵߳ķ����ٶ�
                            Thread.sleep(new Random().nextInt(1000));
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            queue.put(1);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("Thread "+Thread.currentThread().getName()+"��������,��ʱ�����е�����Ϊ��"+queue.size());
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Thread "+Thread.currentThread().getName()+"����ȡ������");
                        try {
                            //ģ���̵߳�ȥ���ٶ�
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            queue.take();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.println("Thread "+Thread.currentThread().getName()+"ȡ������,��ʱ�����е�����Ϊ��"+queue.size());
                    }
                }
            }).start();
        }
        
    }
}