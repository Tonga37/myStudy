package com.tonga.thread.thread;

/**
 * 
 * @Title: SleepTest
 * @author tangjia
 * @date 2018-3-4 下午3:08:04
 */
public class SleepTest {
	
	/**
	 * 通过sleep方法实现的暂停，程序是顺序进入同步块的，只有当上一个线程执行完成的时候，下一个线程才能进入同步方法，
	 * sleep暂停期间一直持有monitor对象锁，其他线程是不能进入的。
	 * 而wait方法则不同，当调用wait方法后，当前线程会释放持有的monitor对象锁，因此，其他线程还可以进入到同步方法，线程被唤醒后，需要竞争锁，获取到锁之后再继续执行。
	 */
    public synchronized void sleepMethod(){
        System.out.println("Sleep start-----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sleep end-----");
    }

    public synchronized void waitMethod(){
        System.out.println("Wait start-----");
        synchronized (this){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Wait end-----");
    }

    public static void main(String[] args) {
        final SleepTest test1 = new SleepTest();

        for(int i = 0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test1.sleepMethod();
                }
            }).start();
        }


        try {
            Thread.sleep(3000);//暂停三秒，等上面程序执行完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------分割线-------------------");

        final SleepTest test2 = new SleepTest();

        for(int i = 0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test2.waitMethod();
                }
            }).start();
        }

    }
}