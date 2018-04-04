package com.tonga.thread.thread.wait;

/**
 * 
 * @Title: WaitTest
 * @author tangjia
 * @date 2018-3-4 ����2:58:51
 */
public class WaitTest {

	/**
	 * wait������һ�����ط�������ײ���ͨ��һ���������������Ķ�������ɵġ�
	 * �ڵ���wait��ʽʱ����Ҫͨ��Synchronized�ؼ��֣���ȡ��monitor���������Ȩ���������IllegalMonitorStateException�쳣
	 */
	public synchronized void testWait(){//����Synchronized�ؼ���
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