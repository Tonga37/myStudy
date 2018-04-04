package com.tonga.thread.thread.wait;

/**
 * 
 * @Title: WaitTest
 * @author tangjia
 * @date 2018-3-4 下午2:58:51
 */
public class WaitTest {

	/**
	 * wait方法是一个本地方法，其底层是通过一个叫做监视器锁的对象来完成的。
	 * 在调用wait方式时，需要通过Synchronized关键字，获取到monitor对象的所有权。否则会抛IllegalMonitorStateException异常
	 */
	public synchronized void testWait(){//增加Synchronized关键字
        System.out.println("Start-----");
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End-------");
    }

    public static void main(String[] args) {
        final WaitTest test = new WaitTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.testWait();
            }
        }).start();
    }
}