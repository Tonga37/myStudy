package com.tonga.thread.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueueʵ�����߳�ͬ���������ڷ������ٴμ���ͬ�����ƣ���������������
 * @Title: BlockingQueueCommunication
 * @author tangjia
 * @date 2018-3-4 ����11:17:29
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
                queue2.put(1);//��֤queue2����
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        public void main(int i) throws InterruptedException{
            queue1.put(1);//����queue1
            for (int j = 0; j < 3; j++) {
                System.out.println("main thread is looping of "+j +" in " + i);
            }
            queue2.take();//����queue2
        }
        public void sub(int i) throws InterruptedException{
            queue2.put(1);//����queue2
            for (int j = 0; j < 2; j++) {
                System.out.println("sub thread is looping of "+j +" in " + i);
            }
            queue1.take();//����queue1
        }
    }
}