package com.tonga.thread.exam.countdown;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �̶߳�׼����ɺ�һ��ִ�е����ӣ���ֵ�赲һ��
 * <p>Title: CountdownLatchTest.java</p>  
 * @author tangjia
 * @date 2018-3-15 ����4:10:19 
 * @version 1.0
 */
public class CountdownLatchTest1 {
    private final static int THREAD_NUM = 10;
    public static void main(String[] args) {
        CountDownLatch lock = new CountDownLatch(THREAD_NUM);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_NUM; i++) {
            exec.submit(new CountdownLatchTask(lock, "Thread-"+i));
        }
        exec.shutdown();
    }

    static class CountdownLatchTask implements Runnable{
        private final CountDownLatch lock;
        private final String threadName;
        CountdownLatchTask(CountDownLatch lock, String threadName) {
            this.lock = lock;
            this.threadName = threadName;
        }
        @Override public void run() {
            //ѭ�������Ϊ��֤����CountdownLatchֻ���赲һ�Σ�CyclicBarrierÿ�ζ����赲
            for (int i = 0; i < 3; i++) {
                System.out.println(threadName + " ׼�����");
                lock.countDown();
                try {
                    lock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " ִ�����");
                
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

        }
    }
}