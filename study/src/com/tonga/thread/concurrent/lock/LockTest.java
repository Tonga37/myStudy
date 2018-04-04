package com.tonga.thread.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: LockTest
 * @author tangjia
 * @date 2018-3-2 ����11:41:10
 * 
 * 1��Lock����Java�������õģ�synchronized��Java���ԵĹؼ��֣�������������ԡ�Lock��һ���࣬ͨ����������ʵ��ͬ�����ʣ�
 * 2��Lock��synchronized��һ��ǳ���Ĳ�ͬ������synchronized����Ҫ�û�ȥ�ֶ��ͷ�����
 *   ��synchronized��������synchronized�����ִ����֮��ϵͳ���Զ����߳��ͷŶ�����ռ�ã���Lock�����Ҫ�û�ȥ�ֶ��ͷ�����
 *   ���û�������ͷ��������п��ܵ��³�����������
 */
public class LockTest implements Runnable{
    
	public static ReentrantLock lock=new ReentrantLock();
    public static int c = 0;
    
    public static void main(String[] args) {
        LockTest lt=new LockTest();
        Thread thread1=new Thread(lt);
        Thread thread2=new Thread(lt);
        thread1.start();
        thread2.start();
        System.out.println(c);
    }
    
    public void run() {
        for(int i=0;i<5;i++){
            lock.lock();//��ȡ��
            try {
                System.out.println(Thread.currentThread().getName()+":lock");
                System.out.println(Thread.currentThread().getName()+"====>"+c);
    			try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                c++;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                System.out.println(Thread.currentThread().getName()+":unlock");
                lock.unlock();//�ͷ���
            }
        }
    }
}