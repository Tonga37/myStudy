package com.tonga.thread.thread.join;

/**
 * 
 * @Title: JoinTest
 * @author tangjia
 * @date 2018-3-4 ����3:12:59
 */
public class JoinTest implements Runnable{

    /**
     * join��������ͨ��wait���������̵߳����������join���̻߳���ִ�У��򽫵�ǰ�߳�����������ֱ��join���߳�ִ����ɣ���ǰ�̲߳���ִ�С�
     * ������һ����Ҫע�⣬�����joinֻ������wait������ȴû�ж�Ӧ��notify������ԭ����Thread��start������������Ӧ�Ĵ���
     * ���Ե�join���߳�ִ������Ժ󣬻��Զ��������̼߳�������ִ�С�
     * @param args
     */
    public static void main(String[] args) {
        for (int i=0;i<3;i++) {
            Thread test = new Thread(new JoinTest());
            test.start();
            try {
            	/**
            	 * ��û��ʹ��join����֮�䣬�߳��ǲ���ִ�еģ���ʹ��join�����������߳���˳��ִ�еġ�
            	 */
                test.join(); //����join����
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finished~~~");
    }
    
    @Override
    public void run() {

        try {
            System.out.println(Thread.currentThread().getName() + " start-----");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " end------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}