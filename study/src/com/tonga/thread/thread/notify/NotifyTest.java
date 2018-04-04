package com.tonga.thread.thread.notify;

/**
 * 
 * @Title: NotifyTest
 * @author tangjia
 * @date 2018-3-4 ����3:02:26
 */
public class NotifyTest {
	
	/**
	 *��1������wait�������߳��ǻ��ͷŶ�monitor���������Ȩ�ġ�
	 *��2��һ��ͨ��wait�����������̣߳�����ͬʱ�������������������ܱ�����ִ�У�
	 * �߳���Ҫ�����ѣ���ʱ���ѻ����notify/notifyll����
	 * �̻߳��Ѻ���Ҫ����������monitor����
	 */
    public synchronized void testWait(){
        System.out.println(Thread.currentThread().getName() +" Start-----");
        try {
            wait(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() +" End-------");
    }

    public static void main(String[] args) throws InterruptedException {
        final NotifyTest test = new NotifyTest();
        for(int i=0;i<3;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test.testWait();
                }
            }).start();
        }

        synchronized (test) {
            test.notify();
        }
        Thread.sleep(3000);
        System.out.println("-----------�ָ���-------------");
        
        synchronized (test) {
            test.notifyAll();
        }
    }
}