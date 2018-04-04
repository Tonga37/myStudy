package com.tonga.thread.thread;

/**
 * 
 * @Title: SleepTest
 * @author tangjia
 * @date 2018-3-4 ����3:08:04
 */
public class SleepTest {
	
	/**
	 * ͨ��sleep����ʵ�ֵ���ͣ��������˳�����ͬ����ģ�ֻ�е���һ���߳�ִ����ɵ�ʱ����һ���̲߳��ܽ���ͬ��������
	 * sleep��ͣ�ڼ�һֱ����monitor�������������߳��ǲ��ܽ���ġ�
	 * ��wait������ͬ��������wait�����󣬵�ǰ�̻߳��ͷų��е�monitor����������ˣ������̻߳����Խ��뵽ͬ���������̱߳����Ѻ���Ҫ����������ȡ����֮���ټ���ִ�С�
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
            Thread.sleep(3000);//��ͣ���룬���������ִ�����
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------�ָ���-------------------");

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