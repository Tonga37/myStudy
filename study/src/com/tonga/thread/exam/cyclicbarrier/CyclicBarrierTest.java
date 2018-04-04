package com.tonga.thread.exam.cyclicbarrier;
import java.util.concurrent.*;

/**
 * CyclicBarrier例子，每次都会阻挡，可重用
 * <p>Title: CyclicBarrierTest.java</p>  
 * @author tangjia
 * @date 2018-3-15 下午4:13:27 
 * @version 1.0
 */
public class CyclicBarrierTest {
    private final static int THREAD_NUM = 10;
    public static void main(String[] args) {
        CyclicBarrier lock = new CyclicBarrier(THREAD_NUM, new Runnable() {
            @Override public void run() {
                System.out.println("大家都准备完成了");
            }
        });
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_NUM; i++) {
            exec.submit(new CountdownLatchTask(lock, "Thread-"+i));
        }
        exec.shutdown();
    }

    static class CountdownLatchTask implements Runnable{
        private final CyclicBarrier lock;
        private final String threadName;
        CountdownLatchTask(CyclicBarrier lock, String threadName) {
            this.lock = lock;
            this.threadName = threadName;
        }
        @Override public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(threadName + " 准备完成");
                try {
                    lock.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " 执行完成");
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